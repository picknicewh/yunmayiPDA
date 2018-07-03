package com.yun.mayi.pda.module.join.waitpack;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PackBoxDetail2;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.bean.PickOrderDetail;
import com.yun.mayi.pda.bean.PickOrderDetailResult;
import com.yun.mayi.pda.bean.PickPrintData;
import com.yun.mayi.pda.bean.PickPrintDataResult;
import com.yun.mayi.pda.db.BoxInfo;
import com.yun.mayi.pda.db.BoxInfoDao;
import com.yun.mayi.pda.db.OrderInfo;
import com.yun.mayi.pda.db.OrderInfoDao;
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
 * 时间：  2018/3/30
 * 名称：待分拣单详情页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackedDetailActivity extends BaseMvpActivity<WaitPackedDetailPresenter> implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, WaitPackedDetailContract.View, WaitPackedDetailFragment.OnOperationCallBack, ConformDialog.OnConformCallBack {
    /**
     * 待分拣
     */
    private static final int WAITPARK = 0;
    /**
     * 已分拣
     */
    private static final int HASPARK = 1;

    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * sku总数
     */
    @BindView(R.id.tv_sku_count)
    EditText tvSkuCount;
    /**
     * 总件数
     */
    @BindView(R.id.tv_count)
    EditText tvCount;
    /**
     * 总箱数
     */
    @BindView(R.id.tv_box_count)
    EditText tvBoxCount;
    /**
     * 打印
     */
    @BindView(R.id.tv_print)
    TextView tvPrint;
    /**
     * 分拣选择
     */
    @BindView(R.id.rg_park)
    RadioGroup rgPark;
    /**
     * 待分拣
     */
    @BindView(R.id.rb_wait_parking)
    RadioButton rbWaitParking;
    /**
     * 已分拣
     */
    @BindView(R.id.rb_has_parking)
    RadioButton rbHasParking;
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
     * 分拣页面
     */
    @BindView(R.id.vp_park)
    ViewPager vpPark;
    /**
     * 页面列表
     */
    private List<BaseFragment> fragmentList;
    /**
     * 适配器
     */
    private ViewPagerAdapter adapter;
    /**
     * 待分拣
     */
    private WaitPackedDetailFragment waitParkFragment;
    /**
     * 已分拣
     */
    private WaitPackedDetailFragment hasParkFragment;
    /**
     * 当前页面
     */
    private int currentPage = 0;
    /**
     * 订单id
     */
    private String orderId;

    /**
     * 类型，已分拣/待分拣
     */
    private int type;
    /**
     * 单件分拣数据库
     */
    private OrderInfoDao orderInfoDao;
    /**
     * 整件分拣数据库
     */
    private BoxInfoDao boxInfoDao;
    /**
     * 确认对话框
     */
    private ConformDialog conformDialog;
    /**
     * 装箱打印的商品id集合
     */
    private String orderDetailIds = "";
    /**
     * 装箱打印的商品数量集合
     */
    private String nums = "";
    /**
     * 是否按了返回
     */
    private boolean isBack = false;
    /**
     * 待拣货单
     */
    private PickOrder pickOrder;

    private PickPrintDataResult pickPrintDataResult;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_wait_packed_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        //  setTitleTextId(R.string.has_packing_order_detail);
        pickOrder = (PickOrder) getIntent().getSerializableExtra("pickOrder");
        pickPrintDataResult = new PickPrintDataResult();
        pickPrintDataResult.setPickOrder(pickOrder);
        setTitleText("分拣单 - " + pickOrder.getMark());
        orderInfoDao = new OrderInfoDao(this);
        boxInfoDao = new BoxInfoDao(this);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        orderId = pickOrder.getOrder_id();
        rgPark.setOnCheckedChangeListener(this);
        pickPrintDataList = new ArrayList<>();
        initViewPage();
        if (mPresenter != null) {
            mPresenter.getPickOrderDetail();
        }

    }

    @Override
    public void back() {
        if (orderInfoDao.getPackedNum(orderId) > 0 || boxInfoDao.getPackedNum(orderId) > 0) {
            conformDialog.show();
            conformDialog.setTitle("你还有已分拣数据未装箱打印，你确定要退出吗？");
            isBack = true;
        } else {
            finish();
        }
    }

    private void initViewPage() {
        fragmentList = new ArrayList<>();
        waitParkFragment = WaitPackedDetailFragment.newInstance(WAITPARK, orderId, false);
        hasParkFragment = WaitPackedDetailFragment.newInstance(HASPARK, orderId, false);
        fragmentList.add(waitParkFragment);
        fragmentList.add(hasParkFragment);
        waitParkFragment.setOnOperationCallBack(this);
        hasParkFragment.setOnOperationCallBack(this);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpPark.setAdapter(adapter);
        vpPark.setCurrentItem(0);
        vpPark.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case WAITPARK:
                rbHasParking.setChecked(false);
                rbWaitParking.setChecked(true);
                type = WAITPARK;
                waitParkFragment.getOrderInfoList(type, getKeyword());
                break;
            case HASPARK:
                rbWaitParking.setChecked(false);
                rbHasParking.setChecked(true);
                type = HASPARK;
                hasParkFragment.getOrderInfoList(type, getKeyword());
                break;
        }
        currentPage = position;
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
                currentPage = WAITPARK;
                waitParkFragment.getOrderInfoList(type, getKeyword());
                break;
            case R.id.rb_has_parking:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                currentPage = HASPARK;
                hasParkFragment.getOrderInfoList(type, getKeyword());
                break;
        }
        vpPark.setCurrentItem(currentPage);
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
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getOrderId() {
        return orderId;
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
        return getIntent().getIntExtra("isPick", 0);
    }

    @Override
    public void setPickOrderDetailResult(PickOrderDetailResult pickOrderDetailResult) {
        orderInfoDao.deleteAll();
        boxInfoDao.deleteAll();
        pickPrintDataResult.setOriginTotalPrice(pickOrderDetailResult.getOriginTotalPrice());
        pickPrintDataResult.setRealTotalPrice(pickOrderDetailResult.getRealTotalPrice());
        List<PickOrderDetail> pickOrderDetails = pickOrderDetailResult.getDetail_list();
        List<PackBoxDetail2> packBoxDetailList = pickOrderDetailResult.getBox_list();
        if (packBoxDetailList != null && pickOrderDetails.size() > 0) {
            for (int i = 0; i < pickOrderDetails.size(); i++) {
                PickOrderDetail orderDetail = pickOrderDetails.get(i);
                OrderInfo orderInfo = BeanChangeUtil.pickOrderDetail2orderInfo(orderDetail, getIsPick());
                if (!orderInfoDao.isInclude(String.valueOf(orderDetail.getId()), orderDetail.getOrder_id()))
                    orderInfoDao.save(orderInfo);
            }
        }
        if (packBoxDetailList != null && packBoxDetailList.size() > 0) {
            for (int i = 0; i < packBoxDetailList.size(); i++) {
                PackBoxDetail2 packBoxDetail = packBoxDetailList.get(i);
                BoxInfo boxInfo = BeanChangeUtil.packBoxDetail2boxInfo(packBoxDetail);
                if (!boxInfoDao.isInclude(boxInfo.getId(), boxInfo.getOrderId()))
                    boxInfoDao.save(boxInfo);
            }
        }
        int boxNum = boxInfoDao.getPackedNum(orderId);
        int packCount = boxInfoDao.getPackedCount(orderId) + orderInfoDao.getPackedCount(orderId);
        tvSkuCount.setText(String.valueOf(pickOrderDetails.size() + packBoxDetailList.size()));
        tvBoxCount.setText(String.valueOf(boxNum));
        tvCount.setText(String.valueOf(packCount));
        waitParkFragment.getOrderInfoList(type, getKeyword());
        hasParkFragment.getOrderInfoList(type, getKeyword());
        etSign.setText("");
    }

    @Override
    public void pickSuccess() {
        orderInfoDao.deleteAllPacked();
        boxInfoDao.deleteAllPacked();
        hasParkFragment.getOrderInfoList(Constants.HAS_PACK, getKeyword());
        waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, getKeyword());
        tvSkuCount.setText(String.valueOf(0));
        tvCount.setText(String.valueOf(0));
        tvBoxCount.setText(String.valueOf(0));
        PrintUtils.printPack(pickPrintDataResult);
        finish();

    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        if (mPresenter != null) {
            mPresenter.getPickOrderDetail();
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public void callBack(int packedNum, int boxNum, int count) {
        //  tvSkuCount.setText(String.valueOf(packedNum));
        tvCount.setText(String.valueOf(count));
        tvBoxCount.setText(String.valueOf(boxNum));
    }


    @OnClick(R.id.tv_print)
    public void print() {
        conformDialog.show();
        conformDialog.setTitle("确认打印吗？");
    }

    private List<PickPrintData> pickPrintDataList;

    @Override
    public void onCallBack() {
        if (isBack) {
            finish();
        } else {
            nums = "";
            orderDetailIds = "";
            pickPrintDataList = getPickPrintDatas();
            pickPrintDataResult.setPickPrintDataList(pickPrintDataList);
            //  PrintUtils.printPack(pickPrintDataResult, orderId);
            packBox();
        }
    }

    private double totalLessMoney = 0;

    private List<PickPrintData> getPickPrintDatas() {
        totalLessMoney = 0;
        List<OrderInfo> orderInfoList = orderInfoDao.getAllPackOrderInfoList(orderId);
        List<BoxInfo> boxInfoList = boxInfoDao.getAllPackOrderInfoList(orderId);
        List<PickPrintData> pickPrintDataList = new ArrayList<>();
        String firstOrderId = orderInfoList.size() > 0 ? orderInfoList.get(0).getId() : "";
        String firstBoxId = boxInfoList.size() > 0 ? boxInfoList.get(0).getId() : "";
        if (firstOrderId.compareTo(firstBoxId) > 0) {
            for (int i = 0; i < orderInfoList.size(); i++) {
                OrderInfo orderInfo = orderInfoList.get(i);
                PickPrintData pickPrintData = BeanChangeUtil.orderInfo2PickPrintData(orderInfo);
                pickPrintDataList.add(pickPrintData);
                String id = orderInfoList.get(i).getId();
                int num = orderInfoList.get(i).getNum();
                if (boxInfoList.size() > 0) {
                    orderDetailIds = orderDetailIds + id + ",";
                    nums = nums + num + ",";
                } else {
                    if (orderInfoList.size() - 1 == i) {
                        orderDetailIds = orderDetailIds + id;
                        nums = nums + num;
                    } else {
                        orderDetailIds = orderDetailIds + id + ",";
                        nums = nums + num + ",";
                    }
                }
                totalLessMoney = totalLessMoney + pickPrintData.getLessMoney();
            }
            for (int i = 0; i < boxInfoList.size(); i++) {
                BoxInfo boxInfo = boxInfoList.get(i);
                PickPrintData pickPrintData = BeanChangeUtil.boxInfoInfo2PickPrintData(boxInfo);
                pickPrintDataList.add(pickPrintData);
                String id = boxInfo.getOrderDetailId();
                int num = boxInfo.getNum();
                if (i == boxInfoList.size() - 1) {
                    orderDetailIds = orderDetailIds + id;
                    nums = nums + num;
                } else {
                    orderDetailIds = orderDetailIds + id + ",";
                    nums = nums + num + ",";
                }
                totalLessMoney = totalLessMoney + pickPrintData.getLessMoney();
            }
            G.log("xxxxxxxxxxxxxxxx" + "小于");
        } else {
            G.log("xxxxxxxxxxxxxxxx" + "大于");
            for (int i = 0; i < boxInfoList.size(); i++) {
                BoxInfo boxInfo = boxInfoList.get(i);
                PickPrintData pickPrintData = BeanChangeUtil.boxInfoInfo2PickPrintData(boxInfo);
                pickPrintDataList.add(pickPrintData);
                String id = boxInfo.getOrderDetailId();
                int num = boxInfo.getNum();
                if (orderInfoList.size() > 0) {
                    orderDetailIds = orderDetailIds + id + ",";
                    nums = nums + num + ",";
                } else {
                    if (i == boxInfoList.size() - 1) {
                        orderDetailIds = orderDetailIds + id;
                        nums = nums + num;
                    } else {
                        orderDetailIds = orderDetailIds + id + ",";
                        nums = nums + num + ",";
                    }
                }
                totalLessMoney = totalLessMoney + pickPrintData.getLessMoney();
            }
            for (int i = 0; i < orderInfoList.size(); i++) {
                OrderInfo orderInfo = orderInfoList.get(i);
                PickPrintData pickPrintData = BeanChangeUtil.orderInfo2PickPrintData(orderInfo);
                pickPrintDataList.add(pickPrintData);
                String id = orderInfoList.get(i).getId();
                int num = orderInfoList.get(i).getNum();
                if (orderInfoList.size() - 1 == i) {
                    orderDetailIds = orderDetailIds + id;
                    nums = nums + num;
                } else {
                    orderDetailIds = orderDetailIds + id + ",";
                    nums = nums + num + ",";
                }
                totalLessMoney = totalLessMoney + pickPrintData.getLessMoney();
            }
        }
        pickPrintDataResult.setTotalLessPrice(totalLessMoney);
        pickPrintDataResult.setRealTotalPrice(pickPrintDataResult.getOriginTotalPrice() - totalLessMoney);
        return pickPrintDataList;
    }

    private boolean packBox() {
        boolean packed;
        BluetoothSocket socket = YunmayiApplication.getBluetoothSocket();
        if (socket == null) {
            G.log("xxxxxxx" + "等于空");
            G.showToast(this, "请连接蓝牙打印机！");
            Intent intent = new Intent(this, SettingBluetoothActivity.class);
            startActivity(intent);
            packed = false;
        } else {
            G.log("xxxxxxx" + "不等于空");
            if (NetworkUtil.isNetworkAvailable()) {
                mPresenter.pickFinish();
                packed = true;
            } else {
                G.showToast(this, "网络异常！");
                packed = false;
            }
        }
        return packed;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (orderInfoDao.getPackedNum(orderId) > 0 || boxInfoDao.getPackedNum(orderId) > 0) {
                conformDialog.show();
                conformDialog.setTitle("你还有已分拣数据未装箱打印，你确定要退出吗？");
                isBack = true;
            }
        }
        return true;
    }

    @OnClick(R.id.tv_search)
    public void search() {
        switch (currentPage) {
            case WAITPARK:
                waitParkFragment.getOrderInfoList(type, getKeyword());
                break;
            case HASPARK:
                hasParkFragment.getOrderInfoList(type, getKeyword());
                break;
        }
    }}
