package com.yun.mayi.pda.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.db.OrderInfo;
import com.yun.mayi.pda.db.OrderInfoDao;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.module.support.packing.WaitPackDetailAdapter;
import com.yun.mayi.pda.utils.G;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者： wh
 * 时间：  2018/1/5
 * 名称：拣货详情对话框
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackDetailDialog extends AlertDialog implements OnItemClickListener, WaitPackDetailAdapter.onAllLessClickListener {
    /**
     * wifi的名字
     */
    @BindView(R.id.rv_park)
    RecyclerView rvPark;
    /**
     * 没有数据
     */
    @BindView(R.id.tv_nodata)
    TextView tvnoData;

    /**
     * 适配器
     */
    private WaitPackDetailAdapter adapter;
    private List<OrderInfo> orderInfoList;
    /**
     * 本地数据库
     */
    private OrderInfoDao orderInfoDao;

    public PackDetailDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_park_detail);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        orderInfoDao = new OrderInfoDao(getContext());
        setDialogWidth();
        orderInfoList = new ArrayList<>();
        adapter = new WaitPackDetailAdapter(getContext(), 0, orderInfoList);
        rvPark.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPark.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnAllLessClickListener(this);
    }

    /**
     * 根据id获取搜索数据
     *
     * @param orderDetailId
     */
    public void getOrderInfoList(String orderDetailId,String  vendorId,String barCode) {
        orderInfoList.clear();
        orderInfoList.addAll(orderInfoDao.getUnPackOrderInfoList(orderDetailId,vendorId,barCode));
        G.log("xxxxxxxxx-----"+orderInfoList.size());
        if (orderInfoList.size() == 0) {
            tvnoData.setVisibility(View.VISIBLE);
        } else {
            tvnoData.setVisibility(View.GONE);
            if (adapter != null) {
                adapter.initData();
            }
        }

    }

    /**
     * 设置屏幕的宽度
     */
    private void setDialogWidth() {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getWindow().setAttributes(params);
    }

    @OnClick({R.id.tv_cancel})
    public void cancel() {
        dismiss();
        if (onRemoveCallBack != null) {
            onRemoveCallBack.onRemoveSuccess("");
        }
    }

    public void setOnRemoveCallBack(OnRemoveCallBack onRemoveCallBack) {
        this.onRemoveCallBack = onRemoveCallBack;
    }

    private OnRemoveCallBack onRemoveCallBack;

    @Override
    public void onClick(View view, int position) {
        if (onRemoveCallBack != null) {
            onRemoveCallBack.onRemoveSuccess(orderInfoList.get(position).getId());
        }
    }
    public void setOnAllLessClickListener(OnAllLessClickListener onAllLessClickListener) {
        this.onAllLessClickListener = onAllLessClickListener;
    }

    private OnAllLessClickListener onAllLessClickListener;

    @Override
    public void onLessClick(int position) {
        if (onAllLessClickListener!=null){
            String orderDetailId = orderInfoList.get(position).getId();
            onAllLessClickListener.onLessClick(orderDetailId);
        }
        dismiss();
    }

    public interface  OnAllLessClickListener{
         void onLessClick(String orderDetailId);
    }
    public interface OnRemoveCallBack {
        void onRemoveSuccess(String id);
    }
}
