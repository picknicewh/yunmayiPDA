package com.yun.mayi.pda.module.support.packed;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.Order;
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
 * 时间：  2018/1/6
 * 名称：装箱详情数据处理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HasPackDetailPresenter extends BasePresenter<HasPackDetailContract.View> implements HasPackDetailContract.Presenter {
   @Inject
    public HasPackDetailPresenter(){

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
}
