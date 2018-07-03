package com.yun.mayi.pda.module.support.packbox;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PackBox;

/**
 * 作者： wh
 * 时间：  2018/1/6
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface PackBoxDetailContract {
    interface View extends BaseContract.View{
        String getToken();

        String getBoxId();

        void setParkBox(PackBox parkBox);

        void setUnBoxResult(String result );
    }
     interface Presenter extends BaseContract.Presenter<View>{
         void getPackBoxDetail();
         void unBox();
     }
}
