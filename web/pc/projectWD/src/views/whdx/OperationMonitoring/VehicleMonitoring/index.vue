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
    		<my-map :center='mapCenter'></my-map>
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
					<div class="carlistmess" v-for="(item,index) in carlist">
						<div>
							<span>
								{{item.deviceID}}
							</span>
							<span style="float: right;">
								{{item.carId}}
							</span>
						</div>
						<div>
							<span>
								{{item.text}}
							</span>
							<span>
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
export default {
    name: 'VehicleMonitoring',
    components: {
    	myMap,
    },
    data () {
        return {
        	mapCenter:{lng: 114.372443, lat: 30.544572},
        	carlist:[
        	],
        	//启动
        	carlaunch:[
        		{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'上线时间'
        		},
        		{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'上线时间'
        		},
        		{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'上线时间'
        		}
        	],
        	//熄火
        	flameout:[
				{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'熄火时间'
        		},
        		{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'熄火时间'
        		},
        		{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'熄火时间'
        		}
        	],
        	offline:[
        		{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'离线时间'
        		},
        		{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'离线时间'
        		},
        		{
        			deviceID:'asdzxc123456',
        			carId:'鄂A12354',
        			time:'2017-12-12 08:00:00',
        			text:'离线时间'
        		}
        	]
        };
    },
    computed: {
    },
    created(){
    	this.$store.commit('setCurrentPath', [{
            title: '首页',
        },{
            title: '运营监控',
        },{
            title: '车辆监控',
        }]),
        this.carlist = this.carlaunch
    },
    methods: {
    	carlaunchClick(){
    		this.carlist = this.carlaunch
    	},
    	flameoutClick(){
    		this.carlist = this.flameout
    	},
    	offlineClick(){
    		this.carlist = this.offline
    	}
    }
};
</script>
