package com.yun.mayi.pda.module.support.packing;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.db.OrderInfo;
import com.yun.mayi.pda.db.OrderInfoDao;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.widget.GoodsDetailDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/01/05
 * 名称：已拣/未拣货单详情
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ParkFragment extends BaseFragment implements WaitPackDetailAdapter.onAllLessClickListener, OnItemClickListener, WaitPackDetailAdapter.OnCheckChangeListener, WaitPackDetailAdapter.OnTitleClickListener {
    private static final String ARG_PARAM_TYPE = "type";
    private int type;
    /**
     * 待拣货列表
     */
    @BindView(R.id.prv_park)
    RecyclerView prvPark;
    /**
     * 没有数据
     */
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    /**
     * 适配器
     */
    private WaitPackDetailAdapter adapter;
    /**
     * 关键字
     */
    private String keyWord;
    /**
     * 分拣订单数据列表
     */
    private List<OrderInfo> orderInfoList;
    /**
     * 数据库
     */
    private OrderInfoDao orderInfoDao;
    /**
     * 入箱个数
     */
    private int packedNum = 0;
    /**
     * 改变数量回调
     */
    public OnOperationCallBack onOperationCallBack;
    /**
     * 点击全缺回调
     */
    public OnOutOfStockOperationCallBack onOutOfStockOperationCallBack;
    /**
     * orderId
     */
    private String orderId;
    /**
     * 供应商id
     */
     private String vendorId;
    /**
     * 商品详情对话框
     */
    private GoodsDetailDialog detailDialog;
    public static ParkFragment newInstance(int type, String orderId,String vendorId, String keyWord) {
        ParkFragment fragment = new ParkFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_TYPE, type);
        args.putString("orderId", orderId);
        args.putString("keyWord", keyWord);
        args.putString("vendorId",vendorId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            type = getArguments().getInt(ARG_PARAM_TYPE);
            orderId = getArguments().getString("orderId");
            vendorId = getArguments().getString("vendorId");
        }
        detailDialog = new GoodsDetailDialog(getActivity());
        orderInfoDao = new OrderInfoDao(getContext());
        packedNum = orderInfoDao.getPackedNum(orderId);
        orderInfoList = new ArrayList<>();
        adapter = new WaitPackDetailAdapter(getActivity(), type, orderInfoList);
        prvPark.setLayoutManager(new LinearLayoutManager(getActivity()));
        prvPark.setAdapter(adapter);
        adapter.setOnAllLessClickListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnCheckChangeListener(this);
        adapter.setOnTitleClickListener(this);
        getOrderInfoList(type, keyWord);
    }

    public void getOrderInfoList(int type, String keyWord) {
        orderInfoList.clear();
        switch (type) {
            case Constants.WAIT_PACK:
                orderInfoList.addAll(orderInfoDao.getUnPackOrderInfoList(orderId,vendorId, keyWord));
                break;
            case Constants.HAS_PACK:
                orderInfoList.addAll(orderInfoDao.getPackOrderInfoList(orderId,vendorId, keyWord));
                break;
        }
        G.log("ssss" + "刷新数据成功！" + orderInfoList.size());
        if (adapter != null) {
            adapter.initData();
        }
        tvNodata.setVisibility(orderInfoList.size() == 0 ? View.VISIBLE : View.GONE);
    }

    public void setCode(String data) {
        this.keyWord = data;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wait_park;
    }

    @Override
    public void onClick(View view, int position) {//入箱操作
        int isSuccess = -1;
        switch (type) {
            case Constants.WAIT_PACK:
                isSuccess = orderInfoDao.packedBox(orderInfoList.get(position).getOrderId(), orderInfoList.get(position).getId(), 1);
                G.log("---xxxxxxx" + isSuccess + "入箱");
                break;
            case Constants.HAS_PACK:
                //
                //    if (orderInfoDao.)
                isSuccess = orderInfoDao.packedBox(orderInfoList.get(position).getOrderId(), orderInfoList.get(position).getId(), 0);
                if (orderInfoDao.getPackNum(orderInfoList.get(position).getOrderId(), orderInfoList.get(position).getId()) == 0) {
                    G.log("---xxxxxxx"+"不是全缺操作后复原的");
                    orderInfoDao.updatePackNum(orderInfoList.get(position).getOrderId(),
                            orderInfoList.get(position).getId(), orderInfoList.get(position).getOriginalNum());
                }
                G.log("---xxxxxxx" + isSuccess + "取消分拣");
                break;
        }
        if (isSuccess != -1) {
            packedNum = orderInfoDao.getPackedNum(orderId);
            if (onOperationCallBack != null) {
                onOperationCallBack.callBack(packedNum);
            }
        }
        orderInfoList.remove(position);
        tvNodata.setVisibility(orderInfoList.size() == 0 ? View.VISIBLE : View.GONE);
        adapter.initData();

    }

    @Override
    public void onTitleClick(int position) {
       detailDialog.show();
       detailDialog.setGoodsInfo(orderInfoList.get(position));
    }
    @Override
    public void onChecked(int position, boolean isChecked) {//减少或添加入箱数量
        orderInfoDao.updateCheckNum(orderId, orderInfoList.get(position).getId(), isChecked ? 1 : 0);
        packedNum = orderInfoDao.getPackedNum(orderId);
        if (onOperationCallBack != null) {
            onOperationCallBack.callBack(packedNum);
        }
    }

    @Override
    public void onLessClick(int position) {//全缺回调
        G.log("ssss" + "全缺回调");
        if (onOutOfStockOperationCallBack != null) {
            String orderDetailId = orderInfoList.get(position).getId();
            onOutOfStockOperationCallBack.onOutOfStock(orderDetailId);
        }
    }

    public void setOnOperationCallBack(OnOperationCallBack onOperationCallBack) {
        this.onOperationCallBack = onOperationCallBack;
    }

    public void setOnOutOfStockOperationCallBack(OnOutOfStockOperationCallBack onOutOfStockOperationCallBack) {
        this.onOutOfStockOperationCallBack = onOutOfStockOperationCallBack;
    }



    public interface OnOperationCallBack {
        void callBack(int packedNum);
    }

    public interface OnOutOfStockOperationCallBack {
        void onOutOfStock(String orderDetailId);
    }
}
