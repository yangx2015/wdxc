package com.ldz.wechat.module.service;


import com.ldz.util.bean.ApiResponse;

public interface MainService  {

    ApiResponse<String> validateStudent(String mhtkt);

    ApiResponse<String> validateStaff(String mhtkt);
}
