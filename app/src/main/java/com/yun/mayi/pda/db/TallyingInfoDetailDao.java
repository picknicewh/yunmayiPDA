package com.yun.mayi.pda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.yun.mayi.pda.bean.TallyingInfoDetail;
import com.yun.mayi.pda.utils.G;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/5/28
 * 名称：司机点货数据库
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class TallyingInfoDetailDao extends BaseDao<TallyingInfoDetail,String> {
    public TallyingInfoDetailDao(Context context) {
        super(context);
    }
    /**
     * 删除全部的数据
     */
    public void deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete from tallying_info";
        db.execSQL(sql);
    }
    /**
     * 获取全部未点货的数据
     */
    public List<TallyingInfoDetail> getUnDrawerPickInfoDetailList(String orderId, String barCode) {
        try {
            QueryBuilder<TallyingInfoDetail, String> qb = mDao.queryBuilder();
            Where<TallyingInfoDetail, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(TallyingInfoDetail.PACKED, 0)
                        .and().eq(TallyingInfoDetail.ORDERID, orderId)
                        .and().gt(TallyingInfoDetail.LOADINGQUANTITY,0);
                G.log("--BoxInfo-y" + where.query().size());
            } else {
                where.and(where.and(where.eq(TallyingInfoDetail.PACKED, 0),
                        where.eq(BoxInfo.ORDERID, orderId)),
                        where.gt(TallyingInfoDetail.LOADINGQUANTITY,0),
                        where.or(where.eq(TallyingInfoDetail.BOXID, barCode),
                                where.eq(TallyingInfoDetail.PRODUCETBARCODE,  barCode),
                                where.like(TallyingInfoDetail.PRODUCTTITLE, "%" + barCode + "%")));
                int s = where.query().size();
                G.log("--BoxInfo-x" + where.query().size());
                G.log("--BoxInfo-x" + barCode);
            }

            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取全部已点货的数据
     */
    public List<TallyingInfoDetail> getDrawerPickInfoDetailList(String orderId, String barCode) {
        try {
            QueryBuilder<TallyingInfoDetail, String> qb = mDao.queryBuilder();
            Where<TallyingInfoDetail, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(TallyingInfoDetail.PACKED, 1).and()
                        .eq(TallyingInfoDetail.ORDERID, orderId);
            } else {
                where.and(where.and(where.eq(TallyingInfoDetail.PACKED, 1),
                        where.eq(TallyingInfoDetail.ORDERID, orderId)),
                        where.or(where.eq(TallyingInfoDetail.BOXID, barCode),
                                where.eq(TallyingInfoDetail.PRODUCETBARCODE, barCode ),
                                where.like(TallyingInfoDetail.PRODUCTTITLE, "%" + barCode + "%")));
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
    public List<TallyingInfoDetail> getAllPackOrderInfoList(String orderId) {
        try {
            QueryBuilder<TallyingInfoDetail, String> qb = mDao.queryBuilder();
            Where<TallyingInfoDetail, String> where = qb.where();
            where.eq(TallyingInfoDetail.ORDERID, orderId);
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
            QueryBuilder<TallyingInfoDetail, String> qb = mDao.queryBuilder();
            Where<TallyingInfoDetail, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(TallyingInfoDetail.ORDERID, orderId);
                G.log("--:xxxxxxxx:x" + where.query().size());
            } else {
                where.and(where.eq(OrderInfo.ORDERID, orderId), where.eq(TallyingInfoDetail.BOXID, barCode),
                        where.or(where.eq(TallyingInfoDetail.PRODUCETBARCODE, barCode),
                                where.like(TallyingInfoDetail.PRODUCETBOXCODE, "%" + barCode + "%"),
                                where.like(TallyingInfoDetail.PRODUCETMIDDLECODE, "%" + barCode + "%"),
                                where.like(TallyingInfoDetail.PRODUCTTITLE, "%" + barCode + "%")));
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
     * 获取订单箱数
     * @param orderId 订单
     */
    public int getBoxNum(String orderId) {
        List<String> boxIdList = new ArrayList<>();
        List<TallyingInfoDetail> boxInfoList = getAllPackOrderInfoList(orderId);
        int num=0;
        for (int i = 0;i<boxInfoList.size();i++){
            TallyingInfoDetail boxInfo = boxInfoList.get(i);
            if (!boxIdList.contains(boxInfo.getBoxId()) &&! boxInfo.getBoxId().equals("0")){
                boxIdList.add(boxInfo.getBoxId());
                num++;
            }
        }
        return num;
    }

    /**
     * 获取全部点货数据件数
     */
    public int getTotalCount(String orderId) {
        int hasPackNum = 0;
        List<TallyingInfoDetail> boxInfoList = getAllPackOrderInfoList(orderId);
        G.log("xssqswewewewe"+boxInfoList.size());
        for (int i = 0;i<boxInfoList.size();i++){
            TallyingInfoDetail boxInfo = boxInfoList.get(i);
            if (boxInfo.getBoxId().equals("0")){
                hasPackNum = hasPackNum+boxInfo.getLoadingQuantity();
                G.log("xssqswewewewe"+boxInfo.getBoxId());
            }
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
            QueryBuilder<TallyingInfoDetail, String> qb = mDao.queryBuilder();
            qb.where().eq(TallyingInfoDetail.ID, id)
                    .and()
                    .eq(TallyingInfoDetail.ORDERID, orderId);
            isInclude = qb.query().size() > 0;
            G.log("-xxxxxboxInfo" + isInclude);
            return isInclude;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInclude;
    }
    /**
     * 点货/出货
     *@param  orderId 订单id
     * @param id 子订单id
     * @param  packed 是否点货
     */
    public int packedBox(String orderId, String id, int packed) {
        int update = -1;
        try {
            UpdateBuilder<TallyingInfoDetail, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(BoxInfo.PACKED, packed)
                    .where().
                    eq(TallyingInfoDetail.ID, id).and().
                    eq(TallyingInfoDetail.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }
    /**
     * 修改装车数量数量
     *@param orderId 订单id
     * @param id 子订单id
     * @param  num 修改数量
     */
    public int updateLoadingNum(String orderId, String id, int num) {
        int update = -1;
        try {
            UpdateBuilder<TallyingInfoDetail, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(TallyingInfoDetail.LOADINGQUANTITY, num)
                    .where().
                    eq(TallyingInfoDetail.ID, id).and()
                    .eq(TallyingInfoDetail.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }
    /**
     * 修改配送数量购买数量
     *@param orderId 订单id
     * @param id 子订单id
     * @param  num 修改数量
     */
    public int updateDelieverNum(String orderId, String id, int num) {
        int update = -1;
        try {
            UpdateBuilder<TallyingInfoDetail, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(TallyingInfoDetail.DELIVERQUANTITY, num)
                    .where().
                    eq(TallyingInfoDetail.ID, id).and()
                    .eq(TallyingInfoDetail.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }

  /*  *//**
     * 修改购买数量
     *@param orderId 订单id
     * @param id 子订单id
     * @param  num 修改数量
     *//*
    public int updatePackNum(String orderId, String id, int num) {
        int update = -1;
        try {
            UpdateBuilder<TallyingInfoDetail, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(TallyingInfoDetail.LOADINGQUANTITY, num)
                    .where().
                    eq(TallyingInfoDetail.ID, id).and()
                    .eq(TallyingInfoDetail.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }*/
    /**
     * 获取当前购买数量
     *@param  orderId 订单id
     * @param id 商品id
     */
    public int getPackNum(String orderId, String id) {
        int packNum = -1;
        try {
            QueryBuilder<TallyingInfoDetail, String> qb = mDao.queryBuilder();
            qb.where().eq(TallyingInfoDetail.ORDERID, orderId).and()
                    .eq(TallyingInfoDetail.ID, id);
            packNum = qb.query().size();
            if (packNum==1){
                packNum  = qb.query().get(0).getLoadingQuantity();
            }
            G.log("xxxxxxxx"+packNum);
            return packNum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packNum;
    }
    /**
     * 缺货操作
     * 修改是否分拣状态为已分拣
     * 修改数量为0
     *
     * @param id 商品id
     */
    public int updateLess(String orderId, String id) {
        int update = -1;
        try {
            UpdateBuilder<TallyingInfoDetail, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(TallyingInfoDetail.PACKED, 1)
                    .updateColumnValue(TallyingInfoDetail.LOADINGQUANTITY, 0)
                    .where().
                    eq(TallyingInfoDetail.ID, id)
                    .and().eq(TallyingInfoDetail.ORDERID, orderId);
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
            DeleteBuilder<TallyingInfoDetail, String> deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq(TallyingInfoDetail.PACKED, packed);
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
            DeleteBuilder<TallyingInfoDetail, String> deleteBuilder = mDao.deleteBuilder();
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
            DeleteBuilder<TallyingInfoDetail, String> deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq(TallyingInfoDetail.ID, id).and()
                    .eq(TallyingInfoDetail.ORDERID, orderId);
            delete = deleteBuilder.delete();
            return delete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delete;
    }
}
