package com.ldz.biz.module.service.impl;

import com.ldz.biz.module.mapper.*;
import com.ldz.biz.module.model.*;
import com.ldz.biz.module.service.ClService;
import com.ldz.biz.module.service.DdService;
import com.ldz.biz.module.service.DdrzService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.exception.RuntimeCheck;
import com.ldz.sys.model.SysJg;
import com.ldz.sys.model.SysYh;
import com.ldz.sys.service.JgService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.SimpleCondition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class DdServiceImpl extends BaseServiceImpl<ClDd,String> implements DdService{


    @Autowired
    private ClDdMapper entityMapper;
    @Autowired
    private JgService jgService;
    @Autowired
    private ClService clService;

    @Autowired
    private DdrzService ddrzService;

    @Autowired
    private ClDdrzMapper ddrzMapper;//历史订单明细表
    @Autowired
    private ClDdlsbMapper ddlsbMapper;//订单历史表(订单原始单据表)
    @Autowired
    private ClGpsLsMapper clGpsLsMapper;//车辆GPS列表
    @Autowired
    private ClJsyMapper clJsyMapper;//驾驶员表
    @Autowired
    private ClClMapper clClMapper;//车辆表
    @Autowired
    private ClLscMapper clLscMapper;//临时车表
    @Autowired
    private ClCdMapper clCdMapper;//车队表



    @Override
    protected Mapper<ClDd> getBaseMapper() {
        return entityMapper;
    }


    @Override
    public ApiResponse<String> saveEntity(ClDd entity, SysYh user) {
        String userId=user.getYhid();

        RuntimeCheck.ifFalse(entity.getJgdm().indexOf(user.getJgdm())==0,"您不能为非本机构分派车辆");

        SysJg org = jgService.findByOrgCode(entity.getJgdm());
        RuntimeCheck.ifNull(org,"当前选择的用车单位有误，请核实！");
        RuntimeCheck.ifBlank(entity.getHcdz(),"候车地址不能为空");
        RuntimeCheck.ifBlank(entity.getMdd(),"目的地不能为空");
        String orderId=genId();
        entity.setId(orderId);

        entity.setCjr(userId);

        entity.setJgdm(org.getJgdm());
        entity.setJgmc(org.getJgmc());
        entity.setCjsj(new Date());
        entity.setDdzt("10");//10-订单创建；11-订单确认；12-订单驳回；13-已派单；20-司机确认(出车)；21-司机完成行程(行程结束)；30-队长确认
        int i=entityMapper.insertSelective(entity);
        if(i==0){
            return ApiResponse.fail("订单入库失败");
        }

        ClDdrz clDdrz=new ClDdrz();
        clDdrz.setId(genId());//主键ID
        clDdrz.setDdId(orderId);//订单ID
        clDdrz.setCjsj(new Date());//创建时间
        clDdrz.setCjr(userId);//创建人
        clDdrz.setJgdm(entity.getJgdm());//机构代码
        clDdrz.setJgmc(entity.getJgmc());//机构名称
        clDdrz.setDdzt(entity.getDdzt());//订单状态
        i=ddrzMapper.insertSelective(clDdrz);
        if(i==0){
            RuntimeCheck.ifFalse(false,"创建订单历史表失败");
            return ApiResponse.error();
        }else{
            return ApiResponse.success();
        }
    }

    /**
     * 订单审核操作
     * @param entity
     * @return
     */
    public ApiResponse<String> updateOrderAuditing(ClDd entity){
        SysYh user=getCurrentUser();
        String userId=user.getYhid();

        //订单状态入参验证 验证入参"ddzt"必须是11 或者  12
        Boolean orderTypeValid=true;
        orderTypeValid= StringUtils.equals(entity.getDdzt(),"11");
        if(!orderTypeValid){
            orderTypeValid=StringUtils.equals(entity.getDdzt(),"12");
            RuntimeCheck.ifFalse(orderTypeValid,"设置订单状态有误");
        }

        ClDd clDd=findById(entity.getId());
        RuntimeCheck.ifNull(clDd,"未找到订单记录");

        String oracleType=clDd.getDdzt();//订单状态
        RuntimeCheck.ifFalse(StringUtils.equals(oracleType,"10"),"当前订单不是待审核状态，无法进行该操作");

        ClDd ent=new ClDd();
        ent.setId(entity.getId());
        ent.setXgr(userId);//修改人
        ent.setXgsj(new Date());//修改时间
        ent.setDdzt(entity.getDdzt());//订单状态
        ent.setShsj(new Date());//审核时间
        int i=update(entity);//  根据主键更新属性不为null的值
        if(i==0){
            RuntimeCheck.ifFalse(false,"订单审核操作失败");
//            return ApiResponse.fail("订单入库失败");
        }

        ClDdrz clDdrz=new ClDdrz();
        clDdrz.setId(genId());//主键ID
        clDdrz.setDdId(entity.getId());//订单ID
        clDdrz.setCjsj(new Date());//创建时间
        clDdrz.setCjr(userId);//创建人
        clDdrz.setJgdm(clDd.getJgdm());//机构代码
        clDdrz.setJgmc(clDd.getJgmc());//机构名称
        clDdrz.setDdzt(ent.getDdzt());//订单状态
        clDdrz.setBz(entity.getSy());
        i=ddrzMapper.insertSelective(clDdrz);
        if(i==0){
            RuntimeCheck.ifFalse(false,"创建订单历史表失败");
            return ApiResponse.error();
        }else{
            return ApiResponse.success();
        }
    }


    /**
     * 订单查询页面
     * @param entity
     * 1、id 订单ID  必填
     * @return
     *
     * oracleLog  订单日志表
     * gpsLog       车辆GPS列表
     * initialOracle 原始单据
     */
    public ApiResponse<Map<String,Object>> orderdDetails(ClDd entity){
//        1、定义初始变量
        Map<String,Object> rMap = new HashMap<String,Object>();
        ApiResponse<Map<String,Object>> result = new ApiResponse<Map<String,Object>>();
//        2、验证订单的有效性
        ClDd clDd=findById(entity.getId());
        RuntimeCheck.ifNull(clDd,"未找到订单记录");
//        3、查询订单日志表
        List<ClDdrz> oracleLog = ddrzService.getOrderList(clDd.getId());
        if(oracleLog!=null){
            rMap.put("oracleLog",oracleLog);
        }

//        4、车辆GPS列表
        SimpleCondition condition = new SimpleCondition(ClGpsLs.class);

        condition.gte(ClGpsLs.InnerColumn.cjsj, clDd.getYysj());//开始时间
        condition.lte(ClGpsLs.InnerColumn.cjsj, clDd.getSjqrsj());//结束时间
        condition.eq(ClGpsLs.InnerColumn.zdbh,clDd.getZdbm());//终端编码
        condition.setOrderByClause(ClGps.InnerColumn.cjsj.desc());//创建时间
        List<ClGpsLs> gpsLog = clGpsLsMapper.selectByExample(condition);
        if(gpsLog!=null){
            rMap.put("gpsLog",gpsLog);
        }

//        5、原始单据信息
        condition = new SimpleCondition(ClDdlsb.class);
        condition.eq(ClDdlsb.InnerColumn.id,entity.getId());
        List<ClDdlsb> initialOracle = ddlsbMapper.selectByExample(condition);
        if(initialOracle!=null){
            rMap.put("initialOracle",initialOracle);
        }
        result.setResult(rMap);
        return result;
    }

    /**
     * 待分配订单
     * 查询订单状态为
     * @param entity
     * @return
     */
    public ApiResponse<List<ClDd>> assignedOrderd(ClDd entity){
        ApiResponse<List<ClDd>> result = new ApiResponse<List<ClDd>>();
        SimpleCondition condition = new SimpleCondition(ClDd.class);
        //是否用乘客做查询条件了。
        if(StringUtils.isNotBlank(entity.getCk())){
            condition.like(ClDd.InnerColumn.ck,entity.getCk());
        }

        //车辆类型 字典项：ZDCLK0001：号牌种类 01、大型汽车 02、小型汽车 03、校园巴士
        if(StringUtils.isNotBlank(entity.getCllx())){
            condition.eq(ClDd.InnerColumn.cllx,entity.getCllx());
        }
        condition.eq(ClDd.InnerColumn.ddzt,"11");// TODO: 2018/3/18 这里的是否需要设置为常量？
        condition.setOrderByClause(ClDd.InnerColumn.yysj.desc());
        List<ClDd> orgs = findByCondition(condition);
        result.setResult(orgs);
        return result;
    }

    /**
     * 运输中心-司机列表
     * 派单司机列表
     * @param entity
     * 1、司机名 xm like 查询
     * @return
     */
    public ApiResponse<List<ClJsy>> driverList(ClJsy entity){
        ApiResponse<List<ClJsy>> result = new ApiResponse<List<ClJsy>>();
        //验证一个司机，只能绑定一个车辆，如果一个司机没有绑定或者绑定数超过的话，就不显示。
        Example resExample = new Example(ClJsy.class);
        resExample.and().andCondition(" (SELECT count(1) from CL_CL where SJ_ID=SFZHM) = ",1);
        if(StringUtils.isNotBlank(entity.getXm())){
            resExample.and().andLike(ClJsy.InnerColumn.xm.name(),"%"+entity.getXm()+"%");
        }

        ClDd parameters =new ClDd();//入参
        parameters.setSjSx("10");//默认身份证号码
        List<ClJsy> list = clJsyMapper.selectByExample(resExample);
        if(list!=null){
            for(ClJsy obj:list){
                parameters.setSj(obj.getSfzhm());//设置身份证号码
                ApiResponse<List<ClDd>> retObject=affirmOrderList(parameters);
                if(retObject.isSuccess()){
                    List<ClDd> clDdList=retObject.getResult();
                    obj.setClDdList(clDdList);
                }
            }
        }
        result.setResult(list);
        return result;

    }
    /**
     * 已分配订单查询
     * @param entity
     * 1、司机属性 sjSx 司机属性：10:内部司机，关联CL_JSY表 11：外部车，关联临时车表
     * 2、驾驶员ID(驾驶员证件号)  SJ  当为内部车时，该值必填
     * 3、车牌号            cph     当为外部车时，该值必填
     * @return
     * 订单列表
     */
    public ApiResponse<List<ClDd>> affirmOrderList(ClDd entity){
        ApiResponse<List<ClDd>> result = new ApiResponse<List<ClDd>>();
        SimpleCondition condition = new SimpleCondition(ClDd.class);

        if(StringUtils.equals(entity.getSjSx(),"10")){
            String sj=StringUtils.trimToNull(entity.getSj());
            RuntimeCheck.ifNull(sj,"入参错误，内部车请传入驾驶员ID");
            condition.eq(ClDd.InnerColumn.sj,sj);
        }else if(StringUtils.equals(entity.getSjSx(),"11")){
            String cph=StringUtils.trimToNull(entity.getCph());
            RuntimeCheck.ifNull(cph,"入参错误，外部车请传入车辆号");
            condition.eq(ClDd.InnerColumn.cph,cph);
        }else {
            RuntimeCheck.ifNull(null,"入参错误，请核实");
        }
        condition.eq(ClDd.InnerColumn.ddzt,"13");// TODO: 2018/3/18 这里的是否需要设置为常量？
        condition.setOrderByClause(ClDd.InnerColumn.yysj.desc());
        List<ClDd> orgs = findByCondition(condition);
        result.setResult(orgs);
        return result;
    }

    /**
     * 派单操作   请求方式为post
     * 1、验证订单ID是否存在
     * 2、验证该订单是否处于：待派单状态
     * 3、验证该订单是分配给内部（司机证件号） 还是外部 (车牌号)
     * 3-1、验证司机证件号、车牌号的准备性
     * 3-2、通过订单id修改派单操作
     * 4、写入日志表。
     * @param entity
     * 1、订单id  id 必填
     * 2、司机属性 sjSx 司机属性：10:内部司机，关联CL_JSY表 11：外部车，关联临时车表
     * 3、驾驶员ID(驾驶员证件号)  SJ  当为内部车时，该值必填
     * 4、车牌号            cph     当为外部车时，该值必填
     * @return
     * 成功或失败提示
     */
    public ApiResponse<String> updateAssignOrder(ClDd entity){
        SysYh user=getCurrentUser();
        String userId=user.getYhid();
        ClDd newClDd=new ClDd();

//        1、查找该ID是否存在
        ClDd clDd=findById(entity.getId());
        RuntimeCheck.ifNull(clDd,"未找到订单记录");
//        2、验证当前状态必须是 11-订单确认状态
        String ddzt=clDd.getDdzt();
        RuntimeCheck.ifFalse(StringUtils.equals(ddzt,"11"),"订单没有处理待派单状态，不能进行派单操作");
//        3、验证司机属性
        if(StringUtils.equals(entity.getSjSx(),"10")){
//            3-1、验证该司机是否存在
            ClJsy jsy = clJsyMapper.selectByPrimaryKey(entity.getSj());
            RuntimeCheck.ifNull(jsy,"未找到司机记录");
//              3-2、验证该司机当前是否处于休息状态
            RuntimeCheck.ifFalse(StringUtils.equals(jsy.getZt(),"00"),"该司机正处理休息状态，不能进行派单操作");

//            3-3、查询车辆表，检查出该司机对应的车辆的信息

            SimpleCondition condition = new SimpleCondition(ClCl.class);
            condition.eq(ClCl.InnerColumn.sjId,entity.getSj());

            List<ClCl> cl=clClMapper.selectByExample(condition);
            RuntimeCheck.ifEmpty(cl,"该司机未关联车辆，不能进行派单操作");

            ClCl clCl=cl.get(0);
            RuntimeCheck.ifNull(clCl,"该司机未关联车辆，不能进行派单操作");

            ClCd clcd=clCdMapper.selectByPrimaryKey(clCl.getCdbh());
            RuntimeCheck.ifNull(clcd,"未找到该车辆所关联的车队记录");

//            3-4、对要修改的字段进行给值
            newClDd.setSjSx("10");//司机属性 内部司机、外部司机
            newClDd.setSj(jsy.getSfzhm());//司机
            newClDd.setSjxm(jsy.getXm());//司机姓名
            newClDd.setClId(clCl.getClId());//车辆ID
            newClDd.setCph(clCl.getCph());//车牌号
//            newClDd.setZws((short) 0);//座位数
            newClDd.setDzbh(clcd.getDzbh());//队长编号
            newClDd.setCdbh(clcd.getCdbh());//车队编号
            newClDd.setZdbm(clCl.getZdbh());//终端编号
        }else if(StringUtils.equals(entity.getSjSx(),"11")){

//            3-5、通过车牌号码查询临时车表，验证该车牌的正确性。
            SimpleCondition condition = new SimpleCondition(ClLsc.class);
            condition.eq(ClLsc.InnerColumn.cph,entity.getCph());
            List<ClLsc> lscList=clLscMapper.selectByExample(condition);
            RuntimeCheck.ifEmpty(lscList,"未找到该车牌记录，不能进行派单操作");
            ClLsc clLsc=lscList.get(0);
            RuntimeCheck.ifNull(clLsc,"未找到该车牌记录，不能进行派单操作");

//            3-6、通过车牌号码查询临时车表，验证该车牌的正确性。
            newClDd.setCph(clLsc.getCph());//车牌号
//            newClDd.setSj("");//司机
//            newClDd.setSjxm("");//司机姓名
            newClDd.setZws(clLsc.getZws());//座位数
            newClDd.setClId(clLsc.getClId());//车辆ID
            newClDd.setSjSx("11");//司机属性 内部司机、外部司机
        }else {
            RuntimeCheck.ifNull(null,"司机属性入参错误，请核实");
        }

        newClDd.setDdzt("13");//订单状态
        newClDd.setId(clDd.getId());

        int i=update(newClDd);
        if(i==0){
            RuntimeCheck.ifFalse(false,"派单操作数据库失败");
        }

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
    /**
     * 取消派单操作   请求方式为post
     * 1、验证订单ID是否存在
     * 2、验证该订单是否处于：已派单状态
     * 3、将订单状态修改为待派单状态
     * 4、写入日志表。
     * @param entity
     * 1、订单id  id 必填
     * @return
     * 成功或失败提示
     */
    public ApiResponse<String> updateCancelAssignOrder(ClDd entity){
        SysYh user=getCurrentUser();
        String userId=user.getYhid();

        ClDd newClDd=new ClDd();

//        1、查找该ID是否存在
        ClDd clDd=findById(entity.getId());
        RuntimeCheck.ifNull(clDd,"未找到订单记录");
//        2、验证当前状态必须是 11-订单确认状态
        String ddzt=clDd.getDdzt();
        RuntimeCheck.ifFalse(StringUtils.equals(ddzt,"13"),"订单没有处于已派单状态，不能进行取消派单操作");


        newClDd.setCph("");//车牌号
        newClDd.setSj("");//司机
        newClDd.setSjxm("");//司机姓名
//        newClDd.setZws("");//座位数
        newClDd.setClId("");//车辆ID
        newClDd.setSjSx("");//司机属性 内部司机、外部司机
        newClDd.setZdbm("");//终端编号
        newClDd.setDdzt("11");//订单状态
        newClDd.setId(clDd.getId());
        int i=update(newClDd);
        if(i==0){
            RuntimeCheck.ifFalse(false,"操作数据库失败");
            return ApiResponse.fail("操作数据库失败");
        }

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

    /**
     * 订单编辑-订单确认
     * 1、订单处于：司机确认(行程结束)
     * 2、只有该队队长才能有限制
     * @param entity
     * @return
     */
    public ApiResponse<String> updateOrder(ClDd entity){
        SysYh user=getCurrentUser();
        String userId=user.getYhid();

//        1、查找该ID是否存在
        ClDd clDd=findById(entity.getId());
        RuntimeCheck.ifNull(clDd,"未找到订单记录");
//        2、验证当前状态必须是 11-订单确认状态
        String ddzt=clDd.getDdzt();
        RuntimeCheck.ifFalse(StringUtils.equals(ddzt,"20"),"订单没有处理司机确认状态，不能进行编辑操作");

        RuntimeCheck.ifFalse(StringUtils.equals(clDd.getDzbh(),userId),"订单不属于本人，不能进行编辑操作");

        RuntimeCheck.ifNull(entity.getZj(),"订单总价不能为空");

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

        int i=update(newClDd);
        if(i==0){
            RuntimeCheck.ifFalse(false,"创建订单历史表失败");
            return ApiResponse.fail("操作数据库失败");
        }else{
            return ApiResponse.success();
        }
    }


    public  ApiResponse<String> updateAffirmOracle(ClDd entity){
        SysYh user=getCurrentUser();
        String userId=user.getYhid();

//        1、查找该ID是否存在
        ClDd clDd=findById(entity.getId());
        RuntimeCheck.ifNull(clDd,"未找到订单记录");
//        2、验证当前状态必须是 11-订单确认状态
        String ddzt=clDd.getDdzt();
        RuntimeCheck.ifFalse(StringUtils.equals(ddzt,"20"),"订单没有处理司机确认状态，不能进行队长确认操作");

        RuntimeCheck.ifFalse(StringUtils.equals(clDd.getDzbh(),userId),"订单不属于本人，不能进行队长确认操作");

        ClDd newClDd=new ClDd();
        newClDd.setId(clDd.getId());
        newClDd.setDdzt("30");//订单状态
        int i=update (newClDd);
        if(i==0){
            RuntimeCheck.ifFalse(false,"操作数据库失败");
//            return ApiResponse.fail("操作数据库失败");
        }

// TODO: 2018-03-22 在队长确认后，需要将订单表明细复制到原始单据表中。



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
    /**
     * 收款管理
     * @param entity
     * ddzt     订单状态  30：因收单据  40：已收单据  必填
     * ck       乘客姓名
     * @return
     * jgdm     机构ID
     * jgmc     机构名称
     * ddList   订单列表
     */
    public ApiResponse<List<Map<String,Object>>> proceedsDetail(ClDd entity){
//        1、定义初始变量
        ApiResponse<List<Map<String,Object>>> result = new ApiResponse<List<Map<String,Object>>>();

        List<Map<String,Object>> rList=new ArrayList<Map<String,Object>>();

        Map<String,Object> rMap = new HashMap<String,Object>();
        String firstJgdm="";//原始机构ID
        String firstJgmc="";//原始机构名称
        List<ClDd> firstDdList=new ArrayList<ClDd>();

//        2、验证参数的有效性
        String ddzt=entity.getDdzt();
        if(!(StringUtils.equals(ddzt,"30")||StringUtils.equals(ddzt,"40"))){
            RuntimeCheck.ifFalse(false,"入参错误");
        }
//        3、查询订单信息
        SimpleCondition condition = new SimpleCondition(ClDd.class);

        if(StringUtils.isNotBlank(entity.getCk())){
            condition.like(ClDd.InnerColumn.ck,entity.getCk());
        }
        condition.eq(ClDd.InnerColumn.ddzt,ddzt);
        condition.setOrderByClause(ClDd.InnerColumn.jgdm.desc());
        List<ClDd> orgs = findByCondition(condition);
//        4、遍历订单LIST列表
        if(orgs!= null){
            for(ClDd list:orgs){
               String ddJgdm=StringUtils.trimToEmpty(list.getJgdm());
                if(StringUtils.isNotBlank(ddJgdm)){
                    if(!StringUtils.equals(firstJgdm,ddJgdm)){
//                        4-1 将上一个机构的信息存放到返回的List中
                        rMap.put("ddList",firstDdList);
                        rMap.put("jgdm",firstJgdm);
                        rMap.put("jgmc",firstJgmc);
                        rList.add(rMap);

//                        4-2、清除上一个机构的订单信息
                        rMap = new HashMap<String,Object>();
                        firstJgdm=ddJgdm;
                        firstJgmc=StringUtils.trimToEmpty(list.getJgmc());
                        firstDdList=new ArrayList<ClDd>();
                    }else{
                        firstDdList.add(list);
                    }
                }
            }
        }
        result.setResult(rList);
        return result;
    }

    /**
     * 付款管理
     * @param entity
     * ddzt     订单状态  30：因收单据  40：已收单据  必填
     * ck       乘客姓名
     * @return
     * sjid     司机ID
     * sjxm     司机姓名
     * ddList   订单列表
     */
    public ApiResponse<List<Map<String,Object>>> paymentDetail(ClDd entity){
//        1、定义初始变量
        ApiResponse<List<Map<String,Object>>> result = new ApiResponse<List<Map<String,Object>>>();

        List<Map<String,Object>> rList=new ArrayList<Map<String,Object>>();

        Map<String,Object> rMap = new HashMap<String,Object>();
        String firstSj="";//原始驾驶员ID
        String firstsjxm="";//原始驾驶员姓名
        List<ClDd> firstDdList=new ArrayList<ClDd>();

//        2、验证参数的有效性
        String ddzt=entity.getDdzt();
        if(!(StringUtils.equals(ddzt,"30")||StringUtils.equals(ddzt,"40"))){
            RuntimeCheck.ifFalse(false,"入参错误");
        }
//        3、查询订单信息
        SimpleCondition condition = new SimpleCondition(ClDd.class);

        if(StringUtils.isNotBlank(entity.getCk())){
            condition.like(ClDd.InnerColumn.ck,entity.getCk());
        }
        condition.eq(ClDd.InnerColumn.ddzt,ddzt);
        condition.setOrderByClause(ClDd.InnerColumn.sj.desc());
        List<ClDd> orgs = findByCondition(condition);
//        4、遍历订单LIST列表
        if(orgs!= null){
            for(ClDd list:orgs){
                String ddSj=StringUtils.trimToEmpty(list.getSj());
                if(StringUtils.isNotBlank(ddSj)){
                    if(!StringUtils.equals(firstSj,ddSj)){
//                        4-1 将上一个机构的信息存放到返回的List中
                        rMap.put("ddList",firstDdList);
                        rMap.put("sjid",firstSj);
                        rMap.put("sjxm",firstsjxm);
                        rList.add(rMap);

//                        4-2、清除上一个机构的订单信息
                        rMap = new HashMap<String,Object>();
                        firstSj=ddSj;
                        firstsjxm=StringUtils.trimToEmpty(list.getSjxm());
                        firstDdList=new ArrayList<ClDd>();
                    }else{
                        firstDdList.add(list);
                    }
                }
            }
        }
        result.setResult(rList);
        return result;
    }
    /**
     * 财务结算-订单编辑
     * 1、订单处于：队长确认(队长确定价格)
     * @return
     */
    public ApiResponse<String> updateFinanceOrder(ClDd entity){
        SysYh user=getCurrentUser();
        String userId=user.getYhid();

//        1、查找该ID是否存在
        ClDd clDd=findById(entity.getId());
        RuntimeCheck.ifNull(clDd,"未找到订单记录");
//        2、验证当前状态必须是 11-订单确认状态
        String ddzt=clDd.getDdzt();
        RuntimeCheck.ifFalse(StringUtils.equals(ddzt,"30"),"订单没有处理队长确认状态，不能进行编辑操作");

        RuntimeCheck.ifNull(entity.getZj(),"订单总价不能为空");

        entity.setSj(userId);//修改人
        entity.setXgsj(new Date());//修改时间
//      3、修改订单
        int i=update(entity);
        if(i==0){
            RuntimeCheck.ifFalse(false,"创建订单历史表失败");
            return ApiResponse.fail("操作数据库失败");
        }

//        4、插入日志表
        ClDdrz clDdrz=new ClDdrz();
        clDdrz.setId(genId());//主键ID
        clDdrz.setDdId(clDd.getId());//订单ID
        clDdrz.setCjsj(new Date());//创建时间
        clDdrz.setCjr(userId);//创建人
        clDdrz.setJgdm(clDd.getJgdm());//机构代码
        clDdrz.setJgmc(clDd.getJgmc());//机构名称
        clDdrz.setDdzt(ddzt);//订单状态
        i=ddrzMapper.insertSelective(clDdrz);
        if(i==0){
            RuntimeCheck.ifFalse(false,"创建订单历史表失败");
            return ApiResponse.error();
        }else{
            return ApiResponse.success();
        }
    }

    public ApiResponse<String> updateFinanceConfirm(String[] ids){
        SysYh user=getCurrentUser();
        String userId=user.getYhid();
        RuntimeCheck.ifNull(ids,"入参错误");
        for (String id : ids) {
//            1、查找该ID是否存在
            ClDd clDd=findById(id);
            RuntimeCheck.ifNull(clDd,"未找到订单记录");
//        2、验证当前状态必须是 11-订单确认状态
            String ddzt=clDd.getDdzt();
            RuntimeCheck.ifFalse(StringUtils.equals(ddzt,"30"),"订单没有处理队长确认状态，不能进行财务确定操作");

            clDd.setSj(userId);//修改人
            clDd.setXgsj(new Date());//修改时间
            clDd.setDdzt("40");
//      3、修改订单
            update(clDd);

//        4、插入日志表
            ClDdrz clDdrz=new ClDdrz();
            clDdrz.setId(genId());//主键ID
            clDdrz.setDdId(clDd.getId());//订单ID
            clDdrz.setCjsj(new Date());//创建时间
            clDdrz.setCjr(userId);//创建人
            clDdrz.setJgdm(clDd.getJgdm());//机构代码
            clDdrz.setJgmc(clDd.getJgmc());//机构名称
            clDdrz.setDdzt(clDd.getDdzt());//订单状态
            ddrzMapper.insertSelective(clDdrz);
        }
        return ApiResponse.success();
    }

    public ClDd orderMoneyFormula(ClDd order){


        return order;
    }
}
