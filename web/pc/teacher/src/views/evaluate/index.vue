<style lang="less">
  @import "../../styles/box.less";
  @import "./eval.less";
</style>
<template>
    <div class="box evaluate" :style="{backgroundImage:'url(http://api.map.baidu.com/staticimage/v2?ak=evDHwrRoILvlkrvaZEFiGp30&width=375&height=667&center=武汉&markers='+mess.mdd+'|'+mess.hcdz+'&zoom=10&markerStyles=-1,http://47.98.39.45:9092/icon/map_line_begin.png|-1,http://47.98.39.45:9092/icon/map_line_end.png)'}">
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
                        {{mess.sjxm}}
                      </div>
                      <div>
                        {{mess.zws}}座位
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
                    {{mess.cph}}
                  </div>
                  <div>
                    {{mess.scs}}.{{mess.xh}}
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
                  {{mess.lc}}
                  公里
                </div>
              </div>
              <div class="body-r-1">
                <div class="lm">
                  金额
                </div>
                <div class="mess">
                  {{mess.zj}}
                  元
                </div>
              </div>
            </div>
          </div>
          <div class="boxStar">
            <five-star @starNum="starNum" :num="pf" :bj="bj"></five-star>
          </div>
          <div class="button" style=" overflow: hidden">
            <button style="float: left;width: 45%" @click="close()">
              关闭
            </button>
            <button v-if="pfzt" style="float: right;width: 45%" @click="save()">
              提    交
            </button>
            <button v-else style="float: right;width: 45%;background-color: #d9d9d9">
              提    交
            </button>
          </div>
      </div>
    </div>
</template>

<script>
  import {Toast} from 'mand-mobile'

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
            pfzt:false,
            bj:true
          }
        },
        created(){
          if(this.cok.get('result')) {

          }else{
            this.$router.push({
              name:'login'
            })
          };

          if(this.mess.pjdj==""){
            this.bj = true
          }else {
            this.bj = false
            this.pf = this.mess.pjdj
          }
          console.log(this.mess)
        },
        methods:{
          starNum(val){
            this.pf = val
            if(val!=0){
              this.pfzt = true
            }
          },
          close(){
            var v = this
            this.$router.back()
          },
          save(){
            var v = this
            v.$http.post(this.apis.PJDD.QUERTY,{'orderId':v.mess.id,'grade':v.pf}).then((res) =>{
              if (res.code==200) {
                v.tsi('评价成功')
              }
            }).catch((error)=>{

            })

          },
          tsi(mes){//表单验证提示
            var v=this
            Toast.info(mes)
            setTimeout(function () {
              Toast.hide()
              v.close()
            },1800)
          },

      }
    }
</script>

<style scoped>

</style>
