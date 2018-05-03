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
							<DatePicker v-model="todaytime" 
								format="yyyy-MM-dd" 
								type="date" 
								placement="bottom-end" 
								placeholder="请输时间" 
								style="width: 220px"></DatePicker>
						</div>
						<!--<div class="butevent">
						</div>-->
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
			</Row>
			<!--<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>-->
		</Card>
		<component
			:is="compName"
			:mess="mess"
			:pbTime="giveTime"></component>
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
				compName:'',
				mess:{},
				tabHeight:220,
				SpinShow:true,
				todaytime:'',
				giveTime:'',
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
                        width: 150,
                        align: 'center',
						render:(h,p)=>{
                            return h('div',p.row.yxkssj + ' ~ ' + p.row.yxjssj)
                        }
                    },
                    {
                        title: '车辆信息',
                        key: 'clList',
                        align: 'center',
                        render: (h, params) => {
                        	let cl = params.row.clList
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
									},cl[i].cph+ ' ; ')
                        		)
                        	}
                        	return h('div', span)
						}
                    },{
						title: '操作',
						key: 'action',
						width: 100,
						align: 'center',
						render: (h, params) => {
							return h('div', [
							h('Button', {
									props: {
										type: 'success',
										icon: 'edit',
										shape: 'circle',
										size: 'small'
									},
									style: {
										cursor: "pointer"
									},
									on: {
										click: () => {
											if(params.row.clList!=null && params.row.clList.length > 0){
												params.row.clList.forEach(function(item,index){
													item.ico = false
												})
											}
											this.mess = params.row
											this.compName = 'addmess'
										}
									}
								})
							]);
						}
					}
				],
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
			this.giveTime = this.todaytime = this.getdateParaD(this.getdate())
			this.tabHeight = this.getWindowHeight() - 220
			this.getmess()
		},
		watch:{
			todaytime:function(n,o){
				this.giveTime = this.getdateParaD(n)
				this.getmess()
			}
		},
		mounted(){
		},
		methods: {
			getmess(){
				var v = this
				console.log('排班数据2')
				//线路数据
				this.$http.post(configApi.XLPBXX.QUERY,{"clcx":"30","date2":v.giveTime}).then((res) =>{
					console.log('排班数据2',res)
					v.tableData = res.result
				}).then((res) =>{
					v.SpinShow = false;
				}).catch((err) =>{
					console.log('bug')
				})
			},
        	changeClick(){
        		this.compName='addmess'
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