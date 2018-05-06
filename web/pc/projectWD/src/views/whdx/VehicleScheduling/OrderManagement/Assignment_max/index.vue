<style lang="less">
	@import '../../../../../styles/common.less';
	.ddfpSty{
		position: relative;
		margin-bottom: 6px;
		margin-right: 3px;
		padding: 3px 8px;
		border-left: solid #fb00ff 2px;
		border-right: solid #fb00ff 2px;
		border-radius: 15px;
	}
</style>
<style scoped>
	.demo-badge{
		width: 42px;
		height: 42px;
		background: #eee;
		border-radius: 6px;
		display: inline-block;
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
				<Card style="width:440px;margin:8px" v-for="(item,index) in drvlist">
					<div slot="title" class="box-row" style="height: 25px;line-height: 25px">
						<div style="font-weight: 700">
							<Icon type="ios-film-outline"></Icon>
							{{item.xm}}
						</div>
						<div class="body-O" style="padding-left:8px ">
								<span>
									总（*）
								</span>
							<span>
									完（*）
								</span>
							<span>
									未（*）
								</span>
						</div>
					</div>
					<span slot="extra">
						<i-switch size="large" v-model="item.zt=='00'">
							<span slot="open">在班</span>
							<span slot="close">休息</span>
						</i-switch>
						<Tooltip content="订单分配" placement="left">
							<Button type="primary"
									:disabled="item.zt=='01'"
									size="small"
									shape="circle"
									icon="plus-round"
									@click="showList(item)"></Button>
						</Tooltip>
					</span>
					<div style="height: 160px;overflow: auto">
						<div  v-if="item.clDdList.length==0">
							<span>
								暂无分配订单
							</span>
						</div>
						<div v-else class="ddfpSty" v-for="(p,i) in item.clDdList" >
							<div>
								<Row>
									<Col span="10">
									<Icon type="person"
										  color="#7b7b7b"></Icon>
									<!--信息工程学院—陈小伟-->
									{{p.jgmc}}-{{p.ck}}
									</Col>
									<Col span="10">
									<Icon type="ios-clock"
										  color="#0897ff"></Icon>
									{{p.yysj}}
									</Col>
								</Row>
							</div>
							<div>
								<Row>
									<Col span="10">
									<Icon type="record" color="#19a853"></Icon>
									{{p.hcdz}}
									</Col>
									<Col span="10">
									<Icon type="record" color="#ff8f00"></Icon>
									{{p.mdd}}
									</Col>
								</Row>
							</div>
							<div style="position: absolute;right: 5px;top:10px;">
								<Tooltip content="撤回" placement="left">
									<Button type="warning"
											size="small"
											shape="circle"
											icon="reply"
											@click="dele(p.id)"></Button>
								</Tooltip>
							</div>
						</div>

					</div>
				</Card>
			</div>
		</div>
		<component :is="compName" :mess="mess"></component>
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
                swi:true,
                mess:{},
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
                this.$http.post(configApi.ORDER.SJLB,{'zjcx':2030}).then((res) =>{
                    if(res.code == 200){
                        v.drvlist = res.result
                    }
                    console.log(res)
                })
            },
            dele(id){//取消分派
                var v = this
                this.$http.post(configApi.ORDER.QXPD,{'id':id}).then((res) =>{
                    if(res.code===200){
                        v.$Message.success(res.message);
                        v.getDrvList()
                    }else{
                        v.$Message.error(res.message);
                    }
                }).catch((error) =>{
                    v.$Message.error('出错了！！！');
                })
            },
            showList(item){
                this.compName = 'pageList'
                this.mess = item
            }
        }
    }
</script>

<style>
</style>