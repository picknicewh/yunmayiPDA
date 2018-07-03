package com.yun.mayi.pda.module.drawer.scan;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.utils.SoundUtils;
import com.yun.mayi.pda.widget.ClearEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间： 2018/06/15
 * 名称：司机--》订单扫描单页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class DrawerScanActivity extends BaseMvpActivity<DrawerScanPresenter> implements DrawerScanContract.View{


    @BindView(R.id.et_sign)
    ClearEditText etSign;
    private SoundUtils soundUtils;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_drawer_scan;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.back);
        setTitleTextId(R.string.drawer_scan);
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
        if (mPresenter!=null){
            mPresenter.deliveryScan();
        }
    }

    @OnClick(R.id.tv_conform)
    public void onViewClicked() {
        if (mPresenter!=null){
            mPresenter.deliveryScan();
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
    public void onSuccess() {
        etSign.setText("");
        soundUtils.success();
    }

    @Override
    public void onFail() {
        soundUtils.warn();
    }
}
