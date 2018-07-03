package com.yun.mayi.pda.module.login;

import com.yun.mayi.pda.base.BaseContract;
import com.yun.mayi.pda.bean.LoginInfo;
import com.yun.mayi.pda.bean.Version;

/**
 * 作者： wh
 * 时间：  2018/3/28
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public interface LoginContract {
    interface View extends BaseContract.View {
        String getUserName();

        String getPassword();

        void setLoginInfo(LoginInfo loginInfo);

        void setVersion(Version version);


    }

    interface Presenter extends BaseContract.Presenter<View> {
        void login();

        void getVersion();
    }
}
