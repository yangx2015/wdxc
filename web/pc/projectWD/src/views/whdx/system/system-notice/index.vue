<style lang="less">
    @import '../../../../styles/common.less';

</style>
<!--查询统计-->
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
							<span>设备终端</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="form.mcLike" placeholder="请输入终端名称" style="width: 200px" @on-keyup.enter="getPageData()"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="getPageData()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="AddMess()">
								<Icon type="plus-round"></Icon>
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<Row>
				<Table :height="tabHeight" :row-class-name="rowClassName" :columns="columns" :data="tableData"></Table>
				<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
					<Spin fix>
						<Icon type="load-c" :size=loading.size class="demo-spin-icon-load"></Icon>
						<div style="font-size: 30px;">{{loading.text}}</div>
					</Spin>
				</div>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>
		</Card>
		<component 
			:is="componentName"
			:mess="choosedRow"></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'
    import configApi from '@/axios/config.js'
    
	import formData from './formData'
	import change from './change'
	export default {
    	name:'char',
    	mixins:[mixins],
		components:{
            formData,change
		},
        data () {
            return {
            	PickerTime:2017,
                SpinShow:false,
            	//分页
                loading:this.$store.state.app.loading,
            	pageTotal:1,
                cjsjInRange:'',
                tabHeight: 220,
                componentName:'',
                choosedRow:{},
            	page:{
            		pageNum:1,
            		pageSize:5
            	},
            	//弹层
            	showModal:false,
                columns: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
	                },
                    {
                        title: '名称',
                        align:'center',
                        key: 'mc'
                    },
                    {
                        title: '终端编号',
                        align:'center',
                        key: 'zdbh'
                    },
                    {
                        title: '绑定车辆',
                        align:'center',
                        key: 'cph',
						render:(h,p)=>{
                            let s = p.row.cph ? p.row.cph : '-';
                            return h('div',s);
						}
                    },
                    {
                        title: '厂商',
                        align:'center',
                        key: 'cs'
                    },
                    {
                        title: '接口地址',
                        align:'center',
                        key: 'sbdz',
                    },
                    {
                        title: '在线状态',
                        align:'center',
                        key: 'zxzt',
                        render:(h,p)=>{
	                     	let val = this.dictUtil.getValByCode(this,this.lmdmDictionary,p.row.zxzt)
	    					return h('div',{
	    						style:{
	    							color:p.row.zxzt=="00" ? '#279a3b':'#ed3f14'
	    						}
	    					},val)
                        }
                    },
                    {
                        title: '在线时间',
                        align:'center',
                        key: 'zxsj'
                    },
                    {
                        title: '终端状态',
                        align:'center',
                        key: 'zt',
                        render:(h,p)=>{
	                     	let val = this.dictUtil.getValByCode(this,this.ztlmdmDictionary,p.row.zt)
	    					return h('div',{
	    						style:{
	    							color:p.row.zt=="00" ? '#279a3b':'#ed3f14'
	    						}
	    					},val)
                        }
                    },
                    {
                        title: '创建时间',
                        align:'center',
                        key: 'cjsj'
                    },
                    {
                        title: '创建人',
                        align:'center',
                        key: 'cjr'
                    },
                    {
                        title:'操作',
                        align:'center',
                        type: 'action',
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
                                        	this.choosedRow = params.row
                                            this.componentName = 'change'
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
                    },
                ],
                tableData: [

                ],
                //form表单
                formTop: {
                },
                //select
                cityList: [
                ],
                //收索
                form:{
                    mcLike:'',
                	pageNum:1,
            		pageSize:5
                },
                Dictionary:[],
				lmdmDictionary:'ZDCLK0032',//在线状态
				ztDictionary:[],
				ztlmdmDictionary:'ZDCLK0031'//设备状态
            }
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '系统管理',
            },{
                title: '设备终端',
            }]),
            this.tabHeight = this.getWindowHeight() - 300
			this.getPageData()
			this.getLXDic()
        },
        methods: {
        	getLXDic(){
                this.Dictionary = this.dictUtil.getByCode(this,this.lmdmDictionary);
                this.ztDictionary = this.dictUtil.getByCode(this,this.ztlmdmDictionary);
        	},
    	    getPageData(){
                this.$http.get(configApi.ZDGL.QUERY,{params:this.form}).then((res) =>{
                    this.SpinShow = false;
                    if(res.code===200){
                        this.tableData = res.page.list;
                        this.pageTotal = res.page.total;
                        console.log('数据结构',this.tableData)
                    }
                })
			},
			pageChange(e){
    	        this.form.pageNum = e;
    	        this.getPageData();
			},
			//新增数据
			AddMess(){
				this.componentName = 'formData'
				this.choosedRow = null;
			},
            //删除数据
            listDele(r){
            	var v = this
				swal({
				  title: "是删除数据?",
				  text: "",
				  icon: "warning",
				  buttons:['取消','确认'],
				})
				.then((willDelete) => {
				  if (willDelete) {
					v.$http.post(configApi.ZDGL.DELE,{'ids':[r.zdbh]}).then((res) =>{
						if(res.code===200){
							this.$Message.success('操作成功');
						}
						v.getPageData()
					})
				  } else {
				  	this.$Message.error('操作失败');
				  }
				})
            },
            pageChange(event){
                this.form.pageNum = event;
                this.getPageData();
            }
        }
    }
</script>
