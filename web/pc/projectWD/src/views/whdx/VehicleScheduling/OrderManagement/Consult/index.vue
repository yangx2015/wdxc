<!--订单查阅-->
<style lang="less">
    @import '../../../../../styles/common.less';
</style>
<template>
	<div class="box">
		<Card>
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>订单查询</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="datetime" type="datetime" placeholder="请输时间" style="width: 220px" @on-change="changeTime"></DatePicker>
						</div>
						<div class="butevent">
							<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<div class="body">
				<Row>
					<Table
							:row-class-name="rowClassName"
							:columns="columns10"
							:data="data9"></Table>
				</Row>
				<Row class="margin-top-10" style="text-align: right;">
					<Page
							:total=pageTotal
							:current=page.pageNum
							:page-size=page.pageSize
							show-total
							show-elevator
							@on-change='pageChange'></Page>
				</Row>
			</div>
		</Card>
		<Modal
		    v-model="showModal"
		    width='800'
		    :mask-closable="false"
		    title="信息详情">
		    <div slot='footer'></div>
		</Modal>
    </div>
</template>
<script>
	import mixins from '@/mixins'
    import configApi from '@/axios/config.js'
    import expandRow from './table-expand.vue';
    export default {
        components: {
        	expandRow
        },
        mixins:[mixins],
        data () {
            return {
            	//收索
                datetime:'',
                findMess:{
                	gte_StartTime:'',
            		lte_StartTime:'',
                	like_CarNumber:'',
                	like_ScName:'',
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
                    	title:'#',
                        type: 'expand',
                        width: 50,
                        render: (h, params) => {
                            return h(expandRow, {
                                props: {
                                    row: params.row
                                }
                            })
                        }
                    },
                    {
                        title: '用车单位',
                        align:'center',
                        key: 'jgmc'
                    },
                    {
                        title: '用车人',
                        align:'center',
                        key: 'ck'
                    },
                    {
                        title: '客户电话',
                        align:'center',
                        key: 'cklxdh'
                    },
                    {
                        title: '出车司机',
                        align:'center',
                        key: 'sjxm'
                    },
                    {
                        title: '司机电话',
                        align:'center',
                        key: 'DriverPhone'
                    },
                    {
                        title: '车型',
                        align:'center',
                        key: 'zws'
                    }
                ],
                data9: [
                ]
            }
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆管理',
            },{
                title: '订单管理',
            },{
                title: '订单查阅',
            }])
			this.findMessList()
        },
        methods:{
        	changeTime(val){
        	},
        	pageChange(event){
                var v = this
                v.page.pageNum = event
                this.findMess.pageNum = event;
                v.findMessList()
        	},
        	findMessList(){
                this.$http.get(configApi.ORDER.QUERY,{params:this.findMess}).then((res) =>{
                    if (res.code === 200 && res.page.list){
                        this.data9 = res.page.list;
                        this.pageTotal = res.page.total;
					}
                })
        	},
        }
    }
</script>
