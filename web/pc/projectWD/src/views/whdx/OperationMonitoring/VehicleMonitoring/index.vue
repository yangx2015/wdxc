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
    		<my-map ref="map"></my-map>
    	</div>
    	<div class="VehicleMonitoringTiT">
    		<div class="box">
    			<div class="boxTiT">
    				<Row :gutter='5' class="cartypemess">
    					<Col span="7">
    						<div class="cartypebox">
    							<Button type="success" @click="changeStatus(0)">
    								<div>{{carArray[0].length}}</div>
    								<div>启动</div>
    							</Button>
    						</div>
    					</Col>
    					<Col span="7">
    						<div class="cartypebox">
    							<Button type="warning" @click="changeStatus(1)">
    								<div>{{carArray[1].length}}</div>
    								<div>熄火</div>
    							</Button>
    						</div>
    					</Col>
    					<Col span="10">
    						<div class="cartypebox" @click="changeStatus(2)">
    							<Button>
    								<div>{{carArray[2].length}}</div>
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
					<div class="carlistmess" v-for="(item,index) in rightCarList" @click="rowClick(item)">
						<div>
							<span>{{item.zdbh}}</span>
							<span style="float: right;">{{item.cph}}</span>
						</div>
						<div style="overflow: hidden;">
							<span>{{item.text}}</span>
							<span style="float: right;">{{item.time}}</span>
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
    	myMap
    },
    data () {
        return {
            rightCarList:[],
            mapCarList:[],
            carArray:[[],[],[]],
			allCarList:[
                {zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'上线时间', status:0, lng:114.27226, lat:30.608123},
                {zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'上线时间', status:0, lng:114.157277 , lat:30.544446},
                {zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'上线时间', status:0, lng: 114.418288, lat: 30.526529},
                {zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, lng: 114.321703, lat: 30.477739},
                {zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, lng: 114.418288, lat: 30.526529},
				{zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, lng:114.157277 , lat:30.544446},
                {zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'离线时间', status:1, lng:114.27226, lat:30.608123},
				{zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'离线时间', status:2, lng:114.157277 , lat:30.544446},
				{zdbh:'asdzxc123456', cph:'鄂A12354', time:'2017-12-12 08:00:00', text:'离线时间', status:2, lng: 114.418288, lat: 30.526529}
			],
        };
    },
    created(){
    	this.$store.commit('setCurrentPath', [{
            title: '首页',
        },{
            title: '运营监控',
        },{
            title: '车辆监控',
        }])
        // this.getmess()
		this.classify();
        this.mapCarList = this.carArray[0];
        this.rightCarList = this.carArray[0];
    },
    methods: {
        changeStatus(status){
            this.rightCarList = this.carArray[status];
            this.mapCarList = this.carArray[status];
            this.$refs.map.init();
		},
		classify(){
            this.carArray[0] = [];
            this.carArray[1] = [];
            this.carArray[2] = [];
			for(let r of this.allCarList){
			    let status = r.status;
			    this.carArray[status].push(r);
			}
		},
    	getmess(){
			var v = this
			this.$http.get(configApi.CLJK.QUERY).then((res) =>{
					console.log('123123',res)
					v.flameout = res.result
					v.mapMess = v.mapCarList = v.carlaunch
			})
		},
		rowClick(item){
            this.mapCarList = [item];
            this.$refs.map.init();
		}
    }
};
</script>
