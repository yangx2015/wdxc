<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
    	<!--<img src="../../static/3.png"/>-->
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="username">
                            <Input v-model="form.username" placeholder="请输入用户名">
                                <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                                <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long>登录</Button>
                        </FormItem>
                    </Form>
                    <p class="login-tip">输入任意用户名和密码即可</p>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
import configApi from '@/axios/config.js'
//import session from '@/libs/session';
export default {
    data () {
        return {
            form: {
                username: 'admini',
                password: '123456'
            },
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
    },
    methods: {
        handleSubmit () {
        	var v = this
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                	v.$http.post(configApi.LOGIN.QUERY, this.form).then((res) =>{
//              		console.log('登陆结果',res)
                		if(res.code===200){
                			Cookies.set('usermess', this.form.username);
                			Cookies.set('result', res.result);
                			v.root()
//						    this.$router.push({
//						    	name: 'home_index'
//						    });
							v.$router.push('home')
                		}else{
                			v.$router.push({
						    	name: 'error-500'
						    });
                		}
                	}).catch((error) =>{
                		console.log('error',error)
                	})
                }
            })
        },
        root(){
        	this.$http.get(configApi.USERROOT.QUERY).then((res) =>{
//      		console.log('权限列表',res)
        		if(res.code===200){
        			this.session.setItem('userRoot',res.result)
        		}
        	}).catch((error) =>{
        		console.log(error)
        	})
        }
    }
};
</script>

<style>

</style>
