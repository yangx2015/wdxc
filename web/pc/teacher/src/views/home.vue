<style lang="less">
  @import '../styles/box.less';
  @import "./hone.less";
</style>
<template>
  <div class="box bodylist">
      <div class="header">
          <div class="box-row">
              <div class="titLeft" @click="MyCenter()">
                  <i class="iconfont icon-wo"></i>
              </div>
              <div class="titCenter body-O" style="text-align: center;">
                  <input class="input"
                         v-model="findInput"
                         @keyup.enter="submit"
                         type="search" placeholder="请输入站点名称">
              </div>
              <div class="titRight" @click="feedback()">
                  <i class="iconfont icon-ybbfeedback"></i>
              </div>
          </div>
      </div>
      <div  class="md-example-child md-example-child-swiper md-example-child-swiper-0">
          <md-swiper
              @before-change="beforeChange"
              @after-change="afterChange"
              ref="swiper">
              <md-swiper-item :key="index" v-for="(item, index) in simple">
                  <a href="javascript:void(0)"
                     class="banner-item"
                     :style="{'background': `${item.color}`}">{{item.text}}</a>
              </md-swiper-item>
          </md-swiper>
      </div>
      <div class="">
        <md-tab-bar
          @indexChanged="tabbarC"
          :titles="titles"
        ></md-tab-bar>
      </div>
      <div v-if="!school" class="body" style="position: relative;">
          <div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%,-150%);font-size: 28px;">
              功能开发中
          </div>
      </div>
      <div v-else class="body">
          <div class="list">
            <div class="listTiT box-row"
                 v-for="(item,index) in lineList"
                 @click="lineMess(item)">
              <div class="body-O">
                <i class="iconfont icon-003lubiao"></i>
                <!--{{item.xlmc}}-->
                123
              </div>
              <div>
                <i class="iconfont icon-right"></i>
              </div>
            </div>
          </div>
      </div>
      <transition name="bounce">
        <div class="trans" v-if="show">
          <div class="centerMess box">
              <div class="header">
                <div class="box-row">
                  <!--<div class="titLeft" @click="MyCenter()">-->
                    <!--<i class="iconfont icon-left"></i>-->
                  <!--</div>-->
                  <div class="titCenter body-O" style="text-align:center;line-height: 0.68rem">
                    <i class="iconfont icon-wo"
                        style="font-size: 0.4rem;color: #949494;"
                    ></i>
                    <span style="font-size: 16px">
                      12345678911
                    </span>
                  </div>
                </div>
              </div>
              <div class="body">
                <myCenter></myCenter>
              </div>
          </div>
          <div class="close" @click="MyCenter">

          </div>
        </div>
      </transition>
  </div>
</template>
<script>
  import {Swiper, SwiperItem,InputItem,TabBar} from 'mand-mobile'
  import simple from 'mand-mobile/components/swiper/demo/data/simple'
  import  myCenter from './myCenter'
  export default {
    name: 'swiper-demo',
    components: {
      myCenter,
      [Swiper.name]: Swiper,
      [SwiperItem.name]: SwiperItem,
      [InputItem.name]:InputItem,
      [TabBar.name]: TabBar,
    },
    data() {
      return {
        show:false,
        simple,
        findInput:'',
        titles: ['校巴线路', '班车线路','约车'],
        lineList:[,,,,,],
        school:true
      }
    },
    mounted() {
    },
    methods: {
      submit(){
        alert(this.findInput)
      },
      beforeChange(from, to) {
        // console.log('befor',from)
        // console.log('befor',to)
      },
      afterChange(from, to) {
        // console.log('after',from)
        // console.log('after',to)
      },
      tabbarC(n,o){//tab切换
        if(n==1){
          this.school = false
        }else if(n==0) {
          this.school = true
        }else if(n==2) {
          this.$router.push({
            name: 'Ycar'
          });
        }
      },
      lineMess(){
        this.$router.push({
          name:'messList'
        })
      },
      MyCenter(){//个人中心
        this.show = !this.show
      },
      feedback(){//信息反馈
        this.$router.push({
          name: 'feedBack'
        });
      }
    },
  }

</script>

<style lang="less" scoped>
  .md-example-child{
    height:320px;
    .banner-item{
        float:left;
        width:100%;
        height:100%;
        line-height:250px;
        text-align:center;
        font-size:28px;
        color:#FFF;
        box-align:center;
        align-items:center;
        box-pack:center;
        justify-content:center;
        text-decoration-line:none;
    }
  }
</style>
