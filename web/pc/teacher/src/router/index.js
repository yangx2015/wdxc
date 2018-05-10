import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: () => import('@/views/home')
    },
    {
      path: '/login',
      name: 'Home',
      component: () => import('@/views/login')
    },
    {
      path: '/center',
      name: 'center',
      component: () => import('@/views/myCenter')
    },
    {
      path: '/feedBack',
      name: 'feedBack',
      component: () => import('@/views/feedback')
    },
    {
      path: '/list',
      name: 'list',
      component: () => import('@/views/pageList')
    }
  ]
})
