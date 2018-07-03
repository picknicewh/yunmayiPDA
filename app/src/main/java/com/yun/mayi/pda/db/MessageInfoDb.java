package com.yun.mayi.pda.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.table.DatabaseTable;
import com.yun.mayi.pda.bean.MessageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： wh
 * 时间： 2016/8/31
 * 名称：角色信息操作类（增删改查）
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */

public class MessageInfoDb {

    private static final String dbname = "message";

    /**
     * 插入数据
     *
     * @param db        数据库
     * @param id        权限角色
     * @param title     标题
     * @param content   内容
     * @param time      时间
     * @param is_delete 是否删除
     */
    public static void insert(SQLiteDatabase db, int id, String title, String content, String time, int is_delete) {
        String sql = "insert into  " + dbname + "(id,title,content,time,action,is_delete) values" +
                " (" + id + ",'" + title + "','" + content + "','" + time + "'," + is_delete + ")";
        db.execSQL(sql);
    }

    /**
     * 插入s数据列表
     *
     * @param messageInfo 消息信息
     * @param db          数据库
     */
    public static void insertMessage(MessageInfo messageInfo, SQLiteDatabase db) {
        if (!isEmpty(db)) {
            delete(db);
        }
        insert(db, messageInfo.getId(), messageInfo.getTitle(), messageInfo.getContent(), messageInfo.getTime(), messageInfo.getIs_delete());
    }


    /**
     * 查询所有数据数据,获取列表信息
     *
     * @param db 数据库
     */
    public static List<MessageInfo> getUserRuleInfoList(SQLiteDatabase db) {
        List<MessageInfo> messageInfoList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from  " + dbname, null);
        while (cursor.moveToNext()) {
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.setId(cursor.getInt(cursor.getColumnIndex("id")));
            messageInfo.setTime(cursor.getString(cursor.getColumnIndex("time")));
            messageInfo.setContent(cursor.getString(cursor.getColumnIndex("content")));
            messageInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            messageInfoList.add(messageInfo);
        }
        cursor.close();
        return messageInfoList;
    }

    /**
     * 判断是否数据是否为空
     *
     * @param db 数据库
     */
    public static boolean isEmpty(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("select * from " + dbname, null);
        while (cursor.moveToNext()) {
            return false;
        }
        return true;
    }

    /**
     * 删除通过id号删除单个系统消息
     *
     * @param db 数据库
     * @param id 唯一用户id号
     */
    public static void deleteByTsId(SQLiteDatabase db, int id) {
        String sql = "delete from " + dbname + " where uid =" + id;
        db.execSQL(sql);
    }

    /**
     * 删除所有
     *
     * @param db 数据库
     */
    public static void delete(SQLiteDatabase db) {
        String sql = "delete from  " + dbname;
        db.execSQL(sql);

    }

}

