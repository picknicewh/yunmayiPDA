package com.yun.mayi.pda.bean;

/**
 * 打包箱明细
 */
public class PackBoxDetail {

    /**
     * 明细ID
     */
    private long id;

    /**
     * 箱ID
     */
    private long boxId;

    /**
     * 供应商ID
     */
    private long vendorId;

    /**
     * 总订单ID
     */
    private String orderId;

    /**
     * 子订单ID
     */
    private long orderDetailId;

    /**
     * 商品ID
     */
    private long productId;

    /**
     * 商品标题
     */
    private String productTitle;

    /**
     * 商品编号
     */
    private String productNumber;

    /**
     * 商品条形码
     */
    private String productBarCode;

    /**
     * 商品规格
     */
    private String productSpec;

    /**
     * 商品单位
     */
    private String productUnit;

    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 原始数量【买家购买数量】
     */
    private int originNum;

    /**
     * 装箱商品真实数量
     */
    private int num;

    /**
     * 订单标识
     */
    private String orderMark;

    private int boxNum;
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductBarCode() {
        return productBarCode;
    }

    public void setProductBarCode(String productBarCode) {
        this.productBarCode = productBarCode;
    }

    public String getProductSpec() {
        return productSpec;
    }

    public void setProductSpec(String productSpec) {
        this.productSpec = productSpec;
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getOriginNum() {
        return originNum;
    }

    public void setOriginNum(int originNum) {
        this.originNum = originNum;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(String orderMark) {
        this.orderMark = orderMark;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }
}
