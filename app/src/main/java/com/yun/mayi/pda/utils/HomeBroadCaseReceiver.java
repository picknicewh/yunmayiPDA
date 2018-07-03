package com.yun.mayi.pda.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.widget.CheckDialog;

/**
 * 作者： wh
 * 时间：  2018/3/7
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HomeBroadCaseReceiver extends BroadcastReceiver implements CheckDialog.onPassWordCatchListener {
    /**
     * 防止意外退出
     */
    private CheckDialog checkDialog;
    public static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    public static final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
    public static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    public static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    public static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

    @Override
    public void onReceive(Context context, Intent arg1) {
        checkDialog = new CheckDialog(context);
        checkDialog.setOnPassWordCatchListener(this);
        String action = arg1.getAction();
        //按下Home键会发送ACTION_CLOSE_SYSTEM_DIALOGS的广播
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = arg1.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            if (reason != null) {
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    // 短按home键
                    Toast.makeText(context, "短按Home键", Toast.LENGTH_SHORT).show();
                    
                } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    // RECENT_APPS键
                    Toast.makeText(context, "RECENT_APPS", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onCatch(String password) {
        YunmayiApplication.exitApp();
    }
}
