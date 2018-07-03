package com.yun.mayi.pda.module.drawer.tallying;

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
 * 时间：  2018/06/07
 * 名称：司机点货页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TallyingActivity extends BaseMvpActivity<TallyingPresenter> implements OnItemClickListener, TallyingContract.View {
    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 订单总数
     */
    @BindView(R.id.tv_order_count)
    TextView tvOrderCount;
    /**
     * 订单列表
     */
    @BindView(R.id.rv_park)
    RecyclerView rvPark;
    /**
     * 适配器
     */
    private TallyingAdapter adapter;
    /**
     * 页码
     */
    private int page = 1;
    /**
     * 页数
     */
    private int size = 2000;
    /**
     * 订单列表
     */
     private List<PickOrder> pickOrderList;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_tallying;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.wait_tallying);
        pickOrderList = new ArrayList<>();
        adapter = new TallyingAdapter(pickOrderList);
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
        if (mPresenter != null) {
            mPresenter.getTallyingList();
        }
    }

    @OnClick(R.id.tv_search)
    public void search() {
        if (mPresenter != null) {
            mPresenter.getTallyingList();
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, TallyingDetailActivity.class);
        intent.putExtra("pickOrder",pickOrderList.get(position));
        intent.putExtra("isPick",2);
        intent.putExtra("isFirst",0);
        startActivity(intent);
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
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
    public String getAgentNumber() {
        return UserMessage.getInstance().getAgentNumber();
    }

    @Override
    public String getNumber() {
        return getIntent().getStringExtra("number");
    }

    @Override
    public void setPickOrderResult(PickOrderResult pickOrderResult) {
        this.pickOrderList.clear();
        this.pickOrderList.addAll(pickOrderResult.getList());
        tvOrderCount.setText(String.valueOf(pickOrderResult.getCount()));
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}
