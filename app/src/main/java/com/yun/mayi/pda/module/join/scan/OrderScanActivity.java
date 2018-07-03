package com.yun.mayi.pda.module.join.scan;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.utils.SoundUtils;
import com.yun.mayi.pda.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：送货单扫描
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class OrderScanActivity extends BaseMvpActivity<OrderScanPresenter> implements OrderScanContract.View {
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 订单id
     */
    private String orderId = "";
    private SoundUtils soundUtils;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_order_scan;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.order_scan);
        soundUtils = new SoundUtils(R.raw.scan_success, R.raw.sacn_fail);
        soundUtils.init(this);
    }

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        orderId = data;
        if (mPresenter != null) {
            mPresenter.OrderAssignPicker();
        }
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getOrderId() {
        return etSign.getText().toString();
    }

    @Override
    public String getAgentNumber() {
        return UserMessage.getInstance().getAgentNumber();
    }

    @Override
    public void success() {
        etSign.setText("");
        soundUtils.success();
    }

    @Override
    public void fail() {
        soundUtils.warn();
    }

    @OnClick(R.id.tv_conform)
    public void conform() {
        if (mPresenter != null) {
            mPresenter.OrderAssignPicker();
        }
    }
}
