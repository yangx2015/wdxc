<style lang="less">
  @import "../../styles/box.less";
  @import "./list.less";
</style>
<template>
      <div class="pagelist box" style="font-size: 16px">
          <div class="header" >
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
          <div class="body DDlist" style="background-color: #e8e8e8">
              <div class="DDitem"
                   v-for="item in mesliat">
                  <div class="tit box-row" v-if="item.sjxm!=''">
                        <div class="body-O" style="color: #3b3b3b;font-weight: 600;font-size: 14px;">
                          {{item.sjxm}}-{{item.cph}}
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
                  </div>
                  <div style="margin: 8px 0;">
                    <i class="iconfont icon-shijian"
                        style="font-size: 14px"
                    ></i>
                    <span style="font-size: 14px;font-weight: 600">
                    {{item.yysj}}
                    </span>
                  </div>
                  <div style="margin: 6px 0">
                    <i class="iconfont icon-dian1"
                        style="color: #00a854;font-size: 14px"
                    ></i>
                    <span style="font-size: 14px">
                      {{item.hcdz}}
                    </span>
                  </div>
                  <div>
                    <i class="iconfont icon-dian1"
                       style="color: #ff8f00;font-size: 14px"
                    ></i>
                    <span style="font-size: 14px">
                      {{item.mdd}}
                    </span>
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
            this.$store.commit('pjMessCh',item)
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
