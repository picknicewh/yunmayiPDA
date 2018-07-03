package com.yun.mayi.pda.module.join.waitpack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.db.BoxInfo;
import com.yun.mayi.pda.db.BoxInfoDao;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：待拣货单详情整箱商品适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackedDetailAdapterT extends RecyclerView.Adapter<WaitPackedDetailAdapterT.ViewHolder> {
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
     * 装箱列表
     */
    private List<BoxInfo> boxInfoList;
    /**
     * 类型
     */
    private int type;
    private BoxInfoDao boxInfoDao;
    private OnNumberChangeListener onNumberChangeListener;

    public WaitPackedDetailAdapterT(Context context, int type, List<BoxInfo> boxInfoList) {
        stockList = new HashMap<>();
        boxInfoDao = new BoxInfoDao(context);
        this.boxInfoList = boxInfoList;
        this.type = type;
        initData();
    }

    public void initData() {
        for (int i = 0; i < getItemCount(); i++) {
            BoxInfo boxInfo = boxInfoList.get(i);
            stockList.put(i, boxInfo.getNum());
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wait_packed_detail_t, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BoxInfo boxInfo = boxInfoList.get(position);
        holder.tvBoxNumber.setText(String.valueOf(boxInfo.getBoxId()));
        holder.tvSpec.setText(boxInfo.getProductSpec());
        holder.tvTitle.setText(boxInfo.getProductTitle());
        final String orderId = boxInfo.getOrderId();
        final String orderDetailId = boxInfo.getId();
        if (boxInfo.getBoxNum() == 1) {
            holder.rlBoxNum.setVisibility(View.GONE);
        } else {
            holder.rlBoxNum.setVisibility(View.VISIBLE);
            holder.tvAdjustCount.setText(String.valueOf(stockList.get(position)));
            holder.tvBoxNum.setText(String.valueOf(boxInfo.getBoxNum()));
        }
        if (type == Constants.WAIT_PACK) {
            holder.tvConform.setText("确认");
            holder.tvLessPark.setVisibility(View.VISIBLE);
        } else {
            holder.tvConform.setText("移出");
            holder.tvLessPark.setVisibility(View.GONE);
        }
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockList.put(position, stockList.get(position) + 1);
                if (stockList.get(position) > boxInfo.getOriginalNum()) {
                    stockList.put(position, boxInfo.getOriginalNum());
                }
                boxInfoDao.updatePackNum(orderId, orderDetailId, stockList.get(position));
                if (onNumberChangeListener != null) {
                    onNumberChangeListener.onNumberChange();
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
                boxInfoDao.updatePackNum(orderId, orderDetailId, stockList.get(position));
                if (onNumberChangeListener != null) {
                    onNumberChangeListener.onNumberChange();
                }
                notifyDataSetChanged();
            }
        });
        holder.tvConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
        holder.tvLessPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.log("------" + "点击tvAllLess");
                if (onAllLessClickListener != null) {
                    onAllLessClickListener.onLessClick(position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvSpec;
        TextView tvBoxNumber;
        TextView tvLessPark;
        TextView tvConform;
        TextView tvBoxNum;
        ImageView ivAdd;
        ImageView ivMinus;
        TextView tvAdjustCount;
        RelativeLayout rlBoxNum;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSpec = itemView.findViewById(R.id.tv_spec);
            tvBoxNumber = itemView.findViewById(R.id.tv_box_number);
            tvBoxNum = itemView.findViewById(R.id.tv_box_num);
            ivAdd = itemView.findViewById(R.id.iv_add);
            ivMinus = itemView.findViewById(R.id.iv_minus);
            tvAdjustCount = itemView.findViewById(R.id.tv_adjust_count);
            tvLessPark = itemView.findViewById(R.id.tv_less_park);
            tvConform = itemView.findViewById(R.id.tv_conform);
            rlBoxNum = itemView.findViewById(R.id.rl_box_num);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return boxInfoList.size();
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

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }


    public interface OnNumberChangeListener {
        void onNumberChange();
    }
}
