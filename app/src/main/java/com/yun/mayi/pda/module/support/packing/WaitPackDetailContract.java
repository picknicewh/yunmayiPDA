package com.yun.mayi.pda.module.support.packing;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.Order;
import com.yun.mayi.pda.bean.PackBox;

/**
 * 作者： wh
 * 时间：  2018/3/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface WaitPackDetailContract {
    interface View extends BaseContract.View{
        String getToken();

        String getOrderId();

        String getOrderDetailIds();

        String getNums();

        String getOrderDetailId();

        int getBoxNum();

        void setParkOrderInfo(Order orderInfo);

        void packSuccess(PackBox packBox);

        void outofStockBack(String result);
    }
    interface Presenter extends BaseContract.Presenter<View>{
        void getPackOrderDetail();
        void packBox();
        void outofStock();
    }
}
