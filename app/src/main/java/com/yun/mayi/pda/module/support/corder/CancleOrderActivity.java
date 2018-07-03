package com.yun.mayi.pda.module.support.corder;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpPackActivity;
import com.yun.mayi.pda.bean.PackBox;
import com.yun.mayi.pda.bean.PackBoxResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.module.support.packbox.PackBoxDetailActivity;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CancleOrderActivity extends BaseMvpPackActivity<CancelPresenter> implements CancelOrderContract.View, PullToRefreshBase.OnRefreshListener2<RecyclerView>, OnItemClickListener {
    /**
     * 取消订单总数
     */
    @BindView(R.id.tv_cancel_order_count)
    TextView tvCancelOrderCount;
    /**
     * 取消箱数
     */
    @BindView(R.id.tv_cancel_count)
    TextView tvCancelCount;
    /**
     * 取消订单列表
     */
    @BindView(R.id.rv_park)
    PullToRefreshRecyclerView rvPark;
    private RecyclerView recyclerView;
    /**
     * 适配器
     */
    private CancelOrderAdapter adapter;
    /**
     * 数据列表
     */
    private List<PackBox> packBoxList;

    /**
     * 页码
     */
    private int page = 1;
    /**
     * 是否有更多数据
     */
    private boolean isHasNext = true;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_cancle_order;
    }

    @Override
    public void initView() {
        super.initView();
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.cancel_order);
        packBoxList = new ArrayList<>();
        rvPark.setMode(PullToRefreshBase.Mode.BOTH);
        rvPark.setOnRefreshListener(this);
        adapter = new CancelOrderAdapter(packBoxList);
        recyclerView = rvPark.getRefreshableView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtil.isNetworkAvailable()) {
            if (mPresenter != null) {
                mPresenter.getPackBoxList();
            }
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
    public int getPage() {
        return page;
    }

    @Override
    public void setParkBoxResult(PackBoxResult parkBox) {
        isHasNext = parkBox.isHasNext();
        tvCancelCount.setText(String.valueOf(parkBox.getCount()));
        tvCancelOrderCount.setText(String.valueOf(parkBox.getList().size()));
        if (page == 1) {
            packBoxList.clear();
        }
        packBoxList.addAll(parkBox.getList());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDataChange() {
        mPresenter.getPackBoxList();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        refreshView.onRefreshComplete();
        rvPark.setMode(PullToRefreshBase.Mode.BOTH);
        page = 1;
        mPresenter.getPackBoxList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        refreshView.onRefreshComplete();
        page++;
        mPresenter.getPackBoxList();
        if (!isHasNext) {
            rvPark.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            showMessage("没有更多数据！");
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, PackBoxDetailActivity.class);
        intent.putExtra("boxId", String.valueOf(packBoxList.get(position).getBoxId()));
        intent.putExtra("source", Constants.CANCLEORDER);
        startActivity(intent);
    }



    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        if (mPresenter != null) {
            mPresenter.getPackBoxList();
        }
    }
}
