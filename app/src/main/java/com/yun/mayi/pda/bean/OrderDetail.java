package com.yun.mayi.pda.bean;

/**
 * 子订单
 */
public class OrderDetail {

    /**
     * 子订单ID
     */
    private long id;

    /**
     * 总订单ID
     */
    private String orderId;

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
     * 商品售价价格
     */
    private double productPrice;
    /**
     * 商品图片
     */
    private String productImage;

    /**
     * 买家购买数量
     */
    private int num;


    private int skuNum;
    /**
     * 异常数量
     */
    private int outOfStockNum;

    /**
     * 是否已打包
     */
    private boolean packed;

    /**
     *加盟商id
     */
    private String vendorId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isPacked() {
        return packed;
    }

    public void setPacked(boolean packed) {
        this.packed = packed;
    }

    public int getOutOfStockNum() {
        return outOfStockNum;
    }

    public void setOutOfStockNum(int outOfStockNum) {
        this.outOfStockNum = outOfStockNum;
    }

    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
