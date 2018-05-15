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
                   {{mess.sjxm}}
                </div>
                <div>
                  {{mess.cph}}
                </div>
                <div>
                  座位数：{{mess.zws}}
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
                  {{mess.lc}}公里
              </div>
            </div>
            <div class="body-r-1">
                <div class="tit">
                  金额
                </div>
                <div class="mess">
                  {{zj}}元
                </div>
            </div>
          </div>
      </div>
      <div class="boxStar">
          <five-star @starNum="starNum" :num="4" :bj="true"></five-star>
      </div>
      <div class="button" style=" overflow: hidden">
        <button style="float: left;width: 45%" @click="close()">
          取消
        </button>
        <button style="float: right;width: 45%" @click="back()">
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
        data(){
          return{
            mess:this.$store.state.app.pjMess
          }
        },
        created(){
          console.log(this.mess)
        },
        methods:{
          starNum(val){

          },
          close(){
            this.$router.back()
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
