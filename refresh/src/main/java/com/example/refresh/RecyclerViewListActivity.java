package com.example.refresh;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.refresh.adapter.CustomRVAdapter;
import com.example.refresh.divider.CustomItemDecoration;
import com.example.refresh.holder.CustomRVHolder;
import com.example.refresh.info.TngouInfo;
import com.example.refresh.interf.TngouService;
import com.example.refresh.interf.YQService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 1、万能adapter测试
 * 2、下拉刷新，上拉加载测试
 */
public class RecyclerViewListActivity extends AppCompatActivity implements Callback<TngouInfo> {
    private RecyclerView rcView;
    private SwipeRefreshLayout srLayout;
    private CustomRVAdapter<String> mAdapter;
    private FloatingActionButton fab;
    private int lastItemPosition;
    private int intNumber;
    Retrofit mRetrofit;
    List<String> nameList;
    int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        init();
    }

    private void initView() {
        rcView = (RecyclerView) findViewById(R.id.recyclerView);
        srLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void init() {
        final LinearLayoutManager llManager = new LinearLayoutManager(this);
        rcView.setLayoutManager(llManager);

        String[] names = getResources().getStringArray(R.array.listpersons);
        nameList = Arrays.asList(names);
        mAdapter = new CustomRVAdapter<String>(this, R.layout.item_contact_person) {
            @Override
            public void convert(CustomRVHolder holder, String item, int position) {
                holder.setText(R.id.tvName, item);
            }
        };
        rcView.setAdapter(mAdapter);
        mAdapter.addAll(nameList);

        //添加自定义分割线
        rcView.addItemDecoration(new CustomItemDecoration(this, CustomItemDecoration.ORIENTATION_HORIZONTAL));

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        srLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark, R.color.colorPrimary);
        srLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
//                        mAdapter.clear();
//                        mAdapter.addAll(nameList);
                        currentPage = 1;
                        refresh("http://www.tngou.net", currentPage);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 子线程中改变UI要runOnUiThread中实现
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                srLayout.setRefreshing(false);
                            }
                        });

                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });

        rcView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //当前列表拉到最底部
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 1 == mAdapter.getItemCount()) {
                    srLayout.setRefreshing(true);
                    mAdapter.add("老毛" + intNumber++, lastItemPosition + 1);
                    currentPage = 1;
                    refresh("http://www.tngou.net", currentPage);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // 子线程中改变UI要runOnUiThread中实现
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    srLayout.setRefreshing(false);
                                }
                            });
                        }
                    }).start();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastItemPosition = llManager.findLastCompletelyVisibleItemPosition();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download();
            }
        });
    }

    private void download() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://ww1.sinaimg.cn")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YQService service = mRetrofit.create(YQService.class);
        Call<ResponseBody> call = service.downloadPicture("/large/610dc034gw1ewwdtf8l8ej20qo046myh.jpg");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = response.body().byteStream();
                File fileDir = Environment.getExternalStorageDirectory();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public Interceptor getRequestHeader() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originRequest = chain.request();
                Request.Builder builder = originRequest.newBuilder();
                //请求头携带通用请求参数
                builder.header("appid", "1");
                builder.header("appKey", "abcdefg");

                Request.Builder requestBuilder = builder.method(originRequest.method(), originRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);

                /**
                 * 请求头参数携带通用请求参数
                 *
                HttpUrl httpUrl = originRequest.url().newBuilder()
                        .addQueryParameter("paltform", "Android")
                        .addQueryParameter("version", "1.0.1")
                        .build();
                Request request = originRequest.newBuilder()
                        .url(httpUrl)
                        .build();
                return chain.proceed(request);
                 */
            }
        };
        return headerInterceptor;
    }

    //非持久化cookie
    public void setCookie(OkHttpClient.Builder builder){
        builder.cookieJar(new CookieJar() {
            HashMap<HttpUrl,List<Cookie>> cookieMap = new HashMap<HttpUrl, List<Cookie>>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieMap.put(url,cookies); //保存cookie
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieMap.get(url); //取出cookie
                return cookies!=null?cookies:new ArrayList<Cookie>();
            }
        });
    }

    private void refresh(String url, int page) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TngouService service = mRetrofit.create(TngouService.class);

        Call<TngouInfo> call = service.ListRepos("cook", 0, page, 12);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<TngouInfo> call, Response<TngouInfo> response) {
        List<TngouInfo.Cook> cookList = response.body().list;
        List<String> nameList = new ArrayList<>();
        for (TngouInfo.Cook cook : cookList) {
            nameList.add(cook.getName());
        }

        if (currentPage == 1) {
            mAdapter.clear();
        }
        mAdapter.addAll(nameList);
        currentPage++;
    }

    @Override
    public void onFailure(Call<TngouInfo> call, Throwable t) {

    }
}
