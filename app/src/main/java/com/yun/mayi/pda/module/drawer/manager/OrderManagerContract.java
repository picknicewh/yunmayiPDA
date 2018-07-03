package com.yun.mayi.pda.module.drawer.manager;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderResult;

/**
 * 作者： wh
 * 时间：  2018/6/25
 * 名称：订单管理数据处理接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface OrderManagerContract {
    interface View extends BaseContract.View {
        String getStartTime();

        String getEndTime();

        String getToken();

        String getKeyword();

        String getAgentNumber();

        int getPage();

        int getSize();

        String getNumber();
        void setPickOrderResult(PickOrderResult pickOrderResult);
    }

    interface Presenter extends BaseContract.Presenter<View> {
       void getTallyingList();
    }
}

