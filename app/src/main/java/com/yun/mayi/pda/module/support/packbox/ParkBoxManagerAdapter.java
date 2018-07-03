package com.yun.mayi.pda.module.support.packbox;

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
 * 时间： 2017/11/21
 * 名称：店铺订单管理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ParkBoxManagerAdapter extends RecyclerView.Adapter<ParkBoxManagerAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;

    private List<PackBox> packBoxList;

    public ParkBoxManagerAdapter(List<PackBox> packBoxList) {

        this.packBoxList = packBoxList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_park_box_manager, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PackBox packBox = packBoxList.get(position);
        holder.tvBoxNum.setText(String.valueOf(packBox.getBoxId()));
        holder.tvSign.setText(packBox.getOrderMark());
        holder.tvSku.setText(String.valueOf(packBox.getItemNum()));
        if (packBox.getBoxNum()>1){
            holder.tvBoxCount.setVisibility(View.VISIBLE);
            holder.tvBoxCount.setText(String.valueOf(packBox.getBoxNum()) + "箱");
        }else {
            holder.tvBoxCount.setVisibility(View.GONE);
        }
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
        TextView tvSku;
        TextView tvSign;
        TextView tvBoxNum;
        TextView tvBoxCount;

        ViewHolder(View itemView) {
            super(itemView);
            tvDetail = itemView.findViewById(R.id.tv_detail);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvSku = itemView.findViewById(R.id.tv_sku);
            tvBoxNum = itemView.findViewById(R.id.tv_box_num);
            tvBoxCount = itemView.findViewById(R.id.tv_box_count);
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
