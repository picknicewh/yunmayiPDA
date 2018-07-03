package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/6/1
 * 名称：订单收款订单信息
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ReceptOrderInfo {
    /**
     * orderId : 1806051612001741
     * mark : 临安1
     * originTotalSellPrice : 66000
     * payTotalSellPrice : 66000
     * realPayTotalSellPrice : 66000
     * payType : 1
     * payPlatform : cod
     * payPlatformNo :
     * isPay : 0
     */

    private String orderId;
    private String mark;
    private double originTotalSellPrice;
    private double payTotalSellPrice;
    private double realPayTotalSellPrice;
    private int payType;
    private String payPlatform;
    private String payPlatformNo;
    private int isPay;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public double getOriginTotalSellPrice() {
        return originTotalSellPrice;
    }

    public void setOriginTotalSellPrice(double originTotalSellPrice) {
        this.originTotalSellPrice = originTotalSellPrice;
    }

    public double getPayTotalSellPrice() {
        return payTotalSellPrice;
    }

    public void setPayTotalSellPrice(double payTotalSellPrice) {
        this.payTotalSellPrice = payTotalSellPrice;
    }

    public double getRealPayTotalSellPrice() {
        return realPayTotalSellPrice;
    }

    public void setRealPayTotalSellPrice(int realPayTotalSellPrice) {
        this.realPayTotalSellPrice = realPayTotalSellPrice;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(String payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getPayPlatformNo() {
        return payPlatformNo;
    }

    public void setPayPlatformNo(String payPlatformNo) {
        this.payPlatformNo = payPlatformNo;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }


}
