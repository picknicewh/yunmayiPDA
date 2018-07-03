package com.yun.mayi.pda.db;


import com.yun.mayi.pda.bean.LoginInfo;

/**
 * ================================================
 * 作    者：wh
 * 时    间：2018/1/2
 * 描    述：
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class UserAction {

    public static void saveLoginInfo(LoginInfo info) {
        UserMessage userMessage = UserMessage.getInstance();
        userMessage.setToken(info.getToken());
        userMessage.setType(info.getType());
        userMessage.setUser_id(info.getUser_id());
        userMessage.setAgentNumber(info.getAgent_number());
    }

    /**
     * 保存用户登录名和密码
     *
     * @param loginName
     * @param passWord
     */
    public static void saveLoginMessage(String loginName, String passWord) {
        UserMessage um = UserMessage.getInstance();
        um.setUsername(loginName);
        um.setPassword(passWord);
    }
}
