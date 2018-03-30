<style lang="less">
    @import '../../../../styles/common.less';
</style>
<style lang="less" scoped="scoped">
    .fromTiT{
    	/*text-align: right;*/
    }
</style>
<!--订单统计-->
<template>
    <div class="box boxbackborder">
    	<div class="tit">
    		<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
    			<div style="height: 45px;line-height: 45px;">
    				<Row class="margin-top-10">
    					<Col span="4">
				        	<span class="titmess">订单统计</span>
				        </Col>
				        <Col span="14">
				        	<Input v-model="findMess.like_ScName" placeholder="请输入查询信息..." style="width: 200px" @on-change="findMessList"></Input>
				        </Col>
				        <Col span="6" class="butevent">
				        	<Button type="primary" @click="AddDataList()">
				        		<Icon type="search"></Icon>
								<!--查询-->
				        	</Button>
				        </Col>
				    </Row>
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
	    	<!-- <Row class="margin-top-10" style="text-align: right;">
		    	<Page :total=pageTotal
		    		:current=page.pageNum
		    		:page-size=page.pageSize
		    		show-total
		    		show-elevator
		    		@on-change='pageChange'></Page>
		    </Row> -->
    	</div>
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
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:5
            	},
            	//弹层--角色分配
            	RootShow:false,
            	indeterminate: false,
            	checkAll:false,
      				checkAllGroup:[],
              roleList:[
                {
                  value:'司机',
                  key:'01'
                },
                {
                  value:'队长',
                  key:'02'
                }
              ],
      				//弹层--新增用户
            	showModal:false,
              tableTiT: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
	                },
	                {
	                    title: '用车单位',
	                    align:'center',
	                    key: 'unit'
	                },
	                {
	                    title: '时间',
	                    align:'center',
	                    width:100,
	                    key: 'time'
	                },
	                {
                        title: '订单总数',
                        align:'center',
                        key: 'allpage'
                    },
                    {
                        title: '已审核',
                        align:'center',
                        key: 'OKaudit'
                    },
                    {
                        title: '未审核',
                        align:'center',
                        key: 'UNaudit'
                    },
                    {
                        title: '已取消',
                        align:'center',
                        key: 'cancel'
                    },
                    {
                        title: '司机确认',
                        align:'center',
                        key: 'dr'
                    },
                    {
                        title: '对长确认',
                        align:'center',
                        key: 'drM'
                    }
                ],
                tableData: [
	                {
	                	unit:'法学院',
	                	time:'2017-05-02',
	                	allpage:'25',
	                	OKaudit:'23',
	                	UNaudit:'2',
	                	cancel:'0',
	                	dr:'20',
	                	drM:'18'
	                }
                ],
                //收索
                datetime:[],
                findMess:{
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
                title: '数据报表',
            },{
                title: '订单统计',
            }])
        },
        methods: {
        	//收索事件
        	findMessList(){
        		var v = this
//      		axios.get('carLogs/pager',this.findMess).then((res) => {
//                  v.tableData = res.data
//                  v.pageTotal = res.total
//              })
        	},
        	//添加新用户信息
        	AddDataList(){
                var v = this
          		v.showModal = true

//              axios.get('carLogs/pager',this.page).then((res) => {
//                  v.tableData = res.data
//                  v.pageTotal = res.total
//              })
            },
            //分页点击事件按
            pageChange(event){
        		var v = this
        		v.page.pageNum = event
        		v.getDataList(v.page)
//      		console.log(v.page)
        	},
        	//角色分配选项
        	handleCheckAll () {
                var v = this
                if (v.indeterminate) {
                    v.checkAll = false;
                } else {
                    v.checkAll = !this.checkAll;
                }
                v.indeterminate = false;
                //
                if (v.checkAll) {
                    // debugger
                    v.roleList.forEach((item,index) => {
                      v.checkAllGroup.push(item.key)
                    })
                } else {
                    v.checkAllGroup = [];
                }
            },
            checkAllGroupChange (data) {
              // alert(data)
              var v = this
              if(data.length == v.roleList.length){
                v.checkAll = true;
              }else{
                v.checkAll = false;
              }
                // if (data.length === 3) {
                //     this.indeterminate = false;
                //     this.checkAll = true;
                // } else if (data.length > 0) {
                //     this.indeterminate = true;
                //     this.checkAll = false;
                // } else {
                //     this.indeterminate = false;
                //     this.checkAll = false;
                // }
            }
        }
    }
</script>
