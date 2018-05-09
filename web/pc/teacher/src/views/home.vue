<style lang="less">
  @import '../styles/box.less';
  @import "./hone.less";
</style>
<template>
  <div>
    <div class="header">
      <div class="box-row">
        <div class="titLeft" @click="MyCenter()">
          <i class="iconfont icon-wo"></i>
        </div>
        <div class="titCenter body-O" style="text-align: center;">
          <input type="text" placeholder="请输入站点名称">
        </div>
        <div class="titRight" @click="feedback()">
        </div>
      </div>
    </div>
    <!--<div  class="md-example-child md-example-child-swiper md-example-child-swiper-0">-->
      <!--<md-swiper-->
        <!--@before-change="beforeChange"-->
        <!--@after-change="afterChange"-->
        <!--ref="swiper">-->
        <!--<md-swiper-item :key="index" v-for="(item, index) in simple">-->
          <!--<a href="javascript:void(0)"-->
             <!--class="banner-item"-->
             <!--:style="{'background': `${item.color}`}">{{item.text}}</a>-->
        <!--</md-swiper-item>-->
      <!--</md-swiper>-->
    <!--</div>-->
  </div>
</template>

<script>
  import {Swiper, SwiperItem,InputItem} from 'mand-mobile'
  export default {
    name: 'swiper-demo',
    components: {
      [Swiper.name]: Swiper,
      [SwiperItem.name]: SwiperItem,
      [InputItem.name]:InputItem
    },
    data() {
      return {
        // simple,
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
      fn(index) {
        this.setValue('#valueSwiper4', index)
      },
      goto() {
        this.$refs.swiper.goto(2)
      },
      play() {
        this.$refs.swiper.play()
      },
      stop() {
        this.$refs.swiper.stop()
      },
    },
  }

</script>

<style lang="less" scoped>
  .md-example-child{
    height:250px;
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
