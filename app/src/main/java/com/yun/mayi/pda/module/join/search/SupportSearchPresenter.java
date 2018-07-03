package com.yun.mayi.pda.module.join.search;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.SalemanVo;
import com.yun.mayi.pda.bean.VendorInfo;
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
 * 时间：  2018/3/16
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class SupportSearchPresenter extends BasePresenter<SupportSearchContract.View> implements SupportSearchContract.Pesenter {

    @Inject
    public SupportSearchPresenter() {
    }

    public void getSalesmansByToken() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        mSubscription = HttpUtils.getManager()
                .getSalesmansByToken(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<List<SalemanVo>>>() {
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
                    public void requestSuccess(Result<List<SalemanVo>> data) {
                        if (data != null) {
                            List<SalemanVo> salemanVoList = data.getData();
                            mView.setSaleManList(salemanVoList);
                        }
                    }
                }));
    }
    public void getVendorInfoByAgentNumber() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token",mView.getToken());
        parameter.setParam("agent_number",mView.getAgentNumber());
        mSubscription = HttpUtils.getManager()
                .getVendorInfoByAgentNumber(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<List<VendorInfo>>>() {
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
                    public void requestSuccess(Result<List<VendorInfo>> data) {
                        if (data != null) {
                            List<VendorInfo> vendorInfoList = data.getData();
                            mView.setVendorInfoList(vendorInfoList);
                        }
                    }
                }));
    }
}
