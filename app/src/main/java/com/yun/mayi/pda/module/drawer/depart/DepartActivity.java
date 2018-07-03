package com.yun.mayi.pda.module.drawer.depart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.bean.PickOrderResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间： 2018/06/07
 * 名称：司机---》司机发车界面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class DepartActivity extends BaseMvpActivity<DepartPresenter> implements DepartContract.View, OnItemClickListener {

    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 订单数量
     */
    @BindView(R.id.tv_order_count)
    TextView tvOrderCount;
    /**
     * 发车订单列表
     */
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    /**
     * 页数
     */
    private int page = 1;
    /**
     * 页码
     */
    private int size = 1000;
    /**
     * 适配器
     */
    private DepartAdapter adapter;
    /**
     * 发车订单列表
     */
    private List<PickOrder> pickOrderList;
    private String orderIdStr;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_depart;
    }

    @Override
    public void initView() {
        setLeftTextId(R.string.workBar);
        setLeftIcon(R.mipmap.ic_back_white);
        setTitleTextId(R.string.drawer_depart);
        pickOrderList = new ArrayList<>();
        adapter = new DepartAdapter(pickOrderList);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        rvData.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.getTallyingList();
        }
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
    public int getPage() {
        return page;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getOrderIdStr() {

        return orderIdStr;
    }

    @Override
    public void setPickOrderResult(PickOrderResult pickOrderResult) {
        pickOrderList.clear();
        tvOrderCount.setText(String.valueOf(pickOrderResult.getCount()));
        pickOrderList.addAll(pickOrderResult.getList());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccess() {
        if (mPresenter != null) {
            mPresenter.getTallyingList();
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }


    @OnClick(R.id.tv_search)
    public void search() {
        if (mPresenter != null) {
            mPresenter.getTallyingList();
        }
    }


    @OnClick(R.id.tv_all_depart)
    public void depart() {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < pickOrderList.size(); i++) {
            PickOrder pickOrder = pickOrderList.get(i);
            //if (pickOrder.getState() == 1 && pickOrder.getIs_pick() == 2 )
            if (pickOrder.getState() == 1) {
                buffer.append(pickOrder.getOrder_id()).append(",");
            }
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        orderIdStr = buffer.toString();
        if (G.isEmteny(getOrderIdStr())) {
            G.showToast(this, "没有待发车的订单。");
            return;
        }
        if (mPresenter != null) {
            mPresenter.startDelivery();
        }
    }

    @Override
    public void onClick(View view, int position) {
        orderIdStr =pickOrderList.get(position).getOrder_id();
        if (mPresenter != null) {
            mPresenter.startDelivery();
        }
    }
}
