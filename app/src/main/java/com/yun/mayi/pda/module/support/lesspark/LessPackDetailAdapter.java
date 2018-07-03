package com.yun.mayi.pda.module.support.lesspark;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PackBoxDetail;

import java.util.List;


/**
 * 作者： wh
 * 时间： 2018/01/06
 * 名称：当日缺货详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LessPackDetailAdapter extends RecyclerView.Adapter<LessPackDetailAdapter.ViewHolder> {

    private List<PackBoxDetail> packBoxDetailList;

    public LessPackDetailAdapter(List<PackBoxDetail> packBoxDetailList) {
        this.packBoxDetailList = packBoxDetailList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_less_park_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PackBoxDetail packBoxDetail = packBoxDetailList.get(position);
        holder.tvSign.setText(packBoxDetail.getOrderMark());
        holder.tvLessCount.setText(String.valueOf((packBoxDetail.getOriginNum() - packBoxDetail.getNum())));
        holder.tvBoxNum.setText(String.valueOf(packBoxDetail.getBoxId()));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvLessCount;
        TextView tvBoxNum;
        TextView tvSign;

        ViewHolder(View itemView) {
            super(itemView);

            tvBoxNum = itemView.findViewById(R.id.tv_box_num);
            tvLessCount = itemView.findViewById(R.id.tv_less_count);
            tvSign = itemView.findViewById(R.id.tv_sign);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return packBoxDetailList.size();
    }


}
