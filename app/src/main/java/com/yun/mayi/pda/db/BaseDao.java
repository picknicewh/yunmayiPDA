package com.yun.mayi.pda.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;

/**
 * Created by smile on 2017/3/8.
 */
public abstract class BaseDao<T, ID> {

    protected DatabaseHelper mHelper;
    protected Dao<T, ID> mDao;
    protected Class<T> mEntityClazz;

    public BaseDao(Context context) {
        mHelper = DatabaseHelper.getInstance(context);
        mEntityClazz = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            mDao = mHelper.getDao(mEntityClazz);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T get(ID id) {
        try {
            return mDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean save(T entity) {
        try {
           int c= mDao.create(entity);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public boolean update(T entity) {
        try {
            mDao.update(entity);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(T entity) {
        try {
            mDao.delete(entity);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
