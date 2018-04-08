<style type="text/css">
	.allmapSty{
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
		<div :id="mapID" class="allmapSty"></div>
	</div>
</template>

<script>
    import configApi from '@/axios/config.js'
	export default {
		name:'getmapdot',
		data(){
			return {
                mapID:'allmap',
				map:'',
				mapcenter:{
					lng: 114.372443,
	    			lat: 30.544572
				},
				zoom:16,
                stationList:[],
				xlId:''
			}
		},
        props: {
            xlid:{
                type:String,
				default:'123'
			}
        },
		created(){
            this.xlId = this.xlid;
            this.mapID = this.mapID + this.xlid
		},
		mounted(){
            this.getStations();
		},
		methods:{
		    showMap(){
                var v = this
                // 百度地图API功能
                this.map = new BMap.Map(v.mapID);    // 创建Map实例
				if (this.stationList.length > 0){
                    this.map.centerAndZoom(new BMap.Point(this.stationList[0].jd, this.stationList[0].wd),this.zoom);  // 初始化地图,设置中心点坐标和地图级别
                }else{
                    this.map.centerAndZoom(new BMap.Point(114.367161, 30.543013),this.zoom);  // 初始化地图,设置中心点坐标和地图级别

                }
                //添加地图类型控件
                this.map.addControl(new BMap.MapTypeControl({
                        mapTypes:[BMAP_NORMAL_MAP]
                    })
                );
                this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
                this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
                this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
                this.map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件

                var pois = [];
                for(let r of this.stationList){
                    pois.push(new BMap.Point(r.jd,r.wd));
				}
                var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
                    scale: 0.6,//图标缩放大小
                    strokeColor:'#f00',//设置矢量图标的线填充颜色
                    strokeWeight: '2',//设置线宽
                });
                var icons = new BMap.IconSequence(sy, '10', '40');
                var polyline = new BMap.Polyline(pois, {
                    enableEditing: false,//是否启用线编辑，默认为false
                    enableClicking: true,//是否响应点击事件，默认为true
                    icons:[icons],
                    strokeWeight:'8',//折线的宽度，以像素为单位
                    strokeOpacity: 0.8,//折线的透明度，取值范围0 - 1
                    strokeColor:"#18a45b" //折线颜色
                });
                this.map.addOverlay(polyline);          //增加折线
			},
            getStations(){
                this.$http.get(configApi.ZD.GET_BY_ROUTE_ID,{params:{xlId:this.xlId}}).then((res) =>{
                    if(res.code===200 && res.result){
                        this.stationList = res.result;
                        this.showMap();
                    }
                })
            }
		}
	}
</script>