package com.yun.mayi.pda.module.drawer.manager;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;

/**
 * 作者： wh
 * 时间：  2018/6/25
 * 名称：订单管理数据处理接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface OrderManagerDetailContract {
    interface View extends BaseContract.View {
        String getToken();

        String getAgentNumber();

        String getOrderId();
        void setReceiptPrintInfo(ReceiptPrintInfo receiptPrintInfo);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void receiptsPrint();

    }
}

