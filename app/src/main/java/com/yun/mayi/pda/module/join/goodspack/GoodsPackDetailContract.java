package com.yun.mayi.pda.module.join.goodspack;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PickOrderDetail;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/30
 * 名称：按货分拣详情适配器
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface GoodsPackDetailContract {
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

        String getProductId();

        void setPickOrderDetailList(List<PickOrderDetail> pickOrderDetailList);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void getPickByProductDetail();
    }
}
