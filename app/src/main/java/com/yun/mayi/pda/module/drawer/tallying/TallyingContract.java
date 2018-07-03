package com.yun.mayi.pda.module.drawer.tallying;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderResult;

/**
 * 作者： wh
 * 时间：  2018/05/25
 * 名称：司机点货详情数据处理类接口
 * 版本说明
 * 附加注释：
 * 主要接口：
 */
public interface TallyingContract {
    interface View extends BaseContract.View {
        String getToken();

        String getBeginTime();

        String getEndTime();

        String getKeyword();

        int getPage();

        int getSize();

        String getAgentNumber();

        String getNumber();

        void setPickOrderResult(PickOrderResult pickOrderResult);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getTallyingList();

    }
}

