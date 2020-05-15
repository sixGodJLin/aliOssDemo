package com.jlin.aliossdemo.utils.http;

import com.jlin.aliossdemo.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author JLin
 * @date 2019/11/9
 * @describe retrofit管理类
 */
public class RetrofitManager {

    private static final int DEFAULT_TIME_OUT = 5;
    /* 读写超时i */
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private Retrofit retrofit;
    private HttpApi httpApi;

    private RetrofitManager() {
        /* 创建OkHttpClient */
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        /* 连接超时时间 */
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
        /* 读操作超时 */
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        /* 写操作超时 */
        builder.writeTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);

        /* ChangeRocModeHandler公共参数拦截器 */
        HttpCommonInterceptor commonInterceptor
                = new HttpCommonInterceptor.Builder()
//                .addHeaderParams("Accept", "application/json")
                .addHeaderParams("Content-Type", "application/json; charset=utf-8")
                .build();
        builder.addInterceptor(commonInterceptor);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }
        /* 创建Retrofit */
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://www.ck9696.com/api/")
                .build();
        httpApi = retrofit.create(HttpApi.class);
    }

    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    /**
     * 获取RetrofitServiceManager
     */
    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取httpService
     *
     * @return httpApi
     */
    public HttpApi getHttpApi() {
        return httpApi;
    }
}
