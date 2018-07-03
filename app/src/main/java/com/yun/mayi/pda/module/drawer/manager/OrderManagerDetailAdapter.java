package com.yun.mayi.pda.module.drawer.manager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/01/08
 * 名称：订单管理详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class OrderManagerDetailAdapter extends RecyclerView.Adapter<OrderManagerDetailAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<ReceiptPrintInfo.DetailInfo> printInfoDetailList;

    public OrderManagerDetailAdapter(List<ReceiptPrintInfo.DetailInfo> printInfoDetailList) {
        this.printInfoDetailList = printInfoDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manager_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ReceiptPrintInfo.DetailInfo detailInfo = printInfoDetailList.get(position);
        holder.tvCount.setText(String.valueOf(detailInfo.getQuantity()));
        holder.tvLessCount.setText(String.valueOf(detailInfo.getOutOfStockNum()));
        holder.tvName.setText(detailInfo.getProductTitle());
        holder.tvLessMoney.setText(String.valueOf(detailInfo.getOutPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvLessCount;
        TextView tvLessMoney;
        TextView tvCount;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvLessCount = itemView.findViewById(R.id.tv_less_count);
            tvLessMoney = itemView.findViewById(R.id.tv_less_money);
            tvCount = itemView.findViewById(R.id.tv_count);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return printInfoDetailList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
