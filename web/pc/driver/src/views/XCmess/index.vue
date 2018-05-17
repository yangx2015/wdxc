<style lang="less">
  @import "../../styles/common.less";
  .linemess{
    background-size: 100%;
    background-repeat:no-repeat;
    .titBox{
      /*height: 142px;*/
      background: rgba(255,255,255,1);
      margin: 14px;
      .ck{
        border-bottom: 2px solid #C8C8C8;
        height: 71px;
        .iconImg{
            img{
              margin: 8px 8px;
            }
        }
        .ckName{
          font-size: 18px;
          line-height: 71px;
        }
        .ckPhone{
          line-height: 50px;
          .phoneBorder{
            border-left: 2px solid #fdc087;
            height: 31px;
            width: 60px;
            margin: 20px 0;
            text-align: center;;
          }
          i{
            /*transform: rotate(180deg);*/
          }
        }
      }
      .line{
        font-size: 18px;
        padding:10px 0 10px 25px;
      }
    }
    /*行程详细*/
    .xcxx{
        padding: 8px 14px;
    }
    /*行程确认*/
    .xcqr{
      padding: 1rem;
    }
  }
</style>
<template>
    <div class="box linemess" :style="{backgroundImage:'url(http://api.map.baidu.com/staticimage/v2?ak=evDHwrRoILvlkrvaZEFiGp30&width=375&height=667&center=武汉&markers='+mess.mdd+'|'+mess.hcdz+'&zoom=10&markerStyles=-1,http://47.98.39.45:9092/icon/map_line_begin.png|-1,http://47.98.39.45:9092/icon/map_line_end.png)'}">
        <div class="titBox">
          <div class="box-row ck">
              <div class="iconImg">
                <img src="/static/tou.jpg" alt="">
              </div>
              <div class="body-O ckName">
                {{mess.ck}}
              </div>
              <div class="ckPhone">
                <div  class="phoneBorder">
                  <Icon type="ios-telephone" size="28" color="#fdc087"></Icon>
                </div>
              </div>
          </div>
          <div class="line">
            <div class="hcdz">
              <Icon type="ios-location" color="#15b740"></Icon>
              {{mess.hcdz}}
            </div>
            <div class="mdd">
              <Icon type="ios-location" color="#ff9b00"></Icon>
              {{mess.mdd}}
            </div>
          </div>
        </div>
        <div class="body">

        </div>
        <div class="xcxx" v-show="mess.ddzt==20||mess.ddzt==30">
            <div class="box-row"
                 style="text-align: center;height: 75p;background: #fff">
              <div class="body-O">
                <div>
                  里程:
                </div>
                <div>
                  {{mess.lc | text}}公里
                </div>
              </div>
              <div class="body-O">
                <div>
                  单价:
                </div>
                <div>
                  {{mess.dj | text}}元/公里
                </div>
              </div>
              <div mess="body-O">
                <div>
                  过桥费:
                </div>
                <div>
                  {{mess.gqf | text}}元
                </div>
              </div>
              <div class="body-O">
                <div>
                  路停费:
                </div>
                <div>
                  {{mess.glf | text}}元
                </div>
              </div>
            </div>
        </div>
        <div class="xcqr" v-show="mess.ddzt!=20&&mess.ddzt!=30">
          <div class="box-row">
              <div class="body-O" style="margin: 0 8px">
                 <Button long @click="center">首页</Button>
              </div>
              <div class="body-O" style="margin: 0 8px">
                <Button type="success" long @click="lineOk">行程确认</Button>
              </div>
          </div>
        </div>
        <div class="xcqr" v-show="mess.ddzt==20||mess.ddzt==30">
          <Button type="success" long @click="center">首页</Button>
        </div>
    </div>
</template>

<script>
    export default {
        name: "index",
        data(){
          return{
            mess:this.$store.state.app.lineData,
          }
        },
        filters: {
          text: function (value) {
            if (!value) return '***'
            else return value
          }
        },
      created(){
        if(this.$store.state.app.user==null){
          this.$router.push({
            name:'login'
          })
        }
      },
        methods:{
          center(){
            this.$router.push({
              name:'center'
            })
          },
          lineOk(){
            this.$router.push({
              name:'okModal'
            })
          }
        }
    }
</script>

<style scoped>

</style>
