package com.jlin.aliossdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ScheduledExecutorService aliOssService;
    private String picPath;

    private static final int REQUEST_TAKE_PHOTO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvUpdate = findViewById(R.id.tv_update);
        tvUpdate.setOnClickListener(v -> {
            picPath = Environment.getExternalStorageDirectory().getPath() + File.separator + FileUtils.getPhotoFileName() + ".jpg";
            //Android7.0文件保存方式改变了
            Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT < 24) {
                Uri picUri = Uri.fromFile(new File(picPath));
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);//将原图的uri传入
                startActivityForResult(openCameraIntent, REQUEST_TAKE_PHOTO);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, picPath);
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(openCameraIntent, REQUEST_TAKE_PHOTO);
            }
        });


        AliOssUtils.getInstance().init(getApplicationContext(), new CommonCallBack() {
            @Override
            public void success() {

                if (aliOssService == null) {
                    aliOssService = new ScheduledThreadPoolExecutor(1);
                }
                aliOssService.scheduleAtFixedRate(() -> {
                    AliOssUtils.getInstance().init(getApplicationContext());
                }, 50, 50, TimeUnit.MINUTES);
            }

            @Override
            public void fail(String message) {
                Toast.makeText(getApplicationContext(), "初始化失败: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bitmap = BitmapFactory.decodeFile(picPath);
                Bitmap compressBitmap = ThumbnailUtils.extractThumbnail(bitmap, 600, 800);
                String imgAddress = FileUtils.saveMyBitmap(compressBitmap, getTime());
                AliOssUtils.getInstance().updateFile(imgAddress, new AliOssUtils.UploadListener() {
                    @Override
                    public void success(List<String> urls) {
                        Log.d(TAG, "----> aliOss 地址: " + urls.get(0));
                    }

                    @Override
                    public void fail(int index) {

                    }
                });
                FileUtils.deleteFile(picPath);
            } else if (resultCode == RESULT_CANCELED) {
                FileUtils.deleteFile(picPath);
            }
        }
    }

    /**
     * 得到当时时间年月日
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(new Date());
    }


}
