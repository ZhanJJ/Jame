package com.example.refresh;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.refresh.adapter.CustomBaseAdapter;
import com.example.refresh.custom.CustomConvertFactory;
import com.example.refresh.holder.CustomBaseViewHolder;
import com.example.refresh.info.LoginInfo;
import com.example.refresh.info.TngouInfo;
import com.example.refresh.interf.TngouService;
import com.example.refresh.interf.YQService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    CustomBaseAdapter<String> mAdapter;
    Retrofit mRetrofit;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Map<String, String> paramMap = new HashMap<String, String>();
                paramMap.put("type", "8003");
                paramMap.put("phone", "13790303985");
                paramMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
//                loginToBean(paramMap);
                loginToJson(paramMap);
//                loginToString(paramMap);
            }
        });

        init();
    }

    /**
     * 返回解析实体
     *
     * @param paramMap 参数Map
     */
    private void loginToBean(Map<String, String> paramMap) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://app.eacheart.com:9090")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YQService yqService = mRetrofit.create(YQService.class);
        //普通传参
//        Call<LoginInfo> loginCall = yqService.loginIn("8003", "13790303985", "e10adc3949ba59abbe56e057f20f883e");
        //通过映射集合传参
        Call<LoginInfo> loginCall = yqService.loginIn(paramMap);
        loginCall.enqueue(new Callback<LoginInfo>() {
            @Override
            public void onResponse(Call<LoginInfo> call, Response<LoginInfo> response) {
                LoginInfo info = response.body();
                Snackbar.make(fab, info.getMsg(), Snackbar.LENGTH_SHORT).
                        setAction("Action", null).show();
            }

            @Override
            public void onFailure(Call<LoginInfo> call, Throwable t) {

            }
        });
    }

    /**
     * 单纯的返回 JSONObject
     * 1.添加自定义Converter
     * 2.GsonConverterFactory.create(gson)换成 JsonConverterFactory.create()
     */
    private void loginToJson(Map<String, String> paramMap) {
        YQService yqService = mRetrofit.create(YQService.class);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://app.eacheart.com:9090")
                .addConverterFactory(CustomConvertFactory.create())
                .build();

        Call<JSONObject> loginJsonCall = yqService.loginInToJson("application/x-www-form-urlencoded;charset=UTF-8", paramMap);
        loginJsonCall.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                JSONObject object = response.body();
                String header = response.raw().header("Content-type"); //获取response中的header
                if (object.has("msg")) {
                    Snackbar.make(fab, object.optString("msg"), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }

    /**
     * 单纯的返回 String
     * 1.添加自定义Converter
     * 2.GsonConverterFactory.create(gson)换成 JsonConverterFactory.create()
     */
    private void loginToString(Map<String, String> paramMap) {
        YQService yqService = mRetrofit.create(YQService.class);
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://app.eacheart.com:9090")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Call<String> loginJsonCall = yqService.loginInToString("application/x-www-form-urlencoded;charset=UTF-8", paramMap);
        loginJsonCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("response--->", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void init() {
        listView = (ListView) findViewById(R.id.listView);
        mAdapter = new CustomBaseAdapter<String>(this, R.layout.item_contact_person) {
            @Override
            public void convert(CustomBaseViewHolder viewHolder, String item) {
                viewHolder.setText(R.id.tvName, item);
            }
        };
        listView.setAdapter(mAdapter);

        String[] names = getResources().getStringArray(R.array.listpersons);
        List<String> nameList = Arrays.asList(names);
        getNameList(1);
    }

    private void getNameList(int page) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://www.tngou.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TngouService service = mRetrofit.create(TngouService.class);

        Call<TngouInfo> call = service.getList("cook", 0, page, 20);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("id", "0");
        paramMap.put("page", page + "");
        paramMap.put("rows", "10");
        Call<TngouInfo> infoCall = service.getList("cook", paramMap);

        infoCall.enqueue(new Callback<TngouInfo>() {
            @Override
            public void onResponse(Call<TngouInfo> call, Response<TngouInfo> response) {
                List<String> stringList = new ArrayList<String>();
                List<TngouInfo.Cook> cookList = response.body().list;
                for (TngouInfo.Cook cook : cookList) {
                    stringList.add(cook.getName());
                }
                mAdapter.clear();
                mAdapter.add(stringList);
            }

            @Override
            public void onFailure(Call<TngouInfo> call, Throwable t) {

            }
        });
    }
}
