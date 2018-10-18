<!--
线路展示
线路维护 - 线路展示组件
-->

<style type="text/css">
      .allmapSty {
            height: 100%;
            width: 100%;
      }

      .claer {
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

    export default {
        name: 'getmapdot',
        data() {
            return {
                mapID: 'allmap',
                map: '',
                mapcenter: {
                    lng: 114.372443,
                    lat: 30.544572
                },
                zoom: 16,
                stationList: [],
                xlId: ''
            }
        },
        props: {
            xlid: {
                type: String,
                default: '123'
            }
        },
        created() {
            this.xlId = this.xlid;
            this.mapID = this.mapID + this.xlid
        },
        mounted() {
            this.getStations();
        },
        methods: {
            showMap() {
                var v = this
                // 百度地图API功能
                this.map = new BMap.Map(v.mapID);    // 创建Map实例
                if (this.stationList.length > 0) {
                    this.map.centerAndZoom(new BMap.Point(this.stationList[0].jd, this.stationList[0].wd), this.zoom);  // 初始化地图,设置中心点坐标和地图级别
                } else {
                    this.map.centerAndZoom(new BMap.Point(114.367161, 30.543013), this.zoom);  // 初始化地图,设置中心点坐标和地图级别

                }
                //添加地图类型控件
                this.map.addControl(new BMap.MapTypeControl({
                        mapTypes: [BMAP_NORMAL_MAP]
                    })
                );
                this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
                this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
                this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
                this.map.addControl(new BMap.NavigationControl());               // 添加平移缩放控件

                this.showStations(this.stationList)
                // this.showLine(this.stationList)
                this.getLinePoints(this.stationList)
            },
            // 获取一条线路的途径点
            getLinePoints(stationList) {
                console.log(stationList);
                // let line = this.lineList[index];
                // if (!line || !line.stationList) {
                //     return;
                // }
                // let stationList = line.stationList;
                let startPoint = new BMap.Point(stationList[0].wd, stationList[0].jd);
                let endPoint = new BMap.Point(stationList[stationList.length - 1].wd, stationList[stationList.length - 1].jd);
                let waypoints = '';
                for (let i = 1; i <= stationList.length - 2; i++) {
                    let station = stationList[i];
                    waypoints += station.wd + ',' + station.jd;
                    if (i < stationList.length - 2) {
                        waypoints += '|';
                    }
                }
                let url = 'http://api.map.baidu.com/direction/v2/driving?origin=' + stationList[0].wd + ',' + stationList[0].jd + '&destination=' + stationList[stationList.length - 1].wd + ',' + stationList[stationList.length - 1].jd + '&ak=evDHwrRoILvlkrvaZEFiGp30';
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
            showStations(line) {
                let stationList = line;
                if (!stationList) return;
                let c = 0;
                for (let r of stationList) {
                		if(r.mc.indexOf('辅助点')==-1){
                			this.addStation(r, ++c);
                		}
                }
            },
            // 站点详情
            addStation(item, i) {
                var marker = new BMap.Marker(new BMap.Point(item.jd, item.wd));
                marker.setLabel(this.getNumberLabel(i));
                this.map.addOverlay(marker);

                let html = '<div style="width: 120px;height: 28px;padding:4px;text-align: center">' +
                    '<span>' + item.mc +'</span> ' +
                    '</div>';
                let point = new BMap.Point(item.jd, item.wd);
                var myLabel = new BMap.Label(html,     //为lable填写内容
                    {
                        offset: new BMap.Size(-80, -70),                  //label的偏移量，为了让label的中心显示在点上
                        position: point
                    });                                //label的位置
                myLabel.setStyle({                                   //给label设置样式，任意的CSS都是可以的
                    fontSize: "16px",               //字号
                    'background-color': 'rgba(255,255,255,0.6)',
                    'border-radius': '4px',
                });
                myLabel.setTitle("我是文本标注label");               //为label添加鼠标提示
                this.map.addOverlay(myLabel);
            },

            showLine(line) {
                console.log(line)
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
            getNumberLabel(number) {
                var offsetSize = new BMap.Size(0, 0);
                var labelStyle = {
                    color: "#fff",
                    backgroundColor: "0.05",
                    border: "0"
                };

                //不同数字长度需要设置不同的样式。
                switch ((number + '').length) {
                    case 1:
                        labelStyle.fontSize = "14px";
                        offsetSize = new BMap.Size(4, 2);
                        break;
                    case 2:
                        labelStyle.fontSize = "12px";
                        offsetSize = new BMap.Size(2, 4);
                        break;
                    case 3:
                        labelStyle.fontSize = "10px";
                        offsetSize = new BMap.Size(-2, 4);
                        break;
                    default:
                        break;
                }

                var label = new BMap.Label(number, {
                    offset: offsetSize
                });
                label.setStyle(labelStyle);
                return label;
            },
            getStations() {
                this.$http.get(this.apis.ZD.GET_BY_ROUTE_ID, {params: {xlId: this.xlId}}).then((res) => {
                    if (res.code === 200 && res.result) {
                        this.stationList = res.result;
                        this.showMap();
                    }
                })
            }
        }
    }
</script>