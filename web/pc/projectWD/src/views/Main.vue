<style lang="less">
    @import "./main.less";
</style>
<template>
    <div class="main" :class="{'main-hide-text': shrink}">
        <div class="sidebar-menu-con" :style="{width: shrink?'60px':'200px', overflow: shrink ? 'visible' : 'auto'}">
                <!--:theme="menuTheme"-->
            <shrinkable-menu
                :shrink="shrink"
                @on-change="handleSubmenuChange"
                :before-push="beforePush"
                :open-names="openedSubmenuArr"
                :menu-list="menuList">
                <div slot="top" class="logo-con">
                    <img v-show="!shrink"  src="../images/logo.png" key="max-logo" />
                    <img v-show="shrink" src="../images/logo-min.png" key="min-logo" />
                </div>
            </shrinkable-menu>
        </div>
        <div class="main-header-con" :style="{paddingLeft: shrink?'60px':'200px'}">
            <div class="main-header">
                <div class="navicon-con">
                    <Button :style="{transform: 'rotateZ(' + (this.shrink ? '-90' : '0') + 'deg)'}" type="text" @click="toggleClick">
                        <Icon type="navicon" size="32"></Icon>
                    </Button>
                </div>
                <div class="header-middle-con">
                    <div class="main-breadcrumb">
                        <breadcrumb-nav :currentPath="currentPath"></breadcrumb-nav>
                    </div>
                </div>
                <div class="header-avator-con">
                    <div class="user-dropdown-menu-con">
                        <Row type="flex" justify="end" align="middle" class="user-dropdown-innercon">
                            <span style="margin-right: 15px;">
                            	<span style="font-size: 18px;">
                            		<b>
                            			欢迎
                            		</b>
                            	</span>
                                <span class="main-user-name">{{ userName }}</span>
                            </span>
                            <Button
                            	size="large"
                            	type="primary"
                            	shape="circle"
                            	@click="handleClickUserDropdown"
                            	@DOMMouseScroll="ButOnmouseover('移入')"
                            	@mousewheel="ButOnmouseover('移出')">
                            	<span>退出登陆</span>
                            	<Icon type="ios-redo"></Icon>
                            </Button>
                        </Row>
                    </div>
                </div>
            </div>
            <div class="tags-con">
                <tags-page-opened :pageTagsList="pageTagsList"></tags-page-opened>
            </div>
        </div>
        <div class="single-page-con" :style="{left: shrink?'60px':'200px'}">
            <div class="single-page" style="height: 100%;">
                <keep-alive :include="cachePage" style="position: relative;height: 100%;">
                    <router-view></router-view>
                </keep-alive>
            </div>
        </div>
    </div>
</template>
<script>
    import shrinkableMenu from './main-components/shrinkable-menu/shrinkable-menu.vue';
    import tagsPageOpened from './main-components/tags-page-opened.vue';
    import breadcrumbNav from './main-components/breadcrumb-nav.vue';
    import fullScreen from './main-components/fullscreen.vue';
    import lockScreen from './main-components/lockscreen/lockscreen.vue';
    import messageTip from './main-components/message-tip.vue';
    import themeSwitch from './main-components/theme-switch/theme-switch.vue';
    import Cookies from 'js-cookie';
    import util from '@/libs/util.js';

	import SockJS from 'sockjs-client';
    // 'sockjs-client' 必须与package.json文件当中dependencies 当中的一模一样
    import Stomp from '@stomp/stompjs';

    export default {
        components: {
            shrinkableMenu,
            tagsPageOpened,
            breadcrumbNav,
            fullScreen,
            lockScreen,
            messageTip,
            themeSwitch
        },
        data () {
            return {
//          	"47.98.39.45:8080"
			    socket : new SockJS("http://"+"192.168.31.180:8080"+"/biz/gps"),
				scoketMess:[],
				
				shrink: false,
                userName: '',
                isFullScreen: false,
                openedSubmenuArr: this.$store.state.app.openedSubmenuArr
            };
        },
        computed: {
        	GetscoketMess() {
				return this.$store.state.app.socketMess
			},
            menuList () {
                return this.$store.state.app.menuList;
            },
            pageTagsList () {
                return this.$store.state.app.pageOpenedList; // 打开的页面的页面对象
            },
            currentPath () {
                return this.$store.state.app.currentPath; // 当前面包屑数组
            },
            avatorPath () {
                return localStorage.avatorImgPath;
            },
            cachePage () {
                return this.$store.state.app.cachePage;
            },
            lang () {
                return this.$store.state.app.lang;
            },
            menuTheme () {
                return []
            },
            mesCount () {
                return this.$store.state.app.messageCount;
            }
        },
        watch: {
            '$route' (to) {
                this.$store.commit('setCurrentPageName', to.name);
                this.checkTag(to.name);
                localStorage.currentPageName = to.name;
            },
            GetscoketMess: function(newQuestion, oldQuestion) {
				this.scoketMess = newQuestion
			}
        },
        mounted () {
            this.init();
            this.sco()
        },
        created () {
            // 显示打开的页面的列表
            this.$store.commit('setOpenedList');
//          this.sco()
        },
        methods: {
        	sco(){
    		console.log('网络连接')
    		//数据推送
				var v = this
			/**
		     * 建立成功的回调函数
		     */
			    v.socket.onopen = function() {
			    };
			/**
		     * 服务器有消息返回的回调函数
		     */
			    v.socket.onmessage = function(e) {
			        console.log('message', e.data);
			    };
		
		    /**
		     * websocket链接关闭的回调函数
		     */
			    v.socket.onclose = function() {
			        console.log('关闭');
			    };
		
			    var stompClient = Stomp.over(v.socket);
			    stompClient.connect({}, function(frame) {
			        stompClient.subscribe('/topic/sendgps',  function(data) { //订阅消息
			            console.log('soc',JSON.parse(data))
			            let jsonMess = JSON.parse(data.body)
			            v.scoketMess.forEach((item,index) => {
							if(item.clid==jsonMess.clid){
								v.scoketMess.splice(index,1)
							}
						})
				        v.scoketMess.push(jsonMess)
			            v.$store.commit('socketMessAdd',v.scoketMess)
			        });
			    });	
			},
        	ButOnmouseover(mes){
        		console.log('ButOnmouseover:',mes)
        	},
            init () {
                this.$store.commit('updateMenulist');
                this.userName = JSON.parse(Cookies.get('result')).userInfo.xm;
                let messageCount = 3;
                this.messageCount = messageCount.toString();
                this.checkTag(this.$route.name);
                this.$store.commit('setMessageCount', 3);
            },
            toggleClick () {
                this.shrink = !this.shrink;
            },
            handleClickUserDropdown (name) {
                    // 退出登录
                    this.$store.commit('logout', this);
                    this.$store.commit('clearOpenedSubmenu');
                    this.$store.commit('clearAllTags');//关闭多页面操作
                    this.$router.push({
                        name: 'login'
                    });
//              }
            },
            checkTag (name) {
                let openpageHasTag = this.pageTagsList.some(item => {
                    if (item.name === name) {
                        return true;
                    }
                });
                if (!openpageHasTag) { //  解决关闭当前标签后再点击回退按钮会退到当前页时没有标签的问题
                    util.openNewPage(this, name, this.$route.params || {}, this.$route.query || {});
                }
            },
            handleSubmenuChange (val) {
//                 console.log('路由',val)
            },
            beforePush (name) {
                return true;
            },
            fullscreenChange (isFullScreen) {
            }
        }
    };
</script>
