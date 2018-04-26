package cn.bidostar.ticserver.utils;


import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.cache.DiskCacheEntity;
import org.xutils.common.util.LogUtil;
import org.xutils.config.DbConfigs;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;

import cn.bidostar.ticserver.model.RequestCommonParamsDto;

/**
 * Created by wangg on 2016/12/30.
 */

public class AppDBUtils {
    private static DbManager.DaoConfig daoConfig = null;
    private static DbManager dbManager = null;

    public static DbManager.DaoConfig getDaoConfig(String... dbName) {
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    //设置数据库名，默认xutils.db
                    .setDbName("ldzappai.db")
                    //.setDbName(ToolsKit.returnFirstNotNullStr(dbName, "WeicyAppFrame.db"))
                    //设置表创建的监听
                    .setTableCreateListener(new DbManager.TableCreateListener() {
                        @Override
                        public void onTableCreated(DbManager db, TableEntity table) {
                            LogUtil.i("DB-onTableCreated：" + table.getName());
                        }
                    })
                    //设置是否允许事务，默认true
                    .setAllowTransaction(true)
                    //设置数据库路径，默认安装程序路径下
                    .setDbDir(new File("/mnt/sdcard/cn.bidostar.ticserver/db/"))
                    //设置数据库的版本号
                    .setDbVersion(3)
                    //设置数据库更新的监听
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion,
                                              int newVersion) {
                            if(newVersion>oldVersion) {//新增一个字段
                                try {
                                    db.addColumn(RequestCommonParamsDto.class, "sczt");
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    })
                    //设置数据库打开的监听
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            //开启数据库支持多线程操作，提升性能
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    });
        }
        return daoConfig;
    }


    /**
     * 获取数据库管理器
     *
     * @param dbName 数据库名称
     * @return
     */
    public static DbManager getDB(String... dbName) {
        if (dbManager == null) {
            dbManager = x.getDb(getDaoConfig(dbName));
        }
        return dbManager;
    }

    /**
     * 封装从XUtils3.0默认缓存数据库中查询数据的方法
     */
    private static DiskCacheEntity dbCache(String key) {
        DiskCacheEntity d = new DiskCacheEntity();
        try {
            Selector<DiskCacheEntity> selector = x.getDb(DbConfigs.HTTP.getConfig()).selector(DiskCacheEntity.class);
            WhereBuilder where = WhereBuilder.b("key", "=", key);
            d = selector.where(where).findFirst();
            if (d == null) {
                d = new DiskCacheEntity();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return d;
    }


    /**
     * 删除指定的缓存
     *
     * @param
     */
    public static void deleteCacheFromDisk(String doctorInfoId, String cachePath) {
        try {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("doctorInfoId", doctorInfoId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String key = cachePath + "?<" + jsonObject.toString() + ">";

            WhereBuilder b = WhereBuilder.b("key", "=", key);
            //http://121.41.41.205:8899/interface/doctorintf/queryMainTemplate?<{"doctorInfoId":"12"}>
//            http://121.41.41.205:8899/interface/doctorintf/controllib?<{"doctorInfoId":"12"}>
            System.out.println("key===" + key);
            x.getDb(DbConfigs.HTTP.getConfig()).delete(DiskCacheEntity.class, b);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定的缓存
     *
     * @param
     */
    public static void deleteCacheFromDisk(JSONObject jsonObject, String cachePath) {

        String key = cachePath + "?<" + jsonObject.toString() + ">";
        WhereBuilder b = WhereBuilder.b("key", "=", key);
        //http://121.41.41.205:8899/interface/doctorintf/queryMainTemplate?<{"doctorInfoId":"12"}>
//            http://121.41.41.205:8899/interface/doctorintf/controllib?<{"doctorInfoId":"12"}>
        System.out.println("key===" + key);
        try {
            x.getDb(DbConfigs.HTTP.getConfig()).delete(DiskCacheEntity.class, b);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从SD获取XUtils缓存
     *
     * @param doctorInfoId
     * @param cachePath
     * @return
     */
    public static String getCacheFromDisk(String doctorInfoId, String cachePath) {

        //控件初始化的时候从数据库获取缓存数据
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("doctorInfoId", doctorInfoId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String cacheResult = dbCache(cachePath + "?<" + jsonObject.toString() + ">").getTextContent();

        return cacheResult != null ? cacheResult : "";
    }

}
