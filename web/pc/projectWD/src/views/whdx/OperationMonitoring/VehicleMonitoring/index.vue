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
		<component :is="componentName"></component>
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
					<Row>
						<Input type="text" v-model="keyword" placeholder="请填写车牌号码..." @input="filter"></Input>
					</Row>
					<div class="carlistmess" v-for="(item,index) in rightCarList" @click="rowClick(item)">
						<div>
							<span>{{item.zdbh}}</span>
							<span style="float: right;">{{item.cph}}</span>
						</div>
						<div style="overflow: hidden;">
							<span >{{item.text}}</span>
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
            componentName:'',
            rightCarList:[],
            mapCarList:[],
            carArray:[[],[],[]],
			status:0,
            choosedCar:null,
			keyword:'',
			allCarList:[
                {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'上线时间', status:0, bdjd:30.608123, bdwd:114.27226},
                {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'上线时间', status:0, bdjd:114.157277 ,bdwd:30.544446},
                {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'上线时间', status:0, bdjd:114.418288, bdwd:30.526529},
                {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, bdjd:114.321703, bdwd:30.477739},
                {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, bdjd:114.418288, bdwd:30.526529},
				{zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, bdjd:114.157277 ,bdwd:30.544446},
                {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'离线时间', status:1, bdjd:114.27226, bdwd:30.608123},
				{zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'离线时间', status:2, bdjd:114.157277 ,bdwd:30.544446},
				{zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'离线时间', status:2, bdjd:114.418288, bdwd: 30.526529}
			],
        };
    },
    computed: {
        GetsocketAllCar() {
            return this.$store.state.app.socketAllCar
        }
    },
    watch: {
        GetsocketAllCar: function(newQuestion, oldQuestion) {
            this.onGpsInfo(newQuestion);
        },
    },
    created(){
        this.$store.commit('setCurrentPath', [{
            title: '首页',
        },{
            title: '运营监控',
        },{
            title: '车辆监控',
        }])
		// this.init();
        this.initGps()
    },
    methods: {
        onGpsInfo(m){
            console.log('m:',m);
            let has = false;
            for(let r of this.allCarList){
			    if (r.clid = m.clid){
                    has = true;
                    r = m;
                    this.handleItem(r);
                    // this.allCarList.push(r);
                    this.classify();
                    if (this.choosedCar){
                        this.mapCarList = [this.choosedCar];
                    }else{
                        this.mapCarList = this.carArray[this.status];
                    }
                    this.rightCarList = this.carArray[this.status];
                    this.$refs.map.init();
                    return;
                }
			}
            console.log('has ',has);
            if (!has){
                this.handleItem(m);
                this.allCarList.push(m);
                this.classify();
                if (this.choosedCar){
                    this.mapCarList = [this.choosedCar];
                }else{
                    this.mapCarList = this.carArray[this.status];
                }
                this.rightCarList = this.carArray[this.status];
                this.$refs.map.init();
			}
		},
        init(){
            this.classify();
            this.mapCarList = this.carArray[0];
            this.rightCarList = this.carArray[0];
		},
        filter(){
            this.classify();
            this.rightCarList = this.carArray[this.status];
            this.mapCarList = this.carArray[this.status];
            this.$refs.map.init();
		},
		initGps(){
            var v = this
            this.$http.get(configApi.CLJK.QUERY).then((res) =>{
                if (res.code === 200){
                    this.allCarList = res.result;
                    for(let r of this.allCarList){
                        this.handleItem(r);
                    }
				}
                this.init();
            })
		},
        showPath(){
            this.$refs.map.showPath();
        },
        changeStatus(status){
            this.choosedCar = null;
            this.status = status;
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
                if (this.keyword === '' || r.cph.indexOf(this.keyword) > 0){
                    this.carArray[status].push(r);
				}
			}
		},
		handleItem(item){
            item.lng = item.bdjd;
            item.lat = item.bdwd;
            switch(item.eventType){
				case '60':
                    item.status = 1;
                    item.text = '熄火时间';
                    break;
				case '80':
                    item.status = 2;
                    item.text = '离线时间';
                    break;
                case '50':
				default:
                    item.status = 0;
                    item.text = '上线时间';
			}
		},
		rowClick(item){
            this.choosedCar = item;
            this.mapCarList = [this.choosedCar];
            this.$refs.map.init();
		}
    }
};
</script>
