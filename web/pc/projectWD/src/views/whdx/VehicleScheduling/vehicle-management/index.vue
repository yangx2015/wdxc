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
				<car-item v-for="(item,index) in tableData" :data="item"  :key="index" style="margin-top: 16px;"></car-item>
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
	import carItem from './comp/carItem'
	export default {
	  name:'char',
      components: {
        expandRow,newmes,allmes,bkShow,carItem
      },
    	mixins:[mixins],
        data () {
            return {
            	mess:{},
            	derMes:{
            		sjId:'',
            		sjxm:''
            	},
				cell:{
                    bindDrvFlag:false
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
                drivers:[],
                deviceList:[],
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
            getDeviceList(){//获取终端编号
                let v = this;
                v.$http.post(this.apis.ZDGL.SXQUERY).then((res) =>{
                    if(res.code===200){
                        if(res.result==undefined){
                            res.result = []
                        }
                        log('终端数据',res)
                        this.deviceList = res.result;
                        this.deviceList.push({'zdbh':v.mess.zdbh})
                    }
                })
            },
            getDrivers(){
                let v = this;
                v.$http.get(this.apis.JSY.QUERY,{params:{pageSize:1000}}).then((res) =>{
                    if(res.code===200){
                        v.drivers = res.page.list;
                        // if(v.derMess.sjId!=null&&!v.messType){
                        // 	v.drivers.push({'xm':v.derMess.sjxm,'sfzhm':v.derMess.sjId})
                        // }
                    }
                })
            },
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
