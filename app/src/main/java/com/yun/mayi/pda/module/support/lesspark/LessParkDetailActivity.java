package com.yun.mayi.pda.module.support.lesspark;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PackBoxDetail;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.utils.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/01/08
 * 名称：当日缺货详情页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LessParkDetailActivity extends BaseMvpActivity<LessPackDetailPresenter> implements LessPackDetailContract.View {
    /**
     * 缺货详情
     */
    @BindView(R.id.rv_less)
    RecyclerView rvLess;
    /**
     * 缺货详情适配器
     */
    private LessPackDetailAdapter adapter;

    /**
     * 数据列表
     */
    private List<PackBoxDetail> packBoxDetailList;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_less_park_detail;
    }



    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.outof_stock_detail);
        packBoxDetailList = new ArrayList<>();
        adapter = new LessPackDetailAdapter(packBoxDetailList);
        rvLess.setLayoutManager(new LinearLayoutManager(this));
        rvLess.setAdapter(adapter);
        if (NetworkUtil.isNetworkAvailable()){
            if (mPresenter != null) {
                mPresenter.getLessPackDetail();
            }
        }

    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getProductId() {

        return getIntent().getStringExtra("productId");
    }

    @Override
    public String getBeginTime() {
        return getIntent().getStringExtra("beginTime");
    }

    @Override
    public String getEndTime() {
        return getIntent().getStringExtra("endTime");
    }

    @Override
    public void setOrderDetailInfoList(List<PackBoxDetail> packBoxDetailList) {
        this.packBoxDetailList.clear();
        this.packBoxDetailList.addAll(packBoxDetailList);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }



    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }
}
