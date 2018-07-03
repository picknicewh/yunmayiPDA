package com.yun.mayi.pda.application;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.yun.mayi.pda.di.component.DaggerAppComponent;
import com.yun.mayi.pda.di.module.AppModule;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.PrintUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * Created by ys on 2017/5/31.
 */

public class YunmayiApplication extends Application {





    public static Map<String, Activity> destoryMap = new HashMap<>();
    /***保存一些 activity*/
    private static YunmayiApplication INSTANCE;
    /**
     * actvity集合
     */
    private static LinkedList<Activity> activityLinkedList;
    /**
     * 不管是蓝牙连接方还是服务器方，得到socket对象后都传入
     */
    public static BluetoothSocket bluetoothSocket;

    public static OutputStream outputStream;
    /**
     * 每个页面修改的起始日期列表
     */
    public static Map<String, Date> startDateList = new HashMap<>();
    /**
     * 每个页面修改的结束日期列表
     */
    public static Map<String, Date> endDateList = new HashMap<>();

    public static YunmayiApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        initInject();

        activityLinkedList = new LinkedList<>();
        registerActivityStaus();
        BluetoothAdapter.getDefaultAdapter().startDiscovery();
    }

    /**
     * 设置当前页面修改的起始日期
     *
     * @param keyword 当前页面的名称
     * @param date    修改的日期
     */
    public static void putStartDate(String keyword, Date date) {
        startDateList.put(keyword, date);
    }
    /**
     * 根据当前页面名称获取当前页面修改起始的日期
     * @param keyword 当前页面的名称
     */
    public static Date getStartDate(String keyword) {
        return startDateList.get(keyword);
    }

    /**
     * 设置当前页面修改的结束日期
     *
     * @param keyword 当前页面的名称
     * @param date    修改的日期
     */
    public static void putEndDate(String keyword, Date date) {
        endDateList.put(keyword, date);
    }
    /**
     * 根据当前页面名称获取当前页面修改结束的日期
     * @param keyword 当前页面的名称
     */
    public static Date getEndDate(String keyword) {
        return endDateList.get(keyword);
    }


    // 获取ApplicationContext
    public static Context getContext() {
        return INSTANCE;
    }

    /**
     * 添加到销毁队列
     *
     * @param activity 要销毁的activity
     */
    public static void addDestoryActivity(Activity activity, String activityName) {
        destoryMap.put(activityName, activity);
    }

    /**
     * 连接蓝牙设备
     */
    public static BluetoothSocket getBluetoothSocket() {
        if (bluetoothSocket == null) {//如果等于空的话，继续扫描
            BluetoothAdapter.getDefaultAdapter().startDiscovery();
        }
        return bluetoothSocket;
    }

    /**
     * 设置蓝牙设备
     */
    public static void setBluetoothSocket(BluetoothSocket bluetoothSocket) {
        YunmayiApplication.bluetoothSocket = bluetoothSocket;
        if (bluetoothSocket != null) {
            G.log("xxxxxxxx：setBluetoothSocket" + "找到设备");
        } else {
            BluetoothAdapter.getDefaultAdapter().startDiscovery();
        }
    }

    public static BluetoothDevice bluetoothDevice;

    /**
     * 设置蓝牙设备
     */
    public static void setBluetoothDevice(BluetoothDevice bluetoothDevice) {
        YunmayiApplication.bluetoothDevice = bluetoothDevice;
        if (bluetoothDevice != null) {
            G.log("xxxxxxxx：setBluetoothDevice" + "找到设备");
        }
    }

    public static void setOutputStream(OutputStream outputStream) {
        YunmayiApplication.outputStream = outputStream;
    }


    /**
     * 销毁指定Activity
     */
    public static void destoryActivity(String activityName) {
        Set<String> keySet = destoryMap.keySet();
        for (String key : keySet) {
            if (key.equals(activityName)) {
                destoryMap.get(key).finish();
            }
        }
    }

    /**
     * 注册页面状态
     */
    private void registerActivityStaus() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.d(TAG, "onActivityCreated: " + activity.getLocalClassName());
                activityLinkedList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.d(TAG, "onActivityStarted: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.d(TAG, "onActivityResumed: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.d(TAG, "onActivityPaused: " + activity.getLocalClassName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.d(TAG, "onActivityStopped: " + activity.getLocalClassName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.d(TAG, "onActivityDestroyed: " + activity.getLocalClassName());
                activityLinkedList.remove(activity);
            }
        });
    }

    public static void disconnect() {
        G.log("xxxxxx" + "修改修改。。。断开连接22222");
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket.close();
                bluetoothSocket = null;
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(Constants.uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (outputStream != null) {
            try {
                outputStream.close();
                outputStream = null;
                G.log("xxxxxx" + "断开连接");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean connect() {
        boolean isConnect;
        if (bluetoothSocket != null) {
            G.log("xxxxxx" + "bluetoothSocket不为空");
            try {
                bluetoothSocket.connect();
                G.log("xxxxxx" + "连接设备");
                outputStream = bluetoothSocket.getOutputStream();
                PrintUtils.setOutputStream(outputStream);
                isConnect = true;
            } catch (IOException e) {
                G.log("xxxxxx" + "异常" + e.getMessage());
                e.printStackTrace();
                isConnect = false;
            }
        } else {
            isConnect = false;
            G.showToast(getContext(), "请连接蓝牙打印机！");
        }
        return isConnect;
    }

    /**
     * 退出app
     */
    public static void exitApp() {
        for (Activity activity : activityLinkedList) {
            activity.finish();
        }
        startDateList.clear();
        endDateList.clear();
    }

    private static DaggerAppComponent mDaggerAppComponent;

    private void initInject() {
        mDaggerAppComponent = (DaggerAppComponent) DaggerAppComponent.builder().
                appModule(new AppModule(this)).build();
    }

    public static DaggerAppComponent getDaggerAppComponent() {
        return mDaggerAppComponent;
    }

}
