package com.yun.mayi.pda.module.join.reject;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.RejectVo;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.widget.ClearEditText;
import com.yun.mayi.pda.widget.RejectCheckDialog;
import com.yun.mayi.pda.widget.RejectGoodsDetailDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 作者： wh
 * 时间： 2018/03/16
 * 名称：退货审核页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class RejectCheckDetailActivity extends BaseMvpActivity<RejectCheckDetailPresenter> implements RejectCheckDetailContract.View, RejectCheckDialog.RemarkCallBack, RejectCheckDetailAdapter.OnCheckChangeListener, RadioGroup.OnCheckedChangeListener, RejectCheckDetailAdapter.OnTitleClickListener,PullToRefreshBase.OnRefreshListener2<RecyclerView> {
    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 已经选中的个数
     */
    @BindView(R.id.tv_has_check)
    TextView tvHasCheck;
    /**
     * 列表
     */
    @BindView(R.id.rv_reject)
    PullToRefreshRecyclerView prv;
    RecyclerView rvReject;
    /**
     * 分拣选择
     */
    @BindView(R.id.rg_check)
    RadioGroup rgCheck;
    /**
     * 下划线1
     */
    @BindView(R.id.line1)
    TextView line1;
    /**
     * 下划线2
     */
    @BindView(R.id.line2)
    TextView line2;
    /**
     * 下划线3
     */
    @BindView(R.id.line3)
    TextView line3;
    /**
     * 没有数据
     */
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.ll_check)
    RelativeLayout llCheck;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 选中的状态，0全部，3未审核，4已打回，5成功退货
     */
    private int state = 3;
    /**
     * 页数
     */
    private int page = 1;
    /**
     * 退货列表
     */
    private List<RejectVo> rejectVoList;
    /**
     * 业务员id
     */
    private int saleManId = 0;
    /**
     * 退货商品id
     */
    private String idStr;
    /**
     * 退货数量
     */
    private String numStr;
    /**
     * 退货金额，用英文逗号','隔开
     */
    private String feeStr;
    /**
     * 备注对话框
     */
    private RejectCheckDialog rejectCheckDialog;
    /**
     * 备注
     */
    private String remark = "";
    /**
     * 适配器
     */
    private RejectCheckDetailAdapter adapter;
    /**
     * 退货商品详情对话框
     */
    private RejectGoodsDetailDialog detailDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_reject_check_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.reject_check_detail);
        saleManId = getIntent().getIntExtra("id", 0);
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        rejectCheckDialog = new RejectCheckDialog(this);
        rejectCheckDialog.setRemarkCallBack(this);
        etSign.setHint("名称/条码/小店编号");
        rvReject = prv.getRefreshableView();
        prv.setMode(PullToRefreshBase.Mode.BOTH);
        prv.setOnRefreshListener(this);
        rgCheck.setOnCheckedChangeListener(this);
        detailDialog = new RejectGoodsDetailDialog(this);
        rejectVoList = new ArrayList<>();
        adapter = new RejectCheckDetailAdapter(rejectVoList, state);
        adapter.setOnTitleClickListener(this);
        rvReject.setLayoutManager(new LinearLayoutManager(this));
        rvReject.setAdapter(adapter);
        adapter.setOnCheckChangeListener(this);
        if (NetworkUtil.isNetworkAvailable()) {
            if (mPresenter != null) {
                mPresenter.getRejectCheckList();
            }
        }
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        if (mPresenter != null) {
            mPresenter.getRejectCheckList();
        }
    }

    @Override
    public String getBeginTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public int getSalesmanId() {
        return saleManId;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public String getKeyWord() {
        return etSign.getText().toString();
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public String getIdStr() {
        return idStr;
    }

    @Override
    public String getNumStr() {
        return numStr;
    }

    @Override
    public String getFeeStr() {
        return feeStr;
    }
   private boolean hasData;
    @Override
    public void setRejectVoList(List<RejectVo> rejectVoList) {
        if (page==1 ){
            tvNodata.setVisibility(rejectVoList.size() == 0 ? View.VISIBLE : View.GONE);
            prv.setVisibility(rejectVoList.size() == 0 ? View.GONE : View.VISIBLE);
            this.rejectVoList.clear();
            G.log("xxx"+"刷新数据啦！！！！！");
        }
        hasData=rejectVoList.size()==20;
        this.rejectVoList.addAll(rejectVoList);
        if (adapter != null) {
            adapter.initData(state);
        }
        int count = G.isEmteny(tvHasCheck.getText().toString()) ? 0 : Integer.parseInt(tvHasCheck.getText().toString());
        if (count > 0) {
            count = count - 1;
            tvHasCheck.setText(String.valueOf(count));
        }
    }

    @Override
    public void showMessage(String message) {
        super.showMessage(message);
        G.showToast(this, message);
        mPresenter.getRejectCheckList();
    }

    @OnClick(R.id.tv_search)
    public void search() {
        mPresenter.getRejectCheckList();
    }

    @Override
    public void onCallBack(String remark, int type) {
        this.remark = remark;
        getData();
        if (type == 0) {
            mPresenter.rejectWareHouse();
        } else {
            mPresenter.passWareHouse();
        }
    }

    @Override
    public void onChecked(Map<Integer, Boolean> selectList, Map<Integer, Integer> stockList, Map<Integer, Double> priceList) {
        int count = 0;
        numStr = "";
        idStr = "";
        feeStr = "";
        for (int i = 0; i < rejectVoList.size(); i++) {
            RejectVo rejectVo = rejectVoList.get(i);
            if (selectList.get(i)) {
                count++;
                numStr = numStr + stockList.get(i) + ",";
                idStr = idStr + rejectVo.getId() + ",";
                feeStr = feeStr + priceList.get(i) + ",";
            }
        }
        if (!G.isEmteny(numStr)) {
            numStr = numStr.substring(0, numStr.lastIndexOf(","));
            idStr = idStr.substring(0, idStr.lastIndexOf(","));
            feeStr = feeStr.substring(0, feeStr.lastIndexOf(","));
        }
        G.log("xxxxx===numStr==" + numStr);
        G.log("xxxxx===idStr==" + idStr);
        G.log("xxxxx===feeStr==" + feeStr);
        tvHasCheck.setText(String.valueOf(count));
    }


    public void getData() {
        numStr = "";
        idStr = "";
        feeStr = "";
        for (int i = 0; i < adapter.getSelectList().size(); i++) {
            RejectVo rejectVo = rejectVoList.get(i);
            if (adapter.getSelectList().get(i)) {
                idStr = idStr + rejectVo.getId() + ",";
                numStr = numStr + adapter.getStockList().get(i) + ",";
                feeStr = feeStr + adapter.getPriceList().get(i) + ",";
            }
        }
        if (!G.isEmteny(numStr)) {
            numStr = numStr.substring(0, numStr.lastIndexOf(","));
            idStr = idStr.substring(0, idStr.lastIndexOf(","));
            feeStr = feeStr.substring(0, feeStr.lastIndexOf(","));
        }
    }

    @OnClick(R.id.tv_check)
    public void check() {
        rejectCheckDialog.show();
        rejectCheckDialog.setType(1);
    }

    @OnClick(R.id.tv_back)
    public void checkBack() {
        rejectCheckDialog.show();
        rejectCheckDialog.setType(0);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rb_un_check:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                line3.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                state = Constants.STATE_REJECT_UN_HECK;
                llCheck.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_back:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                line3.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                state = Constants.STATR_REJECT_BACK;
                llCheck.setVisibility(View.GONE);
                break;
            case R.id.rb_ha_check:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                line3.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                state = Constants.STATE_REJECT_CHECK_SUCCESS;
                llCheck.setVisibility(View.GONE);
                break;
        }
        page=1;
        mPresenter.getRejectCheckList();
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public void onTitleClick(int position) {
        detailDialog.show();
        detailDialog.setRejectInfo(rejectVoList.get(position));
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        refreshView.onRefreshComplete();
        prv.setMode(PullToRefreshBase.Mode.BOTH);
        page=1;
        mPresenter.getRejectCheckList();

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
        refreshView.onRefreshComplete();
        page++;
        mPresenter.getRejectCheckList();
        if (!hasData){
            prv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            G.showToast(this,"没有更多数据！");
        }
    }
}