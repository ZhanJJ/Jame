package com.example.refresh.RxJava;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.refresh.R;
import com.example.refresh.custom.JsonConverterFactory;
import com.example.refresh.httpClient.RetrofitUtils;
import com.example.refresh.interf.YQService;
import com.example.refresh.retrofitHttpClient.HttpCallBack;
import com.example.refresh.retrofitHttpClient.MyHttpUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit.HttpUtil;
import retrofit.interfaces.Error;
import retrofit.interfaces.HeadersInterceptor;
import retrofit.interfaces.ParamsInterceptor;
import retrofit.interfaces.Success;

/**
 * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
 * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
 * Schedulers.newThread() 代表一个常规的新线程
 * AndroidSchedulers.mainThread() 代表Android的主线程
 */
public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = "RxJava";
    private CompositeDisposable mDisposable;
    private FloatingActionButton fab;
    private YQService mService;
    private Context mContext;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.clear(); //退出activity时执行disposable.dispose()方法，使得观察者不再接收事件
        HttpUtil.cancel(this);
    }

    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        findViewById(R.id.btnLogin2).setOnClickListener(this);
    }

    private void init() {
        mDisposable = new CompositeDisposable();
        mService = RetrofitUtils.create("http://app.eacheart.com:9090").create(YQService.class);
//        test1();
//        test2();
//        testZip();
        testFlowable();
    }

    //线程控制
    private void test1() {
        Observable<Integer> observable = new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                // Emitter:发射器
                Log.i(TAG, "Observable thread is: " + Thread.currentThread().getName());
                Log.i(TAG, "Emit 1");
                observer.onNext(1);
                observer.onComplete();
//                for (int i = 0; ; i++) {
//                    observer.onNext(i);
//                }
            }
        };
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "Observer thread is: " + Thread.currentThread().getName());
                Log.i(TAG, "onNext: " + integer);
            }
        };

        observable.subscribeOn(Schedulers.newThread()) //上游-子线程
                .observeOn(AndroidSchedulers.mainThread()) //下游-主线程
                .subscribe(consumer);
    }

    //测试变换操作符
    private void test2() {
        Observable<Integer> observable = new Observable<Integer>() {
            @Override
            protected void subscribeActual(Observer<? super Integer> observer) {
                // Emitter:发射器
                Log.i(TAG, "Observable thread is: " + Thread.currentThread().getName());
                Log.i(TAG, "Emit 1");
                observer.onNext(1);
                Log.i(TAG, "Emit 2");
                observer.onNext(2);
                Log.i(TAG, "Emit 3");
                observer.onNext(3);
                observer.onComplete();
            }
        };

        /**
         * 变换操作符Map
         * 作用就是对上游发送的每一个事件应用一个函数, 使得每一个事件都按照指定的函数去变化.
         */
//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .map(new Function<Integer, String>() {
//                    @Override
//                    public String apply(Integer integer) throws Exception {
//                        return "str" + integer;
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        Log.i(TAG, "Observer thread is: " + Thread.currentThread().getName());
//                        Log.i(TAG, "onNext: " + s);
//                    }
//                });

        /**
         * 变换操作符FlatMap
         * 将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里.
         * 注意flatMap并不保证事件的顺序(使用concatMap则能按照上游发送事件的顺序，来发送，用法基本一致)
         */
        observable.flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> strList = new ArrayList<String>();
                Log.i(TAG, "Flat Emit: " + integer);
                for (int i = 0; i < 2; i++) {
                    strList.add("from flatMap" + integer);
                }
                return Observable.fromIterable(strList).delay(10, TimeUnit.MILLISECONDS); //延时10毫秒
            }
        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "onNext: " + s);
                    }
                });

    }

    //Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件
    //它按照严格的顺序应用这个函数
    //它只发射与发射数据项最少的那个Observable一样多的数据
    private void testZip() {

        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.i(TAG, "Emit 1");
                e.onNext(2);
                Log.i(TAG, "Emit 2");
                e.onNext(3);
                Log.i(TAG, "Emit 3");
                e.onNext(4);
                Log.i(TAG, "Emit 4");
                e.onComplete();
            }
        }).observeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("A");
                Log.i(TAG, "Emit A");
                e.onNext("B");
                Log.i(TAG, "Emit B");
                e.onNext("C");
                Log.i(TAG, "Emit C");
                e.onComplete();
            }
        }).observeOn(Schedulers.newThread());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                Log.i(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void testFlowable() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
//                for (int i = 0; i < 130; i++) {
//                    Log.i(TAG, "Emit " + i);
//                    e.onNext(i);
//                }
                Log.i(TAG, "Observable thread is: " + Thread.currentThread().getName());
                Log.i(TAG, "Emit 1");
                e.onNext(1);
                Log.i(TAG, "Emit 2");
                e.onNext(2);
                Log.i(TAG, "Emit 3");
                e.onNext(3);
                Log.i(TAG, "Emit 4");
                e.onNext(4);
                Log.i(TAG, "Emit 5");
                e.onNext(5);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
//                s.request(2);
                Log.i(TAG, "onSubscribe:");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.i(TAG, "onError: " + t.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        flowable.subscribeOn(Schedulers.io()).subscribe(subscriber);
    }

    private void login() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("type", "8003");
        paramMap.put("phone", "13790303985");
        paramMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
        mService.loginInWithRxJava(paramMap)
                .subscribeOn(Schedulers.io()) //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) //回到主线程处理请求结果
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(JSONObject value) {
                        Log.i(TAG, value.optString("msg"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Snackbar.make(fab, "登录失败", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Snackbar.make(fab, "登录成功", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    //个人信息
    private void personalInfo(String token) {
        mService.personalInfo("8059", token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        Snackbar.make(fab, "您的昵称是： " + jsonObject.optString("username"), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginAndInfo() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("type", "8003");
        paramMap.put("phone", "13790303985");
        paramMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
        YQService yqService = RetrofitUtils.create("http://app.eacheart.com:9090").create(YQService.class);
        yqService.loginInWithRxJava(paramMap)
                .subscribeOn(Schedulers.io()) //在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread()) //回到主线程处理请求结果
                .doOnNext(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        Snackbar.make(fab, jsonObject.optString("msg"), Snackbar.LENGTH_SHORT).show();
                    }
                })
                .observeOn(Schedulers.io()) //回到IO线程去发起请求个人信息请求
                .flatMap(new Function<JSONObject, ObservableSource<JSONObject>>() {
                    @Override
                    public ObservableSource<JSONObject> apply(JSONObject jsonObject) throws Exception {
                        return mService.personalInfo("8059", jsonObject.optString("token"));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())  //回到主线程去处理请求个人信息的结果
                .subscribe(new Consumer<JSONObject>() {
                    @Override
                    public void accept(JSONObject jsonObject) throws Exception {
                        Snackbar.make(fab, "您的昵称是： " + jsonObject.optString("username"), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void login2() {
        new MyHttpUtil.SingleBuilder(mContext)
                .version("1.0.0")
                .baseUrl("http://app.eacheart.com:9090")
                .addConverterFactory(JsonConverterFactory.create())
                .addCallFactory(RxJava2CallAdapterFactory.create())
                .paramsInterceptor(new com.example.refresh.retrofitHttpClient.interf.ParamsInterceptor() {
                    @Override
                    public Map checkParams(Map<String, String> map) {
                        if (map != null) {
                            map.put("token", "");
                        }
                        return map;
                    }
                })
                .build();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("type", "8003");
        paramMap.put("phone", "13790303985");
        paramMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
        new MyHttpUtil.Builder()
                .Params(paramMap)
                .Url("http://app.eacheart.com:9090")
                .post(new HttpCallBack() {
                    @Override
                    public void success(JSONObject model) {
                        Snackbar.make(fab, "登录成功", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void fail(String msg) {
                        Snackbar.make(fab, "登录失败", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void test3() {
        ParamsInterceptor mParamsInterceptor = new ParamsInterceptor() {
            @Override
            public Map checkParams(Map params) {
                //追加统一参数
                params.put("app_type", "android_price");
                return params;
            }
        };
        HeadersInterceptor mHeadersInterceptor = new HeadersInterceptor() {
            @Override
            public Map checkHeaders(Map headers) {
                //追加统一header，例：数据缓存一天
                headers.put("Cache-Time", "3600*24");
                return headers;
            }
        };

        new HttpUtil.SingletonBuilder(getApplicationContext())
                .baseUrl("http://sw.bos.baidu.com")//URL请求前缀地址。必传
//                .versionApi("")//API版本，不传不可以追加接口版本号
//                .addServerUrl("")//备份服务器ip地址，可多次调用传递
//                .addCallFactory()//不传默认StringConverterFactory
//                .addConverterFactory()//不传默认RxJavaCallAdapterFactory
//                .client()//OkHttpClient,不传默认OkHttp3
                .paramsInterceptor(mParamsInterceptor)//不传不进行参数统一处理
                .headersInterceptor(mHeadersInterceptor)//不传不进行headers统一处理
                .build();

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("type", "8003");
        paramMap.put("phone", "13790303985");
        paramMap.put("password", "e10adc3949ba59abbe56e057f20f883e");
        new HttpUtil.Builder("http://app.eacheart.com:9090")

                .Success(new Success() {
                    @Override
                    public void Success(String model) {
                        Snackbar.make(fab, model, Snackbar.LENGTH_SHORT).show();
                    }
                })
                .Error(new Error() {
                    @Override
                    public void Error(Object... values) {
                        Snackbar.make(fab, values.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                })
                .post();

//        new HttpUtil.Builder("http://sw.bos.baidu.com/sw-search-sp/software/c07cde08ce4/Photoshop_CS6.exe")
//                .SavePath(getExternalFilesDir(null) + File.separator + "Photoshop_CS6.exe")
//                .Success(new Success() {
//                    @Override
//                    public void Success(String model) {
//                        //返回path
//                    }
//                })
//                .Error(new Error() {
//                    @Override
//                    public void Error(Object... values) {
//
//                    }
//                })
//                .download();
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.fab: {
                loginAndInfo();
                break;
            }
            case R.id.btnLogin: {
                login2();
                break;
            }
            case R.id.btnLogin2: {
                test3();
                break;
            }
        }
    }
}
