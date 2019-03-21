// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import * as FastClick from "fastclick"
import App from './App'
import router from './router'
import './styles/global.css'
import apis from './axios/config';
import http from './axios/index';
import Cookies from 'js-cookie';
import store from './store';
import local from './unit/local';
import MapUtil from './lib/MapUtil';
import VueRouter from 'vue-router';
import MintUI from 'mint-ui';
import 'mint-ui/lib/style.css';
import iView from 'iview';
Vue.use(iView);
import 'iview/dist/styles/iview.css';
Vue.use(MintUI);
Vue.use(VueRouter);
Vue.prototype.$http = http;
Vue.prototype.apis = apis;
Vue.prototype.cok = Cookies;
Vue.prototype.local = local;
Vue.prototype.MapUtil = MapUtil;
FastClick.attach(document.body)
Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router: router,
  store:store,
  components: { App },
  template: '<App/>'
})