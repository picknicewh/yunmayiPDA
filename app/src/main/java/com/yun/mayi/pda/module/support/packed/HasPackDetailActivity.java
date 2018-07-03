package com.yun.mayi.pda.module.support.packed;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.Order;
import com.yun.mayi.pda.bean.PackBox;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.module.home.setting.SettingBluetoothActivity;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.utils.PrintUtils;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/1/16
 * 名称：已拣货订单详情
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HasPackDetailActivity extends BaseMvpActivity<HasPackDetailPresenter> implements HasPackDetailContract.View, OnItemClickListener, ConformDialog.OnConformCallBack {
    /**
     * 订单列表
     */
    @BindView(R.id.rv_park)
    RecyclerView rvPark;

    /**
     * 订单数
     */
    @BindView(R.id.tv_order_count)
    TextView tvOrderCount;
    /**
     * sku总数
     */
    @BindView(R.id.tv_sku_count)
    TextView tvSkuCount;
    /**
     * 每页多少条数据
     */
    private int page = 1;
    /**
     * 数据列表
     */
    private List<PackBox> packBoxList;

    /**
     * 适配器
     */
    private HasParkedDetailAdapter adapter;

    private ConformDialog conformDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_has_park_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);

        setTitleTextId(R.string.has_packing_order_detail);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        packBoxList = new ArrayList<>();
        adapter = new HasParkedDetailAdapter(packBoxList);
        rvPark.setLayoutManager(new LinearLayoutManager(this));
        rvPark.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        if (NetworkUtil.isNetworkAvailable()) {
            if (mPresenter != null) {
                mPresenter.getPackOrderDetail();
            }
        }
    }


    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getOrderId() {
        return getIntent().getStringExtra("orderId");
    }

    @Override
    public void setParkOrderInfo(Order orderInfo) {
        tvOrderCount.setText(String.valueOf(orderInfo.getSkuNum()));
        tvSkuCount.setText(String.valueOf(orderInfo.getBoxNum()));
        packBoxList.clear();
        packBoxList.addAll(orderInfo.getBoxes());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private int position;

    @Override
    public void onClick(View view, int position) {
        this.position = position;
        conformDialog.show();
        conformDialog.setTitle("是否确认打印？");
    }

    @Override
    public void onCallBack() {
        conformDialog.dismiss();
        PackBox packBox = packBoxList.get(position);
        BluetoothSocket socket = YunmayiApplication.getBluetoothSocket();
        if (socket == null) {
            G.log("xxxxxxx" + "等于空");
            G.showToast(this, "请连接蓝牙打印机！");
            Intent intent = new Intent(this, SettingBluetoothActivity.class);
            startActivity(intent);
        }else {
            if (packBox.getDetails().size() > 0) {
                int boxNum = packBox.getDetails().get(0).getBoxNum();
                for (int i = 0; i < boxNum; i++) {
                    PrintUtils.printData(packBox, boxNum, (i + 1));
                }
            }
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }
}
