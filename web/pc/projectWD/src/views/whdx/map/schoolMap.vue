<!--
校巴监控
运营监控-校巴监控-地图展示

-->


<style type="text/css">
	#allmap{
		height: 100%;
		width: 100%;
	}
	.claer{
		position: absolute;
	    width: 64px;
	    height: 47px;
	    top: 5px;
	    right: 57px;
	    z-index: 100;
	    background-color: rgb(255, 255, 255);
	    text-align: center;
	    line-height: 45px;
	    border: solid 1px #696666;
	    border-radius: 7px;
	}
</style>

<template>
	<div style="height: 100%;position: relative;">
                    <span>
                        <CheckboxGroup v-model="choosedLineIds">
                            <Checkbox v-for="(item,index) in lineList" :label="item.xlmc"></Checkbox>
                        </CheckboxGroup>
                    </span>
		<div id="allmap"></div>
		<!--<div class="claer">
			 <Button type="success" size="small" @click="clear">清除</Button>
		</div>-->
	</div>
</template>

<script>
    import SockJS from 'sockjs-client';
    import Stomp from '@stomp/stompjs';

    export default {
		name:'',
		data(){
			return{
				map:'',
				mapcenter:{
					lng: 114.368383,
	    			lat: 30.551134
				},
                lineList:[],
                choosedLineIds:[],
				zoom:16,
				zoomDot:[],
                scoketMess:[],
                allDeviceList: [],
                socket : new SockJS(this.$http.url+"/gps"),
			}
		},
		computed: {
			GetscoketMess() {
				return this.$store.state.app.socketMess
			}
		},
		watch: {
			GetscoketMess: function(n, o) {
			    var v = this
                this.scoketMess = n
                this.disDot(n)

                for(var i = 0 ; i<n.length ; i++){
                    v.zoomDot.push(
                        new BMap.Point(n[i].bdjd,n[i].bdwd)
                    )
                }
				// setTimeout(function () {
				// 	v.map.setViewport(v.zoomDot);
				// },50)

			},
		},
		created(){
		},
		mounted(){
			//点布控
			var v = this
			// 百度地图API功能
			this.map = new BMap.Map("allmap");    // 创建Map实例
		  	this.mapCenterF()
            this.getAllDevice()
            this.getLineList()
		},
		methods:{
            getLineList(){
                var v = this
                this.$http.post(this.apis.XL.QUERY, {'lx': 30,pageSize:1000}).then((res) => {
                    if (res.code == 200 && res.page.list){
                        this.lineList = res.page.list;
                        for (let i in this.lineList){
                            this.getLineStations(i);
						}
                    }
                })
            },
			getLineStations(i){
                var v = this
                this.$http.post(this.apis.ZD.GET_BY_ROUTE_ID, {'xlId': this.lineList[i].id}).then((res) => {
                    if (res.code == 200 && res.result){
                        this.lineList[i].stationList = res.result;
                        // for (let i = 0;i<res.result.length - 1;i++){
                        for (let i = 0;i<1;i++){
                            this.drivingLine(new BMap.Point(res.result[i].jd,res.result[i].wd),new BMap.Point(res.result[i+1].jd,res.result[i+1].wd),i)
						}
                    }
                })
			},
            getAllDevice() {
                this.$http.get(this.apis.ZDGL.QUERY+'?pageSize=10000').then((res)=>{
                    if (res.code == 200 && res.page.list){
                        this.addDeviceList = res.page.list;
                        this.sco();
                    }
                })
            },
            sco(){
                var v = this
                v.socket.onopen = function() { };
                v.socket.onmessage = function(e) { };
                v.socket.onclose = function() { };
                let stompClient = Stomp.over(v.socket);
                stompClient.connect({}, function(frame) {
                    for (let r of v.addDeviceList){
                        stompClient.subscribe('/topic/sendgps-'+r.zdbh,  function(data) { //订阅消息
                            let jsonMess = JSON.parse(data.body)
                            if(jsonMess.cx==="30"){//校巴
                                v.scoketMess.forEach((item,index) => {
                                    if(item.clid==jsonMess.clid){
                                        v.scoketMess.splice(index,1)
                                    }
                                })
                                v.scoketMess.push(jsonMess)
                                v.$store.commit('socketMessAdd',v.scoketMess)
                            }
                        });
                    }
                });
            },
			//撒点
			disDot(list){
				this.clear()
				// 编写自定义函数,创建标注
				var v = this
				// 随机向地图添加25个标注
				for (var i = 0; i < list.length; i ++) {
					var point = new BMap.Point(list[i].bdjd, list[i].bdwd);
					v.addMarker(list[i],point);
                    v.addLabel(list[i], point);
				}
			},
            addLabel(item,point) {
                let html = '<div style="width: 160px;height: 28px;padding:4px;">' +
                    '<span>['+item.cph+']</span> ' +
                    '<span style="float: right">'+item.speed+' km/h</span>' +
                    '</div>'
                var myLabel = new BMap.Label(html,     //为lable填写内容
                    {
                        offset: new BMap.Size(-80, -70),                  //label的偏移量，为了让label的中心显示在点上
                        position: point
                    });                                //label的位置
                myLabel.setStyle({                                   //给label设置样式，任意的CSS都是可以的
                    // color:"red",                   //颜色
                    fontSize:"16px",               //字号
                    // opacity:0.5,
                    'background-color': 'rgba(255,255,255,0.6)',
                    // border:"none",                    //边
                    'border-radius': '4px',
                    // height:"120px",                //高度
                    // width:"125px",                 //宽
                    // textAlign:"center",            //文字水平居中显示
                    // lineHeight:"120px",            //行高，文字垂直居中显示
                    // background:"url(http://cdn1.iconfinder.com/data/icons/CrystalClear/128x128/actions/gohome.png)",    //背景图片，这是房产标注的关键！
                    // cursor:"pointer"
                });
                myLabel.setTitle("我是文本标注label");               //为label添加鼠标提示
                this.map.addOverlay(myLabel);
            },
            addMarker(item,point){
			    // debugger
                var myIcon = new BMap.Icon(this.getIcon(item), new BMap.Size(32, 32), {anchor: new BMap.Size(16, 32)});
                var marker = new BMap.Marker(point, {icon: myIcon});
                // var marker = new BMap.Marker(point);
                this.map.addOverlay(marker);
            },
            getIcon(car) {
                switch (car.zxzt) {
                    //绿色图标
                    // case 20:
                    //     return 'http://47.98.39.45:9092/icon/running.png';
                    //红色图标
                    // case 10:
                    //     return 'http://47.98.39.45:9092/icon/ic_car.png';
                    //灰色图标
                    case '20':
                        return 'http://47.98.39.45:9092/icon/ic_car_offline.png'
					default:
                        return 'http://47.98.39.45:9092/icon/running.png'
                }
            },
			//地图级别中心
			mapCenterF(){
				var v = this
				var point = new BMap.Point(v.mapcenter.lng, v.mapcenter.lat);
				this.map.centerAndZoom(point, v.zoom);// 初始化地图,设置中心点坐标和地图级别
				this.map.addControl(new BMap.MapTypeControl({
				mapTypes:[
		            BMAP_NORMAL_MAP
		        ]}));
				//添加地图类型控件
				this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
			    this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
			    this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
			    this.map.addControl(new BMap.NavigationControl()); 				// 添加平移缩放控件
			},
            drivingLine(startPoint,endPoint,index){
                let v = this;
                var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/Mario.png", new BMap.Size(32, 70), {    //小车图片
                    //offset: new BMap.Size(0, -5),    //相当于CSS精灵
                    imageOffset: new BMap.Size(0, 0)    //图片的偏移量。为了是图片底部中心对准坐标点。
                });
                var driving = new BMap.DrivingRoute(this.map, {renderOptions:{map: this.map}});    //驾车实例
                driving.search(startPoint, endPoint);
                driving.setSearchCompleteCallback(function(){
                    console.log("result:",driving.getResults());
                    var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组
                    var paths = pts.length;    //获得有几个点
                    //
                    // var carMk = new BMap.Marker(pts[0],{icon:myIcon});
                    // v.map.addOverlay(carMk);
                    // i=0;
                });
            },
            //清除层
			clear(){
				this.map.clearOverlays()
			},
		}
	}
</script>

<style>
</style>
