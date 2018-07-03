package com.yun.mayi.pda.module.drawer.tallying;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderDetailResult;

/**
 * 作者： wh
 * 时间：  2018/05/25
 * 名称：司机点货详情数据处理类接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface TallyingDetailContract {
    interface View extends BaseContract.View{
      String getToken();
      String getOrderId();
      String getAgentNumber();
      String getKeyword();
        String getOrderDetailIds();
        String getNums();
        int getIsPick();
        int getIsFirst();
        void onSuccess();
        void setPickOrderDetailResult(PickOrderDetailResult pickOrderDetailResult);
    }
    interface Presenter extends BaseContract.Presenter<View>{
     void getDeliveryOrderDetail();
     void agentFinishPick();
    }
}

