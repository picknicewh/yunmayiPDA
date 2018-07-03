package com.yun.mayi.pda.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yun.mayi.pda.module.login.LoginActivity;

/**
 * 作者： wh
 * 时间：  2017/12/28
 * 名称：开机自启动广播
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ConnectBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent bootActivityIntent=new Intent(context,LoginActivity.class);
            bootActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(bootActivityIntent);//要启动应用程序的登录界面
        }
    }
}
