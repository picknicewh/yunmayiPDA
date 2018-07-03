package com.yun.mayi.pda.module.home.setting;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.yun.mayi.pda.R;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.base.BaseFragment;
import com.yun.mayi.pda.module.login.LoginActivity;
import com.yun.mayi.pda.widget.ConformDialog;

import butterknife.OnClick;


public class SettingFragment extends BaseFragment implements ConformDialog.OnConformCallBack {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    private ConformDialog conformDialog;

    public static SettingFragment newInstance(String param1) {
        SettingFragment fragment = new SettingFragment();
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
        conformDialog = new ConformDialog(getActivity());
        conformDialog.setOnConformCallBack(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_setting;
    }


    @OnClick(R.id.ll_wifi)
    public void wifi() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    @OnClick(R.id.ll_bluetooth)
    public void bluetooth() {
        Intent intent = new Intent(getActivity(), SettingBluetoothActivity.class);

        startActivity(intent);
    }


    @OnClick(R.id.ll_exit)
    public void exit() {
        conformDialog.show();
    }

    @Override
    public void onCallBack() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();
        YunmayiApplication.startDateList.clear();
        YunmayiApplication.endDateList.clear();
    }
}
