package com.yun.mayi.pda.module.support.packtotal;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PackInfoDetailResult;
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
public class TotalHasPackPresenter extends BasePresenter<TotalHasPackContract.View> implements TotalHasPackContract.Presenter {
    @Inject
    TotalHasPackPresenter() {

    }

    @Override
    public void getVerifyVendorProductList() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("beginTime", mView.getBeginTime());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("keyword", mView.getKeyword());
        mSubscription = HttpUtils.getManager()
                .getVerifyVendorProductList(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PackInfoDetailResult>>() {
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
                    public void requestSuccess(Result<PackInfoDetailResult> data) {
                        if (data != null) {
                            PackInfoDetailResult hasPackInfoList = data.getData();
                            mView.setHasPackInfoData(hasPackInfoList);
                        }
                    }
                }));
    }

    @Override
    public void delVerifyVendorProduct() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("packBoxDetailId", mView.getPackBoxDetailId());
        parameter.setParam("token", mView.getToken());
        mSubscription = HttpUtils.getManager()
                .delVerifyVendorProduct(parameter.getMapParams())
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
                            mView.success();
                        }
                    }
                }));
    }

}
