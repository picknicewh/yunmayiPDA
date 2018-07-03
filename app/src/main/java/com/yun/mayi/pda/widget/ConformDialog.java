package com.yun.mayi.pda.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yun.mayi.pda.R;

/**
 * 作者： wh
 * 时间：  2018/01/06
 * 名称：确认对话框
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ConformDialog extends android.app.AlertDialog implements View.OnClickListener {
    /**
     * 标题
     */
    private TextView tvContent;
    /**
     * 取消
     */
    private Button btCancel;
    /**
     * 确认
     */
    private Button btConform;
    private Activity context;

    public ConformDialog(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_conform);
        tvContent = findViewById(R.id.tv_content);
        btCancel = findViewById(R.id.bt_cancel);
        btConform = findViewById(R.id.bt_conform);
        btCancel.setOnClickListener(this);
        btConform.setOnClickListener(this);
        setCanceledOnTouchOutside(false);
    }

    public void setTitle(String title) {
        tvContent.setText(title);
    }


    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.bt_cancel) {
            dismiss();
        } else if (viewId == R.id.bt_conform) {
            if (onConformCallBack!=null){
                onConformCallBack.onCallBack();
            }
        }
        dismiss();
    }

    public void setOnConformCallBack(OnConformCallBack onConformCallBack) {
        this.onConformCallBack = onConformCallBack;
    }
    public OnConformCallBack onConformCallBack;
    public interface  OnConformCallBack{
        void onCallBack();
    }
}
