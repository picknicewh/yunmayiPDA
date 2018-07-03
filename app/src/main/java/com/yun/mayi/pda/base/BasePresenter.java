package com.yun.mayi.pda.base;

import rx.Subscription;

/**
 * 作者： wh
 * 时间：  2017/8/21
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class BasePresenter<T extends BaseContract.View> implements BaseContract.Presenter<T> {


    public Subscription mSubscription;
    public T mView;

    @Override
    public void attach(T mView) {
        this.mView = mView;
    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null && mSubscription.isUnsubscribed()) {
            mSubscription.isUnsubscribed();
        }
    }
}
