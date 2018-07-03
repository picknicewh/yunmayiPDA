package com.yun.mayi.pda.bean;

/**
 * 作者： wh
 * 时间：  2018/1/8
 * 名称：消息信息
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class MessageInfo {
    /**
     * id
     */
    private int id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 时间
     */
    private String time;
    /**
     * 是否被删除（标记）
     */
    private int is_delete;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(int is_delete) {
        this.is_delete = is_delete;
    }
}
