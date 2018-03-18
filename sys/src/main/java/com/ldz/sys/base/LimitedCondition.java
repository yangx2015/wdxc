package com.ldz.sys.base;

import com.ldz.util.bean.SimpleCondition;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenwei
 * @copyright
 * @category
 * @since 2018/2/8
 */
public class LimitedCondition extends SimpleCondition {

    public LimitedCondition(Class<?> entityClass) {
        super(entityClass);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String orgCode = (String) request.getAttribute("orgCode");
        // todo 机构检查
//        RuntimeCheck.ifBlank(orgCode,"未找到机构");
//        like("jgdm",orgCode);
    }
}
