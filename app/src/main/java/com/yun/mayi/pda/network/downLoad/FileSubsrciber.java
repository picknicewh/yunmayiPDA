package com.yun.mayi.pda.network.downLoad;

import com.yun.mayi.pda.network.ExceptionEngine;
import com.yun.mayi.pda.utils.G;

import rx.Subscriber;

/**
 * 作者： wh
 * 时间：  2018/3/14
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class FileSubsrciber<T> extends Subscriber<T> {

    /**
     * 简单回调接口
     */
    private FileCallBack<T> mFileCallBack;
    public FileSubsrciber( FileCallBack<T> mFileCallBack) {
        this.mFileCallBack = mFileCallBack;
    }

    /**
     * 观察者开始时的回调。调用简单回调开始的方法
     */
    @Override
    public void onStart() {
        super.onStart();
        if (mFileCallBack != null)
            mFileCallBack.onStart();
    }

    /**
     * 观察者完成时的回调，调用简单回调完成时的方法
     */
    @Override
    public void onCompleted() {
        if (mFileCallBack != null)
            mFileCallBack.onCompleted();
        // 数据完成后取消订阅。释放资源，避免内存泄漏.Subscriber内部在onNext和onError后会自动取消订阅
    }

    /**
     * 观察者错误时的回调方法，调用简单回调完成时的方法。因为不管错误还是成功，都代表当前事件已经执行完毕了
     *
     * @param e 错误类型
     */
    @Override
    public void onError(Throwable e) {
        ExceptionEngine.ResponseThrowable exceptionEngine = ExceptionEngine.handleException(e);
        String error = exceptionEngine.message;
        if (G.isEmteny(error)) {
            error = "未知错误！";
        }
        if (mFileCallBack != null) {
            mFileCallBack.onError(error);
        }
    }

    /**
     * 观察者普通事件的回调方法。调用简单回调下一步的方法
     *
     * @param t
     */
    @Override
    public void onNext(T t) {
        if (mFileCallBack != null)
            mFileCallBack.onNext(t);
    }

}
