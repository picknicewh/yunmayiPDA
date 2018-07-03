package com.yun.mayi.pda.module.support.packing;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PackOrderResult;

/**
 * 作者： wh
 * 时间：  2018/3/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface WaitPackContract {
    interface View extends BaseContract.View{
        String getToken();

        String getKeyWord();

        String getBeginTime();

        String getEndTime();

        int getStatus();

        void setParkOrderInfo(PackOrderResult parkInfo);
        /**
         * 分拣员id
         */
        int getPickerId();
    }
    interface Presenter extends BaseContract.Presenter<View>{
        void getPackList();
    }
}
