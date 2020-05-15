package com.jlin.aliossdemo.utils.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author JLin
 * @date 2019/11/9
 * @describe retrofit 请求头拦截器
 */
public class HttpCommonInterceptor implements Interceptor {

    private Map<String,String> mHeaderParamsMap = new HashMap<>();

    public HttpCommonInterceptor(){}

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        /* 新的请求 */
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(),oldRequest.body());

        /* 添加公共参数至head */
        if(mHeaderParamsMap.size()>0){
            for(Map.Entry<String,String> params:mHeaderParamsMap.entrySet()){
                requestBuilder.header(params.getKey(),params.getValue());
            }
        }

        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    public static class Builder{
        HttpCommonInterceptor httpCommonInterceptor;
        public Builder(){
            httpCommonInterceptor = new HttpCommonInterceptor();
        }

        public Builder addHeaderParams(String key, String value){
            httpCommonInterceptor.mHeaderParamsMap.put(key,value);
            return this;
        }

        public Builder addHeaderParams(String key, int value){
            httpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public Builder addHeaderParams(String key, float value){
            httpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public Builder addHeaderParams(String key, long value){
            httpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public Builder addHeaderParams(String key, double value){
            httpCommonInterceptor.mHeaderParamsMap.put(key, String.valueOf(value));
            return this;
        }

        public HttpCommonInterceptor build(){
            return httpCommonInterceptor;
        }

    }


}
