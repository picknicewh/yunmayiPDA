package com.yun.mayi.pda.module.join.manager;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PickOrderResult;
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
 * 时间：  2018/3/16
 * 名称：分拣管理数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackManagerPresenter extends BasePresenter<PackManagerContract.View> implements PackManagerContract.Presenter {

    @Inject
    public PackManagerPresenter() {
    }


    @Override
    public void getConformList() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("agent_number", mView.getAgentNumber());
        parameter.setParam("end_time", mView.getEndTime());
        parameter.setParam("begin_time", mView.getBeginTime());
        parameter.setParam("page", mView.getPage());
        parameter.setParam("size", mView.getSize());
        parameter.setParam("keyword", mView.getKeyword());
        parameter.setParam("isPick", 1);
        mSubscription = HttpUtils.getManager()
                .getPickOrderList(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PickOrderResult>>() {
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
                    public void requestSuccess(Result<PickOrderResult> data) {
                        if (data != null) {
                            PickOrderResult packBoxResult = data.getData();
                            mView.setPickOrderResult(packBoxResult);
                        }
                    }
                }));
    }
}
