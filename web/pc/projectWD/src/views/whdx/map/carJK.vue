<style type="text/css">
	#allmap{
		height: 100%;
		width: 100%;
	}
</style>
<!--地图选点-->
<template>
	<div style="height: 100%;background-color: #00FFFF;">
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
					lng: 114.357527,
	    			lat: 30.550822
				},
				zoom:12,
				dot:[
					{
						lng:114.27226 ,
						lat:30.608123
					},
					{
						lng:114.157277 ,
						lat:30.544446 
					},
					{
						lng: 114.418288,
						lat: 30.526529
					},
					{
						lng: 114.321703,
						lat: 30.477739
					},
				],
				opts:{
					width : 250,     // 信息窗口宽度
					height: 80,     // 信息窗口高度
					title : "信息窗口" , // 信息窗口标题
					enableMessage:false//设置允许信息窗发送短息
			    }
			}
		},
		created(){
			
		},
		mounted(){
			this.Buildmap()
			this.disDot(this.dot)
		},
		methods:{
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
		disDot(list){
			this.clear()
			var v = this
			// 随机向地图添加25个标注
			for (var i = 0; i < list.length; i ++) {
				var point = new BMap.Point(list[i].lng, list[i].lat);
				addMarker(point);
			}
			// 编写自定义函数,创建标注
			function addMarker(point){
//			  	var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(300,150), {anchor: new BMap.Size(130,110),});
			  	
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
				v.openInfo(newHtml,e)
				console.log('点数据',e)
			})
		},
		openInfo(content,e){
			var v = this
			var p = e.target;
			var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
			var infoWindow = new BMap.InfoWindow(content,v.opts);  // 创建信息窗口对象 
			v.map.openInfoWindow(infoWindow,point); //开启信息窗口
		},
		clear(){
			this.map.clearOverlays();//清楚数据点
		}
	}
}
</script>
<!--
	// 百度地图API功能	
	map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(116.417854,39.921988), 15);
	var data_info = [[116.417854,39.921988,"地址：北京市东城区王府井大街88号乐天银泰百货八层"],
					 [116.406605,39.921585,"地址：北京市东城区东华门大街"],
					 [116.412222,39.912345,"地址：北京市东城区正义路甲5号"]
					];
	var opts = {
				width : 250,     // 信息窗口宽度
				height: 80,     // 信息窗口高度
				title : "信息窗口" , // 信息窗口标题
				enableMessage:true//设置允许信息窗发送短息
			   };
	for(var i=0;i<data_info.length;i++){
		var marker = new BMap.Marker(new BMap.Point(data_info[i][0],data_info[i][1]));  // 创建标注
		var content = data_info[i][2];
		map.addOverlay(marker);               // 将标注添加到地图中
		addClickHandler(content,marker);
	}
	function addClickHandler(content,marker){
		marker.addEventListener("click",function(e){
			openInfo(content,e)}
		);
	}
	function openInfo(content,e){
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		map.openInfoWindow(infoWindow,point); //开启信息窗口
	}
-->