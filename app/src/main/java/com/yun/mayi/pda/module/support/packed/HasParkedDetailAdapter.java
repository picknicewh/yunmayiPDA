package com.yun.mayi.pda.module.support.packed;

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
 * 时间： 2018/1/16
 * 名称：已拣货打包详情列表适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HasParkedDetailAdapter extends RecyclerView.Adapter<HasParkedDetailAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;

    private List<PackBox> packBoxList;

    public HasParkedDetailAdapter(List<PackBox> packBoxList) {
        this.packBoxList = packBoxList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_has_park_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PackBox packBox = packBoxList.get(position);
        holder.tvBoxNum.setText(String.valueOf(packBox.getBoxId()));
        holder.tvSign.setText(packBox.getOrderMark());
        holder.tvSku.setText(String.valueOf(packBox.getItemNum()));
        if (packBox.getBoxNum() == 1) {
            holder.tvBoxCount.setVisibility(View.GONE);
        } else {
            holder.tvBoxCount.setText(String.valueOf(packBox.getBoxNum()) + "箱");
            holder.tvBoxCount.setVisibility(View.VISIBLE);
        }
        holder.tvPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvPrint;
        TextView tvSku;
        TextView tvSign;
        TextView tvBoxNum;
        TextView tvBoxCount;

        ViewHolder(View itemView) {
            super(itemView);
            tvPrint = itemView.findViewById(R.id.tv_print);
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
