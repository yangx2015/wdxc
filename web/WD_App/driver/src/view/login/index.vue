<template>
  <div class="box_col login" style="background-color: #fff">

    <div class="login_logo" style="text-align: center;margin-top: 14%;">
      <img src="static/img/logo.jpg" style="width: 45%" alt="">
    </div>
    <div class="box_col_100" style="">
      <div class="form">
        <div class="formItem">
          <input class="input" type="text" placeholder="请输入您的手机号">
        </div>
        <div class="formItem">
          <input class="input" type="password" placeholder="请输入您的密码">
        </div>
        <div class="subBut"> 登 录</div>
      </div>
    </div>
    <div style="height: 200px">
      <img src="static/img/drive/logbotImg.jpg" style="height:90%;width: 100%" alt="">
    </div>
  </div>
</template>


<script>
  export default {
    name: "index",
  }
</script>

<style lang="less">
  .login {
    .form {
      .formItem {
        padding: 12px 0;
        text-align: center;
        .input {
          width: 80%;
        }
      }
      .subBut {
        width: 78%;
        margin: auto;
        text-align: center;
        font-size: 18px;
        font-weight: bold;
        color: #fff;
        background-color: #5cadff;
        padding: 10px;
        margin-top: 22px;
        border-radius: 5px;
      }
    }
  }
</style>
