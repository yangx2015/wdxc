<style lang="less">
    @import '../../../../styles/common.less';
    
</style>
<!--日志管理-->
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
							<span>日志管理</span>
						</div>
						<div class="body-r-1">
							<DatePicker v-model="czsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
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
    </div>
</template>

<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'
	
	export default {
    	name:'char',
    	mixins:[mixins],
        data () {
            return {
            	SpinShow:true,
				tabHeight: 220,
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
                        title: '操作类型',
                        width:120,
                        align:'center',
                        key: 'czlx'
                    },
                    {
                        title: '操作时间',
                        align:'center',
                        key: 'czsj'
                    },
                    {
                        title: '操作人 ',
                        align:'center',
                        key: 'czr'
                    },
                    {
                        title: '对象ID',
                        align:'center',
                        key: 'dx_id'
                    },
                    {
                        title: '对象类型',
                        align:'center',
                        key: 'dxlx'
                    },
                    {
                        title: '参数',
                        align:'center',
                        key: 'cs'
                    },
                    {
                        title: '执行时间',
                        align:'center',
                        key: 'zxsj'
                    },
                    {
                        title: '备注',
                        align:'center',
                        key: 'sm'
                    },
                    {
                        title: '方法',
                        align:'center',
                        key: 'ff'
                    },
                    {
                        title: '操作结果',
                        key: 'jg',
                        width: 150,
                        align: 'center',
                    }
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
                czsjInRange:[],
                findMess:{
                	czsjInRange:[],
                	pageNum:1,
            		pageSize:5
                }
            }
        },
        watch: {
			czsjInRange:function(newQuestion, oldQuestion){
				this.findMess.czsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
			},
		},
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '系统管理',
            },{
                title: '日志管理',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
            this.getmess()
        },
        methods: {
        	getmess(){
				var v = this
				this.$http.get(configApi.DAILY.QUERY).then((res) =>{
					console.log('数据',res)
					v.tableData = res.page.list
					v.SpinShow = false;
				})
			},
            pageChange(event){
        		var v = this
        		v.page.pageNum = event
        		v.getDataList(v.page)
//      		console.log(v.page)
        	},
        	findMessList(){
        		var v = this
        		this.$http.get(configApi.DAILY.QUERY,{params:v.findMess}).then((res) =>{
					console.log('数据',res)
					v.tableData = res.page.list
					v.SpinShow = false;
				})
        	},

        }
    }
</script>
