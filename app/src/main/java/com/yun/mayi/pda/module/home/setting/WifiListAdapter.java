package com.yun.mayi.pda.module.home.setting;

import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.module.common.OnItemClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者： wh
 * 时间：  2017/6/29
 * 名称：wifi篱笆
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<ScanResult> wifiList;
    private Map<Integer, Boolean> connectList;
    private String connectStateInfo = "已连接";
    private String saveStateInfo = "已保存";
    private List<String> configList;

    public WifiListAdapter(List<ScanResult> wifiList, List<String> configList) {
        this.wifiList = wifiList;
        this.configList = configList;
        connectList = new HashMap<>();
        initData(0);
    }

    public void initData(int position) {
        for (int i = 0; i < wifiList.size(); i++) {
            if (i == position) {
                connectList.put(i, true);
            } else {
                connectList.put(i, false);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 设置当前wifi连接状态信息
     */
    public void setWifiConnectState(String connectStateInfo, int position) {
        this.connectStateInfo = connectStateInfo;
        notifyItemChanged(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ScanResult scanResult = wifiList.get(position);
        holder.tvWifi.setText(scanResult.SSID);
        if (configList.contains(scanResult.SSID)) {
            holder.tvCurrentWifi.setVisibility(View.VISIBLE);
            if (connectList.get(position)) {
                holder.tvCurrentWifi.setText(connectStateInfo);
            } else {
                holder.tvCurrentWifi.setText(saveStateInfo);
            }
        } else {
            holder.tvCurrentWifi.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(v, position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWifi;
        TextView tvCurrentWifi;

        ViewHolder(View itemView) {
            super(itemView);
            tvWifi = itemView.findViewById(R.id.tv_wifi);
            tvCurrentWifi = itemView.findViewById(R.id.tv_current_wifi);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
