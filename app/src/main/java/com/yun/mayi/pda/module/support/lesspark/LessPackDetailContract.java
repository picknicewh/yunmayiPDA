package com.yun.mayi.pda.module.support.lesspark;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PackBoxDetail;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/1/6
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface LessPackDetailContract  {

    interface View extends BaseContract.View{
        String getToken();

        String getProductId();

        String getBeginTime();

        String getEndTime();

        void setOrderDetailInfoList(List<PackBoxDetail> packBoxDetailList);
    }
    interface Presenter extends BaseContract.Presenter<View>{
        void getLessPackDetail();
    }
}
