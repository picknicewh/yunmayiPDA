package com.yun.mayi.pda.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/1/5
 * 名称：装多个箱对话框
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackMulBoxDialog extends AlertDialog {
    /**
     * 装箱数
     */
    @BindView(R.id.tv_adjust_count)
    TextView tvAdjustCount;
    /**
     * 打印箱数
     */
    private int packNum = 0;
    /**
     * 临时打印箱数
     */
    private int tempPackNum = 0;
    /**
     * 箱号回调
     */
    private OnPackMulBoxCallBack onPackMulBoxCallBack;
    public PackMulBoxDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pack_mul_box);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }

    public void setPackInfo(int unit, int totalCount) {
        packNum = totalCount % unit == 0 ? totalCount / unit : totalCount / unit + 1;
        tvAdjustCount.setText(String.valueOf(packNum));
        tempPackNum = packNum;
    }

    @OnClick({R.id.iv_minus, R.id.iv_add, R.id.tv_conform, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_minus:
                tempPackNum++;
                if (tempPackNum > packNum) {
                    tempPackNum = packNum;
                }
                tvAdjustCount.setText(String.valueOf(tempPackNum));
                break;
            case R.id.iv_add:
                tempPackNum--;
                if (tempPackNum < 0) {
                    tempPackNum = 0;
                }
                tvAdjustCount.setText(String.valueOf(tempPackNum));
                break;
            case R.id.tv_conform:
                if (onPackMulBoxCallBack != null) {
                    onPackMulBoxCallBack.mulBoxCallBack(packNum);
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }


    public void setOnPackMulBoxCallBack(OnPackMulBoxCallBack onPackMulBoxCallBack) {
        this.onPackMulBoxCallBack = onPackMulBoxCallBack;
    }

    public interface OnPackMulBoxCallBack {
        void mulBoxCallBack(int boxNum);
    }
}
