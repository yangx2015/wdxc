package com.ldz.znzp.service;

import com.ldz.znzp.base.BaseService;
import com.ldz.znzp.model.SysHdyx;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author chenwei
 * @since 2018/2/26
 */
public interface HdService extends BaseService<SysHdyx,String> {


    void sendActivityNews(ChannelHandlerContext ctx, String tid);
}
