package com.ldz.znzp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.znzp.base.BaseServiceImpl;
import com.ldz.znzp.mapper.ClZnzpMapper;
import com.ldz.znzp.model.ClZnzp;
import com.ldz.znzp.model.ClZpXl;
import com.ldz.znzp.service.HdService;
import com.ldz.znzp.service.ZnzpService;
import com.ldz.znzp.service.ZpXlService;
import com.ldz.znzp.util.NettyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ZnzpServiceImpl extends BaseServiceImpl<ClZnzp,String> implements ZnzpService{
    @Autowired
    private ClZnzpMapper entityMapper;
    @Autowired
    private ZpXlService zpXlService;
    @Autowired
    private HdService hdService;

    @Autowired
    private NettyUtil nettyUtil;

    // 忽略当接收json字符串中没有bean结构中的字段时抛出异常问题
    private ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    @Autowired
    private StringRedisTemplate redisDao;



    @Override
    protected Mapper<ClZnzp> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls(){
        return ClZnzp.class;
    }

    @Override
    public ApiResponse<String> saveEntity(ClZnzp entity) {
        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateMedia(String jgdm) {
        // 获取所有站牌
        List<ClZnzp> znzpList = findAll();
        if (znzpList.size() == 0)return ApiResponse.fail("未找到智能站牌");
        List<String> deviceIds = znzpList.stream().map(ClZnzp::getZdbh).collect(Collectors.toList());
        Map<String,Object> tidChannelMap = nettyUtil.getChannelByTids(deviceIds);
        if (tidChannelMap == null){
//            return ApiResponse.fail("未找到channel");
        }
//        for (Map.Entry<String, Object> entry : tidChannelMap.entrySet()) {
//            hdService.sendActivityNews(entry.getValue(),entry.getKey());
//        }
        return ApiResponse.success();
    }

    /**
     * ZNZP_LISTXL_{{xlId}}
     * 通过线路ID查询  智能站牌列表
     * @param xlId
     * @return
     */
    @Override
    public List<ClZnzp> getByXlId(String xlId) {
        List<ClZnzp> retList =null;
        String redisValue="";
        try {
            redisValue=redisDao.boundValueOps("ZNZP_LISTXL_"+xlId).get();
            if(StringUtils.equals(redisValue,"-")){
                return null;
            }
            try {
                retList =  mapper.readValue(redisValue,mapper.getTypeFactory().constructParametricType(List.class,ClZnzp.class));
            } catch (Exception e) {
                log.error("ZNZP_CL_json转换异常",e);
                e.printStackTrace();
            }
            if(retList==null){
                List<ClZpXl> zpXlList = zpXlService.findEq(ClZpXl.InnerColumn.xlId,xlId);
                List<String> zpIds = zpXlList.stream().map(ClZpXl::getZpId).collect(Collectors.toList());
                retList = findIn(ClZnzp.InnerColumn.zdbh,zpIds);
                try {
                    if(retList!=null&&retList.size()>0){
                        redisValue= mapper.writeValueAsString(retList);
                    }
                } catch (JsonProcessingException e) {
                    log.error("json转换异常",e);
                }

                if(StringUtils.isNotEmpty(redisValue)){
                    String endTime = DateUtils.getToday("yyyy-MM-dd")+" 23:59:59";//拼接出失效时间
                    long interval = (DateUtils.getDate(endTime,"yyyy-MM-dd HH:mm:ss").getTime() - new Date().getTime())/1000;

                    redisDao.boundValueOps("ZNZP_LISTXL_"+xlId).set(redisValue,interval, TimeUnit.SECONDS);//有效期为一天
                }else{
                    redisDao.boundValueOps("ZNZP_LISTXL_"+xlId).set("-",1*60,TimeUnit.SECONDS);//当没有查询到这条记录。系统默认1分钟后再查
                }

                log.info("通过线路ID查询_智能站牌列表 ZNZP_LISTXL_{{xlId}} _从数据库中获取"+redisValue+" 。");
            }else{
                log.info("通过线路ID查询_智能站牌列表 ZNZP_LISTXL_{{xlId}} _从redis中获取"+redisValue+" 。");
            }
        }catch (Exception e){}

        return retList==null ? new ArrayList<ClZnzp>():retList;
    }
}
