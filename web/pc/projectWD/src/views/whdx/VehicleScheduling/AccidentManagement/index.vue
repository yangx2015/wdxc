<style lang="less">
	@import '../../../../styles/common.less';
</style>
<style lang="less" scoped="scoped">
	.fromTiT {
		/*text-align: right;*/
	}

</style>
<!--事故管理-->
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
							<span>事故管理</span>
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
		</div>
		<div class="body">
			<Row>
				<Table 
					:row-class-name="rowClassName" 
					:columns="tableTiT" 
					:height="tabHeight"
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
		name: 'char',
		components: {
			newmes
        },
		mixins: [mixins],
		data() {
			return {
				mess:{},
            	messType:true,
            	compName:'',
				
				SpinShow:true,
				tabHeight: 220,
				//分页
				pageTotal: 1,
				page: {
					pageNum: 1,
					pageSize: 5
				},
				tableTiT: [
					{
                	  	title:'序号',
                        type: 'index',
                        align:'center',
                        width: 60,
                    },
                    {
                        title: '车牌号码',
                        align:'center',
                        key: 'cph'
                    },
                    {
                        title: '司机',
                        align:'center',
                        key: 'sj'
                    },
                    {
                        title: '联系电话',
                        align:'center',
                        key: 'lxdh'
                    },
                    {
                        title: '发生时间',
                        align:'center',
                        key: 'sgsj'
                    },
                    {
                        title: '备注',
                        align:'center',
                        key: 'bz'
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
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '车辆管理',
			}, {
				title: '事故管理',
			}])
			this.tabHeight = this.getWindowHeight() - 290
            this.SpinShow = false;
            this.getmess()
		},
		methods: {
			getmess(){
				var v = this
				this.$http.get(configApi.SG.QUERY,{params:v.findMess}).then((res) =>{
					console.log('事故数据',res)
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
//      		console.log(v.page)
        	},
		}
	}
</script>