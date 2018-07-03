package com.yun.mayi.pda.module.support.packtotal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PackDetailInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：送货单列表适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TotalHasPackAdapter extends RecyclerView.Adapter<TotalHasPackAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    /**
     * 数据列表
     */
    private List<PackDetailInfo> hasPackInfoList;

    public TotalHasPackAdapter(List<PackDetailInfo> hasPackInfoList) {
        this.hasPackInfoList = hasPackInfoList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total_pack, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PackDetailInfo hasPackInfo = hasPackInfoList.get(position);
        holder.tvSpec.setText(hasPackInfo.getProduct_spec());
        holder.tvTitle.setText(hasPackInfo.getProduct_title());
        holder.tvNum.setText(String.valueOf(hasPackInfo.getOrigin_num()));
        holder.tvActualNum.setText(String.valueOf(hasPackInfo.getNum()));
        holder.tvConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSpec;
        TextView tvNum;
        TextView tvActualNum;
        TextView tvConform;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSpec = itemView.findViewById(R.id.tv_spec);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvActualNum = itemView.findViewById(R.id.tv_actual_num);
            tvConform = itemView.findViewById(R.id.tv_conform);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return hasPackInfoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
