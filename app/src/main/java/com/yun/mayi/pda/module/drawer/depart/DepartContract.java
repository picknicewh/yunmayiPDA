package com.yun.mayi.pda.module.drawer.depart;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderResult;

/**
 * 作者： wh
 * 时间：  2018/06/07
 * 名称：司机发车数据处理接口类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface DepartContract {
    interface View extends BaseContract.View {
        String getToken();

        String getAgentNumber();

        String getBeginTime();

        String getEndTime();

        String getKeyword();

        int getPage();

        int getSize();

        String getOrderIdStr();

        void setPickOrderResult(PickOrderResult pickOrderResult);

        void onSuccess();
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getTallyingList();
        void startDelivery();
    }
}

