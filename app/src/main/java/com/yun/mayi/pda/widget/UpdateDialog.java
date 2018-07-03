package com.yun.mayi.pda.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.utils.G;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者： wh
 * 时间：  2018/3/13
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class UpdateDialog extends AlertDialog {
    @BindView(R.id.progress)
    ProgressBar progressBar;

    protected UpdateDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }

    public void setProgressChange(int max, int progress) {
        G.log("---progress:"+progress);
        G.log("---max:"+max);
        progressBar.setMax(max);
        progressBar.setProgress(progress);
    }

}
