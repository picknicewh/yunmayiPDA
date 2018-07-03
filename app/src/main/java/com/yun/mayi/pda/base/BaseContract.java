package com.yun.mayi.pda.base;

/**
 * 作者： wh
 * 时间：  2018/3/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface BaseContract {
    interface View {
        /**显示*/
        void showProgress();
        /**隐藏*/
        void hideProgress();
        /**失败*/
        void showMessage(String message);
    }
    interface  Presenter< T extends View>{
        void attach(T mView);
        void detach();
        void unSubscribe();
    }
}
