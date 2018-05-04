<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<!--订单分派-->
<template>
</template>

<script>
	export default{
		name:'driver',
		data(){
			return{
			}
		},
		created(){
			this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆管理',
            },{
                title: '订单管理',
            },{
                title: '订单分配',
            }])
		},
		mounted(){
		}
	}
</script>

<style>
</style>