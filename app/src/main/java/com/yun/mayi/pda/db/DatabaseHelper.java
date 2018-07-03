package com.yun.mayi.pda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.yun.mayi.pda.application.YunmayiApplication;
import com.yun.mayi.pda.bean.TallyingInfoDetail;

import java.sql.SQLException;

/**
 * Created by smile on 2017/3/8.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 8;

    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, "temp", null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (DatabaseHelper.instance == null) {
            YunmayiApplication application = (YunmayiApplication) context.getApplicationContext();
            DatabaseHelper.instance = new DatabaseHelper(application);
        }
        return DatabaseHelper.instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        database.beginTransaction();
        try {
            TableUtils.createTable(connectionSource, OrderInfo.class);
            TableUtils.createTable(connectionSource, BoxInfo.class);
            TableUtils.createTable(connectionSource, TallyingInfoDetail.class);
            database.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                try {
                    TableUtils.createTable(connectionSource, OrderInfo.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    TableUtils.createTable(connectionSource, BoxInfo.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                database.execSQL("ALTER TABLE order_info ADD COLUMN vendorId varchar");
                break;
            case 3:
                database.execSQL("ALTER TABLE order_info ADD COLUMN productPrice varchar");
                break;
            case 4:
                database.execSQL("ALTER TABLE order_info ADD COLUMN productMiddleCode varchar");
                database.execSQL("ALTER TABLE order_info ADD COLUMN productBoxCode varchar");
                break;
            case 5:
                database.execSQL("ALTER TABLE box_info ADD COLUMN productPrice varchar");
                break;
            case 6:
                database.execSQL("ALTER TABLE box_info ADD COLUMN boxNum varchar");
                break;
            case 7:
                try {
                    TableUtils.createTable(connectionSource, TallyingInfoDetail.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

   
}
