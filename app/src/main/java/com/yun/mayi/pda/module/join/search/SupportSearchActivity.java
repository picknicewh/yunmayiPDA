package com.yun.mayi.pda.module.join.search;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseMvpPackActivity;
import com.yun.mayi.pda.bean.SalemanVo;
import com.yun.mayi.pda.bean.VendorInfo;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.drawer.manager.OrderManagerActivity;
import com.yun.mayi.pda.module.join.conform.ConformGoodsActivity;
import com.yun.mayi.pda.module.join.loadpack.WaitPackCarActivity;
import com.yun.mayi.pda.module.drawer.gather.GatheringActivity;
import com.yun.mayi.pda.module.join.goodspack.GoodsPackActivity;
import com.yun.mayi.pda.module.drawer.depart.DepartActivity;
import com.yun.mayi.pda.module.join.manager.PackManagerActivity;
import com.yun.mayi.pda.module.join.waitpack.WaitPackedActivity;
import com.yun.mayi.pda.module.join.reject.RejectCheckDetailActivity;
import com.yun.mayi.pda.module.drawer.tallying.TallyingActivity;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.DateUtil;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.widget.InfoChoosePopup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/3/16
 * 名称：退货审单
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class SupportSearchActivity extends BaseMvpPackActivity<SupportSearchPresenter> implements SupportSearchContract.View, InfoChoosePopup.OnDataChangeListener {

    @BindView(R.id.tv_search)
    TextView tvSearch;
    /**
     * 业务员
     */
    @BindView(R.id.tv_condition)
    TextView tvSalesman;
    /**
     * 选择
     */
    @BindView(R.id.tv_choose)
    TextView tvChoose;
    @BindView(R.id.ll_choose)
    LinearLayout llChoose;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.ll_type)
    LinearLayout llType;

    /**
     * 业务员列表
     */
    private List<String> dataList;
    /**
     * 选择类型
     */
    private OptionsPickerView mOptionsPickerView;
    /**
     * 业务员id
     */
    private int id = 0;
    /**
     * 售价列表
     */
    private List<SalemanVo> salemanVos;
    /**
     * 供应商列表
     */
    private List<VendorInfo> vendorInfoList;
    private int salePosition;
    /**
     * 来自页面
     */
    private int source;
    /**
     * 选择对话框
     */
    private InfoChoosePopup popup;
    /**
     * 数据类型
     */
    private int type = Constants.TYPE_SINGLE;
   private List<String> typeList;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_reject_check;
    }

    @Override
    public void initView() {
        super.initView();
        setSearchGone();
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        salemanVos = new ArrayList<>();
        vendorInfoList = new ArrayList<>();
        dataList = new ArrayList<>();
        source = getIntent().getIntExtra("source", 1);
        popup = new InfoChoosePopup(this);
        popup.setOnDataChangeListener(this);
        initData();
        typeList = new ArrayList<>();
        typeList.add("单件");
        typeList.add("整箱");
        mOptionsPickerView = DateUtil.getOptionPickerView("请选择类型", typeList, type, this, optionsSelectListener);
    }

    private OptionsPickerView.OnOptionsSelectListener optionsSelectListener = new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            if (typeList.size() > 0) {
                type = options1;
                tvType.setText(typeList.get(options1));
            }
        }
    };

    @OnClick(R.id.tv_type)
    public void typeSelect() {
        mOptionsPickerView.show();
    }


    private void initData() {
        switch (source) {
            case Constants.SOURE_REJECT_CHECK:
                setTitleTextId(R.string.reject_check);
                tvChoose.setText("业务员：");
                tvSalesman.setText("全部");
                if (mPresenter != null) {
                    mPresenter.getSalesmansByToken();
                }
                break;
            case Constants.SOURE_CONFORM_GOODS:
                setTitleTextId(R.string.goods_conform);
                llType.setVisibility(View.VISIBLE);
                tvType.setText("请选择类型");
                tvChoose.setText("供应商：");
                tvSalesman.setText("请选择供应商");
                if (mPresenter != null) {
                    mPresenter.getVendorInfoByAgentNumber();
                }
                break;
            case Constants.SOURE_WAIT_PACK:
                setTitleTextId(R.string.wait_packing_order);
                llChoose.setVisibility(View.GONE);
                break;
            case Constants.SOURE_GOODS_PACK:
                setTitleTextId(R.string.goods_pack);
                llChoose.setVisibility(View.GONE);
                break;
            case Constants.SOURE_MANAGER_PACK:
                setTitleTextId(R.string.packed_manager);
                llChoose.setVisibility(View.GONE);
                break;
            case Constants.SOURE_DRAWER_TALLYING:
                setTitleTextId(R.string.drawer_tallying);
                llChoose.setVisibility(View.GONE);
                break;
            case Constants.SOURE_LOAD_CAR:
                setTitleTextId(R.string.pack_car);
                llChoose.setVisibility(View.GONE);
                break;
            case Constants.SOURE_GATHERING:
                setTitleTextId(R.string.order_gather);
                llChoose.setVisibility(View.GONE);
                break;
            case Constants.SOURE_DEPART:
                setTitleTextId(R.string.drawer_depart);
                llChoose.setVisibility(View.GONE);
                break;
            case Constants.SOURE_ORDER_MANAGER:
                setTitleTextId(R.string.order_manager);
                llChoose.setVisibility(View.GONE);
                break;

        }
        YunmayiApplication.putStartDate(keyword + source, startDate);
        YunmayiApplication.putEndDate(keyword + source, endDate);
    }

    @Override
    public void onDataChangeListener(Date startDate, Date endDate) {
        super.onDataChangeListener(startDate, endDate);
        YunmayiApplication.putStartDate(keyword + source, startDate);
        YunmayiApplication.putEndDate(keyword + source, endDate);
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }


    private OptionsPickerView.OnOptionsSelectListener saleOptionsSelectListener = new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            if (dataList.size() > 0) {
                salePosition = options1;
                tvSalesman.setText(dataList.get(options1));
                switch (source) {
                    case Constants.SOURE_REJECT_CHECK:
                        if (options1 == 0) id = 0;
                        else id = salemanVos.get(options1 - 1).getUser_id();
                        break;
                    case Constants.SOURE_CONFORM_GOODS:
                        id = vendorInfoList.get(options1).getUser_id();
                        break;
                }
            }

        }
    };

    @Override
    public void onDataChange() {
    }

    @OnClick(R.id.tv_condition)
    public void saleMan(View view) {
        if (popup != null) {
            popup.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            popup.setInfoList(dataList);
        }
    }

    @OnClick(R.id.tv_search_detail)
    public void searchDetail(View view) {
        Intent intent = new Intent();
        switch (source) {
            case Constants.SOURE_REJECT_CHECK:
                intent.setClass(this, RejectCheckDetailActivity.class);
                break;
            case Constants.SOURE_CONFORM_GOODS:
                intent.setClass(this, ConformGoodsActivity.class);
                if (id == 0) {
                    G.showToast(this, "请选择供应商！");
                    return;
                }
                intent.putExtra("type",type);
                break;
            case Constants.SOURE_WAIT_PACK:
                intent.setClass(this, WaitPackedActivity.class);
                break;
            case Constants.SOURE_GOODS_PACK:
                intent.setClass(this, GoodsPackActivity.class);
                break;
            case Constants.SOURE_MANAGER_PACK:
                intent.setClass(this, PackManagerActivity.class);
                break;
            case Constants.SOURE_LOAD_CAR:
                intent.setClass(this, WaitPackCarActivity.class);
                break;
            case Constants.SOURE_DRAWER_TALLYING:
                intent.setClass(this, TallyingActivity.class);
                intent.putExtra("number","first");
                break;
            case Constants.SOURE_GATHERING:
                intent.setClass(this, GatheringActivity.class);
                break;
            case Constants.SOURE_DEPART:
                intent.setClass(this, DepartActivity.class);
                break;
            case Constants.SOURE_ORDER_MANAGER:
                intent.setClass(this, OrderManagerActivity.class);
                intent.putExtra("number","again");
                break;
        }
        intent.putExtra("id", id);
        intent.putExtra("startTime", tvStartTime.getText().toString());
        intent.putExtra("endTime", tvEndTime.getText().toString());
        startActivity(intent);
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
    public void setSaleManList(List<SalemanVo> saleManList) {
        this.dataList.clear();
        this.salemanVos.clear();
        this.salemanVos.addAll(saleManList);
        dataList.add("全部");
        for (int i = 0; i < saleManList.size(); i++) {
            dataList.add(saleManList.get(i).getName());
        }

    }

    @Override
    public void setVendorInfoList(List<VendorInfo> vendorInfoList) {
        this.dataList.clear();
        this.vendorInfoList.clear();
        this.vendorInfoList.addAll(vendorInfoList);
        for (int i = 0; i < vendorInfoList.size(); i++) {
            dataList.add(vendorInfoList.get(i).getCompany());
        }
    }

    @Override
    public void dataChange(String data) {
        tvSalesman.setText(data);
        int position = G.getPosition(dataList, data);
        G.log("xxeeexxxx" + position);
        switch (source) {
            case Constants.SOURE_REJECT_CHECK:
                if (position == 0) id = 0;
                else id = salemanVos.get(position - 1).getUser_id();
                break;
            case Constants.SOURE_CONFORM_GOODS:
                id = vendorInfoList.get(position).getUser_id();
                break;
        }
    }


}
