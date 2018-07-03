package com.yun.mayi.pda.module.support.sendnote;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PackNoteInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：送货单列表适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TotalWaitPackAdapter extends RecyclerView.Adapter<TotalWaitPackAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    /**
     * 购买数量列表
     */
    private Map<Integer, Integer> stockList;
    /**
     * 数据列表
     */
    private List<PackNoteInfo> noteInfoList;
    /**
     * 设置全缺监听
     */
    private onAllLessClickListener onAllLessClickListener;
    private OnNumberChangeListener onNumberChangeListener;
    public TotalWaitPackAdapter(List<PackNoteInfo> noteInfoList) {
        stockList = new HashMap<>();
        this.noteInfoList = noteInfoList;
        initData();
    }

    public void initData() {
        for (int i = 0; i < noteInfoList.size(); i++) {
            stockList.put(i, noteInfoList.get(i).getQuantity());
        }
        notifyDataSetChanged();
    }

    public Map<Integer, Integer> getStockList() {
        return stockList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total_wait_pack, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PackNoteInfo packNoteInfo = noteInfoList.get(position);
        holder.tvSpec.setText(packNoteInfo.getProduct_spec());
        holder.tvTitle.setText(packNoteInfo.getProduct_title());
        holder.tvStock.setText(String.valueOf(stockList.get(position)));
        holder.tvConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
        holder.tvAllLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAllLessClickListener != null) {
                    onAllLessClickListener.onLessClick(position);
                }
            }
        });
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockList.put(position, stockList.get(position) + 1);
                if (stockList.get(position)>=packNoteInfo.getReal_quantity()){
                    stockList.put(position, packNoteInfo.getReal_quantity());
                }
                if (onNumberChangeListener!=null){
                    int num=0;
                    for (int i=0;i<stockList.size();i++){
                        num+=stockList.get(i);
                    }
                    onNumberChangeListener.onNumberChange(num);
                }
                notifyDataSetChanged();
            }
        });
        holder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockList.put(position, stockList.get(position) - 1);
                if (stockList.get(position) <= 0) {
                    stockList.put(position, 0);
                }
                if (onNumberChangeListener!=null){
                    int num=0;
                    for (int i=0;i<stockList.size();i++){
                        num+=stockList.get(i);
                    }
                    onNumberChangeListener.onNumberChange(num);
                }
                notifyDataSetChanged();
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView ivAdd;
        ImageView ivMinus;
        TextView tvStock;
        TextView tvSpec;
        TextView tvAllLess;
        TextView tvConform;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivAdd = itemView.findViewById(R.id.iv_add);
            ivMinus = itemView.findViewById(R.id.iv_minus);
            tvStock = itemView.findViewById(R.id.tv_adjust_count);
            tvSpec = itemView.findViewById(R.id.tv_spec);
            tvAllLess = itemView.findViewById(R.id.tv_all_less);
            tvConform = itemView.findViewById(R.id.tv_conform);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return noteInfoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnAllLessClickListener(onAllLessClickListener onAllLessClickListener) {
        this.onAllLessClickListener = onAllLessClickListener;
    }

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public interface onAllLessClickListener {
        void onLessClick(int position);
    }

    public interface  OnNumberChangeListener{
        void onNumberChange(int num);
    }
}
