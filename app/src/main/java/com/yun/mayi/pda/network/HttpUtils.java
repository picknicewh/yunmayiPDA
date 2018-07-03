package com.yun.mayi.pda.network;

import android.util.Log;

import com.yun.mayi.pda.network.downLoad.ProgressInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者： wh
 * 时间：  2017/12/22
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HttpUtils {
    /**
     * 超时时间
     */
    public final static int DEFAULT_TIME = 30;
    /**
     * 网络访问
     */
    public static OkHttpClient mOkHttpClient;

    public static ApiServer getManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfigManager.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient()).build();
        return retrofit.create(ApiServer.class);
    }

    private static OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("zcb", "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .build();
        return mOkHttpClient;
    }

    /**
     * 获取下载
     */
    public static ApiServer getDownloadManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConfigManager.baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(getDownLoadOkHttpClient()).build();
        return retrofit.create(ApiServer.class);
    }
    /**
     * 获取下载的okhttp
     */
    private static OkHttpClient getDownLoadOkHttpClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor())
                .connectTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIME, TimeUnit.SECONDS)
                .build();
        return client;
    }

}

