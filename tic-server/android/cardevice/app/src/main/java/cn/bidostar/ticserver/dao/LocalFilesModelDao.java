package cn.bidostar.ticserver.dao;

import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

import cn.bidostar.ticserver.base.AppBaseDao;
import cn.bidostar.ticserver.model.LocalFilesModel;
import cn.bidostar.ticserver.utils.AppDBUtils;
import cn.bidostar.ticserver.utils.I;

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
                I.d("datamessage find sql:",AppDBUtils.getDB().selector(LocalFilesModel.class).where("flagupload", "=", t.getFlagUpload()).toString());

                lists = AppDBUtils.getDB().selector(LocalFilesModel.class).where("flagupload", "=", t.getFlagUpload()).findAll();
            }else{
                I.d("datamessage find sql:",AppDBUtils.getDB().selector(LocalFilesModel.class).where("flagupload", "=", t.getFlagUpload()).and("jltype","=",t.getJltype()).toString());
                lists = AppDBUtils.getDB().selector(LocalFilesModel.class).where("flagupload", "=", t.getFlagUpload()).and("jltype","=",t.getJltype()).findAll();
            }
        }catch (Exception e){
            lists = null;
        }
        return lists;
    }

    public synchronized boolean insertModel(LocalFilesModel model){
        try{
            LocalFilesModel lk = AppDBUtils.getDB().selector(LocalFilesModel.class).where("localPath","=",model.getLocalPath()).findFirst();
            if(lk==null || lk.getId()==0){
                this.insertObj(model);
            }
        }catch (Exception e){

        }
        return true;
    }

    public boolean deleteLikeFileName(String fileLikeName){
        try {
           int i =   AppDBUtils.getDB().delete(LocalFilesModel.class, WhereBuilder.b().expr("localpath like '%"+fileLikeName+"%'"));
            I.e("database delete :删除"+fileLikeName+i);
        } catch (DbException e) {
            e.printStackTrace();
            I.e("database delete error:",e);
            return false;
        }
        return true;
    }

}