package com.yun.mayi.pda.module.support.packed;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpPackActivity;
import com.yun.mayi.pda.bean.Order;
import com.yun.mayi.pda.bean.PackOrderResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/01/05
 * 名称：已拣货单
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HasPackActivity extends BaseMvpPackActivity< HasPackPresenter> implements HasPackContract.View, OnItemClickListener {
    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 订单数
     */
    @BindView(R.id.tv_order_count)
    TextView tvOrderCount;
    /**
     * sku总数
     */
    @BindView(R.id.tv_box_count)
    TextView tvBoxCount;

    /**
     * 订单列表
     */
    @BindView(R.id.rv_park)
    RecyclerView rvPark;
    /**
     * 适配器
     */
    private HasPackAdapter adapter;
    /**
     * 数据列表
     */
    private List<Order> orderList;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_has_park;
    }

    @Override
    public void initView() {
        super.initView();
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.has_packing_order);
     //   setRightIcon();
        orderList = new ArrayList<>();
        adapter = new HasPackAdapter(this, orderList);
        rvPark.setLayoutManager(new LinearLayoutManager(this));
        rvPark.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtil.isNetworkAvailable()){
            mPresenter.getPackList();
        }
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getKeyWord() {
        return etSign.getText().toString();
    }

    @Override
    public String getBeginTime() {
        return tvStartTime.getText().toString();
    }

    @Override
    public String getEndTime() {
        return tvEndTime.getText().toString();
    }

    @Override
    public int getStatus() {
        return Constants.HAS_PACK;
    }

    @Override
    public void setParkOrderInfo(PackOrderResult parkInfo) {
        tvOrderCount.setText(String.valueOf(parkInfo.getOrderNum()));
        tvBoxCount.setText(String.valueOf(parkInfo.getBoxNum()));
        orderList.clear();
        orderList.addAll(parkInfo.getOrders());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.tv_search)
    public void search(View view) {
        mPresenter.getPackList();
    }

    @Override
    public void onDataChange() {
        mPresenter.getPackList();
    }


    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, HasPackDetailActivity.class);
        intent.putExtra("orderId", String.valueOf(orderList.get(position).getId()));
        startActivity(intent);
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }
}
