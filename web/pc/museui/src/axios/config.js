module.exports = {
  ZDMESS:'/put/xl/getzdcl',//线路站点信息  435390474602151936
  XLMAP:'/put/xl/getBusPositions',//地图展示点
  STATIONCODE:'/put/xl/getStationInfo',//附近站点 lng:114,lat:30
	SWIPER:{
		QUERTY:'/put/xl/getadsense'//轮播图片
	},
	LINE:{
		QUERTY:'/put/xl/getAll'//线路信息
	},
	LINEMESS:{
		QUERTY:'/put/xl/getzdcl'//车辆到站信息
	},
  FEEDBACK:{//建议、投诉
    QUERTY:'put/yj/save',
    LIST:'put/yj/pager'
  },
  MAPMDATA:{
    QUERTY:'/put/xl/getStationGpsList'
  },
  GETCAR:{
    QUERTY:'/put/xl/getNextCars'
  }
}
