package com.codersworld.safelib.rest.ttlock;

 import com.codersworld.safelib.rest.ApiRequest;
 import com.google.gson.reflect.TypeToken;
import com.ttlock.bl.sdk.util.LogUtil;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;



public class RetrofitAPIManager {
//    public static final String SERVER_URL = "https://api.ttlock.com.cn";
    public static final String SERVER_URL = "https://euapi.ttlock.com";

    //public static final String SERVER_URL = "https://cnapi.ttlock.com";

    public static ApiRequest provideClientApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(genericClient())
                .baseUrl(SERVER_URL)
                .addConverterFactory(
                        new Converter.Factory() {//Converter转换器
                            @Override
                            public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                                return new Converter<ResponseBody, String>() {

                                    @Override
                                    public String convert(ResponseBody value) throws IOException {
                                        String json = value.string();
                                        LogUtil.d("json:" + json);
                                        return json;
                                    }
                                };
                            }
                        }
                )
                .build();
        return retrofit.create(ApiRequest.class);
    }

    public static OkHttpClient genericClient() {
       /* HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("APIresponse", "" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);*/
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(35, TimeUnit.SECONDS)
             //   .addInterceptor(httpLoggingInterceptor)
                .readTimeout(35, TimeUnit.SECONDS)
                .writeTimeout(35, TimeUnit.SECONDS)
                .build();

        return httpClient;
    }

    public static <T> ApiRequtest enqueue(Call<ResponseBody> call, TypeToken<T> resultType, ApiResponse.Listener<ApiResult<T>> listener, ApiResponse.ErrorListener errorListener) {
        ApiRequtest<T> request = new ApiRequtest<>(call, resultType, listener, errorListener);
        return request;
    }
}