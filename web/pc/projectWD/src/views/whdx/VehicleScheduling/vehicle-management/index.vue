<style lang="less">
    @import '../../../../styles/common.less';
</style>
<!--车辆管理-->
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
							<span>车辆管理</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
							<Input v-model="findMess.cphLike" placeholder="请输入车牌号" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
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
	
  	import expandRow from './table-expand.vue'
  	import newmes from './comp/newmes.vue'
	export default {
	  name:'char',
      components: {
        expandRow,newmes
      },
    	mixins:[mixins],
        data () {
            return {
            	mess:{},
            	messType:true,
            	compName:'newmes',
                clztDict:[],
                clztDictCode:'ZDCLK0016',
                cxDict:[],
                cxDictCode:'ZDCLK0019',
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
//                  {
//                    title:'#',
//                    type: 'expand',
//                    width: 50,
//                    render: (h, params) => {
//                        return h(expandRow, {
//                            props: {
//                                row: params.row
//                            }
//                        })
//                    }
//                  },
					{
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
                    {
                        title: '车牌号',
                        width:'100',
                        align:'center',
                        key: 'cph'
                    },
                    {
                        title: '车型',
                        align:'center',
                        key: 'cx',
                        render:(h,p)=>{
                            let val = this.dictUtil.getValByCode(this,this.cxDictCode,p.row.cx)
                            return h('div',val)
                        }
                    },
                    {
                        title: '载客量',
                        align:'center',
                        key: 'zkl'
                    },
                    {
                        title: '创建人',
                        width:160,
                        align:'center',
                        key: 'cjr'
                    },
                    {
                        title: '创建时间',
                        width:160,
                        align:'center',
                        key: 'cjsj'
                    },
                    {
                        title: '司机',
                        align:'center',
                        key: 'sjxm'
                    },
                    {
                        title: '车辆状态',
                        align:'center',
                        key: 'zt',
                        render:(h,p)=>{
                            let val = this.dictUtil.getValByCode(this,this.clztDictCode,p.row.zt)
                            return h('div',val)
                        }
                    },
                    {
                        title: '终端编号',
                        align:'center',
                        key: 'zdbh'
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
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.messType = false
                                        	this.mess = params.row
                                            this.compName = newmes
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
                tableData: [
	                {
	                	carNumber:'201701010914230001',
	                	LicensePlate:'鄂A12345',
	                	carType:'大巴车',
	                	carModel:'48人座',
	                	time:'2017-05-02 09:10:00',
	                	carState:'离线'
	                }
                ],
                //收索
                cjsjInRange:[],
				findMess: {
					cjsjInRange:'',
                    cphLike: '',
					pageNum: 1,
					pageSize: 5
				},
            }
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆调度',
            },{
                title: '车辆管理',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
            this.SpinShow = false;
            this.getmess()
			this.getCxDict();
			this.getClztDict();
        },
        methods: {
            getClztDict(){
                this.clztDict = this.dictUtil.getByCode(this,this.clztDictCode);
            },
            getCxDict(){
                this.cxDict = this.dictUtil.getByCode(this,this.cxDictCode);
            },
        	getmess(){
                if (this.cjsjInRange.length != 0 && this.cjsjInRange[0] != '' && this.cjsjInRange[1] != ''){
        	        this.findMess.cjsjInRange = this.getdateParaD(this.cjsjInRange[0])+","+this.getdateParaD(this.cjsjInRange[1]);
				}else{
                    this.findMess.cjsjInRange = '';
				}
				var v = this
				this.$http.get(configApi.CLGL.QUERY,{params:v.findMess}).then((res) =>{
					console.log('车辆数据',res.page.list)
					v.tableData = res.page.list
					v.pageTotal = res.page.total
					v.SpinShow = false;
				})
			},
        	AddDataList() {
				var v = this
				v.mess = {}
            	v.messType = true
				v.compName = 'newmes'
			},
        	findMessList(){
                this.getmess()
        	},
        	listDele(id){
                this.util.del(this,configApi.CLGL.DELE,[id.clId],()=>{
                    this.getmess()
				})
			},
            pageChange(event){
                this.findMess.pageNum = event
                this.getmess()
        	},
        }
    }
</script>
