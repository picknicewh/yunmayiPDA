package com.yun.mayi.pda.module.support.sendnote;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PackNoteInfoResult;

/**
 * 作者： wh
 * 时间：  2018/3/27
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface TotalWaitPackContract {
    interface View extends BaseContract.View {
        String getToken();

        String getBeginTime();

        String getEndTime();

        String getKeyword();

        int getProductId();

        String getProductIds();

        int getNum();

        String getNums();

        int getOrignNum();

        String getOrignNums();

        String getOrderCreateTime();
        void setPackNoteInfoData(PackNoteInfoResult packNoteInfoResult);

        void conformSuccess();

    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getVendorOrderInfo();

        void verifyProductNumber();

        void verifyAllProductNumber();
    }
}
