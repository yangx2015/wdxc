<style lang="less">
    @import "../../../../styles/common.less";
    .VehicleMonitoringTiT{
    	width: 225px;
    	background-color: #fff;
    	border-radius: 5px 5px 0 0 ;
    	padding-left: 5px;
    	.boxTiT{
    		height:80px;
    		border-bottom:solid 5px #b3b3b3;
    		.cartypemess{
    			text-align: center;
    			/*line-height: 20px;*/
    			.cartypebox{
    				margin-top:12px;
    			}
    		}
    	}
    	.boxTiTnow{
    		background-color: #0000CC;
    		color: #fff;
    		height: 55px;
    	}
    	.carlistmess{
    		cursor: pointer;
    		padding: 8px 5px 4px 5px;
    		border-bottom: solid 2px #B3B3B3;
    	}
    }
    .sugges{
    	.ivu-card-body{
    		padding: 0!important;
    	}
    	.carlines{
			background-color: #fff;
			padding-top: 80px;
			margin: 0 15px;
			height: 100%;
			overflow: auto;
		}
    }
</style>
<template>
	<div class="box sugges">
		<div class="body height-50">
			<div class="box-row height-100">
				<div class="body-r-4 height-100 padding-5px" style="">
					<div style="height:100%;">
						<Card>
					        <p slot="title">
					            <Icon type="ios-film-outline"></Icon>
					            地图实时监控
					        </p>
						    <div :style="mapheight">
								<my-map :center='mapCenter'></my-map>
							</div>
					    </Card>
			    	</div>
				</div>
				<div class="body-r-5 padding-5px" style="background-color: #fff;width: 55.56%;height: 100%;">
					<div class="box">
						<div>
					    	<Menu mode="horizontal" theme="light" active-name="1">
						        <MenuItem name="1">
						            <!--<Icon type="ios-paper"></Icon>-->
					            		一号线路
						        </MenuItem>
						        <MenuItem name="2">
						            <!--<Icon type="ios-people"></Icon>-->
						            二号线路
						        </MenuItem>
						    </Menu>
						</div>
						<div class="body">
						    <div class="carlines">
						    	<div class="box-row-z">
						    		<div v-for="(item,index) in XBline">
						    			<carline 
						    				:zd="item.entryCount!=0"
						    				:linecar='item.exportCount!=0'
						    				:siteName="item.zdName"
						    				:lineShow="!(index==XBline.length-1)"></carline>
						    		</div>
						  		</div>
					    	</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="body height-50">
			<div class="box-row height-100">
				<div class="body-r-5 height-100 padding-5px" style="width: 55.55%;background-color: #fff;">
					<div class="box">
						<div class="body">
						    <div class="" style="height: 100%;overflow: auto;">
						    	<div class="box-row-z">
						    		<div v-for="i in [,,,,,,,,,,,,,,,,,,,]">
						    			<div style="width: 300px;">
						    				adfdf-------------------------------
						    			</div>
						    		</div>
						  		</div>
					    	</div>
						</div>
					</div>
					<!--<div class="box" style="height:100%;background-color: #00A854;">
						<div class="body">
						    <div class="" style="overflow: auto;height: 100%;">
						    	<div class="box-row-z">
						    		<div v-for="i in [,,,,,,,,,,,,,,,,,,,]">
						    			adfdf-------------------------------
						    		</div>
						    	</div>
						    </div>
						</div>-->
						<!--<Card>
					        <p slot="title">
					            <Icon type="ios-film-outline"></Icon>
					            时速监控
					        </p>
					        <div class="box" :style="SSjk">
					        	<div class="body" style="height: 100%;width: 100%;background-color: #000000;">
					        		<div class="box-row-z">
					        			<div style="width: 300px;" v-for="i in [,,,,,,,]">
					        				werwer
					        			</div>
					        		</div>
					        	</div>
					        </div>-->
					        <!--<div style="padding: 3px 0;">
					        	<Row style="text-align: center;">
									<Col span="8">
										鄂A12345
									</Col>
									<Col span="8">
										鄂A12345
									</Col>
									<Col span="8">
										鄂A12345
									</Col>
								</Row>
					        </div>
					        <div :style="carheight">
								<Row style="height: 100%;text-align: center;">
									<Col span="8" style="height: 100%;">
									        <gauge Eid="Eid1"></gauge>
									</Col>
									<Col span="8" style="height: 100%;">
										<gauge Eid="Eid2"></gauge>
									</Col>
									<Col span="8" style="height: 100%;">
										<gauge Eid="Eid3"></gauge>
									</Col>
								</Row>
					        </div>-->
					    <!--</Card>-->
			    	<!--</div>-->
				</div>
				<div class="body-r-4 height-100 padding-5px" style="">
					<div style="height:100%;">
						<Card>
					        <p slot="title">
					            <Icon type="ios-film-outline"></Icon>
					            	异常行驶记录
					        </p>
						    <div :style="mapheight">
						    	<abnor :tabmess=tabmess></abnor>
							</div>
					    </Card>
			    	</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>

import myMap from '../../map/schoolmap.vue'
import gauge from './comp/gauge.vue'
import abnor from './comp/abnormal.vue'
import carline from './comp/travelist.vue'

import configApi from '@/axios/config.js'
import mixins from '@/mixins'
export default {
    name: 'VehicleMonitoring',
    components: {
    	myMap,gauge,abnor,carline
    },
    mixins: [mixins],
    data () {
        return {
        	mapCenter:{lng: 114.372443, lat: 30.544572},
        	carheight:{
        		height:''
        	},
        	SSjk:{
        		height:''
        	},
        	mapheight:{
        		height:''
        	},
        	XBline:[],
        	tabmess:[]
        };
    },
    computed: {
		GetscoketMess() {
			return this.$store.state.app.socketMess
		}
	},
	watch: {
		GetscoketMess: function(newQuestion, oldQuestion) {
			this.tabmess = newQuestion
//			this.getXBline()
		},
	},
    created(){
    	this.$store.commit('setCurrentPath', [{
            title: '首页',
        },{
            title: '运营监控',
        },{
            title: '校巴监控',
        }])
//      this.getXBline()
    },
     mounted(){
		this.getalert()
    },
    methods: {
    	getXBline(){
    		var v = this
    		this.$http.post(configApi.XBDT.QUERY,{"xlId":"1"}).then((res) =>{
				console.log('线路数据',res)
				v.XBline = res.result
			})
    	},
    	getalert(){
    		var windowHeight = window.innerHeight
    		this.carheight.height = (windowHeight/2 - 160)+'px'
    		this.SSjk.height = (windowHeight/2 - 120)+'px'
    		this.mapheight.height = (windowHeight/2 - 120)+'px'
        },
	    }
};
</script>
