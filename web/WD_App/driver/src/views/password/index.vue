<template>
    <div>
      <div>
        <mt-header title="修改密码">
          <router-link to="/" slot="left">
            <mt-button icon="back">返回</mt-button>
          </router-link>
          <mt-button icon="more" slot="right"></mt-button>
        </mt-header>
      </div>
      <div style="text-align: center;padding-top: 35px">
        <div class="inputcs">
          <input size="large" v-model="form.oldPwd" placeholder="请输入原始密码" style="width: 80%;height: 100%;text-align: center"></input>
        </div>
        <div class="inputcs">
          <input size="large" v-model="form.newPwd" placeholder="请输入新密码" style="width: 80%;height: 100%;text-align: center"></input>
        </div>
        <div class="inputcs">
          <input size="large" v-model="form.secPwd" placeholder="请再输入新密码" style="width: 80%;height: 100%;text-align: center"></input>
        </div>
        <div class="boton" style="width: 80%">
          <mt-button @click.native="submit" size="large" type="primary">提交</mt-button>
        </div>
      </div>
    </div>
</template>

<script>
  import 'mint-ui/lib/style.css'
  import { Toast } from 'mint-ui';
    export default {
        name: "index",
        data(){
          return{
            form:{
              oldPwd:'',
              newPwd:'',
              secPwd:''
            }
          }
        },
        created(){},
      methods:{
        getpassword(){
          this.$http.post("/put/jsy/mdfPwd",this.form).then((res)=>{
            if(res.code == 200){
              // ui.navigateBack();
              this.$router.push({name:'login'})
              Toast('密码修改成功')
            }else{
              // ui.showToast({ title: res.message })
              Toast(res.message)
            }
          })
        },
        submit(){
          if(this.form.oldPwd == '' || this.form.newPwd == '' || this.form.secPwd == ''){
            Toast('密码不能为空')
          }
          else{
            this.getpassword()
          }
        }
      },
    }
</script>

<style scoped>
.inputcs{
  height: 56px;
  padding: 8px 10px 8px 10px;
  border-radius: 15px;
  text-align: center;
}
  .boton{
    padding-top: 25px;
    text-align: center;
    margin: auto;
  }
</style>
