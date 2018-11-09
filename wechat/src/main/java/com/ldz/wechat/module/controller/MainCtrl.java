package com.ldz.wechat.module.controller;

import com.ldz.util.bean.ApiResponse;
import com.ldz.wechat.module.service.MainService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共跳转接口
 * Created by Administrator on 2018/5/12.
 */
@RestController
public class MainCtrl {
    Logger accessLog = LoggerFactory.getLogger("access_info");
    @Autowired
    private MainService wxMainService;
    /**
     * 微信学生跳转
     * @param mhtkt     验证的key
     * @param type      页面是否做跳转 1 不做跳转 非1是做跳转
     * @return
     */
    @RequestMapping("wxxc_loginretransmission")
    public Object validateStudent(String mhtkt, String type){
        accessLog.debug("学生端登录跳转接口：mhtkt:{}，是否做页面跳转:{}",mhtkt,type);
        if(StringUtils.isNotEmpty(mhtkt)){
            ApiResponse<String> ret=wxMainService.validateStudent(mhtkt);
            if(ret.isSuccess()){//身份验证通过

            }else{//身份验证失效

            }
            return new ModelAndView("redirect:http://47.98.39.45:9071/#/pages/index");

        }else{
            if(StringUtils.equals(type,"1")){
                return ApiResponse.fail("传入的KEY值有误，无法验证");
            }else{
                Map<String, String> map=new HashMap<>();
                map.put("ss","ss");
                map.put("ssss","qq");
                ModelAndView modelAndView = new ModelAndView("redirect:http://47.98.39.45:9071/#/pages/index");
                modelAndView.addObject("headers", map);
                return modelAndView;
            }
        }
    }

    /**
     * 微信员工跳转
     * @param mhtkt     验证的key
     * @param type      页面是否做跳转 1 不做跳转 非1是做跳转
     * @return
     */
    @RequestMapping("wxyx_loginretransmission")
    public Object validateStaff(String mhtkt, String type){
        accessLog.debug("员工端登录跳转接口：mhtkt:{}，是否做页面跳转:{}",mhtkt,type);
        if(StringUtils.isNotEmpty(mhtkt)){
            try {
                ApiResponse<String> ret=wxMainService.validateStaff(mhtkt);
                if(ret.isSuccess()){//身份验证通过
                    String token =ret.getResult();
                    return new ModelAndView("redirect:http://47.98.39.45:9071/skip.html?k="+token);
                }else{//身份验证失效
                    return new ModelAndView("redirect:http://47.98.39.45:9072/#/pages/login");
                }
            }catch (Exception e){
                e.printStackTrace();
                return new ModelAndView("redirect:http://47.98.39.45:9072/#/pages/login");
            }
        }else{
            if(StringUtils.equals(type,"1")){
                return ApiResponse.fail("传入的KEY值有误，无法验证");
            }else{
                ModelAndView modelAndView = new ModelAndView("redirect:http://47.98.39.45:9072/#/pages/login");
                return modelAndView;
            }
        }
    }


}
