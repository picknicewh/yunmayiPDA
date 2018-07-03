package com.yun.mayi.pda.module.support.packed;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PackOrderResult;
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
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class HasPackPresenter extends BasePresenter<HasPackContract.View> implements HasPackContract.Presenter {
    @Inject
    public HasPackPresenter(){

    }
    @Override
    public void getPackList(){
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("beginTime", mView.getBeginTime());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("status", mView.getStatus());
        parameter.setParam("keyword", mView.getKeyWord());
        mSubscription = HttpUtils.getManager()
                .getPackList(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PackOrderResult>>() {
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
                    public void requestSuccess(Result<PackOrderResult> data) {
                        if (data != null) {
                            PackOrderResult  packOrderResult = data.getData();
                            mView.setParkOrderInfo(packOrderResult);
                        }
                    }
                }));
    }
}
