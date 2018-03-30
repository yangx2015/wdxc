<style lang="less">
    @import '../../../../styles/common.less';
    
</style>
<!--角色管理-->
<template>
    <div class="box boxbackborder">
    	<div class="tit">
    		<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
    			<div style="height: 45px;line-height: 45px;">
    				<Row class="margin-top-10">
    					<Col span="3">
    						<span  class="titmess">权限分配</span>
    					</Col>
				        <Col span="15" class="inputSty">
				        	<Input v-model="findMess.like_CarNumber" placeholder="请输入角色名称..." style="width: 200px" @on-change="findMessList"></Input>
				        	<Input v-model="findMess.like_ScName" placeholder="请输入角色类型..." style="width: 200px" @on-change="findMessList"></Input>
				        </Col>
				        <Col span="6" class="butevent">
				        	<Button type="primary" @click="findMessList()">
				        		<Icon type="search"></Icon>
								<!--查询-->
				        	</Button>
				        	<Button type="primary" @click="getDataList()">
								<Icon type="plus-round"></Icon>
				        	</Button>
				        </Col>
				    </Row>
    			</div>
			</Row>
    	</div>
    	<div class="body">
	    	<Row>
	    		<Table
    			 	:height="tabHeight"
	    			:row-class-name="rowClassName"
	    			:columns="tableTiT"
	    			:data="tableData"></Table>
	    		<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
					<Spin fix>
			            <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
			            <div style="font-size: 30px;">数据加载中请稍后</div>
			        </Spin>
				</div>
	    	</Row>
	    	<Row class="margin-top-10 pageSty">
		    	<Page :total=pageTotal 
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
	
	import addmess from './comp/addmess.vue'
	import mess from './comp/mess.vue'
	export default {
    	name:'char',
    	mixins:[mixins],
    	components: {
    		addmess,mess
		},
        data () {
            return {
            	SpinShow:true,
            	tabHeight: 220,
            	compName:'',
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
                        title: '权限编码',
                        width:120,
                        align:'center',
                        key: 'RoleCode'
                    },
                    {
                        title: '权限名称',
                        align:'center',
                        key: 'RoleName'
                    },
                    {
                        title: '备注',
                        align:'center',
                        key: 'Remarks'
                    },
                    {
                        title: '创建时间',
                        align:'center',
                        key: 'time'
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
                                        icon:'navicon-round',
                                        shape:'circle',
                                        size:'small'
                                    },
                                    style: {
                                        cursor: "pointer",
                                        margin:'0 8px 0 0'
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
                                        icon:'close',
                                        shape:'circle',
                                        size:'small'
                                    },
                                    style: {
                                        cursor: "pointer",
                                        margin:'0 8px 0 0'
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
                tableData: [
	                {
	                	RoleCode:'123456',
	                	RoleName:'用户权限',
	                	RoleType:'用户权限',
	                	Remarks:'用户权限说明',
	                	time:'2017-05-02 09:10:00'
	                },
	                {
	                	RoleCode:'123456',
	                	RoleName:'日志权限',
	                	RoleType:'日志权限',
	                	Remarks:'日志权限说明',
	                	time:'2017-05-02 09:10:00'
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
                title: '系统管理',
            },{
                title: '权限分配',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
        	setTimeout(() => {
                this.SpinShow = false;
            }, 100);
//          this.getmess()
        },
        methods: {
        	getmess(){
				this.$http.get(configApi.USER.AP).then((res) =>{
					console.log(res)
//					this.tableData = res.page.list
//					this.SpinShow = false;
				})
			},
        	colsemodal(){
            	this.compName = ''
            },
        	changeTime(val){
        		this.findMess.gte_StartTime=val[0]
        		this.findMess.lte_StartTime=val[1]
//      		console.log(this.findMess)
        		this.findMessList()
        	},
        	print(){
        		window.print()
        	},
            listEve(event){
//      		console.log('event',event)
        		var v = this
          		v.showModal = true
          		v.formTop = event
        	},
        	getDataList(){
                var v = this
                v.compName = 'addmess'
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
