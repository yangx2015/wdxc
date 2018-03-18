package com.ldz.biz.aop;

import com.ldz.sys.base.BaseService;
import com.ldz.sys.mapper.SysPtrzMapper;
import com.ldz.sys.model.SysRz;
import com.ldz.sys.model.SysYh;
import com.ldz.util.commonUtil.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenwei on 2017/9/12
 */
@Aspect
@Component
public class OperateLogAop {
    Logger logger = LogManager.getLogger(this.getClass());
    private Map<String,BaseService> serviceMap = new HashMap<>();

    @Autowired
    private SysPtrzMapper logMapper;

    @Around("execution(public * com.ldz.biz..*.*Controller.save*(..))" +
            "|| execution(public * com.ldz.biz..*.*Controller.update*(..))" +
            "|| execution(public * com.ldz.biz..*.*Controller.remove*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        SysRz log = new SysRz();
        BaseService baseService = null;
        Object pk = null;
        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            int elapseTime = (int) (endTime - startTime);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            SysYh userInfo = (SysYh)request.getAttribute("userInfo");
            log.setFf(joinPoint.getTarget().getClass().getSimpleName() +"." + joinPoint.getSignature().getName());
            log.setCzsj(new Date());
            log.setCzr(userInfo.getYhid());
            log.setZxsj(elapseTime);
            log.setCs(getArgsAsString(joinPoint));
            logMapper.insert(log);
        }
    }

    /**
     * 获取参数的json格式
     * @param joinPoint
     * @return
     */
    private String getArgsAsString(ProceedingJoinPoint joinPoint){
        StringBuilder res = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            res.append(JsonUtil.toJson(arg));
        }
        return res.toString();
    }

    /**
     * 获取操作对象类型
     * @param joinPoint
     * @return
     */
    private Object getEntity(ProceedingJoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg.getClass().isAnnotationPresent(Table.class)){
                return arg;
            }
        }
        return null;
    }

    /**
     * 获取主键
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    private Object getPK(Object obj) throws IllegalAccessException {
        if (obj == null)return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)){
                field.setAccessible(true);
                return field.get(obj);
            }
        }
        return null;
    }

    /**
     * 获取BaseService
     * @param joinPoint
     * @return
     */
    private BaseService getBaseService(ProceedingJoinPoint joinPoint){
        try {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            if (serviceMap.containsKey(className)){
                return serviceMap.get(className);
            }
            Method method = joinPoint.getTarget().getClass().getDeclaredMethod("getBaseService");
            if (method == null)return null;
            method.setAccessible(true);
            BaseService baseService = (BaseService) method.invoke(joinPoint.getTarget());
            serviceMap.put(className,baseService);
            return baseService;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Object getById(BaseService baseService,Object pk) throws IllegalAccessException {
        if (baseService == null || pk == null)return null;
        return baseService.findById((Serializable) pk);
    }
}
