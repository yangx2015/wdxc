<!--班车线路维护-->
<style lang="less">
	@import '../../../../../styles/common.less';
</style>
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
							<span class="titmess">班车线路维护</span>
						</Col>
						<Col span="14">
							<Input v-model="findMess.like_CarNumber" placeholder="..." style="width: 200px" @on-change="findMessList"></Input>
						</Col>
						<Col span="6" class="butevent">
							<Button type="primary" @click="findlist()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="AddSpot()">
								<Icon type="plus-round"></Icon>
							</Button>
						</Col>
					</Row>
				</div>
			</Row>
			<Row>
				<Table :height="tabHeight" :row-class-name="rowClassName" :columns="columns10" :data="data9"></Table>
				<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
					<Spin fix>
						<Icon type="load-c" :size=loading.size class="demo-spin-icon-load"></Icon>
						<div style="font-size: 30px;">{{loading.text}}</div>
					</Spin>
				</div>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>
		</Card>
		<component :is="compName"></component>
	</div>
</template>
<script>
	import mixins from '@/mixins'
	import expandRow from './table-expand.vue';
	import compModal from './comp/modal.vue'
	export default {
		components: {
			expandRow,
			compModal
		},
		mixins: [mixins],
		data() {
			return {
				SpinShow:true,
				loading:this.$store.state.app.loading,
				tabHeight: 220,
				compName: '',
				//收索
				datetime: [],
				findMess: {
					gte_StartTime: '',
					lte_StartTime: '',
					like_CarNumber: '',
					like_ScName: '',
					pageNum: 1,
					pageSize: 5
				},
				//弹层
				pageTotal: 1,
				page: {
					pageNum: 1,
					pageSize: 5
				},
				showModal: false,
				columns10: [{
						title: '#',
						type: 'expand',
						width: 50,
						render: (h, params) => {
							return h(expandRow, {
								props: {
									row: params.row
								}
							})
						}
					},
					{
						title: '序号',
						type: 'index',
						align: 'center',
						width: 60,
					},
					{
						title: '线路名称',
						align: 'center',
						key: 'siteName'
					},
					{
						title: '班次',
						align: 'center',
						key: 'direction'
					},
					{
						title: '状态',
						align: 'center',
						key: 'type'
					},
					{
						title: '运行方式',
						align: 'center',
						key: 'model'
					},
					{
						title: '创建人',
						align: 'center',
						key: 'Founder'
					},
					{
						title: '创建时间',
						width: '100',
						align: 'center',
						key: 'time'
					},
					{
						title: '备注',
						width: '100',
						align: 'center',
						key: 'mess'
					},
					{
						title: '操作',
						align: 'center',
						type: 'action',
						render: (h, params) => {
							return h('div', [
								h('Button', {
									props: {
										type: 'primary',
										icon: 'navicon-round',
										shape: 'circle',
										size: 'small'
									},
									style: {
										marginRight: '5px'
									},
									on: {
										click: () => {
											//this.show(params.index)
											this.$Message.info('编辑')
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
									on: {
										click: () => {
											//this.remove(params.index)
											this.$Message.info('确认')
										}
									}
								})
							]);
						}
					},
				],
				data9: [{
					siteName: '珞珈山',
					direction: '早班',
					type: '正常',
					model: '大吧',
					Founder: '王峰',
					time: '2017-09-08 10:11:12',
					mess: '备注信息'
				}]
			}
		},
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '车辆管理',
			}, {
				title: '班车管理',
			}, {
				title: '线路维护',
			}])
			this.tabHeight = this.getWindowHeight() - 290
			setTimeout(() => {
                this.SpinShow = false;
            }, 1000);
		},
		methods: {
			findlist() {
				this.$Message.info('查詢');
			},
			AddSpot() {
				this.compName = 'compModal'
			},
			pageChange(event) {
				var v = this
			},
			findMessList() {
				var v = this
				//      		axios.get('carLogs/pager',this.findMess).then((res) => {
				//                  v.tableData = res.data
				//                  v.pageTotal = res.total
				//              })
			},
		}
	}
</script>