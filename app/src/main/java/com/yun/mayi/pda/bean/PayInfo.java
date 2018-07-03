package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/6/4
 * 名称：支付信息
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PayInfo {

    /**
     * process : false
     * alipayTradeNo : 2018060421001004720521814634
     */

    private boolean process;
    private String alipayTradeNo;

    public boolean isProcess() {
        return process;
    }

    public void setProcess(boolean process) {
        this.process = process;
    }

    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }
}
