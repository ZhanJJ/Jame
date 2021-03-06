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

package com.example.token.http.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by david on 16/8/20.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public interface IApiService {

    @GET("get_token")
    Observable<TokenModel> getToken();

    @GET("refresh_token")
    Observable<TokenModel> refreshToken();

    @GET("request")
    Observable<ResultModel> getResult(@Query("token") String token);
}
