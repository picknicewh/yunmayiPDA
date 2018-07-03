package com.yun.mayi.pda.module.join.loadscan;

import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpActivity;
import com.yun.mayi.pda.bean.DrawerInfo;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.utils.DateUtil;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.SoundUtils;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：装车扫描页面
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LoadScanCodeActivity extends BaseMvpActivity<LoadScanCodePresenter> implements LoadScanCodeContract.View {
    /**
     * 司机
     */
    @BindView(R.id.tv_drawer)
    TextView tvDrawer;
    /**
     * 订单id
     */
    @BindView(R.id.et_order_id)
    ClearEditText etOrderId;

    /**
     * 选择类型
     */
    private OptionsPickerView mOptionsPickerView;
    /**
     * 选择位置
     */
    private int position = 0;
    /**
     * 司机列表
     */
    private List<String> drawerList;
    /**
     * 送货员列表
     */
    private List<DrawerInfo> drawerInfoList;
    /**
     * 声音工具类
     */
    private SoundUtils soundUtils;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_scan_code;
    }

    @Override
    public void initView() {
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.scan_car);
        soundUtils = new SoundUtils(R.raw.scan_success, R.raw.sacn_fail);
        soundUtils.init(this);
        drawerInfoList = new ArrayList<>();
        drawerList = new ArrayList<>();
        if (mPresenter != null) {
            mPresenter.getDeliverymanList();
        }
    }

    private OptionsPickerView.OnOptionsSelectListener optionsSelectListener = new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            if (drawerList.size() > 0) {
                position = options1;
                tvDrawer.setText(drawerList.get(options1));
            }
        }
    };

    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etOrderId.setText(data);
        if (G.isEmteny(tvDrawer.getText().toString())) {
            G.showToast(this, "请选择司机!");
            return;
        }
        if (mPresenter != null) {
            mPresenter.distributeLoading();
        }
    }

    @OnClick(R.id.tv_conform)
    public void conform() {
        String drawer = tvDrawer.getText().toString();
        if (G.isEmteny(drawer)) {
            G.showToast(this, "请选择司机!");
            return;
        }
        if (G.isEmteny(etOrderId.getText().toString())) {
            G.showToast(this, "请输入订单号!");
            return;
        }
        if (mPresenter != null) {
            mPresenter.distributeLoading();
        }
    }

    @OnClick(R.id.tv_drawer)
    public void drawer() {
        if (mOptionsPickerView != null) {
            mOptionsPickerView.show();
        }
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getOrderId() {
        return etOrderId.getText().toString();
    }

    @Override
    public String getAgentNumber() {
        return UserMessage.getInstance().getAgentNumber();
    }

    @Override
    public int getDeliverymanId() {
        return drawerInfoList.get(position).getUser_id();
    }

    @Override
    public void setDrawerInfoList(List<DrawerInfo> drawerInfoList) {
        this.drawerInfoList.clear();
        this.drawerInfoList.addAll(drawerInfoList);
        for (int i = 0; i < drawerInfoList.size(); i++) {
            drawerList.add(drawerInfoList.get(i).getName());
        }
        mOptionsPickerView = DateUtil.getOptionPickerView("请选择司机", drawerList, position, this, optionsSelectListener);
    }

    @Override
    public void success() {
        etOrderId.setText("");
        soundUtils.success();
    }

    @Override
    public void fail() {
        soundUtils.warn();
    }
}
