<style lang="less">
	@import "../../styles/common.less";
	@import "./pageIndex";
</style>
<template>
	<div class="box">
		<div class="header">
			<div class="box-row">
				<!--<div class="titLeft" @click="MyCenter()">-->
					<!--<Icon type="person" color="#949494" size='26'></Icon>-->
				<!--</div>-->
				<div class="titCenter body-O" style="text-align: center;">
            <h2 style="color: #929292;">
              武汉大学校巴班车线路查询
            </h2>
					 <!--<Input v-model="titFind" size="small" placeholder="请输入站点名称" ></Input>-->
				</div>
				<!--<div class="titRight" @click="feedback()">-->
					<!--<Icon type="ios-compose" color="#949494" size='26'></Icon>-->
				<!--</div>-->
			</div>
		</div>
		<div>
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
              <!--<mu-tab>附近站点</mu-tab>-->
              <mu-tab>校园巴士</mu-tab>
              <mu-tab>班车线路</mu-tab>
            </mu-tabs>
            <!--<div class="demo-text" v-if="active1 === 0">-->
              <!--<div style="height:350px;font-weight: 700;font-size: 24px;color: #7f7f7f">-->
                <!--<mu-row style="line-height:170px;">-->
                  <!--<mu-col>附近无站点</mu-col>-->
                <!--</mu-row>-->
              <!--</div>-->
            <!--</div>-->
            <div class="demo-text" v-if="active1 === 0">
              <mu-container>
                <mu-expansion-panel
                  :expand="panel === 'panel1'"
                  @change="toggle('panel1')"
                  v-for="item in StationMess.scheduledBusRouters"
                  @click="lineMess(item.id)">
                  <div slot="header" style="font-weight: 600">
                    {{item.xlmc}}
                  </div>
                  <div>
                    <mu-row>
                      <mu-col align="left"><div>
                        <mu-icon size="13" value="departure_board" color="green" style="padding-top: 2px"></mu-icon>
                        开往 -{{item.endStation.mc}}
                      </div></mu-col>
                      <mu-col align="right"><div>2站</div></mu-col>
                    </mu-row>
                    <mu-row>
                      <mu-col align="left"><div>
                        <mu-icon size="13" value="departure_board" color="red"></mu-icon>
                        开往 - 珞珈山南路
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
                <mu-expansion-panel :expand="panel === 'panel1'" @change="toggle('panel1')">
                  <div slot="header" style="font-weight: 600">
                    武汉大学班车
                  </div>
                  <div>
                    <mu-row>
                      <mu-col align="left"><div><mu-icon size="13" value="departure_board" color="green" style="padding-top: 2px"></mu-icon>
                        开往 - 新校区
                      </div></mu-col>
                      <mu-col align="right"><div>2站</div></mu-col>
                    </mu-row>
                    <mu-row>
                      <mu-col align="left"><div><mu-icon size="13" value="departure_board" color="red" style="padding-top: 2px"></mu-icon>
                        开往 - 老校区
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
				<div class="body">
					<div class="list">
						<div class="listTiT box-row"
							v-for="(item,index) in lineList"
							@click="lineMess(item.id)">
							<div class="body-O">
								<i class="iconfont icon-003lubiao"></i>
								{{item.xlmc}}
							</div>
							<div>
								<i class="iconfont icon-right"></i>
							</div>
						</div>
					</div>
					<div class="list" v-show="false">
						<div class="listTiT box-row">
							<div class="body-O">
								<i class="iconfont icon-003lubiao"></i>
								校巴一号站点
							</div>
							<div>
								<i class="iconfont icon-right"></i>
							</div>
						</div>
						<ul>
							<li v-for="item in [,,,,,,]">
								<div class="carNum">
									202
								</div>
								<div class="box-row line lineTop" @click="lineMess">
									<div class="body-O">
										<i class="iconfont icon-jiantouarrow591"></i>
										开往校图书馆
									</div>
									<div>
										<i class="iconfont icon-xinhao"></i>2站
									</div>
								</div>
								<div class="box-row line" @click="lineMess">
									<div class="body-O">
										<i class="iconfont icon-jiantouarrow591"></i>
										第一食堂
									</div>
									<div>
										<i class="iconfont icon-xinhao"></i>4站
									</div>
								</div>
							</li>
						</ul>
					</div>
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
		  if(this.barNum == 0){
			  this.getLineMess(30)
      }else if(this.barNum == 1){
        this.getLineMess(20)
      }
			this.getSwiperMess();
      this.getGps();
		},
		methods:{
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
