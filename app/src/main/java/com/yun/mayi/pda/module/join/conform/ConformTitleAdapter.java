package com.yun.mayi.pda.module.join.conform;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.bean.AgentInfo;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者： wh
 * 时间： 2018/03/27
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ConformTitleAdapter extends RecyclerView.Adapter<ConformTitleAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    /**
     * 购买数量列表
     */
    private Map<Integer, Boolean> selectList;
     private List<AgentInfo> agentInfoList;
     private Context context;
    public ConformTitleAdapter(List<AgentInfo> agentInfoList, Context context) {
        selectList = new HashMap<>();
        this.agentInfoList = agentInfoList;
        this.context =context;
    }

    public void initData(int position) {
        for (int i = 0; i < getItemCount(); i++) {
            if (position==i){
                selectList.put(i,true );
            }else {
                selectList.put(i,false );
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_list, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AgentInfo agentInfo = agentInfoList.get(position);
        holder.tvTitle.setText(agentInfo.getAgentName());
        if (selectList.get(position)){
            holder.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.red_btn));
        }else {
            holder.tvTitle.setTextColor(ContextCompat.getColor(context,R.color.main_text_color));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){
                    onItemClickListener.onClick(view,position);
                }
            }
        });

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return agentInfoList.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
