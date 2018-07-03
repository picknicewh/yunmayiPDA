package com.yun.mayi.pda.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.RejectVo;
import com.yun.mayi.pda.utils.G;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/01/06
 * 名称：确认对话框
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class RejectGoodsDetailDialog extends AlertDialog {
    /**
     * 商品订单编号
     */
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    /**
     * 退货原因
     */
    @BindView(R.id.tv_reason)
    TextView tvReason;
    /**
     * 客户备注
     */
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    /**
     * 客服备注
     */
    @BindView(R.id.tv_plat_remark)
    TextView tvPlatRemark;

    public RejectGoodsDetailDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_reject_goods_detail);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }

   public  void setRejectInfo(RejectVo rejectInfo){
        tvOrderNumber.setText(rejectInfo.getOrder_id());
        tvPlatRemark.setText(G.isEmteny(rejectInfo.getPlat_remark())?"无":rejectInfo.getPlat_remark());
        tvReason.setText(rejectInfo.getReturn_goods_reason());
        tvRemark.setText(G.isEmteny(rejectInfo.getBuyer_remark())?"无":rejectInfo.getBuyer_remark());
   }

    @OnClick(R.id.bt_cancel)
    public void onViewClicked() {
        dismiss();
    }
}
