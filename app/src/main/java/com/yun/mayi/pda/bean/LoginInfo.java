package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/1/2
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class LoginInfo {

    /**
     * token : 380fa09c15886d3095f0f7ae7a6151da
     * type : 4
     * user_id : 100672
     * agent_number:57100000
     */

    private String token;
    private int type;
    private int user_id;
     private String agent_number;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getAgent_number() {
        return agent_number;
    }

    public void setAgent_number(String agent_number) {
        this.agent_number = agent_number;
    }
}
