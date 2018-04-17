<style lang="less">
    @import '../../../../styles/common.less';
</style>
<!--驾驶员管理-->
<template>
    <div class="boxbackborder">
		<Card>
			<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>驾驶员管理</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
							<Input v-model="findMess.xmLike" placeholder="请输入用户名" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
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
				<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
					<Spin fix>
						<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
						<div style="font-size: 30px;">数据加载中请稍后</div>
					</Spin>
				</div>
			</Row>
			<Row class="margin-top-10" style="text-align: right;">
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
            	PickerTime:2017,
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:5
            	},
                tableTiT: [
                  	{
  	                	title:"#",
  	                	width:40,
  	                	align:'center',
  	                	type:'index',
                      fixed: 'left'
  	                },
                    {
                        title: '头像',
                        width:60,
                        align:'center',
                        key: 'tx',
                        fixed: 'left',
                        render: (h, params) => {
                            return h('div', [
                                h('icon', {
                                    props: {
                                        type: params.row.tx,
                                        size: '22'
                                    },
                                    on: {
                                        click: () => {
																					console.log('数据调试',params)
                                        }
                                    }
                                })
                            ]);
                        }
                    },
                    {
                        title: '姓名',
                        width:80,
                        align:'center',
                        key: 'xm',
                        fixed: 'left'
                    },
  	                {
                        title: '性别',
                        width:60,
                        align:'center',
                        key: 'xb',
                        render: (h, params) => {
                            return h('div',params.row.xb == '1' ? '男' : '女')
                        }
                    },
                    {
                        title: '年龄',
						width:60,
                        align:'center',
                        key: 'nl'
                    },
                    {
                        title: '身份证号',
						width:200,
                        align:'center',
                        key: 'sfzhm'
                    },
                    {
                        title: '状态',
                        width:80,
                        align:'center',
                        key: 'zt',
                        render:(h,p)=>{
                            let val = this.dictUtil.getValByCode(this,this.ztDictCode,p.row.zt);
                            return h('div',val);
                        }
                    },
                    {
                        title:'驾驶证类型',
                        width:120,
                        align:'center',
                        key:'zjcx'
                    },
                    {
                        title:'等级',
                        width:80,
                        align:'center',
                        key:'dj'
                    },
                    {
                        title:'车队编号',
                        width:180,
                        align:'center',
                        key:'cdbh'
                    },
                    {
                        title:'初领日期',
                        width:150,
                        align:'center',
                        key:'clrq'
                    },
                    {
                        title:'档案编号',
                        width:180,
                        align:'center',
                        key:'dabh'
                    },
                    {
                        title: '创建时间',
                        width:150,
                        align:'center',
                        key: 'cjsj'
                    },
                    {
                        title:'创建人',
                        width:100,
                        align:'center',
                        key:'cjr'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        fixed: 'right',
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
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                        	this.messType = false
                                        	this.mess = params.row
                                            this.compName = 'newmes'
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
					xmLike: '',
					pageNum: 1,
					pageSize: 5
				},
                ztDict:[],
                ztDictCode:'ZDCLK0016',
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
                title: '车辆管理',
            },{
                title: '驾驶员管理',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
            this.SpinShow = false;
            this.getmess()
            this.getClztDict()
        },
        methods: {
            getClztDict(){
                this.ztDict = this.dictUtil.getByCode(this,this.ztDictCode);
            },
        	getmess(){
				var v = this
				this.$http.get(configApi.JSY.QUERY,{params:v.findMess}).then((res) =>{
					console.log('驾驶员数据',res)
					v.tableData = res.page.list
					v.pageTotal = res.page.total
					v.SpinShow = false;
				})
			},
        	AddDataList() {
				var v = this
				v.mess = {},
            	v.messType = true,
				v.compName = 'newmes'
			},
        	findMessList(){
        		var v = this
        		v.getmess()
        	},
        	listDele(id){
        		this.$http.post(configApi.JSY.DELE,{'ids':[id.sfzhm]}).then((res) =>{
					if(res.code===200){
						this.$Message.success('操作成功');
					}else{
						this.$Message.error('操作失败');
					}
					this.getmess()
				})
        	},
            pageChange(event){
        		var v = this
        		v.findMess.pageNum = event
				v.getmess()
        	},
        }
    }
</script>
