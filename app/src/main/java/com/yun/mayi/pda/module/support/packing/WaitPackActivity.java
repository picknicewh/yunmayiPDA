package com.yun.mayi.pda.module.support.packing;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseMvpPackActivity;
import com.yun.mayi.pda.bean.Order;
import com.yun.mayi.pda.bean.OrderDetail;
import com.yun.mayi.pda.bean.PackOrderResult;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.NetworkUtil;
import com.yun.mayi.pda.widget.ClearEditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/01/05
 * 名称：未拣货单
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class
WaitPackActivity extends BaseMvpPackActivity<WaitPackPresenter> implements OnItemClickListener, WaitPackContract.View {
    /**
     * 关键字
     */
    @BindView(R.id.et_sign)
    ClearEditText etSign;
    /**
     * 订单数
     */
    @BindView(R.id.tv_order_count)
    TextView tvOrderCount;
    /**
     * sku总数
     */
    @BindView(R.id.tv_sku_count)
    TextView tvSkuCount;

    /**
     * 订单列表
     */
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;

    /**
     * 适配器
     */
    private WaitPackAdapter adapter;

    /**
     * 订单列表
     */
    private List<Order> orderList;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_wati_parking;
    }

    @Override
    public void initView() {
        super.initView();
        setLeftIcon(R.mipmap.ic_back_white);
        setLeftTextId(R.string.workBar);
        setTitleTextId(R.string.wait_packing_order);
        etSign.setHint("标记/订单号");
        orderList = new ArrayList<>();
        adapter = new WaitPackAdapter(this, orderList);
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        rvOrder.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (NetworkUtil.isNetworkAvailable()) {
             etSign.setText("");
            mPresenter.getPackList();
        }
    }

    @Override
    public void onDataChange() {
        mPresenter.getPackList();
    }

    @Override
    public void onClick(View view, int position) {
        List<OrderDetail> orderDetails = parkInfo.getOrders().get(position).getDetails();
        G.log("xzxxxx"+ orderDetails.size());
          if (orderDetails!=null&&orderDetails.size()>0){
              Intent intent = new Intent(this, WaitPackDetailActivity.class);
              intent.putExtra("orderId", orderList.get(position).getId());
              intent.putExtra("vendorId", String.valueOf(orderList.get(position).getVendorId()));
              intent.putExtra("sign",orderList.get(position).getMark());
              startActivity(intent);
          }

    }

    @OnClick(R.id.tv_search)
    public void search(View view) {
        if (mPresenter != null) {
            mPresenter.getPackList();
        }
    }

    @Override
    public String getToken() {
        return UserMessage.getInstance().getToken();
    }

    @Override
    public String getKeyWord() {
        return etSign.getText().toString();
    }

    @Override
    public String getBeginTime() {
        return tvStartTime.getText().toString();
    }

    @Override
    public String getEndTime() {
        return tvEndTime.getText().toString();
    }

    @Override
    public int getStatus() {
        return Constants.WAIT_PACK;
    }
    private PackOrderResult parkInfo;
    @Override
    public void setParkOrderInfo(PackOrderResult parkInfo) {
        this.parkInfo = parkInfo;
        tvOrderCount.setText(String.valueOf(parkInfo.getOrderNum()));
        tvSkuCount.setText(String.valueOf(parkInfo.getItemNum()));
        orderList.clear();
        orderList.addAll(parkInfo.getOrders());
      //  G.log("xxxxxxxxxxx"+parkInfo.getDetails().size());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public int getPickerId() {
        return UserMessage.getInstance().getUser_id();
    }

    @Override
    public void updateScanData(String data) {
        super.updateScanData(data);
        etSign.setText(data);
        mPresenter.getPackList();

    }


    @Override
    protected void initInject() {
        mDaggerActivityComponent.inject(this);
    }
}
