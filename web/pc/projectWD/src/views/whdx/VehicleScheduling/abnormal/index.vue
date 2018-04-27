<style lang="less">
    @import '../../../../styles/common.less';
    
</style>
<!--异常记录-->
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
							<span>异常行驶记录</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input placeholder="车辆类型" style="width: 160px;"></Input>
							<Input placeholder="车牌号码" style="width: 160px;"></Input>
							<Input placeholder="事件类型" style="width: 160px;"></Input>
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
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:5
            	},
                tableTiT: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
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
                //车型
                carType:[],
                //车牌号码
                carnumber:[],
                //事件类型
                thingType:[],
                //收索时间
                czsjInRange:[],
                findMess:{
                	carType:'',
                	carnumber:'',
                	thingType:'',
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
//          this.getmess()
        },
        methods: {
        	getmess(){
				var v = this
				v.SpinShow = true;
				this.$http.get(configApi.DAILY.QUERY,{params:v.findMess}).then((res) =>{
					console.log('数据',res)
					v.tableData = res.page.list
					v.pageTotal = res.page.total;
					v.SpinShow = false;
				})
			},
            pageChange(event){
        		var v = this
        		v.findMess.pageNum = event
        		v.findMessList()
//      		console.log(v.page)
        	},
        	findMessList(){
        		var v = this
        		v.SpinShow = true;
        		this.$http.get(configApi.DAILY.QUERY,{params:v.findMess}).then((res) =>{
					console.log('数据',res)
					v.tableData = res.page.list
                    v.pageTotal = res.page.total;
					v.SpinShow = false;
				})
        	},

        }
    }
</script>
