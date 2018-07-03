package com.yun.mayi.pda.module.join.conform;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.AgentInfo;
import com.yun.mayi.pda.bean.ConformGoodsResult;
import com.yun.mayi.pda.network.BaseSubscriber;
import com.yun.mayi.pda.network.HttpUtils;
import com.yun.mayi.pda.network.RequestCallback;
import com.yun.mayi.pda.network.RequestParameter;
import com.yun.mayi.pda.network.Result;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者： wh
 * 时间：  2018/3/28
 * 名称：确认收货数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ConformGoodsPresenter extends BasePresenter<ConformGoodsContract.View> implements ConformGoodsContract.Presenter {
    @Inject
    public ConformGoodsPresenter() {

    }

    @Override
    public void getConformList() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("beginTime", mView.getStartTime());
        parameter.setParam("keyword", mView.getKeyword());
        parameter.setParam("vendorId", mView.getVendorId());
        parameter.setParam("agentNumber", mView.getAgentNumber());
        parameter.setParam("type",mView.getType());
        mSubscription = HttpUtils.getManager()
                .getVerifyProductListByVendor(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<ConformGoodsResult>>() {
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
                    public void requestSuccess(Result<ConformGoodsResult> data) {
                        if (data != null) {
                            ConformGoodsResult result = data.getData();
                            mView.setConformGoods(result);
                        }
                    }
                }));
    }

    @Override
    public void verifyVendorAllProduct() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("packBoxDetailIdList", mView.getPackBoxDetailIdList());
        parameter.setParam("numList", mView.getNumList());
        parameter.setParam("boxNumList",mView.getBoxNumList());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("agentNumber",mView.getAgentNumber());
        mSubscription = HttpUtils.getManager()
                .verifyVendorAllProductNum(parameter.getMapParams())
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
    public void getDifferentAgentList() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("beginTime", mView.getStartTime());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("vendorId", mView.getVendorId());
        parameter.setParam("type",mView.getType());
        mSubscription = HttpUtils.getManager()
                .getDifferentAgentList(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<List<AgentInfo>>>() {
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
                    public void requestSuccess(Result<List<AgentInfo>> data) {
                        if (data != null) {
                            List<AgentInfo> agentInfoList = data.getData();
                            mView.setAgentInfoList(agentInfoList);
                        }
                    }
                }));
    }
}
