// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
//网络数据请求 参数配置
import apis from './axios/api';
//网络数据请求
import http from './axios/index';
import Mint from 'mint-ui';
import 'mint-ui/lib/style.css'
import '@/assets/css/box.less'
import iView from 'iview';
Vue.use(iView);
import 'iview/dist/styles/iview.css';

Vue.component(Header.name, Header);
Vue.use(Mint);
Vue.prototype.apis = apis;
import { Button } from 'mint-ui';
Vue.component(Button.name, Button);
Vue.prototype.$http = http;

import { Swipe, SwipeItem } from 'mint-ui';
Vue.component(Swipe.name, Swipe);
Vue.component(SwipeItem.name, SwipeItem);

import { Navbar, TabItem } from 'mint-ui';

Vue.component(Navbar.name, Navbar);
Vue.component(TabItem.name, TabItem);

import { Header } from 'mint-ui';

Vue.component(Header.name, Header);

Vue.config.productionTip = false;
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
