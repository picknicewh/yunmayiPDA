package com.yun.mayi.pda.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.db.OrderInfo;
import com.yun.mayi.pda.network.GlideUtils;
import com.yun.mayi.pda.utils.PriceTransfer;

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
public class GoodsDetailDialog extends AlertDialog {
    /**
     * 商品图片
     */
    @BindView(R.id.iv_image)
    ImageView ivImage;
    /**
     * 商品名称
     */
    @BindView(R.id.tv_name)
    TextView tvName;
    /**
     * 商品编码
     */
    @BindView(R.id.tv_code)
    TextView tvCode;
    /**
     * 商品价格
     */
    @BindView(R.id.tv_sell_price)
    TextView tvSellPrice;

    public GoodsDetailDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_goods_detail);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
    }

    public void setGoodsInfo(OrderInfo orderInfo) {
        tvCode.setText(orderInfo.getProductBarCode());
        tvName.setText(orderInfo.getProductTitle());
        GlideUtils.loadImageView(getContext(), "http://api.h5.yunmayi.com"+orderInfo.getProductImage(), ivImage);
        tvSellPrice.setText(PriceTransfer.chageMoneyToString(orderInfo.getProductPrice()));
    }

    @OnClick({R.id.bt_cancel, R.id.bt_conform})
    public void onViewClicked() {
        dismiss();
    }
}
