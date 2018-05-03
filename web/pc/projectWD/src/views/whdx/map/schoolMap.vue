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
	<div style="height: 100%;background-color: #00FFFF;position: relative;">
		<div id="allmap"></div>
		<!--<div class="claer">
			 <Button type="success" size="small" @click="clear">清除</Button>
		</div>-->
	</div>
</template>

<script>
    import SockJS from 'sockjs-client';
    // 'sockjs-client' 必须与package.json文件当中dependencies 当中的一模一样
    import Stomp from '@stomp/stompjs';

    export default {
		name:'',
		data(){
			return{
				// let host = "127.0.0.1"
			    host : "192.168.31.180",
			    socket : new SockJS("http://"+"47.98.39.45:8080"+"/biz/gps"),
//			    socket : new SockJS("http://"+"192.168.31.228"+"/gps"),
			    
			    
				scoketMess:[],
				map:'',
				mapcenter:{
					lng: 114.368383,
	    			lat: 30.551134
				},
				zoom:16
			}
		},
		computed: {
			GetscoketMess() {
				return this.$store.state.app.socketMess
			}
		},
		watch: {
			GetscoketMess: function(newQuestion, oldQuestion) {
				this.scoketMess = newQuestion
				this.disDot(newQuestion)
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
//		  	this.sco()
		},
		methods:{
			//撒点
			disDot(list){
				this.clear()
				// 编写自定义函数,创建标注
				var v = this
				function addMarker(point){
				  var marker = new BMap.Marker(point);
				  v.map.addOverlay(marker);
				}
				// 随机向地图添加25个标注
				for (var i = 0; i < list.length; i ++) {
					var point = new BMap.Point(list[i].bdjd, list[i].bdwd);
					addMarker(point);
				}
			},
			sco(){//数据推送
				var v = this
			/**
		     * 建立成功的回调函数
		     */
			    v.socket.onopen = function() {
			    };
			/**
		     * 服务器有消息返回的回调函数
		     */
			    v.socket.onmessage = function(e) {
			        log('message', e.data);
			    };
		
		    /**
		     * websocket链接关闭的回调函数
		     */
			    v.socket.onclose = function() {
			        log('关闭');
			    };
		
			    var stompClient = Stomp.over(v.socket);
			    stompClient.connect({}, function(frame) {
			        stompClient.subscribe('/topic/sendgps',  function(data) { //订阅消息
			            let jsonMess = JSON.parse(data.body)
			            v.scoketMess.forEach((item,index) => {
							if(item.clid==jsonMess.clid){
								v.scoketMess.splice(index,1)
							}
						})
				        v.scoketMess.push(jsonMess)
			            v.$store.commit('socketMessAdd',v.scoketMess)
			        });
			    });	
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
			//清除层
			clear(){
				this.map.clearOverlays()
			},
		}
	}
</script>

<style>
</style>