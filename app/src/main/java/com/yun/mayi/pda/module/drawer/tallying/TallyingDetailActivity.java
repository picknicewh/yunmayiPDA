package com.yun.mayi.pda.module.drawer.tallying;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.bean.PickOrderDetailResult;
import com.yun.mayi.pda.bean.PickPrintData;
import com.yun.mayi.pda.bean.PickPrintDataResult;
import com.yun.mayi.pda.bean.TallyingInfoDetail;
import com.yun.mayi.pda.db.TallyingInfoDetailDao;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.ViewPagerAdapter;
import com.yun.mayi.pda.module.home.setting.SettingBluetoothActivity;
import com.yun.mayi.pda.utils.BeanChangeUtil;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.utils.PrintUtils;
import com.yun.mayi.pda.widget.ClearEditText;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/05/25
 * 名称：司机点货详情页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TallyingDetailActivity extends BaseMvpActivity<TallyingDetailPresenter> implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, TallyingDetailContract.View, TallyingDetailFragment.OnOperationCallBack, ConformDialog.OnConformCallBack {
    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 待点货
     */
    @BindView(R.id.rb_wait_parking)
    RadioButton rbWaitParking;
    @BindView(R.id.line1)
    TextView line1;
    /**
     * 已点货
     */
    @BindView(R.id.rb_has_parking)
    RadioButton rbHasParking;
    /**
     * 已点货下划线
     */
    @BindView(R.id.line2)
    TextView line2;
    /**
     * 点货
     */
    @BindView(R.id.rg_park)
    RadioGroup rgPark;
    /**
     * 待拣货/已拣货页面
     */
    @BindView(R.id.vp_park)
    ViewPager vpPark;
    /**
     * 件数
     */
    @BindView(R.id.tv_count)
    EditText tvCount;
    /**
     * 箱数
     */
    @BindView(R.id.tv_box_count)
    EditText tvBoxCount;
    /**
     * 页面列表
     */
    private List<BaseFragment> fragmentList;
    /**
     * 适配器
     */
    private ViewPagerAdapter adapter;
    /**
     * 未点货
     */
    private TallyingDetailFragment waitFragment;
    /**
     * 已点货
     */
    private TallyingDetailFragment hasFragment;
    /**
     * 类型/已点货，未点货
     */
    private int type = Constants.WAIT_PACK;
    /**
     * 订单id
     */
    private String orderId;

    private List<TallyingInfoDetail> pickInfoDetailList;

    private TallyingInfoDetailDao tallyingInfoDao;

    /**
     * 装箱打印的商品id集合
     */
    private String orderDetailIds = "";
    /**
     * 装箱打印的商品数量集合
     */
    private String nums = "";
    /**
     * 待点货单
     */
    private PickOrder pickOrder;
    /**
     * 打印数据
     */
    private PickPrintDataResult pickPrintDataResult;
    /**
     * 总缺货金额
     */
    private double totalLessMoney = 0;
    /**
     * 确认对话框
     */
    private ConformDialog conformDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_tallying_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.wait_tallying_detail);
        pickOrder = (PickOrder) getIntent().getSerializableExtra("pickOrder");
        orderId = pickOrder.getOrder_id();
        pickPrintDataResult = new PickPrintDataResult();
        pickPrintDataResult.setPickOrder(pickOrder);
        rgPark.setOnCheckedChangeListener(this);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        tallyingInfoDao = new TallyingInfoDetailDao(this);
        pickInfoDetailList = new ArrayList<>();
        initViewPager();
        if (mPresenter != null) {
            mPresenter.getDeliveryOrderDetail();
        }
    }

    private void initViewPager() {
        fragmentList = new ArrayList<>();
        waitFragment = TallyingDetailFragment.getInstance(Constants.WAIT_PACK, orderId, getIsFirst());
        hasFragment = TallyingDetailFragment.getInstance(Constants.HAS_PACK, orderId, getIsFirst());
        fragmentList.add(waitFragment);
        fragmentList.add(hasFragment);
        waitFragment.setOnOperationCallBack(this);
        hasFragment.setOnOperationCallBack(this);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpPark.setAdapter(adapter);
        vpPark.setCurrentItem(type);
        vpPark.addOnPageChangeListener(this);
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }


    @OnClick(R.id.tv_search)
    public void search() {
        switch (type) {
            case Constants.WAIT_PACK:
                waitFragment.getTallyingInfoList(type, getKeyword());
                break;
            case Constants.HAS_PACK:
                hasFragment.getTallyingInfoList(type, getKeyword());
                break;
        }
    }

    @OnClick(R.id.tv_finish)
    public void finishPick() {
        conformDialog.show();
        conformDialog.setTitle("确认点货完成并打印吗？");
    }

    private List<PickPrintData> getPickPrintDatas() {
        totalLessMoney = 0;
        List<PickPrintData> pickPrintDataList = new ArrayList<>();
        List<TallyingInfoDetail> tallyingInfoDetails = tallyingInfoDao.getAllPackOrderInfoList(orderId);
        for (int i = 0; i < pickInfoDetailList.size(); i++) {
            TallyingInfoDetail infoDetail = tallyingInfoDetails.get(i);
            PickPrintData pickPrintData = BeanChangeUtil.TallyingInfoDetail2PickPrintData(infoDetail,getIsFirst());
            String id = infoDetail.getId();
            int num = 0;
            if (getIsFirst() == 0) {//是否再次修改，如果再次修改修改的是配送数量，不是装货数量
                num = infoDetail.getLoadingQuantity();
            } else {
                num = infoDetail.getDeliverQuantity();
            }
            if (pickInfoDetailList.size() - 1 == i) {
                orderDetailIds = orderDetailIds + id;
                nums = nums + num;
            } else {
                orderDetailIds = orderDetailIds + id + ",";
                nums = nums + num + ",";
            }
            totalLessMoney = totalLessMoney + pickPrintData.getLessMoney();
            pickPrintDataList.add(pickPrintData);
        }
        pickPrintDataResult.setTotalLessPrice(totalLessMoney / 100);
        pickPrintDataResult.setRealTotalPrice(pickPrintDataResult.getOriginTotalPrice() - totalLessMoney / 100);
        return pickPrintDataList;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case Constants.WAIT_PACK:
                rbHasParking.setChecked(false);
                rbWaitParking.setChecked(true);
                waitFragment.getTallyingInfoList(Constants.WAIT_PACK, getKeyword());
                break;
            case Constants.HAS_PACK:
                rbWaitParking.setChecked(false);
                rbHasParking.setChecked(true);
                hasFragment.getTallyingInfoList(Constants.HAS_PACK, getKeyword());
                break;
        }
        type = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rb_wait_parking:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                type = Constants.WAIT_PACK;
                waitFragment.getTallyingInfoList(Constants.WAIT_PACK, getKeyword());
                break;
            case R.id.rb_has_parking:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                type = Constants.HAS_PACK;
                hasFragment.getTallyingInfoList(Constants.HAS_PACK, getKeyword());
                break;
        }
        vpPark.setCurrentItem(type);
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        search();
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getOrderId() {
        return orderId;
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
    public String getOrderDetailIds() {
        return orderDetailIds;
    }

    @Override
    public String getNums() {
        return nums;
    }

    @Override
    public int getIsPick() {
        return getIntent().getIntExtra("isPick", 2);
    }

    @Override
    public int getIsFirst() {
        return getIntent().getIntExtra("isFirst", 0);
    }

    @Override
    public void onSuccess() {
        tallyingInfoDao.deleteAllPacked();
        hasFragment.getTallyingInfoList(Constants.HAS_PACK, getKeyword());
        waitFragment.getTallyingInfoList(Constants.WAIT_PACK, getKeyword());
        tvCount.setText(String.valueOf(0));
        tvBoxCount.setText(String.valueOf(0));
        PrintUtils.drawerPack(pickPrintDataResult);
    }

    @Override
    public void setPickOrderDetailResult(PickOrderDetailResult pickOrderDetailResult) {
        //     tallyingInfoDao.deleteAll();
        pickPrintDataResult.setOriginTotalPrice(pickOrderDetailResult.getOriginTotalPrice());
        pickPrintDataResult.setRealTotalPrice(pickOrderDetailResult.getRealTotalPrice());
        this.pickInfoDetailList.clear();
        this.pickInfoDetailList.addAll(pickOrderDetailResult.getList());
        for (int i = 0; i < pickInfoDetailList.size(); i++) {
            TallyingInfoDetail detail = pickInfoDetailList.get(i);
            if (!tallyingInfoDao.isInclude(String.valueOf(detail.getId()), detail.getOrderId())) {
                tallyingInfoDao.save(detail);
            }
        }
        int boxNum = tallyingInfoDao.getBoxNum(orderId);
        int packCount = tallyingInfoDao.getTotalCount(orderId);
        tvBoxCount.setText(String.valueOf(boxNum));
        tvCount.setText(String.valueOf(packCount));
        waitFragment.getTallyingInfoList(Constants.WAIT_PACK, getKeyword());
        hasFragment.getTallyingInfoList(Constants.HAS_PACK, getKeyword());
        etSign.setText("");
    }

    @Override
    public void callBack(int boxNum, int count) {
        tvBoxCount.setText(String.valueOf(boxNum));
        tvCount.setText(String.valueOf(count));
    }

    @Override
    public void onCallBack() {
        nums = "";
        orderDetailIds = "";
        pickPrintDataList = getPickPrintDatas();
        pickPrintDataResult.setPickPrintDataList(pickPrintDataList);
        packBox();
    }

    private List<PickPrintData> pickPrintDataList;

    private boolean packBox() {
        boolean packed;
        BluetoothSocket socket = YunmayiApplication.getBluetoothSocket();
        if (socket == null) {
            G.showToast(this, "请连接蓝牙打印机！");
            Intent intent = new Intent(this, SettingBluetoothActivity.class);
            startActivity(intent);
            packed = false;
        } else {
            if (NetworkUtil.isNetworkAvailable()) {
                mPresenter.agentFinishPick();
                packed = true;
            } else {
                G.showToast(this, "网络异常！");
                packed = false;
            }

        }
        return packed;
    }
}
