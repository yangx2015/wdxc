package com.bidostar.ticserver.dao;

import com.bidostar.ticserver.model.LocalFilesModel;
import com.bidostar.ticserver.utils.AppDBUtils;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admins on 2018/3/1.
 */

public class LocalFilesModelDao extends AppBaseDao<LocalFilesModel> {

    /**
     * 根据文件路径查询某一个文件
     * @param t
     * @return
     */
    public LocalFilesModel findOneByFilePath(LocalFilesModel t){
        try{
            t =  AppDBUtils.getDB().selector(LocalFilesModel.class).where("localpath","=",t.getLocalPath()).findFirst();
        }catch (Exception e){
            t = null;
        }
        return t;
    }
    public List<LocalFilesModel> findAll(){
        try{
            return AppDBUtils.getDB().findAll(LocalFilesModel.class);
        }catch (Exception e){
            return null;
        }

    }
    /**
     * 查询数据库中文件是否已经上传的列表
     * @param t
     * @return
     */
    public List<LocalFilesModel> findListByFlagupload(LocalFilesModel t){
        List<LocalFilesModel> lists = new ArrayList<>();
        try{
            if(t.getJltype()==null || t.getJltype().trim().equals("")) {
                //I.d("datamessage find sql:",AppDBUtils.getDB().selector(LocalFilesModel.class).where("flagupload", "=", t.getFlagUpload()).toString());

                lists = AppDBUtils.getDB().selector(LocalFilesModel.class).where("flagupload", "=", t.getFlagUpload()).limit(5).offset(1).findAll();
            }else{
                //I.d("datamessage find sql:",AppDBUtils.getDB().selector(LocalFilesModel.class).where("flagupload", "=", t.getFlagUpload()).and("jltype","=",t.getJltype()).toString());
                lists = AppDBUtils.getDB().selector(LocalFilesModel.class).where("flagupload", "=", t.getFlagUpload()).and("jltype","=",t.getJltype()).limit(5).offset(1).findAll();
            }
        }catch (Exception e){
            lists = null;
        }
        return lists;
    }

    public  boolean insertModel(LocalFilesModel model){
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try{
            if(model!=null && model.getLocalPath()!=null) {
                LocalFilesModel lk = AppDBUtils.getDB().selector(LocalFilesModel.class).where("localPath", "=", model.getLocalPath()).findFirst();
                if (lk == null || lk.getLocalPath() == null) {
                    this.insertObj(model);
                }
            }
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
        return true;
    }

    public boolean deleteLikeFileName(String fileLikeName){
        try {
           int i =   AppDBUtils.getDB().delete(LocalFilesModel.class, WhereBuilder.b().expr("localpath like '%"+fileLikeName+"%'"));
        } catch (DbException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
