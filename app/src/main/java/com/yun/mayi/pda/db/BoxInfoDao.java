package com.yun.mayi.pda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.yun.mayi.pda.utils.G;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/4/3
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class BoxInfoDao extends BaseDao<BoxInfo,String> {
    public BoxInfoDao(Context context) {
        super(context);
    }
    /**
     * 删除全部的数据
     */
    public void deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from box_info";
        db.execSQL(sql);
    }
    /**
     * 获取全部待分拣的数据
     */
    public List<BoxInfo> getUnPackBoxInfoList(String orderId, String barCode) {
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            Where<BoxInfo, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(OrderInfo.PACKED, 0)
                        .and().eq(OrderInfo.ORDERID, orderId);
                G.log("--BoxInfo-y" + where.query().size());
            } else {
                where.and(where.and(where.eq(BoxInfo.PACKED, 0), where.eq(BoxInfo.ORDERID, orderId)),
                        where.or(where.eq(BoxInfo.BOXID, barCode),
                                where.eq(BoxInfo.PRODUCETBARCODE,  barCode),
                                where.like(BoxInfo.PRODUCTTITLE, "%" + barCode + "%")));
                int s = where.query().size();
                G.log("--BoxInfo-x" + where.query().size());
            }

            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取全部已分拣的数据
     */
    public List<BoxInfo> getPackOrderInfoList(String orderId, String barCode) {
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            Where<BoxInfo, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(BoxInfo.PACKED, 1).and()
                        .eq(BoxInfo.ORDERID, orderId);
            } else {
                where.and(where.and(where.eq(BoxInfo.PACKED, 1),
                        where.eq(BoxInfo.ORDERID, orderId)),
                        where.or(where.eq(BoxInfo.BOXID, barCode),
                                where.eq(BoxInfo.PRODUCETBARCODE, barCode ),
                                where.like(BoxInfo.PRODUCTTITLE, "%" + barCode + "%")));
            }
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 根据boxId获取装箱信息列表的数据
     * @param  orderId 订单id
     *@param  boxId 箱号
     *@param barCode 关键字
     */
    public List<BoxInfo> getBoxInfoListByBoxId(String orderId,String boxId, String barCode) {
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            Where<BoxInfo, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(BoxInfo.PACKED, 0).and()
                        .eq(BoxInfo.ORDERID, orderId)
                        .and().eq(BoxInfo.BOXID,boxId);
            } else {
                where.and(where.and(where.eq(BoxInfo.PACKED, 0),where.eq(BoxInfo.BOXID,boxId),
                        where.eq(BoxInfo.ORDERID, orderId)),
                        where.or(where.eq(BoxInfo.BOXID, barCode),
                                where.eq(BoxInfo.PRODUCETBARCODE, barCode ),
                                where.like(BoxInfo.PRODUCTTITLE, "%" + barCode + "%")));
            }
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取全部已分拣的数据
     */
    public List<BoxInfo> getAllPackOrderInfoList(String orderId) {
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            Where<BoxInfo, String> where = qb.where();
            where.eq(BoxInfo.ORDERID, orderId);
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取全部待分拣的数据
     */
    public int  getAllPackOrderCount(String orderId, String barCode) {
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            Where<BoxInfo, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(OrderInfo.ORDERID, orderId);
                G.log("--:xxxxxxxx:x" + where.query().size());
            } else {
                where.and(where.eq(OrderInfo.ORDERID, orderId),
                        where.or(where.eq(OrderInfo.PRODUCETBARCODE, barCode),
                                where.like(BoxInfo.PRODUCETBARCODE, "%" + barCode + "%"),
                                where.like(BoxInfo.PRODUCETBARCODE, "%" + barCode + "%"),
                                where.like(OrderInfo.PRODUCTTITLE, "%" + barCode + "%")));
                G.log("--:xxxxxxxx:y" + where.query().size());
            }
            int s = where.query().size();
            return s;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 获取全部的数据个数
     */
    public int getPackedNum(String orderId) {
        List<String> boxIdList = new ArrayList<>();
        List<BoxInfo> boxInfoList = getAllPackOrderInfoList(orderId);
        int num=0;
        for (int i = 0;i<boxInfoList.size();i++){
            BoxInfo boxInfo = boxInfoList.get(i);
            if (!boxIdList.contains(boxInfo.getBoxId())){
                boxIdList.add(boxInfo.getBoxId());
                num = num+boxInfo.getBoxNum();
            }
        }
        return num;
    }
    /**
     * 获取全部已分拣的数据件数
     */
    public int getPackedCount(String orderId) {
        int hasPackNum = 0;
        List<BoxInfo> boxInfoList = getAllPackOrderInfoList(orderId);
        for (int i = 0;i<boxInfoList.size();i++){
            BoxInfo boxInfo = boxInfoList.get(i);
            hasPackNum = hasPackNum+boxInfo.getNum();
        }
        return hasPackNum;
    }
    /**
     * 获取全部未分拣的数据个数
     */
    public int getUnPackedNum(String orderId) {
        int hasPackNum = 0;
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(BoxInfo.PACKED, 0).and()
                    .eq(BoxInfo.ORDERID, orderId);
            hasPackNum = qb.query().size();
            return hasPackNum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasPackNum;
    }
    /**
     * 是否包含id
     *
     * @param id
     */
    public boolean isInclude(String id, String orderId) {
        boolean isInclude = false;
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(BoxInfo.ID, id)
                    .and()
                    .eq(BoxInfo.ORDERID, orderId);
            isInclude = qb.query().size() > 0;
            G.log("-xxxxxboxInfo" + isInclude);
            return isInclude;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInclude;
    }
    /**
     * 是否包含boxId
     *
     * @param boxId
     */
    public boolean isIncludeBoxId(String boxId, String orderId) {
        boolean isInclude = false;
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(BoxInfo.BOXID, boxId)
                    .and()
                    .eq(BoxInfo.ORDERID, orderId);
            isInclude = qb.query().size() > 0;
            G.log("-xxxxxboxInfo" + isInclude);
            return isInclude;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInclude;
    }
    /**
     * 是否包含已经打包
     *
     * @param boxId
     */
    public boolean isPack(String boxId, String orderId) {
         boolean  isPack = false;
         try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(BoxInfo.BOXID, boxId)
                    .and()
                    .eq(BoxInfo.ORDERID, orderId);
            if (qb.query().size()>0){
              isPack =   qb.query().get(0).getPacked()==1;
            }
            G.log("-xxxxxboxInfo" + isPack);
            return isPack;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isPack;
    }
    /**
     * 入箱
     *
     * @param boxId 商品id
     */
    public int packedBox(String orderId, String boxId, int packed) {
        int update = -1;
        try {
            UpdateBuilder<BoxInfo, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(BoxInfo.PACKED, packed)
                    .where().
                    eq(BoxInfo.BOXID, boxId).and().
                    eq(BoxInfo.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return update;
    }
    /**
     * 修改购买数量
     *
     * @param id 商品id
     */
    public int updatePackNum(String orderId, String id, int num) {
        int update = -1;
        try {
            UpdateBuilder<BoxInfo, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(BoxInfo.NUM, num)
                    .where().
                    eq(BoxInfo.ID, id).and()
                    .eq(BoxInfo.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }
    /**
     * 获取当前购买数量
     *
     * @param id 商品id
     */
    public int getPackNum(String orderId, String id) {
        int packNum = -1;
        try {
            QueryBuilder<BoxInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(BoxInfo.ORDERID, orderId).and()
                    .eq(BoxInfo.ID, id);
            packNum = qb.query().size();
            if (packNum==1){
                packNum  = qb.query().get(0).getNum();
            }
            G.log("xxxxxxxx"+packNum);
            return packNum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packNum;
    }
    /**
     * 分拣缺货操作
     * 修改是否分拣状态为已分拣
     * 修改数量为0
     *
     * @param id 商品id
     */
    public int updateLess(String orderId, String id) {
        int update = -1;
        try {
            UpdateBuilder<BoxInfo, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(BoxInfo.PACKED, 1)
                    .updateColumnValue(BoxInfo.NUM, 0)
                    .where().
                    eq(BoxInfo.ID, id)
                    .and().eq(BoxInfo.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }


    /**
     * 装车缺货操作
     * 修改是否分拣状态为已分拣
     * 修改数量为0
     *
     * @param boxId 箱号
     */
    public int updateLessBox(String orderId, String boxId) {
        int update = -1;
        try {
            UpdateBuilder<BoxInfo, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(BoxInfo.PACKED, 1)
                    .updateColumnValue(BoxInfo.NUM, 0)
                    .where().
                    eq(BoxInfo.BOXID, boxId)
                    .and().eq(BoxInfo.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }
    /**
     * 删除所有打包的数据
     */
    public int deleteAllPacked(int packed) {
        int delete = -1;
        try {
            DeleteBuilder<BoxInfo, String> deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq(BoxInfo.PACKED, packed);
            delete = deleteBuilder.delete();
            return delete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delete;
    }
    /**
     * 删除所有打包的数据
     */
    public int deleteAllPacked() {
        int delete = -1;
        try {
            DeleteBuilder<BoxInfo, String> deleteBuilder = mDao.deleteBuilder();
            delete = deleteBuilder.delete();
            return delete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delete;
    }

    /**
     * 删除本地数据
     *
     * @param id 订单id
     */
    public int deleteOrderById(String orderId, String id) {
        int delete = -1;
        try {
            DeleteBuilder<BoxInfo, String> deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq(BoxInfo.ID, id).and()
                    .eq(BoxInfo.ORDERID, orderId);
            delete = deleteBuilder.delete();
            return delete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delete;
    }
}
