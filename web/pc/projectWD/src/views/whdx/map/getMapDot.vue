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
	</div>
</template>

<script>
	export default {
		name:'getmapdot',
		data(){
			return {
				map:'',
				mapcenter:{
					lng: 114.372443,
	    			lat: 30.544572
				},
				zoom:16,
			}
		},
		created(){
			
		},
		mounted(){
			var v = this
			// 百度地图API功能
			this.map = new BMap.Map("allmap");    // 创建Map实例
			this.map.centerAndZoom(new BMap.Point(this.mapcenter.lng, this.mapcenter.lat),this.zoom);  // 初始化地图,设置中心点坐标和地图级别
			//添加地图类型控件
			this.map.addControl(new BMap.MapTypeControl({
					mapTypes:[
			            BMAP_NORMAL_MAP
			        ]
				})
			);
		    this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
		    this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
		    this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
		    this.map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件
		    
		    this.map.addEventListener("click",function(e){
				console.log(e.point.lng + "," + e.point.lat);
				v.map.clearOverlays();
				var marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat)); // 创建点
				v.map.addOverlay(marker);
				v.$emit('getDot',e)
			});
	},
	methods:{
	}
	}
</script>