<style lang="less">
    @import "../../../../styles/common.less";
    .VehicleMonitoringTiT{
    	width: 225px;
    	background-color: #fff;
    	border-radius: 5px 5px 0 0 ;
    	padding-left: 5px;
    	.boxTiT{
    		height:80px;
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
			padding-top: 20px;
			margin: 0 15px;
			height: 100%;
			overflow: auto;
		}
    }
    .SSJKsty{
    	overflow: auto;
    }
</style>
<template>
<div style="height: 100%;width: 100%;">
	<div class="box-row">
		<div class="body-r-5">
			<Card>
		        <p slot="title">
		            <Icon type="ios-film-outline"></Icon>
		            地图实时监控
		        </p>
			    <div :style="mapheight">
					<my-map></my-map>
				</div>
		    </Card>
		</div>
		<div class="body-r-4">
			<div class="box sugges">
				<div class="body"  style="background-color: #fff;">
					<div class="box">
						<div style="overflow: auto;">
							<div class="box-row-z padding-left-right">
								<Button class="margin-5"
									type="primary"
									v-for="(item,index) in XBlineName"
									@click="getXBline(item.id,item.xlmc)">{{item.xlmc}}</Button>
							</div>
						</div>
						<div style="border-top: solid 2px #BBBEC4;">
							 	当前线路:{{lineName}}

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
				<div class="body">
					<Card>
				        <p slot="title">
				            <Icon type="ios-film-outline"></Icon>
				            	异常行驶记录
				        </p>
				    	<abnor :tabmess=tabmess></abnor>
				    </Card>
				</div>
				
			</div>
		</div>
	</div>
	<div class="box sugges" v-if="false">
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
								<my-map></my-map>
							</div>
					    </Card>
			    	</div>
				</div>
				<div class="body-r-5 padding-5px" style="background-color: #fff;width: 55.56%;height: 100%;">
					<div class="box">
						<div style="overflow: auto;">
							<div class="box-row-z padding-left-right">
								<Button class="margin-5"
									type="primary"
									v-for="(item,index) in XBlineName"
									@click="getXBline(item.id,item.xlmc)">{{item.xlmc}}</Button>
							</div>
						</div>
						<div style="border-top: solid 2px #BBBEC4;">
							 	当前线路:{{lineName}}

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
				<div class="body-r-5 height-100 padding-5px" style="width: 55.55%;">
					<div class="box" style="height:100%;">
						<Card>
					        <p slot="title">
					            <Icon type="ios-film-outline"></Icon>
					            时速监控
					        </p>
					        <div class="SSJKsty" :style="carheight">
						    	<div class="box-row-z">
						    		<div v-for="(item,index) in tabmess">
						    			<div class="margin-top-5" style="width: 260px;height: 90%;">
						    				<gauge :Eid="index+'Eid1'" :CarSpeed="item"></gauge>
						    			</div>
						    		</div>
						  		</div>
					    	</div>
					    </Card>
			    	</div>
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
        	carheight:{
        		height:''
        	},
        	SSjk:{
        		height:''
        	},
        	mapheight:{
        		height:''
        	},
        	lineName:'',
        	lineID:'',
        	XBlineName:[],
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
			this.getXBline(this.lineID,this,lineName)
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
        this.getXBlineName()
    },
     mounted(){
		this.getalert()
    },
    methods: {
    	getXBlineName(){//校巴线路名称
    		var v = this
    		this.$http.post(configApi.XL.QUERY).then((res) =>{
				v.XBlineName = res.page.list
				v.getXBline(res.page.list[0].id,res.page.list[0].xlmc)
			})
    	},
    	getXBline(id,name){//校巴线路
    		var v = this
    		this.$http.post(configApi.XBDT.QUERY,{"xlId":id}).then((res) =>{
				v.XBline = res.result
			})
    		v.lineName = name
    		v.lineID = id
    	},
    	getalert(){
    		var windowHeight = window.innerHeight
    		this.carheight.height = (windowHeight/2 - 120)+'px'
    		this.SSjk.height = (windowHeight/2 - 120)+'px'
//  		this.mapheight.height = (windowHeight/2 - 120)+'px'
    		this.mapheight.height = (windowHeight - 210)+'px'
        },
	    }
};
</script>
