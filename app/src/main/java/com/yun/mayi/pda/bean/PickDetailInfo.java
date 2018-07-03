package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/5/10
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PickDetailInfo {
    /**
     * orderId : 1803311430354818
     * totalQuantity : 20
     * outNum : 0
     * outPrice : 0
     * productTitle : BR36SJ清风原木150抽金装3层抽纸8850【连锁】
     * productId : 56492
     */

    private String orderId;
    private int totalQuantity;
    private int outNum;
    private float outPrice;
    private String productTitle;
    private int productId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getOutNum() {
        return outNum;
    }

    public void setOutNum(int outNum) {
        this.outNum = outNum;
    }

    public float getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(float outPrice) {
        this.outPrice = outPrice;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
