package com.yun.mayi.pda.module.support.packbox;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.PackBox;
import com.yun.mayi.pda.bean.PackBoxDetail;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.home.setting.SettingBluetoothActivity;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.utils.PrintUtils;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/01/05
 * 名称：打印装箱打印
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackBoxDetailActivity extends BaseMvpActivity< PackBoxDetailPresenter> implements PackBoxDetailContract.View, ConformDialog.OnConformCallBack, View.OnClickListener {
    /**
     * 装箱一维码
     */
    @BindView(R.id.iv_image)
    ImageView ivImage;
    /**
     * 标记
     */
    @BindView(R.id.tv_sign)
    TextView tvSign;
    /**
     * 操作sku
     */
    @BindView(R.id.tv_sku)
    TextView tvSku;
    /**
     * 箱内数量
     */
    @BindView(R.id.tv_count)
    TextView tvCount;
    /**
     * 装箱商品列表
     */
    @BindView(R.id.rv_park)
    RecyclerView rvPark;
    /**
     * 拆箱
     */
    @BindView(R.id.tv_unbox)
    TextView tvUnbox;
    /**
     * 适配器
     */
    private PackBoxDetailAdapter adapter;
    /**
     * 数据列表
     */
    private List<PackBoxDetail> packBoxDetailList;

    /**
     * 确定拆箱
     */
    private ConformDialog conformDialog;

    private int source;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_print_detail;
    }

    @Override
    public void initView() {
        source = getIntent().getIntExtra("source", Constants.PACKEDDETAIL);
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setSubTitleTextId(R.string.print);
        setSubtitleClickListener(this);
        setTitleTextId(source == Constants.PACKEDDETAIL ? R.string.pack_detail : R.string.cancel_order_detail);
        //   tvUnbox.setVisibility(source == Constants.PACKEDDETAIL ? View.VISIBLE : View.GONE);
        conformDialog = new ConformDialog(this);
        conformDialog.setOnConformCallBack(this);
        packBoxDetailList = new ArrayList<>();
        adapter = new PackBoxDetailAdapter(packBoxDetailList);
        rvPark.setLayoutManager(new LinearLayoutManager(this));
        rvPark.setAdapter(adapter);
        if (NetworkUtil.isNetworkAvailable()) {
            if (mPresenter != null) {
                mPresenter.getPackBoxDetail();
            }
        }
    }


    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getBoxId() {
        return getIntent().getStringExtra("boxId");
    }

    private PackBox packBox;

    @Override
    public void setParkBox(PackBox parkBox) {
        this.packBox = parkBox;
        try {
            ivImage.setImageBitmap(PrintUtils.createOneDCode(String.valueOf(parkBox.getBoxId())));
        } catch (WriterException e) {
            e.printStackTrace();
        }
        tvCount.setText(String.valueOf(parkBox.getItemNum()));
        tvSign.setText(parkBox.getOrderMark());
        tvSku.setText(String.valueOf(parkBox.getBoxNum()));
        packBoxDetailList.clear();
        packBoxDetailList.addAll(parkBox.getDetails());
        if (adapter != null) {
            adapter.initData();
        }
    }

    @Override
    public void setUnBoxResult(String result) {
        showMessage(result);
        finish();
    }

    @OnClick(R.id.tv_unbox)
    public void unBox() {
        operation = 0;
        conformDialog.show();
        conformDialog.setTitle("你确定要拆除此箱吗？");
    }

    @Override
    public void onCallBack() {
        if (operation == 0) {
            mPresenter.unBox();
        } else {
            BluetoothSocket socket = YunmayiApplication.getBluetoothSocket();
            if (socket == null) {
                G.log("xxxxxxx" + "等于空");
                G.showToast(this, "请连接蓝牙打印机！");
                Intent intent = new Intent(this, SettingBluetoothActivity.class);
                startActivity(intent);
            }else {
                if (NetworkUtil.isNetworkAvailable()) {
                    if (packBox.getDetails().size() > 0) {
                        int boxNum = packBox.getDetails().get(0).getBoxNum();
                        for (int i = 0; i < boxNum; i++) {
                            PrintUtils.printData(packBox, boxNum, (i + 1));
                        }
                    }
                } else {
                    G.showToast(this, "网络异常！");

                }
            }
        }

    }

    private int operation;

    @Override
    public void onClick(View view) {
        operation = 1;
        conformDialog.show();
        conformDialog.setTitle("你确定要打印此箱吗？");
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }




}
