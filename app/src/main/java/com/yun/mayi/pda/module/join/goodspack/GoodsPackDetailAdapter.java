package com.yun.mayi.pda.module.join.goodspack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PickOrderDetail;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/3/31
 * 名称：按货分拣详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GoodsPackDetailAdapter extends RecyclerView.Adapter<GoodsPackDetailAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;

    private List<PickOrderDetail> pickOrderDetailList;
    public GoodsPackDetailAdapter( List<PickOrderDetail> pickOrderDetailList) {
        this.pickOrderDetailList = pickOrderDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_pack_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PickOrderDetail pickOrderDetail = pickOrderDetailList.get(position);
        holder.tvNum.setText(String.valueOf(pickOrderDetail.getTotal_quantity()));
        holder.tvSign.setText(pickOrderDetail.getMark());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSign;
        TextView tvNum;

        ViewHolder(View itemView) {
            super(itemView);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvNum = itemView.findViewById(R.id.tv_num);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return pickOrderDetailList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
