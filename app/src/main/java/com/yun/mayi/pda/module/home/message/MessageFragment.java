package com.yun.mayi.pda.module.home.message;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.base.BaseFragment;

import butterknife.BindView;


public class MessageFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    /**
     * 消息列表
     */
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    /**
     * 适配器
     */
    private MessageAdapter adapter;
    public static MessageFragment newInstance(String param1) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        adapter = new MessageAdapter(getActivity());
        rvMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMessage.setAdapter(adapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

}
