package com.yun.mayi.pda.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.yun.mayi.pda.utils.G;

import java.sql.SQLException;
import java.util.List;

/**
 * 作者： wh
 * 时间：  2018/1/9
 * 名称：拣货详情列表
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class OrderInfoDao extends BaseDao<OrderInfo, String> {
    public OrderInfoDao(Context context) {
        super(context);
    }

    /**
     * 删除全部的数据
     */
    public void deleteAll() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = "delete  from order_info";
        db.execSQL(sql);
    }

    /**
     * 获取全部待分拣的数据
     * 供应商待拣货单
     *
     * @param orderId  订单id
     * @param vendorId 供应商id
     * @param barCode  关键字
     */
    public List<OrderInfo> getUnPackOrderInfoList(String orderId, String vendorId, String barCode) {
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            Where<OrderInfo, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(OrderInfo.PACKED, 0)
                        .and().eq(OrderInfo.ORDERID, orderId)
                        .and().eq(OrderInfo.VENDORID, vendorId);
                G.log("--:xxxxxxxx:x" + where.query().size());
            } else {
                where.and(where.and(where.eq(OrderInfo.PACKED, 0),
                        where.eq(OrderInfo.ORDERID, orderId),
                        where.eq(OrderInfo.VENDORID, vendorId)),
                        where.or(where.eq(OrderInfo.PRODUCETBARCODE, barCode),

                                where.like(OrderInfo.PRODUCTTITLE, "%" + barCode + "%")));
                int s = where.query().size();
                G.log("--:xxxxxxxx:y" + where.query().size());
            }
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取全部已分拣的数据
     * 供应商待拣货单
     *
     * @param orderId  订单id
     * @param vendorId 供应商id
     * @param barCode  关键字
     */
    public List<OrderInfo> getPackOrderInfoList(String orderId, String vendorId, String barCode) {
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            Where<OrderInfo, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(OrderInfo.PACKED, 1)
                        .and().eq(OrderInfo.ORDERID, orderId)
                        .and().eq(OrderInfo.VENDORID, vendorId);
                G.log("--:xxxxxxxx:x" + where.query().size());
            } else {
                where.and(where.and(where.eq(OrderInfo.PACKED, 1),
                        where.eq(OrderInfo.ORDERID, orderId),
                        where.eq(OrderInfo.VENDORID, vendorId)),
                        where.or(where.eq(OrderInfo.PRODUCETBARCODE, barCode),
                                where.like(OrderInfo.PRODUCTTITLE, "%" + barCode + "%")));
                int s = where.query().size();
                G.log("--:xxxxxxxx:y" + where.query().size());
            }
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取全部待分拣的数据
     * 供应商使用选择所有选中的打包的商品
     *
     * @param orderId 订单id
     * @param barCode 关键字
     */
    public List<OrderInfo> getPackCheckedOrderInfoList(String orderId, String barCode) {
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            if (G.isEmteny(barCode)) {
                qb.where().eq(OrderInfo.PACKED, 1).
                        and().eq(OrderInfo.ISCHECKED, 1)
                        .and().eq(OrderInfo.ORDERID, orderId);
            } else {
                qb.where().eq(OrderInfo.PACKED, 1).
                        and().eq(OrderInfo.ISCHECKED, 1)
                        .and().eq(OrderInfo.ORDERID, orderId)
                        .and().eq(OrderInfo.PRODUCETBARCODE, barCode);
            }
            return qb.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 供应商使用
     * 获取全部未分拣的数据个数
     *
     * @param orderId
     */
    public int getUnPackedNum(String orderId) {
        int hasPackNum = 0;
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(OrderInfo.PACKED, 0).and()
                    .eq(OrderInfo.ISCHECKED, 0).and()
                    .eq(OrderInfo.ORDERID, orderId);
            hasPackNum = qb.query().size();
            return hasPackNum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasPackNum;
    }

    /**
     * 供应商使用
     * 获取全部已分拣的数据个数
     *
     * @param orderId 订单id
     */
    public int getPackedNum(String orderId) {
        int hasPackNum = 0;
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(OrderInfo.PACKED, 1).and()
                    .eq(OrderInfo.ISCHECKED, 1).and()
                    .eq(OrderInfo.ORDERID, orderId);
            hasPackNum = qb.query().size();
            return hasPackNum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasPackNum;
    }

    /**
     * 加盟商获取全部待分拣的数据
     * 加盟商使用
     * @param orderId 订单id
     */
    public List<OrderInfo> getAllPackOrderInfoList(String orderId) {
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            Where<OrderInfo, String> where = qb.where();
            where.eq(OrderInfo.ORDERID, orderId);
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加盟商使用
     * 获取全部已分拣的数据件数
     * @param orderId 订单id
     */
    public int getPackedCount(String orderId) {
        int hasPackNum = 0;
        List<OrderInfo> orderInfoList = getAllPackOrderInfoList(orderId);
        for (int i = 0; i < orderInfoList.size(); i++) {
            OrderInfo orderInfo = orderInfoList.get(i);
            hasPackNum = hasPackNum + orderInfo.getNum();
        }
        return hasPackNum;
    }

    /**
     * 获取全部待分拣的数据
     * 加盟待检货单
     * @param orderId 订单id
     * @param barCode 关键字
     */
    public List<OrderInfo> getUnPackOrderInfoList(String orderId, String barCode) {
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            Where<OrderInfo, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(OrderInfo.PACKED, 0)
                        .and().eq(OrderInfo.ORDERID, orderId);
            } else {
                where.and(where.and(where.eq(OrderInfo.PACKED, 0),
                        where.eq(OrderInfo.ORDERID, orderId))
                        ,where.or(where.eq(OrderInfo.PRODUCETBARCODE, barCode),
                                where.eq(OrderInfo.PRODUCETMIDDLECODE, barCode),
                                where.eq(OrderInfo.PRODUCETBOXCODE, barCode),
                                where.like(OrderInfo.PRODUCTTITLE, "%" + barCode + "%")));
            }
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取全部已分拣的数据
     * 加盟商待拣货单
     *
     * @param orderId 订单id
     * @param barCode 关键字
     */
    public List<OrderInfo> getPackOrderInfoList(String orderId, String barCode) {
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            Where<OrderInfo, String> where = qb.where();
            if (G.isEmteny(barCode)) {
                where.eq(OrderInfo.PACKED, 1)
                        .and().eq(OrderInfo.ORDERID, orderId);
                G.log("--:xxxxxxxx:x" + where.query().size());
            } else {
                where.and(where.and(where.eq(OrderInfo.PACKED, 1), where.eq(OrderInfo.ORDERID, orderId)),
                        where.or(where.eq(OrderInfo.PRODUCETBARCODE, barCode),
                                where.eq(OrderInfo.PRODUCETMIDDLECODE, barCode),
                                where.eq(OrderInfo.PRODUCETBOXCODE, barCode),
                                where.like(OrderInfo.PRODUCTTITLE, "%" + barCode + "%")));
                G.log("--:xxxxxxxx:y" + where.query().size());
            }
            return where.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 是否包含id
     * 加盟商使用

     * @param orderId 订单id
     * @param id
     */
    public boolean isInclude(String id, String orderId) {
        boolean isInclude = false;
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(OrderInfo.ID, id)
                    .and()
                    .eq(OrderInfo.ORDERID, orderId);
            isInclude = qb.query().size() > 0;
            G.log("-xxxxx" + isInclude);
            return isInclude;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isInclude;
    }
    /**
     * 是否包含已经如下入箱
     *
     * @param boxId
     */
    public boolean isPack(String boxId, String orderId) {
        boolean  isPack = false;
        try {
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(OrderInfo.ID, boxId)
                    .and()
                    .eq(OrderInfo.ORDERID, orderId);
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
     * @param id 商品id
     */
    public int packedBox(String orderId, String id, int packed) {
        int update = -1;
        try {
            UpdateBuilder<OrderInfo, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(OrderInfo.PACKED, packed)
                    .where().
                    eq(OrderInfo.ID, id).and().eq(OrderInfo.ORDERID, orderId);
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
            UpdateBuilder<OrderInfo, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(OrderInfo.NUM, num)
                    .where().
                    eq(OrderInfo.ID, id).and()
                    .eq(OrderInfo.ORDERID, orderId);
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
            QueryBuilder<OrderInfo, String> qb = mDao.queryBuilder();
            qb.where().eq(OrderInfo.ORDERID, orderId).and()
                    .eq(OrderInfo.ID, id);
            packNum = qb.query().size();
            if (packNum == 1) {
                packNum = qb.query().get(0).getNum();
            }
            G.log("xxxxxxxx" + packNum);
            return packNum;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packNum;
    }

    /**
     * 修改是否选中
     *
     * @param id 商品id
     */
    public int updateCheckNum(String orderId, String id, int isCheck) {
        int update = -1;
        try {
            UpdateBuilder<OrderInfo, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(OrderInfo.ISCHECKED, isCheck)
                    .where().
                    eq(OrderInfo.ID, id).and()
                    .eq(OrderInfo.ORDERID, orderId);
            update = ub.update();
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return update;
    }

    /**
     * 全缺操作
     * 修改是否分拣状态为已分拣
     * 修改数量为0
     * @param id 商品id
     */
    public int updateLess(String orderId, String id) {
        int update = -1;
        try {
            UpdateBuilder<OrderInfo, String> ub = mDao.updateBuilder();
            ub.updateColumnValue(OrderInfo.PACKED, 1)
                    .updateColumnValue(OrderInfo.NUM, 0)
                    .where().
                    eq(OrderInfo.ID, id)
                    .and().eq(OrderInfo.ORDERID, orderId);
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
            DeleteBuilder<OrderInfo, String> deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq(OrderInfo.PACKED, packed);
            delete = deleteBuilder.delete();
            return delete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delete;
    }
    /**
     * 删除所有选中的打包的数据
     */
    public int deleteAllCheckedPacked(int packed) {
        int delete = -1;
        try {
            DeleteBuilder<OrderInfo, String> deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq(OrderInfo.PACKED, packed).and().eq(OrderInfo.ISCHECKED,1);
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
            DeleteBuilder<OrderInfo, String> deleteBuilder = mDao.deleteBuilder();
            delete = deleteBuilder.delete();
            return delete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delete;
    }

    /**
     * 删除本地数据
     * @param id 订单id
     */
    public int deleteOrderById(String orderId, String id) {
        int delete = -1;
        try {
            DeleteBuilder<OrderInfo, String> deleteBuilder = mDao.deleteBuilder();
            deleteBuilder.where().eq(OrderInfo.ID, id).and()
                    .eq(OrderInfo.ORDERID, orderId);
            delete = deleteBuilder.delete();
            return delete;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return delete;
    }
}
