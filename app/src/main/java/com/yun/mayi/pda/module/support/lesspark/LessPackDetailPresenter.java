package com.yun.mayi.pda.module.support.lesspark;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PackBoxDetail;
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
 * 时间：  2018/1/6
 * 名称：装箱列表详情数据处理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LessPackDetailPresenter extends BasePresenter<LessPackDetailContract.View> implements  LessPackDetailContract.Presenter{
    @Inject
   public LessPackDetailPresenter(){

   }
   @Override
    public void getLessPackDetail() {
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("beginTime", mView.getBeginTime());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("productId", mView.getProductId());
        mSubscription = HttpUtils.getManager()
                .getForAbnormalOrderDetails(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<List<PackBoxDetail>>>() {
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
                    public void requestSuccess(Result<List<PackBoxDetail>> data) {
                        if (data != null) {
                            List<PackBoxDetail> packBoxDetailList = data.getData();
                            mView.setOrderDetailInfoList(packBoxDetailList);
                        }
                    }
                }));
    }
}
