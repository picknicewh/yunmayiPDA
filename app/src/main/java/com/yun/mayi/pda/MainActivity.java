package com.yun.mayi.pda;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseActivity;
import com.yun.mayi.pda.widget.CheckDialog;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements CheckDialog.onPassWordCatchListener {

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_press_count)
    TextView tvPressCount;
    @BindView(R.id.tv_scan_count)
    TextView tvScanCount;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    private CheckDialog checkDialog;

    private Bitmap bitmap = null;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main1;
    }

    @Override
    public void initView() {
        checkDialog = new CheckDialog(this);
        checkDialog.setOnPassWordCatchListener(this);
    }

    @Override
    public void updateCount() {
        tvPressCount.setText(pressed + "");
        tvScanCount.setText(scanned + "");
    }

    @Override
    public void updateScanData(String data) {
        tvCode.setText(data);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            checkDialog.show();
            checkDialog.clean();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onCatch(String password) {
        if (password.equals("1234")) {
            YunmayiApplication.exitApp();
        }
    }


    @OnClick(R.id.bt_print)
    public void print() {

    }
}
