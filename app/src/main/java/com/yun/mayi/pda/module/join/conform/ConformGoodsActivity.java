package com.yun.mayi.pda.module.join.conform;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.AgentInfo;
import com.yun.mayi.pda.bean.ConformGoodsResult;
import com.yun.mayi.pda.bean.PackBoxInfo;
import com.yun.mayi.pda.bean.PackDetailInfo;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.OnConformPackListener;
import com.yun.mayi.pda.utils.OnNumberChangeListener;
import com.yun.mayi.pda.utils.SoundUtils;
import com.yun.mayi.pda.widget.ClearEditText;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间： 2018/03/28
 * 名称：确认收货页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ConformGoodsActivity extends BaseMvpActivity<ConformGoodsPresenter> implements ConformGoodsContract.View, ConformDialog.OnConformCallBack, TabLayout.OnTabSelectedListener, OnNumberChangeListener, OnConformPackListener {

    /**
     * 确认收货的sku数量
     */
    @BindView(R.id.tv_sku_count)
    EditText tvSkuCount;
    /**
     * 确认收货的数量
     */
    @BindView(R.id.tv_count)
    EditText tvCount;
    /**
     * 打包箱数
     */
    @BindView(R.id.tv_box_num)
    EditText tvBoxNum;
    /**
     * 批量确认
     */
    @BindView(R.id.tv_all_conform)
    TextView tvAllConform;
    /**
     * 搜索框
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;

    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    /**
     * 平台列表显示切换
     */
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    /**
     * 页面列表
     */
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_type)
    TextView tvType;
    /**
     * 确认对话框
     */
    private ConformDialog conformDialog;
    /**
     * 数量列表
     */
    private String numList = "";
    /**
     * id列表
     */
    private String detailIdList = "";
    /**
     * 声音工具类
     */
    private SoundUtils mSoundUtils;
    /**
     * 页面适配器
     */
    private ConformGoodsPageAdapter pageAdapter;
    /**
     * 平台列表
     */
    private List<AgentInfo> agentInfoList;
    /**
     * 平台编号
     */
    private String agentNumber = "";
    private List<ConformGoodsFragment> conformGoodsFragmentList;
    /**
     * 数据列表
     */
    private List<PackDetailInfo> packDetailInfoList;
    /**
     * 装箱列表
     */
    private List<PackBoxInfo> packBoxDetailList;


    /**
     * 当前页面
     */
    private ConformGoodsFragment conformGoodsFragment;
    /**
     * 数据类型
     */
    private int type = Constants.TYPE_SINGLE;
    /**
     * 选择箱号列表
     */
    private String boxNumList = "";
    /**
     * 选择平台位置
     */
    private int position = 0;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_conform_goods;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.goods_conform_detail);
        mSoundUtils = new SoundUtils(R.raw.scan_success, R.raw.sacn_fail, R.raw.scan_mul);
        mSoundUtils.init(this);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        type = getIntent().getIntExtra("type", Constants.TYPE_SINGLE);
        tvType.setText(type == Constants.TYPE_SINGLE ? "单件" : "整件");
        conformGoodsFragmentList = new ArrayList<>();
        agentInfoList = new ArrayList<>();
        pageAdapter = new ConformGoodsPageAdapter(getSupportFragmentManager(), agentInfoList, conformGoodsFragmentList);
        viewpager.setAdapter(pageAdapter);
        tablayout.setupWithViewPager(viewpager);
        tablayout.addOnTabSelectedListener(this);
        packDetailInfoList = new ArrayList<>();
        packBoxDetailList = new ArrayList<>();
        if (mPresenter != null) {
            isScan = false;
            mPresenter.getDifferentAgentList();
        }

    }


    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public String getAgentNumber() {
        return agentNumber;
    }

    @Override
    public String getKeyword() {
        return etSign.getText().toString();
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public int getVendorId() {
        return getIntent().getIntExtra("id", 0);
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public String getStartTime() {
        return getIntent().getStringExtra("startTime");
    }

    @Override
    public String getEndTime() {
        return getIntent().getStringExtra("endTime");
    }


    @Override
    public void conformSuccess() {
        numList = "";
        detailIdList = "";
        if (mPresenter != null) {
            if (isScan) {
                mSoundUtils.success();
                isScan = false;
                etSign.setText("");
            }
            mPresenter.getConformList();
        }
    }

    @Override
    public String getPackBoxDetailIdList() {
        return detailIdList;
    }

    @Override
    public String getNumList() {
        return numList;
    }

    @Override
    public String getBoxNumList() {
        return boxNumList;
    }

    @Override
    public void setConformGoods(ConformGoodsResult result) {
        String boxNum = result.getTotalBoxNum() + "/" + result.getOriginTotalBoxNum();
        String skuCount = result.getSkuNum() + "/" + result.getOriginSkuNum();
        String count = result.getTotalNum() + "/" + result.getOriginTotalNum();
        tvBoxNum.setText(boxNum);
        tvSkuCount.setText(skuCount);
        tvCount.setText(count);
        this.packDetailInfoList.clear();
        this.packBoxDetailList.clear();
        this.packBoxDetailList.addAll(result.getBox_list());
        this.packDetailInfoList.addAll(result.getPack_list());
        if (conformGoodsFragmentList.size() > 0) {
            conformGoodsFragment = conformGoodsFragmentList.get(position);
            conformGoodsFragment.setConformGoodsList(result);
            conformGoodsFragment.setOnNumberChangeListener(this);
            conformGoodsFragment.setOnConformPackListener(this);
        }
    }

    private void initFragment(List<AgentInfo> agentInfoList) {
        conformGoodsFragmentList.clear();
        for (int i = 0; i < agentInfoList.size(); i++) {
            AgentInfo agentInfo = agentInfoList.get(i);
            ConformGoodsFragment conformGoodsFragment =
                    ConformGoodsFragment.newInstance(agentInfo.getAgentNumber(), type);
            conformGoodsFragmentList.add(conformGoodsFragment);
        }
    }

    @Override
    public void setAgentInfoList(List<AgentInfo> agentInfoList) {
        initFragment(agentInfoList);
        this.agentInfoList.clear();
        this.agentInfoList.addAll(agentInfoList);
        if (pageAdapter != null) {
            pageAdapter.notifyDataSetChanged();
        }
        if (agentInfoList.size() > 0) {
            viewpager.setVisibility(View.VISIBLE);
            tvNodata.setVisibility(View.GONE);
            agentNumber = agentInfoList.get(position).getAgentNumber();
        } else {
            viewpager.setVisibility(View.GONE);
            tvNodata.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.tv_all_conform)
    public void allConform() {
        conformDialog.show();
        conformDialog.setTitle("确认收货吗？");
        detailIdList = conformGoodsFragment.getDetailIdList();
        numList = conformGoodsFragment.getNumList();
        boxNumList = conformGoodsFragment.getBoxNumList();
    }

    @Override
    public void onCallBack() {
        if (mPresenter != null) {
            mPresenter.verifyVendorAllProduct();
        }
    }

    @OnClick(R.id.tv_search)
    public void search() {
        if (mPresenter != null) {
            isScan = false;
            mPresenter.getConformList();
        }
    }

    /**
     * 是否扫描查询
     */
    private boolean isScan = false;

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        switch (type) {
            case Constants.TYPE_TOTAL:
                mPresenter.getConformList();
                break;
            case Constants.TYPE_SINGLE:
                for (int i = 0; i < packBoxDetailList.size(); i++) {
                    PackBoxInfo packBoxInfo = packBoxDetailList.get(i);
                    if (packBoxInfo.getAgent_number().equals(getAgentNumber()) ||getAgentNumber().equals("0")) {
                        if (data.equals(packBoxInfo.getBox_id())) {
                            if (packBoxInfo.getIs_delete() == 1) {
                                isScan = false;
                                G.showToast(this, "该箱号订单已取消!");
                            }else {
                                numList = String.valueOf(packBoxInfo.getNum());
                                detailIdList = String.valueOf(packBoxInfo.getId());
                                boxNumList = String.valueOf(packBoxInfo.getBox_num());
                                isScan = true;
                            }
                            break;
                        } else {
                            isScan = false;
                            G.showToast(this, "该箱号不存在!");
                        }
                    } else {
                        isScan = false;
                        G.showToast(this, "该订单为非本地区订单!");
                    }
                }
                if (isScan) {
                    if (boxNumList.equals("1")) {
                        mPresenter.verifyVendorAllProduct();
                    } else {
                        mPresenter.getConformList();
                        mSoundUtils.query();
                    }
                } else {
                    etSign.setText("");
                    mSoundUtils.warn();
                }
                break;
        }
    }


    @Override
    public void onNumberChange(int num) {
        tvCount.setText(String.valueOf(num));
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        position = tab.getPosition();
        agentNumber = agentInfoList.get(position).getAgentNumber();
        if (mPresenter != null) {
            isScan = false;
            mPresenter.getConformList();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onConform(String detailIdList, String numList, String boxNumList) {
        this.numList = numList;
        this.detailIdList = detailIdList;
        this.boxNumList = boxNumList;
        if (mPresenter != null) {
            mPresenter.verifyVendorAllProduct();
        }
    }


}


