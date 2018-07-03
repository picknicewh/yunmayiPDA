package com.yun.mayi.pda.module.support.lesspark;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpPackActivity;
import com.yun.mayi.pda.bean.OrderDetail;
import com.yun.mayi.pda.bean.OrderDetailResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/01/06
 * 名称：当日缺货页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LessPackActivity extends BaseMvpPackActivity<LessPackPresenter> implements OnItemClickListener, LessPackContract.View {
    /**
     * 列表
     */
    @BindView(R.id.rv_park)
    RecyclerView rvPark;
    /**
     * 搜索内容
     */
    @BindView(R.id.et_code_search)
    ClearEditText etCodeSearch;
    /**
     * 缺少的值
     */
    @BindView(R.id.tv_less_sku_count)
    TextView tvLessSkuCount;
    /**
     * 缺少箱数
     */
    @BindView(R.id.tv_less_count)
    TextView tvLessCount;
    /**
     * 适配器
     */
    private LessPackAdapter adapter;

    /**
     * 页码
     */
    private int page = 1;
    /**
     * 数据列表
     */
    private List<OrderDetail> orderDetailList;
    /**
     * 是否第一次启动
     */
    private boolean isFirst = true;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_less_park;
    }

    @Override
    public void initView() {
        super.initView();
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.outof_stock);
        setSearchGone();
        orderDetailList = new ArrayList<>();
        adapter = new LessPackAdapter(orderDetailList);
        rvPark.setLayoutManager(new LinearLayoutManager(this));
        rvPark.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtil.isNetworkAvailable()) {
            if (mPresenter != null) {
                mPresenter.getLessPack();
            }
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @OnClick(R.id.tv_code_search)
    public void search() {
        mPresenter.getLessPack();
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etCodeSearch.setText(data);
        mPresenter.getLessPack();
    }

    @Override
    public void onDataChange() {
        mPresenter.getLessPack();
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, LessParkDetailActivity.class);
        intent.putExtra("productId", String.valueOf(orderDetailList.get(position).getProductId()));
        intent.putExtra("beginTime", getBeginTime());
        intent.putExtra("endTime", getEndTime());
        startActivity(intent);
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getKeyWord() {
        return etCodeSearch.getText().toString();
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
    public void setOrderDetailInfo(OrderDetailResult orderDetailInfo) {
        orderDetailList.clear();
        orderDetailList.addAll(orderDetailInfo.getList());
        tvLessSkuCount.setText(String.valueOf(orderDetailInfo.getCount()));
        tvLessCount.setText(String.valueOf(orderDetailInfo.getOutOfStockNum()));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}
