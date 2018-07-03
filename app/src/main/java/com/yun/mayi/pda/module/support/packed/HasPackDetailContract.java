package com.yun.mayi.pda.module.support.packed;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.Order;

/**
 * 作者： wh
 * 时间：  2018/1/6
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface HasPackDetailContract  {

    interface View extends BaseContract.View{
        String getToken();

        String getOrderId();

        void setParkOrderInfo(Order orderInfo);
    }
    interface Presenter extends BaseContract.Presenter<View>{
        void getPackOrderDetail();
    }
}
