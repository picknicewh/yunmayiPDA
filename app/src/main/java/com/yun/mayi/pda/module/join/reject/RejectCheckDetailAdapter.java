package com.yun.mayi.pda.module.join.reject;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.RejectVo;
import com.yun.mayi.pda.module.common.OnItemClickListener;
import com.yun.mayi.pda.utils.CashierInputFilter;
import com.yun.mayi.pda.utils.Constants;
import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.PriceTransfer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/03/16
 * 名称：退货审核适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class RejectCheckDetailAdapter extends RecyclerView.Adapter<RejectCheckDetailAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    /**
     * 购买数量列表
     */
    private Map<Integer, Integer> stockList;
    /**
     * 是否选中列表
     */
    private Map<Integer, Boolean> selectList;
    /**
     * 进价列表
     */
    private Map<Integer, Double> priceList;
    /**
     * 设置取消选中监听
     */
    private OnCheckChangeListener onCheckChangeListener;
    private OnTitleClickListener onTitleClickListener;
    private List<RejectVo> rejectVoList;
    private int state;

    public RejectCheckDetailAdapter(List<RejectVo> rejectVoList, int state) {
        this.rejectVoList = rejectVoList;
        this.state = state;

    }

    public void initData(int state) {
        this.state = state;
        stockList = new HashMap<>();
        selectList = new HashMap<>();
        priceList = new HashMap<>();

        for (int i = 0; i < rejectVoList.size(); i++) {
            RejectVo rejectVo = rejectVoList.get(i);
            int num = rejectVo.getOrigin_return_goods_number();
            double totalPrice = (num * rejectVo.getProduct_sell_price()) / 100;
            stockList.put(i, num);
            selectList.put(i, false);
            priceList.put(i, totalPrice);
        }
        notifyDataSetChanged();
    }

    public Map<Integer, Integer> getStockList() {
        return stockList;
    }

    public Map<Integer, Double> getPriceList() {
        return priceList;
    }

    public Map<Integer, Boolean> getSelectList() {
        return selectList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reject_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RejectVo rejectVo = rejectVoList.get(position);
        holder.tvSpec.setText(rejectVo.getProduct_spec());
        holder.tvTitle.setText(rejectVo.getProduct_title());
        holder.tvSellPrice.setText("售价:" + PriceTransfer.chageMoneyToString(rejectVo.getProduct_sell_price()));
        holder.etBuyPrice.setFilters(new InputFilter[]{new CashierInputFilter()});
        holder.cbBox.setChecked(selectList.get(position));
        //* 选中的状态，0全部，3未审核，4已打回，5成功退货
        switch (state) {
            case Constants.STATE_REJECT_UN_HECK:
                holder.tvStock.setText(String.valueOf(stockList.get(position)));
                holder.etBuyPrice.setText(String.valueOf(priceList.get(position)));
                holder.etBuyPrice.setEnabled(true);
                holder.cbBox.setVisibility(View.VISIBLE);
                holder.ivAdd.setEnabled(true);
                holder.ivMinus.setEnabled(true);
                break;

            case Constants.STATR_REJECT_BACK:
            case Constants.STATE_REJECT_CHECK_SUCCESS:
                holder.tvStock.setText(String.valueOf(rejectVo.getReturn_goods_number()));
                holder.etBuyPrice.setText(PriceTransfer.chageMoneyToString(rejectVo.getTotal_return_money()));
                holder.cbBox.setVisibility(View.GONE);
                holder.ivAdd.setEnabled(false);
                holder.ivMinus.setEnabled(false);
                break;
        }
        holder.cbBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onCheckChangeListener != null) {
                    selectList.put(position, holder.cbBox.isChecked());
                    onCheckChangeListener.onChecked(selectList, stockList, priceList);
                }
            }
        });
        holder.etBuyPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String price = editable.toString();
                G.log("xxx---xxx" + price);
                double mPrice = G.isEmteny(price) ? 0 : Double.parseDouble(price);
                priceList.put(position, mPrice);
            }
        });
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onTitleClickListener!=null){
                    onTitleClickListener.onTitleClick(position);
                }
            }
        });
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockList.put(position, stockList.get(position) + 1);
                if (stockList.get(position) >= (rejectVo.getQuantity() - rejectVo.getOut_of_stock_num())) {
                    stockList.put(position, rejectVo.getQuantity() - rejectVo.getOut_of_stock_num());
                }
                double totalPrice = (stockList.get(position) * rejectVo.getProduct_sell_price()) / 100;
                priceList.put(position, totalPrice);
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
                double totalPrice = (stockList.get(position) * rejectVo.getProduct_sell_price()) / 100;
                priceList.put(position, totalPrice);
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
        TextView tvSellPrice;
        EditText etBuyPrice;
        CheckBox cbBox;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivAdd = itemView.findViewById(R.id.iv_add);
            ivMinus = itemView.findViewById(R.id.iv_minus);
            tvStock = itemView.findViewById(R.id.tv_adjust_count);
            tvSpec = itemView.findViewById(R.id.tv_spec);
            tvSellPrice = itemView.findViewById(R.id.tv_sell_price);
            etBuyPrice = itemView.findViewById(R.id.tv_buy_price);
            cbBox = itemView.findViewById(R.id.cb_box);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return rejectVoList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
    }

    public void setOnTitleClickListener(OnTitleClickListener onTitleClickListener) {
        this.onTitleClickListener = onTitleClickListener;
    }

    public interface OnTitleClickListener {
        void onTitleClick(int position);
    }
    public interface OnCheckChangeListener {
        void onChecked(Map<Integer, Boolean> selectList, Map<Integer, Integer> stockList, Map<Integer, Double> priceList);
    }
}
