package com.ldz.znzp.service.impl;

import com.ldz.util.bean.SimpleCondition;
import com.ldz.znzp.base.BaseServiceImpl;
import com.ldz.znzp.mapper.ZnzpSysHdyxMapper;
import com.ldz.znzp.model.SysHdyx;
import com.ldz.znzp.service.HdService;
import com.ldz.znzp.util.NettyUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.*;

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
    private NettyUtil nettyUtil;

    @Value("${staticUrl:/}")
    private String staticUrl;

    @Override
    protected Mapper<SysHdyx> getBaseMapper() {
        return sysHdyxMapper;
    }

    /**
     * 发送活动信息
     * @param ctx
     * @param tid
     */
    @Override
    public void sendActivityNews(ChannelHandlerContext ctx, String tid) {
        List<Map<String,String>> urlList=new ArrayList<Map<String,String>>();

        SimpleCondition condition = new SimpleCondition(SysHdyx.class);
        condition.lte(SysHdyx.InnerColumn.kssj, new Date());//开始时间
        condition.gte(SysHdyx.InnerColumn.jssj, new Date());//结束时间
        condition.eq(SysHdyx.InnerColumn.hdlx, "10");//活动类型
        condition.eq(SysHdyx.InnerColumn.zt, "10");//状态(00未开始 10 已开始  20 已结束)

        List<SysHdyx> list=sysHdyxMapper.selectByExample(condition);
        if(list!=null&&list.size()>0){
            for(SysHdyx l:list){
                Map<String,String> map=new HashMap<String,String>();
                map.put("path",staticUrl+l.getUrl());//URL地址
                map.put("size","");
                map.put("md5","");
                map.put("group",l.getWz());
                urlList.add(map);
                //size":"123","md5":"123456","group":"1"
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("command","media");
        map.put("tid",tid);
        map.put("url",urlList);

        nettyUtil.sendData(ctx,map);
    }
}