package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/6/5
 * 名称：支付结果
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PayResult {
    /**
     * tradeNo : 2018060421001004720524206059
     * tradeStatus : TRADE_SUCCESS
     * buyerLogonId : 648***@qq.com
     * buyerPayAmount : 0.01
     * totalAmount : 0.01
     * receiptAmount : null
     * sendPayDate : 2018-06-04 18:18:32
     */

    private String tradeNo;
    private String tradeStatus;
    private String buyerLogonId;
    private String buyerPayAmount;
    private String totalAmount;
    private Object receiptAmount;
    private String sendPayDate;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getBuyerLogonId() {
        return buyerLogonId;
    }

    public void setBuyerLogonId(String buyerLogonId) {
        this.buyerLogonId = buyerLogonId;
    }

    public String getBuyerPayAmount() {
        return buyerPayAmount;
    }

    public void setBuyerPayAmount(String buyerPayAmount) {
        this.buyerPayAmount = buyerPayAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Object getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Object receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getSendPayDate() {
        return sendPayDate;
    }

    public void setSendPayDate(String sendPayDate) {
        this.sendPayDate = sendPayDate;
    }
}
