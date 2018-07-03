package com.yun.mayi.pda.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yun.mayi.pda.application.YunmayiApplication;

import java.io.IOException;

/**
 * 作者： wh
 * 时间：  2018/1/12
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class MyBluetoothReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        G.log("xxxxxxxx：MyBluetoothReceiver" + "扫描");
        switch (action) {
            case BluetoothDevice.ACTION_FOUND:
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    BluetoothSocket bluetoothSocket = null;
                    try {
                        bluetoothSocket = device.createRfcommSocketToServiceRecord(Constants.uuid);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   if (YunmayiApplication.bluetoothDevice==null){
                       YunmayiApplication.setBluetoothDevice(device);
                       YunmayiApplication.setBluetoothSocket(bluetoothSocket);
                   }
                }
                break;
        }
    }
}
