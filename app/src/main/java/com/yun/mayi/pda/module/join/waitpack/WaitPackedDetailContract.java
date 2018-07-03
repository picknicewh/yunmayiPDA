package com.yun.mayi.pda.module.join.waitpack;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderDetailResult;

/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：待拣货单详情单件商品数据处理接口类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface WaitPackedDetailContract {
    interface View extends BaseContract.View{

        /**
         * 加盟商编号
         */
        String getAgentNumber();
        /**
         * 关键字（订单号／标识）
         */
        String getKeyword();
        String getToken();
        /**
         * 订单id
         */
        String getOrderId();
        /**
         * 子订单ID列表
         */
        String getOrderDetailIds();
        /**
         * nums
         */
        String getNums();

        int getIsPick();
        void setPickOrderDetailResult(PickOrderDetailResult pickOrderDetailResult);
        void pickSuccess();
    }
    interface Presenter extends BaseContract.Presenter<View>{
        void getPickOrderDetail();
        void pickFinish();
    }
}
