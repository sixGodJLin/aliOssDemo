package com.jlin.aliossdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jlin.aliossdemo.utils.http.RetrofitManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author JLin.
 * @date 2019/11/20
 * @describe aliOssUtils 阿里云OSS工具类
 */
public class AliOssUtils {
    private static final String TAG = "AliOssUtils";

    private static String endPoint = "oss-cn-hangzhou.aliyuncs.com";
    private static String startPoint = "cunkou-collection";

    private static String aliOss = "https://" + startPoint + "." + endPoint + "/";

    private static AliOssUtils mInstance = null;

    private static OSSCredentialProvider credentialProvider;
    private static OSS oss;

    public static AliOssUtils getInstance() {
        Log.d(TAG, "getInstance: ");
        synchronized (AliOssUtils.class) {
            if (mInstance == null) {
                mInstance = new AliOssUtils();
            }
        }
        return mInstance;
    }

    public void init(Context context, CommonCallBack commonCallBack) {
        Log.d(TAG, "init commonCallBack: 阿里云oss初始化 -->");
        CommonBean commonBean = new CommonBean();
        commonBean.setExchangeTime(getNowTime());
        commonBean.setChannelId("CKDS_ANDROID");
        commonBean.setChannelNo("CKDS");
        commonBean.setChannelPassword("CKDS");
        commonBean.setAppVersion(BuildConfig.VERSION_NAME);
        commonBean.setAppMobileModel(Build.MODEL);

        RetrofitManager.getInstance().getHttpApi().getAliOssToken(commonBean).enqueue(new Callback<AliOssResponse>() {
            @Override
            public void onResponse(Call<AliOssResponse> call, Response<AliOssResponse> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().getCode() == 0) {
                            String assessKeyId = response.body().getData().getAccessKeyId();
                            String accessKeySecret = response.body().getData().getAccessKeySecret();
                            String securityToken = response.body().getData().getSecurityToken();
                            credentialProvider = new OSSStsTokenCredentialProvider(assessKeyId, accessKeySecret, securityToken);

                            new Thread(() -> {
                                oss = new OSSClient(context, endPoint, credentialProvider);
                                Log.d(TAG, "<-- init commonCallBack: 阿里云oss初始化 end" + oss);
                                commonCallBack.success();
                            }).start();
                        } else {
                            String responseMsg = response.body().getMessage();
                            Log.e(TAG, "<-- init commonCallBack: " + responseMsg);
                            commonCallBack.fail(responseMsg);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "init error: " + e.getMessage());
                    commonCallBack.fail(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AliOssResponse> call, Throwable t) {
                Log.e(TAG, "init onFailure: " + t.getMessage());
            }
        });
    }

    public void init(Context context) {
        Log.d(TAG, "init: 阿里云oss初始化 -->");
        CommonBean commonBean = new CommonBean();
        commonBean.setExchangeTime(getNowTime());
        commonBean.setChannelId("CKDS_ANDROID");//
        commonBean.setChannelNo("CKDS");//
        commonBean.setChannelPassword("CKDS");
        commonBean.setAppVersion(BuildConfig.VERSION_NAME);
        commonBean.setAppMobileModel(Build.MODEL);

        RetrofitManager.getInstance().getHttpApi().getAliOssToken(commonBean).enqueue(new Callback<AliOssResponse>() {
            @Override
            public void onResponse(Call<AliOssResponse> call, Response<AliOssResponse> response) {
                try {
                    if (response.body() != null) {
                        if (response.body().getCode() == 0) {
                            String assessKeyId = response.body().getData().getAccessKeyId();
                            String accessKeySecret = response.body().getData().getAccessKeySecret();
                            String securityToken = response.body().getData().getSecurityToken();
                            credentialProvider = new OSSStsTokenCredentialProvider(assessKeyId, accessKeySecret, securityToken);

                            new Thread(() -> {
                                oss = new OSSClient(context, endPoint, credentialProvider);
                                Log.d(TAG, "<-- init: 阿里云oss初始化 end" + oss);
                            }).start();
                        } else {
                            String responseMsg = response.body().getMessage();
                            Log.e(TAG, "<-- init: " + responseMsg);
                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "init error: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AliOssResponse> call, Throwable t) {
                Log.e(TAG, "init onFailure: " + t.getMessage());
            }
        });
    }

    public void updateFile(String fileName, UploadListener listener) {
        List<String> urls = new ArrayList<>();
        new Thread(() -> {
            String url1 = getObjectName();
            PutObjectRequest put = new PutObjectRequest(startPoint, getObjectName(), fileName);
            // 异步上传时可以设置进度回调
            put.setProgressCallback((request, currentSize, totalSize) -> Log.d(TAG, "采集图片上传中"));
            oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    Log.d(TAG, "第一张图片上传成功!");
                    urls.add(aliOss + url1);
                    listener.success(urls);
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    listener.fail(1);
                }
            });
        }).start();
    }

    public void updateFiles(List<String> files, UploadListener listener) {
        List<String> urls = new ArrayList<>();
        new Thread(() -> {
            String url1 = getObjectName();
            PutObjectRequest put = new PutObjectRequest(startPoint, getObjectName(), files.get(0));
            // 异步上传时可以设置进度回调
            put.setProgressCallback((request, currentSize, totalSize) -> Log.d(TAG, "上传第一张图片上传中"));
            oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    Log.d(TAG, "第一张图片上传成功!");
                    urls.add(aliOss + url1);

                    String url2 = getObjectName();
                    PutObjectRequest put2 = new PutObjectRequest(startPoint, url2, files.get(1));
                    // 异步上传时可以设置进度回调
                    put2.setProgressCallback((request2, currentSize2, totalSize2) -> Log.d(TAG, "上传第二张图片上传中"));
                    oss.asyncPutObject(put2, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                        @Override
                        public void onSuccess(PutObjectRequest request2, PutObjectResult result2) {
                            Log.d(TAG, "第二张图片上传成功!");
                            urls.add(aliOss + url2);
                            listener.success(urls);
                        }

                        @Override
                        public void onFailure(PutObjectRequest request2, ClientException clientException2, ServiceException serviceException2) {
                            if (clientException2 != null) {
                                clientException2.printStackTrace();
                                Log.e(TAG, "第二张图片上传失败!" + clientException2.toString());
                            }
                            if (serviceException2 != null) {
                                serviceException2.printStackTrace();
                                Log.e(TAG, "第二张图片上传失败!" + serviceException2.toString());
                            }
                        }
                    });
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                    if (clientException != null) {
                        clientException.printStackTrace();
                        Log.e(TAG, "第一张图片上传失败!" + clientException.toString());
                    }
                    if (serviceException != null) {
                        serviceException.printStackTrace();
                        Log.e(TAG, "第一张图片上传失败!" + serviceException.toString());
                    }
                    listener.fail(1);
                }
            });
        }).start();
    }

    @SuppressLint("SimpleDateFormat")
    private String getObjectName() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssS");
        String dateString = formatter.format(currentTime);
        return "collection/" + dateString + ".png";
    }

    @SuppressLint("SimpleDateFormat")
    public static String getNowTime() {
        SimpleDateFormat sDateFormat_Complete = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat_Complete.format(new Date());
    }

    public interface UploadListener {
        /**
         * 上传图片成功
         *
         * @param urls aliOss urls
         */
        void success(List<String> urls);

        /**
         * 上传图片失败
         *
         * @param index 失败的节点
         */
        void fail(int index);
    }
}
