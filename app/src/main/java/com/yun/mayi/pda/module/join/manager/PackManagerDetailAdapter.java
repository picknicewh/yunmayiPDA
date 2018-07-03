package com.yun.mayi.pda.module.join.manager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PickDetailInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.PriceTransfer;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/01/08
 * 名称：打印详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackManagerDetailAdapter extends RecyclerView.Adapter<PackManagerDetailAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<PickDetailInfo> pickDetailInfoList;

    public PackManagerDetailAdapter(List<PickDetailInfo> pickDetailInfoList) {
        this.pickDetailInfoList = pickDetailInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pack_manager_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PickDetailInfo pickDetailInfo = pickDetailInfoList.get(position);
        holder.tvCount.setText(String.valueOf(pickDetailInfo.getTotalQuantity()));
        holder.tvLessCount.setText(String.valueOf(pickDetailInfo.getOutNum()));
        holder.tvName.setText(pickDetailInfo.getProductTitle());
        holder.tvLessMoney.setText(PriceTransfer.chageMoneyToString((double) pickDetailInfo.getOutPrice()));
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
        return pickDetailInfoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
