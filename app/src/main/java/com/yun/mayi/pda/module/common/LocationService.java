package com.yun.mayi.pda.module.common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.network.BaseSubscriber;
import com.yun.mayi.pda.network.HttpUtils;
import com.yun.mayi.pda.network.RequestCallback;
import com.yun.mayi.pda.network.RequestParameter;
import com.yun.mayi.pda.network.Result;
import com.yun.mayi.pda.utils.G;

import java.util.Timer;
import java.util.TimerTask;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者： wh
 * 时间：  2018/6/5
 * 名称：定位后台服务
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LocationService extends Service implements AMapLocationListener {
    /**
     * 声明mlocationClient对象
     */
    public AMapLocationClient mlocationClient;
    /**
     * 声明mLocationOption对象
     */
    public AMapLocationClientOption mLocationOption = null;
    /**
     * 纬度
     */
    private double mLongitude;
    /**
     * 经度
     */
    private double mLatitude;
    /**
     * 定时器
     */
    private Timer timer;


    @Override
    public void onCreate() {
        super.onCreate();
        initLocation();
        mlocationClient.startLocation();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                addressRefresh();
         //       G.log("fdadaaa" + "经度：" + mLatitude + "纬度：" + mLongitude);
            }
        }, 0, 1000 * 60 * 3);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 初始化定位参数
     */
    private void initLocation() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mlocationClient = new AMapLocationClient(YunmayiApplication.getInstance().getApplicationContext());
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.setLocationListener(this);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                mLatitude = amapLocation.getLatitude();//获取经度
                mLongitude = amapLocation.getLongitude();//获取纬度
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                G.log("LocationService"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    public void addressRefresh() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("latitude", mLatitude);
        parameter.setParam("longitude", mLongitude);
        parameter.setParam("token", UserMessage.getInstance().getToken());
        HttpUtils.getManager()
                .addressRefresh(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<String>>() {
                    @Override
                    public void beforeRequest() {
                    }

                    @Override
                    public void requestError(String msg) {
                        G.log("LocationService" + msg);
                    }

                    @Override
                    public void requestComplete() {
                    }

                    @Override
                    public void requestSuccess(Result<String> data) {
                        if (data != null) {
                            G.log("LocationService" + data.getData());
                        }
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mlocationClient.stopLocation();
        mlocationClient.onDestroy();
        timer.cancel();
    }
}
