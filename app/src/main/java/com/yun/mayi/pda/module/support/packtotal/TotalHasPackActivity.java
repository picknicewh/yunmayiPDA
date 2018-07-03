package com.yun.mayi.pda.module.support.packtotal;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpPackActivity;
import com.yun.mayi.pda.bean.PackDetailInfo;
import com.yun.mayi.pda.bean.PackInfoDetailResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：已拣货商品(整件)
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TotalHasPackActivity extends BaseMvpPackActivity<TotalHasPackPresenter> implements TotalHasPackContract.View, OnItemClickListener, ConformDialog.OnConformCallBack {
    /**
     * sku数
     */
    @BindView(R.id.tv_sku_count)
    TextView tvSkuCount;
    /**
     * 缺货数量
     */
    @BindView(R.id.tv_less_sku_count)
    TextView tvLessSkuCount;
    /**
     * 件数
     */
    @BindView(R.id.tv_count)
    TextView tvCount;
    /**
     * 缺货件数
     */
    @BindView(R.id.tv_less_count)
    TextView tvLessCount;
    /**
     * 包装列表
     */
    @BindView(R.id.rv_recycle)
    RecyclerView rvRecycle;
    /**
     * 适配器
     */
    private TotalHasPackAdapter adapter;
    /**
     * 数据列表
     */
    private List<PackDetailInfo> hasPackInfoList;
    /**
     * 装箱详情id
     */
    private String packBoxDetailId;
    /**
     * 确认删除
     */
    private ConformDialog conformDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pack_total;
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public void initView() {
        super.initView();
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.has_park_total);
        etSign.setHint("条形码/商品名称");
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        hasPackInfoList = new ArrayList<>();
        adapter = new TotalHasPackAdapter(hasPackInfoList);
        adapter.setOnItemClickListener(this);
        rvRecycle.setLayoutManager(new LinearLayoutManager(this));
        rvRecycle.setAdapter(adapter);
        if (mPresenter != null) {
            mPresenter.getVerifyVendorProductList();
        }
    }

    @Override
    public void onDataChange() {
        if (mPresenter != null) {
            mPresenter.getVerifyVendorProductList();
        }
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
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
    public String getKeyword() {
        return etSign.getText().toString();
    }

    @Override
    public String getPackBoxDetailId() {
        return packBoxDetailId;
    }


    @Override
    public void setHasPackInfoData(PackInfoDetailResult hasPackInfoResult) {
        tvCount.setText(String.valueOf(hasPackInfoResult.getTotalNum()));
        tvLessCount.setText(String.valueOf(hasPackInfoResult.getStockOutTotalNum()));
        tvLessSkuCount.setText(String.valueOf(hasPackInfoResult.getStockOutSkuNum()));
        tvSkuCount.setText(String.valueOf(hasPackInfoResult.getSkuNum()));
        this.hasPackInfoList.clear();
        this.hasPackInfoList.addAll(hasPackInfoResult.getPack_list());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void success() {
        if (mPresenter != null) {
            mPresenter.getVerifyVendorProductList();
        }
    }

    @Override
    public void onClick(View view, int position) {
        packBoxDetailId = String.valueOf(hasPackInfoList.get(position).getId());
        conformDialog.show();
        conformDialog.setTitle("确认删除这此条拣货商品吗？");
    }

    @Override
    public void onCallBack() {
        if (mPresenter != null) {
            mPresenter.delVerifyVendorProduct();
        }
    }
    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        if (mPresenter != null) {
            mPresenter.getVerifyVendorProductList();
        }
    }

    @OnClick(R.id.tv_search)
    public void search() {
        if (mPresenter != null) {
            mPresenter.getVerifyVendorProductList();
        }
    }
}
