<template>
  <div class="box_col">
    <mt-header style="height: 40px" fixed title="首页">
      <!--<router-link to="/" slot="left">-->
      <!--<mt-button icon="back">返回</mt-button>-->
      <!--<mt-button @click="handleClose">关闭</mt-button>-->
      <!--</router-link>-->
      <mt-button icon="more" slot="right">建议</mt-button>
    </mt-header>
    <div style="height: 200px;padding-top: 35px">
      <mt-swipe :auto="4000">
        <mt-swipe-item style="background-color: #a8111e">
          <image v-for="item in swiperImg" src= "item.img" style="width: 100%;height: 100%;"></image>
        </mt-swipe-item>
      </mt-swipe>
    </div>
    <div class="box_col_auto">
      <div>
        <mt-navbar v-model="selected">
          <mt-tab-item id="1"><div @click="tabId='1'" style="font-size: 18px">附近站点</div></mt-tab-item>
          <mt-tab-item id="2"><div @click="tabId='2'" style="font-size: 18px">校园巴士</div></mt-tab-item>
          <mt-tab-item id="3"><div @click="tabId='3'" style="font-size: 18px">班车线路</div></mt-tab-item>
        </mt-navbar>
            <mt-tab-container v-model="selected">  
              <mt-tab-container-item id="1">  
                <!--<div  v-if="StationMess.nearbyStations && StationMess.nearbyStations.length==0" class="fjwzd">-->
                  <!--<mt-cell :title="'附近暂无站点'" />  -->
                <!--</div>-->
                <!--<div v-else>-->
                  <!--<mt-cell v-for="item in StationMess.nearbyStations" :title="item.name" @click="goxlmess(item)" />  -->
                <!--</div>-->
                  <div class="box_col_auto">
                    <div class="ListItemSty">
                      <div class="stationRouterBox">
                        <div class="errmess" v-if="StationMess.nearbyStations && StationMess.nearbyStations.length==0">
                          附近暂无站点
                        </div>
                        <div v-else class="stationRouterItem" v-for="item in StationMess.nearbyStations" @click="goxlmess(item)" >
                          <div class="xlName">
                            <!--<icon class="Theme2" type="bus2" size="18"></icon>-->
                            {{item.name}}
                          </div>
                          <div class="toCode">
                            <!--<icon class="Theme2" type="jiantouarrow591" size="14"></icon>-->
                            开往 - {{item.endStation.mc}}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </mt-tab-container-item>  
              <mt-tab-container-item id="2">  
                <!--<div  v-if="StationMess.scheduledBusRouters && StationMess.scheduledBusRouters.length==0" class="fjwzd">-->
                  <!--<mt-cell :title="'暂无数据'" />  -->
                <!--</div>-->
          <!--       <div v-else v-for="item in StationMess.scheduledBusRouters" @click="goxlmess(item)">-->
                  <!--<mt-cell :title="item.name" /> -->
                <!--</div>-->
                  <div class="box_col_auto">
                    <div class="ListItemSty">
                      <div class="stationRouterBox">
                        <div class="errmess" v-if="StationMess.scheduledBusRouters && StationMess.scheduledBusRouters.length==0">
                          暂无数据
                        </div>
                        <div v-else class="stationRouterItem" v-for="item in StationMess.scheduledBusRouters" @click="goxlmess(item)" >
                          <div class="xlName">
                            <!--<icon class="Theme2" type="bus2" size="18"></icon>-->
                            {{item.name}}
                          </div>
                          <div class="toCode">
                            <!--<icon class="Theme2" type="jiantouarrow591" size="14"></icon>-->
                            开往 - {{item.endStation.mc}}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </mt-tab-container-item>  
              <mt-tab-container-item id="3">  
                <!--<div  v-if="StationMess.otherRouters && StationMess.otherRouters.length==0" class="fjwzd">-->
                  <!--<mt-cell :title="'暂无数据'" />  -->
                <!--</div>-->
          <!--       <div v-else @click="goxlmess(item)" v-for="item in StationMess.otherRouters">-->
                  <!--<mt-cell  :title="item.name" />  -->
                <!--</div>-->
                  <div class="box_col_auto">
                    <div class="ListItemSty">
                      <div class="stationRouterBox">
                        <div class="errmess" v-if="StationMess.otherRouters && StationMess.otherRouters.length==0">
                          暂无数据
                        </div>
                        <div v-else class="stationRouterItem" v-for="item in StationMess.otherRouters" @click="goxlmess(item)" >
                          <div class="xlName">
                            <!--<icon class="Theme2" type="bus2" size="18"></icon>-->
                            {{item.name}}
                          </div>
                          <div class="toCode">
                            <!--<icon class="Theme2" type="jiantouarrow591" size="14"></icon>-->
                            开往 - {{item.endStation.mc}}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </mt-tab-container-item>  
            </mt-tab-container>  
      </div>
    </div>
  </div>
</template>

<script>
  import $ from 'jQuery'
    export default {
      name: "index",
      data(){
        return{
          selected:'1',
          swiperImg: [],//轮播图
          StationMess:{},//站点信息
          GPSMESS:{},
        }
      },
      created(){
        this.getSwiperImg()
        this.getGps();
      },
      mounted() {

      },
      methods:{
        goxlmess(item){
          console.log('123',item);
          this.$router.push({name:'xlMess',params:{StationMess:item}})
          // this.$store.commit('setxlMess',item)
        },
        getStationMess(GPS){
          console.log(GPS)
          this.$http.post(this.apis.STATIONCODE,{lng:GPS.lng,lat:GPS.lat}).then((res)=>{
              console.log('站点信息',res);
              if(res.code == 200){
                this.StationMess = res.result
              }
          })
        },
        getGps(){
          var v =this
          var geolocation = new BMap.Geolocation();
          geolocation.getCurrentPosition(function(r){
            console.log(r);
            if(r.point){
              // alert('您的位置：'+r.point.lng+','+r.point.lat);
              // v.getGpsname(r.point.lng,r.point.lat);
              v.getStationMess(r.point)
              console.log(123);
            }
            else {
              alert('failed'+this.getStatus());
            }
          });
        },
        getGpsname(lat,lng){
          var myGeo = new BMap.Geocoder();
          // 根据坐标得到地址描述
          myGeo.getLocation(new BMap.Point(lat, lng), function(result){
            if (result){
              console.log(result);
              // alert(result.address);
            }
          });
        },
        getSwiperImg(){
          var v = this
          this.$http.post(this.apis.SWIPER,{}).then((res)=>{
            if(res.code == 200){
              res.result.forEach(element => {
                console.log("11111111",element.img);
                element.img = "http://47.98.39.45:9090/staticpath/" + element.img;
                console.log("22222",element.img);
              });
              this.swiperImg = res.result
            }
          })
        },
      },
    }
</script>

<style lang="less">
  *{
    margin: 0;
    padding: 0;
  }
.fjwzd{
  font-size: 20px;
  font-weight: 700;
  color: #747776;
}
  .stationRouterBox{
    background-color: #ffffff;
    .stationRouterItem{
      background-color: #ffffff;
      border-bottom:#f2f2f2 3px solid;
      padding: 10px 0;
      .xlName{
        font-size: 15px;
      }
      .toCode{
        color:#8a8a8a;
        font-size: 12px;
        padding-top: 15px;
      }
    }
  }
  .errmess{
    text-align: center;
    line-height: 200px;
    font-size: 20px;
    font-weight: 700;
    color: #bbb;
  }
  .ListItemSty{
    padding: 15px 20px;
    .stationName {
      font-size: 32px;
      font-weight: 700;
      border-bottom: #f2f2f2 3px solid;

      .distanceSty {
        font-size: 26px;
        color: #ffffff;
      }
    }
  }
</style>
