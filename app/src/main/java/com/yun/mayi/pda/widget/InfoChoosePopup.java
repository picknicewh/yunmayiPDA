package com.yun.mayi.pda.widget;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.db.KeyWordDbHelper;
import com.yun.mayi.pda.db.KeyWordInfoDb;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/4/23
 * 名称：信息选择对话框
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class InfoChoosePopup extends PopupWindow implements OnItemClickListener, TextWatcher, View.OnClickListener {

    /**
     * 搜索
     */
    private ClearEditText etSign;
    /**
     * 数据
     */
    private RecyclerView rvData;

    /**
     * 搜索
     */
    private TextView tvSearch;
    /**
     * 适配器
     */
    private InfoAdapter adapter;
    /**
     * 信息列表
     */
    private List<String> infoList;
    /**
     * 数据改变监听
     */
    private OnDataChangeListener onDataChangeListener;
    private View contentView;
    private Activity context;
    /**
     * 数据库创建类
     */
    private KeyWordDbHelper mDbHelper;
    /**
     * 数据库
     */
    private SQLiteDatabase db;

    public InfoChoosePopup(Activity context) {
        this.context = context;
        init();
    }

    private void initView() {
        mDbHelper = KeyWordDbHelper.getInstance();
        db = mDbHelper.getWritableDatabase();
        contentView = LayoutInflater.from(context).inflate(R.layout.dialog_info_choose, null);
        rvData = contentView.findViewById(R.id.rv_data);
        etSign = contentView.findViewById(R.id.et_sign);
        tvSearch = contentView.findViewById(R.id.tv_search);
        tvSearch.setOnClickListener(this);
        infoList = new ArrayList<>();
        adapter = new InfoAdapter(infoList);
        rvData.setLayoutManager(new LinearLayoutManager(context));
        rvData.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        etSign.addTextChangedListener(this);
    }

    /**
     * 设置屏幕的宽度
     */
    private void init() {
        initView();
        //设置SignPopupWindow的View
        this.setContentView(contentView);
        //设置SignPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        //设置SignPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置SignPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x80000000);
        //设置SignPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //防止虚拟软键盘被弹出菜单遮住
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    public void onClick(View view, int position) {
      if (onDataChangeListener!=null){
          onDataChangeListener.dataChange(infoList.get(position));
      }
      dismiss();
    }

    public void setInfoList(List<String> infoList) {
        this.infoList.clear();
        KeyWordInfoDb.insertAll(db, infoList);
        this.infoList.addAll(infoList);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        this.infoList.clear();
        String keyword = editable.toString();
        infoList.addAll(KeyWordInfoDb.getInfoList(db, keyword));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {
        this.infoList.clear();
        String keyword = etSign.getText().toString();
        infoList.addAll(KeyWordInfoDb.getInfoList(db, keyword));
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public interface OnDataChangeListener {
        void dataChange(String data);
    }

    private class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
        /**
         * 数据列表
         */
        private List<String> infoList;
        private OnItemClickListener onItemClickListener;

        public InfoAdapter(List<String> infoList) {
            this.infoList = infoList;
        }


        @Override
        public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_choose, parent, false);
            return new InfoAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final InfoAdapter.ViewHolder holder, final int position) {
            holder.tvInfo.setText(infoList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(view, position);
                    }
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvInfo;

            ViewHolder(View itemView) {
                super(itemView);
                tvInfo = itemView.findViewById(R.id.tv_info);
                itemView.setTag(this);
            }
        }

        @Override
        public int getItemCount() {
            return infoList.size();
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }
}
