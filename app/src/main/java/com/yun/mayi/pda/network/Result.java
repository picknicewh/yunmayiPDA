package com.yun.mayi.pda.network;

/**
 * 作者： wh
 * 时间：  2017/12/22
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class Result<T> {
    private String status;
    private String info;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
