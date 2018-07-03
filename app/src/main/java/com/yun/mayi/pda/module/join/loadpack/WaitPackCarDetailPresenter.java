package com.yun.mayi.pda.module.join.loadpack;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PickOrderDetailResult;
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
 * 时间：  2018/05/13
 * 名称：待拣货单详情数据处理详情类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackCarDetailPresenter extends BasePresenter<WaitPackCarDetailContract.View> implements WaitPackCarDetailContract.Presenter {
    @Inject
    public WaitPackCarDetailPresenter() {

    }

    @Override
    public void getDeliveryOrderDetail() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("agentNumber", mView.getAgentNumber());
        requestParameter.setParam("keyword", mView.getKeyword());
        requestParameter.setParam("orderId", mView.getOrderId());
        requestParameter.setParam("isPick", 1);
        mSubscription = HttpUtils.getManager()
                .getDeliveryOrderDetail(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PickOrderDetailResult>>() {
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
                    public void requestSuccess(Result<PickOrderDetailResult> data) {
                        if (data != null) {
                            PickOrderDetailResult result = data.getData();
                            mView.setPickOrderDetailResult(result);

                        }
                    }
                }));
    }

    @Override
    public void agentFinishPick() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("agentNumber", mView.getAgentNumber());
        requestParameter.setParam("orderId", mView.getOrderId());
        requestParameter.setParam("orderDetailIds", mView.getOrderDetailIds());
        requestParameter.setParam("nums", mView.getNums());
        mSubscription = HttpUtils.getManager()
                .agentFinishPick(requestParameter.getMapParams())
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
