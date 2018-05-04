<style lang="less">
    @import '../../../../../styles/common.less';
    
</style>
<!--订单审核-->
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
						<Button type="primary" @click="findMessList()">查询</Button>
					</Col>
				</Row>
				<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
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
    import configApi from '@/axios/config.js'
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
                        key: 'id'
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
                        title: '联系电话',
                        align:'center',
                        key: 'cklxdh'
                    },
                    {
                        title: '候车地点',
                        align:'center',
                        key: 'hcdz'
                    },
                    {
                        title: '目的地',
                        align:'center',
                        key: 'mdd'
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
                                            this.pass(params.row.id)
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
                                            this.reject(params.row.id)
                                        }
                                    }
                                },'驳回')
                            ]);
                        }
                    }
                ],
                tableData: [
	                {
	                	id:'201801020903000001',
	                	jgmc:'计算机学院',
	                	ck:'毛毛',
	                	cklxdh:'13174174110',
	                	hcdz:'武汉大学正门',
	                	mdd:'武汉火车站'
	                },
	                {
	                	id:'201801020903000001',
	                	jgmc:'计算机学院',
	                	ck:'毛毛',
	                	cklxdh:'13174174110',
	                	hcdz:'武汉大学正门',
	                	mdd:'武汉火车站'
	                },
	                {
	                	id:'201801020903000001',
	                	jgmc:'计算机学院',
	                	ck:'毛毛',
	                	cklxdh:'13174174110',
	                	hcdz:'武汉大学正门',
	                	mdd:'武汉火车站'
	                },
	                {
	                	id:'201801020903000001',
	                	jgmc:'计算机学院',
	                	ck:'毛毛',
	                	cklxdh:'13174174110',
	                	hcdz:'武汉大学正门',
	                	mdd:'武汉火车站'
	                },
	                {
	                	id:'201801020903000001',
	                	jgmc:'计算机学院',
	                	ck:'毛毛',
	                	cklxdh:'13174174110',
	                	hcdz:'武汉大学正门',
	                	mdd:'武汉火车站'
	                },
	                {
	                	id:'201801020903000001',
	                	jgmc:'计算机学院',
	                	ck:'毛毛',
	                	cklxdh:'13174174110',
	                	hcdz:'武汉大学正门',
	                	mdd:'武汉火车站'
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
					ddzt:'10',
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
			this.findMessList();
        },
        methods: {
        	changeTime(val){
//      		this.findMess.gte_StartTime=val[0]
//      		this.findMess.lte_StartTime=val[1]
//      		log(this.findMess)
//      		this.findMessList()
        	},
        	print(){
        		window.print()
        	},
            listEve(num,event){
        		log('event',event)
        		log('num',num)
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

			pass(id){
                swal({
                    title: "审核通过?",
                    text: "",
                    icon: "success",
                    buttons:['取消','确认'],
                }).then((willDelete) => {
                        if (willDelete) {
                            this.audit(id,'11');
                        }
                    });
			},
			reject(id){
                swal({
                    title: "审核驳回?",
                    text: "",
                    icon: "success",
                    buttons:['取消','确认'],
                }).then((willDelete) => {
                    if (willDelete) {
                        this.audit(id,'12');
                    }
                });
			},
            audit(id,zt){
        	    let param = {
        	        ddzt:zt,
					id:id
				}
                this.$http.post(configApi.ORDER.AUDIT,param).then((res) =>{
                    if (res.code === 200){
                        this.findMessList();
                    }
                })
			},
        	GetMess(page){
        		var v = this
//      		log(page)
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
                        this.tableData = res.page.list;
                        this.pageTotal = res.page.total;
                    }
                })
            },

        }
    }
</script>
