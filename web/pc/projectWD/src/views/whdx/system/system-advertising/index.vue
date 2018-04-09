<!--订单查阅-->
<style lang="less">
    @import '../../../../styles/common.less';
</style>
<template>
	<div class="box boxbackborder">
    	<div class="tit">
    		<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
    			<div style="height: 45px;line-height: 45px;">
    				<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>活动管理</span>
				        </div>
						<div class="body-r-1 inputSty">
				        	<Input v-model="findMess.like_ScName" placeholder="请输入相关信息..." style="width: 200px" @on-change="findMessList"></Input>
				        </div>
						<div class="butevent">
				        	<Button type="primary" @click="findMessList()">
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
    	</div>
    	<div class="body">
	    	<Row>
			    <Table 
			    	:height="tabHeight"
			    	:row-class-name="rowClassName"
			    	:columns="columns10" 
			    	:data="data9"></Table>
			    <div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
					<Spin fix>
			            <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
			            <div style="font-size: 30px;">数据加载中请稍后</div>
			        </Spin>
				</div>
			</Row>
		    <Row class="margin-top-10 pageSty">
		    	<Page 
		    		:total=pageTotal 
		    		:current=page.pageNum
		    		:page-size=page.pageSize
		    		show-total
		    		show-elevator
		    		@on-change='pageChange'></Page>
		    </Row>
    	</div>
    	<component :is="compName" @colsemodal='colsemodal'></component>
	</div>
</template>
<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'
	
    import expandRow from './table-expand.vue';
    import addmess from './comp/addmess.vue'
	import mess from './comp/mess.vue'
    export default {
        components: { 
        	expandRow,
        	addmess,
			mess
        },
        mixins:[mixins],
        data () {
            return {
            	Carousel:3,
            	SpinShow:true,
				tabHeight: 220,
            	compName: '',
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
                        title: '序号',
                        width: 65,
                        align:'center',
                        type: 'index',
                    },
                    {
                        title: '活动标题',
                        align:'center',
                        key: 'VehicleUnit'
                    },
                    {
                        title: '内容/URL',
                        align:'center',
                        key: 'UseCarPeople'
                    },
                    {
                        title: '活动类型',//微信--自能站牌
                        align:'center',
                        key: 'phoneNomber'
                    },
	                    {
                    	title:'附件',
                        type: 'expand',
                        width: 65,
                        render: (h, params) => {
                            return h(expandRow, {
                                props: {
                                    row: params.row
                                }
                            })
                        }
                    },{
						title: '操作',
						key: 'action',
						width: 150,
						align: 'center',
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
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.compName = 'mess'
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
											//                                      	this.remove(params.index)
										}
									}
								})
							]);
						}
					}
                ],
                data9: [
                    {
                        VehicleUnit:'信息学院',//活动标题
                        UseCarPeople: '毛毛',//内容、URL
                        phoneNomber: '15113131414',//用车人电话
                        CarModel: '45人座位',//车型--按座位数分
                        BillModel: '单程',//单据类型--单程--往返
                        mileage: '30公里',//行车里程
                        unit: '15元/公里',//里程单价
                        mileageMoney: '450元',//里程总金额
                        Etc: '20分钟',//等时
                        RoadToll:'10元',//过路费
                        BridgeFee:'15元',//过桥费
                        mess:'用车事由',//用车事由
                        DriverName:'王二毛',//司机姓名
                        DriverPhone:'13212121212',//司机电话
                        WaitingPlace:'武汉大学正门',//候车地点
	                	Destination:'武汉火车站',//目的地
	                	startTime:'2017-01-02 08:00:00',//出发时间
                    	addMoney:'470元',//费用合计
                    	active:'img',
                    },
                    {
                    	VehicleUnit:'信息学院',
                        UseCarPeople: '毛毛',
                        phoneNomber: '15113131414',
                        CarModel: '45人座位',
                        BillModel: '单程',
                        mileage: '30公里',
                        unit: '15元/公里',
                        mileageMoney: '450元',
                        Etc: '20分钟',
                        RoadToll:'10元',
                        BridgeFee:'15元',
                        mess:'用车事由',
                        DriverName:'王二毛',
                        DriverPhone:'13212121212',
                        WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站',
	                	startTime:'2017-01-02 08:00:00',
                    	addMoney:'470元',
                    	active:'video',
                    },
                    {
                    	VehicleUnit:'信息学院',
                        UseCarPeople: '毛毛',
                        phoneNomber: '15113131414',
                        CarModel: '45人座位',
                        BillModel: '单程',
                        mileage: '30公里',
                        unit: '15元/公里',
                        mileageMoney: '450元',
                        Etc: '20分钟',
                        RoadToll:'10元',
                        BridgeFee:'15元',
                        mess:'用车事由',
                        DriverName:'王二毛',
                        DriverPhone:'13212121212',
                        WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站',
	                	startTime:'2017-01-02 08:00:00',
                    	addMoney:'470元',
                    }
                ]
            }
        },
        watch:{
        	defaultList:function(n,o){
        	}
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '系统管理',
            },{
                title: '活动管理',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
			this.SpinShow = false;
//      	this.getmess()
        },
        methods:{
        	getmess(){
				var v = this
				this.$http.get(configApi.SUGGES.QUERY).then((res) =>{
					console.log('shuju',res)
					v.tableData = res.page.list
					v.SpinShow = false;
				})
			},
        	changeTime(val){
        	},
        	pageChange(event){
        		var v = this
        	},
        	findMessList(){
        		var v = this
//      		this.$http.get('','');
//      		axios.get('carLogs/pager',this.findMess).then((res) => {
//                  v.tableData = res.data
//                  v.pageTotal = res.total
//              })
        	},
        	getDataList() {
				var v = this
				v.compName = 'addmess'
				//              axios.get('carLogs/pager',this.page).then((res) => {
				//                  v.tableData = res.data
				//                  v.pageTotal = res.total
				//              })
			},
			colsemodal() {
				this.compName = ''
			},
        }
    }
</script>
