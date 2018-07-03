package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/1/13
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class OrderDetailResult {
    /**
     * 总记录数
     */
    private int count;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     *缺货数量
     */
    private  int outOfStockNum;
    /**
     * 是否有下一页
     */
    private boolean hasNext;
    /**
     * 订单明细列表
     */
    private List<OrderDetail> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<OrderDetail> getList() {
        return list;
    }

    public void setList(List<OrderDetail> list) {
        this.list = list;
    }

    public int getOutOfStockNum() {
        return outOfStockNum;
    }

    public void setOutOfStockNum(int outOfStockNum) {
        this.outOfStockNum = outOfStockNum;
    }
}
