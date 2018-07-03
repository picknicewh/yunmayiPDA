package com.yun.mayi.pda.module.drawer.manager;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.bean.PickOrderResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.module.drawer.tallying.TallyingDetailActivity;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/6/25
 * 名称：订单管理頁面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class OrderManagerActivity extends BaseMvpActivity<OrderManagerPresenter> implements OrderManagerContract.View, OrderManagerAdapter.OnUpLoadListener, OnItemClickListener {

    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 订单详情
     */
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    /**
     * 适配器
     */
    private OrderManagerAdapter adapter;
    /**
     * 页码
     */
    private int page = 1;
    /**
     * 页数
     */
    private int size = 2000;
    /**
     * 数据列表
     */
    private List<PickOrder> pickOrderList;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_manager;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.order_manager);
        pickOrderList = new ArrayList<>();
        adapter = new OrderManagerAdapter(pickOrderList);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(adapter);
        adapter.setOnUpLoadListener(this);
        adapter.setOnItemClickListener(this);
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

    @Override
    public String getStartTime() {
        return getIntent().getStringExtra("startTime");
    }

    @Override
    public String getEndTime() {
        return getIntent().getStringExtra("endTime");
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getKeyword() {
        return etSign.getText().toString();
    }

    @Override
    public String getAgentNumber() {
        return UserMessage.getInstance().getAgentNumber();
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
    public String getNumber() {
        return getIntent().getStringExtra("number");
    }

    @Override
    public void setPickOrderResult(PickOrderResult pickOrderResult) {
        this.pickOrderList.clear();
        this.pickOrderList.addAll(pickOrderResult.getList());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onUpLoad(View view, int position) {
        Intent intent = new Intent(this, TallyingDetailActivity.class);
        intent.putExtra("pickOrder",pickOrderList.get(position));
        intent.putExtra("isPick",3);
        intent.putExtra("isFirst",1);
        startActivity(intent);
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, OrderManagerDetailActivity.class);
        intent.putExtra("orderId",pickOrderList.get(position).getOrder_id());
        startActivity(intent);
    }
}
