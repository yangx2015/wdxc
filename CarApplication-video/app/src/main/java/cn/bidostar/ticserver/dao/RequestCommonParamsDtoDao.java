package cn.bidostar.ticserver.dao;

import org.xutils.db.sqlite.SqlInfoBuilder;
import org.xutils.db.sqlite.WhereBuilder;

import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.AppDBUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bidostar.ticserver.model.RequestCommonParamsDto;
import cn.bidostar.ticserver.utils.I;
import cn.bidostar.ticserver.utils.TimeUtils;

/**
 * 数据临时请求对象
 */
public class RequestCommonParamsDtoDao extends AppBaseDao<RequestCommonParamsDto>{

    /**
     * 获取全部数据
     * @return
     */
    public List<RequestCommonParamsDto> findAll(){
        try{
            List<RequestCommonParamsDto> data =  AppDBUtils.getDB().selector(RequestCommonParamsDto.class).orderBy("id").limit(10).findAll();//10条10条的上传
            return data;//10条10条的上传
        }catch (Exception e){
            I.e("RequestCommonParamsDtoDao-findAll", e);
            return null;
        } catch (Throwable throwable) {
            I.e("RequestCommonParamsDtoDao-findAll", throwable);
            return null;
        }
    }
}
