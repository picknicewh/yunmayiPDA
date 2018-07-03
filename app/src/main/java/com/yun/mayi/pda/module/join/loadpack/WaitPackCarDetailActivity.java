package com.yun.mayi.pda.module.join.loadpack;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PackBoxDetail2;
import com.yun.mayi.pda.bean.PickOrderDetail;
import com.yun.mayi.pda.bean.PickOrderDetailResult;
import com.yun.mayi.pda.db.BoxInfo;
import com.yun.mayi.pda.db.BoxInfoDao;
import com.yun.mayi.pda.db.OrderInfo;
import com.yun.mayi.pda.db.OrderInfoDao;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.ViewPagerAdapter;
import com.yun.mayi.pda.module.join.waitpack.WaitPackedDetailFragment;
import com.yun.mayi.pda.utils.BeanChangeUtil;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.SoundUtils;
import com.yun.mayi.pda.widget.ClearEditText;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间： 2018/05/04
 * 名称：司机拣货单详情页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackCarDetailActivity extends BaseMvpActivity<WaitPackCarDetailPresenter> implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, ConformDialog.OnConformCallBack, WaitPackCarDetailContract.View, WaitPackedDetailFragment.OnOperationCallBack {
    /**
     * 待装车
     */
    private static final int WAITPARK = 0;
    /**
     * 已装车
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
    @BindView(R.id.tv_finish)
    TextView tvFinish;
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
     * 类型，已装车/待装车
     */
    private int type;
    /**
     * 装箱打印的商品id集合
     */
    private String orderDetailIds = "";
    /**
     * 装箱打印的商品数量集合
     */
    private String nums = "";
    /**
     * 确认对话框
     */
    private ConformDialog conformDialog;
    /**
     * 是否按了返回
     */
    private boolean isBack = false;
    /**
     * 单件分拣数据库
     */
    private OrderInfoDao orderInfoDao;
    /**
     * 整件分拣数据库
     */
    private BoxInfoDao boxInfoDao;
    private String orderId;
    private SoundUtils soundUtils;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_wait_pack_car_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        soundUtils = new SoundUtils(R.raw.scan_success, R.raw.sacn_fail);
        soundUtils.init(this);
        String sign = getIntent().getStringExtra("sign");
        orderId = getIntent().getStringExtra("orderId");
        setTitleText("装车" + "-" + sign);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        rgPark.setOnCheckedChangeListener(this);
        initViewPage();
        orderInfoDao = new OrderInfoDao(this);
        boxInfoDao = new BoxInfoDao(this);
        if (mPresenter != null) {
            mPresenter.getDeliveryOrderDetail();
        }
    }

    private void initViewPage() {
        fragmentList = new ArrayList<>();
        waitParkFragment = WaitPackedDetailFragment.newInstance(WAITPARK, orderId, true);
        hasParkFragment = WaitPackedDetailFragment.newInstance(HASPARK, orderId, true);
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
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        String boxId = data;
        if (boxInfoDao.isIncludeBoxId(boxId, orderId)) {
            if (!boxInfoDao.isPack(boxId, orderId)) {
                boxInfoDao.packedBox(orderId, boxId, 1);
                soundUtils.success();
            } else {
                G.showToast(this, "已装车！");
                soundUtils.warn();
            }
        } else if (orderInfoDao.isInclude(boxId, orderId)) {
            if (!orderInfoDao.isPack(boxId, orderId)) {
                orderInfoDao.packedBox(orderId, boxId, 1);
                soundUtils.success();
            } else {
                G.showToast(this, "已装车！");
                soundUtils.warn();
            }
        } else {
            soundUtils.warn();
        }
        switch (type) {
            case WAITPARK:
                waitParkFragment.getOrderInfoList(type, "");
                break;
            case HASPARK:
                hasParkFragment.getOrderInfoList(type, "");
                break;
        }
        etSign.setText("");
    }

    @OnClick(R.id.tv_search)
    public void search() {
        switch (type) {
            case WAITPARK:
                waitParkFragment.getOrderInfoList(type, getKeyword());
                break;
            case HASPARK:
                hasParkFragment.getOrderInfoList(type, getKeyword());
                break;
        }
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
                waitParkFragment.getOrderInfoList(type, getKeyword());
                break;
            case HASPARK:
                rbWaitParking.setChecked(false);
                rbHasParking.setChecked(true);
                hasParkFragment.getOrderInfoList(type, getKeyword());
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
                type = WAITPARK;
                waitParkFragment.getOrderInfoList(type, getKeyword());
                break;
            case R.id.rb_has_parking:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                type = HASPARK;
                hasParkFragment.getOrderInfoList(type, getKeyword());
                break;
        }
        vpPark.setCurrentItem(type);
    }

    @Override
    public void onCallBack() {
        getPickPrintDatas();
        if (mPresenter != null) {
            mPresenter.agentFinishPick();
        }
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

    private void getPickPrintDatas() {
        orderDetailIds = "";
        nums = "";
        List<OrderInfo> orderInfoList = orderInfoDao.getAllPackOrderInfoList(orderId);
        List<BoxInfo> boxInfoList = boxInfoDao.getAllPackOrderInfoList(orderId);
        for (int i = 0; i < orderInfoList.size(); i++) {
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
        }
        for (int i = 0; i < boxInfoList.size(); i++) {
            BoxInfo boxInfo = boxInfoList.get(i);
            String id = boxInfo.getOrderDetailId();
            int num = boxInfo.getNum();
            if (i == boxInfoList.size() - 1) {
                orderDetailIds = orderDetailIds + id;
                nums = nums + num;
            } else {
                orderDetailIds = orderDetailIds + id + ",";
                nums = nums + num + ",";
            }
        }
    }

    @Override
    public void setPickOrderDetailResult(PickOrderDetailResult pickOrderDetailResult) {
        // orderInfoDao.deleteAllPacked();
        //  boxInfoDao.deleteAllPacked();
        List<PickOrderDetail> pickOrderDetails = pickOrderDetailResult.getDetail_list();
        List<PackBoxDetail2> packBoxDetailList = pickOrderDetailResult.getBox_list();
        if (packBoxDetailList != null && pickOrderDetails.size() > 0) {
            for (int i = 0; i < pickOrderDetails.size(); i++) {
                PickOrderDetail orderDetail = pickOrderDetails.get(i);
                OrderInfo orderInfo = BeanChangeUtil.pickOrderDetail2orderInfo(orderDetail, 1);
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
    public void onSuccess() {
        orderInfoDao.deleteAllPacked();
        boxInfoDao.deleteAllPacked();
        hasParkFragment.getOrderInfoList(Constants.HAS_PACK, getKeyword());
        waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, getKeyword());
        tvSkuCount.setText(String.valueOf(0));
        tvCount.setText(String.valueOf(0));
        tvBoxCount.setText(String.valueOf(0));
        finish();
    }

    @Override
    public void back() {
     /*   if (orderInfoDao.getPackedNum(orderId) > 0 || boxInfoDao.getPackedNum(orderId) > 0) {
            conformDialog.show();
            conformDialog.setTitle("你还有已分拣数据未装箱打印，你确定要退出吗？");
            isBack = true;
        } else {

        }*/
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
     /*   if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (orderInfoDao.getPackedNum(orderId) > 0 || boxInfoDao.getPackedNum(orderId) > 0) {
                conformDialog.show();
                conformDialog.setTitle("你确定要退出吗？");
                isBack = true;
            }
        }*/
        return true;
    }

    @Override
    public void callBack(int packedNum, int boxNum, int count) {
        //  tvSkuCount.setText(String.valueOf(packedNum));
        tvCount.setText(String.valueOf(count));
        tvBoxCount.setText(String.valueOf(boxNum));
    }


    @OnClick(R.id.tv_finish)
    public void pickFinish() {
        conformDialog.show();
        conformDialog.setTitle("确定装车吗？");
    }
}
