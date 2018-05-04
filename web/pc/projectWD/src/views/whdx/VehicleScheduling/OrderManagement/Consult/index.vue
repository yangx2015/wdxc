<!--订单查阅-->
<style lang="less">
    @import '../../../../../styles/common.less';
</style>
<template>
	<div class="box">
		<Card>
			<div class="tit">
				<Row class="margin-top-10">
					<Col span="6">
						<DatePicker v-model="datetime" type="datetime" placeholder="请输时间" style="width: 220px" @on-change="changeTime"></DatePicker>
					</Col>
					<Col span="6">
						<Input v-model="findMess.like_CarNumber" placeholder="..." style="width: 200px" @on-change="findMessList"></Input>
					</Col>
					<Col span="6">
						<Input v-model="findMess.like_ScName" placeholder="..." style="width: 200px" @on-change="findMessList"></Input>
					</Col>
					<Col span="6" style="text-align: right;">
						<Button type="primary" @click="getDataList()">查询</Button>
					</Col>
				</Row>
				<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
					<div style="height: 45px;line-height: 45px;">
						<span style="margin-left: 60px;font-size: 24px;">订单查询</span>
					</div>
				</Row>
			</div>
			<div class="body">
				<Row>
					<Table
							:row-class-name="rowClassName"
							:columns="columns10"
							:data="data9"></Table>
				</Row>
				<Row class="margin-top-10" style="text-align: right;">
					<Page
							:total=pageTotal
							:current=page.pageNum
							:page-size=page.pageSize
							show-total
							show-elevator
							@on-change='pageChange'></Page>
				</Row>
			</div>
		</Card>
		<Modal
		    v-model="showModal"
		    width='800'
		    :mask-closable="false"
		    title="信息详情">
		    <div slot='footer'></div>
		</Modal>
    </div>
</template>
<script>
	import mixins from '@/mixins'
    import configApi from '@/axios/config.js'
    import expandRow from './table-expand.vue';
    export default {
        components: {
        	expandRow
        },
        mixins:[mixins],
        data () {
            return {
            	//收索
                datetime:[],
                findMess:{
                	gte_StartTime:'',
            		lte_StartTime:'',
                	like_CarNumber:'',
                	like_ScName:'',
                	pageNum:1,
            		pageSize:5
                },
            	//弹层
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:5
            	},
            	showModal:false,
                columns10: [
                    {
                    	title:'#',
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
                        title: '用车单位',
                        align:'center',
                        key: 'jgmc'
                    },
                    {
                        title: '用车人',
                        align:'center',
                        key: 'ck'
                    },
                    {
                        title: '客户电话',
                        align:'center',
                        key: 'cklxdh'
                    },
                    {
                        title: '出车司机',
                        align:'center',
                        key: 'sjxm'
                    },
                    {
                        title: '司机电话',
                        align:'center',
                        key: 'DriverPhone'
                    },
                    {
                        title: '车型',
                        align:'center',
                        key: 'zws'
                    }
                ],
                data9: [
                    {
                        jgmc:'信息学院',//用车单位
                        ck: '毛毛',//用车人
                        cklxdh: '15113131414',//用车人电话
                        zws: '45人座位',//车型--按座位数分
                        BillModel: '单程',//单据类型--单程--往返
                        mileage: '30公里',//行车里程
                        unit: '15元/公里',//里程单价
                        mileageMoney: '450元',//里程总金额
                        Etc: '20分钟',//等时
                        RoadToll:'10元',//过路费
                        BridgeFee:'15元',//过桥费
                        mess:'用车事由',//用车事由
                        sjxm:'王二毛',//司机姓名
                        DriverPhone:'13212121212',//司机电话
                        WaitingPlace:'武汉大学正门',//候车地点
	                	Destination:'武汉火车站',//目的地
	                	startTime:'2017-01-02 08:00:00',//出发时间
                    	addMoney:'470元',//费用合计
                    },
                    {
                    	jgmc:'信息学院',
                        ck: '毛毛',
                        cklxdh: '15113131414',
                        zws: '45人座位',
                        BillModel: '单程',
                        mileage: '30公里',
                        unit: '15元/公里',
                        mileageMoney: '450元',
                        Etc: '20分钟',
                        RoadToll:'10元',
                        BridgeFee:'15元',
                        mess:'用车事由',
                        sjxm:'王二毛',
                        DriverPhone:'13212121212',
                        WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站',
	                	startTime:'2017-01-02 08:00:00',
                    	addMoney:'470元',
                    },
                    {
                    	jgmc:'信息学院',
                        ck: '毛毛',
                        cklxdh: '15113131414',
                        zws: '45人座位',
                        BillModel: '单程',
                        mileage: '30公里',
                        unit: '15元/公里',
                        mileageMoney: '450元',
                        Etc: '20分钟',
                        RoadToll:'10元',
                        BridgeFee:'15元',
                        mess:'用车事由',
                        sjxm:'王二毛',
                        DriverPhone:'13212121212',
                        WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站',
	                	startTime:'2017-01-02 08:00:00',
                    	addMoney:'470元',
                    }
                ]
            }
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆管理',
            },{
                title: '订单管理',
            },{
                title: '订单查阅',
            }])
			this.findMessList()
        },
        methods:{
        	changeTime(val){
        	},
        	pageChange(event){
                var v = this
                v.page.pageNum = event
                this.findMess.pageNum = event;
                v.findMessList()
        	},
        	findMessList(){
                this.$http.get(configApi.ORDER.QUERY,{params:this.findMess}).then((res) =>{
                    if (res.code === 200 && res.page.list){
                        this.data9 = res.page.list;
                        this.pageTotal = res.page.total;
					}
                })
        	},
        }
    }
</script>
