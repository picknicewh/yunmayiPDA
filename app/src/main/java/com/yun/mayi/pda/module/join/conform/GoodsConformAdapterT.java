package com.yun.mayi.pda.module.join.conform;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.PackBoxInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：确认收货整箱商品适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GoodsConformAdapterT extends RecyclerView.Adapter<GoodsConformAdapterT.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<PackBoxInfo> packBoxDetailList;
    private onAllLessClickListener onAllLessClickListener;
    private Map<Integer, Integer> boxNumList;

    public GoodsConformAdapterT(List<PackBoxInfo> packBoxDetailList) {
        this.packBoxDetailList = packBoxDetailList;
        boxNumList = new HashMap<>();
    }

    public void initData() {
        for (int i = 0; i < packBoxDetailList.size(); i++) {
            PackBoxInfo packBoxInfo = packBoxDetailList.get(i);
            boxNumList.put(i, packBoxInfo.getBox_num());
        }
        notifyDataSetChanged();
    }

    public   Map<Integer, Integer> getBoxNum(){
        return boxNumList;
    }
    public String getBoxNumList() {
        String boxNums = "";
        for (int i = 0; i < boxNumList.size(); i++) {
            if (boxNumList.size() - 1 == i) {
                boxNums += boxNumList.get(i);
            } else {
                boxNums = boxNums + boxNumList.get(i) + ",";
            }
        }
        return boxNums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_conform_t, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PackBoxInfo packBoxInfo = packBoxDetailList.get(position);
        holder.tvBoxNumber.setText(String.valueOf(packBoxInfo.getBox_id()));
        holder.tvCount.setText(String.valueOf(packBoxInfo.getNum()));
        holder.tvSkuCount.setText(String.valueOf(packBoxInfo.getSku()));
        holder.tvSign.setText(packBoxInfo.getOrder_mark());
        if (packBoxInfo.getBox_num() == 1) {
            holder.llBoxOne.setVisibility(View.VISIBLE);
            holder.rlBoxNum.setVisibility(View.GONE);

        } else {
            holder.llBoxOne.setVisibility(View.GONE);
            holder.rlBoxNum.setVisibility(View.VISIBLE);
            String boxText = packBoxInfo.getBox_num() + "箱";
            holder.tvAdjustNum.setText(String.valueOf(boxNumList.get(position)));
            holder.tvBoxCount.setText(boxText);
        }
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxNumList.put(position, boxNumList.get(position) + 1);
                if (boxNumList.get(position) > packBoxInfo.getBox_num()) {
                    boxNumList.put(position, packBoxInfo.getBox_num());
                }
                notifyDataSetChanged();
            }
        });
        holder.ivMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boxNumList.put(position, boxNumList.get(position) - 1);
                if (boxNumList.get(position) <= 0) {
                    boxNumList.put(position, 0);
                }
                notifyDataSetChanged();
            }
        });
        holder.tvConformGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
        holder.tvLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onAllLessClickListener != null) {
                    onAllLessClickListener.onLessClick(position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBoxNumber;
        TextView tvSign;
        TextView tvSignText;
        TextView tvSkuCount;
        TextView tvCount;
        TextView tvConformGoods;
        TextView tvLess;
        LinearLayout llBoxOne;
        RelativeLayout rlBoxNum;
        ImageView ivAdd;
        ImageView ivMinus;
        TextView tvAdjustNum;
        TextView tvBoxCount;
        ViewHolder(View itemView) {
            super(itemView);
            tvBoxNumber = itemView.findViewById(R.id.tv_box_number);
            tvSign = itemView.findViewById(R.id.tv_sign);
            tvSignText = itemView.findViewById(R.id.tv_sign_text);
            tvSkuCount = itemView.findViewById(R.id.tv_sku_count);
            tvCount = itemView.findViewById(R.id.tv_count);
            tvConformGoods = itemView.findViewById(R.id.tv_conform_goods);
            tvLess = itemView.findViewById(R.id.tv_less);
            llBoxOne = itemView.findViewById(R.id.ll_box_one);
            rlBoxNum = itemView.findViewById(R.id.rl_box_num);
            ivAdd = itemView.findViewById(R.id.iv_add);
            ivMinus = itemView.findViewById(R.id.iv_minus);
            tvAdjustNum = itemView.findViewById(R.id.tv_adjust_count);
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

    public void setOnAllLessClickListener(onAllLessClickListener onAllLessClickListener) {
        this.onAllLessClickListener = onAllLessClickListener;
    }

    public interface onAllLessClickListener {
        void onLessClick(int position);
    }

}
