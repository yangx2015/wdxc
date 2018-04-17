<!--订单查阅-->
<style lang="less">
    @import '../../../../styles/common.less';
</style>
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
							<span>活动管理</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="findMess.hdbtLike" placeholder="请输入活动名称" style="width: 200px" @on-change="findMessList"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
							<Button type="primary" @click="getDataList()">
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
						:data="data9"></Table>
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
    	<component :is="compName" @colsemodal='colsemodal'></component>
	</div>
</template>
<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'
	
    import expandRow from './table-expand.vue';
    import addmess from './comp/addmess.vue'
	import mess from './comp/mess.vue'
    export default {
        components: { 
        	expandRow,
        	addmess,
			mess
        },
        mixins:[mixins],
        data () {
            return {
            	Carousel:3,
            	SpinShow:true,
				tabHeight: 220,
            	compName: '',
            	//收索
                datetime:[],
                choosedRow:null,
                findMess:{
                	hdbtLike:'',
                	pageNum:1,
            		pageSize:5
                },
            	//弹层
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:5
            	},
            	showModal:false,
                columns10: [
                	{
                        title: '序号',
                        width: 65,
                        align:'center',
                        type: 'index',
                    },
                    {
                        title: '活动标题',
                        align:'center',
                        key: 'hdbt'
                    },
                    {
                        title: '内容/URL',
                        align:'center',
                        key: 'url',
                        render: (h, params) => {
                            return h('a', {
                                attrs: {
                                    href: params.row.url
                                }
                            },params.row.url)
                        }
                    },
                    {
                        title: '活动类型',//微信--自能站牌
                        align:'center',
                        key: 'hdlx',
                        render: (h, params) => {
                            let lx = '';
                            switch(params.row.hdlx){
								case '11':
								    lx = '微信';
								    break;
								case '22':
                                default:
                                    lx = '智能站牌'
                                    break;
							}
                            return h('div',lx);
                        }
                    },
	                    {
                    	title:'附件',
                        type: 'expand',
                        width: 65,
                        render: (h, params) => {
                            return h(expandRow, {
                                props: {
                                       row: params.row
                                }
                            })
                        }
                    },{
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
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
										    this.choosedRow = params.row;
											this.compName = 'addmess'
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
									style: {
										cursor: "pointer",
										margin: '0 8px 0 0'
									},
									on: {
										click: () => {
                                        	this.remove(params.row.hdId)
										}
									}
								})
							]);
						}
					}
                ],
                data9: []
            }
        },
        watch:{
        	defaultList:function(n,o){
        	}
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '系统管理',
            },{
                title: '活动管理',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
			this.SpinShow = false;
     		this.getmess()
        },
        methods:{
        	getmess(){
				var v = this
				v.SpinShow = true;
				this.$http.get(configApi.ADVERTISING.QUERY).then((res) =>{
					v.data9 = res.page.list
					v.pageTotal = res.total
					v.SpinShow = false;
				})
			},
        	changeTime(val){
        	},
        	pageChange(event){
        		var v = this
        	},
        	findMessList(){
        		var v = this
        		v.SpinShow = true;
				this.$http.get(configApi.ADVERTISING.QUERY,{params:this.findMess}).then((res) => {
					 v.data9 = res.data
					 v.pageTotal = res.total
					 v.SpinShow = false;
				 })
        	},
        	remove(id){
        		this.util.del(this,configApi.ADVERTISING.DELE,[id],()=>{
                    this.getmess();
				});
        	},
        	getDataList() {
				var v = this
				this.choosedRow = null;
				v.compName = 'addmess'
				//              axios.get('carLogs/pager',this.page).then((res) => {
				//                  v.tableData = res.data
				//                  v.pageTotal = res.total
				//              })
			},
			colsemodal() {
				this.compName = ''
			},
        }
    }
</script>
