package com.yun.mayi.pda.module.drawer.depart;

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
 * 时间：  2018/3/30
 * 名称：司机发车数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class DepartPresenter extends BasePresenter<DepartContract.View> implements DepartContract.Presenter {
    @Inject
    public DepartPresenter() {

    }


    @Override
    public void getTallyingList() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("agentNumber", mView.getAgentNumber());
        requestParameter.setParam("beginTime", mView.getBeginTime());
        requestParameter.setParam("endTime", mView.getEndTime());
        requestParameter.setParam("keyword", mView.getKeyword());
        requestParameter.setParam("page", mView.getPage());
        requestParameter.setParam("size", mView.getSize());
        requestParameter.setParam("isStart", 1);//0是司机点货  1是发车列表
        mSubscription = HttpUtils.getManager()
                .getTallyingList(requestParameter.getMapParams())
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
                            PickOrderResult result = data.getData();
                            mView.setPickOrderResult(result);
                        }
                    }
                }));
    }

    @Override
    public void startDelivery() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("orderIdStr", mView.getOrderIdStr());
        mSubscription = HttpUtils.getManager()
                .startDelivery(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<String>>() {
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
                    public void requestSuccess(Result<String> data) {
                        if (data != null) {
                            mView.showMessage(data.getData());
                            mView.onSuccess();
                        }
                    }
                }));
    }
}
