<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--用户管理-->
<template>
	<div class="boxbackborder" style="z-index: 99999">
		<Modal width='900' v-model="showModal"  title="历史轨迹">
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="">
							<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
							<Input v-model="findMess.zjhmLike" placeholder="请输入用户名" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
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
						:columns="tableTiT"
						:data="tableData"></Table>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>
		</Modal>
	</div>
</template>

<script>
	import configApi from '@/axios/config.js'
	export default {
		name: 'char',
		data() {
			return {
                showModal:true,
				tabHeight: 220,
				compName: '',
				pageTotal: 2,
				page: {
					pageNum: 1,
					pageSize: 5
				},
				tableTiT: [{
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
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
				cjsjInRange:[],
				findMess: {
					cjsjInRange:'',
					zjhmLike: '',
					pageNum: 1,
					pageSize: 5
				},
			}
		},
		// watch: {
		// 	cjsjInRange:function(newQuestion, oldQuestion){
		// 		this.findMess.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
		// 	},
		// },
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '系统管理',
			}, {
				title: '用户管理',
			}])
		},
		methods: {
            findMessList(){
                var v = this
                // this.$http.get('/api/clzd/getNearbyStations',{params:{lng:114.36457,lat:30.545504}}).then((res) =>{
                //
                // })
			},
			//分页点击事件按
			pageChange(event) {
				var v = this
				v.findMess.pageNum = event
			}
		}
	}
</script>