package com.yun.mayi.pda.module.drawer.tallying;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.TallyingInfoDetail;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/05/25
 * 名称：司机点货详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TallyingDetailAdapter extends RecyclerView.Adapter<TallyingDetailAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    /**
     * 购买数量列表
     */
    private Map<Integer, Integer> stockList;
    /**
     * 设置全缺监听
     */
    private onAllLessClickListener onAllLessClickListener;
    /**
     * 数字改变监听
     */
    private OnNumberChangeListener onNumberChangeListener;
    /**
     *类型
     */
    private int type;
    private List<TallyingInfoDetail> tallyingInfoDetailList;
    private  int isFirst ;
    public TallyingDetailAdapter(int type, List<TallyingInfoDetail> tallyingInfoDetailList,int isFirst) {
        stockList = new HashMap<>();
        this.tallyingInfoDetailList = tallyingInfoDetailList;
        this.type = type;
        this.isFirst = isFirst;
        initData();
    }

    public void initData() {
        stockList.clear();
        for (int i = 0; i < getItemCount(); i++) {
            TallyingInfoDetail tallyingInfoDetail = tallyingInfoDetailList.get(i);
            if (isFirst==0){
                stockList.put(i, tallyingInfoDetail.getLoadingQuantity());//如果第一次点货则，获取加载数量
            }else {
                stockList.put(i, tallyingInfoDetail.getDeliverQuantity());//订单管理点货获取配送数量
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tallying_detail, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
         final TallyingInfoDetail tallyingInfoDetail = tallyingInfoDetailList.get(position);
        holder.tvTitle.setText(tallyingInfoDetail.getProductTitle());
        holder.tvSpec.setText(tallyingInfoDetail.getProductSpec());
        holder.tvStock.setText(String.valueOf(stockList.get(position)));

        if (type==Constants.WAIT_PACK){
            holder.tvConform.setText("确认");
            holder.tvAllLess.setVisibility(View.VISIBLE);
        }else {
            holder.tvConform.setText("移出");
            holder.tvAllLess.setVisibility(View.GONE);
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
                if (stockList.get(position)>=tallyingInfoDetail.getQuantity()){
                    stockList.put(position,tallyingInfoDetail.getQuantity());
                }
                if (onNumberChangeListener!=null){
                    onNumberChangeListener.onNumberChange(position,stockList.get(position));
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
                    onNumberChangeListener.onNumberChange(position,stockList.get(position));
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
        return tallyingInfoDetailList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public void setOnAllLessClickListener(onAllLessClickListener onAllLessClickListener) {
        this.onAllLessClickListener = onAllLessClickListener;
    }

    public interface onAllLessClickListener {
        void onLessClick(int position);
    }
   public interface OnNumberChangeListener{
        void onNumberChange(int position,int num);
   }
}
