package com.yun.mayi.pda.module.join.manager;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.bean.PickOrderResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.module.join.waitpack.WaitPackedDetailActivity;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 作者： wh
 * 时间： 2018/01/08
 * 名称：分拣管理页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackManagerActivity extends BaseMvpActivity<PackManagerPresenter> implements PackManagerContract.View, OnItemClickListener, PackManagerAdapter.OnPackChangeListener {

    /**
     * 关键字搜索框
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 搜索
     */
    @BindView(R.id.tv_search)
    TextView tvSearch;
    /**
     * 总件数
     */
    @BindView(R.id.tv_count)
    TextView tvCount;
    /**
     * 总箱数
     */
    @BindView(R.id.tv_box_count)
    TextView tvBoxCount;
    /**
     * 装箱订单列表
     */
    @BindView(R.id.rv_park)
    PullToRefreshRecyclerView rvPark;
    private RecyclerView recyclerView;
    /**
     * 没有数据显示
     */
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    /**
     * 适配器
     */
    private PackManagerAdapter adapter;
    /**
     * 页数
     */
    private int page = 1;
    /**
     * 页码
     */
    private int size = 200;
    /**
     * 待拣货单列表
     */
    private List<PickOrder> pickOrderList;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pack_manager;
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setTitleTextId(R.string.packed_manager);
        setLeftTextId(R.string.workBar);
        etSign.setHint("订单号/标记");
        pickOrderList = new ArrayList<>();
        recyclerView = rvPark.getRefreshableView();
        adapter = new PackManagerAdapter(pickOrderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnPackChangeListener(this);

    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        if (mPresenter!=null){
            mPresenter.getConformList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter!=null){
            mPresenter.getConformList();
        }
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        if (mPresenter!=null){
            mPresenter.getConformList();
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, PackManagerDetailActivity.class);
      //  intent.putExtra("orderId",pickOrderList.get(position).getOrder_id());
        intent.putExtra("pickOrder",pickOrderList.get(position));
        startActivity(intent);
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
    public String getAgentNumber() {
        return UserMessage.getInstance().getAgentNumber();
    }

    @Override
    public String getKeyword() {
        return etSign.getText().toString();
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
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
    public void setPickOrderResult(PickOrderResult packBoxResult) {
        tvCount.setText(String.valueOf(packBoxResult.getCount()));
        pickOrderList.clear();
        tvNodata.setVisibility(packBoxResult.getList().size() == 0 ? View.VISIBLE : View.GONE);
        pickOrderList.addAll(packBoxResult.getList());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPickChange(View view, int position) {
        Intent intent = new Intent(this, WaitPackedDetailActivity.class);
        intent.putExtra("pickOrder",pickOrderList.get(position));
        intent.putExtra("isPick",1);
        startActivity(intent);
    }
}
