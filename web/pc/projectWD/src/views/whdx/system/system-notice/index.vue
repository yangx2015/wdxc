<style lang="less">
    @import '../../../../styles/common.less';
    
</style>
<!--查询统计-->
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
							<span>设备终端</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="getPageData()" style="width: 220px"></DatePicker>
							<Input v-model="form.mcLike" placeholder="请输入终端名称" style="width: 200px" @on-keyup.enter="getPageData()"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="getPageData()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="componentName = 'formData'">
								<Icon type="plus-round"></Icon>
							</Button>
						</div>
					</div>
				</div>
			</Row>
		</div>
		<div class="body">
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
		</div>
		<component :is="componentName"></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'
    import configApi from '@/axios/config.js'
	import formData from './formData'
	export default {
    	name:'char',
    	mixins:[mixins],
		components:{
            formData
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
				choosedRow:{},
                componentName:'',
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
                        title: '厂商',
                        align:'center',
                        key: 'cs'
                    },
                    {
                        title: '在线状态',
                        align:'center',
                        key: 'zxzt'
                    },
                    {
                        title: '在线时间',
                        align:'center',
                        key: 'zxsj'
                    },
                    {
                        title: '地址',
                        align:'center',
                        key: 'dz'
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
                                            this.choosedRow = params.row;
                                            this.componentName = 'formData';
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
                datetime:[],
                form:{
                    mcLike:'',
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
                title: '设备终端',
            }]),
			this.getPageData();
        },
        methods: {
    	    getPageData(){
                if (this.cjsjInRange.length != 0 && this.cjsjInRange[0] != '' && this.cjsjInRange[1] != ''){
                    this.form.cjsjInRange = this.getdateParaD(this.cjsjInRange[0])+","+this.getdateParaD(this.cjsjInRange[1]);
                }else{
                    this.form.cjsjInRange = '';
                }
                this.$http.get(configApi.ZDGL.QUERY,{params:this.form}).then((res) =>{
                    this.SpinShow = false;
                    if(res.code===200){
                        this.tableData = res.page.list;
                        this.pageTotal = res.page.total;
                    }
                })
			},
			pageChange(e){
    	        this.form.pageNum = e;
    	        this.getPageData();
			},
            //删除数据
            listDele(r){
                this.$http.post(configApi.ZDGL.DELE,{'ids':[r.zdbh]}).then((res) =>{
                    if(res.code===200){
                        this.$Message.success('操作成功');
                    }else{
                        this.$Message.error('操作成功');
                    }
                    this.getmess()
                })
            },
            pageChange(event){
                this.form.pageNum = event;
                this.getmess();
            }
        }
    }
</script>
