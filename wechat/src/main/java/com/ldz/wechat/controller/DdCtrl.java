package com.ldz.wechat.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.pagehelper.Page;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.model.ClDd;
import com.ldz.wechat.model.ClJsy;
import com.ldz.wechat.service.DdService;

/**
 * 订单表维护
 * 1、订单中，分两种车辆 SJ_SX 司机属性
 *      10 内部司机 通过SJ 关联驾驶员表 CL_JSY.SFZHM
 *      11 外部车   通过车牌号 关联临时车表 CL_LSC.CPH
 *   请求地址要加上：api  因为api的话，可以加上权限的鉴权。
 */
@RestController
@RequestMapping("wechat/dd")
public class DdCtrl{
    @Autowired
    private DdService service;
    /**
     * 获取当前登录用户信息
     * @return
     */
    public static SysYh getCurrentUser(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        SysYh userInfo = (SysYh)request.getAttribute("userInfo");
//		RuntimeCheck.ifNull(userInfo,"当前登录用户未空！");
        return userInfo;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        //true:允许输入空值，false:不能为空值
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * 订单新增页面
     * @param entity
     *
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ClDd entity){
        SysYh userInfo = getCurrentUser();
        return service.saveEntity(entity);
    }


    /**
     * 订单审核 分页查询
     * @param entity
     * @param pager
     * @return
     */
    @RequestMapping(value="/pager", method={RequestMethod.POST, RequestMethod.GET})
    public ApiResponse<List<ClDd>> pager(ClDd entity, Page<ClDd> pager){
        SysYh userInfo = getCurrentUser();
//        RuntimeCheck.ifNull(userInfo,"当前登录用户未空！");
        return service.pager(pager);
    }

    /**
     * 订单审核操作
     * @param entity
     *      1、id    审核操作的订单ID  必填
     *      2、ddzt  订单状态  11-订单确认；12-订单驳回  只能是这两个状态   必填
     *      3、sy    审核意见  非必填
     *
     * 规则
     *  1、检查当前订单是否是待审核状态
     *  2、
     * @return
     */
    @RequestMapping(value="/orderAuditing", method={RequestMethod.POST})
    public ApiResponse<String> orderAuditing(ClDd entity){
        return service.updateOrderAuditing(entity);
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
    @RequestMapping(value="/orderDetails", method={RequestMethod.POST,RequestMethod.GET})
    public ApiResponse<Map<String ,Object>> orderDetails(ClDd entity){
        return service.orderdDetails(entity);
    }

    /**
     * 待分配订单
     * 订单状态为待派单的订单
     * @param entity
     * 1、乘客姓名 ck like 查询
     * @return
     * 待分配的订单列表
     */
    @RequestMapping(value="/dfp", method={RequestMethod.POST,RequestMethod.GET})
    public ApiResponse<List<ClDd>> assignedOrderd(ClDd entity){
        return service.assignedOrderd(entity);
    }

    /**
     * 运输中心-司机列表
     * 派单司机列表
     * @param entity
     * 1、司机名 xm like 查询
     * @return
     * 司机列表
     */
    @RequestMapping(value="/pdsjlb", method={RequestMethod.POST,RequestMethod.GET})
    public ApiResponse<List<ClJsy>> driverList(ClJsy entity){
        return service.driverList(entity);
    }

    /**
     * 已分配订单查询
     * @param entity
     * 1、司机属性 sjSx 司机属性：10:内部司机，关联CL_JSY表 11：外部车，关联临时车表
     * 2、驾驶员ID(驾驶员证件号)  SJ  当为内部车时，该值必填
     * 3、车牌号            cph     当为外部车时，该值必填
     *
     * @return
     * 订单列表
     */
    @RequestMapping(value="/yfp", method={RequestMethod.POST,RequestMethod.GET})
    public ApiResponse<List<ClDd>> affirmOrderList(ClDd entity){
        return service.affirmOrderList(entity);
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
    @RequestMapping(value="/pd", method={RequestMethod.POST,RequestMethod.GET})
    public ApiResponse<String> assignOrder(ClDd entity){
        return service.updateAssignOrder(entity);
    }

    /**
     * 取消派单操作   请求方式为post
     * 1、验证订单ID是否存在
     * 2、验证该订单是否处于：已派单状态
     * 3、将订单状态修改为待洗手间
     * 4、写入日志表。
     * @param entity
     * 1、订单id  id 必填
     * @return
     * 成功或失败提示
     */
    @RequestMapping(value="/qxpd", method={RequestMethod.POST,RequestMethod.GET})
    public ApiResponse<String> cancelAssignOrder(ClDd entity){
        return service.updateCancelAssignOrder(entity);
    }


    /**
     * 订单确认-订单详情
     * 通过订单ID 加 队长ID来验证该订单的有效性
     * @param pkid
     *
     * @return
     *  ClDd 对象。
     */
    @RequestMapping(value="/ddxq/{pkid}", method={RequestMethod.GET})
    public ApiResponse<ClDd> get(@PathVariable("pkid")String pkid){
        SysYh userInfo = getCurrentUser();
//        String userId=userInfo.getYhid();
        String userId="1"; // TODO: 2018/3/19   测试代码
        ClDd whereClDd=new ClDd();
        whereClDd.setId(pkid);
        whereClDd.setDzbh(userId);
        List<ClDd> list =service.findByEntity(whereClDd);
        if(list==null|| list.size()==0){
            return ApiResponse.success(new ClDd());
        }
        return ApiResponse.success(list.get(0));
    }
    /**
     * 订单确认-订单编辑
     * 1、订单处于：司机确认(行程结束)
     * 2、只有该队队长才能有限制
     * @param entity
        id      //订单ID  必填
        glf      //过路费
        sy      //事由
        zj      //总价   必填
        sc      //时长
        dj      //单价
        lc      //里程
        scf      //时长费
        lcf      //里程费
     * @return
     */
    @RequestMapping(value="/update", method={RequestMethod.POST})
    public ApiResponse<String> update(ClDd entity){
        return service.updateOrder(entity);
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
    @RequestMapping(value="/ddqr", method={RequestMethod.POST})
    public ApiResponse<String> affirmOracle(ClDd entity){
        return service.updateAffirmOracle(entity);
    }

    /**
     * 收款管理
     * @param entity
     * ddzt     订单状态  30：因收单据  40：已收单据  必填
     * ck       乘客姓名
     * @return
     */
    @RequestMapping(value="/skgl", method={RequestMethod.POST})
    public ApiResponse<List<Map<String,Object>>> proceedsDetail(ClDd entity){
        return service.proceedsDetail(entity);
    }
    /**
     * 付款管理
     * @param entity
     * ddzt     订单状态  30：因收单据  40：已收单据  必填
     * ck       乘客姓名
     * @return
     */
    @RequestMapping(value="/fkgl", method={RequestMethod.POST})
    public ApiResponse<List<Map<String,Object>>> paymentDetail(ClDd entity){
        return service.paymentDetail(entity);
    }
    /**
     * 财务结算-订单编辑
     * 1、订单处于：队长确认(队长确定价格)
     * 2、只有财务才能有限制
     * @param entity
    id      //订单ID  必填
    glf      //过路费
    sy      //事由
    zj      //总价   必填
    sc      //时长
    dj      //单价
    lc      //里程
    scf      //时长费
    lcf      //里程费
    **** //还有其它所有的字段。
     * @return
     */
    @RequestMapping(value="/cwupdate", method={RequestMethod.POST})
    public ApiResponse<String> updateFinance(ClDd entity){
        return service.updateFinanceOrder(entity);
    }

    /**
     * 财务结算-订单确认
     * 1、订单处于：队长确认(队长确定价格)
     * 2、只有财务才能有限制
     * @param entity
     * id[]      //订单ID  必填 是一个或者多个
     * @return
     */
    @RequestMapping(value="/cwqd", method={RequestMethod.POST})
    public ApiResponse<String> updateFinanceConfirm(String[] ids){
        return service.updateFinanceConfirm(ids);
    }

}