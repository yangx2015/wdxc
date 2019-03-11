<template>
  <div class="box" style="background-color: #e0e0e0">
    <head-tit tit="完善个人信息"></head-tit>
    <div class="md-example-child md-example-child-input-item-1">
      <md-field>
        <md-input-item
          title="手机号"
          type="phone"
          v-model="form.dn"
          placeholder="phone xxx xxxx xxxx"
        ></md-input-item>
        <md-input-item
          title="验证码"
          type="digit"
          v-model="form.pollcode"
          placeholder="请输入验证码"
        ></md-input-item>
      </md-field>
    </div>
    <div  class="row" align="center" style="padding: 20px 50px;width: 80%;font-size: 22px">
      <Button class="yzm" style="width: 80%;height: 45px" @click="handleSend2">获取验证码</Button>
    </div>
    <div  class="row" align="center" style="padding: 20px 50px;width: 80%;font-size: 24px" >
      <Button class="login" style="width: 80%;height: 45px" @click="submit">提交</Button>
    </div>
  </div>
</template>
<script>
  import headTit from "@/views/comp/headTit"
  import {InputItem, Field, Toast, Button} from 'mand-mobile'
  export default {
    name: "index",
    components:{
      headTit,
      [Button.name]: Button,
      [InputItem.name]: InputItem,
      [Field.name]: Field,
    },
    data(){
      return{
        form:{
          dn:'',
          pollcode:''
        }
      }
    },
    methods:{
      handleSend2 () {//发起验证码请求
        console.log(this.form.dn)
        if(/^1[34578]\d{9}$/.test(this.form.dn)){
          Toast.info('验证码已发送')
          this.getdn()
        } else {
          Toast.info('手机号码格式不正确')
        }
      },
      submit(){
        if(this.form.pollcode == ''){
          Toast.info('验证码不能为空')
        }else{
          this.getPhone();
        }
      },
      getdn(){
        this.$http('/put/jzg/sendSMS',this.form).then((res)=>{
          var v = this
          if(res.code==200){
            //请求成功！
            v.form.pollcode = res.message
            Toast.info( "请求成功" )
          }else{
            Toast.info(res.message)
          }
        })
      },
      getPhone(){
        this.$http.post('/put/jzg/updateDn',this.form).then((res)=>{
          var v = this
          if(res.code==200){
            //请求成功！
            Toast.info("成功");
            localStorage.setItem('token',res.result);
            this.$route.push({
              name: 'index'
            })
            this.$http.post('/put/jzg/getInfo',{}).then((res)=>{
              console.log('************',res);
              // this.userinfo = res.result.userInfo
              // console.log(this.userinfo);
              // this.showSlideMenu = true;
            })
          }else{
            Toast.info(res.message)
          }
        })
      },
    }
  }
</script>

<style lang="stylus">
  .row{
    height: 20px;
    line-height: 50px;
    font-size: 14px;
  }
  .yzm{
    background-color: #6d96fc;
    border: 0;
    border-radius: 25px;
    font-size: 14px;
    color: #fff;
    margin-top: 30px;
  }
  .login{
    background-color: #fc8609;
    border: 0;
    border-radius: 25px;
    font-size: 14px;
    color: #fff;
    margin-top: 30px;
  }
  .md-example-child-input-item-1
  .md-field
    padding-bottom 40px
</style>
