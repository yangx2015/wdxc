<template>
  <div class="box_col">
    <mt-header title="约车" style="background-color: #ff7f50">
      <mt-button icon="back" slot="left"></mt-button>
      <mt-button icon="more" slot="right"></mt-button>
    </mt-header>
    <div class="userTiT">
      <div class="logTit">
        <!--{{ usermess.userInfo.xm.charAt(0)}}-->
        小
      </div>
      <div class="name">
        <!--{{usermess.userInfo.xm}}-->
        小明
      </div>
      <div class="fen">
        <!--{{usermess.grade}}-->
        0分
      </div>

    </div>
    <mt-navbar v-model="selected">
      <mt-tab-item id="1">选项一</mt-tab-item>
      <mt-tab-item id="2">选项二</mt-tab-item>
      <mt-tab-item id="3">选项三</mt-tab-item>
      
    </mt-navbar>
  </div>
</template>

<script>
  export default {
    name: "index",
    data(){
      return{
        selected:'2'
      }
    }
  }
</script>

<style lang="less">
.userTiT{
  background-image: url('../../../static/img/drive/backTit.png');
  background: 100% no-repeat top;
  text-align: center;
  border-bottom: solid 2px #f5a623;
  .logTit{
    background-color: #5e6dbd;
    color: #fff;
    width: 60px;
    height: 60px;
    border-radius: 50px;
    line-height: 60px;
    font-size: 24px;
    font-weight: 700;
    margin:22px auto 16px;
  }
  .name{
    width: 100%;
    font-size:20px;
    font-weight: 700;
    color: #616161;
    margin: 15px 0;
  }
  .fen{
    width: 100%;
    font-size: 22px;
    font-weight: 700;
    color: #ffa700;
    margin: 15px 0;
  }
}
</style>
