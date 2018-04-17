<style lang="less">
    @import '../../../../styles/common.less';

</style>
<!--角色管理-->
<template>
    <div>
		<Card>
			<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>角色管理</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
							<Input v-model="findMess.jsmcLike" placeholder="请输入用户名" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
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
			<Row style="position: relative;">
				<Table
						:row-class-name="rowClassName"
						:height="tabHeight"
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
		</Card>
    	<component
    		:is="compName" 
    		:messdata="messdata"
    		:usermesType="userMesType"
    		@listF='listF'></component>
    </div>
</template>

<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'
	
	import addrole from './comp/addmess.vue'
	import modifyRolePermission from './comp/modifyRolePermission.vue'
	export default {
    	name:'char',
    	mixins:[mixins],
    	components: {
    		addrole,
            modifyRolePermission
		},
        data () {
            return {
            	SpinShow:true,
            	messdata:'',
            	userMesType:true,
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
                        title: '角色编码',
                        width:120,
                        align:'center',
                        key: 'jsId'
                    },
                    {
                        title: '角色名称',
                        align:'center',
                        key: 'jsmc'
                    },
                    {
                        title: '类型',
                        align:'center',
                        key: 'jslx',
                        render:(h,p)=>{
                            switch(p.row.jslx){
                                case '00':
                                    return h('div','管理');
                                case '11':
                                default:
                                    return h('div','员工');
                            }
                        }
                    },
                    {
                        title: '备注',
                        align:'center',
                        key: 'bz'
                    },
                    {
                        title: '创建时间',
                        align:'center',
                        key: 'cjsj'
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
										icon: 'edit',
										shape: 'circle',
										size: 'small'
									},
									style: {
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
											this.userMesType = false
											this.messdata = params.row
											this.compName = 'addrole'
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
                                        	this.listDele(params.row.jsId)
											this.getmess()
                                        }
                                    }
                                })
                            ]);
                        }
                    }
                ],
                tableData: [
	                {
	                	jsId:'123456',
	                	jsmc:'驾驶员角色',
	                	jslx:'员工',
	                	bz:'角色说明',
	                	cjsj:'2017-05-02 09:10:00'
	                }
                ],
                //form表单
                formTop: {
                },
                //select
                cityList: [
                ],
                //收索
                cjsjInRange:[],
                findMess:{
                	cjsjInRange:'',
                	jsmcLike:'',
                	pageNum:1,
            		pageSize:5
                }
            }
        },
        watch: {
			cjsjInRange:function(newQuestion, oldQuestion){
				this.findMess.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
			},
		},
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '系统管理',
            },{
                title: '角色管理',
            }]),
            this.tabHeight = this.getWindowHeight() - 290
            this.getmess()
        },
        mounted(){
        },
        methods: {
        	getmess(){
        		this.$http.get(configApi.ROLE.QUERY).then((res) =>{
					this.tableData = res.page.list
					this.SpinShow = false;
				})
        	},
        	RootShowF(val) {
				this.messdata = val.row
				this.compName = 'mess'
			},
			//收索事件
        	findMessList(){
        		var v = this
        		v.SpinShow = true;
				this.$http.get(configApi.ROLE.QUERY,{params:v.findMess}).then((res) =>{
					//console.log(res)
					v.tableData = res.page.list
					v.SpinShow = false;
				})
        	},
			//数据删除
        	listDele(id) {
        		this.util.del(this,configApi.ROLE.DELE,[id],()=>{
                    this.getmess();
				});
            },
            //添加数据
        	AddDataList(){
        		var v = this
    			v.compName = 'addrole'
    			this.userMesType = true
    			this.messdata = {}
            },
            listF(res){
            	this.getmess()
            	this.compName = ''
            },
            pageChange(event){
        		var v = this
        		v.page.pageNum = event
//      		console.log(v.page)
			}
        }
    }
</script>
