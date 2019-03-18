<!---->
<template>
  <div class="box_col" style="overflow-y: hidden;">
    <mu-appbar style="width: 100%;" color="primary">
      <mu-button flat slot="right"></mu-button>
      地图查看
      <mu-button flat slot="right" @click="Goback">关闭</mu-button>
    </mu-appbar>
    <div class="box_col_100">
      <div id="allmap" style="height:800px;width: 100%"></div>
    </div>
  </div>
</template>


<script>
  import $ from 'jquery';
  import bindRoad from '../../../lib/bindRoad'

  export default {
    config: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor:"#26a2ff",
      navigationBarTextStyle:"white",
      navigationBarTitleText:"地图查看",
      title:'地图查看',
      backgroundColor:'#f2f2f2',
      delay:false,//延迟加载
    },
    data() {
      return {
        thisIndex:5,
        height:'',
        map:'',
        item:[
          {Icon:"../static/img/Bus.png",}
        ],
        carList:[],
        stationList:[],
        xlID:'',
        pointList:''
      }
    },
    created(){
      if(this.$store.state.app.lineID!=0){
        this.xlID = this.$store.state.app.lineID
        // this.getxlmess()
      }else {
        this.$router.push({
          name:'center'
        })
      }
    },
    methods: {
      getBusCode(){
        var v = this
        this.$http.post(this.apis.XLMAP,{xlId:this.xlID}).then((res)=>{
          if(res.code === 200 && res.result){
            v.carList = res.result
            console.log('r.stationNumber',res);
            let stationNumber = 0;
            for (let r of v.carList) {
              console.log('r',r);
              stationNumber ++;
              r.stationNumber = stationNumber;
                var point = new BMap.Point(r.jd, r.wd);
                v.addCar(r, point);
            };
          }
          setTimeout(()=>{v.getBusCode()},5000)
        })
      },
      getxlmess(){
        var v = this
        this.$http.post(this.apis.ZDMESS,{xlid:this.xlID}).then((res)=>{
          if(res.code==200&&res.result){
            v.stationList =  res.result.list;
            let stationNumber = 0;
            for (let r of v.stationList) {
              console.log('r',r);
              stationNumber ++;
              r.stationNumber = stationNumber;
              if(!r.zdName||r.zdName === ''){
                continue
              }
              if(r.zdName.indexOf('辅助点') == -1){
                var point = new BMap.Point(r.jd, r.wd);
                v.addStation(r, point,stationNumber);
              }
            };
            v.getLinePoints(v.stationList)
            v.pointList = v.stationList
          }
        })
      },
      Goback(){
        this.$router.back();
      },
      getIcon(car) {
        switch (car.zxzt) {
          case 20:
            return '/static/img/Bus.png';
          case 10:
            return '/static/img/Bus.png';
          default:
            return '/static/img/Bus.png'
        }
      },
      addCar(item, point){
        bindRoad.bindPoint3(item,this.pointList,1000,this.xlId,this.carList)
        var myIcon = ''
        var mess = ""
        console.log('item',item);
        myIcon =  new BMap.Icon(this.getIcon(item), new BMap.Size(48, 48), {anchor: new BMap.Size(32, 32)});
        var marker = new BMap.Marker(point,{icon:myIcon});
        mess = item.cphm
        this.map.addOverlay(marker);
        let html = '<div style=" width: 120px;height: 28px;padding:4px;text-align: center">' +
          '<span>' + mess +'</span> ' +
          '</div>';
        var myLabel = new BMap.Label(html,                 //为lable填写内容
          {
            offset: new BMap.Size(-80, -70),           //label的偏移量，为了让label的中心显示在点上
            position: point
          });                                            //label的位置
        myLabel.setStyle({                                 //给label设置样式，任意的CSS都是可以的
          fontSize: "16px",                              //字号
          'background-color': 'rgba(255,255,255,0.6)',
          'border-radius': '4px',
        });
        this.map.addOverlay(myLabel);
      },
      addStation(item, point,i){
        var myIcon = ''
        var mess = ""
        console.log('item',item);
        var marker = new BMap.Marker(point);
        marker.setLabel(this.getNumberLabel(i));
        mess = item.zdName
        this.map.addOverlay(marker);
        let html = '<div style=" width: 120px;height: 28px;padding:4px;text-align: center">' +
          '<span>' + mess +'</span> ' +
          '</div>';

        var myLabel = new BMap.Label(html,                 //为lable填写内容
          {
            offset: new BMap.Size(-80, -70),           //label的偏移量，为了让label的中心显示在点上
            position: point
          });                                            //label的位置
        myLabel.setStyle({                                 //给label设置样式，任意的CSS都是可以的
          fontSize: "16px",                              //字号
          'background-color': 'rgba(255,255,255,0.6)',
          'border-radius': '4px',
        });
        this.map.addOverlay(myLabel);
      },
      // 获取一条线路的途径点

      showLine(points){
        console.log("1111111",points);
        let ps = [];
        for (let r of points){
          ps.push(new BMap.Point(r.lng, r.lat));
        }
        var polyline = new BMap.Polyline(ps,
          {strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5}
        );
        this.map.addOverlay(polyline);

      },
// 获取一条线路的途径点
      getLinePoints(stationList){
        let startPoint = new BMap.Point(stationList[0].wd,stationList[0].jd);
        let endPoint = new BMap.Point(stationList[stationList.length -1].wd,stationList[stationList.length -1].jd);
        let waypoints = '';
        for (let i = 1;i<=stationList.length - 2;i++){
          let station = stationList[i];
          waypoints += station.wd+','+station.jd;
          if (i < stationList.length - 2){
            waypoints += '|';
          }
        }
        let url = 'http://api.map.baidu.com/direction/v2/driving?origin='+stationList[0].wd+','+stationList[0].jd+'&destination='+stationList[stationList.length -1].wd+','+stationList[stationList.length -1].jd+'&ak=evDHwrRoILvlkrvaZEFiGp30';
        url += '&waypoints='+waypoints;
        let points = [];
        let v = this;
        $.ajax({
          url:url,
          type:"get",
          dataType:'JSONP',
          success:function(res){
            if (res.status == 0){
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
            }
          }
        })
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

        var label = new BMap.Label(number,{
          offset: offsetSize
        });
        label.setStyle(labelStyle);
        return label;
      },
    },
    mounted() {
      var script = document.createElement("script")
      script.type = "text/javascript"
      var callbackName = '_callback'+Date.now()
      let v = this;
      window[callbackName]= function(){
        v.map = new BMap.Map("allmap");    // 创建Map实例
        v.map.centerAndZoom(new BMap.Point(114.368107 , 30.543083), 11);  // 初始化地图,设置中心点坐标和地图级别
        var point = new BMap.Point(114.368107, 30.543083);
        v.map.centerAndZoom(point, 16);
        v.map.setCurrentCity("武汉");          // 设置地图显示的城市 此项是必须设置的
        v.map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
        v.getBusCode()
        v.getxlmess()
        delete window[callbackName];
      }
      script.src="https://api.map.baidu.com/api?v=2.0&ak=mSjqt13IyQy0GOlkAEGBO5FA2aiIT4q7&callback="+callbackName
      document.body.appendChild(script)
    }
  }
</script>

<style lang="less">
  .lineMess{
    .ui-timeline-wrapper{
      .ui-timeline-row{
        overflow: auto;
        ul{
          display: flex;
          flex-direction: row ;
          flex-wrap:nowrap ;
          height: 100%;
        }
      }
    }
  }
</style>

