import Vue from 'vue';
import axios from 'axios'
import api from './api'
import Cookies from 'js-cookie';
import {router} from '../router/index';
import qs from 'qs';
import store from '@/store'
//订单分配权限
let httpInstance = axios.create({
// baseURL: 'http://127.0.0.1:80',
baseURL: 'http://47.98.39.45:8080',
// 	 baseURL: 'http://192.168.31.181:80',//陈
//    	 baseURL: 'http://192.168.31.228:80',//羊
// 	 baseURL: 'http://192.168.31.228:80',//羊
    timeout: 300000,
    headers: {'Content-Type':'application/x-www-form-urlencoded'},
    withCredentials:true
});
// 添加请求拦截器 数据请求之前
httpInstance.interceptors.request.use((config) => {
	console.log('数据加载前',config.url)
	if(config.url =="/api/clzd/getzdcl/"){
		store.commit('CloadingType',false)//全局加载等待
	}else{
		store.commit('CloadingType',true)//全局加载等待
	}

    var headers = config.headers;
    var contentType = headers['Content-Type'];
    if (contentType == "application/x-www-form-urlencoded"){
        config.data = qs.stringify(config.data);
        try{
            //如果是数组对象，将转换出来的数组字符串中的[]关键字替换，这样方便后台接收数据
            config.data = config.data.replace(/(%5B\d%5D)/g,"");
        }catch(e){

        }
    }
    // 在发送请求之前做些什么
//  console.log('拦截数据请求',config)
//  console.log('123123',Cookies.get('result'))
    if(Cookies.get('result')){
    	let accessToken = JSON.parse(Cookies.get('result')).accessToken;
    	config.headers.token = accessToken.token;
    	config.headers.userid = accessToken.userId;
    }
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器 数据响应之后
httpInstance.interceptors.response.use((response) => {
	var v = this
    // 对响应数据做点什么
    store.commit('CloadingType',false)
    if (response.status===404){
        router.push({name: 'error-404'})
    }
    if (response.status===200){
        if (response.data.code===403){
            router.push({name: 'error-403'})
        }else{
            return response.data;
        }
    }else{
        router.push({name: 'error-500'})
    }
    // if(response.status===200&&response.data.code===200){
    // 	return response.data;
    // }else if(!Cookies.get('result')||response.status===500){
  	// 	router.push({name: 'error-500'})
    // }else if(Cookies.get('result')&&response.status===500){
  	// 	router.push({name: 'errorpage_500'})
    // }else if(response.status===200&&response.data.code===403){
    // 	router.push({name: 'error-403'})
    // }else if(response.status===200&&response.data.code===500){
    // 	router.push({name: 'errorpage_500'})
    // }
}, function (error) {

    // 对响应错误做点什么
		if(!Cookies.get('result')){
			console.log('1')
  		router.push({name: 'error-500'})
    }else if(Cookies.get('result')){
    	console.log('2')
  		router.push({name: 'errorpage_500'})
    }
    return Promise.reject(error);
});
export default httpInstance;
