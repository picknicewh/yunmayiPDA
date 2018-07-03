package com.yun.mayi.pda.module.join.conform;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.bean.ConformGoodsResult;
import com.yun.mayi.pda.bean.PackBoxInfo;
import com.yun.mayi.pda.bean.PackDetailInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.OnConformPackListener;
import com.yun.mayi.pda.utils.OnNumberChangeListener;
import com.yun.mayi.pda.widget.ConformDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
/**
 * 作者： wh
 * 时间： 2018/03/28
 * 名称：确认收货按平台查询收货列表的数据
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ConformGoodsFragment extends BaseFragment implements OnItemClickListener, GoodsConformAdapterS.onAllLessClickListener, GoodsConformAdapterS.OnNumberChangeListener, ConformDialog.OnConformCallBack, GoodsConformAdapterT.onAllLessClickListener {
    /**
     * 单件确认收货
     */
    @BindView(R.id.rv_single)
    RecyclerView rvSingle;
    /**
     * 打包确认收货
     */
    @BindView(R.id.rv_total)
    RecyclerView rvTotal;
    /**
     * 没有数据
     */
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.ll_data)
    LinearLayout llData;

    /**
     * 单件确认收货适配器
     */
    private GoodsConformAdapterS singleAdapter;
    /**
     * 打包确认收货
     */
    private GoodsConformAdapterT totalAdapter;
    /**
     * 数据列表
     */
    private List<PackDetailInfo> packDetailInfoList;
    /**
     * 装箱列表
     */
    private List<PackBoxInfo> packBoxDetailList;
    /**
     * 确认对话框
     */
    private ConformDialog conformDialog;
    /**
     * 数量列表
     */
    private String numList = "";
    /**
     * id列表
     */
    private String detailIdList = "";
    /**
     * 箱数列表
     */
    private String boxNumList = "";
    /**
     * 平台编号
     */
    private String agentNumber;
    /**
     * 数据类型
     */
    private int type;
    /**
     * 数量改变监听
     */
    private OnNumberChangeListener onNumberChangeListener;
    /**
     * 确认收货监听
     */
    private OnConformPackListener onConformPackListener;

    public ConformGoodsFragment (){

    }
    public static ConformGoodsFragment newInstance(String agentNumber,int type) {
        ConformGoodsFragment fragment = new ConformGoodsFragment();
        Bundle args = new Bundle();
        args.putString("agentNumber", agentNumber);
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        agentNumber = getArguments().getString("agentNumber");
        type = getArguments().getInt("type");
        G.log("-------------------x--------------------"+this);
        conformDialog = new ConformDialog(getActivity());
        conformDialog.setOnConformCallBack(this);
        switch (type) {
            case Constants.TYPE_TOTAL:
                packDetailInfoList = new ArrayList<>();
                singleAdapter = new GoodsConformAdapterS(packDetailInfoList, agentNumber);
                rvSingle.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvSingle.setAdapter(singleAdapter);
                singleAdapter.setOnItemClickListener(this);
                singleAdapter.setOnAllLessClickListener(this);
                singleAdapter.setOnNumberChangeListener(this);
                break;
            case Constants.TYPE_SINGLE:
                packBoxDetailList = new ArrayList<>();
                rvSingle.setNestedScrollingEnabled(false);
                totalAdapter = new GoodsConformAdapterT(packBoxDetailList);
                totalAdapter.setOnItemClickListener(this);
                totalAdapter.setOnAllLessClickListener(this);
                rvTotal.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvTotal.setNestedScrollingEnabled(false);
                rvTotal.setAdapter(totalAdapter);
                break;
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_conform_goods;
    }

    public void setConformGoodsList(ConformGoodsResult result) {
        G.log("-------------------x--------------------"+type);
        switch (type) {
            case Constants.TYPE_TOTAL:
                if (result.getPack_list().size() == 0) {
                    tvNodata.setVisibility(View.VISIBLE);
                    llData.setVisibility(View.GONE);
                } else {
                    llData.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);
                }
                this.packDetailInfoList.clear();
                this.packDetailInfoList.addAll(result.getPack_list());

                if (singleAdapter != null) {
                    singleAdapter.initData();
                }
                break;
            case Constants.TYPE_SINGLE:
                if (result.getBox_list().size() == 0) {
                    tvNodata.setVisibility(View.VISIBLE);
                    llData.setVisibility(View.GONE);
                } else {
                    llData.setVisibility(View.VISIBLE);
                    tvNodata.setVisibility(View.GONE);
                }
                this.packBoxDetailList.clear();
                for (int i = 0; i < result.getBox_list().size(); i++) {
                    PackBoxInfo packBoxInfo = result.getBox_list().get(i);
                    if (packBoxInfo.getIs_delete() == 0) {
                        packBoxDetailList.add(packBoxInfo);
                    }
                }

             //   this.packBoxDetailList.addAll(result.getBox_list());
                if (totalAdapter != null) {
                    totalAdapter.initData();
                }
                break;
        }
    }


    @Override
    public void onLessClick(int position) {
        numList = "0";
        switch (type) {
            case Constants.TYPE_TOTAL:
                detailIdList = String.valueOf(packDetailInfoList.get(position).getId());
                break;
            case Constants.TYPE_SINGLE:
                detailIdList = String.valueOf(packBoxDetailList.get(position).getId());
                break;
        }
        conformDialog.show();
        conformDialog.setTitle("确认全缺收货吗？");
    }
    /**
     * 获取数据列表
     */
    public String getNumList() {
        detailIdList = "";
        numList = "";

        switch (type) {
            case Constants.TYPE_TOTAL:
                for (int i = 0; i < packDetailInfoList.size(); i++) {
                    if (i == packDetailInfoList.size() - 1) numList += singleAdapter.getStockList().get(i);
                    else numList = numList + singleAdapter.getStockList().get(i) + ",";
                }
                break;
            case Constants.TYPE_SINGLE:
                for (int i = 0; i < packBoxDetailList.size(); i++) {
                    if (i == packBoxDetailList.size() - 1)
                        numList += packBoxDetailList.get(i).getNum();
                    else
                        numList = numList + packBoxDetailList.get(i).getNum() + ",";
                }
                break;
        }
        return numList;
    }
    /**
     * 获取数据列表
     */
    public String getBoxNumList() {
        boxNumList = "";
        switch (type) {
            case Constants.TYPE_TOTAL:
                for (int i = 0; i < packDetailInfoList.size(); i++) {
                    if (i == packDetailInfoList.size() - 1) boxNumList +=1;
                    else boxNumList = boxNumList + 1 + ",";
                }
                break;
            case Constants.TYPE_SINGLE:
                if (totalAdapter!=null){
                    boxNumList = totalAdapter.getBoxNumList();
                }
                break;
        }
        return boxNumList;
    }

    /**
     * 获取数据列表
     */
    public String getDetailIdList() {
        detailIdList = "";
        switch (type) {
            case Constants.TYPE_TOTAL:
                for (int i = 0; i < packDetailInfoList.size(); i++) {
                    if (i == packDetailInfoList.size() - 1){
                        detailIdList += packDetailInfoList.get(i).getId();
                    }else {
                        detailIdList = detailIdList + packDetailInfoList.get(i).getId() + ",";
                    }
                }
                break;
            case Constants.TYPE_SINGLE:
                for (int i = 0; i < packBoxDetailList.size(); i++) {
                    if (i == packBoxDetailList.size() - 1) {
                        detailIdList += packBoxDetailList.get(i).getId();
                    } else {
                        detailIdList = detailIdList + packBoxDetailList.get(i).getId() + ",";
                    }
                }
                break;
        }
        return detailIdList;
    }

    @Override
    public void onClick(View view, int position) {
        switch (type) {
            case Constants.TYPE_TOTAL:
                numList = String.valueOf(singleAdapter.getStockList().get(position));
                detailIdList = String.valueOf(packDetailInfoList.get(position).getId());
                boxNumList="1";
                break;
            case Constants.TYPE_SINGLE:
                numList = String.valueOf(packBoxDetailList.get(position).getNum());
                detailIdList = String.valueOf(packBoxDetailList.get(position).getId());
                if (totalAdapter!=null){
                    boxNumList = String.valueOf(totalAdapter.getBoxNum().get(position));
                }
                break;
        }
        conformDialog.show();
        conformDialog.setTitle("确认收货吗？");

    }

    @Override
    public void onNumberChange(int num) {
        if (onNumberChangeListener != null) {
            onNumberChangeListener.onNumberChange(num);
        }
    }

    @Override
    public void onCallBack() {
        if (onConformPackListener != null) {
            onConformPackListener.onConform(detailIdList, numList,boxNumList);
        }
    }

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public void setOnConformPackListener(OnConformPackListener onConformPackListener) {
        this.onConformPackListener = onConformPackListener;
    }

}
