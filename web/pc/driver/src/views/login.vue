<style lang="less">
    @import './login.less';
  .loginBytton{
      position: fixed;
      bottom: 0;
      width:100%;
      padding: 45px;
  }
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">
    	<!--<img src="../../static/3.png"/>-->
        <div class="yc">
          <h1 style="color: #cce8ff;">Welcome</h1>
          <!--<h1 class="huan">-->
            <!--欢-->
          <!--</h1>-->
          <!--<h1 class="ying">-->
            <!--迎-->
          <!--</h1>-->
          <!--<h1 class="yue">-->
            <!--约-->
          <!--</h1>-->
          <!--<h1 class="che">-->
            <!--车-->
          <!--</h1>-->
        </div>
        <div class="login-con">
            <Card :bordered="false">
                <!--<p slot="title">-->
                    <!--<Icon type="log-in"></Icon>-->
                    <!--欢迎登录-->
                <!--</p>-->
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="xm">
                            <Input v-model="form.xm" placeholder="请输入用户名">
                                <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="sfzhm">
                            <Input type="password" v-model="form.sfzhm" placeholder="请输入密码">
                                <span slot="prepend">
                                    <Icon :size="14" type="card"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                    </Form>
                </div>
            </Card>
        </div>
        <div class="loginBytton">
            <Button @click="handleSubmit"
                    size="large"
                    type="primary" long>登录</Button>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
import configApi from '@/axios/config.js'
export default {
    data () {
        return {
            form: {
                xm: '测试订单A',
                sfzhm: '2123123'
            },
            rules: {
                xm: [
                    { required: true, message: '账号不能为空', trigger: 'blur' }
                ],
                sfzhm: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ]
            }
        };
    },
    created(){
    },
    methods: {
        handleSubmit () {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                	this.$http.post(this.apis.LOGIN.QUERTY, this.form).then((res) =>{
                		console.log('登陆结果',res)
                		if(res.code===200){
                          Cookies.set('result', res.result);
                          this.$router.push({
                            name:'center'
                          })
                    }else{
                		}
                	})
                }
            });
        }
    }
};
</script>

<style>

</style>
