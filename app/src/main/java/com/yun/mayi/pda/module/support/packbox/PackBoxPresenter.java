package com.yun.mayi.pda.module.support.packbox;

import com.yun.mayi.pda.base.BasePresenter;
import com.yun.mayi.pda.bean.PackBoxResult;
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
 * 时间：  2018/1/6
 * 名称：装箱列表数据处理
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackBoxPresenter extends BasePresenter<PackBoxContract.View> implements PackBoxContract.Presenter {
    @Inject
    public PackBoxPresenter(){

   }
   @Override
    public void getPackBoxManagert(){
        RequestParameter parameter = new RequestParameter();
        parameter.setParam("beginTime", mView.getBeginTime());
        parameter.setParam("endTime", mView.getEndTime());
        parameter.setParam("token", mView.getToken());
        parameter.setParam("page", mView.getPage());
        parameter.setParam("keyword", mView.getKeyWord());
        parameter.setParam("pickerId", UserMessage.getInstance().getUser_id());
        mSubscription = HttpUtils.getManager()
                .getPackBoxList(parameter.getMapParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<>(new RequestCallback<Result<PackBoxResult>>() {
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
                    public void requestSuccess(Result<PackBoxResult> data) {
                        if (data != null) {
                            PackBoxResult  packBoxResult = data.getData();
                            mView.setParkBoxResult(packBoxResult);
                        }
                    }
                }));
    }
}
