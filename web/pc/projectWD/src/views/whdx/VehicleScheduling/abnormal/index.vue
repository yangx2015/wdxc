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
							<Select v-model="findMess.cx" 
								@on-change = 'findMessList'
								clearable 
								placeholder="请选择车辆类型"
								filterable style="width: 160px;">
				                <Option v-for="(item,index) in carType" 
				                	:value="item.key" 
				                	style="text-align: left;"
				                	:key="index">{{item.val}}</Option>
				            </Select>
				            <Select v-model="findMess.cph"
				            	@on-change = 'findMessList'
				            	clearable 
				            	placeholder="请选择车牌号码"
				            	filterable style="width: 160px;">
				                <Option v-for="(item,index) in carnumber" 
				                	:value="item.cph" 
				                	style="text-align: left;"
				                	:key="index">{{item.cph}}</Option>
				            </Select>
				            <Select v-model="findMess.sjlx"
				            	@on-change = 'findMessList'
				            	clearable 
				            	placeholder="请选择事件类型"
				            	filterable style="width: 160px;">
				                <Option v-for="(item,index) in thingType" 
				                	:value="item.key" 
				                	style="text-align: left;"
				                	:key="index">{{item.val}}</Option>
				            </Select>
							<DatePicker v-model="czsjInRange" 
								@on-change = 'findMessList'
								format="yyyy-MM-dd" type="daterange" 
								placement="bottom-end" placeholder="请输时间" 
								@on-keyup.enter="findMessList()" style="width: 160px"></DatePicker>
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
			<!--<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal
					  :current=page.pageNum
					  :page-size=page.pageSize
					  show-total
					  show-elevator
					  @on-change='pageChange'></Page>
			</Row>-->
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
                        title: '异常事件',
                        key: 'sjlx',
                        align: 'center',
                        render: (h, p) => {
							let val = this.dictUtil.getValByCode(this,this.dicSJcode,p.row.sjlx)
							return h('div',val)
						}
                    },
                    {
                        title: '车牌号',
                        key: 'cph',
                        align: 'center',
                    },
                    {
                        title: '驾驶员',
                        key: 'sjxm',
                        align: 'center',
                    },
                    {
                        title: '车型',
                        key: 'cx',
                        align: 'center',
                        render: (h, p) => {
							let val = this.dictUtil.getValByCode(this,this.dicCarCode,p.row.cx)
							return h('div',val)
						}
                    },
                    {
                        title: '发生时间',
                        key: 'cjsj',
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
                	cx:'',
                	cph:'',
                	sjlx:'',
                	czsjInRange:[]
//              	pageNum:1,
//          		pageSize:5
                },
               	dicCarCode:'ZDCLK0019',
               	dicCarList:[],
               	dicSJcode:'ZDCLK0038',
               	dicSJlist:[]
            }
        },
        watch: {
			czsjInRange:function(newQuestion, oldQuestion){
				if (newQuestion.length > 0 && newQuestion[0] != ''){
					this.findMess.czsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
				}else{
					this.findMess.czsjInRange  = ''
				}
				console.log('newQuestion',newQuestion)
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
			this.tabHeight = this.getWindowHeight() - 220
            this.getmess()
            this.getLXDic()
        },
        methods: {
        	getLXDic(){
        		var v = this
                this.carType = this.dictUtil.getByCode(this,this.dicCarCode);
            	this.thingType = this.dictUtil.getByCode(this,this.dicSJcode);
            	this.$http.get(configApi.CLGL.QUERY).then((res) =>{
            		if(res.code == 200){
            			v.carnumber = res.page.list
            		}
				})
            	console.log('车型',this.carType)
            	console.log('事件',this.thingType)
			},
        	getmess(){
				var v = this
				v.SpinShow = true;
				this.$http.get(configApi.CLSBYXJL.QUERY,{params:v.findMess}).then((res) =>{
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
        		this.$http.get(configApi.CLSBYXJL.QUERY,{params:v.findMess}).then((res) =>{
					console.log('数据',res)
					v.tableData = res.page.list
                    v.pageTotal = res.page.total;
					v.SpinShow = false;
				})
        	},

        }
    }
</script>
