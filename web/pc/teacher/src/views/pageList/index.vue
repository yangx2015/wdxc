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
                   v-for="item in mesliat">
                  <div class="tit box-row" v-if="item.sjxm!=''">
                        <div class="body-O">
                          周师傅-鄂A00000
                        </div>
                        <div @click="fiveStar(item)">
                          已完成
                          <i class="iconfont icon-right"></i>
                        </div>
                  </div>
                  <div class="tit box-row" v-else>
                    <div class="body-O">
                      订单指派中……
                    </div>
                    <!--<div>-->
                      <!--已完成-->
                      <!--<i class="iconfont icon-right"></i>-->
                    <!--</div>-->
                  </div>
                  <div>
                    <i class="iconfont icon-shijian"></i>
                    {{item.yysj}}
                  </div>
                  <div>
                    <i class="iconfont icon-dian1"
                        style="color: #00a854"
                    ></i>
                    {{item.hcdz}}
                  </div>
                  <div>
                    <i class="iconfont icon-dian1"
                       style="color: #ff8f00"
                    ></i>
                    {{item.mdd}}
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
        data(){
          return{
            mesliat:[]
          }
        },
        created(){
          if (this.cok.get('result')) {

          }else{
            this.$router.push({
              name:'login'
            })
          }
          this.getList()
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
          },
          getList(){
            var v = this
            v.$http.post(this.apis.DDLIST.QUERTY).then((res) =>{
                if (res.code==200) {
                  this.mesliat = res.result
                }
            }).catch((error)=>{

            })
          }
        }
    }
</script>

<style scoped>

</style>
