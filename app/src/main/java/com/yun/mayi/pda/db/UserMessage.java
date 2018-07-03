package com.yun.mayi.pda.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.yun.mayi.pda.application.YunmayiApplication;

/**
 * ================================================
 * 作    者：wh
 * 时    间：2016/7/19
 * 描    述：用户信息存储类
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class UserMessage {
    private SharedPreferences spf;
    private SharedPreferences.Editor editor;
    private static UserMessage userInfo;

    public UserMessage(Context context) {
        spf = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        editor = spf.edit();
    }

    public synchronized static UserMessage getInstance() {
        if (null == userInfo) {
            userInfo = new UserMessage(YunmayiApplication.getInstance().getApplicationContext());
        }
        return userInfo;
    }


    /**
     * 当前用户查询用户信息id
     * 如果父账号的id=0则当前id = user_id
     * 如果父账号的id！=0则当前id = parent_id
     */
    public int getUId() {
        return spf.getInt("uid", 1);
    }

    /**
     * 设置当前用户查询用户信息id
     *
     * @param uid 用户信息id
     */
    public void setUId(int uid) {
        editor.putInt("uid", uid);
        editor.commit();
    }
    /**
     * 当前用户id
     * 可能是子账号的子id
     * 也可能是管理员的真实id
     */
    public int getUser_id() {
        return spf.getInt("user_id", 1);
    }

    /**
     * 设置用户id
     *
     * @param user_id 用户id
     */
    public void setUser_id(int user_id) {
        editor.putInt("user_id", user_id);
        editor.commit();
    }

    /**
     * 用户的姓名（登陆名）
     */
    public String getUsername() {
        return spf.getString("username", "");
    }

    /**
     * 设置用户的名字
     *
     * @param username 用户的名字
     */
    public void setUsername(String username) {
        editor.putString("username", username);
        editor.commit();
    }

    /**
     * 设置用户的密码（登陆的密码）
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        editor.putString("password", password);
        editor.commit();
    }

    /**
     * 用户的密码（登陆的密码）
     */
    public String getPassword() {
        return spf.getString("password", "");
    }

    /**
     * token
     *
     * @param token token 值
     */
    public void setToken(String token) {
        editor.putString("token", token);
        editor.commit();
    }

    public String getToken() {
        return spf.getString("token", null);
    }


    /**
     * @param type type 类型
     *type=1供应商  2分拣员  3配送员 4子账号 5加盟商
     */
    public void setType(int type) {
        editor.putInt("type", type);
        editor.commit();
    }

    public int getType() {
        return spf.getInt("type", -1);
    }

    /**
     * @param update 是否需要更新
     */
    public void setUpdate(boolean update) {
        editor.putBoolean("update", update);
        editor.commit();
    }

    public boolean getUpdate() {
        return spf.getBoolean("update", true);
    }

    /**
     * 获取加盟商编号
     */
    public String getAgentNumber(){
      return spf.getString("agentNumber","");
    }
    /**
     * 设置加盟商编号
     * @param agentNumber 加盟商编号
     */
    public void setAgentNumber(String agentNumber){
        editor.putString("agentNumber", agentNumber);
        editor.commit();
    }


}


