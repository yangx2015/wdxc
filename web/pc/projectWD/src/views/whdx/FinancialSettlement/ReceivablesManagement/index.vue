<!--
	收款管理
-->
<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div class="box">
		<Row class="tit" style="height: 120px;">
			<Col span="6">
			<Menu mode="horizontal" theme="light" active-name="1" @on-select="MenuClick">
				<MenuItem name="1">
				<Icon type="ios-paper"></Icon>
				应收单据
				</MenuItem>
				<MenuItem name="2">
				<Icon type="android-checkbox-outline"></Icon>
				已收单据
				</MenuItem>
			</Menu>
			</Col>
			<Col span="15">
			<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
				单笔费用结算公式：里程 * 单价 + 过路费 + 过桥费 + 等时费 = 合计总价
			</div>
			</Col>
			<Col span="3">
			<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
				<div v-show="param.ddzt === '30'">
					应收单据：{{list.length}}单
				</div>
				<div v-show="param.ddzt === '40'">
					已收单据：{{list.length}}单
				</div>
			</div>
			</Col>
			<Col span="24">
			<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
				<Cascader style="width:300px;float: left;margin-top: 16px;margin-left: 4px;padding-right: 10px;"
					 v-model="CascaderList" :clearable='false' @on-change="change" :data="orgTree" placeholder="请选择用车单位"></Cascader>
				<DatePicker v-model="param.startTime" :options="dateOpts" type="datetime" placeholder="请输入开始时间"></DatePicker>
				<DatePicker v-model="param.endTime" :options="dateOpts" type="datetime" placeholder="请输入结束时间"></DatePicker>
				<Button type="primary" @click="getData()">
                                    <Icon type="md-search"></Icon>
                              </Button>
			</div>
			</Col>
		</Row>
		<Row :gutter="16" class="margin-top-10 body clientList" v-for="(item,index) in list">
			<Col span="24" :lg="24" :md="24" :sm="24" :xs="24" class="margin-top-10">
			<Card style="width:100%" :id="'group_'+item.orgCode">
				<div slot="title">
					<Icon type="md-person"></Icon>
					{{item.orgName}}
				</div>
				<span slot="extra">
			        	<span>
			        		收款金额：{{item.amount}}元
			        		<Button type="success" size="small"
                                                        @click="print(item,index)">打印</Button>
			        		<Button v-if="param.ddzt === '30'" type="primary" size="small"
                                                        @click="confirm(index)">确认</Button>
			        	</span>
				</span>
				<!--信息-->
				<div>
					<Table ref="table" border 
						:height="tabHeight"
						:columns="param.ddzt === '30' ? columns3 : columns4"
						:data="item.orderList"
						@on-selection-change="(e)=>{tableSelectionChange(e,index)}"></Table>
				</div>
			</Card>
			</Col>
		</Row>
		<component :is="compName" :row='listMess'></component>
	</div>
</template>
<script>
	import swal from 'sweetalert2'
	import edit from './edit'
	import print from './print'
	import mixins from '@/mixins'
	export default {
		name: 'client',
		components: {
			edit,
			print
		},
		mixins: [mixins],
		data() {
			return {
				listMess:{},
				tabHeight:0,
				CascaderList: ['100', '100002', '100002001'],
				dateOpts: {
					shortcuts: [{
							text: '今天',
							value() {
								return new Date();
							}
						},
						{
							text: '三天前',
							value() {
								const date = new Date();
								date.setTime(date.getTime() - 3600 * 1000 * 24 * 3);
								return date;
							}
						},
						{
							text: '一周前',
							value() {
								const date = new Date();
								date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
								return date;
							}
						}
					]
				},
				v: this,
				compName: '',
				choosedItem: null,
				columns3: [{
						type: 'selection',
						width: 60,
						align: 'center'
					},
					{
						title:'#',
						type:'index',
						minWidth:60,
						align:'center'
					},
					{
						title: '用车人员',
						key: 'ck',
						minWidth:100,
						align:'center'
					},
					{
						title: '候车地点',
						key: 'hcdz',
						minWidth:100,
						align:'center'
					},
					{
						title: '目的地',
						key: 'mdd',
						minWidth:100,
						align:'center'
					}, {
						title: '司机',
						key: 'sjxm',
						minWidth:100,
						align:'center'
					}, {
						title: '车型(座位)',
						key: 'zws',
						minWidth:100,
						align:'center'
					}, {
						title: '出车时间',
						key: 'yysj',
						minWidth:100,
						align:'center'
					},
					{
                        title: '评分',
                        align: 'center',
                        minWidth:120,
                        key: 'pjdj',
                    },
                    {
                        title: '评价内容',
                        align: 'center',
                        minWidth:180,
                        key: 'pjnr',
                    },
					{
						title: '里程(公里)',
						key: 'lc',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.lc == '') {
								return h('div', '0')
							}
							return h('div', p.row.lc)
						}
					}, {
						title: '里程单价',
						key: 'dj',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.dj == '') {
								return h('div', '0')
							}
							return h('div', p.row.dj)
						}
					}, {
						title: '过路费',
						key: 'glf',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.glf == '') {
								return h('div', '0')
							}
							return h('div', p.row.glf)
						}
					}, {
						title: '路停费',
						key: 'gqf',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.gqf == '') {
								return h('div', '0')
							}
							return h('div', p.row.gqf)
						}
					}, {
						title: '加班费',
						key: 'jbf',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.jbf == '') {
								return h('div', '0')
							}
							return h('div', p.row.jbf)
						}
					}, {
						title: '节假日补助',
						minWidth:120,
						align:'center',
						key: 'jjrjl',
						render: (h, p) => {
							if(p.row.jjrjl == '') {
								return h('div', '0')
							}
							return h('div', p.row.jjrjl)
						}
					}, {
						title: '车费合计',
						key: 'zj',
						minWidth:100,
						align:'center'
					}, {
						title: '事由',
						key: 'sy',
						minWidth:120,
						align:'center'
					},
					{
						title: '操作',
						key: 'action',
						align: 'center',
						minWidth:180,
						align:'center',
						fixed:'right',
						render: (h, params) => {
							return h('div', [
							 h('Button', {
                                    props: {
                                        type: 'info',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.listMess = params.row
											this.compName = 'CLP'
                                        }
                                    }
                                },'车单打印'),
								h('Button', {
									props: {
										type: 'primary',
										size: 'small'
									},
									style: {
										marginRight: '5px'
									},
									on: {
										click: () => {
											this.choosedItem = params.row;
											this.compName = 'edit';
										}
									}
								}, '单据编辑')
							]);
						}
					}
				],
				columns4: [{
						type: 'selection',
						width: 60,
						align: 'center'
					},
					{
						title:'#',
						type:'index',
						minWidth:60,
						align:'center'
					},
					{
						title: '用车人员',
						key: 'ck',
						minWidth:100,
						align:'center'
					},
					{
						title: '候车地点',
						key: 'hcdz',
						minWidth:100,
						align:'center'
					},
					{
						title: '目的地',
						key: 'mdd',
						minWidth:100,
						align:'center'
					}, {
						title: '司机',
						key: 'sjxm',
						minWidth:100,
						align:'center'
					}, {
						title: '车型',
						key: 'zws',
						minWidth:100,
						align:'center'
					}, {
						title: '出车时间',
						key: 'yysj',
						minWidth:100,
						align:'center'
					}, 
					{
                        title: '评分',
                        align: 'center',
                        minWidth:120,
                        key: 'pjdj',
                    },
                    {
                        title: '评价内容',
                        align: 'center',
                        minWidth:180,
                        key: 'pjnr',
                    },
					{
						title: '里程(公里)',
						key: 'lc',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.lc == '') {
								return h('div', '0')
							}
							return h('div', p.row.lc)
						}
					}, {
						title: '里程单价',
						key: 'dj',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.dj == '') {
								return h('div', '0')
							}
							return h('div', p.row.dj)
						}
					}, {
						title: '过路费',
						key: 'glf',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.glf == '') {
								return h('div', '0')
							}
							return h('div', p.row.glf)
						}
					}, {
						title: '路停费',
						key: 'gqf',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.gqf == '') {
								return h('div', '0')
							}
							return h('div', p.row.gqf)
						}
					}, {
						title: '加班费',
						key: 'jbf',
						minWidth:100,
						align:'center',
						render: (h, p) => {
							if(p.row.jbf == '') {
								return h('div', '0')
							}
							return h('div', p.row.jbf)
						}
					}, {
						title: '节假日补助',
						minWidth:120,
						align:'center',
						key: 'jjrjl',
						render: (h, p) => {
							if(p.row.jjrjl == '') {
								return h('div', '0')
							}
							return h('div', p.row.jjrjl)
						}
					}, {
						title: '车费合计',
						key: 'zj',
						minWidth:100,
						align:'center'
					}, {
						title: '事由',
						key: 'sy',
						minWidth:120,
						align:'center'
					},
                    {
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        minWidth:90,
                        fixed:'right',
                        render: (h, params) => {
                            return h('div', [
                            h('Button', {
                                    props: {
                                        type: 'info',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.listMess = params.row
											this.compName = 'CLP'
                                        }
                                    }
                                },'车单打印')
                            ]);
                        }
                    }
				],
				munName: '1',
				param: {
					ddzt: '30',
					ck: '',
					jgmc: '',
					jgdm: '100002001',
					startTime: '',
					endTime: ''
				},
				list: [],
				selectedData: [],
				treeValue: [],
				orgTree: [],
			}
		},
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '财务结算',
			}, {
				title: '收款管理',
			}])
			this.tabHeight = this.getWindowHeight() - 340
			this.getData();
			this.getOrgTree();
		},
		mounted() {

		},
		methods: {
			getOrgTree() {
				this.$http.get(this.apis.FRAMEWORK.GET_TREE_Node).then((res) => {
					this.orgTree = res.result
				})
			},
			change(vaule, selectedData) {
				console.log(vaule)
				this.param.jgdm = selectedData[selectedData.length - 1].value
				this.treeValue = vaule;
			},
			tableSelectionChange(e, i) {
				this.selectedData[i] = e;
			},
			getData() {
				this.list = [];
				let startTime = this.param.startTime;
				let endTime = this.param.endTime;
				if(typeof startTime === 'object') {
					this.param.startTime = startTime.format('yyyy-MM-dd hh:mm:ss');
				}
				if(typeof endTime === 'object') {
					this.param.endTime = endTime.format('yyyy-MM-dd hh:mm:ss');
				}
				this.$http.get(this.apis.ORDER.collectingList, {
					params: this.param
				}).then((res) => {
					if(res.code === 200 && res.result) {
						this.list = res.result;
						for(let r of this.list) {
							this.selectedData.push([]);
						}
					}
				})
			},
			confirm(index) {
				if(this.selectedData[index].length === 0) {
					this.$Message.error("请选择订单");
					return;
				}
				swal({
					title: "确认已付款?",
					type: "warning",
					showCancelButton: true,
					confirmButtonText: '确定',
					cancelButtonText: '取消',
				}).then((confirm) => {
					if(confirm.value) {
						let ids = '';
						for(let r of this.selectedData[index]) {
							ids += r.id + ',';
						}
						let v = this;
						let url = this.apis.ORDER.collectingConfirm;
						v.$http.post(url, {
							'ids': ids
						}).then((res) => {
							if(res.code === 200) {
								v.$Message.success(res.message);
								this.getData();
							} else {
								v.$Message.error(res.message);
							}
						})
					}
				});
			},
			//选项卡的切换
			MenuClick(event) {
				this.param.ddzt = (event === '1' ? '30' : '40');
				this.getData();
			},
			//卡片事件
			changeLimit(mes) {
				alert(mes)
			},
			print(item, index) {
				if(this.selectedData[index].length === 0) {
					this.$Message.error("请选择订单");
					return;
				}
				item.choosedOrderList = this.selectedData[index];
				this.choosedItem = item;
				this.compName = 'print';
			},
			show() {

			}
		}
	}
</script>

<style>

</style>