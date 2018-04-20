<template>
	<div style="width: 100%;height: 100%;background-color: #00FFFF;">
		<Modal v-model="showModal" width='900'  title="历史轨迹">
			<div id="allmap" style="width: 100%;height: 600px;"></div>
		</Modal>
	</div>
</template>

<script>
	export default {
		name: 'historyTarckMap',
		data() {
			return {
                showModal:true,
				map: '',
				mapcenter: {
					lng: 114.357527,
					lat: 30.550822
				},
				zoom: 4,
				stationList: [
				{
					id: "435383685965938688",
					dz: null,
					mc: "校大门",
					jd: 114.36439,
					wd: 30.545721,
				},{
					id: "435383685965938688",
					dz: null,
					mc: "校大门",
					jd: 114.36439,
					wd: 30.545721,
				},{
					id: "435383835257995264",
					dz: null,
					mc: "桂园",
					jd: 114.367372,
					wd: 30.546219,
				},{
					id: "435383897451134976",
					dz: null,
					mc: "明珠园",
					jd: 114.366259,
					wd: 30.546126,
				},{
					id: "435388661425504256",
					dz: null,
					mc: "自强超市",
					jd: 114.373086,
					wd: 30.545628,
				},{
					id: "435388816090464256",
					dz: null,
					mc: "主教学楼",
					jd: 114.37384,
					wd: 30.546095,
				},{
					id: "435389037470023680",
					dz: null,
					mc: "学生五舍",
					jd: 114.372583,
					wd: 30.54314,
				},{
					id: "435389106743148544",
					dz: null,
					mc: "学生十五舍",
					jd: 114.376607,
					wd: 30.542704,
				},{
					id: "435389271692541952",
					dz: null,
					mc: "体育馆",
					jd: 114.367013,
					wd: 30.544633,
				}]
			}
		},
		created() {

		},
		mounted() {
			this.Buildmap()
			this.line()
			let vi = 0
            if(vi==this.stationList.length){
            	vi = 0
            }
            this.animationDot(this.stationList[vi])
            setInterval(()=>{
            	vi ++
            },500)
		},
		methods: {
			Buildmap() {
                var v = this
				// 百度地图API功能
				this.map = new BMap.Map("allmap"); // 创建Map实例
				this.map.centerAndZoom(new BMap.Point(this.mapcenter.lng, this.mapcenter.lat), this.zoom); // 初始化地图,设置中心点坐标和地图级别
				//添加地图类型控件
				this.map.addControl(new BMap.MapTypeControl({
					mapTypes: [
						BMAP_NORMAL_MAP
					]
				}));
				this.map.enableScrollWheelZoom(true); //开启鼠标滚轮缩放
				this.map.addControl(new BMap.ScaleControl()); // 添加比例尺控件
				this.map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
				this.map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
			},
			line(){
				var v = this
				var pois = [];
                for(let r of v.stationList){
                    pois.push(new BMap.Point(r.jd,r.wd));
				}
                v.map.setViewport(pois)
                var sy = new BMap.Symbol(BMap_Symbol_SHAPE_BACKWARD_OPEN_ARROW, {
                    scale: 0.6,//图标缩放大小
                    strokeColor:'#fff',//设置矢量图标的线填充颜色
                    strokeWeight: '2',//设置线宽
                });
                var icons = new BMap.IconSequence(sy, '10', '20');
                var polyline = new BMap.Polyline(pois, {
                    enableEditing: true,//是否启用线编辑，默认为false
                    enableClicking: true,//是否响应点击事件，默认为true
                    icons:[icons],
                    strokeWeight:'8',//折线的宽度，以像素为单位
                    strokeOpacity: 0.8,//折线的透明度，取值范围0 - 1
                    strokeColor:"#18a45b" //折线颜色
                });
                this.map.addOverlay(polyline);          //增加折线

			},
			animationDot(item){
				var pt = new BMap.Point(item.jd, item.wd);
				var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(300,150), {anchor: new BMap.Size(130,110),});
				var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
				this.map.addOverlay(marker2);
			}
		}
	}
</script>

<style>

</style>
