package com.yun.mayi.pda.module.drawer.manager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PickOrder;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/01/08
 * 名称：订单收款适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class OrderManagerAdapter extends RecyclerView.Adapter<OrderManagerAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private OnUpLoadListener onUpLoadListener;
    private List<PickOrder> pickOrderList;

    public OrderManagerAdapter(  List<PickOrder> pickOrderList) {
    this.pickOrderList = pickOrderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PickOrder pickOrder = pickOrderList.get(position);
        holder.tvOrderId.setText(pickOrder.getOrder_id());
        holder.tvSign.setText(pickOrder.getMark());
        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, position);
                }
            }
        });
        holder.tvUpLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onUpLoadListener!=null){
                    onUpLoadListener.onUpLoad(view,position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId;
        TextView tvSign;
        TextView tvUpLoad;
        TextView tvDetail;


        ViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvUpLoad = itemView.findViewById(R.id.tv_upload);
            tvDetail = itemView.findViewById(R.id.tv_detail);

            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return pickOrderList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnUpLoadListener(OnUpLoadListener onUpLoadListener) {
        this.onUpLoadListener = onUpLoadListener;
    }

    public  interface  OnUpLoadListener{
        void   onUpLoad(View view, int position);
    }
}
