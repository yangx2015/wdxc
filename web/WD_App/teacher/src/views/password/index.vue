<template>
      <div class="box" style="background-color: #e0e0e0">
        <head-tit tit="修改密码"></head-tit>
        <div class="md-example-child md-example-child-input-item-1">
          <md-field>
            <md-input-item
              v-model="form.oldPwd"
              ref="name"
              type="password"
              title="请输入原始密码"
              placeholder="原始密码"
              is-title-latent
              clearable
            ></md-input-item>
            <md-input-item
              v-model="form.newPwd"
              ref="id"
              title="请输入新的密码"
              type="password"
              placeholder="新密码"
              is-title-latent
              clearable
            ></md-input-item>
            <md-input-item
              v-model="form.secPwd"
              ref="id"
              type="password"
              title="请再次输入新密码"
              placeholder="新密码"
              is-title-latent
              clearable
            ></md-input-item>
          </md-field>
          <!-- <div>
              <sms-input vmodel="YZM" bindsend="handleSend2" init-text="短信验证码"></sms-input>
          </div> -->
        </div>
        <div  class="row" align="center" style="padding: 20px 50px;width: 80%;font-size: 22px">
          <button class="login" style="width: 80%;height: 45px">确定</button>
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
            form:{},
          }
        },
       methods:{
         getpassword(){

         },
          submit(){
              if(this.oldPwd == '' || this.newPwd == '' || this.secPwd == ''){
                Toast.failed('操作失败')
              }
              else if(this.newPwd != this.secPwd){
                Toast.failed('操作失败')
              }
              else{
                this.getpassword()
              }
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
