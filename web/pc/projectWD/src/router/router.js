import Main from '@/views/Main.vue';

// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/login',
    name: 'login',
    meta: {
        title: 'Login - 登录'
    },
    component: () => import('@/views/login.vue')
};

export const page404 = {
    path: '/*',
    name: 'error-404',
    meta: {
        title: '404-页面不存在'
    },
    component: () => import('@/views/error-page/404.vue')
};

export const page403 = {
    path: '/403',
    meta: {
        title: '403-权限不足'
    },
    name: 'error-403',
    component: () => import('@//views/error-page/403.vue')
};

export const page500 = {
    path: '/500',
    meta: {
        title: '500-服务端错误'
    },
    name: 'error-500',
    component: () => import('@/views/error-page/500.vue')
};

export const locking = {
    path: '/locking',
    name: 'locking',
    component: () => import('@/views/main-components/lockscreen/components/locking-page.vue')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/home',
    component: Main,
    children: [
        { path: 'home', title: {i18n: 'home'}, name: 'home_index', component: () => import('@/views/home/home.vue') },
        { path: 'ownspace', title: '个人中心', name: 'ownspace_index', component: () => import('@/views/own-space/own-space.vue') },
        { path: 'message', title: '消息中心', name: 'message_index', component: () => import('@/views/message/message.vue') },
        { path: 'historyTarck', title: '历史轨迹', name: 'historyTarck_index', component: () => import('@/views/whdx/historyTarck') }
    ]
};

// 作为Main组件的子页面展示并且在左侧菜单显示的路由写在appRouter里
export let appRouter = [
	{
		path: '/system',
        icon: 'android-car',
        name: 'system',
        title: '系统管理',
        component: Main,
        children: [
			{
				path: 'system-user',
				icon: 'android-clipboard',
				name: 'system-user',
				title: '用户管理',
				component: () => import('@/views/whdx/system/system-user')
			},
			{
				path: 'system-role',
				icon: 'android-clipboard',
				name: 'system-role',
				title: '角色管理',
				component: () => import('@/views/whdx/system/system-role')
			},
			{
				path: 'system-framework',
				icon: 'android-clipboard',
				name: 'system-framework',
				title: '组织机构',
				component: () => import('@/views/whdx/system/system-framework')
			},
			{
				path: 'system-dictionary',
				icon: 'android-clipboard',
				name: 'system-dictionary',
				title: '字典管理',
				component: () => import('@/views/whdx/system/system-dictionary')
			},
			{
				path: 'system-ITSM',
				icon: 'android-clipboard',
				name: 'system-ITSM',
				title: '服务管理',
				component: () => import('@/views/whdx/system/system-ITSM')
			},
			{
				path: 'system-function',
				icon: 'android-clipboard',
				name: 'system-function',
				title: '功能管理',
				component: () => import('@/views/whdx/system/system-function')
			},
			{
				path: 'system-daily',
				icon: 'android-clipboard',
				name: 'system-daily',
				title: '日志管理',
				component: () => import('@/views/whdx/system/system-daily')
			},
			{
				path: 'system-suggestions',
				icon: 'android-clipboard',
				name: 'system-suggestions',
				title: '意见反馈',
				component: () => import('@/views/whdx/system/system-suggestions')
			},
			{
				path: 'system-notice',
				icon: 'android-clipboard',
				name: 'system-notice',
				title: '设备终端',
				component: () => import('@/views/whdx/system/system-notice')
			},
			{
				path: 'system-propelling',
				icon: 'android-clipboard',
				name: 'system-propelling',
				title: '自能站牌',
				component: () => import('@/views/whdx/system/system-propelling')
			},
			{
				path: 'system-advertising',
				icon: 'android-clipboard',
				name: 'system-advertising',
				title: '活动管理',
				component: () => import('@/views/whdx/system/system-advertising')
			}
		]
	},
	{
		path: '/VehicleScheduling',
        icon: 'android-car',
        name: 'VehicleScheduling',
        title: '车辆调度',
        component: Main,
        children:[
        	{
        		path: 'vehicle-management',
				icon: 'android-clipboard',
				name: 'vehicle-management',
				title: '车辆管理',
				component: () => import('@/views/whdx/VehicleScheduling/vehicle-management')
        	},
        	{
        		path:'/',
				icon: 'android-clipboard',
				name: 'TemporaryCarManagement',
				title: '临时车管理',
				component: () => import('@/views/whdx/VehicleScheduling/TemporaryCarManagement/index'),
				children: [
					{
						path: 'TemporaryCarManagement/UnitManagement',
						icon: 'android-clipboard',
						name: 'UnitManagement',
						title: '单位管理',
						component: () => import('@/views/whdx/VehicleScheduling/TemporaryCarManagement/UnitManagement')
					},
					{
						path: 'TemporaryCarManagement/VehicleManagement',
						icon: 'android-clipboard',
						name: 'VehicleManagement',
						title: '车辆管理',
						component: () => import('@/views/whdx/VehicleScheduling/TemporaryCarManagement/VehicleManagement')
					},
				]
			},
			{
				path: 'DriverManagement',
				icon: 'android-clipboard',
				name: 'DriverManagement',
				title: '驾驶员管理',
				component: () => import('@/views/whdx/VehicleScheduling/DriverManagement')
			},
			{
				path: 'FleetManagement',
				icon: 'android-clipboard',
				name: 'FleetManagement',
				title: '车队管理',
				component: () => import('@/views/whdx/VehicleScheduling/FleetManagement')
			},
			{
				path: 'ElectronicFence',
				icon: 'android-clipboard',
				name: 'ElectronicFence',
				title: '电子围栏',
				component: () => import('@/views/whdx/VehicleScheduling/ElectronicFence')
			},
			{
				path: 'AccidentManagement',
				icon: 'android-clipboard',
				name: 'AccidentManagement',
				title: '事故管理',
				component: () => import('@/views/whdx/VehicleScheduling/AccidentManagement')
			},
			{
				path: 'OverspeedLimit',
				icon: 'android-clipboard',
				name: 'OverspeedLimit',
				title: '超速限定',
				component: () => import('@/views/whdx/VehicleScheduling/OverspeedLimit')
			},
			{
				path: 'CloudVideo',
				icon: 'android-clipboard',
				name: 'CloudVideo',
				title: '云视频库',
				component: () => import('@/views/whdx/VehicleScheduling/CloudVideo')
			},
        	{
        		path:'/',
				icon: 'android-clipboard',
				name: 'ShuttleBus',
				title: '班车管理',
				component: () => import('@/views/whdx/VehicleScheduling/ShuttleBus/index'),
				children: [{
						path: 'ShuttleBus/SiteMaintenance',
						icon: 'android-clipboard',
						name: 'SiteMaintenance',
						title: '站点维护',
						component: () => import('@/views/whdx/VehicleScheduling/ShuttleBus/SiteMaintenance')
					},
					{
						path: 'ShuttleBus/LineMaintenance',
						icon: 'android-clipboard',
						name: 'LineMaintenance',
						title: '线路维护',
						component: () => import('@/views/whdx/VehicleScheduling/ShuttleBus/LineMaintenance')
					},
					{
						path: 'ShuttleBus/Scheduling',
						icon: 'android-clipboard',
						name: 'Scheduling',
						title: '车辆排班',
						component: () => import('@/views/whdx/VehicleScheduling/ShuttleBus/Scheduling')
					}
				]
			},
      		{
        		path:'/',
				icon: 'android-clipboard',
				name: 'ScManage',
				title: '校巴管理',
				component: () => import('@/views/whdx/VehicleScheduling/ScManage/index'),
				children: [{
		            path: 'ScManage/Sc_SiteMaintenance',
		            icon: 'android-clipboard',
		            name: 'Sc_SiteMaintenance',
		            title: '站点维护',
		            component: () => import('@/views/whdx/VehicleScheduling/ScManage/Sc_SiteMaintenance')
		          },
		          {
		            path: 'ScManage/Sc_LineMaintenance',
		            icon: 'android-clipboard',
		            name: 'Sc_LineMaintenance',
		            title: '线路维护',
		            component: () => import('@/views/whdx/VehicleScheduling/ScManage/Sc_LineMaintenance')
		          },
		          {
		            path: 'ScManage/Sc_Scheduling',
		            icon: 'android-clipboard',
		            name: 'Sc_Scheduling',
		            title: '校巴排班',
		            component: () => import('@/views/whdx/VehicleScheduling/ScManage/Sc_Scheduling')
      					}
      				]
      },
			{
        		path:'/',
				icon: 'android-clipboard',
				name: 'OrderManagement',
				title: '订单管理',
				component: () => import('@/views/whdx/VehicleScheduling/OrderManagement/index'),
				children: [{
						path: 'OrderManagement/Establish',
						icon: 'android-clipboard',
						name: 'Establish',
						title: '创建订单',
						component: () => import('@/views/whdx/VehicleScheduling/OrderManagement/Establish')
					},
					{
						path: 'OrderManagement/ToExamine',
						icon: 'android-clipboard',
						name: 'ToExamine',
						title: '订单审核',
						component: () => import('@/views/whdx/VehicleScheduling/OrderManagement/ToExamine')
					},
					{
						path: 'OrderManagement/Consult',
						icon: 'android-clipboard',
						name: 'Consult',
						title: '订单查阅',
						component: () => import('@/views/whdx/VehicleScheduling/OrderManagement/Consult')
					},
					{
						path: 'OrderManagement/Assignment',
						icon: 'android-clipboard',
						name: 'Assignment',
						title: '订单分派',
						component: () => import('@/views/whdx/VehicleScheduling/OrderManagement/Assignment')
					},
					{
						path: 'OrderManagement/Confirm',
						icon: 'android-clipboard',
						name: 'Confirm',
						title: '订单确认',
						component: () => import('@/views/whdx/VehicleScheduling/OrderManagement/Confirm')
					}
				]
			},
        ]
	},
	{
		path: '/FinancialSettlement',
        icon: 'android-car',
        name: 'FinancialSettlement',
        title: '财务结算',
        component: Main,
        children:[
        	{
        		path: 'ReceivablesManagement',
		        icon: 'android-car',
		        name: 'ReceivablesManagement',
		        title: '收款管理',
		        component: () => import('@/views/whdx/FinancialSettlement/ReceivablesManagement')
			},
        	{
        		path: 'PaymentManagement',
		        icon: 'android-car',
		        name: 'PaymentManagement',
		        title: '付款管理',
		        component: () => import('@/views/whdx/FinancialSettlement/PaymentManagement')
			},
			{
        		path: 'AccountingFormula',
		        icon: 'android-car',
		        name: 'AccountingFormula',
		        title: '核算公式',
		        component: () => import('@/views/whdx/FinancialSettlement/AccountingFormula')
			},
			{
        		path: 'Reimbursement',
		        icon: 'android-car',
		        name: 'Reimbursement',
		        title: '报销记账',
		        component: () => import('@/views/whdx/FinancialSettlement/Reimbursement')
			}
		]
	},
	{
		path: '/Echarts',
        icon: 'android-car',
        name: 'Echarts',
        title: '数据报表',
        component: Main,
        children:[
        	{
        		path: 'SafeDriving',
		        icon: 'android-car',
		        name: 'SafeDriving',
		        title: '安全驾驶',
		        component: () => import('@/views/whdx/Echarts/SafeDriving')
			},
        	{
        		path: 'OrderStatistics',
		        icon: 'android-car',
		        name: 'OrderStatistics',
		        title: '订单统计',
		        component: () => import('@/views/whdx/Echarts/OrderStatistics')
			},
			{
        		path: 'BusStatistics',
		        icon: 'android-car',
		        name: 'BusStatistics',
		        title: '班车统计',
		        component: () => import('@/views/whdx/Echarts/BusStatistics')
			},
			{
        		path: 'TrafficStatistics',
		        icon: 'android-car',
		        name: 'TrafficStatistics',
		        title: '出车统计',
		        component: () => import('@/views/whdx/Echarts/TrafficStatistics')
			},
			{
        		path: 'TerminalAnomaly',
		        icon: 'android-car',
		        name: 'TerminalAnomaly',
		        title: '终端异常',
		        component: () => import('@/views/whdx/Echarts/TerminalAnomaly')
			},
			{
        		path: 'VehicleDetails',
		        icon: 'android-car',
		        name: 'VehicleDetails',
		        title: '年审提醒',
		        component: () => import('@/views/whdx/Echarts/VehicleDetails')
			},
			{
        		path: 'OverspeedStatistics',
		        icon: 'android-car',
		        name: 'OverspeedStatistics',
		        title: '超速统计',
		        component: () => import('@/views/whdx/Echarts/OverspeedStatistics')
			},
			{
        		path: 'VehicleAccident',
		        icon: 'android-car',
		        name: 'VehicleAccident',
		        title: '车辆事故',
		        component: () => import('@/views/whdx/Echarts/VehicleAccident')
			},
			{
        		path: 'CollectionStatistics',
		        icon: 'android-car',
		        name: 'CollectionStatistics',
		        title: '收款统计',
		        component: () => import('@/views/whdx/Echarts/CollectionStatistics')
			},
			{
        		path: 'PaymentStatistics',
		        icon: 'android-car',
		        name: 'PaymentStatistics',
		        title: '付款统计',
		        component: () => import('@/views/whdx/Echarts/PaymentStatistics')
			},
			{
        		path: 'ReimbursementStatistics',
		        icon: 'android-car',
		        name: 'ReimbursementStatistics',
		        title: '报销统计',
		        component: () => import('@/views/whdx/Echarts/ReimbursementStatistics')
			}
		]
	},
	{
		path: '/OperationMonitoring',
        icon: 'android-car',
        name: 'OperationMonitoring',
        title: '运营监控',
        component: Main,
        children:[
        	{
        		path: 'VehicleMonitoring',
		        icon: 'android-car',
		        name: 'VehicleMonitoring',
		        title: '车辆监控',
		        component: () => import('@/views/whdx/OperationMonitoring/VehicleMonitoring')
			},
        	{
        		path: 'BusMonitor',
		        icon: 'android-car',
		        name: 'BusMonitor',
		        title: '校巴监控',
		        component: () => import('@/views/whdx/OperationMonitoring/BusMonitor')
			}
		]
	},
//  {
//      path: '/error-page',
//      icon: 'android-sad',
//      title: '错误页面',
//      name: 'errorpage',
//      component: Main,
//      children: [
//          { path: 'index', title: '错误页面', name: 'errorpage_index', component: () => import('@/views/error-page/error-page.vue') }
//      ]
//  },
    {
    	path: '/',
    	meta: {
    	    title: '500-服务端错误'
    	},
    	name: 'error-page-500',
    	component: Main,
        children: [
            { path: '500', title: '服务器繁忙', name: 'errorpage_500', component: () => import('@/views/error-page/500.vue') }
        ]
    }
];

// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter,
    locking,
    ...appRouter,
    page500,
    page403,
    page404
];
