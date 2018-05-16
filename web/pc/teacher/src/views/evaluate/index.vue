<style lang="less">
  @import "../../styles/box.less";
  @import "./eval.less";
</style>
<template>
    <div class="box evaluate">
      <div class="body">
      </div>
      <div class="carMess">
          <div class="box-row tit">
            <div class="body-r-2 titBox">
              <div class="box-row xxxx">
                  <div class="body-O img">
                      <img src="./img/drive.png" style="width: 0.8rem">
                  </div>
                  <div class="driName">
                      <div>
                        张师傅
                      </div>
                      <div>
                        5座位
                      </div>
                  </div>
              </div>
            </div>
            <div class="body-r-4 titBox">
              <div class="box-row xxxx">
                <div class="body-O img">
                  <img src="./img/cart.jpg" style="width:1.8rem">
                </div>
                <div>
                  <div>
                    鄂A12345
                  </div>
                  <div>
                    5座位车
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="boxCenter">
            <div class="box-row">
              <div class="body-r-1">
                <div class="lm">
                  里程
                </div>
                <div class="mess">
                  <!--{{mess.lc}}-->99
                  公里
                </div>
              </div>
              <div class="body-r-1">
                <div class="lm">
                  金额
                </div>
                <div class="mess">
                  <!--{{zj}}-->1000
                  元
                </div>
              </div>
            </div>
          </div>
          <div class="boxStar">
            <five-star @starNum="starNum" :num="0" :bj="true"></five-star>
          </div>
          <div class="button" style=" overflow: hidden">
            <button style="float: left;width: 45%" @click="close()">
              关闭
            </button>
            <button v-if="pf!=0" style="float: right;width: 45%" @click="back()">
              提    交
            </button>
            <button v-else style="float: right;width: 45%;background-color: #d9d9d9">
              提    交
            </button>
          </div>
      </div>



      <div class="boxTop" v-if="false">
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
      <div class="boxCenter" v-if="false">
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
      <div class="boxStar"  v-if="false">
          <five-star @starNum="starNum" :num="pf" :bj="true"></five-star>
      </div>
      <div class="button" style=" overflow: hidden" v-if="false">
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
            mess:this.$store.state.app.pjMess,
            pf:0,
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
