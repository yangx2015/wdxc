<style lang="less">
	@import '../../../../../styles/common.less';
	.SC {
		.full-calendar-header {
			font-size: 22px;
		}
		.day-number {
			font-size: 18px;
		}
		.dates-bg {
			.today {
				background: #09b938!important;
			}
		}
		.comp-full-calendar {
			max-width: 100%!important;
			margin: 0!important;
		}
	}
	#fullcalendar{
		.full-calendar-body .dates .dates-events .events-week .events-day{
			min-height: 74px!important;
		}
		.full-calendar-body .dates .week-row .day-cell{
			min-height: 74px!important;
		}
		.comp-full-calendar{
			padding: 15px!important;
		}
	}
</style>
<template>
	<div class="SC box">
		<div class="body" v-show='dateMess' style="background-color: #fff;">
			<component 
				:is="modalName"
				:todaytime='todaytime'
				@okdrag='okdrag'></component>
		</div>
		<div class="body" v-show='!dateMess' style="height: 100%;">
			<div class="box-row">
				<div class="body-r-1" style="height:100%;border-right: solid 1px #22CDDE;">
					<div id="fullcalendar" style="height: 100%;">
						<fullcalendar :events='events' lang='zh' title='日历标题' @dayClick="dayClick" @eventClick='eventClick'>
						</fullcalendar>
					</div>
						<!-- <div slot="fc-header-left" style="background-color: #f00;">
									123
							</div>
							<div slot="fc-header-right" style="background-color: #f00;">
									456
							</div>
							<div slot="fc-body-card" style="background-color: #f00;">
							 		798
							</div> -->
				</div>
				<div style="width: 360px;padding: 15px;height: 100%;background-color: #fff;border-left: solid 1px #22CDDE;">
					<div class="box" style="">
						<div class="tit">
							<div style="text-align: center;font-size: 18px;padding: 5px;">
								<b>
									{{todaytime}}
								</b>
								<span style="font-size: 14px;">
									排班信息
								</span>
							</div>
							<div style="text-align: right;padding-bottom: 5px;">
								<Button type="primary" size="small" @click="changeClick">编辑</Button>
							</div>
						</div>
						<div class="body">
							<Table 
								border 
								:height="tabHeight" 
								:columns="columns2" 
								:data="data2"></Table>
							<div>
								<Button type="warning">复制当日排班信息</Button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import fullcalendar from 'vue-fullcalendar'
	import drag from './comp/drag.vue'
	import drlist from '../../../components/draggable-list/draggable-list.vue'
	import mixins from '@/mixins'
	export default {
		name: '',
		mixins:[mixins],
		components: {
			fullcalendar,
			drag,drlist
		},
		data() {
			return {
				tabHeight:'',
				todaytime:'',
				dateMess: false,
				modalName: '',
				events: [{
						title: '早班',
						start: '2018-01-01',
						cssClass: 'family',
						YOUR_DATA: {
							name: 'hello'
						}
					},
					{
						title: 'event1',
						start: '2018-01-01',
						cssClass: 'family',
						YOUR_DATA: {}
					},
					{
						title: 'event2',
						start: '2018-01-06',
						end: '2018-01-08',
						cssClass: ['family', 'career'],
						YOUR_DATA: {}
					}
				],
				columns1: [{
						title: '序号',
						type: 'index'
					},
					{
						title: '班次',
						key: 'Shift'
					},
					{
						title: '线路',
						key: 'line'
					},
					{
						title: '司机',
						key: 'driver'
					}
				],
				data1: [{
					Shift: '早班',
					line: '999线',
					driver: '王炫智'
				}],
				columns2: [
                	{
                        type: 'index',
                        width: 40,
                        align: 'center'
                    },
                    {
                        title: '班次',
                        key: 'date'
                    },
                    {
                        title: '车辆',
                        width: 100,
                        key: 'car',
                        align: 'center'
                    },
                    {
                        title: '线路',
                        key: 'line',
                        align: 'center'
                    },
                    {
                        title: '状态',
                        key: 'type',
                        align: 'center',
                        render: (h, params) => {
							return h('div', [
								h('span',{
									style:{
										fontWeight:900,
										color:params.row.type=='正常'?'#228B22':'#FF4500',
									}
								},params.row.type)
							]);
						}
                    }
                ],
                data2: [
                    {
                        date:'早班',
                        car:'鄂A12345',
                        line:'1号线路',
                        type:'正常',
                        km:'45km/h'
                    },
                    {
                        date:'午班',
                        car:'鄂A11345',
                        line:'1号线路',
                        type:'正常',
                        km:'60km/h'
                    },
                    {
                        date:'晚班',
                        car:'鄂A96345',
                        line:'1号线路',
                        type:'正常',
                        km:'20km/h'
                    },
                    {
                        date:'早班',
                        car:'鄂A12695',
                        line:'3号线路',
                        type:'正常',
                        km:'40km/h'
                    },
                    {
                        date:'晚班',
                        car:'鄂A12665',
                        line:'3号线路',
                        type:'正常',
                        km:'70km/h'
                    }
                ]
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
				title: '班车排班',
			}])
		},
		mounted(){
			this.todaytime = this.getdateStrD()
			this.getalert()
		},
		methods: {
			okdrag(){
//				alert('132')
				this.dateMess=!this.dateMess
			},
			getalert(){
        		var windowHeight = window.innerHeight
        		this.tabHeight = windowHeight - 280
        		log('浏览器高',this.tabHeight)
        	},
        	changeClick(){
        		this.dateMess = true
        		this.modalName = drag
//				this.modalName = drlist
        	},
			dayClick(event) {
				this.todaytime = this.getdateParaD(event)
//				log('天事件', this.getdateParaD(event))
//				log('天事件', event.toLocaleString())
//				this.dateMess = true
			},
			eventClick(event) {
				log('备注事件', event)
			}
		}
	}
</script>

<style>

</style>