package com.yun.mayi.pda.module.support.corder;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PackBoxResult;
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
 * 名称：取消订单数据处理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class CancelPresenter extends BasePresenter<CancelOrderContract.View> implements  CancelOrderContract.Presenter{
    @Inject
   public CancelPresenter(){

   }
    public void getPackBoxList(){
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("beginTime", mView.getBeginTime());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("page", mView.getPage());
        parameter.setParam("keyword", mView.getKeyWord());
        mSubscription = HttpUtils.getManager()
                .listCancel(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PackBoxResult>>() {
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
                    public void requestSuccess(Result<PackBoxResult> data) {
                        if (data != null) {
                            PackBoxResult  packBoxResult = data.getData();
                            mView.setParkBoxResult(packBoxResult);
                        }
                    }
                }));
    }
}
