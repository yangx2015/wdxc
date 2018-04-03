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
			    socket : new SockJS("http://"+"192.168.31.180"+"/gps"),
			    
			    
				scoketMess:this.$store.state.app.socketMess,
				map:'',
				mapcenter:{
					lng: 114.372443,
	    			lat: 30.544572
				},
				zoom:16
			}
		},
		created(){
			this.cs()
		},
		mounted(){
			//点布控
			var v = this
			// 百度地图API功能
			this.map = new BMap.Map("allmap");    // 创建Map实例
		  	this.mapCenter()
//		  	this.sco()
		},
		methods:{
			cs(){
				let arr = [9,8,7,6,5,4,3,2]
//				console.log(arr)
//				for(var r in arr){
//					if(arr[r]==7){
//						arr.splice(3,1,250)
//					}
//				}
				arr.forEach((index,item) => {
					if(item==7){
						arr.splice(3,1,256)
					}
				})
				setTimeout(() =>{
					console.log(arr)
				},200)
			},
			sco(){
				var v = this
			/**
		     * 建立成功的回调函数
		     */
			    v.socket.onopen = function() {
			        console.log('开启');
			    };
			/**
		     * 服务器有消息返回的回调函数
		     */
			    v.socket.onmessage = function(e) {
			        console.log('message', e.data);
			    };
		
		    /**
		     * websocket链接关闭的回调函数
		     */
			    v.socket.onclose = function() {
			        console.log('关闭');
			    };
		
			    var stompClient = Stomp.over(v.socket);
			    stompClient.connect({}, function(frame) {
			        stompClient.subscribe('/topic/sendgps',  function(data) { //订阅消息
						console.log('数据接受1',data);
						console.log('数据接受2',data.body);
			            console.log('数据接受3',JSON.parse(data.body));
			            console.log('数据存储',v.scoketMess)
			            v.scoketMess.push(JSON.parse(data.body))
			            v.$store.commit('socketMessAdd',v.scoketMess)
			        });
			    });	
			},
			//地图级别中心
			mapCenter(){
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
		}
	}
</script>

<style>
</style>