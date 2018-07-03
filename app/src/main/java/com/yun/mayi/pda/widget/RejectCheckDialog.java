package com.yun.mayi.pda.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager;
import android.widget.EditText;

import com.yun.mayi.pda.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/3/17
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class RejectCheckDialog extends AlertDialog {
    @BindView(R.id.et_remark)
    EditText etRemark;
    /**
     * 类型0，退货打回，1退货通过
     */
    private int type;

    public RejectCheckDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reject_check);
        ButterKnife.bind(this);
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    public void setType(int type) {
        this.type = type;
        etRemark.setText("");
    }

    @OnClick(R.id.bt_conform)
    public void confrom() {
        if (remarkCallBack != null) {
            remarkCallBack.onCallBack(etRemark.getText().toString(), type);
        }
        dismiss();
    }

    private RemarkCallBack remarkCallBack;

    public void setRemarkCallBack(RemarkCallBack remarkCallBack) {
        this.remarkCallBack = remarkCallBack;
    }

    public interface RemarkCallBack {
        void onCallBack(String remark, int type);
    }
}
