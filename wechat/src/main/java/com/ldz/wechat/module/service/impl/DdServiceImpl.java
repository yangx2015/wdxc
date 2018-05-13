package com.ldz.wechat.module.service.impl;


import com.ldz.util.bean.ApiResponse;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.wechat.base.BaseServiceImpl;
import com.ldz.wechat.module.mapper.ClDdMapper;
import com.ldz.wechat.module.mapper.ClDdrzMapper;
import com.ldz.wechat.module.model.*;
import com.ldz.wechat.module.service.*;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;


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
        String xm=entity.getCk();
        String cklxdh=entity.getCklxdh();//
        RuntimeCheck.ifBlank(xm,"乘客姓名不能为空");
        RuntimeCheck.ifBlank(cklxdh,"乘客联系电话不能为空");

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
        entity.setJgdm(clJsy.getJgdm());
        entity.setJgmc(clJsy.getJdmc());
        entity.setCjsj(new Date());
        entity.setFkzt("00"); // 未付款
        entity.setDdzt("10");//10-订单创建；11-订单确认；12-订单驳回；13-已派单；20-司机确认(出车)；21-司机完成行程(行程结束)；30-队长确认
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
//        2、验证当前状态必须是 11-订单确认状态
//        订单状态  10-订单创建；11-订单确认(待派单)；12-订单驳回；13-已派单；20-司机确认(行程结束)；30-队长确认; 40-财务已收

      if(StringUtils.equals(ddzt,"13")){
          ClJsy clJsy= jsyService.findById(userId);
          RuntimeCheck.ifFalse(!StringUtils.equals(clDd.getSj(),clJsy.getSfzhm()),"您不能对非本人的订单进行操作");
      }else if(StringUtils.equals(ddzt,"20")){
          // TODO: 2018/5/12  获取驾驶员信息，
          SysYh user=yhService.selectByKey(userId);
          RuntimeCheck.ifNull(user,"用户核实失败");
          RuntimeCheck.ifTrue(StringUtils.equals(user.getZw(),"队长"),"角色错误，不能进行操作");
          RuntimeCheck.ifFalse(clDd.getJgdm().indexOf(user.getJgdm())==0,"您不能对非本机构订单进行操作");
          RuntimeCheck.ifNull(entity.getZj(),"订单总价不能为空");
          RuntimeCheck.ifNull(entity.getZj()>0,"订单总价不能为空");
      }else{
          RuntimeCheck.ifTrue(true,"订单状态异常，不能进行订单编辑操作");
      }


      ClDd newClDd=new ClDd();
      newClDd.setId(clDd.getId());
      newClDd.setSj(userId);//修改人
      newClDd.setXgsj(new Date());//修改时间
      newClDd.setGlf(entity.getGlf());//过路费
      newClDd.setSy(entity.getSy());//事由
      newClDd.setZj(entity.getZj());//总价
      newClDd.setSc(entity.getSc());//时长
      newClDd.setDj(entity.getDj());//单价
      newClDd.setLc(entity.getLc());//里程
      newClDd.setScf(entity.getScf());//时长费
      newClDd.setLcf(entity.getLcf());//里程费
      newClDd.setFkzt("00"); // 未付款

      int i=update(newClDd);
      if(i==0){
          RuntimeCheck.ifFalse(false,"修改订单失败");
          return ApiResponse.fail("操作数据库失败");
      }else{
          return ApiResponse.success();
      }
  }

    /**
     * 订单确认 操作
     * 1、订单处于：司机确认(行程结束)
     * 2、只有该队队长才能有限制
     * 3、修改订单状态为 队长确认
     * 4、复制订单表到原始订单表中
     *
     *
     * @param entity
     * 订单ID 必填
     * @return
     * 成功与否
     */
   public ApiResponse<String> updateAffirmOracle(ClDd entity, String userId){
       SysYh user=yhService.selectByKey(userId);
       RuntimeCheck.ifNull(user,"用户核实失败");
       RuntimeCheck.ifTrue(StringUtils.equals(user.getZw(),"队长"),"角色错误，不能进行操作");
//        1、查找该ID是否存在
       ClDd clDd=findById(entity.getId());
       RuntimeCheck.ifNull(clDd,"未找到订单记录");
       RuntimeCheck.ifFalse(clDd.getJgdm().indexOf(user.getJgdm())==0,"您不能对非本机构订单进行操作");
////        2、验证当前状态必须是 11-订单确认状态
//        String ddzt=clDd.getDdzt();
//        RuntimeCheck.ifFalse(StringUtils.equals(ddzt,"20"),"订单没有处理司机  确认状态，不能进行队长确认操作");
//        RuntimeCheck.ifFalse(StringUtils.equals(clDd.getDzbh(),userId),"订单不属于本人，不能进行队长确认操作");

       RuntimeCheck.ifNull(clDd.getZj(),"订单总价不能为空");
       RuntimeCheck.ifNull(clDd.getZj()>0,"订单总价不能为空");

       ClDd newClDd=new ClDd();
       newClDd.setId(clDd.getId());
       newClDd.setDdzt("30");//订单状态
       int i=update (newClDd);
       if(i==0){
           RuntimeCheck.ifFalse(false,"操作数据库失败");
       }

       // 需要将订单表明细复制到原始单据表中。
       entityMapper.insertCopyOrder(clDd.getId(),userId);

       ClDdrz clDdrz=new ClDdrz();
       clDdrz.setId(genId());//主键ID
       clDdrz.setDdId(clDd.getId());//订单ID
       clDdrz.setCjsj(new Date());//创建时间
       clDdrz.setCjr(userId);//创建人
       clDdrz.setJgdm(clDd.getJgdm());//机构代码
       clDdrz.setJgmc(clDd.getJgmc());//机构名称
       clDdrz.setDdzt(newClDd.getDdzt());//订单状态
       i=ddrzMapper.insertSelective(clDdrz);
       if(i==0){
           RuntimeCheck.ifFalse(false,"创建订单历史表失败");
           return ApiResponse.error();
       }else{
           return ApiResponse.success();
       }
   }
}
