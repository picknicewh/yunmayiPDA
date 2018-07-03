package com.yun.mayi.pda.module.join.goodspack;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderDetailResult;

/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：按货分拣商品数据处理接口类
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface GoodsPackContract {
    interface View extends BaseContract.View {
        /**
         * 开始时间
         */
        String getBeginTime();
        /**
         * 结束时间
         */
        String getEndTime();

        String getToken();

        String getAgentNumber();

        String getKeyword();
        void setPickOrderDetailResult(PickOrderDetailResult pickOrderDetailResult);
    }
    interface Presenter extends BaseContract.Presenter<View> {
      void  getPickByProduct();
    }
}
