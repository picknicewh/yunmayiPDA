package com.yun.mayi.pda.module.login;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.LoginInfo;
import com.yun.mayi.pda.bean.Version;
import com.yun.mayi.pda.network.BaseSubscriber;
import com.yun.mayi.pda.network.HttpUtils;
import com.yun.mayi.pda.network.RequestCallback;
import com.yun.mayi.pda.network.RequestParameter;
import com.yun.mayi.pda.network.Result;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者： wh
 * 时间：  2018/1/2
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    @Inject
    public LoginPresenter() {

    }

    @Override
    public void login() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("username", mView.getUserName());
        parameter.setParam("password", mView.getPassword());
        mSubscription = HttpUtils.getManager()
                .login(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<LoginInfo>>() {
                    @Override
                    public void beforeRequest() {
                        mView.showProgress();
                    }

                    @Override
                    public void requestError(String msg) {
                        mView.hideProgress();
                        mView.showMessage(msg);
                    }

                    @Override
                    public void requestComplete() {
                        mView.hideProgress();
                    }

                    @Override
                    public void requestSuccess(Result<LoginInfo> data) {
                        if (data != null) {
                            LoginInfo loginInfo = data.getData();
                            mView.setLoginInfo(loginInfo);
                        }
                    }
                }));
    }

    @Override
    public void getVersion() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("packageName", "com.yun.mayi.pda");
        mSubscription = HttpUtils.getManager().getVersion(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<Version>>() {
                    @Override
                    public void beforeRequest() {

                    }

                    @Override
                    public void requestError(String msg) {
                        mView.showMessage(msg);
                    }

                    @Override
                    public void requestComplete() {

                    }

                    @Override
                    public void requestSuccess(Result<Version> data) {
                        if (data != null) {
                            mView.setVersion(data.getData());
                        }
                    }
                }));
    }



}

