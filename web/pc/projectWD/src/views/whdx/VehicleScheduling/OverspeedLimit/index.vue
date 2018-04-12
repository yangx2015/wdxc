<style lang="less">
	@import '../../../../styles/common.less';
</style>
<style lang="less" scoped="scoped">
	.fromTiT {
		/*text-align: right;*/
	}
</style>
<!--超速限定-->
<template>
	<div class="topDiv">
		<Card>
			<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>超速限定</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
							<Input v-model="findMess.cxLike" placeholder="请输入车型" style="width: 200px" @on-keyup.enter="getmess()"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="getmess()">
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
				showModal: false,
				tableTiT: [{
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
					{
						title: '车型',
						align: 'center',
						width:'120',
						key: 'cx'
					},
					{
						title: '车速上限',
						align: 'center',
						key: 'sdsx',
						render:(h,p)=> {
                            return h('div', p.row.sdsx +' KM/h')
                    	}
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
				tableData: [{
					numCar: '鄂A12345',
					dr:'李成',
					time: '2017-05-02',
					mess:"5",
					max:"75km/h"
				}],
				//收索
                cjsjInRange:[],
				findMess: {
					cjsjInRange:'',
                    cxLike: '',
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
				title: '超速限定',
			}])
			this.tabHeight = this.getWindowHeight() - 290
            this.SpinShow = false;
            this.getmess()
		},
		methods: {
			getmess(){
                if (this.cjsjInRange.length != 0 && this.cjsjInRange[0] != '' && this.cjsjInRange[1] != ''){
                    this.findMess.cjsjInRange = this.getdateParaD(this.cjsjInRange[0])+","+this.getdateParaD(this.cjsjInRange[1]);
                }else{
                    this.findMess.cjsjInRange = '';
                }
				var v = this
				this.$http.get(configApi.CS.QUERY,{params:v.findMess}).then((res) =>{
					console.log('超速数据',res)
					v.tableData = res.page.list
					v.pageTotal = res.page.total
					v.SpinShow = false;
				})
			},
            listF(res){
                this.getmess()
                this.compName = ''
            },
        	AddDataList() {
				var v = this
				v.compName = 'newmes'
			},
        	findMessList(){
        		var v = this
        	},
        	listDele(r){
                this.$http.post(configApi.CS.DELE,{'ids':[r.id]}).then((res) =>{
                    if(res.code===200){
                        this.$Message.success('操作成功');
                    }else{
                        this.$Message.error('操作成功');
                    }
                    this.getmess()
                })
        	},
            pageChange(event){
        		var v = this
        		v.findMess.pageNum = event
                this.getmess()
//      		console.log(v.page)
        	},
		}
	}
</script>