<style lang="less">
	@import '../../../../../styles/common.less';
	.ddfpSty{
		padding: 3px 5px;
		border-left: solid #00a050 2px;
		border-right: solid #00a050 2px;
		border-radius: 15px;
	}
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
				<Card style="width:400px" v-for="(item,index) in drvlist">
					<p slot="title">
						<Icon type="ios-film-outline"></Icon>
						{{item.xm}}
					</p>
					<a href="#" slot="extra">
						<Icon type="ios-loop-strong"></Icon>
						Change
					</a>
					<div style="height: 160px;overflow: auto">
						<div class="ddfpSty" v-for="(p,i) in item.clDdList" >
							<div>
								<Row>
									<Col span="12">
										<Icon type="person"
											color="#7b7b7b"></Icon>
										信息工程学院——陈小伟
										<!--{{p.jgmc}} {{p.ck}}-->
									</Col>
									<Col span="12">
										<Icon type="ios-clock"
											color="#0897ff"></Icon>
										{{p.yysj}}
									</Col>
								</Row>
							</div>
							<div>
								<Row>
									<Col span="12">
										<Icon type="record" color="#19a853"></Icon>
										{{p.hcdz}}
									</Col>
									<Col span="12">
										<Icon type="record" color="#ff8f00"></Icon>
										{{p.mdd}}
									</Col>
								</Row>
							</div>
						</div>
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
                compName:'',
			    drvlist:[],
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
			getDrvList(){//司机列表
			    var v = this
                this.$http.post(configApi.ORDER.SJLB,{}).then((res) =>{
                    if(res.code == 200){
						v.drvlist = res.result
					}
					console.log(res)
                })
			},
			getYfp(sjid){//已分派订单列表
                this.$http.post(configApi.ORDER.YFP,{'sjSx':10,'sj':sjid,'cph':''}).then((res) =>{
                    console.log(res)
                })
			},
			dele(id){//取消分派
                this.$http.post(configApi.ORDER.QXPD,{'id':id}).then((res) =>{
                    console.log(res)
                })
			}
		}
	}
</script>

<style>
</style>