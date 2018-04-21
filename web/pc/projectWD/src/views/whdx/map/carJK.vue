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
    import configApi from '@/axios/config.js'
    import carInfo from '../OperationMonitoring/VehicleMonitoring/carInfo'
	export default {
		name:'getmapdot',
        components: {
            carInfo
        },
		data(){
			return {
                componentName:'',
				choosedItem:null,
				map:'',
				mapcenter:{
					lng: 114.357527,
	    			lat: 30.550822
				},
				zoom:12,
				carList:[],
				fancePoints:[
					{lng:114.27226, lat:30.608123},
					{lng:114.157277 ,lat:30.544446},
					{lng:114.418288, lat: 30.526529},
				]
			}
		},
		created(){
		},
		mounted(){
            this.Buildmap()
		    this.init();
		},
		methods:{
		    showFance(carId){
                this.fancePoints = [];
                var v = this
                this.$http.get(configApi.DZWL.GET_BY_CAR_ID+"?clId="+carId).then((res) =>{
                    if (res.code === 200){
                        let s = res.result.dlxxzb;
                        let ps = s.split(";");
                        for (let r of ps){
                            let point = r.split(",");
                            this.fancePoints.push({lng:point[1],lat:point[0]})
                        }
                    }
                })
                this.addArea(this.fancePoints);
			},
		    showPath(carId){
                this.addLine(this.fancePoints);
			},
		    init(){
                this.carList = this.$parent.mapCarList;
                console.log(this.carList);
                if (this.carList.length > 0){
                    this.map.centerAndZoom(new BMap.Point(this.carList[0].lng, this.carList[0].lat),this.zoom);  // 初始化地图,设置中心点坐标和地图级别
                }
                this.showCarPosition()
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
		showCarPosition(){
			this.clear()
			var v = this
			for(let r of this.carList){
                var point = new BMap.Point(r.lng, r.lat);
                this.addMarker(r,point);
            }
		},
			addLine(points){
                let ps = [];
                for (let r of points){
                    ps.push(new BMap.Point(r.lng,r.lat))
                }
                var polygon = new BMap.Polyline(ps, {strokeColor:"red", strokeWeight:2, strokeOpacity:0.5});  //创建多边形
                this.map.addOverlay(polygon);
			},
			addArea(points){
		        let ps = [];
		        for (let r of points){
		            ps.push(new BMap.Point(r.lng,r.lat))
				}
                var polygon = new BMap.Polygon(ps, {strokeColor:"red", strokeWeight:2, strokeOpacity:0.5});  //创建多边形
                this.map.addOverlay(polygon);
			},
            addMarker(item,point){
                var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(300,150),{anchor: new BMap.Size(130,110)});
                var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/car.png", new BMap.Size(52,26),  {anchor : new BMap.Size(27, 13)});
                var marker = new BMap.Marker(point,{icon:myIcon});
                this.map.addOverlay(marker);
                this.addClickHandler(item,marker);
            },
		addClickHandler(item,marker){
			var v = this
			marker.addEventListener("click",function(e){
                v.componentName = 'carInfo';
                v.choosedItem = item;
			})
		},
		clear(){
			this.map.clearOverlays();//清楚数据点
		}
	}
}
</script>
