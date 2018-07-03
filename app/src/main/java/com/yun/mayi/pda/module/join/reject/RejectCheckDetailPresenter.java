package com.yun.mayi.pda.module.join.reject;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.RejectVoResult;
import com.yun.mayi.pda.db.UserMessage;
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
 * 时间：  2018/3/16
 * 名称：退货审核数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class RejectCheckDetailPresenter extends BasePresenter<RejectCheckDetailContract.View> implements RejectCheckDetailContract.Presenter {

    @Inject
    public RejectCheckDetailPresenter() {
    }

    @Override
    public void getRejectCheckList() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", UserMessage.getInstance().getToken());
        parameter.setParam("beginTime", mView.getBeginTime());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("salesmanId", mView.getSalesmanId());
        parameter.setParam("state", mView.getState());
        parameter.setParam("keyword", mView.getKeyWord());
        parameter.setParam("page", mView.getPage());
        mSubscription = HttpUtils.getManager()
                .getWareHouseList(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<RejectVoResult>>() {
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
                    public void requestSuccess(Result<RejectVoResult> data) {
                        if (data != null) {
                            RejectVoResult rejectVo = data.getData();
                            mView.setRejectVoList(rejectVo.getData());
                        }
                    }
                }));
    }

    @Override
    public void rejectWareHouse() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", UserMessage.getInstance().getToken());
        parameter.setParam("remark", mView.getRemark());
        parameter.setParam("idStr", mView.getIdStr());
        parameter.setParam("numStr", mView.getNumStr());
        parameter.setParam("feeStr", mView.getFeeStr());
        mSubscription = HttpUtils.getManager()
                .rejectWareHouse(parameter.getMapParams())
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
                        }
                    }
                }));
    }

    @Override
    public void passWareHouse() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", UserMessage.getInstance().getToken());
        parameter.setParam("remark", mView.getRemark());
        parameter.setParam("idStr", mView.getIdStr());
        parameter.setParam("numStr", mView.getNumStr());
        parameter.setParam("feeStr", mView.getFeeStr());
        mSubscription = HttpUtils.getManager()
                .passWareHouse(parameter.getMapParams())
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
                        }
                    }
                }));
    }
}
