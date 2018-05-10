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
                  <input class="input" type="text" placeholder="请输入站点名称">
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
      <div v-if="false" class="body" style="position: relative;">
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
  </div>
</template>

<script>
  import {Swiper, SwiperItem,InputItem,TabBar} from 'mand-mobile'
  import simple from 'mand-mobile/components/swiper/demo/data/simple'

  export default {
    name: 'swiper-demo',
    components: {
      [Swiper.name]: Swiper,
      [SwiperItem.name]: SwiperItem,
      [InputItem.name]:InputItem,
      [TabBar.name]: TabBar,
    },
    data() {
      return {
        simple,
        titles: ['校巴线路', '班车线路','约车'],
        lineList:[,,,,,]
      }
    },
    mounted() {
      window.triggerSwiper0 = () => {
        this.goto()
      }
      window.triggerSwiper1 = () => {
        this.play()
      }
      window.triggerSwiper2 = () => {
        this.stop()
      }
    },
    methods: {
      setValue(id, value) {
        document.querySelector(id) && (document.querySelector(id).innerHTML = value)
      },
      beforeChange(from, to) {
        this.setValue('#valueSwiper0', from)
        this.setValue('#valueSwiper1', to)
      },
      afterChange(from, to) {
        this.setValue('#valueSwiper2', from)
        this.setValue('#valueSwiper3', to)
      },
      tabbarC(n,o){//tab切换
        alert(n)
        alert(o)
      },
      lineMess(){

      }
    },
  }

</script>

<style lang="less" scoped>
  .md-example-child{
    height:300px;
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
