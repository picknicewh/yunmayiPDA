package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/1/12
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PackBoxResult {
    /**
     * 总记录数
     */
    private int count;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 是否有下一页
     */
    private boolean hasNext;
    /**
     *订单数
     */
    private int orderNum;
    private int boxNum;
    /**
     * 装箱列表
     */
    private List<PackBox> list;

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

    public List<PackBox> getList() {
        return list;
    }

    public void setList(List<PackBox> list) {
        this.list = list;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }
}
