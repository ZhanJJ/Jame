/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.token;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.token.base.BaseActivity;
import com.example.token.http.GlobalToken;
import com.example.token.http.RetrofitUtil;
import com.example.token.http.api.IApiService;
import com.example.token.http.api.ResultModel;
import com.example.token.http.api.TokenModel;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by david on 16/8/21.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class TokenTestActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_token_get;
    private Button btn_request;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_token_test;
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
        initView();
    }

    private void initView() {
        btn_token_get = (Button) findViewById(R.id.btn_token_get);
        btn_request = (Button) findViewById(R.id.btn_request);

        btn_token_get.setOnClickListener(this);
        btn_request.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_request: {
                for (int i = 0; i < 5; i++) {
                    RetrofitUtil.getInstance()
                            .getProxy(IApiService.class)
                            .getResult(GlobalToken.getToken())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<ResultModel>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(ResultModel model) {

                                }
                            });
                }
                break;
            }
            case R.id.btn_token_get: {
                RetrofitUtil.getInstance()
                        .get(IApiService.class)
                        .getToken()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<TokenModel>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(TokenModel model) {
                                if (model != null && !TextUtils.isEmpty(model.token)) {
                                    GlobalToken.updateToken(model.token);
                                }
                            }
                        });
                break;
            }
        }
    }
}
