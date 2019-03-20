<template>
  <div>
    <div>
      <mu-appbar style="width: 100%;text-align: center" color="orange600">
        <mu-button icon slot="left" @click="goback">
          <mu-icon value="chevron_left"></mu-icon>
        </mu-button>
        反馈记录
        <mu-button flat slot="right"></mu-button>
      </mu-appbar>
    </div>
    <div >
      <mu-container>
        <mu-tabs :value.sync="active1" inverse color="orange600" indicator-color="orange600"
                 text-color="orange600" full-width @change="tabbarC(active1)">
          <mu-tab>意见</mu-tab>
          <mu-tab>反馈</mu-tab>
          <mu-tab>投诉</mu-tab>
        </mu-tabs>
        <!--<div v-if="active1 === 0" style="margin-top: 0.5rem">-->
          <!--<div v-for="item in suggestInfo" class="info">-->
            <!--<div class="infoDate">-->
              <!--{{item.data}}-->
            <!--</div>-->
            <!--<div style="clear: both;width: 0;"></div>-->
            <!--<div class="infoTitle" @click="info">-->
              <!--{{item.title}}-->
            <!--</div>-->
            <!--<div class="infoTip" :class="item.tip==='已解决'?'infoTextFinish':'infoTextUnfinish'">-->
              <!--{{item.tip}}-->
            <!--</div>-->
          <!--</div>-->
        <!--</div>-->
        <!--<div v-if="active1 === 1" style="margin-top: 0.5rem">-->
          <!--<div v-for="item in complainInfo" class="info">-->
            <!--<div class="infoDate">-->
              <!--{{item.data}}-->
            <!--</div>-->
            <!--<div style="clear: both;width: 0;"></div>-->
            <!--<div class="infoTitle">-->
              <!--{{item.title}}-->
            <!--</div>-->
            <!--<div class="infoTip" :class="item.tip==='已解决'?'infoTextFinish':'infoTextUnfinish'">-->
              <!--{{item.tip}}-->
            <!--</div>-->
          <!--</div>-->
        <!--</div>-->
        <!--<div v-if="active1 === 2" style="margin-top: 0.5rem">-->
          <!--<div v-for="item in responseInfo" class="info">-->
            <!--<div class="infoDate">-->
              <!--{{item.data}}-->
            <!--</div>-->
            <!--<div style="clear: both;width: 0;"></div>-->
            <!--<div class="infoTitle">-->
              <!--{{item.title}}-->
            <!--</div>-->
            <!--<div class="infoTip" :class="item.tip==='已解决'?'infoTextFinish':'infoTextUnfinish'">-->
              <!--{{item.tip}}-->
            <!--</div>-->
          <!--</div>-->
        <!--</div>-->
      </mu-container>
      <div class="body" style="padding: 8px">
        <div  v-if="listmess.length==0" style="text-align: center;line-height: 4rem">
          <div v-if="form.yjlx=='00'" class="wushuju">
            暂无意见数据
          </div>
          <div v-if="form.yjlx=='10'" class="wushuju">
            暂无反馈数据
          </div>
          <div v-else class="wushuju">
            暂无投诉数据
          </div>
        </div>
        <div class="hisList" v-for="item in listmess" v-else>
          <div class="close" @click="remove(item.yjId)">
            <i class="iconfont icon-guanbi"></i>
          </div>
          <div class="time">
            <i class="iconfont icon-shijian"></i>
            {{item.cjsj}}
          </div>
          <div class="fknr mrmes">
            <div class="tit">反馈内容：</div>
            <div class="mess">
              {{item.nr}}
            </div>
          </div>
          <div class="cljg mrmes">
            <div class="tit">处理结果：</div>
            <div class="lsmess" v-if="item.cljg!=''">
              {{item.cljg}}
            </div>
            <div class="lsmess" v-else>
              未处理
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        titles: ['意见','反馈', '投诉'],
        form:{
          yjlx:'00'
        },
        listmess:[],
        active1: 0,
      }
    },
    created(){
      this.feedbackList()
    },
    methods:{
      goback(){
        this.$router.back()
      },
      tabbarC(n){//tab切换****10投诉  00改建/建议
        if(n===0){
          this.form.yjlx = '00'
        }else if(n===1){
          this.form.yjlx = '10'
        }
        else if(n===2){
          this.form.yjlx = '20'
        }
        this.feedbackList()
      },
      feedbackList(){
        var v = this
        v.$http.post(this.apis.FEEDBACK.LIST, {yhId:'2334232',yjlx:v.form.yjlx}).then((res) =>{
          console.log('*****',res)
          if(res.code ==200){
            v.listmess = res.page.list
          }
        }).catch((error)=>{

        })
      },
      remove(id){
        var v = this
        v.$http.post(this.apis.REMOVE.DELE, {'ids':[id]}).then((res) =>{
          console.log('*****',res)
          v.feedbackList()
        }).catch((error)=>{

        })
      }
    }
  }
</script>

<style lang="less">
  .wushuju{
    padding-top: 80px;
    color: #7f7f7f;
    font-weight: 700;
    font-size: 20px;
    text-align: center;
    vertical-align: middle;
  }
  .hisList{
    border: solid 2px #d8d8d8;
    border-radius: 6px;
    padding: 0.3rem 0.4rem;
    margin: 0.3rem 0;
    box-shadow: 1px 2px 2px #888888;
    position: relative;
  .close{
  i{
    color: #808080;
  }
  position: absolute;
  top: 0;
  right: 0;
  }
  .time{
    color: #020101;
    font-size: 16px;
    font-weight: 600;
  }
  .mrmes{
  .tit{
    color: #7388f5;
    font-weight: 600;
  }
  .mess{
    color:#808080;
    max-height: 65px;
    padding-left: 0.6rem

  }
    .lsmess{
      color: #ff3c73;
      max-height: 65px;
      padding-left: 0.4rem
    }
  }
  }
</style>
