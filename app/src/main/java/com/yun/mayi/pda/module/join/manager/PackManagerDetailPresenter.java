package com.yun.mayi.pda.module.join.manager;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PickDetailInfoResult;
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
 * 名称：分家管理数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackManagerDetailPresenter extends BasePresenter<PackManagerDetailContract.View> implements PackManagerDetailContract.Presenter {

    @Inject
    public PackManagerDetailPresenter() {
    }


    @Override
    public void getPickOrderDetail() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token",mView.getToken());
        requestParameter.setParam("agentNumber",mView.getAgentNumber());
        requestParameter.setParam("orderId",mView.getOrderId());
        mSubscription = HttpUtils.getManager()
                .pickedDetail(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PickDetailInfoResult>>() {
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
                    public void requestSuccess(Result<PickDetailInfoResult> data) {
                        if (data != null) {
                            PickDetailInfoResult pickDetailInfoResult = data.getData();
                            mView.setPickDetailInfoResult(pickDetailInfoResult);
                        }
                    }
                }));
    }


}
