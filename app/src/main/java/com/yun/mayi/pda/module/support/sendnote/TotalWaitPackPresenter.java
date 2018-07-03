package com.yun.mayi.pda.module.support.sendnote;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PackNoteInfoResult;
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
 * 时间：  2018/3/27
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TotalWaitPackPresenter extends BasePresenter<TotalWaitPackContract.View> implements TotalWaitPackContract.Presenter {
    @Inject
    public TotalWaitPackPresenter() {

    }

    @Override
    public void getVendorOrderInfo() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("beginTime", mView.getBeginTime());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("keyword", mView.getKeyword());
        mSubscription = HttpUtils.getManager()
                .getVendorOrderInfo(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PackNoteInfoResult>>() {
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
                    public void requestSuccess(Result<PackNoteInfoResult> data) {
                        if (data != null) {
                            PackNoteInfoResult packNoteInfoResult = data.getData();
                            mView.setPackNoteInfoData(packNoteInfoResult);
                        }
                    }
                }));
    }

    @Override
    public void verifyProductNumber() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("num", mView.getNum());
        parameter.setParam("originNum", mView.getOrignNum());
        parameter.setParam("pid", mView.getProductId());
        parameter.setParam("orderCreateTime",mView.getOrderCreateTime());

        mSubscription = HttpUtils.getManager()
                .verifyProductNumber(parameter.getMapParams())
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
                            mView.conformSuccess();
                        }
                    }
                }));
    }
    @Override
    public void verifyAllProductNumber() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("numList", mView.getNums());
        parameter.setParam("originNumList", mView.getOrignNums());
        parameter.setParam("pidList", mView.getProductIds());
        parameter.setParam("orderCreateTime",mView.getOrderCreateTime());
        parameter.setParam("queryBeginTime", mView.getBeginTime());
        parameter.setParam("queryEndTime", mView.getEndTime());
        mSubscription = HttpUtils.getManager()
                .verifyAllProductNumber(parameter.getMapParams())
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
                            mView.conformSuccess();
                        }
                    }
                }));
    }
}
