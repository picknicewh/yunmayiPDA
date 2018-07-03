package com.yun.mayi.pda.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.ReceptOrderInfo;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.PriceTransfer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/01/06
 * 名称：支付宝确认支付对话框
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class AliPayConformDialog extends AlertDialog {

    /**
     * 支付码
     */
    @BindView(R.id.et_code)
    ClearEditText etCode;
    /**
     * 总金额
     */
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    /**
     * 实际金额
     */
    @BindView(R.id.tv_actual_money)
    TextView tvActualMoney;
    private Activity context;

    public AliPayConformDialog(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_ailpay_conform);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
     //   etCode.setText("28124599449668678");
    }

    public void setOrderInfo(ReceptOrderInfo receptOrderInfo) {
        tvTotalMoney.setText(PriceTransfer.chageMoneyToString(receptOrderInfo.getPayTotalSellPrice()));
        tvActualMoney.setText(PriceTransfer.chageMoneyToString(receptOrderInfo.getRealPayTotalSellPrice()));
    }
    public void setScanCode(String scanCode){
        etCode.setText(scanCode);
    }

    public void setOnConformCallBack(OnConformCallBack onConformCallBack) {
        this.onConformCallBack = onConformCallBack;
    }

    public OnConformCallBack onConformCallBack;

    @OnClick({R.id.bt_cancel, R.id.bt_conform})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_cancel:
                dismiss();
                break;
            case R.id.bt_conform:
                 String totalFee = tvActualMoney.getText().toString();
                 String authCode = etCode.getText().toString();
                 if (G.isEmteny(authCode)){
                     G.showToast(context,"支付编码不能为空！");
                     return;
                 }
                if (G.isEmteny(totalFee)){
                    G.showToast(context,"支付金额不能为空！");
                    return;
                }
                if (onConformCallBack != null) {
                    onConformCallBack.onCallBack(totalFee,authCode);
                }
                dismiss();
                break;
        }

    }

    public interface OnConformCallBack {
        void onCallBack(String totalFee,String authCode);
    }
}
