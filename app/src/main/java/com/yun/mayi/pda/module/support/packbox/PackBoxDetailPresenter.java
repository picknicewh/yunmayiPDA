package com.yun.mayi.pda.module.support.packbox;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PackBox;
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
public class PackBoxDetailPresenter extends BasePresenter<PackBoxDetailContract.View>  implements PackBoxDetailContract.Presenter{

    @Inject
    public PackBoxDetailPresenter() {

    }
    @Override
    public void getPackBoxDetail() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("boxId", mView.getBoxId());
        mSubscription = HttpUtils.getManager()
                .getPackBoxDetails(parameter.getMapParams())
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
                            PackBox packBox = data.getData();
                            mView.setParkBox(packBox);
                        }
                    }
                }));
    }
    @Override
    public void unBox() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("boxId", mView.getBoxId());
        mSubscription = HttpUtils.getManager()
                .unboxing(parameter.getMapParams())
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
                            String result = data.getData();
                            mView.setUnBoxResult(result);
                        }
                    }
                }));
    }
}
