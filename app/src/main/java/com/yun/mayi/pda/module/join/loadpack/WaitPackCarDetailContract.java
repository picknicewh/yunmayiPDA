package com.yun.mayi.pda.module.join.loadpack;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderDetailResult;

/**
 * 作者： wh
 * 时间：  2018/05/13
 * 名称：待拣货单数据处理类接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface WaitPackCarDetailContract {
    interface View extends BaseContract.View{
      String getToken();
      String getOrderId();
      String getAgentNumber();
      String getKeyword();
      String getOrderDetailIds();
      String getNums();
        void onSuccess();
     void setPickOrderDetailResult(PickOrderDetailResult pickOrderDetailResult);

    }
    interface Presenter extends BaseContract.Presenter<View>{
       void getDeliveryOrderDetail();
       void agentFinishPick();
    }
}

