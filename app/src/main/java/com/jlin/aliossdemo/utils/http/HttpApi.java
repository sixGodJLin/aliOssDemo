package com.jlin.aliossdemo.utils.http;

import com.jlin.aliossdemo.AliOssResponse;
import com.jlin.aliossdemo.CommonBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HTTP;

/**
 * @author JLin
 * @date 2019/11/9
 * @describe http接口
 */
public interface HttpApi {
    /* 上传称重数据 */
    @HTTP(method = "POST", path = "http://sts.ck9696.com/oss/ckOSSServer/getCkOSSToken", hasBody = true)
    Call<AliOssResponse> getAliOssToken(@Body CommonBean commonBean);
}
