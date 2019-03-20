<style lang="less">
	@import "../../styles/common.less";
	@import "./pageIndex";
</style>
<template>
	<div>
    <div class="titbar">
      <mu-appbar style="width: 100%;" color="#fb8c00" align="center">
        <mu-button @click="refalse" icon slot="left"><mu-icon size="26" value="rotate_right" color="#FFF"></mu-icon> </mu-button>
            主页
        <mu-menu slot="right">
          <mu-button flat @click="feedback">
            <mu-icon size="26" value="local_post_office" color="#FFF"></mu-icon>
          </mu-button>
        </mu-menu>
      </mu-appbar>
    </div>
		<div style="width: 100%;padding-top: 58px">
			<swiper
				:list="imglist"
				v-model="imglistV"
				:auto="true"
				:loop="true"
				:interval="2000"
				@on-index-change="demo01_onIndexChange"></swiper>
		</div>
		<div class="body bodylist" style="background-color: #fff;">
			<div class="box">
				<div class="">
					<!--<tab>-->
				      <!--<tab-item :selected="barNum==0" @on-item-click="onItemClick">校园巴士</tab-item>-->
				      <!--<tab-item :selected="barNum==1" @on-item-click="onItemClick">通勤巴士</tab-item>-->
				    <!--</tab>-->
          <mu-container>
            <mu-tabs :value.sync="active1" inverse color="secondary" text-color="rgba(0, 0, 0, .54)" full-width>
              <mu-tab>附近站点</mu-tab>
              <mu-tab>校园巴士</mu-tab>
              <mu-tab>班车线路</mu-tab>
            </mu-tabs>
            <div class="demo-text" v-if="active1 === 0">
              <div style="height:350px;font-weight: 700;font-size: 24px;color: #7f7f7f">
                <mu-row style="line-height:170px;">
                  <mu-col>附近无站点</mu-col>
                </mu-row>
              </div>
            </div>
            <div class="demo-text" v-if="active1 === 1">
              <mu-container>
                <mu-expansion-panel
                  v-for="(item,index) in StationMess.scheduledBusRouters"
                  :expand="panel === 'panel'+(index+1)"
                  @change="toggle('panel'+(index+1))"
                  >
                  <div slot="header" style="font-weight: 600">
                    {{item.name}}
                  </div>
                  <div @click="lineMess(item.id)">
                    <mu-row>
                      <mu-col align="left"><div>
                        <mu-icon size="13" value="departure_board" color="green" style="padding-top: 2px"></mu-icon>
                        开往 -{{item.endStation.mc}}
                      </div></mu-col>
                      <mu-col align="right"><div v-if="item.nextBus == ''">*站</div><div v-else>{{item.nextBus}}站</div></mu-col>
                    </mu-row>
                    <mu-row>
                      <mu-col align="left"><div>
                        <mu-icon size="13" value="departure_board" color="red"></mu-icon>
                        {{item.startTime}} - {{item.endTime}}
                      </div></mu-col>
                      <mu-col align="right"><div class="icon-container">
                        <mu-icon size="20" value="directions_bus" color="red"></mu-icon>
                      </div></mu-col>
                    </mu-row>
                  </div>
                </mu-expansion-panel>
              </mu-container>
            </div>
            <div class="demo-text" v-if="active1 === 2">
              <mu-container>
                <mu-expansion-panel v-for="(item,index) in StationMess.otherRouters" :expand="panel === 'panel'+(index+1)" @change="toggle('panel'+(index+1))">
                  <div slot="header" style="font-weight: 600">
                    {{item.name}}
                  </div>
                  <div @click="lineMess(item.id)">
                    <mu-row>
                      <mu-col align="left"><div><mu-icon size="13" value="departure_board" color="green" style="padding-top: 2px"></mu-icon>
                        开往 - {{item.endStation.mc}}
                      </div></mu-col>
                      <mu-col align="right" style="font-weight: 500"><div v-if="item.nextBus == ''">*站</div><div v-else>{{item.nextBus}}站</div></mu-col>
                    </mu-row>
                    <mu-row>
                      <mu-col span="9" align="left"><div><mu-icon size="13" value="departure_board" color="red" style="padding-top: 2px"></mu-icon>
                        {{item.startTime}} - {{item.endTime}}
                      </div></mu-col>
                      <mu-col align="right"><div class="icon-container">
                        <mu-icon size="20" value="directions_bus" color="red"></mu-icon>
                      </div></mu-col>
                    </mu-row>
                  </div>
                </mu-expansion-panel>
              </mu-container>
            </div>
          </mu-container>
				</div>
			</div>
		</div>
    <!--<component-->
      <!--:is="componentName"-->
      <!--:mess="choosedRow"></component>-->
	</div>
</template>

<script>
	import { Swiper, SwiperItem, Cell, XButton, Tab, TabItem } from 'vux'
	import configApi from '@/axios/config.js'
	export default{
		name:'',
		components: {
		    Swiper,
		    SwiperItem,
		    Cell,
		    XButton, Tab, TabItem
		 },
		data(){
			return{
        StationMess:{},//站点信息
        active1:1,
        panel: 'panel1',
				school:true,
				titFind:'',
				imglistV:1,
        barNum:this.$store.state.app.barNum,
				imglist: [],
				lineList:[]
			}
		},
		created(){
      this.jsBridge.postNotification("CLIENT_USER_LOCATION", {});
      this.jsBridge.bind('CLIENT_USER_LOCATION', function(object){
        console.log(object);
        alert('经纬度:',object.longitude+','+object.latitude)
      });

		  if(this.barNum == 0){
			  this.getLineMess(30)
      }else if(this.barNum == 1){
        this.getLineMess(20)
      }
			this.getSwiperMess();
      this.getGps();
		},
		methods:{
      getLocation(object){
        console.log(object);
        alert('经纬度:',object.longitude+','+object.latitude)
        var nr = JOSN.stringify(object)
        this.$http.post(this.apis.FEEDBACK.QUERTY,{nr:nr,yjlx:'00'}).then((res) =>{
          console.log('*****',res)
        })
      },
      refalse(){
        this.getSwiperMess();
        this.getGps();
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
        toggle(panel) {
          this.panel = panel === this.panel ? '' : panel;
        },
		  	MyCenter(){//个人中心
		  		this.$router.push({
		    		name:'myCenter'
		    	})
		  	},
		  	feedback(){//反馈信息
		  		this.$router.push({
		    		name:'feedback'
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
		  	getSwiperMess(){
		  		var v = this
		  		this.$http.post(configApi.SWIPER.QUERTY).then((res)=>{
		  			if(res.code ==200){
		  				console.log('图片数据',res)
//		  				v.$Message.success('This is a success tip');
		  				v.imglist = res.result
		  			}else{
		  				v.$Message.success('图片获取失败');
		  			}
		  		}).catch((error) =>{
	        		console.log('出错了',error)
	        	})
		  	},
		  	getLineMess(lx){
		  		var v = this
		  		this.$http.post(configApi.LINE.QUERTY,{'lx':lx}).then((res)=>{
		  			if(res.code ==200){
		  				console.log('线路数据',res)
//		  				v.$Message.success('This is a success tip');
		  				v.lineList = res.result
		  			}else{
		  				v.$Message.success('线路数据获取失败');
		  			}
		  		}).catch((error) =>{
	        		console.log('出错了',error)
	        	})
		  	},
		  	demo01_onIndexChange (index) {
//				console.log(index)
		    },
		    lineMess(id){
          this.$store.commit('ChlineID',id)
		    	this.$router.push({
            name:'lineMess'
          })
		    },
		    onItemClick(index){
		    	if(index==0){
            this.getLineMess(30)
		    	}else if(index==1){
            this.getLineMess(20)
		    	}
          this.$store.commit('barCh',index)
		    }
		}
	}
</script>

<style>
  .titbar {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
  }
  .demo-text {
    padding: 1px;
    background: #fff;
    p {
      margin: 8px 0;
    }
  }
</style>
