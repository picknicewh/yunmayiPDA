package com.yun.mayi.pda.module.join.goodspack;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PickOrderDetail;
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
 * 名称：：按货分拣详情数据处理类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class GoodsPackDetailPresenter extends BasePresenter<GoodsPackDetailContract.View> implements GoodsPackDetailContract.Presenter {
    @Inject
    public GoodsPackDetailPresenter(){

    }

    @Override
    public void getPickByProductDetail() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("token", mView.getToken());
        parameter.setParam("agent_number", mView.getAgentNumber());
        parameter.setParam("end_time",mView.getEndTime());
        parameter.setParam("begin_time",mView.getBeginTime());
        parameter.setParam("product_id",mView.getProductId());
        mSubscription = HttpUtils.getManager()
                .getPickByProductDetail(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<List<PickOrderDetail>>>() {
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
                    public void requestSuccess(Result<List<PickOrderDetail>> data) {
                        if (data != null) {
                            List<PickOrderDetail> pickOrderDetails = data.getData();
                            mView.setPickOrderDetailList(pickOrderDetails);
                        }
                    }
                }));
    }
}
