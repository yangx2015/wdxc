<style lang="less">
    @import '../../../../../styles/common.less';
    
</style>
<!--订单审核-->
<template>
    <div class="box">
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
    		<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
    			<div style="height: 45px;line-height: 45px;">
	    			<span style="margin-left: 60px;font-size: 24px;">订单审核</span>
    			</div>
			</Row>
    	</div>
    	<div class="body">
	    	<Row>
	    		<Table
	    			:row-class-name="rowClassName"
	    			:columns="tableTiT"
	    			:data="tableData"></Table>
	    	</Row>
	    	<Row class="margin-top-10" style="text-align: right;">
		    	<Page :total=pageTotal 
		    		:current=page.pageNum
		    		:page-size=page.pageSize
		    		show-total
		    		show-elevator
		    		@on-change='pageChange'></Page>
		    </Row>
    	</div>
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
	import swal from 'sweetalert'
//	import axios from '@/axios'
	export default {
    	name:'char',
    	mixins:[mixins],
        data () {
            return {
            	PickerTime:2017,
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:5
            	},
            	//弹层
            	showModal:false,
                tableTiT: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
	                },
	                {
                        title: '订单编号',
                        width:180,
                        align:'center',
                        key: 'messNumber'
                    },
                    {
                        title: '用车单位',
                        align:'center',
                        key: 'UseCarUnit'
                    },
                    {
                        title: '用车人',
                        align:'center',
                        key: 'UseCarPeople'
                    },
                    {
                        title: '联系电话',
                        align:'center',
                        key: 'phoneNomber'
                    },
                    {
                        title: '候车地点',
                        align:'center',
                        key: 'WaitingPlace'
                    },
                    {
                        title: '目的地',
                        align:'center',
                        key: 'Destination'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'success',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.listEve(h,params)
                                        }
                                    }
                                },'通过'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.listEve(h,params)
                                        }
                                    }
                                },'驳回')
                            ]);
                        }
                    }
                ],
                tableData: [
	                {
	                	messNumber:'201801020903000001',
	                	UseCarUnit:'计算机学院',
	                	UseCarPeople:'毛毛',
	                	phoneNomber:'13174174110',
	                	WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站'
	                },
	                {
	                	messNumber:'201801020903000001',
	                	UseCarUnit:'计算机学院',
	                	UseCarPeople:'毛毛',
	                	phoneNomber:'13174174110',
	                	WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站'
	                },
	                {
	                	messNumber:'201801020903000001',
	                	UseCarUnit:'计算机学院',
	                	UseCarPeople:'毛毛',
	                	phoneNomber:'13174174110',
	                	WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站'
	                },
	                {
	                	messNumber:'201801020903000001',
	                	UseCarUnit:'计算机学院',
	                	UseCarPeople:'毛毛',
	                	phoneNomber:'13174174110',
	                	WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站'
	                },
	                {
	                	messNumber:'201801020903000001',
	                	UseCarUnit:'计算机学院',
	                	UseCarPeople:'毛毛',
	                	phoneNomber:'13174174110',
	                	WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站'
	                },
	                {
	                	messNumber:'201801020903000001',
	                	UseCarUnit:'计算机学院',
	                	UseCarPeople:'毛毛',
	                	phoneNomber:'13174174110',
	                	WaitingPlace:'武汉大学正门',
	                	Destination:'武汉火车站'
	                }
                ],
                //form表单
                formTop: {
                },
                //select
                cityList: [
                ],
                //收索
                datetime:[],
                findMess:{
                	gte_StartTime:'',
            		lte_StartTime:'',
                	like_CarNumber:'',
                	like_ScName:'',
                	pageNum:1,
            		pageSize:5
                }
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
                title: '订单审核',
            }]),
			this.getDataList();
        },
        methods: {
        	changeTime(val){
//      		this.findMess.gte_StartTime=val[0]
//      		this.findMess.lte_StartTime=val[1]
//      		console.log(this.findMess)
//      		this.findMessList()
        	},
        	print(){
        		window.print()
        	},
            listEve(num,event){
        		console.log('event',event)
        		console.log('num',num)
        		var v = this
				swal({
				  title: "审核通过?",
				  text: "",
				  icon: "success",
				  buttons:['取消','确认'],
				})
				.then((willDelete) => {
				  if (willDelete) {
				  	this.$Message.success('订单审核通过');
				  } else {
				  	this.$Message.error('订单审核失败');
				  }
				});
        	},
        	getDataList(){
                var v = this
//              axios.get('carLogs/pager',this.page).then((res) => {
//                  v.tableData = res.data
//                  v.pageTotal = res.total
//              })
            },
        	GetMess(page){
        		var v = this
//      		console.log(page)
        	},
            pageChange(event){
        		var v = this
        		v.page.pageNum = event
        		v.getDataList(v.page)
//      		console.log(v.page)
        	},
        	findMessList(){
        		var v = this
//      		axios.get('carLogs/pager',this.findMess).then((res) => {
//                  v.tableData = res.data
//                  v.pageTotal = res.total
//              })
        	},

        }
    }
</script>
