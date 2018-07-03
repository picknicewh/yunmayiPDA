package com.yun.mayi.pda.module.join.waitpack;

import android.content.Intent;
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
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：待分拣单页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackedActivity extends BaseMvpActivity<WaitPackedPresenter> implements WaitPackedContract.View, OnItemClickListener {

    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 订单列表
     */
    @BindView(R.id.rv_pack)
    RecyclerView rvPack;
    /**
     * 订单总数
     */
    @BindView(R.id.tv_order_count)
    TextView tvOrderCount;
    /**
     * 没有数据显示
     */
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    /**
     * 适配器
     */
    private WaitPackedAdapter adapter;
    /**
     * 页数
     */
    private int page = 1;
    /**
     * 页码
     */
    private int size = 2000;
    /**
     * 待拣货单列表
     */
    private List<PickOrder> pickOrderList;

    public int getLayoutResId() {
        return R.layout.activity_wait_pack;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.wait_packing_order);
        etSign.setHint("标记/订单号");
        pickOrderList = new ArrayList<>();
        adapter = new WaitPackedAdapter(pickOrderList);
        rvPack.setLayoutManager(new LinearLayoutManager(this));
        rvPack.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.getConformList();
        }

    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }


    @OnClick(R.id.tv_search)
    public void search() {
        if (mPresenter != null) {
            mPresenter.getConformList();
        }
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        if (mPresenter != null) {
            mPresenter.getConformList();
        }
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
        tvOrderCount.setText(String.valueOf(packBoxResult.getCount()));
        pickOrderList.clear();
        tvNodata.setVisibility(packBoxResult.getList().size() == 0 ? View.VISIBLE : View.GONE);
        pickOrderList.addAll(packBoxResult.getList());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, WaitPackedDetailActivity.class);
        intent.putExtra("pickOrder",pickOrderList.get(position));
        intent.putExtra("isPick",0);
        startActivity(intent);
    }
}
