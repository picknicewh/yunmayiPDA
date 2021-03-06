package com.yun.mayi.pda.module.support.packed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.Order;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/01/06
 * 名称：已拣货订单适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HasPackAdapter extends RecyclerView.Adapter<HasPackAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private Context context;
    private List<Order> orderList;

    public HasPackAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_has_park, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Order order = orderList.get(position);
        holder.tvSign.setText(order.getMark());
        holder.tvSku.setText(String.valueOf(order.getSkuNum()));
        if (order.getBoxNum()==1){
            holder.llBoxCount.setVisibility(View.GONE);
        }else {
            holder.llBoxCount.setVisibility(View.VISIBLE);
            holder.tvBoxNum.setText(String.valueOf(order.getBoxNum())+"箱");
        }
        holder.tvParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvParking;
        TextView tvSku;
        TextView tvSign;
        TextView tvBoxNum;
        LinearLayout llBoxCount;
        ViewHolder(View itemView) {
            super(itemView);
            tvParking = itemView.findViewById(R.id.tv_parking);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvSku = itemView.findViewById(R.id.tv_sku);
            tvBoxNum = itemView.findViewById(R.id.tv_box_count);
            llBoxCount = itemView.findViewById(R.id.ll_box_count);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
