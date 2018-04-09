import Vue from 'vue';
import iView from 'iview';
import Util from '../libs/util';
import VueRouter from 'vue-router';
import Cookies from 'js-cookie';
import {routers, otherRouter, appRouter} from './router';
import menuList from '../data/list'
Vue.use(VueRouter);

// 路由配置
const RouterConfig = {
    // mode: 'history',
    routes: routers
};

export const router = new VueRouter(RouterConfig);
function hasPermission(list,name){
    for(let r of menuList.menuList){
        if (r.name === name) {
            return true;
        }
    }
    return false;
}
router.beforeEach((to, from, next) => {
    iView.LoadingBar.start();
    if(to.name=='login'||(to.name!='login'&&Cookies.get('usermess'))){
        // if (!hasPermission(to.name) && to.name !== 'error-403' && to.name !== 'home_index'){
        //     next({
        //         name: 'error-403'
        //     });
        //     return;
        // }
    	next()
    }else{
    	next({
            name: 'login'
        });
    }
//	next()
});
router.afterEach((to) => {
    Util.openNewPage(router.app, to.name, to.params, to.query);
    iView.LoadingBar.finish();
    window.scrollTo(0, 0);
});
