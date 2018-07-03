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
public class PackNoteInfoResult {

    /**
     * skuNum : 2
     * totalNum : 10
     * data : []
     */

    private int skuNum;
    private int totalNum;
    private List<PackNoteInfo> data;

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

    public List<PackNoteInfo> getData() {
        return data;
    }

    public void setData(List<PackNoteInfo> data) {
        this.data = data;
    }
}
