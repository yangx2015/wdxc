<template>
  <div :style="{height:AF.getPageHeight()+'px',background:''}">
    <router-view/>
  </div>
</template>
<script>
  import moment from 'moment'

  import SideMenu from './components/side-menu'
  import HeaderBar from './components/header-bar'
  import TagsNav from './components/tags-nav'
  import User from './components/user'
  import Fullscreen from './components/fullscreen'
  import Language from './components/language'
  import { mapMutations, mapActions} from 'vuex'
  import {getNewTagList, getNextName} from '@/libs/util'
  import minLogo from '@/assets/images/logo-minN.png'
  import maxLogo from '@/assets/images/logoNMax8.png'
  import './main.less'
  export default {
    name: 'Main',
    components: {
      SideMenu,
      HeaderBar,
      Language,
      TagsNav,
      Fullscreen,
      User
    },
    data() {
      return {
        textTime:'',
        collapsed: false,
        minLogo,
        maxLogo,
        isFullscreen: false
      }
    },
    computed: {
      tagNavList() {
        return this.$store.state.app.tagNavList
      },
      tagRouter() {
        return this.$store.state.app.tagRouter
      },
      userAvator() {
        return this.$store.state.user.avatorImgPath
      },
      cacheList() {
        return this.tagNavList.length ? this.tagNavList.filter(item => !(item.meta && item.meta.notCache)).map(item => item.name) : []
      },
      menuList() {
        return this.$store.getters.menuList
        // return []
        // return JSON.parse(localStorage.getItem('menuList'))
      },
      menuArr(){
        return this.$store.state.app.permissionMenuList
      },
      local() {
        return this.$store.state.app.local
      }
    },
    created(){
      this.textTime = moment().format('YYYY年MM-DD')
      if(this.menuArr.length == 0){
        this.setPermissionMenuList(JSON.parse(localStorage.getItem('menuList')))
        // this.menuList()
      }
    },
    methods: {
      ...mapMutations([
        'setBreadCrumb',
        'setTagNavList',
        'addTag',
        'setLocal',
        'setPermissionMenuList'
      ]),
      ...mapActions([
        'handleLogin'
      ]),
      turnToPage(name) {
        if (name.indexOf('isTurnByHref_') > -1) {
          window.open(name.split('_')[1])
          return
        }
        this.$router.push({
          name: name
        })
      },
      handleCollapsedChange(state) {
        this.collapsed = state
      },
      handleCloseTag(res, type, name) {
        const nextName = getNextName(this.tagNavList, name)
        this.setTagNavList(res)
        if (type === 'all') this.turnToPage('home')
        else if (this.$route.name === name) this.$router.push({name: nextName})
      },
      handleClick(item) {
        this.turnToPage(item.name)
      }
    },
    watch: {
      '$route'(newRoute) {
        this.setBreadCrumb(newRoute.matched)
        this.setTagNavList(getNewTagList(this.tagNavList, newRoute))
      }
    },
    mounted() {
      /**
       * @description 初始化设置面包屑导航和标签导航
       */
      this.setTagNavList()
      this.addTag(this.$store.state.app.homeRoute)
      this.setBreadCrumb(this.$route.matched)
      // 设置初始语言
      this.setLocal(this.$i18n.locale)
      // 文档提示
      // this.$Notice.info({
      //   title: '想快速上手，去看文档吧',
      //   duration: 0,
      //   render: (h) => {
      //     return h('p', {
      //       style: {
      //         fontSize: '13px'
      //       }
      //     }, [
      //       '点击',
      //       h('a', {
      //         attrs: {
      //           href: 'https://lison16.github.io/iview-admin-doc/#/',
      //           target: '_blank'
      //         }
      //       }, 'iview-admin2.0文档'),
      //       '快速查看'
      //     ])
      //   }
      // })
    }
  }
</script>
