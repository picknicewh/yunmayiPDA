package com.yun.mayi.pda.module.join.manager;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickDetailInfoResult;

/**
 * 作者： wh
 * 时间：  2018/3/16
 * 名称：分拣管理详情数据处理类接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface PackManagerDetailContract {
    interface View extends BaseContract.View {
        /**
         * 加盟商编号
         */
        String getAgentNumber();

        String getToken();

        /**
         * 订单id
         */
        String getOrderId();

       void setPickDetailInfoResult(PickDetailInfoResult pickDetailInfoResult);


    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getPickOrderDetail();
    }
}
