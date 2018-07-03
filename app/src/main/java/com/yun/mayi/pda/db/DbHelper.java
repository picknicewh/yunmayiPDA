package com.yun.mayi.pda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yun.mayi.pda.application.YunmayiApplication;

/**
 * ================================================
 * 作    者：wh
 * 时    间：2018/01/08
 * 描    述：消息数据库
 * 版    本：
 * 修订历史：
 * 主要接口：
 * ================================================
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db"; //数据库名称
    private static final int version = 1; //数据库版本
    private static final String TABLE_MESSAGE = "message";//表名

    private static DbHelper messageInfoDbHelper;
    public static DbHelper getInstance(){
        if (messageInfoDbHelper==null){
            messageInfoDbHelper = new DbHelper(YunmayiApplication.getInstance().getApplicationContext());
        }
        return messageInfoDbHelper;
    }
    public DbHelper(Context context) {
        super(context, DB_NAME, null, 102);
    }

    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_message="create table " +TABLE_MESSAGE+
                "(id integer primary key autoincrement," +
                "title varchar(50) not null," +
                "content varchar(50) not null," +
                "time varchar(50) not null," +
                "is_delete integer)";

        db.execSQL(create_message);
    }

    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String delete_message = "drop table if exists " + TABLE_MESSAGE;
        db.execSQL(delete_message);
        this.onCreate(db);
    }
}
