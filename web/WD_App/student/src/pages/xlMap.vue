<template>
  <!--<div id="allmap" style="width: 100%; height: {{mapHeight}}px"></div>-->
  <!--<div id="allmap":style="{width: '100%', height: mapHeight + 'px'}"></div>-->
  <div id="allmap" :style="mapStyle"></div>
</template>
<script type="text/javascript">
  import BMap from 'BMap'
  export default{
    data(){
      return{
        mapStyle:{
          width:'100%',
          height:'100%'
        }
      }
    },
    props:{
      mapHeight:{
        type:Number,
        default:800
      },
      //经度
      longitude:{
        type:Number,
        default:116.404
      },
      latitude:{
        type:Number,
        default:39.915
      }
    },
    mounted(){
      this.ready()
    },
    methods:{
      ready(){
        var map =new BMap.Map("allmap");
        var point =new BMap.Point(this.longitude,this.latitude);
        var marker =new BMap.Marker(point);
        map.addOverlay(marker);
        map.centerAndZoom(point,15);
        map.enableScrollWheelZoom(true);

        var content = '<div style="margin:0;line-height:16px;padding:2px;font-size:12px;">' +
          /*'<img :src="s1.jpg" style="float:right;zoom:1;overflow:hidden;width:100px;height:66px;margin-left:3px;"/>'+*/
          '地址：北京市丰台区丰台科技园汉威国际广场3区5号楼2M<br/>电话：022-3344563' +
          '</div>';
        // 信息窗的配置信息
        /*var opts ={
          width :200,
          height:50,
          title :"地址：",
        }*/
        /*var infoWindow =new BMap.InfoWindow(this.description, opts);// 创建信息窗口对象
        marker.addEventListener("click",function(){
          map.openInfoWindow(infoWindow,point);
        });
        map.enableScrollWheelZoom(true);
        map.openInfoWindow(infoWindow,point);//开启信息窗口*/
        var searchInfoWindow = null;
        searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
          title  : "北京中厚明德",      //标题
          width  : 200,             //宽度
          height : 70,              //高度
          panel  : "panel",         //检索结果面板
          enableAutoPan : true,     //自动平移
          searchTypes   :[
            BMAPLIB_TAB_SEARCH,   //周边检索
            BMAPLIB_TAB_TO_HERE,  //到这里去
            BMAPLIB_TAB_FROM_HERE //从这里出发
          ]
        });
        var marker = new BMap.Marker(point);  // 创建标注
        marker.addEventListener("click", function(e){
          searchInfoWindow.open(marker);
        });
        map.addOverlay(marker);              // 将标注添加到地图中
      }
    }
  }
</script>
<style>
  #allmap{
    width:100%;
    height:100%;
  }
</style>
