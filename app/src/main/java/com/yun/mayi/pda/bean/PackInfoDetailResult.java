package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/4/9
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackInfoDetailResult {

    /**
     * skuNum : 1
     * totalNum : 3
     * stockOutSkuNum : 1
     * stockOutTotalNum : 2
     * data : []
     */
    private int skuNum;
    private int totalNum;
    private int stockOutSkuNum;
    private int stockOutTotalNum;
    private List<PackDetailInfo>  pack_list;
    private List<PackBoxDetail2>box_list;
    public int getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(int skuNum) {
        this.skuNum = skuNum;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getStockOutSkuNum() {
        return stockOutSkuNum;
    }

    public void setStockOutSkuNum(int stockOutSkuNum) {
        this.stockOutSkuNum = stockOutSkuNum;
    }

    public int getStockOutTotalNum() {
        return stockOutTotalNum;
    }

    public void setStockOutTotalNum(int stockOutTotalNum) {
        this.stockOutTotalNum = stockOutTotalNum;
    }

    public List<PackDetailInfo> getPack_list() {
        return pack_list;
    }

    public void setPack_list(List<PackDetailInfo> pack_list) {
        this.pack_list = pack_list;
    }

    public List<PackBoxDetail2> getBox_list() {
        return box_list;
    }

    public void setBox_list(List<PackBoxDetail2> box_list) {
        this.box_list = box_list;
    }
}
