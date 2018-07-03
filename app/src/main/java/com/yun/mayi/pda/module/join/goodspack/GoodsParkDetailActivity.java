package com.yun.mayi.pda.module.join.goodspack;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PickOrderDetail;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.utils.G;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * 作者： wh
 * 时间： 2018/3/30
 * 名称：整箱按货分拣详情页
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GoodsParkDetailActivity extends BaseMvpActivity<GoodsPackDetailPresenter> implements GoodsPackDetailContract.View {

    @BindView(R.id.rv_detail)
    RecyclerView rvDetail;
    /**
     * 适配器
     */
     private GoodsPackDetailAdapter adapter;
     private List<PickOrderDetail> pickOrderDetailList;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_goods_park_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.goods_pack_detail);
        pickOrderDetailList =new ArrayList<>();
        adapter = new GoodsPackDetailAdapter(pickOrderDetailList);
        rvDetail.setLayoutManager(new LinearLayoutManager(this));
        rvDetail.setAdapter(adapter);
        if (mPresenter != null) {
            mPresenter.getPickByProductDetail();
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
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getAgentNumber() {
        return UserMessage.getInstance().getAgentNumber();
    }

    @Override
    public String getProductId() {
        return getIntent().getStringExtra("productId");
    }

    @Override
    public void setPickOrderDetailList(List<PickOrderDetail> pickOrderDetailList) {
        G.log("xxxxxxxxx"+pickOrderDetailList.size());
        this.pickOrderDetailList.clear();
        this.pickOrderDetailList.addAll(pickOrderDetailList);
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initInject() {
       mDaggerActivityComponent.inject(this);
    }
}
