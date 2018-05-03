<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--角色管理-->
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
							<span>功能管理</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="findMess.gnmcLike" placeholder="请输入功能名称" style="width: 200px" @on-change="getmess()"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="getmess()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="getDataList()">
								<Icon type="plus-round"></Icon>
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<Row style="position: relative;">
				<Table :height="tabHeight" :row-class-name="rowClassName" :columns="tableTiT" :data="tableData"></Table>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>
		</Card>
		<component
			:is="compName"
			:chmess="chmess"
			:Dictionary="Dictionary"></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'
	
	import formData from './comp/formData.vue'
	import mess from './comp/mess.vue'
	export default {
		name: 'char',
		mixins: [mixins],
		components: {
			formData,
			mess
		},
		data() {
			return {
				SpinShow:true,
				tabHeight: 220,
				compName: '',
				PickerTime: 2017,
                choosedRow:null,
				//分页
				pageTotal: 1,
				page: {
					pageNum: 1,
					pageSize: 8
				},
				//数据传输
				chmess:{},
				tableTiT: [
				    {title: "序号", width: 60, align: 'center', type: 'index', fixed: 'left'},
					{title: '功能名称', align: 'center', width: 120, key: 'gnmc', fixed: 'left'},
					{title: '功能代码', align: 'center', width: 120, key: 'gndm'},
					{title: '服务代码', align: 'center', width: 120, key: 'fwdm'},
					{title: '状态', align: 'center', width: 120, key: 'zt',
                        render:(h,p)=>{
	                     	let val = this.dictUtil.getValByCode(this,this.lmdmDictionary,p.row.zt)
	    					return h('div',val)
                        }

					},
					{title: '排序', align: 'center', width: 120, key: 'px'},
					{title: '备注', align: 'center', width: 120, key: 'bz'},
					{title: 'URL', align: 'center', width: 120, key: 'url'},
					{title: '父节点', align: 'center', width: 120, key: 'fjd'},
					{title: '跳转地址', align: 'center', width: 120, key: 'tzdz'},
					{
						title: '图标',
						align: 'center',
						width: 60,
						key: 'tb',
						render: (h, params) => {
							return h('div', [
								h('Icon', {
									props: {
										type: params.row.tb,
										size: '22'
									},
									on: {
										click: () => {
											//log('数据调试', params)
										}
									}
								})
							]);
						}
					},
					{
						title: 'API前缀',
						align: 'center',
						width: 120,
						key: 'apiQz'
					},
					{
						title: 'API后缀',
						align: 'center',
						width: 120,
						key: 'apiHz'
					},
					{
						title: '创建人',
						width: 100,
						align: 'center',
						key: 'cjr'
					},
					{
						title: '创建时间',
						width: 100,
						align: 'center',
						key: 'cjsj'
					},
					{
						title: '操作',
						key: 'action',
						width: 100,
						align: 'center',
						fixed: 'right',
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
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.compName = 'formData'
											this.choosedRow = params.row;
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
											this.listDele(params.row.gndm)
										}
									}
								})
							]);
						}
					}
				],
				tableData: [{
						gnmc: '功能管理',
						gndm: 'gongnengguanli',
						fwdm: 'gongnengguanli',
						zt: '正常',
						bz: '备注信息',
						url: 'localhost:8080/#/',
						fjd: '系统管理',
						tzdz: 'system/system-function',
						tb: 'ionic',
						apiQz: 'http://192.168.10.39:8080',
						apiHz: '/asdf/afdasdf/adfadf',
						cjr: '小豆子',
						sj: '2017-05-02 09:10:00'
					}
				],
				//form表单
				formTop: {},
				//select
				cityList: [],
				//收索
				datetime: [],
				findMess: {
					gnmcLike:'',
					pageNum: 1,
					pageSize: 8
				},
				Dictionary:[],
				lmdmDictionary:'ZDCLK0007'
			}
		},
		created() {
			this.$store.commit('setCurrentPath', [{
					title: '首页',
				}, {
					title: '系统管理',
				}, {
					title: '功能管理',
				}]),
				this.tabHeight = this.getWindowHeight() - 300
            	this.getmess()
            	this.getLXDic()//字典数据
		},
		methods: {
			getLXDic(){
                this.Dictionary = this.dictUtil.getByCode(this,this.lmdmDictionary);
            	log('字典',this.Dictionary)
			},
			getmess(){
				var v = this
				this.$http.get(configApi.FUNCTION.QUERY,{params:this.findMess}).then((res) =>{
					//log('功能数据',res)
					v.tableData = res.page.list
					v.SpinShow = false
					v.pageTotal = res.page.total
				})
			},
			//删除数据
			listDele(id){
				this.util.del(this,configApi.FUNCTION.DELE,[id],()=>{
                    this.getmess();
				});
			},
			changeTime(val) {
				this.findMess.gte_StartTime = val[0]
				this.findMess.lte_StartTime = val[1]
			},
			getDataList() {
				var v = this
				v.compName = 'formData'
				this.choosedRow = null;
			},
			GetMess(page) {
				var v = this
				//      		log(page)
			},
			pageChange(event) {
				var v = this
				v.findMess.pageNum = event
				v.getmess()
				//      		log(v.page)
			},
//			findMessList() {
//				var v = this
//	      		this.$http.get('carLogs/pager',this.findMess).then((res) => {
//               	v.tableData = res.data
//                	v.pageTotal = res.total
//            	})
//			},

		}
	}
</script>