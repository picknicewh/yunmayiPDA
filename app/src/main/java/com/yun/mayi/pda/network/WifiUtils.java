package com.yun.mayi.pda.network;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

import com.yun.mayi.pda.utils.G;

import java.util.ArrayList;
import java.util.List;

public class WifiUtils {
    private WifiManager localWifiManager;//提供Wifi管理的各种主要API，主要包含wifi的扫描、建立连接、配置信息等
    //private List<ScanResult> wifiScanList;//ScanResult用来描述已经检测出的接入点，包括接入的地址、名称、身份认证、频率、信号强度等
    private List<WifiConfiguration> wifiConfigList;//WIFIConfiguration描述WIFI的链接信息，包括SSID、SSID隐藏、password等的设置
    private WifiInfo wifiConnectedInfo;//已经建立好网络链接的信息
    private WifiLock wifiLock;//手机锁屏后，阻止WIFI也进入睡眠状态及WIFI的关闭

    public WifiUtils(Context context) {
        localWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    //检查WIFI状态
    public int WifiCheckState() {
        return localWifiManager.getWifiState();
    }

    public boolean isConnect() {
        int state = WifiCheckState();
        boolean isConnect = false;
        if (state == WifiManager.WIFI_STATE_ENABLED) {
            isConnect = true;
        }
        return isConnect;
    }

    //开启WIFI
    public void WifiOpen() {
        if (!localWifiManager.isWifiEnabled()) {
            localWifiManager.setWifiEnabled(true);
        }
    }

    //关闭WIFI
    public void WifiClose() {
        if (localWifiManager.isWifiEnabled()) {
            localWifiManager.setWifiEnabled(false);
        }
    }

    //扫描wifi
    public void WifiStartScan() {
        localWifiManager.startScan();
    }

    //得到Scan结果
    public List<ScanResult> getScanResults() {
        List<ScanResult> scanResultList = new ArrayList<>();
        List<ScanResult> scanResults = localWifiManager.getScanResults();
        if (scanResults != null && scanResults.size() > 0) {
            List<String> wifiList = new ArrayList<>();
            for (int i = 0; i < scanResults.size(); i++) {
                ScanResult strScan = scanResults.get(i);
                String str = strScan.SSID;
                if (!wifiList.contains(str) && !G.isEmteny(str)) {
                    wifiList.add(str);
                    scanResultList.add(strScan);
                }
            }
            wifiList.clear();
        }
        return scanResultList;//得到扫描结果
    }

    public int getCurrentPosition(String wifiName) {
        int position = 0;
        for (int i = 0; i < getScanResults().size(); i++) {
            ScanResult strScan = getScanResults().get(i);
            if (wifiName.equals(strScan.SSID)) {
                position = i;
            }
        }
        return position;
    }

    //Scan结果转为Sting
    public List<String> scanResultToString(List<ScanResult> list) {
        List<String> strReturnList = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            ScanResult strScan = list.get(i);
            String str = strScan.toString();
            boolean bool = strReturnList.add(str);
            if (!bool) {
                Log.i("scanResultToSting", "Addfail");
            }
        }
        return strReturnList;
    }

    //得到Wifi配置好的信息
    public List<WifiConfiguration> getConfiguration() {
        wifiConfigList = localWifiManager.getConfiguredNetworks();//得到配置好的网络信息
        return wifiConfigList;
    }

    //得到Wifi配置好的信息
    public List<String> getConfigItem() {
        List<String> itemList = new ArrayList<>();
        wifiConfigList = localWifiManager.getConfiguredNetworks();//得到配置好的网络信息
        if (wifiConfigList!=null&&wifiConfigList.size()>0){
            for (int i = 0; i < wifiConfigList.size(); i++) {
                String itemId =wifiConfigList.get(i).SSID;
                String item = itemId.substring(1,itemId.length()-1);
                itemList.add(item);

            }
        }
        return itemList;
    }
    public String getConnectWifi() {
       wifiConfigList =  getConfiguration();
        String connectWifi = "";
        if (wifiConfigList!=null&&wifiConfigList.size() > 0) {
            connectWifi = wifiConfigList.get(0).SSID;
        }
        return connectWifi;
    }

    //判定指定WIFI是否已经配置好,依据WIFI的地址BSSID,返回NetId
    public int IsConfiguration(String SSID) {
        Log.i("IsConfiguration", String.valueOf(wifiConfigList.size()));
        for (int i = 0; i < wifiConfigList.size(); i++) {
            Log.i(wifiConfigList.get(i).SSID, String.valueOf(wifiConfigList.get(i).networkId));
            if (wifiConfigList.get(i).SSID.equals(SSID)) {//地址相同
                return wifiConfigList.get(i).networkId;
            }
        }
        return -1;
    }

    //添加指定WIFI的配置信息,原列表不存在此SSID
    public int AddWifiConfig(List<ScanResult> wifiList, String ssid, String pwd) {
        int wifiId = -1;
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult wifi = wifiList.get(i);
            if (wifi.SSID.equals(ssid)) {
                Log.i("AddWifiConfig", "equals");
                WifiConfiguration wifiCong = new WifiConfiguration();
                wifiCong.SSID = "\"" + wifi.SSID + "\"";//\"转义字符，代表"
                wifiCong.preSharedKey = "\"" + pwd + "\"";//WPA-PSK密码
                wifiCong.hiddenSSID = false;
                wifiCong.status = WifiConfiguration.Status.ENABLED;
                wifiId = localWifiManager.addNetwork(wifiCong);//将配置好的特定WIFI密码信息添加,添加完成后默认是不激活状态，成功返回ID，否则为-1
                if (wifiId != -1) {
                    return wifiId;
                }
            }
        }
        return wifiId;
    }

    /**
     * 忘记某一个wifi密码
     *
     * @param targetSsid
     */
    public void removeWifiBySsid(String targetSsid) {
        targetSsid = "\""+targetSsid+"\"";
        List<WifiConfiguration> wifiConfigs = localWifiManager.getConfiguredNetworks();
        for (WifiConfiguration wifiConfig : wifiConfigs) {
            String ssid = wifiConfig.SSID;
            G.log("zxxxxxx"+"removeWifiBySsid ssid="+ssid+"----"+targetSsid+"-----" );
            if (ssid.equals(targetSsid)) {
               G.log("zxxxxx"+"removeWifiBySsid success, SSID = " + wifiConfig.SSID + " netId = " + String.valueOf(wifiConfig.networkId));
                localWifiManager.removeNetwork(wifiConfig.networkId);
                localWifiManager.saveConfiguration();
            }
        }
    }

    public boolean isWifiEnabled() {
        return localWifiManager.isWifiEnabled();
    }

    //连接指定Id的WIFI
    public boolean ConnectWifi(int wifiId) {
        for (int i = 0; i < wifiConfigList.size(); i++) {
            WifiConfiguration wifi = wifiConfigList.get(i);
            if (wifi.networkId == wifiId) {
                while (!(localWifiManager.enableNetwork(wifiId, true))) {//激活该Id，建立连接
                    Log.i("ConnectWifi", String.valueOf(wifiConfigList.get(wifiId).status));//status:0--已经连接，1--不可连接，2--可以连接
                }
                return true;
            }
        }
        return false;
    }

    //创建一个WIFILock
    public void createWifiLock(String lockName) {
        wifiLock = localWifiManager.createWifiLock(lockName);
    }

    //锁定wifilock
    public void acquireWifiLock() {
        wifiLock.acquire();
    }

    //解锁WIFI
    public void releaseWifiLock() {
        if (wifiLock.isHeld()) {//判定是否锁定
            wifiLock.release();
        }
    }

    //得到建立连接的信息
    public void getConnectedInfo() {
        wifiConnectedInfo = localWifiManager.getConnectionInfo();
    }

    //得到连接的MAC地址
    public String getConnectedMacAddr() {
        return (wifiConnectedInfo == null) ? "NULL" : wifiConnectedInfo.getMacAddress();
    }

    //得到连接的名称SSID
    public String getConnectedSSID() {
        return (wifiConnectedInfo == null) ? "NULL" : wifiConnectedInfo.getSSID();
    }

    //得到连接的IP地址
    public int getConnectedIPAddr() {
        return (wifiConnectedInfo == null) ? 0 : wifiConnectedInfo.getIpAddress();
    }

    //得到连接的ID
    public int getConnectedID() {
        return (wifiConnectedInfo == null) ? 0 : wifiConnectedInfo.getNetworkId();
    }
}
