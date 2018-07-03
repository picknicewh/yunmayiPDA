package com.yun.mayi.pda.module.join.loadscan;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.DrawerInfo;
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
 * 时间：  2018/3/30
 * 名称：装车扫描数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LoadScanCodePresenter extends BasePresenter<LoadScanCodeContract.View> implements LoadScanCodeContract.Presenter {
    @Inject
    public LoadScanCodePresenter() {

    }

    @Override
    public void getDeliverymanList() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token",mView.getToken());
        requestParameter.setParam("agentNumber",mView.getAgentNumber());
    //    requestParameter.setParam("order_id",mView.getOrderId());
        mSubscription = HttpUtils.getManager()
                .getDeliverymanList(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<List<DrawerInfo>>>() {
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
                    public void requestSuccess(Result<List<DrawerInfo>> data) {
                        if (data != null) {
                           List<DrawerInfo> drawerInfoList = data.getData();
                           mView.setDrawerInfoList(drawerInfoList);
                        }
                    }
                }));
    }

    @Override
    public void distributeLoading() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token",mView.getToken());
        requestParameter.setParam("agentNumber",mView.getAgentNumber());
        requestParameter.setParam("orderId",mView.getOrderId());
        requestParameter.setParam("deliverymanId",mView.getDeliverymanId());
        mSubscription = HttpUtils.getManager()
                .distributeLoading(requestParameter.getMapParams())
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
                        mView.fail();
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
