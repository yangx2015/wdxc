package cn.bidostar.ticserver.base;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import cn.bidostar.ticserver.utils.AppDBUtils;


/**
 * Created by wangg on 2017/1/10.
 */

public class AppBaseDao<T> {

    /**
     * 插入全部数据，但是这里会先删除数据然后再插入
     * @param lists
     * @return
     */
    public  int insertAll(List<T> lists){
        int saveIndex = 0;
        try{
            deleteAll();
            AppDBUtils.getDB().saveOrUpdate(lists);
            saveIndex++;
        }catch (Exception e){

        }
        return saveIndex;
    }

    public boolean insertObj(T t){
        try{
            AppDBUtils.getDB().save(t);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public  int insertAllUpdate(List<T> lists){
        int saveIndex = 0;
        try{
            AppDBUtils.getDB().saveOrUpdate(lists);
            saveIndex++;
        }catch (Exception e){

        }
        return saveIndex;
    }

    public boolean delete(T t){
        try{
            AppDBUtils.getDB().delete(t);
        }catch (Exception e){

        }
        return true;
    }

    public boolean delete(Long id){
        try{
            AppDBUtils.getDB().deleteById((Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0],id);
        }catch (Exception e){

        }
        return true;
    }

    public boolean update(T t){
        try{
            AppDBUtils.getDB().saveOrUpdate(t);
        }catch (Exception e){

        }
        return true;
    }




    /**
     * 清空表
     * @return
     */
    public boolean deleteAll(){
        try{
            AppDBUtils.getDB().delete((Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
