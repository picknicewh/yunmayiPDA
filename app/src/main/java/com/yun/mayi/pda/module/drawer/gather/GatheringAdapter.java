package com.yun.mayi.pda.module.drawer.gather;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.ReceptOrderInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.G;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/06/01
 * 名称：订单收款适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GatheringAdapter extends RecyclerView.Adapter<GatheringAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<ReceptOrderInfo> orderInfoList;
    public GatheringAdapter(List<ReceptOrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gathing, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ReceptOrderInfo orderInfo = orderInfoList.get(position);
        holder.tvOrderId.setText(String.valueOf(orderInfo.getOrderId()));
        holder.tvSign.setText(String.valueOf(orderInfo.getMark()));
        if (orderInfo.getIsPay()==0){
            holder.tvGathering.setVisibility(View.VISIBLE);
            holder.tvHasGather.setVisibility(View.GONE);
            holder.tvPayWay.setVisibility(View.GONE);
        }else {
            holder.tvGathering.setVisibility(View.GONE);
            holder.tvHasGather.setVisibility(View.VISIBLE);
            holder.tvPayWay.setVisibility(View.VISIBLE);
            holder.tvPayWay.setText(G.getPayWay(orderInfo.getPayType(),orderInfo.getPayPlatform()));
        }
        holder.tvGathering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
        holder.tvPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPrintClickListener!=null){
                    onPrintClickListener.onPrint(position);
                }
            }
        });
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGathering;
        TextView tvPrint;
        TextView tvOrderId;
        TextView tvSign;
        TextView tvHasGather;
        TextView tvPayWay;
        ViewHolder(View itemView) {
            super(itemView);
            tvGathering = itemView.findViewById(R.id.tv_gathering);
            tvPrint = itemView.findViewById(R.id.tv_print);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvOrderId = itemView.findViewById(R.id.tv_order_id);
            tvHasGather = itemView.findViewById(R.id.tv_has_gather);
            tvPayWay = itemView.findViewById(R.id.tv_pay_way);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return orderInfoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnPrintClickListener(OnPrintClickListener onPrintClickListener) {
        this.onPrintClickListener = onPrintClickListener;
    }

    private OnPrintClickListener onPrintClickListener;
    public interface  OnPrintClickListener{
        void onPrint(int position);
    }
}
