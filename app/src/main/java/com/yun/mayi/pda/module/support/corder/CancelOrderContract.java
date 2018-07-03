package com.yun.mayi.pda.module.support.corder;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.PackBoxResult;

/**
 * 作者： wh
 * 时间：  2018/1/6
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface CancelOrderContract {
    interface View extends BaseContract.View{
        String getToken();

        String getKeyWord();

        String getBeginTime();

        String getEndTime();

        int getPage();

        void setParkBoxResult(PackBoxResult parkBox);
    }
    interface Presenter extends BaseContract.Presenter<View>{
        void getPackBoxList();
    }
}
