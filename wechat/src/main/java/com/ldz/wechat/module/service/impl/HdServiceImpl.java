package com.ldz.wechat.module.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.module.mapper.SysHdyxMapper;
import com.ldz.wechat.module.mapper.SysYxhdwjMapper;
import com.ldz.wechat.module.model.SysHdyx;
import com.ldz.wechat.module.model.SysYxhdwj;
import com.ldz.wechat.module.service.HdService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chenwei
 * @copyright
 * @category
 * @since 2018/2/26
 */
@Service
public class HdServiceImpl extends BaseServiceImpl<SysHdyx,String> implements HdService {
    @Autowired
    private SysHdyxMapper hdyxMapper;
    @Autowired
    private SysYxhdwjMapper yxhdwjMapper;
    @Value("${staticUrl:/}")
    private String staticUrl;

    @Override
    protected Mapper<SysHdyx> getBaseMapper() {
        return hdyxMapper;
    }


    public List<SysHdyx> getAdsenseList(){
        SimpleCondition condition = new SimpleCondition(SysHdyx.class);
        condition.lte(SysHdyx.InnerColumn.kssj, new Date());//开始时间
        condition.gte(SysHdyx.InnerColumn.jssj, new Date());//结束时间
        condition.eq(SysHdyx.InnerColumn.hdlx, "00");//活动类型 00微信、10智能站牌
        condition.eq(SysHdyx.InnerColumn.zt, "10");//状态(00未开始 10 已开始  20 已结束)
        List<SysHdyx> list=hdyxMapper.selectByExample(condition);
        if(list==null){
            list=new ArrayList<SysHdyx>();
        }
        setFiles(list);
        return list;
    }

    private void setFiles(List<SysHdyx> list){
        if (list.size() == 0)return;
        List<String> hdIds = list.stream().map(SysHdyx::getHdId).collect(Collectors.toList());
        SimpleCondition condition = new SimpleCondition(SysYxhdwj.class);
        condition.in(SysYxhdwj.InnerColumn.hdId,hdIds);
        List<SysYxhdwj> files = yxhdwjMapper.selectByExample(condition);
        if (files.size() == 0)return;
        Map<String,SysHdyx> hdMap = list.stream().collect(Collectors.toMap(SysHdyx::getHdId, p->p));
        for (SysYxhdwj file : files) {
            String hdId = file.getHdId();
            if (StringUtils.isEmpty(hdId))continue;
            SysHdyx hd = hdMap.get(hdId);
            if (hd == null)continue;
            if (StringUtils.isEmpty(hd.getImg())){
                hd.setImg(staticUrl+"/"+file.getWjlj());
                hd.setUrl(staticUrl+"/"+file.getWjlj());
            }
        }
    }

//    private List<SysHdyx> setFiles(List<SysHdyx> list){
//        if(list!=null&&list.size()>0){
//            List<String> hdIds = list.stream().map(SysHdyx::getHdId).collect(Collectors.toList());
//            SimpleCondition condition = new SimpleCondition(SysYxhdwj.class);
//            condition.in(SysYxhdwj.InnerColumn.hdId,hdIds);
//            List<SysYxhdwj> files = yxhdwjMapper.selectByExample(condition);
//            if (files.size() == 0){
//
//            }else{
//
//            }
//            Map<String,SysHdyx> hdMap = list.stream().collect(Collectors.toMap(SysHdyx::getHdId, p->p));
//
//
//            for(SysHdyx l:list){
//                String tableUrl=l.getUrl();
//                boolean b=(tableUrl.toLowerCase()).startsWith("http");//判断字符串是否已百度二字开头
//                if(!b){
//                    l.setUrl(staticUrl+l.getUrl());
//                }
//                String imgUrl=l.getImg();
//                if(StringUtil.isNotEmpty(imgUrl)){
//                    b=(imgUrl.toLowerCase()).startsWith("http");//判断字符串是否已百度二字开头
//                    if(!b){
//                        l.setImg(staticUrl+l.getImg());
//                    }
//                }
//            }
//        }
//        return list;
//    }
}
