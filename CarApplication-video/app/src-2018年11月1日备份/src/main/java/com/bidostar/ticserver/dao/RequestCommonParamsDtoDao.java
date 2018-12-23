package com.bidostar.ticserver.dao;

import com.bidostar.ticserver.model.RequestCommonParamsDto;
import com.bidostar.ticserver.utils.AppDBUtils;

import java.util.List;

/**
 * 数据临时请求对象
 */
public class RequestCommonParamsDtoDao extends AppBaseDao<RequestCommonParamsDto> {

    /**
     * 获取全部数据
     * @return
     */
    public List<RequestCommonParamsDto> findAll(){
        try{
            //return AppDBUtils.getDB().findAll(RequestCommonParamsDto.class);
           return AppDBUtils.getDB().selector(RequestCommonParamsDto.class).orderBy("id").limit(20).findAll();//10条10条的上传
        }catch (Exception e){
            return null;
        }
    }

    public void deleteList(List<RequestCommonParamsDto> list){
        try{
            AppDBUtils.getDB().delete(list);
        }catch (Exception e){

        }
    }

}
