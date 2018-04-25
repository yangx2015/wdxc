<style lang="less">
    @import "../../../../styles/common.less";
    .VehicleMonitoringTiT{
    	width: 225px;
    	background-color: #fff;
    	border-radius: 5px 5px 0 0 ;
    	padding-left: 5px;
    	.boxTiT{
    		.cartypemess{
    			text-align: center;
    			.cartypebox{
    				margin-top:12px;
    				margin-bottom:12px;
					margin-left: 16px;
					float: left;
    			}
    		}
    	}
    	.carlistmess{
			height: 214px;
    		cursor: pointer;
    		padding: 6px 16px 18px 16px;
    		border-bottom: solid 1px #919191;
    	}
    }
	.btn_obd{
		padding: 2px;
		border: 1px solid #5cadff;
		border-radius: 4px;
	}
	.top_btn{
		width: 76px;
		height: 76px;
		color:white;
		border-radius: 10px;
		font-size: 18px;
	}
	.search_input{
		background-color: #cccccc;
		border-radius: 10px;
		border:none;
		padding: 8px;
		font-size: 15pt;
		margin-left: 17px;
		appearance: none;
		-web-kit-appearance:none;
		-moz-appearance: none;
		outline:none;
		text-decoration:none;
	}
	._18pt{
		font-size: 18pt;
	}
	._16pt{
		font-size: 16pt;
	}
	._14pt{
		font-size: 14pt;
	}
	::-webkit-input-placeholder { /* WebKit browsers */
		color: #999;
	}
	:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
		color: #999;
	}
	::-moz-placeholder { /* Mozilla Firefox 19+ */
		color: #999;
	}
	:-ms-input-placeholder { /* Internet Explorer 10+ */
		color: #999;
	}
</style>
<template>
    <div class="box-row">
		<component :is="componentName"></component>
    	<div class="body-F" style="height:100%;">
    		<my-map ref="map"></my-map>
    	</div>
    	<div class="VehicleMonitoringTiT" style="width: 300px">
    		<div class="box">
    			<div class="boxTiT">
    				<div  class="cartypemess">
						<div class="cartypebox">
							<Button style="background-color: #6ebaff;" class="top_btn" @click="changeStatus(0)">
								<div>{{carArray[0].length}}</div>
								<div>启动</div>
							</Button>
						</div>
						<div class="cartypebox">
							<Button style="background-color: #ff9b87;" class="top_btn" @click="changeStatus(1)">
								<div>{{carArray[1].length}}</div>
								<div>熄火</div>
							</Button>
						</div>
						<div class="cartypebox">
							<Button style="background-color: #8190ff;" class="top_btn" @click="changeStatus(2)">
								<div>{{carArray[2].length}}</div>
								<div>离线</div>
							</Button>
						</div>
    				</div>
    			</div>
    			<div class="body">
					<div style="margin-top: 18px">
						<input type="text" class="search_input" v-model="keyword" placeholder="请填写车牌号码..." @input="filter"></input>
					</div>

					<div class="carlistmess" v-for="(item,index) in rightCarList">
						<div>
							<span class="_18pt">{{item.zdbh}}</span>
						</div>
						<div>
							<span class="_16pt">司机：</span>
							<span class="_16pt">{{item.sjxm ? item.sjxm : '未绑定司机'}}</span>
						</div>
						<div>
							<span class="_16pt">车牌号：</span>
							<span class="_16pt">{{item.cph}}</span>
						</div>
						<div>
							<span class="_16pt">行驶速度：</span>
							<span class="_16pt">{{item.speed ? item.speed : 0}} km/h</span>
						</div>
						<div style="margin-top: 18px">
							<span  class="_14pt" style="color: #919191">{{item.text}}</span><br>
							<span  class="_14pt" style="color: #919191">{{formateLongDate(item.time)}}</span>
						</div>
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
            SpinShow:false,
            componentName:'',
            rightCarList:[],
            mapCarList:[],
            carArray:[[],[],[]],
			status:0,
            choosedCar:null,
			keyword:'',
            obd:{},
			allCarList:[
                // {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'上线时间', status:0, bdjd:30.608123, bdwd:114.27226},
                // {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'上线时间', status:0, bdjd:114.157277 ,bdwd:30.544446},
                // {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'上线时间', status:0, bdjd:114.418288, bdwd:30.526529},
                // {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, bdjd:114.321703, bdwd:30.477739},
                // {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, bdjd:114.418288, bdwd:30.526529},
				// {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'熄火时间', status:1, bdjd:114.157277 ,bdwd:30.544446},
                // {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'离线时间', status:1, bdjd:114.27226, bdwd:30.608123},
				// {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'离线时间', status:2, bdjd:114.157277 ,bdwd:30.544446},
				// {zdbh:'asdzxc123456',cph:'鄂A12354',sjxm:'张三',speed:'100KM/h', time:'2017-12-12 08:00:00', text:'离线时间', status:2, bdjd:114.418288, bdwd: 30.526529}
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
        formateLongDate(long){
            if (long.indexOf('-')>0) return long;
          	let d = new Date(long);
          	return d.getFullYear()+'年'+d.getMonth()+'月'+d.getDate() + " "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds()
		},
        formatDate(date){
            return date.substring(0,4)+'年'+date.substring(4,6)+"月"+date.substring(6,8)+"日";
        },
        formatTime(time){
            return time.substring(0,2)+':'+time.substring(2,4)+":"+time.substring(4,6);
        },
        getObdInfo(item){
            console.log(item);
            this.SpinShow = true;
            var v = this
            this.obd = {};
            this.$http.post(configApi.CLJK.getObdTimely,{obdId:item.obdId}).then((res) =>{
                if (res.code === 200){
                    this.obd = res.result;
                }
                this.SpinShow = false;
            })
        },
        onGpsInfo(m){
            let has = false;
            let exist = null;
            for(let r of this.allCarList){
			    if (r.zdbh === m.zdbh){
                    exist = r;
                    break;
                }
			}
            this.handleItem(m);
            if (exist){
                let index  = this.allCarList.indexOf(exist);
                this.allCarList.splice(index,1,m);
			}else{
                this.allCarList.push(m);
			}
            this.classify();
            if (this.choosedCar){
                this.mapCarList = [this.choosedCar];
            }else{
                this.mapCarList = this.carArray[this.status];
            }
            this.rightCarList = this.carArray[this.status];
            this.$refs.map.update();
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
            console.log('initGps');
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
            if (item.zxzt){
                switch(item.zxzt){
                    case '10':
                        item.status = 1;
                        item.text = '熄火时间';
                        break;
                    case '20':
                        item.status = 2;
                        item.text = '离线时间';
                        break;
                    case '00':
                    default:
                        item.status = 0;
                        item.text = '更新时间';
                }
			}else{
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
                        item.text = '更新时间';
                }
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

