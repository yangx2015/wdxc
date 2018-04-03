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

    // let host = "127.0.0.1"
    let host = "192.168.31.180"

    var socket = new SockJS("http://"+host+"/gps");

    /**
     * 建立成功的回调函数
     */
    socket.onopen = function() {
        console.log('open');
    };

    /**
     * 服务器有消息返回的回调函数
     */
    socket.onmessage = function(e) {
        console.log('message', e.data);
    };

    /**
     * websocket链接关闭的回调函数
     */
    socket.onclose = function() {
        console.log('close');
    };

    var stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/get',  function(data) { //订阅消息
            console.log(data);
        });
    });

    export default {
		name:'',
		data(){
			return{
				map:'',
				mapcenter:{
					lng: 114.372443,
	    			lat: 30.544572
				},
				zoom:16
			}
		},
		created(){
			
		},
		mounted(){
			//点布控
			var v = this
			// 百度地图API功能
			this.map = new BMap.Map("allmap");    // 创建Map实例
			this.map.centerAndZoom(new BMap.Point(this.mapcenter.lng, this.mapcenter.lat),this.zoom);  // 初始化地图,设置中心点坐标和地图级别
			//添加地图类型控件
			this.map.addControl(new BMap.MapTypeControl({
				mapTypes:[
		            BMAP_NORMAL_MAP
//		            BMAP_HYBRID_MAP
		        ]}));	  

			this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
		    this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
		    this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
		    this.map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
		  	
//		  	for (var i = 0; i < 25; i ++) {
//				var point = new BMap.Point(114.372443 + (0.001*i),30.544572 + (0.001*i));
//				v.map.addMarker(point);
//			}
		},
		methods:{
		}
	}
</script>

<style>
</style>