package com.yun.mayi.pda.module.support.lesspark;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.OrderDetailResult;
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
 * 名称：装箱列表数据处理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LessPackPresenter extends BasePresenter<LessPackContract.View> implements LessPackContract.Presenter {
    @Inject
   public LessPackPresenter(){

   }
    public void getLessPack() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("beginTime", mView.getBeginTime());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("page", mView.getPage());
        parameter.setParam("keyword", mView.getKeyWord());
        mSubscription = HttpUtils.getManager()
                .getLessPack(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<OrderDetailResult>>() {
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
                    public void requestSuccess(Result<OrderDetailResult> data) {
                        if (data != null) {
                            OrderDetailResult orderDetailResult = data.getData();
                            mView.setOrderDetailInfo(orderDetailResult);
                        }
                    }
                }));
    }
}
