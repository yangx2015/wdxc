<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Card>
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>校巴排班</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="todaytime" format="yyyy-MM-dd" type="date" placement="bottom-end" placeholder="请输时间" style="width: 220px"></DatePicker>
						</div>
						<div class="butevent">
							<Button type="primary" @click="">
								<Icon type="plus-round"></Icon>
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<Row style="position: relative;">
				<Table
						size='large'
						:height="tabHeight"
						:row-class-name="rowClassName"
						:columns="tableTiT"
						:data="tableData"></Table>
				<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
					<Spin fix>
						<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
						<div style="font-size: 30px;">数据加载中请稍后</div>
					</Spin>
				</div>
			</Row>
			<!--<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>-->
		</Card>
		<component
			:is="compName"></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'
	
	import addmess from './comp/addmess'
	
	export default {
		name: '',
		mixins:[mixins],
		components: {
			addmess
		},
		data() {
			return {
				compName:'addmess',
				tabHeight:220,
				SpinShow:true,
				todaytime:'',
				//分页
				//---数据总数
				pageTotal: 2,
				page: {
					//---当前页码
					pageNum: 1,
					//---每页显示条数
					pageSize: 5
				},
				tableTiT: [
				{
                        type: 'index',
                        width: 40,
                        align: 'center'
                    },
                    {
                        title: '线路名称',
                        key: 'xlmc'
                    },
                    {
                        title: '运行时间',
                        width: 100,
                        key: 'yxjssj',
                        align: 'center'
                    },
                    {
                        title: '车辆信息',
                        key: 'clList',
                        align: 'center',
                        render: (h, params) => {
                        	let cl = params.row.clList
                        	console.log('***************8',cl)
                        	if(cl===null){
                        		return
                        	}
                        	let span = []
                        	for(var i = 0 ;i<cl.length;i++){
                        		span.push(
                        			h('span',{
										style:{
											marginRight:'5px',
										}
									},cl[i].cph)
                        		)
                        	}
                        	return h('div', span)
						}
                    }],
				tableData: []
			}
		},
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '车辆管理',
			}, {
				title: '校巴管理',
			}, {
				title: '校巴排班',
			}]),
			this.todaytime = this.getdateParaD(this.getdate())
			this.tabHeight = this.getWindowHeight() - 220
			this.getmess()
		},
		mounted(){
		},
		methods: {
			getmess(){
				var v = this
				console.log('排班数据2')
				//线路数据
				this.$http.post(configApi.XLPBXX.QUERY,{"clcx":"30","date2":v.todaytime}).then((res) =>{
					console.log('排班数据2',res)
					v.tableData = res.result
				}).then((res) =>{
					v.SpinShow = false;
				}).catch((err) =>{
					console.log('bug')
				})
				
				
				
//				this.$http.post(configApi.PB.QUERY,{"clcx":"30","lulx":"10","date2":v.todaytime}).then((res) =>{
//					console.log('排班数据',res)
//					v.tableData = res.result
////					v.pageTotal = res.page.total
//					v.SpinShow = false;
//				})
			},
			okdrag(){
//				alert('132')
				this.dateMess=false
			},
        	changeClick(){
        		this.dateMess = true
        		this.modalName = 'drag'
//				this.modalName = drlist
        	},
			dayClick(event) {
				this.todaytime = this.getdateParaD(event)
				this.getmess()
//				console.log('天事件', this.getdateParaD(event))
//				console.log('天事件', event.toLocaleString())
//				this.dateMess = true
			},
			eventClick(event) {
				console.log('备注事件', event)
			},
			//dome组件刷新
			domeC(){
//				debugger
				this.$refs.pbxx.SpinShow = false
				this.$router.push({
					name:'Sc_Scheduling'
				})
				this.modalName = 'drag'
			},
			//分页点击事件按
			pageChange(event) {
				var v = this
				v.findMess.pageNum = event
				v.getmess()
			}
		}
	}
</script>

<style>

</style>