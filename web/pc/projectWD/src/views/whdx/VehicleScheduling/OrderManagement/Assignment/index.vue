<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<!--订单分派-->
<template>
	<div class="box boxbackborder">
		<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
			<div style="height: 45px;line-height: 45px;">
				<div class="margin-top-10 box-row">
					<div class="titmess">
						<span>订单分派</span>
					</div>
					<div class="body-r-1 inputSty">
					</div>
					<div class="butevent">
					</div>
				</div>
			</div>
		</Row>
		<div class="body" style="border-top:3px #c8c8c8 solid;padding-top: 5px">
			<div class="box-row-list">
				<Card style="width:350px">
					<p slot="title">
						<Icon type="ios-film-outline"></Icon>
						Classic film
					</p>
					<a href="#" slot="extra">
						<Icon type="ios-loop-strong"></Icon>
						Change
					</a>
					<div>
						123
					</div>
				</Card>
			</div>
		</div>
		<component :is="compName"></component>
	</div>
</template>

<script>
	import pageList from './comp/list'
    import configApi from '@/axios/config.js'
	export default{
		name:'driver',
        components: {
            pageList
        },
		data(){
			return{
                compName:'pageList',
			    drvlist:[]
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
            }]);
			this.getDrvList()
		},
		mounted(){
		},
		methods:{
			getDrvList(){
                this.$http.post(configApi.ORDER.SJLB,{}).then((res) =>{
					console.log(res)
                })
			}
		}
	}
</script>

<style>
</style>