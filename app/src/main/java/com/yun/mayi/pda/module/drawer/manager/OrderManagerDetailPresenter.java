package com.yun.mayi.pda.module.drawer.manager;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;
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
 * 时间：  2018/6/25
 * 名称：订单管理数据处理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class OrderManagerDetailPresenter extends BasePresenter<OrderManagerDetailContract.View> implements OrderManagerDetailContract.Presenter {
    @Inject
    public OrderManagerDetailPresenter() {

    }

    public void receiptsPrint() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("agentNumber", mView.getAgentNumber());
        requestParameter.setParam("orderId", mView.getOrderId());
        mSubscription = HttpUtils.getManager()
                .receiptsPrint(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<ReceiptPrintInfo>>() {
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
                    public void requestSuccess(Result<ReceiptPrintInfo> data) {
                        if (data != null) {
                            ReceiptPrintInfo receiptPrintInfo = data.getData();
                            mView.setReceiptPrintInfo(receiptPrintInfo);
                        }
                    }
                }));
    }

}
