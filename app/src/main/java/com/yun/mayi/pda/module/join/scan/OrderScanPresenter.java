package com.yun.mayi.pda.module.join.scan;

import com.yun.mayi.pda.base.BasePresenter;
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
 * 名称：订单扫描数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class OrderScanPresenter extends BasePresenter<OrderScanContract.View> implements OrderScanContract.Presenter {
    @Inject
    public OrderScanPresenter() {

    }

    @Override
    public void OrderAssignPicker() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token",mView.getToken());
        requestParameter.setParam("agent_number",mView.getAgentNumber());
        requestParameter.setParam("order_id",mView.getOrderId());
        mSubscription = HttpUtils.getManager()
                .OrderAssignPicker(requestParameter.getMapParams())
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
                        mView.fail();
                    }

                    @Override
                    public void requestComplete() {
                        mView.hideProgress();
                    }

                    @Override
                    public void requestSuccess(Result<String> data) {
                        if (data != null) {
                          mView.showMessage(data.getData());
                          mView.success();
                        }
                    }
                }));
    }
}
