package com.yun.mayi.pda.module.join.loadpack;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间： 2018/05/04
 * 名称：司机拣货(装车)单页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackCarActivity extends BaseMvpActivity<WaitPackCarPresenter> implements OnItemClickListener, WaitPackCarContract.View {
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
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 适配器
     */
    private WaitPackCarAdapter waitPackCarAdapter;
    /**
     * 数据列表
     */
    private List<PickOrder> pickOrderList;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_wait_pack_car;
    }

    @Override
    public void initView() {
        setLeftTextId(R.string.back);
        setLeftIcon(R.mipmap.ic_back_white);
        setTitleTextId(R.string.wait_pick_goods);
        pickOrderList = new ArrayList<>();
        waitPackCarAdapter = new WaitPackCarAdapter(this,pickOrderList);
        rvPark.setLayoutManager(new LinearLayoutManager(this));
        rvPark.setAdapter(waitPackCarAdapter);
        waitPackCarAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter!=null){
            mPresenter.getLoadingOrderList();
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, WaitPackCarDetailActivity.class);
        intent.putExtra("orderId",pickOrderList.get(position).getOrder_id());
        intent.putExtra("sign",pickOrderList.get(position).getMark());
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
    public void setPickOrderList(List<PickOrder> pickOrderList) {
        this.pickOrderList.clear();
        this.pickOrderList.addAll(pickOrderList);
        tvOrderCount.setText(pickOrderList==null?"0":String.valueOf(pickOrderList.size()));
        if (waitPackCarAdapter!=null){
            waitPackCarAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.tv_search)
    public void search() {
        if (mPresenter!=null){
            mPresenter.getLoadingOrderList();
        }
    }
}
