package cn.bidostar.ticserver.dao;

import java.util.List;

import cn.bidostar.ticserver.base.AppBaseDao;
import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.AppDBUtils;

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
