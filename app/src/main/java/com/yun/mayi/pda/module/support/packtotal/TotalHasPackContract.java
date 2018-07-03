package com.yun.mayi.pda.module.support.packtotal;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PackInfoDetailResult;

/**
 * 作者： wh
 * 时间：  2018/3/27
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface TotalHasPackContract {

    interface View extends BaseContract.View{
      String getToken();
      String getBeginTime();
      String getEndTime();
        String getKeyword();
      String getPackBoxDetailId();

      void setHasPackInfoData( PackInfoDetailResult hasPackInfoResult);
        void success();
    }
    interface Presenter extends BaseContract.Presenter<View>{
       void getVerifyVendorProductList();
        void delVerifyVendorProduct();


    }
}
