package com.yun.mayi.pda.module.join.conform;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PackDetailInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：确认收货单件商品适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GoodsConformAdapterS extends RecyclerView.Adapter<GoodsConformAdapterS.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    /**
     * 购买数量列表
     */
    private Map<Integer, Integer> stockList;
    /**
     * 设置全缺监听
     */
    private onAllLessClickListener onAllLessClickListener;
    private List<PackDetailInfo> packDetailInfoList;
    private OnNumberChangeListener onNumberChangeListener;
    private String agentNumber;
    public GoodsConformAdapterS(List<PackDetailInfo> packDetailInfoList, String agentNumber) {
        stockList = new HashMap<>();
        this.packDetailInfoList = packDetailInfoList;
        this.agentNumber = agentNumber;
        initData();
    }

    public void initData() {
        for (int i = 0; i < getItemCount(); i++) {
            stockList.put(i, packDetailInfoList.get(i).getNum());
        }
        notifyDataSetChanged();
    }

    public Map<Integer, Integer> getStockList() {
        return stockList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_conform_s, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PackDetailInfo packDetailInfo = packDetailInfoList.get(position);
        holder.tvSpec.setText(packDetailInfo.getProduct_spec());
        holder.tvStock.setText(String.valueOf(stockList.get(position)));
        holder.tvTitle.setText(packDetailInfo.getProduct_title());
        holder.tvOrgNum.setText(packDetailInfo.getNum()+"件");
        holder.tvPackedNum.setText(packDetailInfo.getReceive_num()+"件");
        if (agentNumber.equals("0")){
             holder.rlSingle.setVisibility(View.GONE);
             holder.rlTotal.setVisibility(View.VISIBLE);
        }else {
             holder.rlSingle.setVisibility(View.VISIBLE);
            holder.rlTotal.setVisibility(View.GONE);
        }
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
                if (stockList.get(position)>=packDetailInfo.getOrigin_num()){
                    stockList.put(position, packDetailInfo.getOrigin_num());
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
        TextView tvOrgNum;
        TextView tvPackedNum;
        RelativeLayout rlSingle;
        RelativeLayout rlTotal;
        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivAdd = itemView.findViewById(R.id.iv_add);
            ivMinus = itemView.findViewById(R.id.iv_minus);
            tvStock = itemView.findViewById(R.id.tv_adjust_count);
            tvSpec = itemView.findViewById(R.id.tv_spec);
            tvAllLess = itemView.findViewById(R.id.tv_all_less);
            tvConform = itemView.findViewById(R.id.tv_conform);
            rlSingle = itemView.findViewById(R.id.rl_single);
            rlTotal = itemView.findViewById(R.id.rl_total);
            tvOrgNum = itemView.findViewById(R.id.tv_org_num);
            tvPackedNum  = itemView.findViewById(R.id.tv_packed_num);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return packDetailInfoList.size();
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

    public interface OnNumberChangeListener{
        void onNumberChange(int num);
    }
}
