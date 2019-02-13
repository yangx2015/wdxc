// let ajaxUrl = 'http://172.20.10.2:10010';
// let ajaxUrl = 'http://192.168.43.206:10010';
// let ajaxUrl = 'http://192.168.123.12:10010';//羊
// let ajaxUrl ='http://192.168.123.13:8080'; // 陈伟
// let ajaxUrl ='http://192.168.123.6:10010';//宋
// let ajaxUrl = "http://10.113.4.71:9090/biz";
let ajaxUrl = "http://47.98.39.45:9099"
export default {
  NETWORK_ERR_STR: '网络加载异常!',
  // url:'http://192.168.3.234:10010',
  // url:'http://47.98.39.45:9986/biz',
  url: ajaxUrl,
  upFile: ajaxUrl + '/upload',
  // getImgUrl: 'http://192.168.3.35:8889'+'/img/',
  getImgUrl: ajaxUrl + '/img/',
  SWIPER:'/put/xl/getadsense', // 轮播图
  STATIONCODE:'/put/xl/getStationInfo',//附近站点 lng:114,lat:30
  ZDMESS:'/put/xl/getzdcl',//线路站点信息  435390474602151936

  ALLLINE:'/put/xl/getAll',//线路类型 字段：lx  参数：30校巴 20班车  ‘当前项目 只需要 此接口的班车线路信息’
  NEXTBUS:'/put/xl/getNextCars',//下一俩车到站 xlId zdId
  XLMAP:'/put/xl/getBusPositions',//地图展示点

  PROPOSAL:'/put/yj/save',//意见反馈 yjLx 00意见 10反馈 20投诉
  PROPList:'/put/yj/pager',//意见列表

}
