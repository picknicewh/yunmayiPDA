package com.yun.mayi.pda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yun.mayi.pda.application.YunmayiApplication;

/**
 * ================================================
 * 作    者：wh
 * 时    间：2018/01/08
 * 描    述：关键字数据库
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class KeyWordDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db"; //数据库名称
    private static final String TABLE_KEYWORD = "keyword_info";//表名

    private static KeyWordDbHelper dbKeyWordHelper;
    public static KeyWordDbHelper getInstance(){
        if (dbKeyWordHelper==null){
            dbKeyWordHelper = new KeyWordDbHelper(YunmayiApplication.getInstance().getApplicationContext());
        }
        return dbKeyWordHelper;
    }
    public KeyWordDbHelper(Context context) {
        super(context, DB_NAME, null, 117);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_message="create table " +TABLE_KEYWORD+
                "(id integer primary key autoincrement," +
                 "info varchar(50) not null)";
        db.execSQL(create_message);
    }

    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String delete_keyword = "drop table if exists " + TABLE_KEYWORD;
        db.execSQL(delete_keyword);
        this.onCreate(db);
    }
}
