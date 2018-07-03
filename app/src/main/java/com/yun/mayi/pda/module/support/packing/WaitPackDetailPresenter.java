package com.yun.mayi.pda.module.support.packing;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.Order;
import com.yun.mayi.pda.bean.PackBox;
import com.yun.mayi.pda.db.UserMessage;
import com.yun.mayi.pda.network.BaseSubscriber;
import com.yun.mayi.pda.network.HttpUtils;
import com.yun.mayi.pda.network.RequestCallback;
import com.yun.mayi.pda.network.RequestParameter;
import com.yun.mayi.pda.network.Result;
import com.yun.mayi.pda.utils.G;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者： wh
 * 时间：  2018/1/6
 * 名称
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackDetailPresenter extends BasePresenter<WaitPackDetailContract.View> implements WaitPackDetailContract.Presenter {

    @Inject
    public WaitPackDetailPresenter(){

    }
    @Override
    public void getPackOrderDetail() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("orderId", mView.getOrderId());
        mSubscription = HttpUtils.getManager()
                .getPackOrderDetail(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<Order>>() {
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
                    public void requestSuccess(Result<Order> data) {
                        if (data != null) {
                            Order order = data.getData();
                            mView.setParkOrderInfo(order);
                        }
                    }
                }));
    }
    @Override
    public void packBox() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("orderId", mView.getOrderId());
        parameter.setParam("orderDetailIds", mView.getOrderDetailIds());
        parameter.setParam("nums", mView.getNums());
        parameter.setParam("boxNum",mView.getBoxNum());
        parameter.setParam("operatorId", UserMessage.getInstance().getUser_id());
        mSubscription = HttpUtils.getManager()
                .packBox(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PackBox>>() {
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
                    public void requestSuccess(Result<PackBox> data) {
                        if (data != null) {
                            G.log("ssssss" + "返回成功！.....");
                            PackBox packBox = data.getData();
                            mView.packSuccess(packBox);
                        }
                    }
                }));
    }

    @Override
    public void outofStock() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("orderDetailId", mView.getOrderDetailId());
        mSubscription = HttpUtils.getManager()
                .outofstock(parameter.getMapParams())
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
                            G.log("ssssss" + "返回成功！.....");
                            String result = data.getData();
                            mView.outofStockBack(result);
                        }
                    }
                }));
    }
}
