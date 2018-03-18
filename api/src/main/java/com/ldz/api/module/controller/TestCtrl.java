package com.ldz.api.module.controller;

import com.ldz.util.bean.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenwei
 * @copyright
 * @category
 * @since 2018/2/9
 */
@RestController
@RequestMapping("test")
public class TestCtrl {
    @RequestMapping("hello")
    public ApiResponse<String> hello(String name){
        return ApiResponse.success("hello:"+name);
    }
}
