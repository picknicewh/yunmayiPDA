package com.yun.mayi.pda.module.support.packbox;

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
 * 名称：装箱管理页
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ParkBoxManagerActivity extends BaseMvpPackActivity< PackBoxPresenter> implements OnItemClickListener, PackBoxContract.View, PullToRefreshBase.OnRefreshListener2<RecyclerView> {
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
    @BindView(R.id.tv_sku_count)
    TextView tvSkuCount;

    /**
     * 订单列表
     */
    @BindView(R.id.rv_park)
    PullToRefreshRecyclerView prvPack;
    RecyclerView rvPark;
    /**
     * 适配器
     */
    private ParkBoxManagerAdapter adapter;
    /**
     * 每页多少条数据
     */
    private int page = 1;

    /**
     * 数据列表
     */
    private List<PackBox> packBoxList;
    /**
     * 是否还有更多数据
     */
    private boolean hasMoreData = true;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_park_box_manager;
    }

    @Override
    public void initView() {
        super.initView();
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.pack_manager);
        prvPack.setMode(PullToRefreshBase.Mode.BOTH);
        rvPark = prvPack.getRefreshableView();
        prvPack.setOnRefreshListener(this);
        packBoxList = new ArrayList<>();
        adapter = new ParkBoxManagerAdapter(packBoxList);
        rvPark.setLayoutManager(new LinearLayoutManager(this));
        rvPark.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtil.isNetworkAvailable()){
            if (mPresenter != null) {
                mPresenter.getPackBoxManagert();
            }
        }

    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, PackBoxDetailActivity.class);
        intent.putExtra("boxId", String.valueOf(packBoxList.get(position).getBoxId()));
        intent.putExtra("source", Constants.PACKEDDETAIL);
        startActivity(intent);
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
        tvOrderCount.setText(String.valueOf(parkBox.getOrderNum()));
        tvSkuCount.setText(String.valueOf(parkBox.getBoxNum()));
        hasMoreData = parkBox.isHasNext();
        if (page == 1) {
            packBoxList.clear();
        }
        packBoxList.addAll(parkBox.getList());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.tv_search)
    public void search() {
        page = 1;
        mPresenter.getPackBoxManagert();
    }


    @Override
    public void onDataChange() {//时间改变选择
        mPresenter.getPackBoxManagert();
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        mPresenter.getPackBoxManagert();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        page = 1;
        refreshView.onRefreshComplete();
        mPresenter.getPackBoxManagert();
        prvPack.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        page++;
        refreshView.onRefreshComplete();
        mPresenter.getPackBoxManagert();
        if (!hasMoreData) {
            prvPack.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            showMessage("没有更多数据");
        }
    }
}
