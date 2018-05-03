<style lang="less">
    @import '../../../../../styles/common.less';
</style>
<!--临时车辆管理-->
<template>
    <div class="boxbackborder">
		<Card>
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>临时车辆管理</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
							<Input v-model="findMess.zjhmLike" placeholder="请输入用户名" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="AddDataList()">
								<Icon type="plus-round"></Icon>
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<Row>
				<Table
						:height="tabHeight"
						:row-class-name="rowClassName"
						:columns="tableTiT"
						:data="tableData"></Table>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal
					  :current=page.pageNum
					  :page-size=page.pageSize
					  show-total
					  show-elevator
					  @on-change='pageChange'></Page>
			</Row>
		</Card>
		<component
			:is="compName" 
			:mess="mess"
			:messType="messType"></component>
    </div>
</template>

<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'
	
	import newmes from './comp/newmes.vue'
	export default {
    	name:'char',
    	components: {
			newmes
        },
    	mixins:[mixins],
        data () {
            return {
            	mess:{},
            	messType:true,
            	compName:'',
            	
            	SpinShow:true,
				tabHeight: 220,
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:5
            	},
                tableTiT: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
	                },
	                {
                        title: '车辆编号',
                        width:200,
                        align:'center',
                        key: 'clId'
                    },
                    {
                        title: '车牌号码',
                        align:'center',
                        key: 'cph'
                    },
                      {
                        title: '所属单位',
                        align:'center',
                        key: 'lsdwId'
                    },
                    {
                        title: '车辆类别',
                        align:'center',
                        key: 'cllx'
                    },
                    {
                        title: '车辆型号',
                        align:'center',
                        key: 'zws'
                    },
                    {
                        title: '注册时间',
                        width:160,
                        align:'center',
                        key: 'cjsj'
                    },
                    {
                        title: '车辆状态',
                        align:'center',
                        key: 'zt'
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
                                            this.messType = false
                                        	this.mess = params.row
                                            this.compName = 'newmess'
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
                                            this.listDele(params.row)
                                        }
                                    }
                                })
                            ]);
                        }
                    }
                ],
                tableData: [],
                //收索
                cjsjInRange:[],
				findMess: {
					cjsjInRange:'',
					zjhmLike: '',
					pageNum: 1,
					pageSize: 5
				}
            }
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆调度',
            },{
                title: '临时车管理',
            },{
                title: '车辆管理',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
            this.SpinShow = false;
            this.getmess()
        },
        methods: {
        	getmess(){
				var v = this
				this.$http.get(configApi.LSC.QUERY,{params:v.findMess}).then((res) =>{
					log('临时车数据',res)
					v.tableData = res.page.list
					v.pageTotal = res.page.total
					v.SpinShow = false;
				})
			},
        	AddDataList() {
				var v = this
				v.compName = 'newmes'
			},
        	findMessList(){
        		var v = this
        	},
        	listDele(){
        		
        	},
            pageChange(event){
        		var v = this
        		v.page.pageNum = event
//      		log(v.page)
        	},
        }
    }
</script>
