package com.yun.mayi.pda.module.join.manager;

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
 * 名称：打印详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackManagerAdapter extends RecyclerView.Adapter<PackManagerAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private OnPackChangeListener onPackChangeListener;
    private List<PickOrder> pickOrderList;

    public PackManagerAdapter(List<PickOrder> pickOrderList) {
        this.pickOrderList = pickOrderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pack_manager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PickOrder pickOrder = pickOrderList.get(position);
        holder.tvOrderId.setText(String.valueOf(pickOrder.getOrder_id()));
        holder.tvSign.setText(String.valueOf(pickOrder.getMark()));

        holder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(view, position);
                }
            }
        });
        holder.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPackChangeListener!=null){
                    onPackChangeListener.onPickChange(view,position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId;
        TextView tvSign;
        TextView tvCancel;
        TextView tvDetail;


        ViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvCancel = itemView.findViewById(R.id.tv_cancel);
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

    public void setOnPackChangeListener(OnPackChangeListener onPackChangeListener) {
        this.onPackChangeListener = onPackChangeListener;
    }

    public  interface  OnPackChangeListener{
        void  onPickChange(View view ,int position);
    }
}
