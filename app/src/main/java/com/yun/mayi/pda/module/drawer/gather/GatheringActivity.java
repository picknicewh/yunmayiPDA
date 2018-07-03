package com.yun.mayi.pda.module.drawer.gather;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PayInfo;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;
import com.yun.mayi.pda.bean.ReceptOrderInfo;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.module.home.setting.SettingBluetoothActivity;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.PrintUtils;
import com.yun.mayi.pda.widget.AliPayConformDialog;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 作者： wh
 * 时间：  2018/06/01
 * 名称：订单收款页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GatheringActivity extends BaseMvpActivity<GatheringPresenter> implements GatheringContract.View, OnItemClickListener, GatheringAdapter.OnPrintClickListener, AliPayConformDialog.OnConformCallBack {
    @BindView(R.id.et_sign)
    ClearEditText etSign;

    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    /**
     * 适配器
     */
    private GatheringAdapter adapter;
    /**
     * 数据列表
     */
    private List<ReceptOrderInfo> pickOrderList;
    /**
     * 当前打印的订单id
     */
    private String orderId = "";
    /**
     * 确认支付对话框
     */
    private AliPayConformDialog dialog;
    /**
     * 实际收款
     */
    private String totalFee;
    /**
     * 支付编码
     */
    private String authCode;
    /**
     * 平台支付交易码
     */
    private String alipayTradeNo;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gathering;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.order_gather);
        pickOrderList = new ArrayList<>();
        adapter = new GatheringAdapter(pickOrderList);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnPrintClickListener(this);
        dialog = new AliPayConformDialog(this);
        dialog.setOnConformCallBack(this);
        if (mPresenter != null) {
            mPresenter.getOrderReceiptList();
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }


    @Override
    public String getAgentNumber() {
        return UserMessage.getInstance().getAgentNumber();
    }

    @Override
    public String getBeginTime() {
        return getIntent().getStringExtra("startTime");
    }

    @Override
    public String getEndTime() {
        return getIntent().getStringExtra("endTime");
    }

    @Override
    public String getKeyword() {
        return etSign.getText().toString();
    }

    @Override
    public String getOrderId() {
        return orderId;
    }


    @Override
    public String getTotalFee() {
        return totalFee;
    }

    @Override
    public String getAuthCode() {
        return authCode;
    }

    @Override
    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    @Override
    public void setPickOrderList(List<ReceptOrderInfo> orderInfoList) {
        this.pickOrderList.clear();
        this.pickOrderList.addAll(orderInfoList);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setReceiptPrintInfo(ReceiptPrintInfo receiptPrintInfo) {
        BluetoothSocket socket = YunmayiApplication.getBluetoothSocket();
        if (socket == null) {
            G.showToast(this, "请连接蓝牙打印机！");
            Intent intent = new Intent(this, SettingBluetoothActivity.class);
            startActivity(intent);
        } else {
            PrintUtils.drawerPack(receiptPrintInfo);
        }
    }

    @Override
    public void setPayInfo(PayInfo payInfo) {
        alipayTradeNo = payInfo.getAlipayTradeNo();
        if (mPresenter != null) {
            mPresenter.queryPayList();
        }
    }



    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        if (dialog != null && dialog.isShowing()) {
            etSign.setText("");
            dialog.setScanCode(data);
        }
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        if (mPresenter != null) {
            mPresenter.getOrderReceiptList();
        }
    }

    @Override
    public void onClick(View view, int position) {
        orderId = pickOrderList.get(position).getOrderId();
        dialog.show();
        dialog.setOrderInfo(pickOrderList.get(position));
    }

    @Override
    public void onPrint(int position) {
        orderId = pickOrderList.get(position).getOrderId();
        if (mPresenter != null) {
            mPresenter.receiptsPrint();
        }
    }

    @Override
    public void onCallBack(String totalFee, String authCode) {
        this.totalFee = totalFee;
        this.authCode = authCode;
        if (mPresenter != null) {
            mPresenter.aliPay();
        }
    }
}
