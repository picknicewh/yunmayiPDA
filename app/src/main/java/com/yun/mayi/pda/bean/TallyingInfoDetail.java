package com.yun.mayi.pda.bean;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 作者： wh
 * 时间：  2018/5/28
 * 名称：司机点货详情
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@DatabaseTable(tableName = "tallying_info")
public class TallyingInfoDetail {
    /**
     * id : 23034332
     * orderId : 1804102251256956
     * productId : 515134
     * productTitle : 【清仓】沙宣洗发露 （炫亮彩护）200ml 0092
     * vendorId : 16694328
     * productBarCode : 6903148070092
     * productMiddleCode :
     * productBoxCode :
     * productPrice : 0
     * productSpec : 12瓶/箱
     * productUnit : 瓶
     * productImage : /upload/2018/01/31/f97e0591fdd8dd809fd1d3c687d3d020.jpg
     * quantity : 3---->下单数量
     * pickQuantity : 0---->加盟商分拣数量
     * deliverQuantity : 3---->司机配送数量
     * loadingQuantity : 3---->加盟商装车数量
     * finishQuantity : 3---->收款数量
     * outOfStockNum : 0
     * boxId : 0
     */
    /**
     * 子订单ID
     */
    public static final String ID = "id";
    /**
     * 总订单ID
     */
    public static final String ORDERID = "orderId";
    /**
     * 商品ID
     */
    public static final String PRODUCTID = "productId";
    /**
     * 箱ID
     */
    public static final String BOXID = "boxId";
    /**
     * 供应商id
     */
    public static final String VENDORID = "vendorId";
    /**
     * 商品标题
     */
    public static final String PRODUCTTITLE = "productTitle";

    /**
     * 商品条形码
     */
    public static final String PRODUCETBARCODE = "productBarCode";
    /**
     * 商品中码条形码
     */
    public static final String PRODUCETMIDDLECODE = "productMiddleCode";

    /**
     * 商品箱条形码
     */
    public static final String PRODUCETBOXCODE = "productBoxCode";
    /**
     * 商品价格
     */
    public static final String PRODUCTEPRICE = "productPrice";
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
     * 买家购买数量
     */
    public static final String QUANTITY = "quantity";
    /**
     * 拣货数量
     */
    public static final String PICKQUANTITY = "pickQuantity";
    /**
     * 配送数量
     */
    public static final String DELIVERQUANTITY = "deliverQuantity";
    /**
     * 加载数量
     */
    public static final String LOADINGQUANTITY = "loadingQuantity";
    /**
     * 完成数量
     */
    public static final String FINISHQUANTITY = "finishQuantity";
    /**
     * 异常数量
     */
    public static final String OUTOFSTOCKNUM = "outOfStockNum";
    /**
     * 是否已打包
     */
    public static final String PACKED = "packed";

    @DatabaseField(canBeNull = false, columnName = ID, dataType = DataType.STRING)
    private String id;

    @DatabaseField(canBeNull = false, columnName = ORDERID, dataType = DataType.STRING)
    private String orderId;

    @DatabaseField(canBeNull = false, columnName = PRODUCTID, dataType = DataType.STRING)
    private String productId;

    @DatabaseField(columnName = VENDORID, dataType = DataType.STRING)
    private String vendorId;

    @DatabaseField(columnName = PRODUCTTITLE, dataType = DataType.STRING)
    private String productTitle;

    @DatabaseField(columnName = BOXID, dataType = DataType.STRING)
    private String boxId;

    @DatabaseField(columnName = PRODUCTEPRICE, dataType = DataType.DOUBLE)
    private double productPrice;

    @DatabaseField(columnName = PRODUCETBARCODE, dataType = DataType.STRING)
    private String productBarCode;
    @DatabaseField(columnName = PRODUCETMIDDLECODE, dataType = DataType.STRING)

    private String productMiddleCode;

    @DatabaseField(columnName = PRODUCETBOXCODE, dataType = DataType.STRING)
    private String productBoxCode;

    @DatabaseField(columnName = PRODUCTSPEC, dataType = DataType.STRING)
    private String productSpec;

    @DatabaseField(columnName = PRODUCTUTIL, dataType = DataType.STRING)
    private String productUnit;

    @DatabaseField(columnName = PRODUCTIMAGE, dataType = DataType.STRING)
    private String productImage;

    @DatabaseField(columnName = QUANTITY, dataType = DataType.INTEGER)
    private int quantity;

    @DatabaseField(columnName = PICKQUANTITY, dataType = DataType.INTEGER)
    private int pickQuantity;

    @DatabaseField(columnName = DELIVERQUANTITY, dataType = DataType.INTEGER)
    private int deliverQuantity;
    @DatabaseField(columnName = LOADINGQUANTITY, dataType = DataType.INTEGER)

    private int loadingQuantity;
    @DatabaseField(columnName = FINISHQUANTITY, dataType = DataType.INTEGER)

    private int finishQuantity;
    @DatabaseField(columnName = OUTOFSTOCKNUM, dataType = DataType.INTEGER)

    private int outOfStockNum;
    @DatabaseField(columnName = PACKED, dataType = DataType.INTEGER)
    private int packed;

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

    public String getBoxId() {
        return boxId;
    }

    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductBarCode() {
        return productBarCode;
    }

    public void setProductBarCode(String productBarCode) {
        this.productBarCode = productBarCode;
    }

    public String getProductMiddleCode() {
        return productMiddleCode;
    }

    public void setProductMiddleCode(String productMiddleCode) {
        this.productMiddleCode = productMiddleCode;
    }

    public String getProductBoxCode() {
        return productBoxCode;
    }

    public void setProductBoxCode(String productBoxCode) {
        this.productBoxCode = productBoxCode;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPickQuantity() {
        return pickQuantity;
    }

    public void setPickQuantity(int pickQuantity) {
        this.pickQuantity = pickQuantity;
    }

    public int getDeliverQuantity() {
        return deliverQuantity;
    }

    public void setDeliverQuantity(int deliverQuantity) {
        this.deliverQuantity = deliverQuantity;
    }

    public int getLoadingQuantity() {
        return loadingQuantity;
    }

    public void setLoadingQuantity(int loadingQuantity) {
        this.loadingQuantity = loadingQuantity;
    }

    public int getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(int finishQuantity) {
        this.finishQuantity = finishQuantity;
    }

    public int getOutOfStockNum() {
        return outOfStockNum;
    }

    public void setOutOfStockNum(int outOfStockNum) {
        this.outOfStockNum = outOfStockNum;
    }

    public int getPacked() {
        return packed;
    }

    public void setPacked(int packed) {
        this.packed = packed;
    }
}
