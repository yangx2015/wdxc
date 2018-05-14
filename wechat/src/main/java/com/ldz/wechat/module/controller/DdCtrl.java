package com.ldz.wechat.module.controller;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.model.ClDd;
import com.ldz.wechat.module.model.SysZdxm;
import com.ldz.wechat.module.service.DdService;
import com.ldz.wechat.module.service.ZdxmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/12.
 */
@RestController
@RequestMapping("put/dd")
public class DdCtrl {
    @Autowired
    private DdService service;
    @Autowired
    private ZdxmService zdxmService;

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static String getCurrentUser(boolean require) {
        return "1";
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String userInfo = (String) request.getAttribute("userInfo");
//        RuntimeCheck.ifTrue(require && userInfo == null,"当前登录用户未空！");
//        return userInfo;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        //true:允许输入空值，false:不能为空值
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    @RequestMapping(value="/getzdxm", method={RequestMethod.POST})
    public ApiResponse<List<SysZdxm>> save(String zdlmdm){
        if(StringUtils.isEmpty(zdlmdm)){
            zdlmdm="ZDCLK0041";
        }
        List<SysZdxm> list=zdxmService.findByTypeCode(zdlmdm);
        return ApiResponse.success(list);
    }
//
    /**
     * 订单新增页面
     * @param entity
     *
     * @return
     */
    @RequestMapping(value="/save", method={RequestMethod.POST})
    public ApiResponse<String> save(ClDd entity){
        String userId = getCurrentUser(true);
        return service.saveEntity(entity,userId);
    }
    /**
     * 订单确认-订单详情
     * 通过订单ID 加 来验证驾驶员ID该订单的有效性
     * @param pkid
     *
     * @return
     *  ClDd 对象。
     */
    @RequestMapping(value="/ddxq/{pkid}", method={RequestMethod.GET})
    public ApiResponse<ClDd> get(@PathVariable("pkid")String pkid){
        String userId = getCurrentUser(true);
        ClDd whereClDd=new ClDd();
        whereClDd.setId(pkid);
        whereClDd.setSj(userId);
        List<ClDd> list =service.findByEntity(whereClDd);
        if(list==null|| list.size()==0){
            return ApiResponse.success(new ClDd());
        }
        return ApiResponse.success(list.get(0));
    }

    /**
     * 订单确认-订单编辑(司机和队长共用的修改功能。)
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
        String userId = getCurrentUser(true);
        return service.updateOrder(entity,userId);
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
        String userId = getCurrentUser(true);
        return service.updateAffirmOracle(entity,userId);
    }

    /**
     * 司机确认
     * @param id
     * @return
     */
    @RequestMapping(value = "driverConfirm",method = {RequestMethod.POST})
    public ApiResponse<String> driverConfirm(String id){
        String userId = getCurrentUser(true);
        return service.driverConfirm(id,userId);
    }
}
