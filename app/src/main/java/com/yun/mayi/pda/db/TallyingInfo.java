package com.yun.mayi.pda.db;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 作者： wh
 * 时间：  2018/5/28
 * 名称：司机点货数据信息
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
@DatabaseTable(tableName = "tallying_info")
public class TallyingInfo {
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
    public static  final String BOXID ="boxId";
    /**
     * 供应商id
     */
    public static  final String VENDORID = "vendorId";
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
    public static final String PRODUCTEPRICE= "productPrice";
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
    public static final String NUM = "num";
    /**
     * 原始数量
     */
    public static final String ORIGINALNUM = "originalNum";
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

    @DatabaseField(columnName = NUM, dataType = DataType.INTEGER)
    private int num;

    @DatabaseField(columnName = ORIGINALNUM, dataType = DataType.INTEGER)
    private int originalNum;

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
