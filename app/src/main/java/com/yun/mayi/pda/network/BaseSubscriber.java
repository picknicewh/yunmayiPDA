package com.yun.mayi.pda.network;

import com.yun.mayi.pda.utils.G;
import com.yun.mayi.pda.utils.NetworkUtil;

import rx.Subscriber;

/**
 * 作者： wh
 * 时间：  2017/12/22
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class BaseSubscriber<T> extends Subscriber<T> {

    private RequestCallback<T> mRequestCallback;
    private String SUCCESS_CODE = "success";
    private String ERROR_CODE = "fail";

    public BaseSubscriber(RequestCallback<T> mRequestCallback) {
        this.mRequestCallback = mRequestCallback;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mRequestCallback != null) {
            mRequestCallback.beforeRequest();
        }
        if (!NetworkUtil.isNetworkAvailable()) {
            mRequestCallback.requestError("无网络，请检查网络！");
            onCompleted();
        }
    }

    @Override
    public void onCompleted() {
        if (mRequestCallback != null) {
            mRequestCallback.requestComplete();
        }
    }

    @Override
    public void onError(Throwable e) {
        ExceptionEngine.ResponseThrowable exceptionEngine = ExceptionEngine.handleException(e);
        String error = exceptionEngine.message;
        if (G.isEmteny(error)) {
            error = "未知错误！";
        }
        if (mRequestCallback != null) {
            mRequestCallback.requestError(error);
        }
    }

    @Override
    public void onNext(T t) {
        if (mRequestCallback == null) {
            G.log("-------"+"没有数据");
            return;
        }
        if (t instanceof Result) {
            Result result = (Result) t;
            if (SUCCESS_CODE.equals(result.getStatus())) {
                mRequestCallback.requestSuccess(t);
                G.log("-------"+result.getStatus());
            } else if (ERROR_CODE.equals(result.getStatus())) {
                mRequestCallback.requestError(result.getInfo());
            }
        }
    }

}
