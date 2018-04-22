<<<<<<< .mine
module.exports = {
	UPLOAD:'http://47.98.39.45:8080/biz/upload',
    STATIC_PATH:'http://47.98.39.45:9092/',
	VIDEO_PATH:'http://47.98.39.45:9091',
	USERROOT:{
		GET_MENU_LIST:'/api/gn/getUserFunctions',
        GET_MENU_TREE:'/api/gn/getMenuTree',
        INIT_MENU:'/api/gn/initMenu',
		MODIFY_PSD:'/api/yh/mdfPwd'
	},
	LOGIN:{
		QUERY:'login'
	},
	USER:{
		QUERY:'/api/yh/pager',//用户管理
		ADD:'/api/yh/save',
		CHANGE:'/api/yh/update',
		GIVE:'/api/js/modifyUserRoles',
		DELE:'/api/yh/removeIds'
	},
	ROLE:{
		QUERY:'/api/js/pager',//角色管理
		ALL:'/api/js/getAll',//角色管理
		ADD:'/api/js/save',
		CHANGE:'/api/js/update',
		GIVE:'/api/js/modifyUserRoles',
		DELE:'/api/js/removeIds',
        MODIFY_USER_ROLES:'/api/js/modifyUserRoles',
        GET_USER_ROLES:'/api/js/getUserRoles'
	},
	FRAMEWORK:{
		QUERY:'api/jg/pager',//机构管理
        ADD:'/api/jg/save',
        CHANGE:'/api/jg/update',
        DELE:'/api/jg/removeIds',
		GET_TREE:'/api/jg/getOrgTree'
	},
	DICTIONARY:{
	   QUERY:'/api/zd/pager',// 查询字典
       ADD:'/api/zd/save',// 新增字典
       CHANGE:'/api/zd/update',// 编辑字典
       DELE:'/api/zd/removeIds' // 删除字典
	},
    DICTIONARY_LIST:{
       QUERY:'/api/zdxm/pager',//查询字典项
       ADD:'/api/zdxm/save',// 新增字典项
       CHANGE:'/api/zdxm/update',// 编辑字典项
        DELE:'/api/zdxm/removeIds',// 删除字典项
        GET_BY_CONDITION:'/api/zdxm/getCondition', // 删除字典
    },
	ITMS:{
		QUERY:'api/fw/pager',//服务管理
        ADD:'/api/fw/save',
        CHANGE:'/api/fw/update',
        DELE:'/api/fw/removeIds'
	},
	FUNCTION:{
		QUERY:'api/gn/pager',//功能管理
        ADD:'/api/gn/save',
        CHANGE:'/api/gn/update',
        DELE:'/api/gn/removeIds',
		GET_ORG_PERMISSION_TREE:'/api/gn/getOrgPermissionTree',
		GET_ALL_PERMISSION_TREE:'/api/gn/getAllPermissionTree',
		GET_ROLE_PERMISSION_TREE:'/api/gn/getRolePermissionTree',
		SET_ROLE_FUNCTIONS:'/api/gn/setRoleFunctions',
		GET_ROLE_FUNCTIONS:'/api/gn/getRoleFunctions'
	},
	DAILY:{
		QUERY:'api/rz/pager',//日志管理
        ADD:'/api/rz/save',
        CHANGE:'/api/rz/update',
        DELE:'/api/rz/removeIds'

	},
	SUGGES:{
		QUERY:'api/yj/pager',//意见管理
        CHANGE:'/api/yj/update',
	},
	ADVERTISING:{
		QUERY:'api/hd/pager',//活动管理
        ADD:'/api/hd/save',
        CHANGE:'/api/hd/update',
        DELE:'/api/hd/removeIds'
	},
	//******************车辆管理******************
	CLOUD:{//云视频库
		QUERY:'/api/spk/pager'
	},
	CLGL:{
		QUERY: '/api/cl/pager',
        ADD:   '/api/cl/save',
        CHANGE:'/api/cl/update',
        DELE:  '/api/cl/removeIds',
		GET_ORG_CAR_LIST:'/api/cl/getOrgCarList'
	},
	XL:{
		QUERY: '/api/xl/pager',
        ADD:   '/api/xl/save',
        CHANGE:'/api/xl/update',
        DELE:  '/api/xl/removeIds'
	},
	LSDW:{
		QUERY: '/api/lsdw/pager',
		ADD:   '/api/lsdw/save',
        CHANGE:'/api/lsdw/update',
		DELE:  '/api/lsdw/removeIds'
	},
	LSC:{
		QUERY: '/api/lsc/pager',
		ADD:   '/api/lsc/save',
        CHANGE:'/api/lsc/update',
		DELE:  '/api/lsc/removeIds'
	},
	JSY:{
		QUERY:  '/api/jsy/pager',
        ADD:    '/api/jsy/save',
        CHANGE: '/api/jsy/update',
        DELE:   '/api/jsy/removeIds',
        NOT_BIND_LIST:   '/api/jsy/notBindList',
	},
	CD:{
		QUERY:  '/api/cd/pager',
		ADD:    '/api/cd/save',
        CHANGE: '/api/cd/update',
		DELE:   '/api/cd/removeIds',
		GET_CONDITION:'/api/cd/getCondition'
	},
	SG:{
		QUERY:  '/api/sg/pager',
		ADD:    '/api/sg/save',
        CHANGE: '/api/sg/update',
		DELE:   '/api/sg/removeIds'
	},
	ZD:{
		QUERY:  '/api/clzd/pager',
		ADD:    '/api/clzd/save',
        CHANGE: '/api/clzd/update',
		DELE:   '/api/clzd/removeIds',
		GET_ALL:'/api/clzd/getAll',
		GET_BY_ROUTE_ID:'/api/clzd/getByXlId'
	},
	PB:{//校巴排版
		QUERY:  '/api/pb/xbpb/',
	},
	XLPBXX:{//车辆排班信息
		QUERY: '/api/pb/xbbjpb/',
		ADD:    '/api/pb/save',
        DELE:   '/api/pb/removeIds',
        deleteByXlAndCl:   '/api/pb/deleteByXlAndCl',
	},
	XBDT:{//校巴实时动态
		QUERY: '/api/clzd/getzdcl/',
	},
	CS:{//超速
		QUERY:  '/api/cssd/pager',
		ADD:    '/api/cssd/save',
        CHANGE: '/api/cssd/update',
		DELE:   '/api/cssd/removeIds'
	},
	ZNZP:{//智能站牌
		QUERY:  '/api/znzp/pager',
		ADD:    '/api/znzp/save',
        CHANGE: '/api/znzp/update',
		DELE:   '/api/znzp/removeIds'
	},
	ZDGL:{//终端管理
		QUERY:  '/api/zdgl/pager',
		ADD:    '/api/zdgl/save',
        CHANGE: '/api/zdgl/update',
		DELE:   '/api/zdgl/removeIds'
	},
	CLJK:{
		QUERY:'/api/cl/InitClGps'
	}
}




















=======
module.exports = {
	UPLOAD:'http://47.98.39.45:8080/biz/upload',
    STATIC_PATH:'http://47.98.39.45:9092/',
	VIDEO_PATH:'http://47.98.39.45:9091',
	USERROOT:{
		GET_MENU_LIST:'/api/gn/getUserFunctions',
        GET_MENU_TREE:'/api/gn/getMenuTree',
        INIT_MENU:'/api/gn/initMenu',
		MODIFY_PSD:'/api/yh/mdfPwd'
	},
	LOGIN:{
		QUERY:'login'
	},
	USER:{
		QUERY:'/api/yh/pager',//用户管理
		ADD:'/api/yh/save',
		CHANGE:'/api/yh/update',
		GIVE:'/api/js/modifyUserRoles',
		DELE:'/api/yh/removeIds'
	},
	ROLE:{
		QUERY:'/api/js/pager',//角色管理
		ALL:'/api/js/getAll',//角色管理
		ADD:'/api/js/save',
		CHANGE:'/api/js/update',
		GIVE:'/api/js/modifyUserRoles',
		DELE:'/api/js/removeIds',
        MODIFY_USER_ROLES:'/api/js/modifyUserRoles',
        GET_USER_ROLES:'/api/js/getUserRoles'
	},
	FRAMEWORK:{
		QUERY:'api/jg/pager',//机构管理
        ADD:'/api/jg/save',
        CHANGE:'/api/jg/update',
        DELE:'/api/jg/removeIds',
		GET_TREE:'/api/jg/getOrgTree'
	},
	DICTIONARY:{
	   QUERY:'/api/zd/pager',// 查询字典
       ADD:'/api/zd/save',// 新增字典
       CHANGE:'/api/zd/update',// 编辑字典
       DELE:'/api/zd/removeIds' // 删除字典
	},
    DICTIONARY_LIST:{
       QUERY:'/api/zdxm/pager',//查询字典项
       ADD:'/api/zdxm/save',// 新增字典项
       CHANGE:'/api/zdxm/update',// 编辑字典项
        DELE:'/api/zdxm/removeIds',// 删除字典项
        GET_BY_CONDITION:'/api/zdxm/getCondition', // 删除字典
    },
	ITMS:{
		QUERY:'api/fw/pager',//服务管理
        ADD:'/api/fw/save',
        CHANGE:'/api/fw/update',
        DELE:'/api/fw/removeIds'
	},
	FUNCTION:{
		QUERY:'api/gn/pager',//功能管理
        ADD:'/api/gn/save',
        CHANGE:'/api/gn/update',
        DELE:'/api/gn/removeIds',
		GET_ORG_PERMISSION_TREE:'/api/gn/getOrgPermissionTree',
		GET_ALL_PERMISSION_TREE:'/api/gn/getAllPermissionTree',
		GET_ROLE_PERMISSION_TREE:'/api/gn/getRolePermissionTree',
		SET_ROLE_FUNCTIONS:'/api/gn/setRoleFunctions',
		GET_ROLE_FUNCTIONS:'/api/gn/getRoleFunctions'
	},
	DAILY:{
		QUERY:'api/rz/pager',//日志管理
        ADD:'/api/rz/save',
        CHANGE:'/api/rz/update',
        DELE:'/api/rz/removeIds'

	},
	SUGGES:{
		QUERY:'api/yj/pager',//意见管理
        CHANGE:'/api/yj/update',
	},
	ADVERTISING:{
		QUERY:'api/hd/pager',//活动管理
        ADD:'/api/hd/save',
        CHANGE:'/api/hd/update',
        DELE:'/api/hd/removeIds'
	},
	//******************车辆管理******************
	CLOUD:{//云视频库
		QUERY:'/api/spk/pager'
	},
	CLGL:{
		QUERY: '/api/cl/pager',
        ADD:   '/api/cl/save',
        CHANGE:'/api/cl/update',
        DELE:  '/api/cl/removeIds',
		GET_ORG_CAR_LIST:'/api/cl/getOrgCarList',
		GPS_HITSOR:'/api/clsbyxsjjl/history'
	},
	XL:{
		QUERY: '/api/xl/pager',
        ADD:   '/api/xl/save',
        CHANGE:'/api/xl/update',
        DELE:  '/api/xl/removeIds'
	},
	LSDW:{
		QUERY: '/api/lsdw/pager',
		ADD:   '/api/lsdw/save',
        CHANGE:'/api/lsdw/update',
		DELE:  '/api/lsdw/removeIds'
	},
	LSC:{
		QUERY: '/api/lsc/pager',
		ADD:   '/api/lsc/save',
        CHANGE:'/api/lsc/update',
		DELE:  '/api/lsc/removeIds'
	},
	JSY:{
		QUERY:  '/api/jsy/pager',
        ADD:    '/api/jsy/save',
        CHANGE: '/api/jsy/update',
        DELE:   '/api/jsy/removeIds',
        NOT_BIND_LIST:   '/api/jsy/notBindList',
	},
	CD:{
		QUERY:  '/api/cd/pager',
		ADD:    '/api/cd/save',
        CHANGE: '/api/cd/update',
		DELE:   '/api/cd/removeIds',
		GET_CONDITION:'/api/cd/getCondition'
	},
	SG:{
		QUERY:  '/api/sg/pager',
		ADD:    '/api/sg/save',
        CHANGE: '/api/sg/update',
		DELE:   '/api/sg/removeIds'
	},
	ZD:{
		QUERY:  '/api/clzd/pager',
		ADD:    '/api/clzd/save',
        CHANGE: '/api/clzd/update',
		DELE:   '/api/clzd/removeIds',
		GET_ALL:'/api/clzd/getAll',
		GET_BY_ROUTE_ID:'/api/clzd/getByXlId'
	},
	PB:{//校巴排版
		QUERY:  '/api/pb/xbpb/',
	},
	XLPBXX:{//车辆排班信息
		QUERY: '/api/pb/xbbjpb/',
//		ADD:    '/api/pb/save',
		ADD:    '/api/pb/savepb',
//      DELE:   '/api/pb/removeIds',
        DELE:   '/api/pb/deleteByXlAndCl',
        CARLIST:'api/pb/getcllist',//其余车辆
        deleteByXlAndCl:   '/api/pb/deleteByXlAndCl',
	},
	XBDT:{//校巴实时动态
		QUERY: '/api/clzd/getzdcl/',
	},
	CS:{//超速
		QUERY:  '/api/cssd/pager',
//		ADD:    '/api/cssd/save',
		ADD:	'api/cssd/setCssds',
        CHANGE: '/api/cssd/update',
		DELE:   '/api/cssd/removeIds'
	},
	ZNZP:{//智能站牌
		QUERY:  '/api/znzp/pager',
		ADD:    '/api/znzp/save',
        CHANGE: '/api/znzp/update',
		DELE:   '/api/znzp/removeIds'
	},
	ZDGL:{//终端管理
		QUERY:  '/api/zdgl/pager',
		ADD:    '/api/zdgl/save',
        CHANGE: '/api/zdgl/update',
		DELE:   '/api/zdgl/removeIds'
	},
	DZWL:{//电子围栏
		QUERY:  '/api/dzwl/pager',
		ADD:    '/api/dzwl/save',
        CHANGE: '/api/dzwl/update',
		DELE:   '/api/dzwl/removeIds',
		GET_BY_CAR_ID:'/api/dzwl/getByCarId',
        setCarsDzwl:'/api/dzwl/setCarsDzwl'
	},
	CLJK:{
		QUERY:'/api/cl/InitClGps',
		GET_CAR_INFO:'/api/cl/getCarInfo'
	},
	CARTREE:{//车辆列表
		QUERY:'/api/cl/getcltj'
	},
	SBZDDZ:{//设备终端接口地址
		ADD:'/pub/intstruction/send'
	}
}
>>>>>>> .theirs