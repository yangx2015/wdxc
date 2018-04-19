<style type="text/css">
	#allmap{
		height: 100%;
		width: 100%;
	}
</style>
<!--地图选点-->
<template>
	<div style="height: 100%;background-color: #00FFFF;">
		<component :is="componentName"></component>
		<div id="allmap"></div>
	</div>
</template>

<script>
    import carInfo from '../OperationMonitoring/VehicleMonitoring/carInfo'
	export default {
		name:'getmapdot',
        components: {
            carInfo
        },
		data(){
			return {
                componentName:'',
				map:'',
				mapcenter:{
					lng: 114.357527,
	    			lat: 30.550822
				},
				zoom:12,
				carList:[],
			}
		},
		created(){
		},
		mounted(){
            this.Buildmap()
		    this.init();
		},
		methods:{
		    init(){
                this.carList = this.$parent.mapCarList;
                this.disDot()
			},
			Buildmap(){
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
		},
		//撒点
		disDot(){
			this.clear()
			var v = this
			// 随机向地图添加25个标注
			for(let r of this.carList){
                console.log(r.lng,r.lat);
                var point = new BMap.Point(r.lng, r.lat);
                addMarker(point);
            }
			// 编写自定义函数,创建标注
			function addMarker(point){
			  	var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(300,150), {anchor: new BMap.Size(130,110),});
			  	var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/car.png",  new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)});
			  	var marker = new BMap.Marker(point,{icon:myIcon});
				v.map.addOverlay(marker);
				v.addClickHandler('content',marker);
			}
		},
		addClickHandler(content,marker){
			var v = this
			let newHtml = '<input type="button" name="" id="" value="dianji" />'
			marker.addEventListener("click",function(e){
                console.log('carInfo');
                v.componentName = 'carInfo';
				// v.openInfo(newHtml,e)
				console.log('点数据',e)
			})
		},
		clear(){
			this.map.clearOverlays();//清楚数据点
		}
	}
}
</script>