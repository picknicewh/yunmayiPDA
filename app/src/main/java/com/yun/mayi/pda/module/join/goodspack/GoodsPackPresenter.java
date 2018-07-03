package com.yun.mayi.pda.module.join.goodspack;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PickOrderDetailResult;
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
 * 名称：按货分拣数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GoodsPackPresenter extends BasePresenter<GoodsPackContract.View> implements GoodsPackContract.Presenter {
    @Inject
    public GoodsPackPresenter(){

    }

    @Override
    public void getPickByProduct() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("agent_number", mView.getAgentNumber());
        parameter.setParam("end_time",mView.getEndTime());
        parameter.setParam("begin_time",mView.getBeginTime());
        parameter.setParam("keyword",mView.getKeyword());
        mSubscription = HttpUtils.getManager()
                .getPickByProduct(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PickOrderDetailResult>>() {
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
                    public void requestSuccess(Result<PickOrderDetailResult> data) {
                        if (data != null) {
                            PickOrderDetailResult pickOrderDetailResult = data.getData();
                            mView.setPickOrderDetailResult(pickOrderDetailResult);
                        }
                    }
                }));
    }
}
