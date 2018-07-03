package com.yun.mayi.pda.network;

/**
 * 作者： wh
 * 时间：  2017/12/22
 * 名称：网络返回回调
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface RequestCallback<T> {
    /**
     * 请求之前调用
     */
    void beforeRequest();

    /**
     * 请求错误调用
     *
     * @param msg 错误信息
     */
    void requestError(String msg);

    /**
     * 请求完成调用
     */
    void requestComplete();

    /**
     * 请求成功调用
     *
     * @param data 数据
     */
    void requestSuccess(T data);
}
