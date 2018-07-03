package com.yun.mayi.pda.module.home.setting;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
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
public class BluetoothListAdapter extends RecyclerView.Adapter<BluetoothListAdapter.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<BluetoothDevice> bluetoothDevices;
    private Map<Integer, Boolean> connectList;

    public BluetoothListAdapter(List<BluetoothDevice> bluetoothDevices) {
        this.bluetoothDevices = bluetoothDevices;
    }

    public void initData(int position) {
        connectList = new HashMap<>();
        for (int i = 0; i < bluetoothDevices.size(); i++) {
            if (i == position) {
                connectList.put(i, true);
            } else {
                connectList.put(i, false);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wifi_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        BluetoothDevice bluetoothDevice = bluetoothDevices.get(position);
        holder.tvWifi.setText(bluetoothDevice.getName());
        if (YunmayiApplication.bluetoothDevice != null) {
            if (YunmayiApplication.bluetoothDevice.getName().equals(bluetoothDevice.getName())) {
                holder.tvConnect.setVisibility(View.VISIBLE);
            } else {
                holder.tvConnect.setVisibility(View.GONE);
            }
        }else {
            holder.tvConnect.setVisibility(View.GONE);
        }
        holder.tvCurrentWifi.setVisibility(View.GONE);
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
        TextView tvConnect;

        ViewHolder(View itemView) {
            super(itemView);
            tvWifi = itemView.findViewById(R.id.tv_wifi);
            tvCurrentWifi = itemView.findViewById(R.id.tv_current_wifi);
            tvConnect = itemView.findViewById(R.id.tv_connect);
            itemView.setTag(this);
        }
    }

    @Override
    public int getItemCount() {
        return bluetoothDevices.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
