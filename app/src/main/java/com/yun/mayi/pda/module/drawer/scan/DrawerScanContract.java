package com.yun.mayi.pda.module.drawer.scan;

import com.yun.mayi.pda.base.BaseContract;

/**
 * 作者： wh
 * 时间：  2018/5/28
 * 名称：订单收款数据处理接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface DrawerScanContract {
    interface View extends BaseContract.View {
        String getToken();
        String getOrderId();
        void onSuccess();
        void onFail();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void deliveryScan();

    }
}

