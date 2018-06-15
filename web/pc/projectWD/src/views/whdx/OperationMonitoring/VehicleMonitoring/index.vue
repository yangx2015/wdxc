<style lang="less">
    @import "../../../../styles/common.less";
</style>
<style>
	.choosed{
		background-color: #eee;
	}
</style>
<template>
    <div class="box-row">
		<div style="position:absolute;width:400px;padding-top:30px;padding-left:30px;z-index:9999">
			<Col span="24">
				<Input placeholder="查设备、找车辆、找司机" size="large" v-model="searchKey">
				<Button slot="append" type="primary" icon="ios-search" @click="filter"></Button>
				</Input>
				<Tabs v-if="showTabs" ref="tabRef" style="background-color:white;"  size="small">
					<TabPane :label="qblabel" name="name0" style="height:300px;overflow:auto;" v-show="tabShowFlag">
						<Row v-for="(item,index) in allCarList" @click.native="rowClick(item)">
							<Col span="24">
								<Card style="margin:0 15px 5px 15px;" :class="{'choosed':choosedCar == item}">
									<p slot="title">
										<Icon type="soup-can-outline"></Icon>
										{{item.zdbh}}
									</p>
									<p slot="extra" style="color:#19be6b">
										{{formateLongDate(item.time)}}
									</p>
									<Row  type="flex" justify="start">
										<Col span="8">
											<Icon type="model-s"></Icon>
											{{item.cph}}
										</Col>
										<Col span="8">
											<Icon type="person"></Icon>
											{{item.sjxm ? item.sjxm : '暂无绑定'}}
										</Col>
										<Col span="2" offset="6">
											<Poptip v-if="item.obdId != ''" title="OBD信息"  placement="left" width="300"  style="float: right">
												<Button size="small" @click="getObdInfo(item)" style="font-weight: 700;color: black">OBD</Button>
												<div slot="content">
													<h3 v-if="gpsObdMessage == null" >暂无数据</h3>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">更新日期</Col>
														<Col span="16"><span>{{formatDate(gpsObdMessage.creatorDate)}} {{formatTime(gpsObdMessage.creatortime)}}</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">发动机转速</Col>
														<Col span="16"><span>{{gpsObdMessage.engineSpeed}} r/min</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">车速</Col>
														<Col span="16"><span>{{gpsObdMessage.obdSpeed}} KM/h</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">剩余油量</Col>
														<Col span="16"><span>{{gpsObdMessage.syyl}} L</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">耗油量</Col>
														<Col span="16"><span>{{gpsObdMessage.hyl}} L</span></Col>
													</Row>
													<Row v-if="obdFaultCode && obdFaultCode.length != 0">
														<Col style="border-bottom: 1px solid #cccccc"></Col>
														<Col span="8">故障报告</Col>
														<Col span="16">
															<div v-for="item in obdFaultCode" style="border-bottom: 1px solid #cccccc">
																<span>{{item.faultCode}}</span>
																<!--<span :class="{obdFaultStatus:item.faultType != null,obdFaultHandled:item.faultType == '10',obdFaultNotHandle:item.faultType != '10'}">{{item.faultType == '10' ? '已解决' : '未解决'}}</span>-->
																<br>
																<span>{{item.creationTime}}</span>
															</div>
														</Col>
													</Row>
												</div>
											</Poptip>
										</Col>
									</Row>
								</Card>
							</Col>
						</Row>
					</TabPane>
					<TabPane :label="dhlabel" name="name1" style="height:300px;overflow:auto;" v-show="tabShowFlag">
						<Row v-for="(item,index) in carArray[0]" @click.native="rowClick(item)">
							<Col span="24">
								<Card style="margin:0 15px 5px 15px;" :class="{'choosed':choosedCar == item}">
									<p slot="title">
										<Icon type="soup-can-outline"></Icon>
										{{item.zdbh}}
									</p>
									<p slot="extra" style="color:#19be6b">
										{{formateLongDate(item.time)}}
									</p>
									<Row  type="flex" justify="start">
										<Col span="8">
											<Icon type="model-s"></Icon>
											{{item.cph}}
										</Col>
										<Col span="8">
											<Icon type="person"></Icon>
											{{item.sjxm ? item.sjxm : '暂无绑定'}}
										</Col>
										<Col span="2" offset="6">
											<Poptip v-if="item.obdId != ''" title="OBD信息"  placement="left" width="300"  style="float: right">
												<Button size="small" @click="getObdInfo(item)" style="font-weight: 700;color: black">OBD</Button>
												<div slot="content">
													<h3 v-if="gpsObdMessage == null" >暂无数据</h3>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">更新日期</Col>
														<Col span="16"><span>{{formatDate(gpsObdMessage.creatorDate)}} {{formatTime(gpsObdMessage.creatortime)}}</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">发动机转速</Col>
														<Col span="16"><span>{{gpsObdMessage.engineSpeed}} r/min</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">车速</Col>
														<Col span="16"><span>{{gpsObdMessage.obdSpeed}} KM/h</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">剩余油量</Col>
														<Col span="16"><span>{{gpsObdMessage.syyl}} L</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">耗油量</Col>
														<Col span="16"><span>{{gpsObdMessage.hyl}} L</span></Col>
													</Row>
													<Row v-if="obdFaultCode && obdFaultCode.length != 0">
														<Col style="border-bottom: 1px solid #cccccc"></Col>
														<Col span="8">故障报告</Col>
														<Col span="16">
															<div v-for="item in obdFaultCode" style="border-bottom: 1px solid #cccccc">
																<span>{{item.faultCode}}</span>
																<!--<span :class="{obdFaultStatus:item.faultType != null,obdFaultHandled:item.faultType == '10',obdFaultNotHandle:item.faultType != '10'}">{{item.faultType == '10' ? '已解决' : '未解决'}}</span>-->
																<br>
																<span>{{item.creationTime}}</span>
															</div>
														</Col>
													</Row>
												</div>
											</Poptip>
										</Col>
									</Row>
								</Card>
							</Col>
						</Row>
					</TabPane>
					<TabPane :label="xhlabel" name="name2" style="height:300px;overflow:auto;" v-show="tabShowFlag">
						<Row  v-for="(item,index) in carArray[1]" @click.native="rowClick(item)">
							<Col span="24">
								<Card style="margin:0 15px 5px 15px;" :class="{'choosed':choosedCar == item}">
									<p slot="title">
										<Icon type="soup-can-outline"></Icon>
										{{item.zdbh}}
									</p>
									<p slot="extra" style="color:#ed3f14">
										{{formateLongDate(item.time)}}
									</p>
									<Row  type="flex" justify="start">
										<Col span="8">
											<Icon type="model-s"></Icon>
											{{item.cph}}
										</Col>
										<Col span="8">
											<Icon type="person"></Icon>
											{{item.sjxm ? item.sjxm : '暂无绑定'}}
										</Col>
										<Col span="2" offset="6">
											<Poptip v-if="item.obdId != ''" title="OBD信息"  placement="left" width="300"  style="float: right">
												<Button size="small" @click="getObdInfo(item)" style="font-weight: 700;color: black">OBD</Button>
												<div slot="content">
													<h3 v-if="gpsObdMessage == null" >暂无数据</h3>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">更新日期</Col>
														<Col span="16"><span>{{formatDate(gpsObdMessage.creatorDate)}} {{formatTime(gpsObdMessage.creatortime)}}</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">发动机转速</Col>
														<Col span="16"><span>{{gpsObdMessage.engineSpeed}} r/min</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">车速</Col>
														<Col span="16"><span>{{gpsObdMessage.obdSpeed}} KM/h</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">剩余油量</Col>
														<Col span="16"><span>{{gpsObdMessage.syyl}} L</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">耗油量</Col>
														<Col span="16"><span>{{gpsObdMessage.hyl}} L</span></Col>
													</Row>
													<Row v-if="obdFaultCode && obdFaultCode.length != 0">
														<Col style="border-bottom: 1px solid #cccccc"></Col>
														<Col span="8">故障报告</Col>
														<Col span="16">
															<div v-for="item in obdFaultCode" style="border-bottom: 1px solid #cccccc">
																<span>{{item.faultCode}}</span>
																<!--<span :class="{obdFaultStatus:item.faultType != null,obdFaultHandled:item.faultType == '10',obdFaultNotHandle:item.faultType != '10'}">{{item.faultType == '10' ? '已解决' : '未解决'}}</span>-->
																<br>
																<span>{{item.creationTime}}</span>
															</div>
														</Col>
													</Row>
												</div>
											</Poptip>
										</Col>
									</Row>
								</Card>
							</Col>
						</Row>
					</TabPane>
					<TabPane :label="lxlabel" name="name3" style="height:300px;overflow:auto;" v-show="tabShowFlag">
						<Row  v-for="(item,index) in carArray[2]" @click.native="rowClick(item)">
							<Col span="24">
								<Card style="margin:0 15px 5px 15px;" :class="{'choosed':choosedCar == item}">
									<p slot="title">
										<Icon type="soup-can-outline"></Icon>
										{{item.zdbh}}
									</p>
									<p slot="extra">
										{{formateLongDate(item.time)}}
									</p>
									<Row  type="flex" justify="start">
										<Col span="8">
											<Icon type="model-s"></Icon>
											{{item.cph}}
										</Col>
										<Col span="8">
											<Icon type="person"></Icon>
											{{item.sjxm ? item.sjxm : '暂无绑定'}}
										</Col>
										<Col span="2" offset="6">
											<Poptip v-if="item.obdId != ''" title="OBD信息"  placement="left" width="300"  style="float: right">
												<Button size="small" @click="getObdInfo(item)" style="font-weight: 700;color: black">OBD</Button>
												<div slot="content">
													<h3 v-if="gpsObdMessage == null" >暂无数据</h3>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">更新日期</Col>
														<Col span="16"><span>{{formatDate(gpsObdMessage.creatorDate)}} {{formatTime(gpsObdMessage.creatortime)}}</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">发动机转速</Col>
														<Col span="16"><span>{{gpsObdMessage.engineSpeed}} r/min</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">车速</Col>
														<Col span="16"><span>{{gpsObdMessage.obdSpeed}} KM/h</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">剩余油量</Col>
														<Col span="16"><span>{{gpsObdMessage.syyl}} L</span></Col>
													</Row>
													<Row v-if="gpsObdMessage != null">
														<Col span="8">耗油量</Col>
														<Col span="16"><span>{{gpsObdMessage.hyl}} L</span></Col>
													</Row>
													<Row v-if="obdFaultCode && obdFaultCode.length != 0">
														<Col style="border-bottom: 1px solid #cccccc"></Col>
														<Col span="8">故障报告</Col>
														<Col span="16">
															<div v-for="item in obdFaultCode" style="border-bottom: 1px solid #cccccc">
																<span>{{item.faultCode}}</span>
																<!--<span :class="{obdFaultStatus:item.faultType != null,obdFaultHandled:item.faultType == '10',obdFaultNotHandle:item.faultType != '10'}">{{item.faultType == '10' ? '已解决' : '未解决'}}</span>-->
																<br>
																<span>{{item.creationTime}}</span>
															</div>
														</Col>
													</Row>
												</div>
											</Poptip>
										</Col>
									</Row>
								</Card>
							</Col>
						</Row>
					</TabPane>
					<Button type="primary" size="small" :icon="changeBtnIcon" slot="extra" style="margin:5px" @click.native="changeBtn"></Button>
				</Tabs>
			</Col>
		</div>
		<div style="position:absolute;width:300px;top:0;right:0;z-index:9990;padding-top:30px;padding-right:30px;float: right" type="flex" justify="end">
			<car-info @close="closeItem" ref="carInfoRef"></car-info>
		</div>
		<div class="body-F" style="height:100%;">
			<my-map ref="map"></my-map>
		</div>
    </div>
</template>

<script>

    Date.prototype.format = function(format)
    {
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(),    //day
            "h+" : this.getHours(),   //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length==1 ? o[k] :
                    ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }
import myMap from '../../map/carJK.vue';
import carInfo from './carInfo';
import Stomp from '@stomp/stompjs';
import SockJS from 'sockjs-client';
export default {
    name: 'VehicleMonitoring',
    components: {
    	myMap,carInfo
    },
    data () {
        return {
            tabShowFlag:false,
            SpinShow:false,
            componentName:'',
            mapCarList:[],
            carArray:[[],[],[]],
			status:0,
            choosedCar:null,
			keyword:'',
            obd:{},
            gpsObdMessage:null,
            obdFaultCode:[],
			initTime:'',
            searchKey:'',
            showTabs:false,
			allCarList:[],
			allCarList:[],

            socket : new SockJS(this.$http.url+"/gps"),
            // socket : new SockJS("http://127.0.0.1/gps"),

            changeBtnIcon:'chevron-down',
            qblabel: (h) => {
                return h('div', [
                    h('span','全部 '),
                    h('Button', {
                        props: {
                            shape:'circle',
                            size:'small',
                            disabled:true
                        },
                        style:'background-color:#19be6b;color:white'
                    }, this.allCarList.length)
                ])
            },
            dhlabel: (h) => {
                return h('div', [
                    h('span','点火 '),
                    h('Button', {
                        props: {
                            shape:'circle',
                            size:'small',
                            disabled:true
                        },
                        style:'background-color:#19be6b;color:white'
                    }, this.carArray[0].length)
                ])
            },
            xhlabel: (h) => {
                return h('div', [
                    h('span','熄火 '),
                    h('Button', {
                        props: {
                            shape:'circle',
                            size:'small',
                            disabled:true
                        },
                        style:'background-color:#ed3f14;color:white'
                    }, this.carArray[1].length)
                ])
            },
            lxlabel: (h) => {
                return h('div', [
                    h('span','离线 '),
                    h('Button', {
                        props: {
                            shape:'circle',
                            size:'small',
                            disabled:true
                        },
                        style:'background-color:#657180;color:white'
                    }, this.carArray[2].length)
                ])
            }
        };
    },
    // computed: {
    //     GetsocketAllCar() {
    //         return this.$store.state.app.socketAllCar
    //     }
    // },
    // watch: {
    //     GetsocketAllCar: function(newQuestion, oldQuestion) {
    //         this.onGpsInfo(newQuestion);
    //     },
    // },
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
        changeBtn(){
            if (this.changeBtnIcon == "chevron-down"){
                this.changeBtnIcon = "chevron-up";
                this.tabShowFlag = true;
            }else{
                this.changeBtnIcon = "chevron-down";
                this.tabShowFlag = false;
            }
        },
        closeItem(){
            this.choosedCar = null;
		},
        sco(){
            //数据推送
            var v = this
            v.socket.onopen = function() { };
            v.socket.onmessage = function(e) { };
            v.socket.onclose = function() { };
            var stompClient = Stomp.over(v.socket);
            stompClient.connect({}, function(frame) {
                for (let r of v.allCarList){
                    stompClient.subscribe('/topic/sendgps-'+r.zdbh,  function(data) { //订阅消息
                        v.onGpsInfo(JSON.parse(data.body))
                    });
				}
            });
        },
        formateLongDate(long){
            log(long);
            if (typeof long == 'string'){
                return long;
			}
          	let d = new Date(long);
            log(d);
            return d.format("yyyy-MM-dd hh:mm:ss");
		},
        formatDate(date){
            log(date);
            if (!date)return '';
            return date.substring(0,4)+'-'+date.substring(4,6)+"-"+date.substring(6,8)+"-";
        },
        formatTime(time){
            log(time);
            if (!time)return '';
            return time.substring(0,2)+':'+time.substring(2,4)+":"+time.substring(4,6);
        },
        getObdInfo(item){
            this.SpinShow = true;
            var v = this
            this.gpsObdMessage = null;
            this.obdFaultCode = [];
            this.$http.post(this.apis.CLJK.getObdTimely,{obdId:item.obdId}).then((res) =>{
                if (res.code === 200){
                    if (res.result.gpsObdMessage){
                        this.gpsObdMessage = res.result.gpsObdMessage;
					}
                    if (res.result.obdFaultCode){
                        this.obdFaultCode = res.result.obdFaultCode;
					}
                }
                this.SpinShow = false;
            })
        },
        onGpsInfo(m){
            let has = false;
            let exist = null;
            let newCar = {};
            for(let r of this.allCarList){
			    if (r.zdbh === m.zdbh){
                    exist = r;
                    break;
                }
			}
            if (exist){
                let n = this.updateItem(exist,m);
                newCar = n;
                let index  = this.allCarList.indexOf(exist);
                this.allCarList.splice(index,1,n);
			}else{
                this.handleItem(m);
                newCar = m;
                this.allCarList.push(m);
			}
            this.classify();
            if (this.choosedCar){
                if (this.choosedCar.zdbh == newCar.zdbh){
                    this.mapCarList = [newCar];
                    this.$refs.map.update();
                    return;
				}else{
                    this.mapCarList = [this.choosedCar];
				}
            }else{
                this.mapCarList = this.carArray[this.status];
                this.$refs.map.update();
            }
		},
        init(){
            this.classify();
            this.mapCarList = this.carArray[0];
            this.showTabs = true;
            this.changeBtn();
		},
		initGps(){
            log('initGps');
            var v = this
            this.$http.get(this.apis.CLJK.QUERY).then((res) =>{
                if (res.code === 200){
                    this.initTime = new Date().getTime();
                    this.allCarList = res.result;
                    for(let r of this.allCarList){
                        this.handleItem(r);
                    }
                    this.sco();
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
            this.mapCarList = this.carArray[status];
            this.$refs.map.init();
		},
        filter(){
            this.classify();
            this.mapCarList = this.carArray[this.status];
            this.$refs.map.init();
        },
		classify(){
            this.carArray[0] = [];
            this.carArray[1] = [];
            this.carArray[2] = [];
			for(let r of this.allCarList){
			    let status = r.status;
                if (this.searchKey === ''
					|| r.cph.indexOf(this.searchKey) >= 0
					|| r.zdbh.indexOf(this.searchKey) >= 0
					|| r.sjxm.indexOf(this.searchKey) >= 0
				){
                    this.carArray[status].push(r);
				}
			}
            this.showTabs = false;
            this.showTabs = true;
		},
		updateItem(o,n){
            let r = {};
            r.cx = n.cx;
            r.lng = n.bdjd;
            r.lat = n.bdwd;
            r.clid = n.clid;
            r.zdbh = n.zdbh;
            r.bdjd = n.bdjd;
            r.bdwd = n.bdwd;
            r.zxzt = n.zxzt;
            r.cph = o.cph;
            r.sjxm = o.sjxm;
            r.obdId = o.obdId;
            switch(n.zxzt){
                case '10':
                    r.status = 1;
                    r.text = '熄火时间';
                    r.speed = 0;
                    r.time = o.time;
                    break;
                case '20':
                    r.status = 2;
                    r.text = '离线时间';
                    r.speed = 0;
                    r.time = o.time;
                    break;
                case '00':
                default:
                    r.time = n.time;
                    r.speed = n.speed;
                    r.status = 0;
                    r.text = '更新时间';
            }
            log(r);
            return r;
		},
		handleItem(item){
            item.lng = item.bdjd;
            item.lat = item.bdwd;
            if (item.zxzt){
                switch(item.zxzt){
                    case '10':
                        item.status = 1;
                        item.text = '熄火时间';
                        item.speed = 0;
                        break;
                    case '20':
                        item.status = 2;
                        item.text = '离线时间';
                        item.speed = 0;
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
                        item.speed = 0;
                        break;
                    case '80':
                        item.status = 2;
                        item.text = '离线时间';
                        item.speed = 0;
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
            this.$refs.carInfoRef.init(item);
		}
    }
};
</script>

