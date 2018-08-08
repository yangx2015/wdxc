<style lang="less">
    @import '../../../../styles/common.less';

</style>
<style>
</style>
<!--车辆管理-->
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
							<span>车辆管理</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="param.cphLike" placeholder="请输入车牌号" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
							<Input v-model="param.zdbhLike" placeholder="请输入终端编号" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
							<Input v-model="param.sjxmLike" placeholder="请输入司机姓名" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
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
			<Row :gutter="20">
				<Col span="6" v-for="(item,index) in tableData" :key="index" style="margin-top: 16px;">
					<Card>
						<p slot="title" style="font-size: 18px">
							<Icon type="ios-car" size="24"/>
							{{item.cph}}
						</p>
						<a href="#" slot="extra" @click.prevent="changeLimit">
							<Tag color="cyan">{{getCx(item.cx)+item.zkl+'座'}}</Tag>
						</a>
						<Row>
							<Col span="24">
								<Row v-if="item.sjxm">
									<Col span="2" style="padding-right: 45px;margin-top: -5px;">
										<Icon type="md-person" size="28" color="#2d8cf0"/>
									</Col>
									<Col span="10">
										<span>{{item.sjxm}}</span>
									</Col>
									<Col span="4" offset="6">
										<Poptip
												confirm
												title="确认解除绑定?"
												@on-ok="ok"
												@on-cancel="cancel">
											<Button type="text" icon="ios-trash" style="color:#ff9900;font-size:24px;margin-top: -16px;" ghost></Button>
										</Poptip>
									</Col>
								</Row>
								<Row v-else-if="!item.sjxm">
									<Col span="2" style="padding-right: 45px;margin-top: -5px;">
										<Icon type="md-person" size="28" color="#2d8cf0"/>
									</Col>
									<Col span="10">
										<span  v-if="!item.bindDrvFlag">暂未绑定</span>
										<Select v-else-if="item.bindDrvFlag" style="width:150px" clearable @on-clear="()=>{item.bindDrvFlag=false}">
											<Option value="10">11</Option>
										</Select>
									</Col>
									<Col span="4" offset="6">
										<Tooltip content="绑定司机" v-if="!item.bindDrvFlag">
											<Button type="text" icon="md-code-working" style="color:#2db7f5;font-size:24px;margin-top: -16px;" ghost @click="()=>{item.bindDrvFlag=true}">绑定1</Button>
										</Tooltip>
										<Button v-else-if="item.bindDrvFlag" type="info" ghost >绑定2</Button>
									</Col>
								</Row>
							</Col>
						</Row>
						<Row>
							<Col span="24">
								<Row>
									<Col span="2" style="padding-right: 45px;margin-top: -5px;">
										<Icon type="md-videocam" size="28" color="#2d8cf0"/>
									</Col>
									<Col span="10" ref="termRef">
										<span v-if="!item.bindTremFlag">暂未绑定</span>
										<Select v-else-if="item.bindTremFlag" style="width:150px" clearable @on-clear="()=>{item.bindTremFlag=false}">
											<Option value="10">{{item.zdbh}}</Option>
										</Select>
									</Col>
									<Col span="4" offset="6">
										<Tooltip content="绑定设备" v-if="!item.bindTremFlag">
											<Button type="text" icon="md-code-working" style="color:#2db7f5;font-size:24px;margin-top: -16px;" ghost  @click="()=>{cellData.bindTremFlag=true}"></Button>
										</Tooltip>
										<Button v-else-if="item.bindTremFlag" type="info" ghost >绑定</Button>
									</Col>
								</Row>
							</Col>
						</Row>
						<Row type="flex" justify="end" style="padding-top: 20px">
							<Col span="22">
								<ButtonGroup size="large">
									<Tooltip content="编辑">
										<Button  icon="ios-clipboard"></Button>
									</Tooltip>
									<Tooltip content="车辆档案">
										<Button  icon="ios-bookmarks"></Button>
									</Tooltip>
									<Tooltip content="历史轨迹">
										<Button  icon="md-map"></Button>
									</Tooltip>
									<Tooltip content="电子围栏">
										<Button icon="ios-globe"></Button>
									</Tooltip>
									<Tooltip content="删除">
										<Button icon="md-trash"></Button>
									</Tooltip>
								</ButtonGroup>
							</Col>
						</Row>
					</Card>
				</Col>
			</Row>
			<!--<Row>-->
				<!--<Table-->
						<!--:height="tabHeight"-->
						<!--:columns="tableTiT"-->
						<!--:data="tableData"-->
				<!--&gt;</Table>-->
			<!--</Row>-->
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal
					  :current=param.pageNum
					  :page-size=param.pageSize :page-size-opts=[8,10,20,30,40,50]  @on-page-size-change='(e)=>{param.pageSize=e;pageChange()}'
					  show-total
					  show-elevator show-sizer
					  @on-change='pageChange'></Page>
			</Row>
		</Card>
    	<component
			:is="compName"
			:mess="mess"
			:derMess="derMes"
			:messType="messType"></component>
    </div>
</template>

<script>
	import mixins from '@/mixins'
  	import expandRow from './table-expand.vue'
  	import newmes from './comp/newmes.vue'
	import allmes from './comp/otherMess.vue'
	import bkShow from './comp/BKshow.vue'

	export default {
	  name:'char',
      components: {
        expandRow,newmes,allmes,bkShow
      },
    	mixins:[mixins],
        data () {
            return {
            	mess:{},
            	derMes:{
            		sjId:'',
            		sjxm:''
            	},
            	messType:true,
            	compName:'',
                clztDict:[],
                clztDictCode:'ZDCLK0016',
                cxDict:[],
                cxDictCode:'ZDCLK0019',
            	SpinShow:true,
				tabHeight: 220,
            	PickerTime:2017,
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:8
            	},
                tableData: [],
                //收索
				param: {
                    cphLike: '',
					pageNum: 1,
					pageSize:8
				},
            }
        },
        created(){
	      log('123','title')
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆调度',
            },{
                title: '车辆管理',
            }]),
			this.tabHeight = this.getWindowHeight() - 290
            this.SpinShow = false;
            this.getPageData()
			this.getCxDict();
			this.getClztDict();
        },
        methods: {
	      ok(item){

		  },
	      cancel(item){

		  },
	      getCx(code){
              let val = this.dictUtil.getValByCode(this,this.cxDictCode,code)
			  return val;
		  },
            getClztDict(){
                this.clztDict = this.dictUtil.getByCode(this,this.clztDictCode);
            },
            getCxDict(){
                this.cxDict = this.dictUtil.getByCode(this,this.cxDictCode);
            },
        	getPageData(){
				var v = this
				this.$http.get(this.apis.CLGL.QUERY,{params:v.param}).then((res) =>{
				    if (res.code == 200 && res.page.list){
                        v.tableData = res.page.list
                        v.pageTotal = res.page.total
						for (let r of v.tableData){
						    r.bindDrvFlag = !!r.sjxm;
						}
					}

					v.SpinShow = false;
				})
			},
			unbindDevice(carId){
                swal({
                    title: "确定解绑该终端？",
                    text: "",
                    icon: "warning",
                    buttons:['取消','确认'],
                }).then((willDelete) => {
                    if (willDelete) {
                        this.$http.post(this.apis.CLGL.unbindDevice,{carId:carId}).then((res)=>{
                            if (res.code === 200){
                                this.getPageData();
                                this.$Message.success("解绑成功");
                            }else{
                                this.$Message.error(res.message);
                            }
                        })
                    }
                });
			},
			unbindDriver(carId){
                swal({
                    title: "确定解绑驾驶员？",
                    text: "",
                    icon: "warning",
                    buttons:['取消','确认'],
                }).then((willDelete) => {
                    if (willDelete) {
                        this.$http.post(this.apis.CLGL.unbindDriver,{carId:carId}).then((res)=>{
                            if (res.code === 200){
                                this.getPageData();
                                this.$Message.success("解绑成功");
                            }else{
                                this.$Message.error(res.message);
                            }
                        })
                    }
                });
			},
        	AddDataList() {
				var v = this
				v.mess = {}
            	v.messType = true
				v.compName = 'newmes'
			},
        	findMessList(){
                this.getPageData()
        	},
        	listDele(id){
                this.util.del(this,this.apis.CLGL.DELE,[id.clId],()=>{
                    this.getPageData()
				})
			},
            pageChange(event){
                this.param.pageNum = event
                this.getPageData()
        	},
        }
    }
</script>
