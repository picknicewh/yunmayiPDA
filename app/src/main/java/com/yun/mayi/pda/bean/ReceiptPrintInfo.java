package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/5/30
 * 名称：司机打印货单数据
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class ReceiptPrintInfo {

    /**
     * payType : 1
     * payPlatform : cod
     * createDatetime : 2018-04-10 22:51:24
     * buyerName : 糊涂斯基
     * buyerNumber : 571999998
     * buyerMobile : 18358119525
     * buyerAddress : 测试
     * originTotalSellPrice : 6454.75
     * payTotalSellPrice : 6420.75
     * realPayTotalSellPrice : 5758.95
     * orderId : 1804102251256956
     * deliverymanId : 103156
     * deliverymanName : 潘俊
     * deliverymanMobile : 13516773222
     * salesmanId : 100669
     * salesmanName : 孟玉涛
     * salesmanMobile : 13333333333
     * agentCompany :
     * serviceMobile :
     * footSet : 服务承诺：针对日化／百货／零食／调味类商品，在您收到货24小时内因质量、缺货等问题，直接拨打客服热线400-069-2666反馈即可为您解决。酒水饮料／泡面您需要当场清点验收，原则上此类商品不受理事后退货缺货。
     * totalOutPrice : 661.8
     * detailList : [{"productTitle":"太太乐三鲜鸡精100g$","quantity":18,"outOfStockNum":6,"productSellPrice":390,"outPrice":2340},{"productTitle":"XT 雪涛精制海盐 350g*50包","quantity":4,"outOfStockNum":2,"productSellPrice":8000,"outPrice":16000},{"productTitle":"1.25L*12瓶可口可乐【连锁】","quantity":3,"outOfStockNum":1,"productSellPrice":5400,"outPrice":5400},{"productTitle":"600ml*24瓶百事可乐【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":5250,"outPrice":0},{"productTitle":"XT 蓝海星未加碘自然晶盐 300g*50包","quantity":2,"outOfStockNum":1,"productSellPrice":10200,"outPrice":10200},{"productTitle":"【无满减无月返】【特价】红牛维生素功能饮料250ml*24罐$【连锁】！","quantity":1,"outOfStockNum":0,"productSellPrice":11400,"outPrice":0},{"productTitle":"【特价】（纯悦）饮用水 550ml*12瓶（17年6月，不退换）","quantity":2,"outOfStockNum":1,"productSellPrice":500,"outPrice":500},{"productTitle":"BR36SJ清风原木150抽金装3层抽纸8850【连锁】","quantity":9,"outOfStockNum":0,"productSellPrice":890,"outPrice":0},{"productTitle":"YD 电动车自行车汽车轮胎篮球球类高压打气筒充气泵 DT-001【连锁】","quantity":2,"outOfStockNum":0,"productSellPrice":2680,"outPrice":0},{"productTitle":"YD dj【特价】捷艺 1600W超大功率电吹风机 0484【连锁】","quantity":2,"outOfStockNum":0,"productSellPrice":1960,"outPrice":0},{"productTitle":"YD 【特价】正宗王麻子DC-316型号不锈钢厨用切片刀熟菜切片菜刀 1把盒装 316","quantity":2,"outOfStockNum":0,"productSellPrice":1980,"outPrice":0},{"productTitle":"YD 天运精美竹制花型桌垫 隔热垫 餐盘垫 2086（K三1）","quantity":5,"outOfStockNum":0,"productSellPrice":180,"outPrice":0},{"productTitle":"YD 高级塑料衣架 5只装 恒信 1031【连锁】","quantity":5,"outOfStockNum":2,"productSellPrice":725,"outPrice":1450},{"productTitle":"\u203b 1盒/30板 7号南孚电池 碱性电池 聚能环高性能 AAA小电池【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":11300,"outPrice":0},{"productTitle":"ls乐事大波浪薯片香脆烤鸡翅味 70g","quantity":100,"outOfStockNum":7,"productSellPrice":470,"outPrice":3290},{"productTitle":"【清仓】飘柔植物精选清凉舒爽薄荷洗发露380ml 2857","quantity":3,"outOfStockNum":2,"productSellPrice":1800,"outPrice":3600},{"productTitle":"【特价】蓝月亮亮白增艳薰衣草洗衣液500g袋装*12袋 7334【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":7980,"outPrice":0},{"productTitle":"【清仓】沙宣洗发露 （炫亮彩护）200ml 0092","quantity":3,"outOfStockNum":2,"productSellPrice":1800,"outPrice":3600},{"productTitle":"【尾品特卖】750ml*6瓶凯斯特进口原酒鹦鹉葡萄酒白箱（生产日期14年12月）","quantity":1,"outOfStockNum":0,"productSellPrice":48000,"outPrice":0},{"productTitle":"【尾品特卖】500ml*6瓶52°杜康花瓷五星精品（生产日期13年1月）","quantity":1,"outOfStockNum":0,"productSellPrice":135000,"outPrice":0},{"productTitle":"《农夫》农夫山泉天然饮用水 1.5L*12瓶【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":2700,"outPrice":0},{"productTitle":"农夫山泉东方树叶茉莉花茶饮料 480ml*24瓶【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":8800,"outPrice":0},{"productTitle":"《农夫》农夫山泉东方树叶原味乌龙茶饮料 500ml*24瓶【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":8800,"outPrice":0},{"productTitle":"《农夫》农夫山泉水溶C100青皮桔味 445ml*15瓶【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":5500,"outPrice":0},{"productTitle":"《农夫》农夫山泉水溶C100西柚味 445ml*15瓶【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":5500,"outPrice":0},{"productTitle":"《农夫》农夫果园（芒果+菠萝+番石榴+苹果+番茄) 500ml*15瓶【连锁】","quantity":1,"outOfStockNum":0,"productSellPrice":4500,"outPrice":0},{"productTitle":"娃哈哈纯净水（纸箱） 596ml*24瓶","quantity":100,"outOfStockNum":9,"productSellPrice":2200,"outPrice":19800},{"productTitle":"【无满减无月返】五常昌旺纯真100%稻花香 10kg$","quantity":1,"outOfStockNum":0,"productSellPrice":10550,"outPrice":0},{"productTitle":"【单品送】【无满减无月返】五常昌旺纯真100%稻花香 10kg$","quantity":1,"outOfStockNum":0,"productSellPrice":10550,"outPrice":0}]
     */

    private int payType;
    private String payPlatform;
    private String createDatetime;
    private String buyerName;
    private String buyerNumber;
    private String buyerMobile;
    private String buyerAddress;
    private String mark;
    private double originTotalSellPrice;
    private double payTotalSellPrice;
    private double realPayTotalSellPrice;
    private String orderId;
    private int deliverymanId;
    private String deliverymanName;
    private String deliverymanMobile;
    private int salesmanId;
    private String salesmanName;
    private String salesmanMobile;
    private String agentCompany;
    private String serviceMobile;
    private String footSet;
    private double totalOutPrice;
    private String buyerShopName;

    private List<DetailInfo> detailList;


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
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

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(String buyerNumber) {
        this.buyerNumber = buyerNumber;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
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

    public void setRealPayTotalSellPrice(double realPayTotalSellPrice) {
        this.realPayTotalSellPrice = realPayTotalSellPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getDeliverymanId() {
        return deliverymanId;
    }

    public void setDeliverymanId(int deliverymanId) {
        this.deliverymanId = deliverymanId;
    }

    public String getDeliverymanName() {
        return deliverymanName;
    }

    public void setDeliverymanName(String deliverymanName) {
        this.deliverymanName = deliverymanName;
    }

    public String getDeliverymanMobile() {
        return deliverymanMobile;
    }

    public void setDeliverymanMobile(String deliverymanMobile) {
        this.deliverymanMobile = deliverymanMobile;
    }

    public int getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(int salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanMobile() {
        return salesmanMobile;
    }

    public void setSalesmanMobile(String salesmanMobile) {
        this.salesmanMobile = salesmanMobile;
    }

    public String getAgentCompany() {
        return agentCompany;
    }

    public void setAgentCompany(String agentCompany) {
        this.agentCompany = agentCompany;
    }

    public String getServiceMobile() {
        return serviceMobile;
    }

    public void setServiceMobile(String serviceMobile) {
        this.serviceMobile = serviceMobile;
    }

    public String getFootSet() {
        return footSet;
    }

    public void setFootSet(String footSet) {
        this.footSet = footSet;
    }

    public double getTotalOutPrice() {
        return totalOutPrice;
    }

    public void setTotalOutPrice(double totalOutPrice) {
        this.totalOutPrice = totalOutPrice;
    }

    public String getBuyerShopName() {
        return buyerShopName;
    }

    public void setBuyerShopName(String buyerShopName) {
        this.buyerShopName = buyerShopName;
    }

    public List<DetailInfo> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DetailInfo> detailList) {
        this.detailList = detailList;
    }

    public static class DetailInfo {
        /**
         * productTitle : 太太乐三鲜鸡精100g$
         * quantity : 18
         * outOfStockNum : 6
         * productSellPrice : 390
         * outPrice : 2340
         */

        private String productTitle;
        private int quantity;
        private int outOfStockNum;
        private double productSellPrice;
        private double outPrice;

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getOutOfStockNum() {
            return outOfStockNum;
        }

        public void setOutOfStockNum(int outOfStockNum) {
            this.outOfStockNum = outOfStockNum;
        }

        public double getProductSellPrice() {
            return productSellPrice;
        }

        public void setProductSellPrice(double productSellPrice) {
            this.productSellPrice = productSellPrice;
        }

        public double getOutPrice() {
            return outPrice;
        }

        public void setOutPrice(int outPrice) {
            this.outPrice = outPrice;
        }
    }
}
