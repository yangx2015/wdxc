<style lang="less">
	@import '../../../../styles/common.less';
</style>
<style lang="less" scoped="scoped">
	.fromTiT {
		/*text-align: right;*/
	}
</style>
<!--核算公式-->
<template>
	<div class="boxbackborder">
		<Card>
			<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
						<Icon type="ios-paper" size='30' color='#fff'></Icon>
					</span>
				<div style="height: 45px;line-height: 45px;">
					<Row class="margin-top-10">
						<Col span="4">
							<span class="titmess">核算公式</span>
						</Col>
						<Col span="14">
						<Input v-model="findMess.like_CarNumber" placeholder="请输入收索信息" style="width: 200px" @on-change="findMessList"></Input>
						</Col>
						<Col span="6" class="butevent">
							<Button type="primary" @click="">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="AddDataList()">新建核算公式</Button>
						</Col>
					</Row>
				</div>
			</Row>
			<Row>
				<Table :row-class-name="rowClassName" :columns="tableTiT" :data="tableData"></Table>
			</Row>
			<!-- <Row class="margin-top-10" style="text-align: right;">
					<Page :total=pageTotal
						:current=page.pageNum
						:page-size=page.pageSize
						show-total
						show-elevator
						@on-change='pageChange'></Page>
				</Row> -->
		</Card>
		<component :is="compName"></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'
	//	import axios from '@/axios'
	import modal from './comp/modal'
	export default {
		name: 'char',
		mixins: [mixins],
		components:{
			modal
		},
		data() {
			return {
				compName:'',
				//分页
				pageTotal: 1,
				page: {
					pageNum: 1,
					pageSize: 5
				},
				//弹层--角色分配
				RootShow: false,
				indeterminate: false,
				checkAll: false,
				checkAllGroup: [],
				roleList: [{
						value: '司机',
						key: '01'
					},
					{
						value: '队长',
						key: '02'
					}
				],
				//弹层--新增用户
				showModal: false,
				tableTiT: [{
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
					{
						title: '车型',
						align: 'center',
						key: 'name'
					},
					{
						title: '核算类型',
						align: 'center',
						key: 'mess'
					},
					{
						title: '核算基数',
						align: 'center',
						key: 'cardinal'
					},
					{
						title: '核算单价',
						align: 'center',
						key: 'money'
					},
//					{
//						title: '状态',
//						align: 'center',
//						key: 'time',
//						render: (h, params) => {
//							return h('div', [
//								h('Button', {
//									props: {
//										type: 'success',
//										size: 'small'
//									},
//									style: {
//										marginRight: '5px'
//									},
//									on: {
//										click: () => {
//											// this.show(params.index)
//											console.log(params)
//										}
//									}
//								}, params.row.time)
//							]);
//						}
//					},
					{
						title: '操作',
						width: 150,
						align: 'center',
						render: (h, params) => {
							return h('div', [
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
											// this.show(params.index)
										}
									}
								}, '编辑')
							]);
						}
					}
				],
				tableData: [
					{
						name: '45人坐',
						mess: '加班',
						cardinal:'1小时',
						money: '40元',
						time: '正常',
					},{
						name: '48人坐',
						mess: '节假日',
						cardinal:'',
						money: '400元',
						time: '正常',
					},{
						name: '6人坐',
						mess: '里程',
						cardinal:'1公里',
						money: '40元',
						time: '正常',
					}
				],
				//收索
				datetime: [],
				findMess: {
					like_CarNumber: '',
					like_ScName: '',
					pageNum: 1,
					pageSize: 5
				}
			}
		},
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '财务结算',
			}, {
				title: '核算公式',
			}])
		},
		methods: {
			//收索事件
			findMessList() {
				var v = this
				//      		axios.get('carLogs/pager',this.findMess).then((res) => {
				//                  v.tableData = res.data
				//                  v.pageTotal = res.total
				//              })
			},
			//添加新用户信息
			AddDataList() {
				var v = this
				v.compName = 'modal'
				//              axios.get('carLogs/pager',this.page).then((res) => {
				//                  v.tableData = res.data
				//                  v.pageTotal = res.total
				//              })
			},
			//分页点击事件按
			pageChange(event) {
				var v = this
				v.page.pageNum = event
				v.getDataList(v.page)
				//      		console.log(v.page)
			},
			//角色分配选项
			handleCheckAll() {
				var v = this
				if(v.indeterminate) {
					v.checkAll = false;
				} else {
					v.checkAll = !this.checkAll;
				}
				v.indeterminate = false;
				//
				if(v.checkAll) {
					// debugger
					v.roleList.forEach((item, index) => {
						v.checkAllGroup.push(item.key)
					})
				} else {
					v.checkAllGroup = [];
				}
			},
			checkAllGroupChange(data) {
				// alert(data)
				var v = this
				if(data.length == v.roleList.length) {
					v.checkAll = true;
				} else {
					v.checkAll = false;
				}
				// if (data.length === 3) {
				//     this.indeterminate = false;
				//     this.checkAll = true;
				// } else if (data.length > 0) {
				//     this.indeterminate = true;
				//     this.checkAll = false;
				// } else {
				//     this.indeterminate = false;
				//     this.checkAll = false;
				// }
			}
		}
	}
</script>