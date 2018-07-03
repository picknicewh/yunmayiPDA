package com.yun.mayi.pda.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.utils.CustomViewGroup;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.SoundUtils;
import com.yun.mayi.pda.utils.StatusBarUtil;
import com.yun.mayi.pda.widget.LoadingDialog;
import com.zltd.decoder.Constants;
import com.zltd.industry.ScannerManager;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yun.mayi.pda.utils.Constants.ACTION_DISPLAY_SCAN_RESULT;
import static com.yun.mayi.pda.utils.Constants.SCN_CUST_ACTION_SCODE;
import static com.yun.mayi.pda.utils.Constants.SCN_CUST_EX_SCODE;

/**
 * 作者： wh
 * 时间：  2017/12/23
 * 名称：基础共有的activity
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public abstract class BaseActivity extends AppCompatActivity implements ScannerManager.IScannerStatusListener, BaseContract.View {


    protected static final int UPDATE_LIST = 0x1000;
    protected static final int UPDATE_NUMBER = 0x1001;

    /**
     * 扫描枪输入管理类
     */
    protected ScannerManager mScannerManager;
    /**
     * 扫描枪的声音获取工具类
     */
    protected SoundUtils mSoundUtils;
    /**
     * 按下扫描枪
     */
    protected int pressed = 0;
    /**
     * 扫描输入条码
     */
    protected int scanned = 0;
    /**
     * 解码类型
     */
    protected int decoderType = 0;
    /**
     * 左边的图标（返回键）
     */
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView tvTitle;
    /**
     * 副标题
     */
    @BindView(R.id.tv_subtitle)
    TextView tvSubtitle;
    /**
     * 右边图标
     */
    @BindView(R.id.iv_right)
    ImageView ivRight;
    /**
     * 导航栏
     */
    RelativeLayout llNav;
    /**
     * 内容
     */
    LinearLayout flContent;
    /**
     * 返回内容
     */
    @BindView(R.id.tv_back_text)
    TextView tvBackText;
    /**
     * 返回
     */
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    /**
     * 是否按下扫描枪
     */
    private boolean mIsScanKeyDown = false;
    /**
     * 加载框
     */
    private LoadingDialog loadingDialog;
    /**
     * 是否隐藏标题栏
     */
    private boolean isHideNav = false;
    private WindowManager manager;
    private CustomViewGroup customViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeInit();
        setContentView(R.layout.activity_base);
        llNav = findViewById(R.id.rl_nav);
        flContent = findViewById(R.id.fl_content);
        llNav.setVisibility(isHideNav ? View.GONE : View.VISIBLE);
        flContent.requestFocus();
        getLayoutInflater().inflate(getLayoutResId(), flContent, true);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        //    prohibitDropDown();//禁止下拉
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
        mScannerManager = ScannerManager.getInstance();
        try {
            decoderType = mScannerManager.getDecoderType();
        } catch (NullPointerException e) {
            G.log("xxx" + "空指针异常！");
        }
        mSoundUtils = SoundUtils.getInstance();
        mSoundUtils.init(this);
        initView();
        initReceiver();
    }

    //禁止下拉
    private void prohibitDropDown() {
        manager = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (50 * getResources()
                .getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.TRANSPARENT;
        customViewGroup = new CustomViewGroup(this);
        manager.addView(customViewGroup, localLayoutParams);
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SCN_CUST_ACTION_SCODE);
        intentFilter.addAction(ACTION_DISPLAY_SCAN_RESULT);
        registerReceiver(mSamDataReceiver, intentFilter);
        registerReceiver(mScannerReceiver, intentFilter);
    }


    private BroadcastReceiver mSamDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SCN_CUST_ACTION_SCODE)) {
                String message;
                try {
                    message = intent.getStringExtra(SCN_CUST_EX_SCODE);
                    Message msg = mHandle.obtainMessage(UPDATE_LIST, message);
                    mHandle.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("in", e.toString());
                }
            }
        }
    };

    private BroadcastReceiver mScannerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION_DISPLAY_SCAN_RESULT.equals(intent.getAction())) {
                String result;
                try {
                    result = intent.getStringExtra("decode_data");
                    Message msg = mHandle.obtainMessage(UPDATE_LIST, result);
                    mHandle.sendMessage(msg);
                    G.log("xxxxxxxxxxxxxxxx----------" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                    G.log("in" + e.toString());
                }
            }
        }
    };

    //允许下拉
    private void allowDropDown() {
        manager.removeView(customViewGroup);
    }

    /**
     * 设置左图标
     *
     * @param resId
     */
    public void setLeftIcon(int resId) {
        ivLeft.setImageResource(resId);
    }

    /**
     * 设置左文字
     *
     * @param textId
     */
    public void setLeftTextId(int textId) {

        tvBackText.setText(getResources().getText(textId));
    }

    /**
     * 设置左文字
     *
     * @param textId
     */
    public void setSubTitleTextId(int textId) {

        tvSubtitle.setText(getResources().getText(textId));
    }

    /**
     * 设置右图标
     *
     * @param resId
     */
    public void setRightIcon(int resId) {
        ivRight.setImageResource(resId);
    }

    /**
     * 设置标题
     *
     * @param textId int资源
     */
    public void setTitleTextId(int textId) {
        tvTitle.setText(getResources().getText(textId));
    }

    /**
     * 设置标题
     *
     * @param text
     */
    public void setTitleText(String text) {
        tvTitle.setText(text);
    }

    /**
     * 设置副标题监听
     *
     * @param onClickListener 监听
     */
    public void setSubtitleClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            tvSubtitle.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置右边监听
     *
     * @param onClickListener 监听
     */
    public void setRightClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            ivRight.setOnClickListener(onClickListener);
        }
    }

    public void back() {
        finish();
    }

    /**
     * 设置返回监听
     *
     * @param onClickListener 监听
     */
    public void setBackClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            llBack.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置隐藏头部
     *
     * @param isHideNav 标题
     */
    public void hideTitleLayout(boolean isHideNav) {
        this.isHideNav = isHideNav;
    }

    /**
     * 界面初始化前期准备
     */
    public void beforeInit() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.red_btn));
    }

    public abstract int getLayoutResId();

    public abstract void initView();

    @Override
    protected void onResume() {
        super.onResume();
        mScannerManager.addScannerStatusListener(this);
        if (decoderType == Constants.DECODER_ONED_SCAN) {
            if (!mScannerManager.getScannerEnable()) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.action_settings)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setMessage(R.string.scan_message)
                        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                closeSelf();
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //  ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        //    activityManager.moveTaskToFront(getTaskId(), 0);
        mScannerManager.removeScannerStatusListener(this);
        try {
            mScannerManager.disconnectDecoderSRV();
        } catch (NullPointerException e) {
            G.log("xxx" + "空指针异常！");
        }
    }

    @SuppressLint("HandlerLeak")
    protected Handler mHandle = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_LIST:
                    scanned++;
                    updateScanData((String) msg.obj);
                    G.log("ssssss" + msg.obj.toString());
                case UPDATE_NUMBER:
                    updateCount();
                    break;
                default:
                    break;
            }
        }
    };

    public void updateCount() {
    }

    public void updateScanData(String data) {
    }

    protected void closeSelf() {
        this.finish();
    }

    /**
     * 清除数据
     */
    public void clear() {
        pressed = 0;
        scanned = 0;
        mHandle.sendEmptyMessage(UPDATE_NUMBER);
    }

    @Override
    public void onScannerResultChanage(byte[] bytes) {
        String data = new String(bytes);
        G.log("eeeee" + data);
        mSoundUtils.success();
        Message msg = mHandle.obtainMessage(UPDATE_LIST, data);
        mHandle.sendMessage(msg);

    }

    @Override
    public void onScannerStatusChanage(int i) {
    }


    /**
     * 显示加载动画
     */
    @Override
    public void showProgress() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    /**
     * 隐藏加载动画
     */
    @Override
    public void hideProgress() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {
        G.showToast(this, message);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        G.log("---x-x-：onKeyDown" + event.getKeyCode());
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_STEP_BACKWARD: {
                if (!mIsScanKeyDown) {
                    pressed++;
                    //扫描枪分发事件
                    mScannerManager.dispatchScanKeyEvent(event);
                    mIsScanKeyDown = true;
                    mHandle.sendEmptyMessage(UPDATE_NUMBER);
                }
                return true;
            }
            case KeyEvent.KEYCODE_CALL:
                return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        G.log("---x-x-：onKeyUp" + event.getKeyCode());
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_STEP_BACKWARD: {
                if (mIsScanKeyDown) {
                    mIsScanKeyDown = false;
                    mScannerManager.dispatchScanKeyEvent(event);
                    //  mScannerManager.triggerLevel(ScannerManager.HIGH_LEVEL);
                }
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //     allowDropDown();
        unregisterReceiver(mSamDataReceiver);
        unregisterReceiver(mScannerReceiver);
    }


}
