<style lang="less">
	@import '../../../../styles/common.less';
</style>
<style lang="less" scoped="scoped">
	.fromTiT {
		/*text-align: right;*/
	}
</style>
<!--报销记账-->
<template>
	<div class="boxbackborder">
		<Card>
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
						<Icon type="ios-paper" size='30' color='#fff'></Icon>
					</span>
				<div style="height: 45px;line-height: 45px;">
					<Row class="margin-top-10">
						<Col span="4">
							<span class="titmess">报销记账</span>
						</Col>
						<Col span="14">
							<Input v-model="findMess.like_CarNumber" placeholder="请输入用户名" style="width: 200px" @on-change="findMessList"></Input>
						</Col>
						<Col span="6" class="butevent">
							<Button type="primary" @click="">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="AddDataList()">
								<Icon type="plus-round"></Icon>
							</Button>
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
					pageSize:8
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
				tableTiT: [{
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
					{
						title: '报销人',
						align: 'center',
						key: 'name'
					},
					{
						title: '报销事项',
						align: 'center',
						key: 'mess'
					},
					{
						title: '报销时间',
						align: 'center',
						key: 'time'
					},
					{
						title: '报销金额',
						align: 'center',
						key: 'money'
					},
					{
						title: '发票数量',
						align: 'center',
						key: 'number'
					},
				],
				tableData: [{
					name:'李成',
					mess:'油费报销',
					time:'2018-02-01',
					money:'10250元',
					number:'1'
				}],
				//收索
				datetime: [],
				findMess: {
					like_CarNumber: '',
					like_ScName: '',
					pageNum: 1,
					pageSize:8
				}
			}
		},
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '财务结算',
			}, {
				title: '报销记账',
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
			},
			//分页点击事件按
			pageChange(event) {
				var v = this
				v.page.pageNum = event
				v.getDataList(v.page)
				//      		log(v.page)
			}
		}
	}
</script>
