<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--用户管理-->
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
							<span>用户管理</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="findMess.xmLike" 
								placeholder="请输入用户姓名" style="width: 200px" 
								@on-keyup.enter="findMessList()"
								@on-change="findMessList"></Input>
							<Input v-model="findMess.sjhLike" 
								placeholder="请输入手机号码" style="width: 200px" 
								@on-keyup.enter="findMessList()"
								@on-change="findMessList"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="AddDataList()">
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
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>
		</Card>
		<component
			:is="compName"
			:usermes="usermes"
			:usermesType="userMesType"
			@listF='listF'></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'


	import newmess from './comp/newmes.vue'
	import changemes from './comp/changmes.vue'
	export default {
		name: 'char',
		components: {
			newmess,
			changemes
		},
		mixins: [mixins],
		data() {
			return {
				//tab高度
				tabHeight: 220,
				//动态组建
				compName: '',
				//动态组建数据
				usermes:{},
				userMesType:true,
				//分页
				//---数据总数
				pageTotal: 2,
				page: {
					//---当前页码
					pageNum: 1,
					//---每页显示条数
					pageSize: 5
				},
				tableTiT: [{
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
					{
						title: '帐号',
						align: 'center',
						key: 'zh'
					},
					{
						title: '姓名',
						align: 'center',
						key: 'xm'
					},
					{
						title: '证件号码',
						align: 'center',
						key: 'zjhm'
					},
					{
						title: '性别',
						align: 'center',
						key: 'xb',
						render: (h, params) => {
							return h('div',params.row.xb == '1' ? '男' : '女')
						}
					},
					{
						title: '手机号',
						width: 120,
						align: 'center',
						key: 'sjh'
					},
					{
						title: '职务',
						align: 'center',
						key: 'zw'
					},
					{
						title: '类型',
						align: 'center',
						key: 'lx',
                        render:(h,p)=>{
                            let val = this.dictUtil.getValByCode(this,this.yhlxDictCode,p.row.lx)
                            return h('div',val)
                        }
					},
					{
						title: '创建时件',
						align: 'center',
						key: 'cjsj'
					},
					{
						title: '操作',
						key: 'action',
						width: 150,
						align: 'center',
						render: (h, params) => {
							if(params.row.lx==='su'){
								return ''
							}
							return h('div', [
							h('Button', {
									props: {
										type: 'success',
										icon: 'edit',
										shape: 'circle',
										size: 'small'
									},
									style: {
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.userMesType = false
											this.usermes = params.row
											this.compName = 'newmess'
										}
									}
								}),
								h('Button', {
									props: {
										type: 'primary',
										icon: 'navicon-round',
										shape: 'circle',
										size: 'small'
									},
									style: {
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.RootShowF(params.row)

										}
									}
								}),
								h('Button', {
									props: {
										type: 'error',
										icon: 'close',
										shape: 'circle',
										size: 'small'
									},
									style: {
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.listDele(params.row.yhid)
										}
									}
								})
							]);
						}
					}
				],
				tableData: [{
						zh: '123456',
						ma: '123123',
						xm: '毛毛1',
						xb: '男',
						sjh: '13114632587',
						zw: '司机',
						cjr: '大毛毛',
						cjsj: '2017-05-02 09:10:00',
						zjhm:'12'
					}
				],
				//收索
//				cjsjInRange:[],
				findMess: {
					sjhLike:'',
					xmLike: '',
					pageNum: 1,
					pageSize: 5
				},
				yhlxDict:[],
				yhlxDictCode:'ZDCLK0003'
			}
		},
		watch: {
//			cjsjInRange:function(newQuestion, oldQuestion){
//				this.findMess.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
//			},
		},
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '系统管理',
			}, {
				title: '用户管理',
			}]),
			this.tabHeight = this.getWindowHeight() - 260
            this.getmess()
            this.getDict()
		},
		methods: {
		    getDict(){
                this.yhlxDict = this.dictUtil.getByCode(this,this.yhlxDictCode);
                console.log(this.yhlxDict);
            },
			enter(mes){
//				console.log(mes)
//
//				console.log('页面高度',Math.floor((this.getWindowHeight() - 290)/48))
			},
			getmess(){
				var v = this
				this.$http.get(configApi.USER.QUERY,{params:v.findMess}).then((res) =>{
//					console.log(res)
					v.tableData = res.page.list
					v.pageTotal = res.page.total
				})
			},
			//权限分配
			RootShowF(val) {
				var v = this
				v.compName = 'changemes'
				this.usermes = val;
//              console.log(val);

            },
			//收索事件
			findMessList() {
				var v = this
				this.$http.get(configApi.USER.QUERY,{params:v.findMess}).then((res) =>{
//					console.log(res)
					v.tableData = res.page.list
				})
			},
			//添加新用户信息
			AddDataList() {
				var v = this
				v.compName = 'newmess'
				v.userMesType = true
				this.usermes = null
			},
			listF(res){
				this.getmess()
				this.compName = ''
			},
			//删除数据
			listDele(id){
				this.util.del(this,configApi.USER.DELE,[id],()=>{
                    this.getmess();
				});
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