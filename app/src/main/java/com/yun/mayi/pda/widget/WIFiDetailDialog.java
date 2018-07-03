package com.yun.mayi.pda.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.network.WifiUtils;
import com.yun.mayi.pda.utils.G;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/1/3
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WIFiDetailDialog extends AlertDialog {
    /**
     * wifi的名字
     */
    @BindView(R.id.tv_wifi_name)
    TextView tvWifiName;
    /**
     * 安全性
     */
    @BindView(R.id.tv_safe)
    TextView tvSafe;
    /**
     * 连接状态
     */
    @BindView(R.id.tv_connect_status)
    TextView tvConnectStatus;
    /**
     * 连接强度
     */
    @BindView(R.id.tv_wifi_strong)
    TextView tvWifiStrong;
    private WifiUtils mWifiUtils;

    public WIFiDetailDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_wifi_detail);
        ButterKnife.bind(this);
        mWifiUtils = new WifiUtils(getContext());
    }
    public void setWifiInfo(ScanResult scanResult) {
        tvWifiName.setText(scanResult.SSID);
        G.log("xxxxxx" + scanResult.level);


    }

    @OnClick({R.id.tv_clean, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clean:
                String wifiName = tvWifiName.getText().toString();
                mWifiUtils.removeWifiBySsid(wifiName);
                if (onRemoveCallBack!=null){
                    onRemoveCallBack.onRemoveSuccess();
                }
                break;
            case R.id.tv_cancel:
                break;
        }
        dismiss();
    }

    public void setOnRemoveCallBack(OnRemoveCallBack onRemoveCallBack) {
        this.onRemoveCallBack = onRemoveCallBack;
    }

    private OnRemoveCallBack onRemoveCallBack;
    public interface  OnRemoveCallBack{
        void onRemoveSuccess();
    }
}
