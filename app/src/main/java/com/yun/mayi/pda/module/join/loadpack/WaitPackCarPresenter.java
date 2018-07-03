package com.yun.mayi.pda.module.join.loadpack;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PickOrder;
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
 * 时间：  2018/5/14
 * 名称：：待拣货单数据处理详情类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class WaitPackCarPresenter extends BasePresenter<WaitPackCarContract.View> implements WaitPackCarContract.Presenter {
    @Inject
    public WaitPackCarPresenter() {

    }
    @Override
    public void getLoadingOrderList() {
        RequestParameter requestParameter = new RequestParameter();
        requestParameter.setParam("token",mView.getToken());
        requestParameter.setParam("beginTime",mView.getBeginTime());
        requestParameter.setParam("endTime",mView.getEndTime());
        requestParameter.setParam("keyword",mView.getKeyword());
        mSubscription = HttpUtils.getManager()
                .getLoadingOrderList(requestParameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<List<PickOrder>>>() {
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
                    public void requestSuccess(Result<List<PickOrder>> data) {
                        if (data != null) {
                            List<PickOrder> pickOrderList = data.getData();
                            mView.setPickOrderList(pickOrderList);
                        }
                    }
                }));
    }
}
