package com.yun.mayi.pda.module.join.manager;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PickDetailInfo;
import com.yun.mayi.pda.bean.PickDetailInfoResult;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.bean.PickPrintData;
import com.yun.mayi.pda.bean.PickPrintDataResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.home.setting.SettingBluetoothActivity;
import com.yun.mayi.pda.utils.BeanChangeUtil;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.utils.PriceTransfer;
import com.yun.mayi.pda.utils.PrintUtils;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 作者： wh
 * 时间： 2018/01/08
 * 名称：分拣管理详情页
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackManagerDetailActivity extends BaseMvpActivity<PackManagerDetailPresenter> implements PackManagerDetailContract.View, ConformDialog.OnConformCallBack {

    /**
     * 一维码
     */
    @BindView(R.id.iv_image)
    ImageView ivImage;
    /**
     * 打印
     */
    @BindView(R.id.tv_print)
    TextView tvPrint;
    /**
     * 总价
     */
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    /**
     * 缺货金额
     */
    @BindView(R.id.tv_less_money)
    TextView tvLessMoney;
    /**
     * 应收金额
     */
    @BindView(R.id.tv_actual_money)
    TextView tvActualMoney;
    /**
     * 打包列表
     */
    @BindView(R.id.rv_pack)
    RecyclerView rvPack;
    @BindView(R.id.tv_pick_info)
    TextView tvPickInfo;
    private PackManagerDetailAdapter adapter;
    /**
     * 订单id
     */
    private String orderId;
    private PickOrder pickOrder;
    /**
     * 数量列表
     */
    private List<PickDetailInfo> pickDetailInfoList;
    private PickPrintDataResult pickPrintDataResult;
    private ConformDialog conformDialog;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_pack_manager_detail;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.pack_order_detail);
        pickOrder = (PickOrder) getIntent().getSerializableExtra("pickOrder");
        orderId = pickOrder.getOrder_id();
        tvPickInfo.setText(pickOrder.getPicker_name());
        try {
            ivImage.setImageBitmap(PrintUtils.createOneDCode(orderId));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        pickPrintDataResult = new PickPrintDataResult();
        pickPrintDataResult.setPickOrder(pickOrder);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        pickDetailInfoList = new ArrayList<>();
        adapter = new PackManagerDetailAdapter(pickDetailInfoList);
        rvPack.setLayoutManager(new LinearLayoutManager(this));
        rvPack.setAdapter(adapter);
        if (mPresenter != null) {
            mPresenter.getPickOrderDetail();
        }
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }


    @Override
    public String getAgentNumber() {
        return UserMessage.getInstance().getAgentNumber();
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
    public void setPickDetailInfoResult(PickDetailInfoResult pickDetailInfoResult) {
        tvActualMoney.setText(PriceTransfer.chageMoneyToString(pickDetailInfoResult.getPayTotalPrice()));
        tvLessMoney.setText(PriceTransfer.chageMoneyToString(pickDetailInfoResult.getTotalOutPrice()));
        tvTotalMoney.setText(PriceTransfer.chageMoneyToString(pickDetailInfoResult.getOriginTotalPrice()));
        pickDetailInfoList.clear();
        this.pickDetailInfoList.addAll(pickDetailInfoResult.getList());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
        pickPrintDataResult.setTotalLessPrice(pickDetailInfoResult.getTotalOutPrice() / 100);
        pickPrintDataResult.setRealTotalPrice(pickDetailInfoResult.getPayTotalPrice() / 100);
        pickPrintDataResult.setOriginTotalPrice(pickDetailInfoResult.getOriginTotalPrice() / 100);
        List<PickPrintData> pickPrintDataList = new ArrayList<>();
        for (int i = 0; i < pickDetailInfoResult.getList().size(); i++) {
            PickDetailInfo pickDetailInfo = pickDetailInfoResult.getList().get(i);
            PickPrintData pickPrintData = BeanChangeUtil.PickDetailInfo2PickPrintData(pickDetailInfo);
            pickPrintDataList.add(pickPrintData);
        }
        pickPrintDataResult.setPickPrintDataList(pickPrintDataList);
    }


    @OnClick(R.id.tv_print)
    public void onViewClicked() {
        conformDialog.show();
        conformDialog.setTitle("确认打印此条订单吗？");
    }

    @Override
    public void onCallBack() {
        BluetoothSocket socket = YunmayiApplication.getBluetoothSocket();
        if (socket == null) {
            G.log("xxxxxxx" + "等于空");
            G.showToast(this, "请连接蓝牙打印机！");
            Intent intent = new Intent(this, SettingBluetoothActivity.class);
            startActivity(intent);
        } else {
            G.log("xxxxxxx" + "不等于空");
            if (pickPrintDataResult != null) {
                if (NetworkUtil.isNetworkAvailable())
                    PrintUtils.printPack(pickPrintDataResult);
                else G.showToast(this, "网络异常！");
            }
        }
    }
}
