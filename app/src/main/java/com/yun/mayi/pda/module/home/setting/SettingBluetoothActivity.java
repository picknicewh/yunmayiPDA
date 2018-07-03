package com.yun.mayi.pda.module.home.setting;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseActivity;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.BluetoothService;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.PermissionsChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import butterknife.BindView;

public class SettingBluetoothActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener, OnItemClickListener {

    /**
     * 是否开启蓝牙
     */
    @BindView(R.id.tg_switch)
    ToggleButton tgSwitch;
    /**
     * 已配对蓝牙列表
     */
    @BindView(R.id.rv_bluetooth)
    RecyclerView rvBluetooth;
    /**
     * 可用对蓝牙列表
     */
    //   @BindView(R.id.rv_unbluetooth)
    //  RecyclerView rvUnbluetooth;
    /**
     * 配对蓝牙
     */
    @BindView(R.id.ll_bind)
    LinearLayout llBind;
    /**
     * 没有配对蓝牙
     */
    //   @BindView(R.id.ll_unbind)
    //  LinearLayout llUnbind;
    /**
     * 蓝牙工具类
     */
    private BluetoothService mBluetoothService;
    /**
     * 用于存放未配对蓝牙设备
     */
    //  private ArrayList<BluetoothDevice> unbondDevices = null;
    /**
     * 用于存放已配对蓝牙设备
     */
    private ArrayList<BluetoothDevice> bondDevices = null;
    /**
     * 适配器
     */
    private BluetoothListAdapter bondAdapter;
    /**
     * 没有配对的设备
     */
    //  private BluetoothListAdapter unbondAdapter;
    /**
     * 蓝牙打印的uuid
     */
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private  String[] PERMISSIONS = new String[]{
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
    };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_bluetooth_setting;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.bluetooth);
        tgSwitch.setOnCheckedChangeListener(this);
        mBluetoothService = new BluetoothService();
        mBluetoothService.openBluetooth(this);
        mBluetoothService.searchDevices();
        //  unbondDevices = new ArrayList<>();
        bondDevices = new ArrayList<>();
        initIntentFilter();
        tgSwitch.setChecked(mBluetoothService.isOpen());

        //已配对
        bondAdapter = new BluetoothListAdapter(bondDevices);
        rvBluetooth.setLayoutManager(new LinearLayoutManager(this));
        rvBluetooth.setAdapter(bondAdapter);
        bondAdapter.setOnItemClickListener(this);
        //未配对
        //  unbondAdapter = new BluetoothListAdapter(unbondDevices);
        //  rvUnbluetooth.setLayoutManager(new LinearLayoutManager(this));
        // rvUnbluetooth.setAdapter(unbondAdapter);
        //动态申请权限
        PermissionsChecker checker = PermissionsChecker.getInstance(this);
        if (checker.lacksPermissions(PERMISSIONS)) {
            checker.getPerMissions(PERMISSIONS);
            return;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            mBluetoothService.openBluetooth(this);
            mBluetoothService.searchDevices();
        } else {
            G.log("xxxxxx" + "修改修改。。。断开连接");
            YunmayiApplication.setBluetoothSocket(null);
            mBluetoothService.closeBluetooth();

        }
    }

    /**
     * 设置广播信息过滤
     * 注册广播接收器，接收并处理搜索结果
     */
    public void initIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view, int position) {
        String currentDeviceName = "";
        if (YunmayiApplication.bluetoothDevice != null) {
            currentDeviceName = YunmayiApplication.bluetoothDevice.getName();
            YunmayiApplication.disconnect();
            String chooseDeviceName = bondDevices.get(position).getName();
            if (!chooseDeviceName.equals(currentDeviceName)){
                connect(bondDevices.get(position));
                if (bondAdapter!=null){
                    bondAdapter.notifyDataSetChanged();
                }
              //  String message = currentDeviceName + "断开连接..." + "连接" + bondDevices.get(position).getName() + "打印机成功！";
             //   G.showToast(this, message);
            }else {
              //  G.showToast(this, "你选择的和当前设备相同哦!");
            }
        }else {
            connect(bondDevices.get(position));
            if (bondAdapter!=null){
                bondAdapter.initData(position);
            }
            G.showToast(this, "连接" + bondDevices.get(position).getName() + "打印机成功！");
        }

    }

    /**
     * 蓝牙广播接收器
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                        if (!bondDevices.contains(device)) {
                            bondDevices.add(device);
                            bondAdapter.initData(0);
                        }
                        G.log("xxdcc" + device.getName());
                      /*  if (device.getName().equals("XT423") || device.getName().equals("L31 BT Printer")) {
                            BluetoothSocket bluetoothSocket = null;
                            try {
                                bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            YunmayiApplication.setBluetoothSocket(bluetoothSocket);
                        }*/
                    }/* else {
                        if (!unbondDevices.contains(device)) {
                            unbondDevices.add(device);
                            unbondAdapter.notifyDataSetChanged();
                        }
                    }*/
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    if (mBluetoothService.getmBluetoothAdapter().getState() == BluetoothAdapter.STATE_ON) {//打开蓝牙
                        mBluetoothService.searchDevices();
                        llBind.setVisibility(View.VISIBLE);
                        //    llUnbind.setVisibility(View.VISIBLE);
                    } else if (mBluetoothService.getmBluetoothAdapter().getState() == BluetoothAdapter.STATE_OFF) {//关闭蓝牙
                        llBind.setVisibility(View.GONE);
                        bondDevices.clear();
                        if (bondAdapter!=null){
                            bondAdapter.notifyDataSetChanged();
                        }
                         /*  llUnbind.setVisibility(View.GONE);
                            unbondDevices.clear();
                           if (unbondAdapter != null) {
                            unbondAdapter.notifyDataSetChanged();
                        }*/
                    }
                    break;

            }
            //默认连接第一个
            if (bondDevices.size() > 0) {
               // connect(bondDevices.get(0));
            }

        }
    };


    private void connect(BluetoothDevice device) {
        BluetoothSocket bluetoothSocket = null;
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }
        YunmayiApplication.setBluetoothDevice(device);
        YunmayiApplication.setBluetoothSocket(bluetoothSocket);
    }
}
