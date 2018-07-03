package com.yun.mayi.pda.module.home.setting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseActivity;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.network.WifiUtils;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.widget.WIFiDetailDialog;
import com.yun.mayi.pda.widget.WiFiPasswordDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingWifiActivity extends BaseActivity implements WIFiDetailDialog.OnRemoveCallBack, WiFiPasswordDialog.onPassWordCatchListener, OnItemClickListener, CompoundButton.OnCheckedChangeListener {
    private static final String ARG_PARAM1 = "param1";
    /**
     * 是否连接wifi
     */
    @BindView(R.id.tg_switch)
    ToggleButton tgSwitch;
    /**
     * wifi列表
     */
    @BindView(R.id.rv_wifi)
    RecyclerView rvWifi;

    private String mParam1;

    /**
     * wifi列表适配器
     */
    private WifiListAdapter adapter;
    /**
     * wifi数据列表
     */
    private List<ScanResult> wifiList;
    private List<String> configList;
    /**
     * 监听wifi是否连接
     */
    private NetworkConnectChangedReceiver receiver;

    /**
     * 连接WiFi输入密码对话框
     */
    private WiFiPasswordDialog dialog;
    /**
     * 已连接wfi的位置
     */
    private int position = 0;

    /**
     * wifi工具类
     */
    private WifiUtils wifiUtils;
    /**
     * =当前连接的id
     */
    private String currentssid;
    /**
     * 当前连接结果
     */
    private ScanResult mScanResult;
    /**
     * 连接成功位置
     */
    private int connectPosition;
    private WIFiDetailDialog wiFiDetailDialog;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_setting_wifi;
    }

    @Override
    public void initView() {
        setTitleTextId(R.string.setting_wifi);
        setLeftIcon(R.mipmap.ic_back);
        wiFiDetailDialog = new WIFiDetailDialog(this);
        wiFiDetailDialog.setOnRemoveCallBack(this);
        dialog = new WiFiPasswordDialog(this);
        dialog.setOnPassWordCatchListener(this);
        wifiUtils = new WifiUtils(this);
        tgSwitch.setChecked(wifiUtils.isWifiEnabled());
        tgSwitch.setOnCheckedChangeListener(this);
        rvWifi.setVisibility(wifiUtils.isWifiEnabled() ? View.VISIBLE : View.GONE);
        wifiList = new ArrayList<>();
        configList = new ArrayList<>();
        wifiList = wifiUtils.getScanResults();
        configList = wifiUtils.getConfigItem();
        adapter = new WifiListAdapter(wifiList,configList);
        rvWifi.setLayoutManager(new LinearLayoutManager(this));
        rvWifi.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        initFilter();
        wifiUtils.getConfiguration();
        String connectWifiName = wifiUtils.getConnectWifi();
        connectPosition = wifiUtils.getCurrentPosition(connectWifiName);
    }

    /**
     * 注册广播
     */
    private void initFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        receiver = new NetworkConnectChangedReceiver();
       registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onRemoveSuccess() {
        connectPosition =-1;
        wifiList.clear();
        configList.clear();
        wifiList.addAll(wifiUtils.getScanResults());
        configList.addAll(wifiUtils.getConfigItem());
        if (adapter!=null){
            adapter.initData(connectPosition);
        }
    }

    @Override
    public void onCatch(String password) {
        int wifiItemId = wifiUtils.IsConfiguration("\"" + currentssid + "\"");
        if (wifiItemId != -1) {
            wifiUtils.ConnectWifi(wifiItemId);
            G.log("aaaa" + "连接wifi" + currentssid + "成功！");
        } else if (!G.isEmteny(password)) {
            int netId = wifiUtils.AddWifiConfig(wifiList, currentssid, password);
            G.log("aaaa"+String.valueOf(netId));
            if (netId != -1) {
                wifiUtils.getConfiguration();//添加了配置信息，要重新得到配置信息
                wifiUtils.ConnectWifi(netId);
                connectPosition =position;
                G.log("aaaa" + "添加了配置信息后连接wifi" + currentssid + "成功！");
            } else {
                G.showToast(this, "密码错误");
            }
        }
    }

    @Override
    public void onClick(View view, int position) {
        mScanResult = wifiList.get(position);
        String wifiName = wifiList.get(position).SSID;
        currentssid = wifiName;
        this.position = position;
        if (connectPosition != position) {
            dialog.show();
            dialog.setWifiName(wifiName);
        } else {
            wiFiDetailDialog.show();
            wiFiDetailDialog.setWifiInfo(mScanResult);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        rvWifi.setVisibility(b ? View.VISIBLE : View.GONE);
        if (b) {
            wifiUtils.WifiOpen();
            wifiUtils.WifiStartScan();
        } else {
            wifiUtils.WifiClose();
        }
    }

    private class NetworkConnectChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            String connectStateInfo = "";
            G.log("action:" + action);
            switch (action) {
                case WifiManager.WIFI_STATE_CHANGED_ACTION:
                    //  connectStateInfo="正在建立连接";
                    //adapter.setWifiConnectState(connectStateInfo,position);
                    break;
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (networkInfo != null && networkInfo.getState() != null) {
                        switch (networkInfo.getState()) {
                            case CONNECTED:
                                WifiInfo wifiInfo = intent.getParcelableExtra(WifiManager.EXTRA_WIFI_INFO);
                                currentssid = wifiInfo.getSSID();
                                G.log("vvvvv" + "wifi连接成功......" + currentssid);
                                connectStateInfo = "已连接";
                                adapter.setWifiConnectState(connectStateInfo, position);
                                break;
                            case CONNECTING:
                                connectStateInfo = "连接中...";
                                G.log("vvvvv" + "wifi连接中......");
                                adapter.setWifiConnectState(connectStateInfo, position);
                                break;
                            case DISCONNECTED:
                                connectStateInfo = "已保存";
                                adapter.setWifiConnectState(connectStateInfo, position);
                                G.log("vvvvv" + "wifi断开连接......");
                                break;
                            case DISCONNECTING:
                                connectStateInfo = "断开连接中..";
                                adapter.setWifiConnectState(connectStateInfo, position);
                                G.log("vvvvv" + "wifi断开连接中......");
                                break;
                            case UNKNOWN:
                                G.log("vvvvv" + "未知......");
                                break;
                        }
                    }
                    wifiList.clear();
                    wifiList.addAll(wifiUtils.getScanResults());
                    configList.clear();
                    configList.addAll(wifiUtils.getConfigItem());
                    if (adapter != null) {
                        adapter.initData(position);
                    }
                    break;
                case WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION:
                    /**
                     * 这个应该是广播wifi启动状态的，true表示连接到wifi设备，一般在wifi state处于enable时得到
                     * false则表示断开设备连接，wifi此时状态为disabling。
                     * 这只是指机器内的wifi连接状态变化，与网络无关）
                     */
                    G.log("vvvvv" + "wifi启动状态的改变......");
                    break;
                case WifiManager.SUPPLICANT_STATE_CHANGED_ACTION:
                    /**
                     * 发送WIFI连接的过程信息，如果出错ERROR信息才会收到。连接WIFI时触发，触发多次。
                     */
                    G.log("vvvvv" + "发送WIFI连接的过程信息出错ERROR信息......");
                    break;
            }
        }

    }

}
