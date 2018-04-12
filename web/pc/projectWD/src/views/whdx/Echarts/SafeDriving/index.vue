<style lang="less">
	@import '../../../../styles/common.less';
</style>
<style lang="less" scoped="scoped">
	.fromTiT {
		/*text-align: right;*/
	}
</style>
<!--安全驾驶统计-->
<template>
	<div class="topDiv">
		<Card>
			<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<Row class="margin-top-10">
						<Col span="4">
							<span class="titmess">安全驾驶</span>
						</Col>
						<Col span="14">
							<Input v-model="findMess.like_ScName" placeholder="请输入查询信息..." style="width: 200px" @on-change="findMessList"></Input>
						</Col>
						<Col span="6" class="butevent">
							<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
						</Col>
					</Row>
				</div>
			</Row>
			<Row>
				<Table :row-class-name="rowClassName" :columns="tableTiT" :data="tableData"></Table>
			</Row>
		</Card>
	</div>
</template>

<script>
	import mixins from '@/mixins'
	//	import axios from '@/axios'
	export default {
		name: 'char',
		mixins: [mixins],
		data() {
			return {
				//弹层--角色分配
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
						title: '时间',
						align: 'center',
						width: 100,
						key: 'time'
					},
					{
						title: '车牌号',
						align: 'center',
						key: 'unit'
					},
					{
						title: '驾驶人',
						align: 'center',
						key: 'allpage'
					},
					{
						title: '急加速',
						align: 'center',
						key: 'OKaudit'
					},
					{
						title: '急刹车',
						align: 'center',
						key: 'UNaudit'
					},
					{
						title: '超速行驶',
						align: 'center',
						key: 'cancel'
					}
				],
				tableData: [{
					unit: '鄂A12345',
					time: '2017-05-02',
					allpage: '周师傅',
					OKaudit: '23',
					UNaudit: '2',
					cancel: '5',
				}],
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
				title: '数据报表',
			}, {
				title: '安全驾驶',
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
			}
		}
	}
</script>