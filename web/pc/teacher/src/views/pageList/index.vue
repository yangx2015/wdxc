<style lang="less">
  @import "../../styles/box.less";
  @import "./list.less";
</style>
<template>
      <div class="pagelist box" style="font-size: 16px">
          <div class="header">
              <div class="box-row">
                <div class="titLeft" @click="back()">
                  <i class="iconfont icon-left"></i>
                </div>
                <div class="titCenter body-O" style="text-align: center;">
                      历史订单
                </div>
                <div class="titLeft">
                </div>
              </div>
          </div>
          <div class="body DDlist">
              <div class="DDitem"
                   @click="fiveStar(item)"
                   v-for="item in [,,,,,,,,,,,]">
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
      </div>
</template>

<script>

    export default {
        name: "",
        components:{
        },
        methods:{
          starNum(val){
            alert(val)
          },
          back(){
            this.$router.push({
              name:'Home'
            })
          },
          fiveStar(item){
            this.$router.push({
                name:'evaluate'
            })
          }
        }
    }
</script>

<style scoped>

</style>
