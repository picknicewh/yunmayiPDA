package com.yun.mayi.pda.module.drawer.scan;

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
 * 时间：  2018/5/28
 * 名称：订单收款数据处理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class DrawerScanPresenter extends BasePresenter<DrawerScanContract.View> implements DrawerScanContract.Presenter {
    @Inject
    public DrawerScanPresenter() {

    }

    @Override
    public void deliveryScan() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token", mView.getToken());
        requestParameter.setParam("orderId", mView.getOrderId());
        mSubscription = HttpUtils.getManager()
                .deliveryScan(requestParameter.getMapParams())
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
                        mView.onFail();
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
