package com.yun.mayi.pda.module.drawer.gather;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PayInfo;
import com.yun.mayi.pda.bean.ReceiptPrintInfo;
import com.yun.mayi.pda.bean.ReceptOrderInfo;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/5/28
 * 名称：订单收款数据处理接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface GatheringContract {
    interface View extends BaseContract.View {
        String getToken();

        String getAgentNumber();

        String getBeginTime();

        String getEndTime();

        String getKeyword();

        String getOrderId();

        String getTotalFee();

        String getAuthCode();

        String getAlipayTradeNo();
        void setPickOrderList(List<ReceptOrderInfo> receptOrderInfoList);
        void setReceiptPrintInfo(ReceiptPrintInfo receiptPrintInfo);
        void setPayInfo(PayInfo payInfo);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getOrderReceiptList();

        void receiptsPrint();

        void aliPay();

        void queryPayList();
    }
}

