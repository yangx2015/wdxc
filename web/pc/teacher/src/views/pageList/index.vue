<style lang="less">
  @import "../../styles/box.less";
  @import "./list.less";
</style>
<template>
      <div class="pagelist box" style="font-size: 16px">
          <div>
            tit
          </div>
          <div class="body DDlist">
              <div class="DDitem" v-for="item in [,,,,,,,,,,,]">
                <div class="tit box-row">
                      <div class="body-O">
                        周师傅-鄂A00000
                      </div>
                      <div>
                        已完成
                        <i class="iconfont icon-right"></i>
                      </div>
                </div>
                <div>
                  <i class="iconfont icon-shijian"></i>
                  时间
                </div>
                <div>
                  <i class="iconfont icon-dian1"
                      style="color: #00a854"
                  ></i>
                  出发
                </div>
                <div>
                  <i class="iconfont icon-dian1"
                     style="color: #ff8f00"
                  ></i>
                  目的地
                </div>
              </div>
          </div>
        <!--<five-star @starNum="starNum"></five-star>-->
      </div>
</template>

<script>
  import fiveStar from '@/views/comp/fiveStar'
    export default {
        name: "",
        components:{
          fiveStar
        },
        methods:{
          starNum(val){
            alert(val)
          }
        }
    }
</script>

<style scoped>

</style>
