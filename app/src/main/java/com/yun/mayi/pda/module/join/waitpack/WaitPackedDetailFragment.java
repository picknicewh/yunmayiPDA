package com.yun.mayi.pda.module.join.waitpack;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.db.BoxInfo;
import com.yun.mayi.pda.db.BoxInfoDao;
import com.yun.mayi.pda.db.OrderInfo;
import com.yun.mayi.pda.db.OrderInfoDao;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/3/31
 * 名称：待拣货单详情页
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackedDetailFragment extends BaseFragment implements WaitPackedDetailAdapterS.onAllLessClickListener, OnItemClickListener, WaitPackedDetailAdapterS.OnNumberChangeListener, WaitPackedDetailAdapterT.OnNumberChangeListener {
    private static final String ARG_PARAM_TYPE = "type";
    /**
     * 单件列表
     */
    @BindView(R.id.rv_single)
    RecyclerView rvSingle;
    private WaitPackedDetailAdapterS adapterS;
    /**
     * 整箱装箱列表
     */
    @BindView(R.id.rv_total)
    RecyclerView rvTotal;
    private WaitPackedDetailAdapterT adapterT;
    /**
     * 拣货详情列表
     */
    private List<OrderInfo> orderInfoList;
    /**
     * 拣货数据库
     */
    private OrderInfoDao orderInfoDao;
    /**
     * 拣货装箱详情列表
     */
    private List<BoxInfo> boxInfoList;
    /**
     * 拣货装箱数据库
     */
    private BoxInfoDao boxInfoDao;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 类型
     */
    private int type;
    /**
     * 入箱sku数
     */
    private int packedNum = 0;
    /**
     * 入箱件数
     */
    private int packCount = 0;
    /**
     * 箱数
     */
    private int boxNum = 0;
    /**
     * 改变数量回调
     */
    public OnOperationCallBack onOperationCallBack;
    private boolean isDrawer;

    public static WaitPackedDetailFragment newInstance(int type, String orderId, boolean isDrawer) {
        WaitPackedDetailFragment fragment = new WaitPackedDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM_TYPE, type);
        args.putString("orderId", orderId);
        args.putBoolean("isDrawer", isDrawer);
        fragment.setArguments(args);
        return fragment;
    }

    private String keyWord;

    /**
     * 获取列表并刷新数据
     */
    public void getOrderInfoList(int type, String keyWord) {
        this.keyWord = keyWord;
        orderInfoList.clear();
        boxInfoList.clear();
        switch (type) {
            case Constants.WAIT_PACK:
                orderInfoList.addAll(orderInfoDao.getUnPackOrderInfoList(orderId, keyWord));
                boxInfoList.addAll(boxInfoDao.getUnPackBoxInfoList(orderId, keyWord));
                break;
            case Constants.HAS_PACK:
                orderInfoList.addAll(orderInfoDao.getPackOrderInfoList(orderId, keyWord));
                boxInfoList.addAll(boxInfoDao.getPackOrderInfoList(orderId, keyWord));
                break;
        }
        G.log("ssss" + "刷新数据成功！" + orderInfoList.size());

        if (adapterS != null) {
            adapterS.initData();
        }
        if (adapterT != null) {
            adapterT.initData();
        }
    }

    @Override
    public void initView() {
        orderId = getArguments().getString("orderId");
        type = getArguments().getInt(ARG_PARAM_TYPE);
        isDrawer = getArguments().getBoolean("isDrawer");
        orderInfoDao = new OrderInfoDao(getActivity());
        boxInfoDao = new BoxInfoDao(getActivity());
        orderInfoList = new ArrayList<>();
        adapterS = new WaitPackedDetailAdapterS(getActivity(), type, orderInfoList);
        rvSingle.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSingle.setNestedScrollingEnabled(true);
        rvSingle.setAdapter(adapterS);
        adapterS.setOnItemClickListener(this);
        adapterS.setOnAllLessClickListener(this);
        adapterS.setOnNumberChangeListener(this);
        boxInfoList = new ArrayList<>();
        adapterT = new WaitPackedDetailAdapterT(getActivity(), type, boxInfoList);
        adapterT.setOnAllLessClickListener(lessClickListener);
        adapterT.setOnItemClickListener(onItemClickListener);
        adapterT.setOnNumberChangeListener(this);
        rvTotal.setNestedScrollingEnabled(true);
        rvTotal.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTotal.setAdapter(adapterT);

    }

    private WaitPackedDetailAdapterT.onAllLessClickListener lessClickListener = new WaitPackedDetailAdapterT.onAllLessClickListener() {
        @Override
        public void onLessClick(int position) {
            String boxId = boxInfoList.get(position).getBoxId();
            if (isDrawer) {
                boxInfoDao.updateLessBox(orderId, boxId);//司机全缺操作
                List<BoxInfo> boxInfos = new ArrayList<>();
                for (int i = 0; i < boxInfoList.size(); i++) {
                    BoxInfo boxInfo = boxInfoList.get(i);
                    if (boxInfo.getBoxId().equals(boxId)) {
                        boxInfos.add(boxInfo);
                    }
                }
                boxInfoList.removeAll(boxInfos);
            } else {
                boxInfoDao.updateLess(orderId, boxInfoList.get(position).getId());//全缺操作
                boxInfoList.remove(position);
            }
            if (adapterT != null) {
                adapterT.initData();
            }
        }
    };
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(View view, int position) {
            int isSuccess = -1;
            String boxId = boxInfoList.get(position).getBoxId();
            String orderId = boxInfoList.get(position).getOrderId();
            switch (type) {
                case Constants.WAIT_PACK:
                    isSuccess = boxInfoDao.packedBox(orderId, boxId, 1);
                    G.log("---xxxxxxx" + isSuccess + "入箱");
                    boxInfoList.clear();
                    boxInfoList.addAll(boxInfoDao.getUnPackBoxInfoList(orderId, keyWord));
                    break;
                case Constants.HAS_PACK:
                    isSuccess = boxInfoDao.packedBox(orderId, boxId, 0);
                    if (boxInfoDao.getPackNum(orderId, boxId) == 0) {
                        if (isDrawer) {
                            for (int i = 0; i < boxInfoList.size(); i++) {
                                if (boxInfoList.get(i).getBoxId().equals(boxId)) {
                                    boxInfoDao.updatePackNum(orderId, boxId, boxInfoList.get(i).getOriginalNum());
                                }
                            }
                        } else {
                            boxInfoDao.updatePackNum(orderId, boxId, boxInfoList.get(position).getOriginalNum());
                        }
                        //
                        G.log("---xxxxxxx" + "不是全缺操作后复原的");
                    }
                    boxInfoList.clear();
                    boxInfoList.addAll(boxInfoDao.getPackOrderInfoList(orderId, keyWord));
                    break;
            }
            if (isSuccess != -1) {
                boxNum = boxInfoDao.getPackedNum(orderId);
                packedNum = orderInfoDao.getPackedNum(orderId);
                packCount = boxInfoDao.getPackedCount(orderId) + orderInfoDao.getPackedCount(orderId);
                if (onOperationCallBack != null) {
                    onOperationCallBack.callBack(packedNum, boxNum, packCount);
                }

            }
            if (adapterT != null) {
                adapterT.initData();
            }
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wait_packed_detail;
    }

    @Override
    public void onLessClick(int position) {

        orderInfoDao.updateLess(orderId, orderInfoList.get(position).getId());//全缺操作
        orderInfoList.remove(position);
        if (adapterS != null) {
            adapterS.initData();
        }
    }

    @Override
    public void onClick(View view, int position) {
        int isSuccess = -1;
        String id = orderInfoList.get(position).getId();
        String orderId = orderInfoList.get(position).getOrderId();
        switch (type) {
            case Constants.WAIT_PACK:
                isSuccess = orderInfoDao.packedBox(orderId, id, 1);
                G.log("---xxxxxxx" + isSuccess + "入箱");
                break;
            case Constants.HAS_PACK:
                isSuccess = orderInfoDao.packedBox(orderId, id, 0);
                if (orderInfoDao.getPackNum(orderId, id) == 0) {
                    orderInfoDao.updatePackNum(orderId, id, orderInfoList.get(position).getOriginalNum());
                    G.log("---xxxxxxx" + "不是全缺操作后复原的");
                }
                break;
        }
        if (isSuccess != -1) {
            boxNum = boxInfoDao.getPackedNum(orderId);
            packedNum = orderInfoDao.getPackedNum(orderId);
            packCount = boxInfoDao.getPackedCount(orderId) + orderInfoDao.getPackedCount(orderId);
            if (onOperationCallBack != null) {
                onOperationCallBack.callBack(packedNum, boxNum, packCount);
            }
        }
        orderInfoList.remove(position);
        if (adapterS != null) {
            adapterS.initData();
        }
    }

    public void setOnOperationCallBack(OnOperationCallBack onOperationCallBack) {
        this.onOperationCallBack = onOperationCallBack;
    }

    @Override
    public void onNumberChange() {
        boxNum = boxInfoDao.getPackedNum(orderId);
        packedNum = orderInfoDao.getPackedNum(orderId);
        packCount = boxInfoDao.getPackedCount(orderId) + orderInfoDao.getPackedCount(orderId);
        if (onOperationCallBack != null) {
            onOperationCallBack.callBack(packedNum, boxNum, packCount);
        }
    }

    public interface OnOperationCallBack {
        void callBack(int packedNum, int boxNum, int count);
    }
}
