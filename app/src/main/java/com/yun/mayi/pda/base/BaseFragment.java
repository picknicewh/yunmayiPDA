package com.yun.mayi.pda.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者： wh
 * 时间：  2017/8/21
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public abstract class BaseFragment extends Fragment implements BaseContract.View  {
    Unbinder unbinder;
    /**
     * 加载框
     */
    private LoadingDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }
    public void beforeInit() {

    }
    public abstract void initView();

    public abstract int getLayoutResId();
    @Override
    public void showMessage(String message) {
        G.showToast(getActivity(), message);
    }

    @Override
    public void showProgress() {
        if (dialog == null) {
            dialog = new LoadingDialog(getActivity());
        }
        dialog.show();
    }

    @Override
    public void hideProgress() {
        dialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
