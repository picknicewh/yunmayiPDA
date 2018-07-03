package com.yun.mayi.pda.module.drawer.depart;

import android.graphics.Color;
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
 * 名称：司机发车订单适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class DepartAdapter extends RecyclerView.Adapter<DepartAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;

    private List<PickOrder> pickOrderList;

    public DepartAdapter(List<PickOrder> pickOrderList) {

        this.pickOrderList = pickOrderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_depart, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PickOrder pickOrder = pickOrderList.get(position);
        holder.tvOrderId.setText(pickOrder.getOrder_id());
        holder.tvSign.setText(pickOrder.getMark());
        if (pickOrder.getState() == 1 ) {
            holder.tvParkCar.setText("发车");
            holder.tvParkCar.setBackgroundResource(R.drawable.round_orange);
            holder.tvParkCar.setTextColor(Color.WHITE);
            holder.tvParkCar.setEnabled(true);
        }  else if ((pickOrder.getState() == 2 || pickOrder.getState() == 3) && pickOrder.getIs_pick() == 3) {
            holder.tvParkCar.setText("已完成");
            holder.tvParkCar.setBackgroundColor(Color.WHITE);
            holder.tvParkCar.setTextColor(Color.RED);
            holder.tvParkCar.setEnabled(false);
        }else if (pickOrder.getState() == 2 ) {
            holder.tvParkCar.setText("配送中");
            holder.tvParkCar.setBackgroundColor(Color.WHITE);
            holder.tvParkCar.setTextColor(Color.RED);
            holder.tvParkCar.setEnabled(false);
        }
        holder.tvParkCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvParkCar;
        TextView tvSign;
        TextView tvOrderId;

        ViewHolder(View itemView) {
            super(itemView);
            tvParkCar = itemView.findViewById(R.id.tv_park_car);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvSign = itemView.findViewById(R.id.tv_sign);
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
