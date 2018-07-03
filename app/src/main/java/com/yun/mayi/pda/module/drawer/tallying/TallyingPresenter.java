package com.yun.mayi.pda.module.drawer.tallying;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PickOrderResult;
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
 * 时间：  2018/05/13
 * 名称：司机点货适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TallyingPresenter extends BasePresenter<TallyingContract.View> implements TallyingContract.Presenter {
    @Inject
    public TallyingPresenter() {

    }


    @Override
    public void getTallyingList() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token",mView.getToken());
        requestParameter.setParam("agentNumber",mView.getAgentNumber());
        requestParameter.setParam("beginTime",mView.getBeginTime());
        requestParameter.setParam("endTime",mView.getEndTime());
        requestParameter.setParam("keyword",mView.getKeyword());
        requestParameter.setParam("page",mView.getPage());
        requestParameter.setParam("size",mView.getSize());
        requestParameter.setParam("isStart",0);//0是司机点货  1是发车列表
        requestParameter.setParam("number",mView.getNumber());
        mSubscription = HttpUtils.getManager()
                .getTallyingList(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PickOrderResult>>() {
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
                    public void requestSuccess(Result<PickOrderResult> data) {
                        if (data != null) {
                            PickOrderResult result = data.getData();
                            mView.setPickOrderResult(result);
                        }
                    }
                }));
    }

}
