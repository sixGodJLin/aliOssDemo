package com.jlin.aliossdemo;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("MainActivity:" + "onCreate" + "====" + getPackageName());

        final String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        final String startpoint = "oss-token-test";
        //     明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        final OSSCredentialProvider credentialProvider =
                new OSSStsTokenCredentialProvider("STS.NJYip3ZGFUacEWWvR3pQrby1y",
                        "Ha9qYmaJkst8BY4j1mCxPLtJFqJNMqPhwFmqhYYFBRCg",
                        "CAIS5wF1q6Ft5B2yfSjIr4nsIsqHt5hn4qOIR3HmklJmfN5ejbyamzz2IHtLfHhqAuwYs/k3nmhU6/cSlqNyTIQAWUHf" +
                                "cZMptmO4HpQHJtivgde8yJBZor/HcDHhJnyW9cvWZPqDP7G5U/yxalfCuzZuyL/hD1uLVECkNpv74vwOLK5gPG+CYCFBGc1dKyZ7" +
                                "tcYeLgGxD/u2NQPwiWeiZygB+CgS0DMis/vm+KDGtEqC1m+d4/QOuoH8LqKja8RRJ5plW7+3prctKfKQinUMtUUXrP0u0PEVqS2iucqG" +
                                "RkVc+AnDKe3Q/82w42wagAGmRtgcGJS4euwc04GePIie31y4M57jkdhVIj5UnOF40d/bpPrf2uzLfO/vZpbh+lIEJRDZjGXz6Bco7NB+PrF" +
                                "CFhBv93TzNo4+RjqSCR3Lu0qbV7LdLDZ37GW2KJHH4wEir/E7EG/iRlhe2DRXRhZiJasKGUL1qhwk/CeN5fvY3w==");
        new Thread(new Runnable() {
            @Override
            public void run() {
                OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider);
                //通过填写文件名形成objectname,通过这个名字指定上传和下载的文件
                // 构造上传请求
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssS");
                String dateString = formatter.format(currentTime);
                final String objectname = "ckgi/" + dateString + ".png";

                PutObjectRequest put = new PutObjectRequest(startpoint, objectname, Environment.getExternalStorageDirectory().getPath() + "/123456.JPG");
                // 异步上传时可以设置进度回调
                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                        System.out.println("MainActivity:" + "onProgress" + "====");
                    }
                });
                OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                        System.out.println("MainActivity:" + "onSuccess" + "====" + request.getUploadFilePath());
                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                        // 请求异常
                        if (clientExcepion != null) {
                            // 本地异常如网络异常等
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                        }
                        System.out.println("MainActivity:" + "onFailure" + "====" + serviceException.toString());
                    }
                });
            }
        }).start();
    }
}
