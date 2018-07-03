package com.yun.mayi.pda.module.support.packbox;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PackBoxDetail;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/01/08
 * 名称：打印详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackBoxDetailAdapter extends RecyclerView.Adapter<PackBoxDetailAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private Map<Integer, Integer> stockList;
    private List<PackBoxDetail> packBoxDetailList;

    public PackBoxDetailAdapter(List<PackBoxDetail> packBoxDetailList) {
        stockList = new HashMap<>();
        this.packBoxDetailList = packBoxDetailList;

    }

    public void initData() {
        for (int i = 0; i < packBoxDetailList.size(); i++) {
            stockList.put(i, packBoxDetailList.get(i).getNum());
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_print_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        PackBoxDetail packBoxDetail = packBoxDetailList.get(position);
        holder.tvBoxNum.setText(String.valueOf(stockList.get(position)));
        holder.tvSpec.setText(packBoxDetail.getProductSpec());
        holder.tvTitle.setText(packBoxDetail.getProductTitle());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSpec;
        TextView tvBoxNum;
        ViewHolder(View itemView) {
            super(itemView);
            tvSpec = itemView.findViewById(R.id.tv_spec);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvBoxNum = itemView.findViewById(R.id.tv_adjust_count);
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
