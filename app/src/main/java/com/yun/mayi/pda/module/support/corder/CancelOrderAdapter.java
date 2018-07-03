package com.yun.mayi.pda.module.support.corder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PackBox;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2017/01/06
 * * 名称：取消订单适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class CancelOrderAdapter extends RecyclerView.Adapter<CancelOrderAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<PackBox> packBoxList;

    public CancelOrderAdapter(List<PackBox> packBoxList) {
        this.packBoxList = packBoxList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cancle_order, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PackBox packBox = packBoxList.get(position);
        holder.tvBoxNum.setText(String.valueOf(packBox.getBoxId()));
        holder.tvSign.setText(packBox.getOrderMark());
        holder.tvSku.setText(String.valueOf(packBox.getItemNum()));
        if (packBox.getDetails() == null) {
            holder.tvSku.setText(String.valueOf(0));
        }
        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSku;
        TextView tvSign;
        TextView tvBoxNum;
        TextView tvDetail;

        ViewHolder(View itemView) {
            super(itemView);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvSku = itemView.findViewById(R.id.tv_sku);
            tvBoxNum = itemView.findViewById(R.id.tv_box_num);
            tvDetail = itemView.findViewById(R.id.tv_detail);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return packBoxList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
