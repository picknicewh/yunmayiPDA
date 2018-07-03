package com.yun.mayi.pda.module.join.goodspack;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PackBoxDetail2;
import com.yun.mayi.pda.bean.PickOrderDetail;
import com.yun.mayi.pda.bean.PickOrderDetailResult;
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
 * 名称：按货分拣页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GoodsPackActivity extends BaseMvpActivity<GoodsPackPresenter> implements GoodsPackContract.View, OnItemClickListener {

    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 按货单个分拣列表
     */
    @BindView(R.id.rv_park_s)
    RecyclerView rvPackS;
    /**
     * sku数
     */
    @BindView(R.id.tv_sku)
    TextView tvSku;
    private GoodsPackAdapterS adapterS;
    /**
     * 按货整箱分拣列表
     */
    @BindView(R.id.rv_park_t)
    RecyclerView rvPackT;
    private GoodsPackAdapterT adapterT;
    /**
     * 按货单个分拣数据列表
     */
    private List<PickOrderDetail> pickOrderDetailList;
    /**
     * 按货整个分拣数据列表
     */
    private List<PackBoxDetail2> packBoxDetailList;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_goods_pack;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.goods_pack);
        pickOrderDetailList = new ArrayList<>();
        packBoxDetailList = new ArrayList<>();
        adapterS = new GoodsPackAdapterS(pickOrderDetailList);
        rvPackS.setLayoutManager(new LinearLayoutManager(this));
        rvPackS.setNestedScrollingEnabled(true);
        rvPackS.setAdapter(adapterS);
        adapterS.setOnItemClickListener(this);
        adapterT = new GoodsPackAdapterT(packBoxDetailList);
        rvPackT.setLayoutManager(new LinearLayoutManager(this));
        rvPackT.setNestedScrollingEnabled(true);
        rvPackT.setAdapter(adapterT);
        if (mPresenter != null) {
            mPresenter.getPickByProduct();
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
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
    public String getKeyword() {
        return etSign.getText().toString();
    }

    @Override
    public void setPickOrderDetailResult(PickOrderDetailResult pickOrderDetailResult) {
        this.packBoxDetailList.clear();
        this.pickOrderDetailList.clear();
         tvSku.setText(String.valueOf(pickOrderDetailResult.getCount()));
        this.pickOrderDetailList.addAll(pickOrderDetailResult.getDetail_list());
        this.packBoxDetailList.addAll(pickOrderDetailResult.getBox_list());
        if (adapterS != null) {
            adapterS.notifyDataSetChanged();
        }
        if (adapterT != null) {
            adapterT.notifyDataSetChanged();
        }
    }


    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        mPresenter.getPickByProduct();
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        if (mPresenter != null) {
            mPresenter.getPickByProduct();
        }
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(this, GoodsParkDetailActivity.class);
        intent.putExtra("startTime", getBeginTime());
        intent.putExtra("endTime", getEndTime());
        intent.putExtra("productId", String.valueOf(pickOrderDetailList.get(position).getProduct_id()));
        startActivity(intent);
    }
}
