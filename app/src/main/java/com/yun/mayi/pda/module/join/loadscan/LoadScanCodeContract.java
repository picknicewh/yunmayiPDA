package com.yun.mayi.pda.module.join.loadscan;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.DrawerInfo;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：装车扫描数据处理类接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface LoadScanCodeContract {
    interface View extends BaseContract.View{
      String getToken();
      String getOrderId();
      String getAgentNumber();
      int getDeliverymanId();
        void setDrawerInfoList(List<DrawerInfo> drawerInfoList);
      void success();
      void fail();
    }
    interface Presenter extends BaseContract.Presenter<View>{
      void getDeliverymanList();
      void distributeLoading();
    }
}

