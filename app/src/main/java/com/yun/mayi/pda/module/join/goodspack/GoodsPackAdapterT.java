package com.yun.mayi.pda.module.join.goodspack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PackBoxDetail2;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/3/30
 * 名称：整箱按货分拣商品适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GoodsPackAdapterT extends RecyclerView.Adapter<GoodsPackAdapterT.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<PackBoxDetail2> packBoxDetailList;

    public GoodsPackAdapterT(List<PackBoxDetail2> packBoxDetailList) {
        this.packBoxDetailList = packBoxDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_pack_t, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PackBoxDetail2 packBoxDetail = packBoxDetailList.get(position);
        holder.tvBoxNumber.setText(String.valueOf(packBoxDetail.getBox_id()));
        holder.tvSign.setText(packBoxDetail.getOrder_mark());
        if (packBoxDetail.getBox_num()==1){
            holder.llBoxCount.setVisibility(View.GONE);
        }else {
            holder.llBoxCount.setVisibility(View.VISIBLE);
            holder.tvBoxCount.setText(String.valueOf(packBoxDetail.getBox_num())+"箱");
        }
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
        TextView tvBoxNumber;
        LinearLayout llBoxCount;
        TextView tvBoxCount;

        ViewHolder(View itemView) {
            super(itemView);
            tvBoxNumber = itemView.findViewById(R.id.tv_box_number);
            tvSign = itemView.findViewById(R.id.tv_sign);
            llBoxCount = itemView.findViewById(R.id.ll_box_count);
            tvBoxCount = itemView.findViewById(R.id.tv_box_count);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return packBoxDetailList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
