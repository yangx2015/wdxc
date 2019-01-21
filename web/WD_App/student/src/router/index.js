import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/components/HelloWorld')
    },{
      path: '/index',
      name: 'index',
      component: () => import('@/pages/index')
    },{
      path: '/welcome',
      name: 'welcome',
      component: () => import('@/pages/welcome')
    },{
      path: '/xlMap',
      name: 'xlMap',
      component: () => import('@/pages/xlMap')
    },{
      path: '/xlMess',
      name: 'xlMess',
      component: () => import('@/pages/xlMess')
    }
  ]
})
