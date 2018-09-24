<template>
      <div :id="mapID" style="height: 400px;width:100%;background-color: #f5a623">
      </div>
</template>

<script>
    export default {
        name: "hisList",
        props:{
            gpsList:Array,
            index:Number
        },
        data(){
            return{
                mapID:'allmap',
                map: '',
                mapcenter: {
                    // lng: 114.368383,
                    // lat: 30.551134
                    lng: 113.888383,
                    lat: 30.681134
                },
                zoom: 12,
            }
        },
        created(){
            console.log('历史轨迹数据',this.gpsList);
            // console.log(this.index);
            this.mapID = this.mapID +this.index
        },
        mounted(){
            var v = this
            setTimeout(function () {
                v.showMap();
            },50)
        },
        methods:{
            showMap() {

                var v = this
                // 百度地图API功能
                this.map = new BMap.Map(v.mapID);    // 创建Map实例
                // if (this.gpsList.length > 0) {
                //     let lng = (this.gpsList[0].longitude+this.gpsList[this.gpsList.length-1].longitude)/2
                //     let lat = (this.gpsList[0].latitude+this.gpsList[this.gpsList.length-1].latitude)/2
                let lng = this.gpsList[0].longitude
                let lat = this.gpsList[0].latitude
                    this.map.centerAndZoom(new BMap.Point(v.mapcenter.lng, v.mapcenter.lat), this.zoom);  // 初始化地图,设置中心点坐标和地图级别
                // this.map.centerAndZoom(new BMap.Point(lng,lat), this.zoom)
                // } else {
                //     this.map.centerAndZoom(new BMap.Point(114.367161, 30.543013), this.zoom);  // 初始化地图,设置中心点坐标和地图级别
                //
                // }
                //添加地图类型控件
                this.map.addControl(new BMap.MapTypeControl({
                        mapTypes: [BMAP_NORMAL_MAP]
                    })
                );
                this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
                this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
                this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
                this.map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件

                this.addMarker(this.gpsList[0].bdjd,
                    this.gpsList[0].bdwd,
                    this.apis.STATIC_PATH+'icon/map_line_begin.png')
                this.addMarker(this.gpsList[this.gpsList.length-1].bdjd,
                    this.gpsList[this.gpsList.length-1].bdwd,
                    this.apis.STATIC_PATH+'icon/map_line_end.png')
                // this.showLine(this.gpsList)
                this.getLinePoints(this.gpsList)
                // setTimeout(function () {
                //     v.map.setViewport(this.gpsList);
                // },100)
            },
            getLinePoints(stationList) {
                console.log('============',stationList);
                // let line = this.lineList[index];
                // if (!line || !line.stationList) {
                //     return;
                // }
                // let stationList = line.stationList;
                // let startPoint = new BMap.Point(stationList[0].bdwd, stationList[0].bdjd);
                // let endPoint = new BMap.Point(stationList[stationList.length - 1].bdwd, stationList[stationList.length - 1].bdjd);
                let waypoints = '';
                for (let i = 1; i <= stationList.length - 2; i++) {
                    let station = stationList[i];
                    waypoints += station.bdwd + ',' + station.bdjd;
                    if (i < stationList.length - 2) {
                        waypoints += '|';
                    }
                }
                let url = 'http://api.map.baidu.com/direction/v2/driving?origin=' + stationList[0].bdwd + ',' + stationList[0].bdjd + '&destination=' + stationList[stationList.length - 1].bdwd + ',' + stationList[stationList.length - 1].bdjd + '&ak=evDHwrRoILvlkrvaZEFiGp30';
                url += '&waypoints=' + waypoints;
                let points = [];
                let v = this;
                $.ajax({
                    url: url,
                    type: "get",
                    dataType: 'JSONP',
                    success: function (res) {
                        if (res.status == 0) {
                            let route = res.result.routes[0];
                            points.push({lat: route.origin.lat, lng: route.origin.lng});
                            for (let step of route.steps) {
                                points.push({lng: step.start_location.lng, lat: step.start_location.lat});
                                let paths = step.path.split(";");
                                for (let path of paths) {
                                    if (path === '') continue
                                    let point = path.split(",");
                                    points.push({lng: point[0], lat: point[1]});
                                }
                                points.push({lng: step.end_location.lng, lat: step.end_location.lat});
                            }
                            v.showLine(points);
                            // line.points = points;
                        }
                    }
                })
            },
            showLine(line) {
                var v = this
                console.log('线路数据',line)
                // if (!line.points) return;
                let ps = [];
                for (let r of line) {
                    ps.push(new BMap.Point(r.lng, r.lat));
                }
                var polyline = new BMap.Polyline(ps,
                    {strokeColor: line.color, strokeWeight: 6, strokeOpacity: 0.9}
                );
                this.map.addOverlay(polyline);
            },
            addMarker(lng,lat,icon) {
                var pt = new BMap.Point(lng, lat);
                var myIcon = new BMap.Icon(icon, new BMap.Size(70,70),{imageSize: new BMap.Size(37,62),anchor: new BMap.Size(19,62)});
                var marker = new BMap.Marker(pt,{icon:myIcon}); // 创建标注
                this.map.addOverlay(marker); // 将标注添加到地图中
            },
        }
    }
</script>

<style scoped>

</style>