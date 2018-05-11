<style lang="less">
  @import "../../styles/box.less";
  @import "./eval.less";
</style>
<template>
    <div class="box evaluate">
      <div class="boxTop">
          <div class="box-row">
            <div class="iconImg">
                <i class="iconfont icon-wo"
                ></i>
            </div>
            <div class="body-r-1 mess">
                <div>
                   周师傅
                </div>
                <div>
                    鄂A12345
                </div>
                <div>
                  车型
                </div>
            </div>
          </div>
      </div>
      <div class="boxCenter">
          <div class="box-row">
            <div class="body-r-1">
              <div class="tit">
                  里程
              </div>
              <div class="mess">
                  65公里
              </div>
            </div>
            <div class="body-r-1">
                <div class="tit">
                  金额
                </div>
                <div class="mess">
                  105元
                </div>
            </div>
          </div>
      </div>
      <div class="boxStar">
          <five-star @starNum="starNum" :num="4" :bj="true"></five-star>
      </div>
      <div class="button">
        <button @click="back()">
          提    交
        </button>
      </div>
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

          },
          back(){
            this.$router.push({
              name:'list'
            })
          }
      }
    }
</script>

<style scoped>

</style>
