package com.yun.mayi.pda.module.drawer.tallying;

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
 * 名称：司机点货订单适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TallyingAdapter extends RecyclerView.Adapter<TallyingAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;

    private List<PickOrder> pickOrderList;

    public TallyingAdapter(List<PickOrder> pickOrderList) {

        this.pickOrderList = pickOrderList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tallying, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PickOrder pickOrder = pickOrderList.get(position);
        holder.tvOrderId.setText(pickOrder.getOrder_id());
        holder.tvSign.setText(pickOrder.getMark());
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
