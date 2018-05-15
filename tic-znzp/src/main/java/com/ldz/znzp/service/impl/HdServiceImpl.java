package com.ldz.znzp.service.impl;

import com.ldz.util.bean.SimpleCondition;
import com.ldz.znzp.base.BaseServiceImpl;
import com.ldz.znzp.mapper.SysYxhdwjMapper;
import com.ldz.znzp.mapper.ZnzpSysHdyxMapper;
import com.ldz.znzp.model.SysHdyx;
import com.ldz.znzp.model.SysYxhdwj;
import com.ldz.znzp.service.HdService;
import com.ldz.znzp.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;
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
    private ZnzpSysHdyxMapper sysHdyxMapper;
    @Autowired
    private SysYxhdwjMapper yxhdwjMapper;
    @Autowired
    private NettyUtil nettyUtil;

    @Value("${staticUrl:/}")
    private String staticUrl;

    @Override
    protected Mapper<SysHdyx> getBaseMapper() {
        return sysHdyxMapper;
    }

    @Override
    public List<SysHdyx> getHd(String jgdm){
        SimpleCondition condition = new SimpleCondition(SysHdyx.class);
        condition.lte(SysHdyx.InnerColumn.kssj, new Date());//开始时间
        condition.gte(SysHdyx.InnerColumn.jssj, new Date());//结束时间
        condition.eq(SysHdyx.InnerColumn.hdlx, "10");//活动类型
        if (StringUtils.isNotEmpty(jgdm)){
            condition.eq(SysHdyx.InnerColumn.jgdm, jgdm);//活动类型
        }
        List<SysHdyx> list = sysHdyxMapper.selectByExample(condition);
        return list;
    }

    @Override
    public List<Map<String,String>> convert(List<SysHdyx> list){
        List<Map<String,String>> urlList= new ArrayList<>();
        for (SysHdyx hdyx : list) {
            Map<String,String> map= new HashMap<>();
            String tableUrl=hdyx.getUrl();
            boolean b=(tableUrl.toLowerCase()).startsWith("http");//判断字符串是否已百度二字开头
            String path = "";
            if(b){
                path = hdyx.getUrl();
            }else{
                path = staticUrl + hdyx.getUrl();
            }
            map.put("path",path);
            map.put("md5",path);
            map.put("size","");
            map.put("group",hdyx.getWz());
            urlList.add(map);
        }
        return urlList;
    }

    /**
     * 发送活动信息
     * @param ctx
     * @param tid
     */
    @Override
    public void sendActivityNews(ChannelHandlerContext ctx, String tid) {
        SimpleCondition condition = new SimpleCondition(SysHdyx.class);
        condition.lte(SysHdyx.InnerColumn.kssj, new Date());//开始时间
        condition.gte(SysHdyx.InnerColumn.jssj, new Date());//结束时间
        condition.eq(SysHdyx.InnerColumn.hdlx, "01");//活动类型
//        condition.eq(SysHdyx.InnerColumn.zt, "10");//状态(00未开始 10 已开始  20 已结束)

        List<Map<String,String>> urlList= new ArrayList<>();
        List<SysHdyx> list=sysHdyxMapper.selectByExample(condition);
        if (list.size() != 0){
            for (SysHdyx hdyx : list) {
                condition = new SimpleCondition(SysYxhdwj.class);
                condition.eq(SysYxhdwj.InnerColumn.hdId,hdyx.getHdId());
                List<SysYxhdwj> files = yxhdwjMapper.selectByExample(condition);
                if (files.size() != 0){
                    for (SysYxhdwj file : files) {
                        Map<String,String> map=new HashMap<String,String>();
                        map.put("path",file.getWjlj());
                        map.put("md5",file.getWjlj());
                        map.put("size","");
                        map.put("group",hdyx.getWz());
                        urlList.add(map);
                    }
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("command","media");
        map.put("tid",tid);
        map.put("url",urlList);
        nettyUtil.sendData(ctx,map);
    }
}
