<style lang="less">
    @import '../../../../styles/common.less';
    
</style>
<!--活动公告-->
<template>
    <div class="box">
    	<div class="tit">
	    	<Row class="margin-top-10">
	    		<Col span="6">
	    			<DatePicker v-model="datetime" type="datetime" placeholder="请输时间" style="width: 220px" @on-change="changeTime"></DatePicker>
	    		</Col>
		        <!--<Col span="6">
		        	<Input v-model="findMess.like_CarNumber" placeholder="..." style="width: 200px" @on-change="findMessList"></Input>
		        </Col>
		        <Col span="6">
		        	<Input v-model="findMess.like_ScName" placeholder="..." style="width: 200px" @on-change="findMessList"></Input>
		        </Col>-->
		        <Col span="18" style="text-align: right;">
		        	<Button type="primary" @click="getDataList()">新增</Button>
		        </Col>
		    </Row>
    		<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
    			<div style="height: 45px;line-height: 45px;">
	    			<span style="margin-left: 60px;font-size: 24px;">活动公告</span>
    			</div>
			</Row>
    	</div>
    	<div class="body">
	    	<Row>
	    		<Table
	    			:row-class-name="rowClassName"
	    			:columns="tableTiT"
	    			:data="tableData"></Table>
	    	</Row>
	    	<Row class="margin-top-10" style="text-align: right;">
		    	<Page :total=pageTotal 
		    		:current=page.pageNum
		    		:page-size=page.pageSize
		    		show-total
		    		show-elevator
		    		@on-change='pageChange'></Page>
		    </Row>
    	</div>
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
//	import axios from '@/axios'
	export default {
    	name:'char',
    	mixins:[mixins],
        data () {
            return {
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
                        title: '活动信息',
                        align:'center',
                        key: 'Remarks'
                    },
                    {
                        title: '创建时间',
                        width:180,
                        align:'center',
                        key: 'time'
                    }
                ],
                tableData: [
	                {
	                	Remarks:'活动信息通知',
	                	time:'2017-05-02 09:10:00',
	                },
	                {
	                	Remarks:'活动信息通知',
	                	time:'2017-05-02 09:10:00',
	                },
	                {
	                	Remarks:'活动信息通知',
	                	time:'2017-05-02 09:10:00',
	                },
	                {
	                	Remarks:'活动信息通知',
	                	time:'2017-05-02 09:10:00',
	                },
	                {
	                	Remarks:'活动信息通知',
	                	time:'2017-05-02 09:10:00',
	                },
	                {
	                	Remarks:'活动信息通知',
	                	time:'2017-05-02 09:10:00',
	                },
	                {
	                	Remarks:'活动信息通知',
	                	time:'2017-05-02 09:10:00',
	                },
	                {
	                	Remarks:'活动信息通知',
	                	time:'2017-05-02 09:10:00',
	                }
                ],
                //form表单
                formTop: {
                },
                //select
                cityList: [
                ],
                //收索
                datetime:[],
                findMess:{
                	gte_StartTime:'',
            		lte_StartTime:'',
                	like_CarNumber:'',
                	like_ScName:'',
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
                title: '活动公告',
            }]),
			this.getDataList();
        },
        methods: {
        	changeTime(val){
        		this.findMess.gte_StartTime=val[0]
        		this.findMess.lte_StartTime=val[1]
//      		console.log(this.findMess)
        		this.findMessList()
        	},
        	print(){
        		window.print()
        	},
            listEve(num,event){
        		console.log('event',event)
        		console.log('num',num)
//      		var v = this
//        		v.showModal = true
//        		v.formTop = event
        	},
        	getDataList(){
                var v = this
//              axios.get('carLogs/pager',this.page).then((res) => {
//                  v.tableData = res.data
//                  v.pageTotal = res.total
//              })
            },
        	GetMess(page){
        		var v = this
//      		console.log(page)
        	},
            pageChange(event){
        		var v = this
        		v.page.pageNum = event
        		v.getDataList(v.page)
//      		console.log(v.page)
        	},
        	findMessList(){
        		var v = this
//      		axios.get('carLogs/pager',this.findMess).then((res) => {
//                  v.tableData = res.data
//                  v.pageTotal = res.total
//              })
        	},

        }
    }
</script>
