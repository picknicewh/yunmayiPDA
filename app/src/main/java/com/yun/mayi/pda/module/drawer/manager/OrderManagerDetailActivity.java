package com.yun.mayi.pda.module.drawer.manager;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.home.setting.SettingBluetoothActivity;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.PrintUtils;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderManagerDetailActivity extends BaseMvpActivity<OrderManagerDetailPresenter> implements OrderManagerDetailContract.View, ConformDialog.OnConformCallBack {

    /**
     * 订单一维码
     */
    @BindView(R.id.iv_image)
    ImageView ivImage;
    /**
     * 送货员信息
     */
    @BindView(R.id.tv_pick_info)
    TextView tvPickInfo;
    /**
     * 总价
     */
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    /**
     * 缺货金额
     */
    @BindView(R.id.tv_less_money)
    TextView tvLessMoney;
    /**
     * 应收金额
     */
    @BindView(R.id.tv_actual_money)
    TextView tvActualMoney;
    /**
     * 订单列表
     */
    @BindView(R.id.rv_pack)
    RecyclerView rvPack;
    /**
     * 列表
     */
    private List<ReceiptPrintInfo.DetailInfo> printInfoDetailList;
    /**
     * 订单管理详情适配器
     */
    private OrderManagerDetailAdapter adapter;
    /**
     * 确认对话框
     */
    private ConformDialog conformDialog;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_manager_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.order_manager_detail);
        try {
            ivImage.setImageBitmap(PrintUtils.createOneDCode(getOrderId()));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        printInfoDetailList = new ArrayList<>();
        adapter = new OrderManagerDetailAdapter(printInfoDetailList);
        rvPack.setLayoutManager(new LinearLayoutManager(this));
        rvPack.setAdapter(adapter);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        if (mPresenter != null) {
            mPresenter.receiptsPrint();
        }
    }


    @OnClick(R.id.tv_print)
    public void print() {
        conformDialog.show();
        conformDialog.setTitle("确认打印该数据？");
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
    public String getOrderId() {
        return getIntent().getStringExtra("orderId");
    }

    private ReceiptPrintInfo receiptPrintInfo;

    @Override
    public void setReceiptPrintInfo(ReceiptPrintInfo receiptPrintInfo) {
        this.receiptPrintInfo = receiptPrintInfo;
        tvTotalMoney.setText(String.valueOf(receiptPrintInfo.getOriginTotalSellPrice()));
        tvActualMoney.setText(String.valueOf(receiptPrintInfo.getRealPayTotalSellPrice()));
        tvLessMoney.setText(String.valueOf(receiptPrintInfo.getTotalOutPrice()));
        tvPickInfo.setText(receiptPrintInfo.getDeliverymanName());
        printInfoDetailList.addAll(receiptPrintInfo.getDetailList());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCallBack() {
        BluetoothSocket socket = YunmayiApplication.getBluetoothSocket();
        if (socket == null) {
            G.showToast(this, "请连接蓝牙打印机！");
            Intent intent = new Intent(this, SettingBluetoothActivity.class);
            startActivity(intent);
        } else {
            if (receiptPrintInfo != null) {
                PrintUtils.drawerPack(receiptPrintInfo);
            } else {
                //G.showToast(this,"打印数据");
            }

        }
    }
}
