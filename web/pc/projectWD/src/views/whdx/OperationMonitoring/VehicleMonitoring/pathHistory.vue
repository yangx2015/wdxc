<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--用户管理-->
<template>
	<div class="boxbackborder" style="z-index: 99999">
		<Modal width='1200' v-model="showModal"  title="历史轨迹">
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="">
							<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
						</div>
						<div class="butevent">
							<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<Row :gutter="16">
				<Col span="6" v-for="item in tableData">
					<Card>
						<Row>
							<img src="'http://api.map.baidu.com/staticimage?width=400&height=300&center='+item.ksjd,item.kswd+'&zoom=11&markers='+item.ksjd,item.kswd+'|'+item.ksjd,item.kswd+'&markerStyles=l,0|l,1'" class="imgItem">
						</Row>
						<Row>
							<Col span="8"><span>开始时间</span></Col><Col span="16"><span class="span_time">{{item.kssj}}</span></Col>
							<Col span="8"><span>结束时间</span></Col><Col span="16"><span class="span_time">{{item.jssj}}</span></Col>
							<Col span="8"><span>时长</span></Col><Col span="16"><span class="span_time">35分钟</span></Col>
						</Row>
					</Card>
				</Col>
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
				tableTiT: [
				    {
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
				    {
						title: "开始时间",
						width: 200,
						align: 'center',
						key: 'kssj'
					},
				    {
						title: "结束时间",
						width: 200,
						align: 'center',
                        key: 'jssj'
					},
                    {
                        title:'操作',
                        align:'center',
                        type: 'action',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'success',
                                        icon: 'navicon-round',
                                        shape: 'circle',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.showPath();
                                        }
                                    }
                                }),
                            ]);
                        }
                    }
				],
				tableData: [
				    {
				        ksjd:'114.365288',
						kswd:'30.54485',
						kssj: '2018-04-01 09:10:00',
						jssj: '2018-04-01 09:10:00',
					},
				    {
                        ksjd:'114.365252',
                        kswd:'30.546126',
						kssj: '2018-04-01 09:10:00',
						jssj: '2018-04-01 09:10:00',
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
		    showPath(){
		      this.$parent.showPath();
		      this.$parent.componentName = '';
			},
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
<style>
	.span_time{
		color: #5cadff;
	}
	.imgItem{
		width: 100%;
		height: 100%;
	}
</style>
