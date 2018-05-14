<style lang="less">
	@import '../styles/common.less';
    @import './login.less';
    .login{
	    .imgLeft{
	    	position: relative;
	    	.loginImg{
	    		width: 90%;
	    		position: absolute;
	    		bottom: 0;
			    border-radius: 50px;
	    	}
	    }
	    .from{
	    	/*position: relative;*/
	    	.loginTiT{
	    		/*position: absolute;*/
	    		/*top: -50px;*/
	    		text-align: center;

	    	}
	    	.fromList{
	    		padding-top: 20px;
	    	}
	    }

    }
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
    	<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
			<Spin fix>
				<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
				<div style="font-size: 30px;">数据加载中...</div>
			</Spin>
		</div>
        <div class="login-con">
            <Card :bordered="false" style="width: 100%;">
                <div class="form-con box-row">
                	<div class="body-O imgLeft">
                		<!--<img class="loginImg" src="/static/logo.png" alt="" />-->
                		<img class="loginImg" src="/static/login-left.png" alt="" />
                	</div>
                	<div class="body-O from">
	                	<div class="loginTiT">
	                		<h1>
	                			武汉大学车辆管理信息平台
	                		</h1>
	                	</div>
	                    <Form ref="loginForm" :model="form" :rules="rules">
	                    	<div class="fromList">
		                        <FormItem prop="username">
		                            <Input v-model="form.username" placeholder="请输入用户名">
		                                <span slot="prepend">
		                                    <Icon :size="16" type="person"></Icon>
		                                </span>
		                            </Input>
		                        </FormItem>
	                    	</div>
	                    	<div class="fromList">
		                        <FormItem prop="password">
		                            <Input type="password" v-model="form.password" placeholder="请输入密码">
		                                <span slot="prepend">
		                                    <Icon :size="14" type="locked"></Icon>
		                                </span>
		                            </Input>
		                        </FormItem>
	                    	</div>
	                        <FormItem>
	                            <Button @click="handleSubmit" type="primary" long>登录</Button>
	                        </FormItem>
	                    </Form>
                	</div>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';

import menuList from '../data/list'
import {appRouter} from '../router/router';
export default {
    data () {
        return {
        	SpinShow:false,
            form: {
                username: 'admini',
                password: '123456'
            },
            menus:[],
            rules: {
                username: [
                    { required: true, message: '账号不能为空', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ]
            }
        };
    },
    created(){
        menuList.menuTree = [];
    },
    methods: {
        handleSubmit () {
            debugger
        	var v = this
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                	v.SpinShow = true
                	v.$http.post(this.apis.LOGIN.QUERY, this.form).then((res) =>{
                		if(res.code===200) {
                            Cookies.set('usermess', this.form.username);
                            Cookies.set('result', res.result);
                            sessionStorage.setItem("userInfo",res.result.userInfo);
                            v.initDict();
                            v.getMenuTree();
                            v.SpinShow = false
                        }else if(res.code===500){
                            this.$Message.error(res.message);
                            this.form.username='';
                            this.form.password='';
                		}else{
                            this.$Message.error("用户登陆失败，请重试！");
                            this.form.username='';
                            this.form.password='';
                		}
                		v.SpinShow = false
                	}).catch((error) =>{
                		v.SpinShow = false
                		log('error',error)
                	})
                }
            }),
            setTimeout(()=>{
            	v.SpinShow = false
            },500)
        },
        getMenuTree(){
        	var v = this
        	this.$http.get(this.apis.USERROOT.GET_MENU_TREE).then((res) =>{
        		if(res.code===200){
                    v.session.setItem('menuList',res.result)
//                  menuList.menuTree = res.result;
                    this.addToMenuList(res.result);
                    this.$router.push('home')

                }
        	}).catch((error) =>{
        		log(error)
        	})
        },
        addToMenuList(list){
            for(let r of list){
                menuList.menuList.push(r.name);
                if (r.children){
                    this.addToMenuList(r.children);
                }
            }
        },
        getMenuList(){
        	this.$http.get(this.apis.USERROOT.GET_MENU_LIST).then((res) =>{
        		if(res.code===200){
                    menuList.menuList = res.result;
        		    this.getMenuTree();
                }
        	}).catch((error) =>{
        		log(error)
        	})
        },
        addToList(list){
            for (let r of list){
                this.menus.push(r);
                if (r.children){
                    for (let c of r.children){
                        c.pid = r.name;
                    }
                    this.addToList(r.children);
                }
            }
        },
        initDict(){
            this.$http.get(this.apis.DICTIONARY.QUERY,{params:{pageSize:10000}}).then((res) =>{
                if(res.code===200){
                    for (let r of res.page.list){
                        let a = [];
                        if (!r.zdxmList)continue
                        for (let e of r.zdxmList){
                            a.push({key:e.zddm,val:e.zdmc});
                        }
                        this.$store.state.app.dictMap.set(r.lmdm,a)
                        this.session.setItem('dictMap',this.$store.state.app.dictMap)
//						log('字典',this.$store.state.app.dictMap)
                    }
                }
            }).catch((error) =>{
                log(error)
            })
        },
        initMenu(){
            this.addToList(appRouter,this.menus);
            for (let r of this.menus){
                delete r.children;
                delete r.component;
            }

            let params = {menus:JSON.stringify(this.menus)}
            this.$http.post(this.apis.USERROOT.INIT_MENU,params).then((res) =>{
                if(res.code===200){
                    log(res);
                }
            }).catch((error) =>{
                log(error)
            })
        }
    }
};
</script>

<style>

</style>
