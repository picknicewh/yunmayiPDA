package com.yun.mayi.pda.module.join.waitpack;

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
 * 时间： 2018/01/04
 * 名称：待拣货订单适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackedAdapter extends RecyclerView.Adapter<WaitPackedAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<PickOrder> pickOrderList;
    public WaitPackedAdapter(List<PickOrder> pickOrderList) {
        this.pickOrderList = pickOrderList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wait_parked, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PickOrder pickOrder = pickOrderList.get(position);
        holder.tvOrderId.setText(String.valueOf(pickOrder.getOrder_id()));
        holder.tvSign.setText(String.valueOf(pickOrder.getMark()));
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
        TextView tvOrderId;
        TextView tvSign;

        ViewHolder(View itemView) {
            super(itemView);
            tvParking = itemView.findViewById(R.id.tv_parking);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
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
}
