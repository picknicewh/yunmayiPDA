package com.yun.mayi.pda.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yun.mayi.pda.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2017/12/28
 * 名称：输入wifi密码对话框
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WiFiPasswordDialog extends AlertDialog {
    /**
     * wifi的名称
     */
    @BindView(R.id.tv_wifi_name)
    TextView tvWifiName;
    /**
     * 输入的wifi密码
     */
    @BindView(R.id.et_password)
    ClearEditText etPassword;
    /**
     * 回调监听
     */
    private onPassWordCatchListener onPassWordCatchListener;

    public WiFiPasswordDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wifi_password);
        ButterKnife.bind(this);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    public void setWifiName(String wifiName) {
        tvWifiName.setText(wifiName);
    }

    @OnClick({R.id.tv_cancel, R.id.tv_conform})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                etPassword.setText("");
                break;
            case R.id.tv_conform:
                if (onPassWordCatchListener != null) {
                    String password = etPassword.getText().toString();
                    onPassWordCatchListener.onCatch(password);
                }
                break;
        }
        dismiss();
    }


    public void setOnPassWordCatchListener(onPassWordCatchListener onPassWordCatchListener) {
        this.onPassWordCatchListener = onPassWordCatchListener;
    }

    public interface onPassWordCatchListener {
        void onCatch(String password);
    }
}
