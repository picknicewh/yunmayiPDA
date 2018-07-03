package com.yun.mayi.pda.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 作者： wh
 * 时间：  2018/3/13
 * 名称：下载文件路径
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class FileUtils {

    /**
     * 统一路径
     */
    public static String getFilePath() {
        return Environment.getExternalStorageDirectory() + "/pda/";
    }

    public static File createApkFile(Context context) {
        String path = "/temp.apk";
        File file = new File(getApkFileLoadPath(context) + path);
        return file;

    }

    /**
     * 统一apk下载路径
     */
    public static String getApkFileLoadPath(Context context) {
        return createFile(getCachePath(context, "/apk"));
    }

    /**
     * 获取app缓存路径
     *
     * @param context
     * @return
     */
    public static String getCachePath(Context context, String path) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = context.getExternalCacheDir().getPath() + path;
        } else {
            //外部存储不可用
            cachePath = context.getCacheDir().getPath() + path;
        }
        return cachePath;
    }

    /**
     * 统一下载路径
     */
    public static String getDownLadPath(Context context, String path) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            //外部存储可用
            cachePath = Environment.getExternalStorageDirectory() + path;
        } else {
            //外部存储不可用
            cachePath = context.getCacheDir().getPath() + path;
        }
        return cachePath;
    }

    /**
     * 判断文件夹是否存在
     */
    public static String createFile(String path) {
        File file = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }


}
