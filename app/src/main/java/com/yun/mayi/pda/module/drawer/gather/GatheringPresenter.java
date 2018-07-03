package com.yun.mayi.pda.module.drawer.gather;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PayInfo;
import com.yun.mayi.pda.bean.PayResult;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;
import com.yun.mayi.pda.bean.ReceptOrderInfo;
import com.yun.mayi.pda.network.BaseSubscriber;
import com.yun.mayi.pda.network.HttpUtils;
import com.yun.mayi.pda.network.RequestCallback;
import com.yun.mayi.pda.network.RequestParameter;
import com.yun.mayi.pda.network.Result;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者： wh
 * 时间：  2018/5/28
 * 名称：订单收款数据处理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GatheringPresenter extends BasePresenter<GatheringContract.View> implements GatheringContract.Presenter {
    @Inject
    public GatheringPresenter() {

    }

    @Override
    public void getOrderReceiptList() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("agentNumber", mView.getAgentNumber());
        requestParameter.setParam("endTime", mView.getEndTime());
        requestParameter.setParam("beginTime", mView.getBeginTime());
        requestParameter.setParam("keyword", mView.getKeyword());
        mSubscription = HttpUtils.getManager()
                .getOrderReceiptList(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<List<ReceptOrderInfo>>>() {
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
                    public void requestSuccess(Result<List<ReceptOrderInfo>> data) {
                        if (data != null) {
                            List<ReceptOrderInfo> pickOrderList = data.getData();
                            mView.setPickOrderList(pickOrderList);
                        }
                    }
                }));
    }

    @Override
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

    @Override
    public void aliPay() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("authCode", mView.getAuthCode());
        requestParameter.setParam("totalFee", mView.getTotalFee());
        requestParameter.setParam("orderId", mView.getOrderId());
        mSubscription = HttpUtils.getManager()
                .aliPay(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PayInfo>>() {
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
                    public void requestSuccess(Result<PayInfo> data) {
                        if (data != null) {
                            PayInfo payInfo = data.getData();
                            mView.setPayInfo(payInfo);
                        }
                    }
                }));
    }

    @Override
    public void queryPayList() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("alipayTradeNo", mView.getAlipayTradeNo());
        requestParameter.setParam("orderId", mView.getOrderId());
        mSubscription = HttpUtils.getManager()
                .queryPayList(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PayResult>>() {
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
                    public void requestSuccess(Result<PayResult> data) {
                        if (data != null) {
                            PayResult payResult = data.getData();
                            if (payResult.getTradeStatus().equals("TRADE_SUCCESS")){
                                getOrderReceiptList();//交易成功重新刷新数据
                            }else {
                                queryPayList();//没有交易成功，则继续查询
                            }
                        }
                    }
                }));
    }
}
