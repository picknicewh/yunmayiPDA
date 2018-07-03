package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/4/2
 * 名称：待分拣数据
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class PickOrderResult {
    /**
     * 订单总数
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
     * 待分拣列表
     */
    private List<PickOrder> list;

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

    public List<PickOrder> getList() {
        return list;
    }

    public void setList(List<PickOrder> list) {
        this.list = list;
    }
}
