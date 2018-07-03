package com.yun.mayi.pda.module.support.packing;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.db.OrderInfo;
import com.yun.mayi.pda.db.OrderInfoDao;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/01/06
 * 名称：已拣/未拣货单详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackDetailAdapter extends RecyclerView.Adapter<WaitPackDetailAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private int type;
    /**
     * 购买数量列表
     */
    private Map<Integer, Integer> stockList;
    /**
     * 数据列表
     */
    private List<OrderInfo> orderInfoList;
    /**
     * 设置全缺监听
     */
    private onAllLessClickListener onAllLessClickListener;
    /**
     * 设置取消选中监听
     */
    private OnCheckChangeListener onCheckChangeListener;
    private OnTitleClickListener onTitleClickListener;
    /**
     * 数据库操作
     */
    private OrderInfoDao orderInfoDao;

    public WaitPackDetailAdapter(Context context, int type, List<OrderInfo> orderInfoList) {
        orderInfoDao = new OrderInfoDao(context);
        this.type = type;
        stockList = new HashMap<>();
        this.orderInfoList = orderInfoList;
        initData();
    }

    public void initData() {
        for (int i = 0; i < orderInfoList.size(); i++) {
            stockList.put(i, orderInfoList.get(i).getNum());
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wait_park_detail, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderInfo orderInfo = orderInfoList.get(position);
        holder.tvSpec.setText(orderInfo.getProductSpec());
        holder.tvTitle.setText(orderInfo.getProductTitle());
        holder.tvStock.setText(String.valueOf(stockList.get(position)));
        final String orderDetailId = orderInfo.getId();
        final String orderId = orderInfo.getOrderId();
        switch (type) {
            case Constants.WAIT_PACK:
                holder.cbBox.setVisibility(View.GONE);
                holder.tvParkStock.setText("入箱");
                holder.tvParkStock.setBackgroundResource(R.drawable.round_orange);
                holder.tvAllLess.setVisibility(View.VISIBLE);
                break;
            case Constants.HAS_PACK:
                holder.cbBox.setVisibility(View.VISIBLE);
                holder.cbBox.setChecked(orderInfo.getIsChecked() == 1);
                if (stockList.get(position) != orderInfo.getOriginalNum()) {
                    holder.tvStock.setTextColor(Color.RED);
                } else {
                    holder.tvStock.setTextColor(Color.BLACK);
                }
                holder.tvParkStock.setText("移出");
                holder.tvAllLess.setVisibility(View.GONE);
                break;
        }
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTitleClickListener!=null){
                    onTitleClickListener.onTitleClick(position);
                }
            }
        });
        holder.tvParkStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
        holder.cbBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onCheckChangeListener != null) {
                    onCheckChangeListener.onChecked(position, holder.cbBox.isChecked());
                }
            }
        });
        holder.tvAllLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.log("------" + "点击tvAllLess");
                if (onAllLessClickListener != null) {
                    onAllLessClickListener.onLessClick(position);
                }
            }
        });
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockList.put(position, stockList.get(position) + 1);
                if (stockList.get(position) >= orderInfo.getOriginalNum()) {
                    stockList.put(position, orderInfo.getOriginalNum());
                }
                orderInfoDao.updatePackNum(orderId, orderDetailId, stockList.get(position));
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
                orderInfoDao.updatePackNum(orderId, orderDetailId, stockList.get(position));
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
        TextView tvParkStock;
        CheckBox cbBox;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivAdd = itemView.findViewById(R.id.iv_add);
            ivMinus = itemView.findViewById(R.id.iv_minus);
            tvStock = itemView.findViewById(R.id.tv_adjust_count);
            tvSpec = itemView.findViewById(R.id.tv_spec);
            tvAllLess = itemView.findViewById(R.id.tv_all_less);
            tvParkStock = itemView.findViewById(R.id.tv_park_stock);
            cbBox = itemView.findViewById(R.id.cb_box);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return orderInfoList.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnAllLessClickListener(onAllLessClickListener onAllLessClickListener) {
        this.onAllLessClickListener = onAllLessClickListener;
    }

    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public interface onAllLessClickListener {
        void onLessClick(int position);
    }


    public interface OnTitleClickListener{
        void onTitleClick(int position);
    }
    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
    }


    public interface OnCheckChangeListener {
        void onChecked(int position, boolean isChecked);
    }
}
