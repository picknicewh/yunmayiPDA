package com.yun.mayi.pda.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： wh
 * 时间： 2018/4/24
 * 名称：关键字操作类（增删改查）
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */

public class KeyWordInfoDb {

    private static final String dbname = "keyword_info";

    /**
     * 插入数据
     *
     * @param db        数据库
     * @param info     信息
     */
    public static void insert(SQLiteDatabase db, String info) {
        String sql = "insert into  " + dbname + "(info) values" + "('" + info + "')";
        db.execSQL(sql);
    }

    /**
     * 插入数据
     *
     * @param db        数据库
     */
    public static void insertAll(SQLiteDatabase db,List<String> infoList) {
        delete(db);//删除全部数据，再添加
        for (int i = 0;i<infoList.size();i++){
            insert(db,infoList.get(i));
        }

    }

    /**
     * 查询所有数据数据,获取列表信息
     * @param db 数据库
     */
    public static List<String> getInfoList(SQLiteDatabase db,String keyword) {
        List<String> infoList = new ArrayList<>();
        String sql  = "select  info  from " + dbname + " where info  like " + "'%"+keyword+"%'";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            infoList.add(cursor.getString(cursor.getColumnIndex("info")));
        }
        cursor.close();
        return infoList;
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
     * 删除所有
     *
     * @param db 数据库
     */
    public static void delete(SQLiteDatabase db) {
        String sql = "delete from  " + dbname;
        db.execSQL(sql);

    }

}

