package com.yun.mayi.pda.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 作者： wh
 * 时间：  2018/4/3
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@DatabaseTable(tableName = "box_info")
public class BoxInfo {
    /**
     * 子订单ID
     */
    public static final String ID = "id";
    /**
     * 箱ID
     */
    public static  final String BOXID ="boxId";
    /**
     * 供应商ID
     */
    public static  final String VENDORID  =  "vendorId";
    /**
     * 总订单ID
     */
    public static   final String ORDERID="orderId" ;
    /**
     * 子订单ID
     */
    public  static   final String ORDERDETAILID = "orderDetailId" ;
    /**
     * 商品ID
     */
    public  static   final String  PRODUCTID  = "productId" ;
    /**
     * 商品标题
     */
    public static   final String  PRODUCTTITLE =  "productTitle" ;
    /**
     * 商品编号
     */
    public static final String PRODUCTNUMBER = "productNumber";
    /**
     * 商品条形码
     */
    public static final String PRODUCETBARCODE = "productBarCode";
    /**
     * 商品规格
     */
    public static final String PRODUCTSPEC = "productSpec";
    /**
     * 商品单位
     */
    public static final String PRODUCTUTIL = "productUtil";
    /**
     * 商品图片
     */
    public static final String PRODUCTIMAGE = "productImage";

    /**
     *商品价格
     */
    public static final String PRODUCTPRICE = "productPrice";
    /**
       * 买家购买数量
     */
    public static final String NUM = "num";
    /**
     * 原始数量【买家购买数量】
     */
    public static final String  ORIGINNUM=  "originNum" ;
    /**
     * 订单标识
     */
    public static final String ORDERMARK = "orderMark";
    /**
     * 是否已打包
     */
    public static final String PACKED = "packed";
    /**
     * 打包数量
     */
    public static final String BOXNUM = "boxNum";

    @DatabaseField(canBeNull = false, columnName = ID, dataType = DataType.STRING)
    private String id;

    @DatabaseField(canBeNull = false, columnName = ORDERID, dataType = DataType.STRING)
    private String orderId;

    @DatabaseField(canBeNull = false, columnName = PRODUCTID, dataType = DataType.STRING)
    private String productId;

    @DatabaseField(columnName = BOXID, dataType = DataType.STRING)
    private String   boxId;

    @DatabaseField(columnName = ORDERDETAILID, dataType = DataType.STRING)
    private String orderDetailId;

    @DatabaseField( columnName = VENDORID, dataType = DataType.STRING)
    private String vendorId;

    @DatabaseField(columnName = PRODUCTTITLE, dataType = DataType.STRING)
    private String productTitle;

    @DatabaseField(columnName = PRODUCTNUMBER, dataType = DataType.STRING)
    private String productNumber;

    @DatabaseField(columnName = PRODUCETBARCODE, dataType = DataType.STRING)
    private String productBarCode;


    @DatabaseField(columnName = PRODUCTSPEC, dataType = DataType.STRING)
    private String productSpec;

    @DatabaseField(columnName = PRODUCTUTIL, dataType = DataType.STRING)
    private String productUnit;

    @DatabaseField(columnName = PRODUCTIMAGE, dataType = DataType.STRING)
    private String productImage;

    @DatabaseField(columnName = PRODUCTPRICE, dataType = DataType.DOUBLE)
    private double productPrice;

    @DatabaseField(columnName = ORDERMARK, dataType = DataType.STRING)
    private String orderMark;

    @DatabaseField(columnName = NUM, dataType = DataType.INTEGER)
    private int num;

    @DatabaseField(columnName = ORIGINNUM, dataType = DataType.INTEGER)
    private int originalNum;

    @DatabaseField(columnName = PACKED, dataType = DataType.INTEGER)
    private int packed;
    @DatabaseField(columnName = BOXNUM, dataType = DataType.INTEGER)
    private int boxNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
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

    public String getOrderMark() {
        return orderMark;
    }

    public void setOrderMark(String orderMark) {
        this.orderMark = orderMark;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getOriginalNum() {
        return originalNum;
    }

    public void setOriginalNum(int originalNum) {
        this.originalNum = originalNum;
    }

    public int getPacked() {
        return packed;
    }

    public void setPacked(int packed) {
        this.packed = packed;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }
}
