<template>
    <div id="main" class="app-main">
        <router-view></router-view>
        <div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 9999;">
			<Spin fix>
				<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
				<div style="font-size: 30px;">数据加载中...</div>
			</Spin>
		</div>
    </div>
</template>

<script>
	
    export default {
        data () {
            return {
            	SpinShow:false,
                theme: this.$store.state.app.themeColor
            };
        },
        computed: {
            loading(){
            	return this.$store.state.app.loadingType;
            }
        },
        watch: {
            loading:function(newQuestion, oldQuestion){
            	this.SpinShow = newQuestion
            }
        },
        mounted () {

        },
        beforeDestroy () {

        },
        methods: {

        }
    };
</script>

<style>
@import "//at.alicdn.com/t/font_605313_dytag0ll15eqxgvi.css";
html,body{
    width: 100%;
    height: 100%;
    background: #f0f0f0;
    overflow: hidden;
}
.app-main{
    width: 100%;
    height: 100%;
}
</style>
