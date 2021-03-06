package com.yun.mayi.pda.module.join.manager;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderResult;

/**
 * 作者： wh
 * 时间：  2018/3/16
 * 名称：分拣管理页面数据处理类接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface PackManagerContract {
    interface View extends BaseContract.View {
        /**
         * 开始时间
         */
        String getBeginTime();

        /**
         * 结束时间
         */
        String getEndTime();

        /**
         * 加盟商编号
         */
        String getAgentNumber();

        /**
         * 关键字（订单号／标识）
         */
        String getKeyword();

        String getToken();

        int getPage();

        int getSize();


        void setPickOrderResult(PickOrderResult packBoxResult);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getConformList();
    }
}
