webpackJsonp([13],{

/***/ 100:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__ = __webpack_require__(101);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__app_ui__ = __webpack_require__(163);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__app_ui___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1__app_ui__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__static_css_box_less__ = __webpack_require__(174);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__static_css_box_less___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2__static_css_box_less__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__static_css_color_less__ = __webpack_require__(175);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__static_css_color_less___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3__static_css_color_less__);

__WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].networkTimeout = NaN;
__WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].networkBaseUrl = 'http://47.98.39.45:9099';




var options = {
  app: __WEBPACK_IMPORTED_MODULE_1__app_ui___default.a,
  beforeEach: function beforeEach(to, from, next) {
    console.log('去', to);
    console.log('来', from);
    next();
  }
};

__WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].extend({
  // 微信js---初始化
  getWxJs: function getWxJs() {
    // 微信js初始化 
    var script = document.createElement("script");
    script.type = "text/javascript";
    script.src = "./static/utils/jweixin-1.2.0.js";
    document.body.appendChild(script);

    script.onload = function () {
      // 微信js初始化 回调函数
      console.log('*****wx', wx);

      // 微信js初始化成功后引用 微信功能方法
      __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].getApp().wxUtil = __webpack_require__(176).default;

      //获取Code 直
      var authCode = __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].getApp().wxUtil.getQueryString("code");
      console.log('获取code', authCode);

      if (authCode) {

        // 获取Openid
        __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].getApp().wxUtil.vueParent = this;
        __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].getApp().wxUtil.getOpenid(authCode, function (res) {
          console.log('openid-------', res);
          localStorage.setItem("openid", res); //存储openid
          __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].getApp().wxUtil.initConfig(); //执行 微信 config
        });
      } else {
        return;
      }
    };
  },

  //ajax数据请求 基于 ui.request()方法 二次封装
  //参数传入 method    -->"请求方式：'POST','GET',……"
  //        url       -->"网络数据请求地址"
  //        data      -->"网络数据请求的参数传入 ：{key : val} *** 无参数传入时 {} "
  //        callback  -->"回调函数 网络数据返回"
  $http: function $http(method, url, data, callback) {
    //网路数据请求

    var token = localStorage.getItem("token");
    if (token != null && token != '' && token != undefined) {
      __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].getApp().Ajax.header.token = token;
    }
    __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].request({
      // ui.getApp().Ajax.url+':'+ui.getApp().Ajax.port+
      url: url, //仅为示例，并非真实的接口地址
      data: data,
      header: __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].getApp().Ajax.header,
      method: method,
      success: function success(res) {
        console.log('请求成功');
        if (res.data.code !== 200) {
          __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].showToast({ title: res.data.message });
          __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].redirectTo({
            url: '/pages/login'
          });
        } else {
          callback && callback(res.data);
        }
      },
      fail: function fail(err) {
        console.log('请求失败');
        callback && callback(err.data);
        // },
        // complete:function(mess){
        //   console.log('请求结果')
        // callback && callback(mess.data);
      }
    });
  },
  pageHeight: function pageHeight(val) {
    return __WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].DEFAULT_CONTENT_HEIGHT + val;
  },
  pageWidth: function pageWidth() {
    return window.innerWidth;
  }
});

var config = {};
config.routes = [{
  path: '*',
  component: function component(resolve) {
    __webpack_require__.e/* require */(4).then(function() { var __WEBPACK_AMD_REQUIRE_ARRAY__ = [__webpack_require__(181)]; ((resolve).apply(null, __WEBPACK_AMD_REQUIRE_ARRAY__));}.bind(this)).catch(__webpack_require__.oe);
  },
  meta: {
    pageConfig: {
      "navigationBarTitleText": "404",
      "delay": false,
      "disableScroll": true
    }
  }
}, {
  path: '/touch-ui-start-page',
  component: __webpack_require__(178).default
}, {
  path: '/pages/index',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "列表",
      title: '列表',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-b2b901', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(0).then(__webpack_require__.bind(null, 182));
  }
}, {
  path: '/',
  redirect: '/pages/index'
}, {
  path: '/pages/login',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "登录",
      title: '登录',
      backgroundColor: '#fff',
      delay: false //延迟加载

    }, pageClass: 'page-e743b5', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(3).then(__webpack_require__.bind(null, 183));
  }
}, {
  path: '/pages/detail',
  meta: { pageConfig: {
      navigationBarTitleText: '详情'
    }, pageClass: 'page-d6da34', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(10).then(__webpack_require__.bind(null, 184));
  }
}, {
  path: '/pages/user',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarTextStyle: 'white',
      backgroundColor: '#F4F4F4'
    }, pageClass: 'page-fbda7f', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(6).then(__webpack_require__.bind(null, 185));
  }
}, {
  path: '/pages/xlMess',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#FF7F50",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "线路详情",
      title: '线路详情',
      backgroundColor: '#fff',
      delay: false,
      disableScroll: true //延迟加载

    }, pageClass: 'page-6e2e31', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(5).then(__webpack_require__.bind(null, 186));
  }
}, {
  path: '/pages/xlMap',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarBackgroundColor: "#26a2ff",
      navigationBarTextStyle: "white",
      navigationBarTitleText: "地图查看",
      title: '地图查看',
      backgroundColor: '#f2f2f2',
      delay: false //延迟加载

    }, pageClass: 'page-8308ff', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(7).then(__webpack_require__.bind(null, 187));
  }
}, {
  path: '/pages/welcome',
  meta: { pageConfig: {
      navigationBarTitleText: "欢迎",
      navigationStyle: 'custom'
    }, pageClass: 'page-6b343d', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(8).then(__webpack_require__.bind(null, 188));
  }
}, {
  path: '/pages/common/busline',
  meta: { pageConfig: {
      navigationStyle: 'custom'
    }, pageClass: 'page-6dd939', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(11).then(__webpack_require__.bind(null, 189));
  }
}, {
  path: '/pages/prop/add',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarTextStyle: "white",
      navigationBarTitleText: "新增意见反馈",
      title: '新增意见反馈',
      backgroundColor: '#fff',
      delay: false //延迟加载

    }, pageClass: 'page-e84941', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(1).then(__webpack_require__.bind(null, 190));
  }
}, {
  path: '/pages/prop/pages',
  meta: { pageConfig: {
      navigationStyle: 'custom',
      navigationBarTextStyle: "white",
      navigationBarTitleText: "意见反馈",
      title: '意见反馈',
      backgroundColor: '#fff',
      delay: false //延迟加载

    }, pageClass: 'page-c18ff9', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(9).then(__webpack_require__.bind(null, 191));
  }
}, {
  path: '/pages/money',
  meta: { pageConfig: {
      navigationBarTitleText: "费用详情"
    }, pageClass: 'page-80bf16', isTabBar: false },
  component: function component() {
    return __webpack_require__.e/* import() */(2).then(__webpack_require__.bind(null, 192));
  }
}];
config.window = { "scrollType": "div", "navigationBarBackgroundColor": "#ffffff", "navigationBarTextStyle": "black", "navigationBarBorderColor": "rgba(231, 231, 231, 0.6)" };
config.theme = { "theme-color": "#3399ff" };
config.template = undefined;
config.tabBar = {};
config.pageTabBars = [];

__WEBPACK_IMPORTED_MODULE_0_touchui_dist_ui__["a" /* default */].start(options, config);

/***/ }),

/***/ 156:
/***/ (function(module, exports) {

module.exports = {"zh-cn":{"ui.alert.button_text":"确定","ui.confirm.confirm_text":"确定","ui.confirm.cancel_text":"取消","ui.prompt.confirm_text":"确定","ui.prompt.cancel_text":"取消","ui.picker.confirm_text":"确定","ui.picker.cancel_text":"取消","ui.actionsheet.cancel_text":"取消","ui.loading.text":"加载中...","ui.cascader.loading_text":"加载中","ui.cascader.please_select":"请选择","ui.keyboard.space_text":"空格","ui.keyboard.complete_text":"完成","ui.countdown.done_text":"已结束","ui.requeststatus.loading":"加载中...","ui.requeststatus.nomore":"没有更多了...","ui.requeststatus.offline":"网络异常，点击刷新","ui.requeststatus.timeout":"请求超时，点击刷新","ui.requeststatus.error":"请求失败，点击刷新","ui.requeststatus.empty":"没有数据，点击刷新","ui.pulldownrefresh.pull":"下拉刷新","ui.pulldownrefresh.release":"释放刷新","ui.pulldownrefresh.refreshing":"正在刷新...","ui.pulldownrefresh.success":"刷新成功","ui.pulldownrefresh.cancel":"取消下拉","ui.pullupload.pull_load_more":"上拉加载更多","ui.pullupload.pull":"上拉加载","ui.pullupload.release":"释放加载","ui.pullupload.refreshing":"正在加载...","ui.pullupload.success":"加载成功","ui.pullupload.cancel":"取消加载","ui.smscode.getcode":"获取短信验证码","ui.smscode.run":"{%s}秒后重新获取","ui.smscode.reset":"重新获取验证码","ui.smscode.placeholder":"请输入验证码","ui.table.request_failed":"请求失败","ui.table.refresh":"刷新","ui.table.timeout":"网络超时","ui.table.offline":"网络无法连接，请检查您的网络","ui.table.empty":"结果为空","ui.table.need_login":"请先登录","ui.table.loading":"正在加载...","ui.cascader.select":"请选择","ui.calendar.head":"日 一 二 三 四 五 六","ui.calendar.yearsplit":"年","ui.calendar.monthsplit":"月","ui.countdown.over":"已结束"},"en":{"ui.alert.button_text":"OK","ui.confirm.confirm_text":"Confirm","ui.confirm.cancel_text":"Cancel","ui.prompt.confirm_text":"Confirm","ui.prompt.cancel_text":"Cancel","ui.picker.confirm_text":"Confirm","ui.picker.cancel_text":"Cancel","ui.actionsheet.cancel_text":"Cancel","ui.loading.text":"Loading...","ui.cascader.loading_text":"Loading","ui.cascader.please_select":"Please select","ui.keyboard.space_text":"Space","ui.keyboard.complete_text":"Complete","ui.countdown.done_text":"Done","ui.requeststatus.loading":"Loading...","ui.requeststatus.nomore":"No more data...","ui.requeststatus.offline":"Network error, tap to refresh","ui.requeststatus.timeout":"Network error, tap to refresh","ui.requeststatus.error":"Request failed, tap to refresh","ui.requeststatus.empty":"No data, tap to refresh","ui.pulldownrefresh.pull":"Pull refresh","ui.pulldownrefresh.release":"Release to refresh","ui.pulldownrefresh.refreshing":"Refreshing...","ui.pulldownrefresh.success":"Refresh success","ui.pulldownrefresh.cancel":"Cancel pull","ui.pullupload.pull_load_more":"Pull to load more","ui.pullupload.pull":"Pull load","ui.pullupload.release":"Release to load","ui.pullupload.refreshing":"Loading...","ui.pullupload.success":"Load success","ui.pullupload.cancel":"Cancel load","ui.smscode.getcode":"Get code","ui.smscode.run":"{%s} seconds","ui.smscode.reset":"Reset","ui.smscode.placeholder":"Please input code","ui.table.request_failed":"Request failed","ui.table.refresh":"Refresh","ui.table.timeout":"Timeout","ui.table.offline":"Can't connect to network","ui.table.empty":"No results","ui.table.need_login":"Please login first","ui.table.loading":"Loading...","ui.cascader.select":"Please Select","ui.calendar.head":"Sun Mon Tue Wen Thu Fri Sat","ui.calendar.yearsplit":"/","ui.calendar.monthsplit":"","ui.countdown.over":"Time's up"}}

/***/ }),

/***/ 163:
/***/ (function(module, exports, __webpack_require__) {

function injectStyle (ssrContext) {
  __webpack_require__(164)
  __webpack_require__(166)
}
var Component = __webpack_require__(88)(
  /* script */
  __webpack_require__(167),
  /* template */
  __webpack_require__(173),
  /* styles */
  injectStyle,
  /* scopeId */
  null,
  /* moduleIdentifier (server only) */
  null
)

module.exports = Component.exports


/***/ }),

/***/ 164:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 166:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 167:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray__ = __webpack_require__(27);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__ = __webpack_require__(13);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_tab_bar__ = __webpack_require__(168);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_icon__ = __webpack_require__(57);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_col__ = __webpack_require__(90);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_row__ = __webpack_require__(91);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_nav_bar__ = __webpack_require__(60);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_text__ = __webpack_require__(95);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_slide_menu__ = __webpack_require__(171);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_view__ = __webpack_require__(89);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__static_ajax_url__ = __webpack_require__(87);












/* harmony default export */ __webpack_exports__["default"] = ({
  components: {
    UiView: __WEBPACK_IMPORTED_MODULE_9_touchui_dist_components_view__["a" /* default */],
    UiSlideMenu: __WEBPACK_IMPORTED_MODULE_8_touchui_dist_components_slide_menu__["a" /* default */],
    UiText: __WEBPACK_IMPORTED_MODULE_7_touchui_dist_components_text__["a" /* default */],
    UiNavBar: __WEBPACK_IMPORTED_MODULE_6_touchui_dist_components_nav_bar__["a" /* default */],
    UiRow: __WEBPACK_IMPORTED_MODULE_5_touchui_dist_components_row__["a" /* default */],
    UiCol: __WEBPACK_IMPORTED_MODULE_4_touchui_dist_components_col__["a" /* default */],
    UiIcon: __WEBPACK_IMPORTED_MODULE_3_touchui_dist_components_icon__["a" /* default */],
    UiTabBar: __WEBPACK_IMPORTED_MODULE_2_touchui_dist_components_tab_bar__["a" /* default */]
  },

  config: {
    "pages": ["pages/index", "pages/login", "pages/detail", "pages/user", "pages/xlMess", "pages/xlMap", "pages/welcome", "pages/common/busline", "pages/prop/add", "pages/prop/pages", "pages/money"],
    "theme": {
      "theme-color": "#3399ff"
    },
    "window": {
      "scrollType": "div",
      "navigationBarBackgroundColor": "#ffffff",
      "navigationBarTextStyle": "black",
      "navigationBarBorderColor": "rgba(231, 231, 231, 0.6)"
    },
    // "tabBar": {
    //   "color": "#7a7e83",
    //   "selectedColor": "#39f",
    //   "borderColor": "rgba(231, 231, 231, 0.6)",
    //   "backgroundColor": "#fff",
    //   "list": [
    //     {
    //       "pagePath": "pages/index",
    //       "iconName": "home",
    //       "text": "首页"
    //     },
    //     {
    //       "pagePath": "pages/user",
    //       "iconName": "user",
    //       "text": "我的"
    //     }
    //   ]
    // },
    "networkBaseUrl": 'http://47.98.39.45:9099',
    // "networkBaseUrl":'http://10.113.4.71:8080/wechat',
    "networkTimeout": "3000"
  },
  data: function data() {
    return {
      //全局变量
      swiperIndex: 0,
      // ajax数据请求参数配置
      apis: __WEBPACK_IMPORTED_MODULE_10__static_ajax_url__["a" /* default */],
      Ajax: {
        url: __WEBPACK_IMPORTED_MODULE_10__static_ajax_url__["a" /* default */].url,
        port: __WEBPACK_IMPORTED_MODULE_10__static_ajax_url__["a" /* default */].port,
        header: {
          'Content-Type': 'application/x-www-form-urlencoded',
          'token': ''
        }
      },
      wxUtil: null, //微信 功能方法
      lineMess: {}, //线路信息
      station: {
        stationID: '',
        xlID: ''
      }, //站点ID

      globalData: {},

      orderMess: {}, //订单详情

      WIN_WIDTH: ui.WIN_WIDTH,
      tabIndex: 0,
      tabBarList: [],
      navStyle: {},
      tabBar: {},
      tabBarHeight: 56,
      isApp: ui.IS_APP,
      headerHeight: ui.DEFAULT_HEADER_HEIGHT,
      paths: [],
      barHeight: ui.STATUS_BAR_HEIGHT,
      showSlideMenu: false
    };
  },

  computed: {
    // 控制通用导航栏是否显示
    navigationBarVisible: function navigationBarVisible() {
      return this.winConfig.navigationStyle !== 'custom';
    },

    // 控制通用导航栏是否显示返回按钮（首页不显示，其他页显示）
    showBack: function showBack() {
      var homePath = this.paths[0];
      if (this.tabBar && this.tabBar.paths) {
        return this.tabBar.paths.indexOf(homePath) < 0;
      } else {
        return homePath !== this.$route.path;
      }
    },

    // 通用导航栏应用app.json和page.json的设置
    winConfig: function winConfig() {
      var window = this.$router.options.window;
      var config = __WEBPACK_IMPORTED_MODULE_1_babel_runtime_helpers_extends___default()({}, window, this.pageConfig);
      var color = config.navigationBarTextStyle || 'white';

      var scrollType = document.body.getAttribute('scroll-type');

      this.navStyle = {
        backgroundColor: config.navigationBarBackgroundColor,
        color: color,
        borderBottom: '1px solid ' + config.navigationBarBorderColor,
        backgroundImage: config.navigationBarBackgroundGradient,
        position: scrollType === 'body' ? 'fixed' : 'absolute'
      };

      if (this.navStyle.backgroundColor) {
        this.navStyle.backgroundImage = 'none';
      }

      if (config.navigationBarBorderColor) {
        this.navStyle.borderBottom = '1px solid ' + config.navigationBarBorderColor;
      } else {
        this.navStyle.borderBottom = '0';
      }
      return config;
    },

    // 控制底部标签栏是否显示
    isTabBar: function isTabBar() {
      return this.tabBarList && this.tabBarList.length > 0;
    },

    // 设置底部标签栏样式
    tabBarStyle: function tabBarStyle() {
      var style = {};
      if (this.navigationBarVisible && this.tabBar.position === 'top') {
        style.top = this.headerHeight + 'px';
      }
      var scrollType = document.body.getAttribute('scroll-type');
      style.position = scrollType === 'body' ? 'fixed' : 'absolute';
      return style;
    },
    bottom: function bottom() {
      return this.isTabBar ? this.tabBarHeight + 'px' : 0;
    },

    // 底部标签栏应用app.json和page.json的设置
    pageConfig: function pageConfig() {
      if (this.isTabBar) {
        return this.tabBarList[this.tabIndex].pageConfig;
      } else {
        return this.$route.meta.pageConfig || window.__$uiPageConfig;
      }
    }
  },
  watch: {
    '$route.path': function $routePath(path) {
      this.handleIosGesture();
      this.initTab();
      this.initBodyStyle();
    },
    wxUtil: function wxUtil(n, o) {
      //微信方法监听
      console.log('数据监听*-*-*-*-*-*-*-');
      if (n == null) {
        this.getWxJs();
      }
    }
  },
  // 初始化
  created: function created() {
    var _this = this;

    //微信js 初始化
    this.getWxJs();
    //微信js初始化END


    this.initTab();
    this.initPaths();
    this.$nextTick(function () {
      _this.handleIosGesture();
    });
  },

  methods: {
    handleNavIconTap: function handleNavIconTap() {
      this.showSlideMenu = !this.showSlideMenu;
    },

    // 初始化底部标签栏
    initTab: function initTab() {
      this.tabBar = this.getTabBar();
      this.tabBarList = this.tabBar.list;
      if (this.tabBar.paths) {
        this.tabIndex = this.tabBar.paths.indexOf(this.$route.path);
      }
    },

    // 初始化路由
    initPaths: function initPaths() {
      var routes = this.$router.options.routes;
      this.paths = routes.map(function (item) {
        return item.path;
      });
    },
    initBodyStyle: function initBodyStyle() {
      if (this.winConfig && this.winConfig.backgroundColor) {
        document.body.style.backgroundColor = this.winConfig.backgroundColor;
      }
    },

    // iOS是否禁用侧滑退出
    handleIosGesture: function handleIosGesture() {
      if (ui.IS_IOS) {
        ui.setPagePopGesture && ui.setPagePopGesture({
          openPagePopGesture: !this.pageConfig.disableIosGesture
        });
      }
    },

    // tabBar示例用到，底部标签栏切换前事件处理
    handleTabBeforeChange: function handleTabBeforeChange(_ref) {
      var id = _ref.id,
          index = _ref.index,
          next = _ref.next;

      next();
    },


    // tabBar示例用到，底部标签栏切换后事件处理
    handleTabChange: function handleTabChange(_ref2) {
      var id = _ref2.id,
          index = _ref2.index;
    },

    // 回退处理
    handleBack: function handleBack() {
      ui.navigateBack();
    },
    getTabBar: function getTabBar() {
      var tabBars = [this.$router.options.tabBar].concat(__WEBPACK_IMPORTED_MODULE_0_babel_runtime_helpers_toConsumableArray___default()(this.$router.options.pageTabBars));
      for (var i = 0; i < tabBars.length; i++) {
        if (tabBars[i].paths && tabBars[i].paths.indexOf(this.$route.path) > -1) {
          return tabBars[i];
        }
      }
      return {};
    }
  },
  mounted: function mounted() {}
});

/***/ }),

/***/ 173:
/***/ (function(module, exports) {

module.exports={render:function (){var _vm=this;var _h=_vm.$createElement;var _c=_vm._self._c||_h;
  return _c('div', {
    staticClass: "ui-view",
    attrs: {
      "id": "ui-app"
    }
  }, [_c('ui-slide-menu', {
    attrs: {
      "width": "280",
      "threshold": "30",
      "touch": _vm.pageConfig.touchSlideMenu
    },
    model: {
      value: (_vm.showSlideMenu),
      callback: function($$v) {
        _vm.showSlideMenu = $$v
      },
      expression: "showSlideMenu"
    }
  }, [_c('div', {
    staticClass: "menu ui-view",
    slot: "menu"
  }, [_c('ui-text', {
    staticStyle: {
      "color": "#fff",
      "margin": "10px"
    },
    attrs: {
      "block": ""
    }
  }, [_vm._v("侧滑菜单")])], 1), _vm._v(" "), _c('div', {
    staticClass: "ui-view",
    slot: "panel"
  }, [(_vm.pageConfig) ? _c('ui-nav-bar', {
    directives: [{
      name: "show",
      rawName: "v-show",
      value: (_vm.navigationBarVisible),
      expression: "navigationBarVisible"
    }],
    staticClass: "page-a08ae6",
    style: (_vm.navStyle)
  }, [_c('div', {
    staticClass: "ui-row",
    style: ({})
  }, [_c('div', {
    staticClass: "ui-col",
    style: ({
      'flex': '0 0 80px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content"
  }, [_c('span', {
    directives: [{
      name: "show",
      rawName: "v-show",
      value: (_vm.showBack),
      expression: "showBack"
    }],
    staticClass: "back",
    on: {
      "tap": _vm.handleBack
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "arrow-left",
      "font-size": "24"
    }
  }), _vm._v("返回\n          ")], 1), _vm._v(" "), _c('span', {
    directives: [{
      name: "show",
      rawName: "v-show",
      value: (!_vm.showBack),
      expression: "!showBack"
    }],
    staticClass: "back",
    on: {
      "tap": _vm.handleNavIconTap
    }
  }, [_c('ui-icon', {
    attrs: {
      "type": "list",
      "color": "#2B333B",
      "size": "16"
    }
  })], 1)])]), _vm._v(" "), _c('div', {
    staticClass: "nav-bar-center ui-col ui-col-align-center align-center",
    style: ({
      'flex': ("0 0 " + (_vm.WIN_WIDTH-160) + "px")
    }),
    attrs: {
      "content-direction": "row"
    }
  }, [_c('div', {
    staticClass: "ui-col-content align-center flex-row"
  }, [_c('div', {
    staticClass: "nav-bar-center-loading ui-view",
    staticStyle: {
      "display": "none"
    }
  }), _vm._v(" "), _c('span', {
    staticClass: "nav-bar-center-text"
  }, [_vm._v(_vm._s(_vm.$t(_vm.winConfig.navigationBarTitleText)))])])]), _vm._v(" "), _c('div', {
    staticClass: "ui-col",
    style: ({
      'flex': '0 0 80px'
    })
  }, [_c('div', {
    staticClass: "ui-col-content"
  })])])]) : _vm._e(), _vm._v(" "), _c('keep-alive', [(!_vm.isApp && _vm.pageConfig.cache) ? _c('router-view', {
    staticClass: "router-view"
  }) : _vm._e()], 1), _vm._v(" "), (_vm.isApp || !_vm.pageConfig.cache) ? _c('router-view', {
    staticClass: "router-view"
  }) : _vm._e(), _vm._v(" "), (_vm.isTabBar) ? _c('ui-tab-bar', {
    style: (_vm.tabBarStyle),
    attrs: {
      "id": _vm.tabBar.id,
      "group-id": _vm.tabBar.groupId,
      "icon-size": _vm.tabBar.iconSize,
      "font-size": _vm.tabBar.fontSize,
      "position": _vm.tabBar.position,
      "list": _vm.tabBarList,
      "paths": _vm.tabBar.paths,
      "background-color": _vm.tabBar.backgroundColor,
      "border-color": _vm.tabBar.borderColor,
      "color": _vm.tabBar.color,
      "selected-color": _vm.tabBar.selectedColor
    },
    on: {
      "beforechange": _vm.handleTabBeforeChange,
      "change": _vm.handleTabChange
    },
    model: {
      value: (_vm.tabIndex),
      callback: function($$v) {
        _vm.tabIndex = $$v
      },
      expression: "tabIndex"
    }
  }) : _vm._e()], 1)])], 1)
},staticRenderFns: []}

/***/ }),

/***/ 174:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 175:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 176:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jquery__ = __webpack_require__(177);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jquery___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_jquery__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__url__ = __webpack_require__(87);



// import wx from '#/static/ajax/jweixin-1.2.0.js'
// var wx = require('#/static/ajax/jweixin-1.2.0.js')
// import router from '@/router'
let wechatUtil = {}

wechatUtil.now = new Date();
wechatUtil.timestamp = parseInt(wechatUtil.now.getTime()/1000);
wechatUtil.appId = 'wxb01394ea85904296';
wechatUtil.token = '';
wechatUtil.sign = '';
wechatUtil.code = '';
wechatUtil.openid = '';
wechatUtil.nonceStr = randomString(16);
wechatUtil.baseUrl = __WEBPACK_IMPORTED_MODULE_1__url__["a" /* default */].url+':8080/biz/';
wechatUtil.authLoginUrl = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid='+wechatUtil.appId+'&redirect_uri='+__WEBPACK_IMPORTED_MODULE_1__url__["a" /* default */].url+'/wx&response_type=code&scope=snsapi_userinfo&state=debug&connect_redirect=1#wechat_redirect';

wechatUtil.afterReady = '';
//存储Vue对象，用来在微信方法中，可以调用vue内容
wechatUtil.vueParent = null;
wechatUtil.getQueryString = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
};

wechatUtil.getCode = ()=>{
    window.location.href = wechatUtil.authLoginUrl;
};
wechatUtil.getOpenid = (code,callback)=>{
    __WEBPACK_IMPORTED_MODULE_0_jquery___default.a.ajax({
        // url:wechatUtil.baseUrl+urls.wechat.getOpenid+"?code="+code,
        url:__WEBPACK_IMPORTED_MODULE_1__url__["a" /* default */].url+':8080/biz'+__WEBPACK_IMPORTED_MODULE_1__url__["a" /* default */].wechat.getOpenid+"?code="+code,
        type:'get',
        success:function(res){
            console.log(res);
            if (res.code == 200){
                wechatUtil.openid = res.message;
                callback && callback(res.message)
            }else {
              alert('ID获取失败！！！')
            }
        }
    })
};

wechatUtil.initConfig = ()=>{
    let curl = location.href.split('#')[0];
  // let url = wechatUtil.baseUrl+urls.wechat.getJsApiSign+"?&timestamp="+wechatUtil.timestamp+"&url="+encodeURIComponent(curl)+'&nonceStr='+wechatUtil.nonceStr;
  let url = __WEBPACK_IMPORTED_MODULE_1__url__["a" /* default */].url+':8080/biz'+__WEBPACK_IMPORTED_MODULE_1__url__["a" /* default */].wechat.getJsApiSign+"?&timestamp="+wechatUtil.timestamp+"&url="+encodeURIComponent(curl)+'&nonceStr='+wechatUtil.nonceStr;
  __WEBPACK_IMPORTED_MODULE_0_jquery___default.a.ajax({
    url:url,
    type:'get',
    success:function(res){
      if (res.code == 200){
        wechatUtil.sign = res.message;
        wechatUtil.config();
      }
    }
  })
};


wechatUtil.config = function(){
    wx.config({
        debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
        appId: wechatUtil.appId, // 必填，公众号的唯一标识
        timestamp: wechatUtil.timestamp, // 必填，生成签名的时间戳
        nonceStr: wechatUtil.nonceStr, // 必填，生成签名的随机串
        signature: wechatUtil.sign,// 必填，签名
        jsApiList: ['scanQRCode','chooseImage','uploadImage','previewImage','chooseWXPay'] // 必填，需要使用的JS接口列表
    });
    wechatUtil.refreshNonceStr();
};
wechatUtil.refreshNonceStr = ()=>{
    wechatUtil.nonceStr = randomString(16);
};

wechatUtil.pay = function(prepay,callback){
    wx.chooseWXPay({
        appId:prepay.appId,
        timestamp: prepay.timeStamp,// 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
        nonceStr: prepay.nonceStr, // 支付签名随机串，不长于 32 位
        package: prepay.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
        signType: prepay.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
        paySign: prepay.paySign, // 支付签名
        success: function (res) {
          callback && callback(res)
// 支付成功后的回调函数
        }
    });
};
wechatUtil.checkJsApi = ()=>{
  wx.checkJsApi({
    jsApiList: ['scanQRCode','chooseImage','uploadImage','previewImage','chooseWXPay'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
    success: function(res) {
      console.log(res);
      // 以键值对的形式返回，可用的api值true，不可用为false
      // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
    }
  });
};
wx.ready(function(){
    localStorage.setItem("projectType",true)//微信js加载成功
    if (typeof wechatUtil.afterReady == 'function'){
        wechatUtil.afterReady('wx');
        return;
    }
    //微信jsapi使用，不允许页面切换，所以这里使用router来做页面跳转，不再使用location强制跳页面
    if (wechatUtil.vueParent){
    //   wechatUtil.vueParent.$router.push({name:'indexName'});
    console.log('页面跳转')
      return;
    }
    wechatUtil.checkJsApi();
    //window.location.href = "/wx/";
    // chooseImage();
    // wechatUtil.qrScan();
    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
});

wx.error(function(res){
    console.log('error',res);
    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
});
//--------------------------------------------------------------------------
//以上功能方法 是调用微信开发功能的前期准备*******调用wechatUtil.getAccessToken()
//---------------------------------------------------------------------------
wechatUtil.qrScan = (callback)=>{//打开微信扫码功能
    wx.scanQRCode({
        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
        scanType: ["qrCode"], // 可以指定扫二维码还是一维码，默认二者都有
        success: function (res) {
            // alert(res)
            var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
            // alert(result);
            callback && callback(result);
        },
        fail : function(res) {
            console.log(res)
            // alert(JSON.stringify(res));
        }
    });
};
wechatUtil.chooseImage = (callback)=>{//拍照或从手机相册中选图接口
    console.log('chooseImage');
    wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            console.log(res);
            callback && callback(localIds);
        }
    });
};
wechatUtil.previewImage = ()=>{//预览图片接口
  wx.previewImage({
    current: '', // 当前显示图片的http链接
    urls: [] // 需要预览的图片http链接列表
  })
};
wechatUtil.uploadImage = (id , callback)=>{//上传图片接口
    wx.uploadImage({
        localId: id, // 需要上传的图片的本地ID，由chooseImage接口获得
        isShowProgressTips: 1, // 默认为1，显示进度提示
        success: function (res) {
          console.log(res);
          var serverId = res.serverId; // 返回图片的服务器端ID
          callback && callback(res)
        }
    });
};
wechatUtil.getMapCode = (callback)=>{
    wx.getLocation({
        type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
        success: function (res) {
            var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
            var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
            var speed = res.speed; // 速度，以米/每秒计
            var accuracy = res.accuracy; // 位置精度
            callback && callback(res)
        }
    });
};
function randomString(len) {
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = $chars.length;
    var pwd = '';
    for (let i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

/* harmony default export */ __webpack_exports__["default"] = (wechatUtil);

/***/ }),

/***/ 87:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
let ajaxUrl = "http://47.98.39.45:9099";
// let ajaxUrl = "http://10.113.4.70:8080";

/* harmony default export */ __webpack_exports__["a"] = ({
    url:ajaxUrl,
    SWIPER:{
		QUERTY:'/put/xl/getadsense'//轮播图片
	},
	LINE:{
		QUERTY:'/put/xl/getAll'//线路信息
	},
	LINEMESS:{
		QUERTY:'/put/xl/getzdcl'//车辆到站信息
	},
    LOGIN:{
        QUERTY:'/put/jsy/getjsy',//登录
    },
    MESLIST:{//订单列表
        QUERTY:'/put/dd/getsjlist'
    },
    LISTOK:{//行程确认
        CHANGE:'/put/dd/update'
    },
    GPS:{
        DOT:'/put/dd/getStartPointAndEndPoint'
    },
    INFO:'/put/jsy/getInfo',//用户信息
    PROPOSAL:'/put/yj/save',//意见反馈 yjLx 00意见 10反馈 20投诉
    PROPList:'/put/yj/pager',//意见列表
    HISTORYGJ: '/put/dd/getddgps'//历史轨迹
});

/***/ })

},[100]);