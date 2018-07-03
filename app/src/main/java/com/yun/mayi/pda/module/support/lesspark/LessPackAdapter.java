package com.yun.mayi.pda.module.support.lesspark;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.OrderDetail;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/01/06
 * 名称：当日缺货适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LessPackAdapter extends RecyclerView.Adapter<LessPackAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<OrderDetail> orderDetailList;

    public LessPackAdapter(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_less_park, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderDetail orderDetail = orderDetailList.get(position);
        holder.tvCount.setText(String.valueOf(orderDetail.getOutOfStockNum()));
        holder.tvName.setText(orderDetail.getProductTitle());
        holder.tvSpec.setText(orderDetail.getProductSpec());
        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetail;
        TextView tvName;
        TextView tvCount;
        TextView tvSpec;

        ViewHolder(View itemView) {
            super(itemView);
            tvDetail = itemView.findViewById(R.id.tv_detail);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSpec = itemView.findViewById(R.id.tv_spec);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
