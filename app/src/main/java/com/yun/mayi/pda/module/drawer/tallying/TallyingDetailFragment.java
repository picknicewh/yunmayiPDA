package com.yun.mayi.pda.module.drawer.tallying;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.bean.TallyingInfoDetail;
import com.yun.mayi.pda.db.TallyingInfoDetailDao;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者： wh
 * 时间：  2018/05/25
 * 名称：司机点货详情页面
 * 版本说明：type=0待点货type=1已点货
 * 附加注释：
 * 主要接口：
 */
public class TallyingDetailFragment extends BaseFragment implements TallyingDetailAdapter.OnNumberChangeListener, OnItemClickListener, TallyingDetailAdapter.onAllLessClickListener {

    private static final String TYPE = "type";
    private static final String ORDERID = "orderId";
    private static final String ISFIRST = "isFirst";
    @BindView(R.id.rv_unload)
    RecyclerView rvUnload;
    /**
     * 适配器
     */
    private TallyingDetailAdapter adapter;
    private int type;
    /**
     * 点货数据列表
     */
    private List<TallyingInfoDetail> tallyingInfoDetailList;
    private String keyword;
    /**
     * 数据库管理
     */

    private TallyingInfoDetailDao tallyingInfoDao;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 操作回调
     */
    private OnOperationCallBack onOperationCallBack;
    /**
     * 入箱件数
     */
    private int packCount = 0;
    /**
     * 箱数
     */
    private int boxNum = 0;
    /**
     * 是否第一次点货
     */
    private  int isFirst=0;
    public TallyingDetailFragment() {

    }

    public static TallyingDetailFragment getInstance(int type, String orderId,int isFirst) {
        TallyingDetailFragment fragment = new TallyingDetailFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        args.putString(ORDERID, orderId);
        args.putInt(ISFIRST,isFirst);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
        if (getArguments() != null) {
            type = getArguments().getInt(TYPE);
            orderId = getArguments().getString(ORDERID);
            isFirst =getArguments().getInt(ISFIRST);
        }
        tallyingInfoDao = new TallyingInfoDetailDao(getActivity());
        tallyingInfoDetailList = new ArrayList<>();
        adapter = new TallyingDetailAdapter(type, tallyingInfoDetailList,isFirst);
        adapter.setOnNumberChangeListener(this);
        adapter.setOnItemClickListener(this);
        adapter.setOnAllLessClickListener(this);
        rvUnload.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUnload.setAdapter(adapter);
    }

    public void getTallyingInfoList(int type, String keyword) {
        this.keyword = keyword;
        tallyingInfoDetailList.clear();
        List<TallyingInfoDetail> tallyingInfoDetails = new ArrayList<>();
        switch (type) {
            case Constants.WAIT_PACK:
                tallyingInfoDetails = tallyingInfoDao.getUnDrawerPickInfoDetailList(orderId, keyword);
                break;
            case Constants.HAS_PACK:
                tallyingInfoDetails = tallyingInfoDao.getDrawerPickInfoDetailList(orderId, keyword);
                break;
        }
        G.log("ssss" + "刷新数据成功！" + tallyingInfoDetails.size());
        tallyingInfoDetailList.addAll(tallyingInfoDetails);

        if (adapter != null) {
            adapter.initData();
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_tallying;
    }

    @Override
    public void onNumberChange(int position, int num) {
        TallyingInfoDetail infoDetail = tallyingInfoDetailList.get(position);
        String orderDetailId = infoDetail.getId();
        if (isFirst==0){
            tallyingInfoDao.updateLoadingNum(orderId, orderDetailId, num);//修改装车数量，第一次司机点货，修改装车数量
        }else {
            tallyingInfoDao.updateDelieverNum(orderId, orderDetailId, num);//修改配送数量，第二次司机点货，修改配送数量
        }

        boxNum = tallyingInfoDao.getBoxNum(orderId);
        packCount = tallyingInfoDao.getTotalCount(orderId);
        if (onOperationCallBack != null) {
            onOperationCallBack.callBack(boxNum, packCount);
        }
    }


    private int isSuccess;

    @Override
    public void onClick(View view, int position) {
        String id = tallyingInfoDetailList.get(position).getId();
        String orderId = tallyingInfoDetailList.get(position).getOrderId();
        switch (type) {
            case Constants.WAIT_PACK:
                isSuccess = tallyingInfoDao.packedBox(orderId, id, 1);
                G.log("---xxxxxxx" + position + "入箱");
                break;
            case Constants.HAS_PACK:
                isSuccess = tallyingInfoDao.packedBox(orderId, id, 0);
                if (tallyingInfoDao.getPackNum(orderId, id) == 0) {
                    if (isFirst==0){
                        tallyingInfoDao.updateLoadingNum(orderId, id, tallyingInfoDetailList.get(position).getDeliverQuantity());
                    }else {
                        tallyingInfoDao.updateDelieverNum(orderId, id, tallyingInfoDetailList.get(position).getDeliverQuantity());
                    }
                    G.log("---xxxxxxx" + "不是全缺操作后复原的");
                }
                break;
        }
        tallyingInfoDetailList.remove(position);
        if (isSuccess != -1) {
            boxNum = tallyingInfoDao.getBoxNum(orderId);
            packCount = tallyingInfoDao.getTotalCount(orderId);
            if (onOperationCallBack != null) {
                onOperationCallBack.callBack(boxNum, packCount);
            }
        }
        if (adapter != null) {
            adapter.initData();
        }
    }

    @Override
    public void onLessClick(int position) {
        tallyingInfoDao.updateLess(orderId, tallyingInfoDetailList.get(position).getId());//全缺操作
        tallyingInfoDetailList.remove(position);
        if (adapter != null) {
            adapter.initData();
        }
    }

    public interface OnOperationCallBack {
        void callBack(int boxNum, int count);
    }

    public void setOnOperationCallBack(OnOperationCallBack onOperationCallBack) {
        this.onOperationCallBack = onOperationCallBack;
    }
}
