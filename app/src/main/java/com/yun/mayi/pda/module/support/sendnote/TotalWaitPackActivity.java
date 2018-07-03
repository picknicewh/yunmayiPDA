package com.yun.mayi.pda.module.support.sendnote;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpPackActivity;
import com.yun.mayi.pda.bean.PackNoteInfo;
import com.yun.mayi.pda.bean.PackNoteInfoResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.DateUtil;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：送货单(整件)
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TotalWaitPackActivity extends BaseMvpPackActivity<TotalWaitPackPresenter> implements OnItemClickListener, TotalWaitPackContract.View, TotalWaitPackAdapter.onAllLessClickListener, ConformDialog.OnConformCallBack, TotalWaitPackAdapter.OnNumberChangeListener {


    @BindView(R.id.rv_send_list)
    RecyclerView rvSendList;
    /**
     * 数库总数
     */
    @BindView(R.id.tv_sku_count)
    TextView tvSkuCount;
    /**
     * 总件数
     */
    @BindView(R.id.tv_count)
    TextView tvCount;
    /**
     * 没有数据
     */
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    /**
     * 适配器
     */
    private TotalWaitPackAdapter adapter;
    /**
     * 列表
     */
    private List<PackNoteInfo> packNoteInfoList;
    /**
     * 商品ID
     */
    private int pid;
    /**
     * 商品ID列表
     */
    private String pids = "";
    /**
     * 原始数量
     */
    private int originNum;
    /**
     * 原始数量列表
     */
    private String originNums = "";
    /**
     * 实际数量
     */
    private int num;
    /**
     * 实际数量列表
     */
    private String nums = "";
    /**
     * 确认对话框
     */
    private ConformDialog conformDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_total_wait_pack;
    }


    @Override
    public void initView() {
        super.initView();
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.send_note_list);
        etSign.setHint("条形码/商品名称");
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        packNoteInfoList = new ArrayList<>();
        adapter = new TotalWaitPackAdapter(packNoteInfoList);
        rvSendList.setLayoutManager(new LinearLayoutManager(this));
        rvSendList.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnAllLessClickListener(this);
        adapter.setOnNumberChangeListener(this);
        if (mPresenter != null) {
            mPresenter.getVendorOrderInfo();
        }
    }

    @Override
    public void onDataChange() {
        if (mPresenter != null) {
            mPresenter.getVendorOrderInfo();
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }


    @Override
    public void onClick(View view, int position) {
        pid = packNoteInfoList.get(position).getProduct_id();
        Map<Integer, Integer> stockList = adapter.getStockList();
        num = stockList.get(position);
        originNum = packNoteInfoList.get(position).getReal_quantity();
        conformDialog.show();
        conformDialog.setTitle("确认拣货该商品？");
        isAll = false;
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
    public int getProductId() {
        return pid;
    }

    @Override
    public String getProductIds() {
        return pids;
    }

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public String getNums() {
        return nums;
    }

    @Override
    public int getOrignNum() {
        return originNum;
    }

    @Override
    public String getOrignNums() {
        return originNums;
    }

    @Override
    public String getOrderCreateTime() {
        String date = tvEndTime.getText().toString();
        date = date.replace("16:","00:");
        return date;
    }

    @Override
    public void setPackNoteInfoData(PackNoteInfoResult packNoteInfoResult) {
        tvCount.setText(String.valueOf(packNoteInfoResult.getTotalNum()));
        tvSkuCount.setText(String.valueOf(packNoteInfoResult.getSkuNum()));
        if (packNoteInfoResult.getData()==null&&packNoteInfoResult.getData().size()==0){
            tvNodata.setVisibility( View.VISIBLE);
            rvSendList.setVisibility(View.GONE);
        }else{
            tvNodata.setVisibility( View.GONE);
            rvSendList.setVisibility(View.VISIBLE);
        }
        this.packNoteInfoList.clear();
        this.packNoteInfoList.addAll(packNoteInfoResult.getData());
        if (adapter != null) {
            adapter.initData();
        }
    }

    @Override
    public void conformSuccess() {
        if (mPresenter != null) {
            mPresenter.getVendorOrderInfo();
        }
    }

    @Override
    public void onLessClick(int position) {
        pid = packNoteInfoList.get(position).getProduct_id();
        num = 0;
        originNum = packNoteInfoList.get(position).getReal_quantity();
        conformDialog.show();
        conformDialog.setTitle("确认全缺的拣货该商品？");
        isAll = false;
    }
    public boolean timeIsRight(){
        return DateUtil.getBetweenDays(getBeginTime(),getEndTime())==1;
    }

    @OnClick(R.id.tv_all_conform)
    public void conform() {
        isAll = true;
        nums = "";
        pids = "";
        originNums = "";
        Map<Integer, Integer> stockList = adapter.getStockList();
        for (int i = 0; i < packNoteInfoList.size(); i++) {
            PackNoteInfo packNoteInfo = packNoteInfoList.get(i);
            if (i == packNoteInfoList.size() - 1) {
                nums += stockList.get(i);
                pids += packNoteInfo.getProduct_id();
                originNums += packNoteInfo.getReal_quantity();
            } else {
                nums = nums + stockList.get(i) + ",";
                pids = pids + packNoteInfo.getProduct_id() + ",";
                originNums = originNums + packNoteInfo.getReal_quantity() + ",";
            }
        }
        conformDialog.show();
        conformDialog.setTitle("确认全部的拣货所有商品？");
    }

    private boolean isAll = false;

    @Override
    public void onCallBack() {
        if (!timeIsRight()){
            G.showToast(this,"请选择正确的时间段（相隔一天）！");
            return;
        }
        if (mPresenter != null) {
            if (isAll) {
                mPresenter.verifyAllProductNumber();
            } else {
                mPresenter.verifyProductNumber();
            }
        }
    }

    @Override
    public void onNumberChange(int num) {
        tvCount.setText(String.valueOf(num));
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        if (mPresenter != null) {
            mPresenter.getVendorOrderInfo();
        }
    }

    @OnClick(R.id.tv_search)
    public void onViewClicked() {
        if (mPresenter != null) {
            mPresenter.getVendorOrderInfo();
        }
    }

}
