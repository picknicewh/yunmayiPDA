package com.yun.mayi.pda.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.yun.mayi.pda.application.YunmayiApplication;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

/**
 * 作者： wh
 * 时间：  2018/1/11
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class BluetoothService {
    private BluetoothAdapter mBluetoothAdapter;
    private Context context = null;
    private String deviceAddress = null;
    /**
     * 当前打印机设备
     */
    private BluetoothDevice device = null;
    /**
     * 蓝牙连接服务
     */
    private BluetoothSocket bluetoothSocket = null;
    /**
     * 输出流
     */
    private OutputStream outputStream = null;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private boolean isConnection = false;

    public BluetoothService() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothAdapter getmBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    /**
     * 打开蓝牙
     */
    public void openBluetooth(Activity activity) {
        Intent enableBtIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, 1);

    }


    /**
     * 关闭蓝牙
     */
    public void closeBluetooth() {
        this.mBluetoothAdapter.disable();
    }

    /**
     * 判断蓝牙是否打开
     *
     * @return boolean
     */
    public boolean isOpen() {
        return this.mBluetoothAdapter.isEnabled();

    }

    /**
     * 搜索蓝牙设备
     * 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
     */
    public void searchDevices() {
        mBluetoothAdapter.startDiscovery();
    }

    /**
     * 连接蓝牙设备
     */
    public boolean connect() {
        if (!this.isConnection) {
            try {
                bluetoothSocket = YunmayiApplication.bluetoothSocket;
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                this.isConnection = true;
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.isDiscovering();
                }
            } catch (Exception e) {
                Toast.makeText(this.context, "连接失败！", 1).show();
                return false;
            }
            Toast.makeText(this.context, this.device.getName() + "连接成功！",
                    Toast.LENGTH_SHORT).show();

            return true;
        } else {
            return true;
        }
    }

    /**
     * 断开蓝牙设备连接
     */
    public void disconnect() {
        System.out.println("断开蓝牙设备连接");
        try {
            bluetoothSocket.close();
            outputStream.close();
        } catch (IOException e) {

        }
    }

}
