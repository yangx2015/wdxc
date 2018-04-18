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
</style>
<template>
    <div class="box-row">
    	<div class="body-F" style="height:100%;">
    		<my-map :mapCarlist='mapMess'></my-map>
    	</div>
    	<div class="VehicleMonitoringTiT">
    		<div class="box">
    			<div class="boxTiT">
    				<Row :gutter='5' class="cartypemess">
    					<Col span="7">
    						<div class="cartypebox">
    							<Button type="success" @click="carlaunchClick">
    								<div>3</div>
    								<div>启动</div>
    							</Button>
    						</div>
    					</Col>
    					<Col span="7">
    						<div class="cartypebox">
    							<Button type="warning" @click="flameoutClick">
    								<div>3</div>
    								<div>熄火</div>
    							</Button>
    						</div>
    					</Col>
    					<Col span="10">
    						<div class="cartypebox" @click="offlineClick">
    							<Button>
    								<div>3</div>
    								<div>
    									<span style="font-size: 8px;">
    										离线30天
    									</span>
    								</div>
    							</Button>
    						</div>
    					</Col>
    				</Row>
    			</div>
    			<div class="body">
					<div class="carlistmess" v-for="(item,index) in carlist" @click="carClick(item)">
						<div>
							<span>
								{{item.zdbh}}
							</span>
							<span style="float: right;">
								{{item.cph}}
							</span>
						</div>
						<div style="overflow: hidden;">
							<span>
								{{item.text}}
							</span>
							<span style="float: right;">
								{{item.time}}
							</span>
						</div>
					</div>
    			</div>
    		</div>
    	</div>
    </div>
</template>

<script>

import myMap from '../../map/carJK.vue'
import configApi from '@/axios/config.js'
export default {
    name: 'VehicleMonitoring',
    components: {
    	myMap,
    },
    data () {
        return {
        	mapMess:[],//地图数据传输
        	carlist:[],
        	//启动
        	carlaunch:[
        	],
        	//熄火
        	flameout:[
        	],
        	offline:[
        	]
        };
    },
    computed: {
    	carListSco(){
			return this.$store.state.app.socketAllCar
    	}
    },
    watch:{
    	carListSco:function(newQuestion, oldQuestion){
    		this.carlaunch = newQuestion
    	}
    },
    created(){
    	this.$store.commit('setCurrentPath', [{
            title: '首页',
        },{
            title: '运营监控',
        },{
            title: '车辆监控',
        }]),
        this.getmess()
    },
    methods: {
    	carlaunchClick(){//启动
    		this.mapMess = this.carlist = this.carlaunch
    	},
    	flameoutClick(){
    		this.mapMess = this.carlist = this.flameout
    	},
    	offlineClick(){
    		this.mapMess = this.carlist = this.offline
    	},
    	getmess(){
			var v = this
			this.$http.get(configApi.CLJK.QUERY).then((res) =>{
					console.log('123123',res)
					v.flameout = res.result
					v.mapMess = v.carlist = v.carlaunch
			})
		},
		carClick(item){
			console.log(item)
			this.mapMess=[item]
		}
    }
};
</script>
