package com.jlin.aliossdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author JLin
 * @date 2019/12/30
 * @describe 文件工具类
 */
public class FileUtils {
    /**
     * 生成照片的名字
     *
     * @return name
     */
    @SuppressLint("SimpleDateFormat")
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + format.format(date);
    }

    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 用來保存登入的信息的身份选择
     */
    public static boolean Createfile_idtitly(Context context, String identity) {
        try {
            // 通过创建文件输出流创建一个文件，保存你登入时候的值
            FileOutputStream fileOutputStream = context.openFileOutput("identity.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(identity.getBytes());
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 用來保存登入的信息的（在登入点击了保存密码）
     */
    public static boolean Createfile(Context context, String msg) {
        try {
            // 通过创建文件输出流创建一个文件，保存你登入时候的值
            FileOutputStream fileOutputStream = context.openFileOutput("cuenko.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(msg.getBytes());
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 在确保第一次保存了登入的信息，在第二次就把file中的值付给EditText
     */
    public static String Assignment(Context context) {
        String msg = null;
        try {
            // 使用它接收里面的值
            // 文件输入流接收读取context的openFillUnput("打开文件")
            FileInputStream fis = context.openFileInput("cuenko.txt");
            // 文件的读取
            InputStreamReader isr = new InputStreamReader(fis);
            // 读取文件总的缓冲的内容；
            BufferedReader br = new BufferedReader(isr);
            // 得到第一行的数据
            msg = br.readLine();
            fis.close();
            isr.close();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * @param mBitmap bitmap图片
     * @param bitName 图片名称
     */
    public static String saveMyBitmap(Bitmap mBitmap, String bitName) {
        File destDir = new File(Environment.getExternalStorageDirectory() + "/images/");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        try {
            File f = new File(Environment.getExternalStorageDirectory() + "/images/", bitName + ".jpg");
            if (f.exists()) {
                f.delete();
            }
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int options = 100;
            mBitmap.compress(Bitmap.CompressFormat.JPEG, options, os);
            FileOutputStream out = new FileOutputStream(f);
            out.write(os.toByteArray());
            out.flush();
            out.close();
            return Environment.getExternalStorageDirectory() + "/images/" + bitName + ".jpg";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
