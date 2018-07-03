package com.yun.mayi.pda.module.support.packing;

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
import com.yun.mayi.pda.bean.Order;
import com.yun.mayi.pda.bean.OrderDetail;
import com.yun.mayi.pda.bean.PackBox;
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
import com.yun.mayi.pda.widget.PackDetailDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/01/05
 * 名称：已拣/未拣货单详情
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackDetailActivity extends BaseMvpActivity<WaitPackDetailPresenter> implements WaitPackDetailContract.View, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, ParkFragment.OnOperationCallBack, ConformDialog.OnConformCallBack, ParkFragment.OnOutOfStockOperationCallBack, PackDetailDialog.OnRemoveCallBack, PackDetailDialog.OnAllLessClickListener {
    /**
     * 待分拣
     */
    private static final int WAITPARK = 0;
    /**
     * 已分拣
     */
    private static final int HASPARK = 1;
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
     *
     */
    @BindView(R.id.et_code_search)
    ClearEditText etCodeSearch;
    /**
     * 已经入箱个数
     */
    @BindView(R.id.tv_has_park)
    TextView tvHasPark;
    /**
     * 打印的箱数
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
     * 待分拣
     */
    private ParkFragment waitParkFragment;
    /**
     * 已分拣
     */
    private ParkFragment hasParkFragment;
    /**
     * 当前页面
     */
    private int currentPage = 0;
    /**
     * 对话框
     */
    private PackDetailDialog detailDialog;
    /**
     * 本地数据保存
     */
    private OrderInfoDao orderInfoDao;
    /**
     * 还有入箱未打印的数据存在的时候弹出确认退出对话框
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
     * 全缺的id
     */
    private String orderDetailId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 供应商id
     */
    private String vendorId;
    /**
     * 关键字
     */
    private String barCode = "";
    /**
     * 是否点击了搜索
     */
    private boolean isSearch = false;
    /**
     * 是否按了返回
     */
    private boolean isBack = false;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_wait_park_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
       //   setTitleTextId(R.string.packing_order_detail);
        barCode = etCodeSearch.getText().toString();
        orderId = getIntent().getStringExtra("orderId");
        vendorId = getIntent().getStringExtra("vendorId");
        String sign = getIntent().getStringExtra("sign");
        setTitleText("拣货详情 - "+sign);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        detailDialog = new PackDetailDialog(this);
        detailDialog.setOnRemoveCallBack(this);
        detailDialog.setOnAllLessClickListener(this);
        rgPark.setOnCheckedChangeListener(this);
        orderInfoDao = new OrderInfoDao(this);
        tvHasPark.setText(String.valueOf(orderInfoDao.getPackedNum(orderId)));
        initViewPage();
        if (NetworkUtil.isNetworkAvailable()) {
            mPresenter.getPackOrderDetail();
        }
    }

    @Override
    public void back() {
        if (orderInfoDao.getPackedNum(orderId) > 0) {
            conformDialog.show();
            conformDialog.setTitle("你还有已分拣数据未装箱打印，你确定要退出吗？");
            isBack = true;
        } else {
            finish();
        }
    }

    private void initViewPage() {
        fragmentList = new ArrayList<>();
        waitParkFragment = ParkFragment.newInstance(WAITPARK, orderId, vendorId, barCode);
        hasParkFragment = ParkFragment.newInstance(HASPARK, orderId, vendorId, barCode);
        fragmentList.add(waitParkFragment);
        fragmentList.add(hasParkFragment);
        waitParkFragment.setOnOperationCallBack(this);
        hasParkFragment.setOnOperationCallBack(this);
        waitParkFragment.setOnOutOfStockOperationCallBack(this);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpPark.setAdapter(adapter);
        vpPark.setCurrentItem(0);
        vpPark.addOnPageChangeListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        if (G.isEmteny(etCodeSearch.getText().toString())) {
            isSearch = false;
        }
        barCode = isSearch ? etCodeSearch.getText().toString() : "";
        switch (id) {
            case R.id.rb_wait_parking:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                currentPage = WAITPARK;
                waitParkFragment.getOrderInfoList(WAITPARK, barCode);
                break;
            case R.id.rb_has_parking:
                line1.setBackgroundColor(ContextCompat.getColor(this, R.color.home_title));
                line2.setBackgroundColor(ContextCompat.getColor(this, R.color.red_btn));
                currentPage = HASPARK;
                hasParkFragment.getOrderInfoList(HASPARK, barCode);
                break;
        }
        vpPark.setCurrentItem(currentPage);
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
                break;
            case HASPARK:
                rbWaitParking.setChecked(false);
                rbHasParking.setChecked(true);
                break;
        }
        currentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        G.log("ssssss" + data);
        etCodeSearch.setText(data);
        detailDialog.show();
        detailDialog.getOrderInfoList(orderId, vendorId,data);
    }

    @OnClick(R.id.tv_search)
    public void search() {
        isSearch = true;
        barCode = etCodeSearch.getText().toString();
        switch (currentPage) {
            case Constants.WAIT_PACK:
                waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, barCode);
                break;
            case Constants.HAS_PACK:
                hasParkFragment.getOrderInfoList(Constants.HAS_PACK, barCode);
                break;
        }
    }


    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getOrderId() {
        return getIntent().getStringExtra("orderId");
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
    public String getOrderDetailId() {
        return orderDetailId;
    }

    @Override
    public int getBoxNum() {
        int  boxNum = G.isEmteny(tvBoxCount.getText().toString())?0:Integer.parseInt(tvBoxCount.getText().toString());
        return boxNum;
    }

    @Override
    public void setParkOrderInfo(Order orderInfo) {
        orderInfoDao.deleteAll();
        List<OrderDetail> orderDetailList = orderInfo.getDetails();
        G.log("xxxxxxxx" + orderInfo.getDetails().size());
        for (int i = 0; i < orderDetailList.size(); i++) {
            OrderInfo info = BeanChangeUtil.orderDetail2orderInfo(orderDetailList.get(i),
                    String.valueOf(orderInfo.getVendorId()));
            if (!orderInfoDao.isInclude(info.getId(), info.getOrderId())) {
                orderInfoDao.save(info);
            }
        }
        barCode = "";
        hasParkFragment.getOrderInfoList(Constants.HAS_PACK, barCode);
        waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, barCode);
    }

    @Override
    public void packSuccess(PackBox packBox) {
        G.log("ssssss" + "装箱打印中.....");
        barCode = "";
        if (orderInfoDao.getPackedNum(orderId) == 0) {
            orderInfoDao.deleteAllPacked(0);
            waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, barCode);
        } else {
            orderInfoDao.deleteAllCheckedPacked(1);
          //  waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, barCode);
            hasParkFragment.getOrderInfoList(Constants.HAS_PACK, barCode);
        }
        tvHasPark.setText(String.valueOf(0));
        int count = G.isEmteny(tvBoxCount.getText().toString()) ? 1 : Integer.parseInt(tvBoxCount.getText().toString());
        if (packBox.getDetails().size()==1 ){
            if (packBox.getDetails().get(0).getNum()==0){
                G.log("当前只有一个拣货商品且全缺！");
                return;
            }
        }
        for (int i = 0; i < count; i++) {
            PrintUtils.printData(packBox, count, i + 1);
        }
        tvBoxCount.setText(String.valueOf(1));
    }

    @Override
    public void outofStockBack(String result) {
        showMessage(result);
        int count = orderInfoDao.deleteOrderById(orderId, orderDetailId);
        waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, barCode);
        G.log("ssss" + (count > 0 ? "删除成功！" : "删除失败"));
    }

    @Override
    public void callBack(int packedNum) {
        G.log("www" + packedNum);
        tvHasPark.setText(String.valueOf(packedNum));
    }

    //全缺操作
    @Override
    public void onOutOfStock(String orderDetailId) {
        barCode = "";
        this.orderDetailId = orderDetailId;
        int isSuccess = orderInfoDao.updateLess(orderId, orderDetailId);
        if (isSuccess > 0) {
            showMessage("操作成功");
            tvHasPark.setText(String.valueOf(orderInfoDao.getPackedNum(orderId)));
            waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, barCode);
            hasParkFragment.getOrderInfoList(Constants.HAS_PACK, barCode);
        }

    }

    //对话框全缺操作
    @Override
    public void onLessClick(String orderDetailId) {
        barCode = "";
        this.orderDetailId = orderDetailId;
        int isSuccess = orderInfoDao.updateLess(orderId, orderDetailId);
        if (isSuccess > 0) {
            showMessage("操作成功");
            tvHasPark.setText(String.valueOf(orderInfoDao.getPackedNum(orderId)));
            waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, barCode);
            hasParkFragment.getOrderInfoList(Constants.HAS_PACK, barCode);
        }
    }

    private boolean isPacked = true;

    @OnClick(R.id.tv_save_print)
    public void savePrint() {
        nums = "";
        orderDetailIds = "";
        List<OrderInfo> orderInfoList = orderInfoDao.getPackCheckedOrderInfoList(orderId, barCode);
        if (orderInfoList.size() == 0 && currentPage == WAITPARK) {
            isPacked = false;
            conformDialog.show();
            conformDialog.setTitle("是否将全部未分拣的商品装箱打印？");
            isBack = false;
            return;
        } else {

            for (int i = 0; i < orderInfoList.size(); i++) {
                String id = orderInfoList.get(i).getId();
                int num = orderInfoList.get(i).getNum();
                if (i == orderInfoList.size() - 1) {
                    orderDetailIds = orderDetailIds + id;
                    nums = nums + num;
                } else {
                    orderDetailIds = orderDetailIds + id + ",";
                    nums = nums + num + ",";
                }
            }

            G.log("xxxxx-------x"+orderInfoList.size());
            isPacked = packBox();
        }
    }

    @Override
    public void onCallBack() {
        if (!isPacked && !isBack) {
            G.log("ssssss" + "进入装箱");
            nums = "";
            orderDetailIds = "";
            List<OrderInfo> orderInfoList = orderInfoDao.getUnPackOrderInfoList(orderId, vendorId, barCode);
            for (int i = 0; i < orderInfoList.size(); i++) {
                String id = orderInfoList.get(i).getId();
                int num = orderInfoList.get(i).getNum();
                if (i == orderInfoList.size() - 1) {
                    orderDetailIds = orderDetailIds + id;
                    nums = nums + num;
                } else {
                    orderDetailIds = orderDetailIds + id + ",";
                    nums = nums + num + ",";
                }
            }
            tvHasPark.setText(String.valueOf(orderInfoList.size()));
            isPacked = packBox();
        } else {
            finish();
        }
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
                mPresenter.packBox();
                packed = true;
            }else {
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
            if (orderInfoDao.getPackedNum(orderId) > 0) {
                conformDialog.show();
                conformDialog.setTitle("你还有已分拣数据未装箱打印，你确定要退出吗？");
                isBack = true;
            }
        }
        return true;
    }


    @Override
    public void onRemoveSuccess(String id) {
        etCodeSearch.setText("");
        barCode = "";
        if (!G.isEmteny(id)){
            orderInfoDao.packedBox(orderId, id, 1);
            waitParkFragment.getOrderInfoList(Constants.WAIT_PACK, barCode);
            tvHasPark.setText(String.valueOf(orderInfoDao.getPackedNum(orderId)));
            detailDialog.dismiss();
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }


}
