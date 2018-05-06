<!--订单查阅-->
<style lang="less">
    @import '../../../../../styles/common.less';
</style>
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
							<span>单位管理</span>
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
						:columns="columns10"
						:data="tableData"></Table>
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
    	name:'',
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

            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:8
            	},
                columns10: [
                    {
                	  	title:'序号',
                        type: 'index',
                        align:'center',
                        width: 60,
                    },
                    {
                        title: '单位编号',
                        align:'center',
                        key: 'dwbh'
                    },
                    {
                        title: '单位名称',
                        align:'center',
                        key: 'dwmc'
                    },
                    {
                        title: '负责人',
                        align:'center',
                        key: 'lxr'
                    },
                    {
                        title: '联系电话',
                        align:'center',
                        key: 'lxdh'
                    },
                    {
                        title: '状态',
                        align:'center',
                        key: 'zt'
                    },
                    {
                        title: '登记次数',
                        align:'center',
                        key: 'djcs'
                    },
                    {
                        title: '创建人',
                        align:'center',
                        key: 'cjr'
                    },
                    {
                        title: '创建时间',
                        width:'100',
                        align:'center',
                        key: 'cjsj'
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
                    },
                ],
                tableData: [
                    {
                        UnitNumber:'010024',
                        UnitName:'创世纪',
                        ResName:'王小海',
                        ResPhone:'15178946598',
                        type:'正常',
                        register:'5',
                        Founder:'王峰',
                        time:'2017-09-08 10:11:12'
                    }
                ],
                //收索
                cjsjInRange:[],
				findMess: {
					cjsjInRange:'',
					zjhmLike: '',
					pageNum: 1,
					pageSize:8
				}
            }
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆管理',
            },{
                title: '临时车管理',
            },{
                title: '单位管理',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
            this.SpinShow = false;
            this.getmess()
        },
        methods:{
        	getmess(){
				var v = this
				this.$http.get(configApi.LSDW.QUERY,{params:v.findMess}).then((res) =>{
					log('临时单位',res)
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
