package com.yun.mayi.pda.module.join.loadpack;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrder;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：待见货单页面数据处理类接口
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface WaitPackCarContract {
    interface View extends BaseContract.View{
      String getToken();
      String getBeginTime();
      String getEndTime();
      String getKeyword();
       void setPickOrderList( List<PickOrder> pickOrderList);
    }
    interface Presenter extends BaseContract.Presenter<View>{
        void getLoadingOrderList();
    }
}

