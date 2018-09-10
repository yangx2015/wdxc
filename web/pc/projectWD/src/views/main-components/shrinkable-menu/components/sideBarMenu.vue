<style lang="less">
    @import '../styles/menu.less';
</style>

<template>
    <div>
        <Menu ref="sideMenu" :active-name="menuNmae" :open-names="openNames" :theme="menuTheme" width="auto" @on-select="changeMenu">
            <div v-for="item in mesList">
                <Submenu v-if="item.children && item.children.length > 0" :name="item.name" :key="item.name">
                    <template slot="title">
                        <Icon :type="item.icon" :size="iconSize"></Icon>
                        {{itemTitle(item)}}
                    </template>
                    <div v-for="child in item.children" :key="child.name">
                        <div v-if="child.children">
                            <Submenu :name="child.name">
                                <template slot="title">
                                    <Icon :type="child.icon" :size="iconSize"></Icon>
                                    {{itemTitle(child)}}
                                </template>
                                <MenuItem v-for="childs in child.children" :name="childs.name" :key="childs.item">
                                    <Icon :type="childs.icon" :size="iconSize"></Icon>
                                    {{itemTitle(childs)}}
                                </MenuItem>
                            </Submenu>
                        </div>
                        <div v-else>
                            <MenuItem :name="child.name">
                                <Icon :type="child.icon" :size="iconSize"></Icon>
                                {{itemTitle(child)}}
                            </MenuItem>
                        </div>
                    </div>
                </Submenu>
                <MenuItem v-else :name="item.name">
                    <Icon :type="item.icon" :size="iconSize"></Icon>
                    {{itemTitle(item)}}
                </MenuItem>
            </div>
        </Menu>
    </div>
</template>
<script>
    import menuList from '../../../../data/list'
    export default {
        name: 'sidebarMenu',
        data() {
            return {
                //打开页面
                menuNmae: '',
                mesList: []
            }
        },

        computed: {
            currentPageName() {
                return this.$store.state.app.currentPageName
            },
            leftMenuList(){
                return this.$store.state.app.leftMenuList;
            }
        },
        watch: {
            currentPageName: function(newQuestion, oldQuestion) {
                this.menuNmae = newQuestion
            },
            leftMenuList: function(newQuestion, oldQuestion) {
                this.mesList = newQuestion
                console.log(newQuestion);
            },
        },
        props: {
            menuList: Array,
            iconSize: Number,
            menuTheme: {
                type: String,
                default: 'Primary'
            },
            openNames: {
                type: Array
            }
        },
        methods: {
            changeMenu(active) {
                log('菜单数据',active);
//              this.$store.commit('setCurrentPageName', active)
                this.$emit('on-change', active);
            },
            itemTitle(item) {
                if(typeof item.title === 'object') {
                    return this.$t(item.title.i18n);
                } else {
                    return item.title;
                }
            },
        },
        updated() {
            this.$nextTick(() => {
                if(this.$refs.sideMenu) {
                    // this.$refs.sideMenu.updateOpened();
                }
            });
        },
        created() {
//          this.mesList = menuList.menuTree
//             this.mesList = this.session.getItem('menuList')
            log('菜单数据',this.mesList)
        }

    };
</script>
