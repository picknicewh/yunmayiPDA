package com.yun.mayi.pda.module.join.conform;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.AgentInfo;
import com.yun.mayi.pda.bean.ConformGoodsResult;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/28
 * 名称：确认收货数据处理接口类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface ConformGoodsContract {
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
        int getVendorId();
        int getType();
        String getStartTime();
        String getEndTime();
        String getPackBoxDetailIdList();
        String getNumList();
        String getBoxNumList();
        void setConformGoods(ConformGoodsResult result);
        void setAgentInfoList(List<AgentInfo>agentInfoList);
        void conformSuccess();
    }
     interface Presenter extends BaseContract.Presenter<View>{
          void getConformList();

         void verifyVendorAllProduct();
         void getDifferentAgentList();
     }
}
