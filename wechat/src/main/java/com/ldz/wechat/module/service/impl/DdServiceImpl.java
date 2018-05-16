package com.ldz.wechat.module.service.impl;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.module.mapper.ClDdMapper;
import com.ldz.wechat.module.mapper.ClDdrzMapper;
import com.ldz.wechat.module.model.ClDd;
import com.ldz.wechat.module.model.ClDdrz;
import com.ldz.wechat.module.model.ClJsy;
import com.ldz.wechat.module.model.SysJzgxx;
import com.ldz.wechat.module.model.SysYh;
import com.ldz.wechat.module.service.ClJsyService;
import com.ldz.wechat.module.service.DdService;
import com.ldz.wechat.module.service.DdrzService;
import com.ldz.wechat.module.service.JgService;
import com.ldz.wechat.module.service.SysJzgxxService;
import com.ldz.wechat.module.service.YhService;

import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class DdServiceImpl extends BaseServiceImpl<ClDd,String> implements DdService {

    @Autowired
    private ClDdMapper entityMapper;
    @Autowired
    private ClJsyService jsyService;
    @Autowired
    private DdrzService ddrzService;
    @Autowired
    private JgService jgService;

    @Autowired
    private YhService yhService;
    @Autowired
    private ClDdrzMapper ddrzMapper;//历史订单明细表
    @Autowired
    private SysJzgxxService jzgxxService;
    @Override
    protected Mapper<ClDd> getBaseMapper() {
        return entityMapper;
    }

    public ApiResponse<String> saveEntity(ClDd entity, String userId){
        SysJzgxx clJsy= jzgxxService.findById(userId);
        RuntimeCheck.ifNull(clJsy, "未找到记录");
        RuntimeCheck.ifBlank(entity.getHcdz(),"候车地址不能为空");
        RuntimeCheck.ifBlank(entity.getMdd(),"目的地不能为空");
        RuntimeCheck.ifBlank(entity.getCllx(),"车辆车型不能为空");
        RuntimeCheck.ifNull(entity.getZws(),"乘客人数不能为空");
        RuntimeCheck.ifNull(entity.getYysj(),"乘客预车时间不能为空");
        String orderId=genId();
        entity.setId(orderId);
//        entity.setCjr(userId);
        entity.setCkCjl(userId);//乘客创建人
        entity.setCk(clJsy.getXm());
        entity.setJgdm(clJsy.getJgdm());
        entity.setJgmc(clJsy.getJdmc());
        if (StringUtils.isEmpty(entity.getCklxdh())){
            entity.setCklxdh(clJsy.getSjhm());
        }
        entity.setCjsj(new Date());
        entity.setFkzt("00"); // 未付款
        entity.setDdzt("10");//10-订单创建；11-订单确认；12-订单驳回；13-已派单；20-司机确认(行程结束)；30-队长确认
        int i=entityMapper.insertSelective(entity);
        RuntimeCheck.ifTrue(i==0,"订单入库失败");

        ddrzService.log(entity);

        return ApiResponse.success();
    }

  public   ApiResponse<String> updateOrder(ClDd entity,String userId){

//        1、查找该ID是否存在
      ClDd clDd=findById(entity.getId());
      RuntimeCheck.ifNull(clDd,"未找到订单记录");
      String ddzt=clDd.getDdzt();
//        订单状态  10-订单创建；11-订单确认(待派单)；12-订单驳回；13-已派单；20-司机确认(行程结束)；30-队长确认; 40-财务已收

      if(StringUtils.equals(ddzt,"13")){
          ClJsy clJsy= jsyService.findById(userId);
          RuntimeCheck.ifFalse(StringUtils.equals(clDd.getSj(),clJsy.getSfzhm()),"您不能对非本人的订单进行操作");
      }else{
          RuntimeCheck.ifTrue(true,"订单状态异常，不能进行订单编辑操作");
      }

      ClDd newClDd=new ClDd();
      newClDd.setId(clDd.getId());
      newClDd.setSj(userId);//修改人
      newClDd.setXgsj(new Date());//修改时间
      newClDd.setGlf(entity.getGlf());//过路费
      newClDd.setGqf(entity.getGqf());//过桥费
      newClDd.setSy(entity.getSy());//事由
      newClDd.setZj(entity.getZj());//总价
      newClDd.setSc(entity.getSc());//时长
      newClDd.setDj(entity.getDj());//单价
      newClDd.setLc(entity.getLc());//里程
      newClDd.setScf(entity.getScf());//时长费
      newClDd.setLcf(entity.getLcf());//里程费
      newClDd.setFkzt("00"); // 未付款
      newClDd.setDdzt("20");//订单状态
      newClDd.setSjqrsj(new Date());

      int i=update(newClDd);
      if(i==0){
          RuntimeCheck.ifFalse(false,"修改订单失败");
          return ApiResponse.fail("操作数据库失败");
      }else{
          ClDdrz clDdrz=new ClDdrz();
          clDdrz.setId(genId());//主键ID
          clDdrz.setDdId(clDd.getId());//订单ID
          clDdrz.setCjsj(new Date());//创建时间
          clDdrz.setCjr(userId);//创建人
          clDdrz.setJgdm(clDd.getJgdm());//机构代码
          clDdrz.setJgmc(clDd.getJgmc());//机构名称
          clDdrz.setDdzt(newClDd.getDdzt());//订单状态
          i=ddrzMapper.insertSelective(clDdrz);

          return ApiResponse.success();
      }
  }

    /**
     * 教职工 订单查询
     * @return
     */
    public ApiResponse<List<ClDd>> getOrderWorkersList(String userId){

        ApiResponse<List<ClDd>> result = new ApiResponse<List<ClDd>>();
        SimpleCondition condition = new SimpleCondition(ClDd.class);
        condition.eq(ClDd.InnerColumn.ckCjl.name(),userId);
        condition.setOrderByClause(ClDd.InnerColumn.yysj.desc());
        List<ClDd> orgs = findByCondition(condition);
        result.setResult(orgs);
        return result;
    }
    /**
     * 列表 订单查询
     * @param type  1、今日单据  2、待确认  3、历史单据
     * @return
     */
    public ApiResponse<List<ClDd>> getOrderDriverList(String userId, String type){
        ApiResponse<List<ClDd>> result = new ApiResponse<List<ClDd>>();
        SimpleCondition condition = new SimpleCondition(ClDd.class);
        condition.eq(ClDd.InnerColumn.sj.name(),userId);

       if(StringUtils.equals(type,"2")) {//待确认
            // 10-订单创建；11-订单确认；12-订单驳回；13-已派单；20-司机完成行程(行程结束)；30-队长确认
            condition.eq(ClDd.InnerColumn.ddzt.name(),"13");
           condition.setOrderByClause(ClDd.InnerColumn.yysj.asc());
        }else if(StringUtils.equals(type,"3")) {//历史单据
            // 10-订单创建；11-订单确认；12-订单驳回；13-已派单；20-司机完成行程(行程结束)；30-队长确认
            List<String> list= new ArrayList<String>();
            list.add("13");
            condition.notIn(ClDd.InnerColumn.ddzt.name(),list);
           condition.setOrderByClause(ClDd.InnerColumn.yysj.desc());
        }else{
            RuntimeCheck.ifTrue(true, "未找到记录");
        }


        List<ClDd> orgs = findByCondition(condition);

        result.setResult(orgs);
        return result;
    }

    @Override
    public ApiResponse<String> evaluate(String orderId, String grade) {
        RuntimeCheck.ifBlank(orderId,"请选择订单");
        RuntimeCheck.ifBlank(grade,"请输入评分");
        ClDd order = findById(orderId);
        RuntimeCheck.ifNull(order,"未找到订单");
        ClDd newOrder = new ClDd();
        newOrder.setId(orderId);
        newOrder.setPjdj(new Short(grade));
        entityMapper.updateByPrimaryKeySelective(newOrder);
        return ApiResponse.success();
    }
}
