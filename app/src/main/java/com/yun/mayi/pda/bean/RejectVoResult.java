package com.yun.mayi.pda.bean;

import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/3/17
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class RejectVoResult {

    /**
     * count : 3
     * hasNext : false
     * totalPage : 1
     * data : []
     */

    private int count;
    private boolean hasNext;
    private int totalPage;
    private List<RejectVo> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<RejectVo> getData() {
        return data;
    }

    public void setData(List<RejectVo> data) {
        this.data = data;
    }
}
