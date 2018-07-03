package com.yun.mayi.pda.module.join.waitpack;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.db.OrderInfo;
import com.yun.mayi.pda.db.OrderInfoDao;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：待拣货单详情单件商品适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackedDetailAdapterS extends RecyclerView.Adapter<WaitPackedDetailAdapterS.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    /**
     * 购买数量列表
     */
    private Map<Integer, Integer> stockList;
    /**
     * 设置全缺监听
     */
    private onAllLessClickListener onAllLessClickListener;
    private OnNumberChangeListener onNumberChangeListener;
    /**
     *数据列表
     */
    private List<OrderInfo> orderInfoList;
    /**
     *类型
     */
    private int type;
    /**
     * 数据库
     */
    private OrderInfoDao orderInfoDao;
    public WaitPackedDetailAdapterS(Context context,int type, List<OrderInfo> orderInfoList) {
        stockList = new HashMap<>();
        orderInfoDao = new OrderInfoDao(context);
        this.orderInfoList = orderInfoList;
        this.type = type;
        initData();
    }

    public void initData() {
        for (int i = 0; i < getItemCount(); i++) {
            OrderInfo orderInfo = orderInfoList.get(i);
            stockList.put(i, orderInfo.getNum());
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wait_packed_detail_s, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderInfo orderInfo = orderInfoList.get(position);
        holder.tvTitle.setText(orderInfo.getProductTitle());
        holder.tvSpec.setText(orderInfo.getProductSpec());
        holder.tvStock.setText(String.valueOf(stockList.get(position)));
        if (type==Constants.WAIT_PACK){
            holder.tvConform.setText("确认");
        }else {
            holder.tvConform.setText("移出");
        }
        final String orderDetailId = orderInfo.getId();
        final String orderId = orderInfo.getOrderId();
        holder.tvConform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   G.log("---xxxxxxx" + position + "入箱操作");

                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
        holder.tvAllLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    G.log("---xxxxxxx" + position+"点击tvAllLess");
                if (onAllLessClickListener != null) {
                    onAllLessClickListener.onLessClick(position);
                }
            }
        });
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockList.put(position, stockList.get(position) + 1);
                if (stockList.get(position)>=orderInfo.getOriginalNum()){
                    stockList.put(position,orderInfo.getOriginalNum());
                }
                orderInfoDao.updatePackNum(orderId, orderDetailId, stockList.get(position));
                if (onNumberChangeListener!=null){
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
                orderInfoDao.updatePackNum(orderId, orderDetailId, stockList.get(position));
                if (onNumberChangeListener!=null){
                    onNumberChangeListener.onNumberChange();
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
        return orderInfoList.size();
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
        void onNumberChange();
    }
}
