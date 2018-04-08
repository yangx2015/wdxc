<style lang="less">
@import './draggable-list.less';
li{
	list-style: none;
}
</style>
<template>
	<div class="box">
		<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
			<Spin fix style="background-color:#fff">
	            <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
	            <div style="font-size: 30px;">排版</div>
	        </Spin>
		</div>
		<div class="tit">
			<Row class="margin-top-10">
				<Col span="24" style="text-align:center;">
				<h1>
					{{todaytime}}
					排班</h1>
				<Button style="float: right;margin-left:8px" type="success" @click="okmodel()">完成</Button>
				</Col>
			</Row>
		</div>
		<div class="body">
			<div class="box-row">
				<div style="width: 260px;margin-right:5px;height: 100%;font-size: 16px;border-right: solid 1px #008855;">
					<Row style="text-align: center;border-bottom: solid 3px #9B9B9B;">
						<Col span="8">姓名</Col>
						<Col span="8">车辆</Col>
						<Col span="8">状态</Col>
					</Row>
					<Row style="text-align: center;font-size: 14px;">
						<ul id="todoList" class="iview-admin-draggable-list" style="min-height: 300px;">
                            <li v-for="(item,index) in carList" :key="index" :data-index="index">
		                        <div style="border: solid 1px #9B9B9B;padding: 3px 0;overflow: hidden;">
		                        	<div style="width: 33.33%;float: left;">{{item.sjxm}}</div>
		                        	<div style="width: 33.33%;float: left;">{{item.cph}}</div>
		                        	<div style="width: 33.33%;float: left;">{{item.zt | changeZT}}</div>
								</div>
                            </li>
                        </ul>
					</Row>
				</div>
				<div class="body-F" style="background-color: #00A854;padding: 5px;">
					<Row :gutter='8'>
						<Col span="8" class="margin-bottom-10" v-for="(item,index) in listdata">
							<Card style="width:100%">
								<p slot="title">
									<Icon type="ios-film-outline"></Icon>
									{{item.xlmc}}
								</p>
								<span slot="extra">
										            <Icon type="ios-loop-strong"></Icon>
										            时间
										        </span>
								<div style="height: 140px;text-align: center;overflow: auto;" :id="index+'_card'">
									<Row style="border: solid 1px #9B9B9B;padding: 3px 0;" v-for="(items,indexs) in item.clList">
										<Col span="8">周师傅</Col>
										<Col span="8">鄂A12345</Col>
										<Col span="8">静态</Col>
									</Row>
								</div>
							</Card>
						</Col>
					</Row>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import Sortable from 'sortablejs';
	
	import configApi from '@/axios/config.js'
	export default {
		name:'',
		data(){
			return{
				listdata:[],
				carList:[],
				SpinShow:false
			}
		},
		props: {
			todaytime: {
				type: String,
				default: '2018-03-26'
			}
		},
		filters: {
			changeZT:(value)=>{
				switch(value){
					case '00' :
						return '正常'
					case '01' :
						return '停用'	
				}
			}
		},
		created(){
			this.getmess()
		},
		mounted (){
			let vm = this;
	        let todoList = document.getElementById('todoList');
	        Sortable.create(todoList, {
	            group: {
	                name: 'list',
	                pull: 'clone'
	            },
	            animation: 120,
	            onRemove (event) {
//	            	console.log(event.item.getAttribute('data-index'))
//					console.log(vm.carList[event.item.getAttribute('data-index')].clId)
//					console.log(event)

	            	//车辆id
	            	let clid = vm.carList[event.item.getAttribute('data-index')].clId
	            	//线路id
	            	let linelength = event.to.id
	            	let lineID = linelength.substring(0,linelength.length-5)
	            	let xlid = vm.listdata[lineID].id
	            	vm.AddList(clid,xlid)
	            	vm.SpinShow = true
	            }
	        });
		},
		methods:{
			
			getmess(){
				var v = this
				//车辆数据
				this.$http.get(configApi.CLGL.QUERY).then((res) =>{
					v.carList = res.page.list
					console.log('车辆数据',res.page.list)
				}).catch((err) =>{
					console.log('bug')
				})
				//线路数据
				this.$http.post(configApi.XLPBXX.QUERY,{"clcx":"30","lulx":"10","date2":'2018-03-26'}).then((res) =>{
					console.log('排班数据2',res)
					v.listdata = res.result
				}).then((res) =>{
					v.fordata()
				}).catch((err) =>{
					console.log('bug')
				})
			},
			AddList(carID,LineID){
				var v = this
				this.$http.post(configApi.XLPBXX.ADD,{"clid":carID,"xlid":LineID,"date2":'2018-03-26'}).then((res) =>{
					console.log('排版新增',res)
//					if(res.code==500){
						v.$parent.domeC()
//					}
				})
			},
			okmodel(){
				this.$emit('okdrag')
			},
			fordata(){
				let vm = this;
				for(var i=0;i<vm.listdata.length;i++){
					Sortable.create((document.getElementById(i+'_card')), {
			            group: {
			                name: 'list',
			                pull: true
			            },
			            animation: 120,
			            onRemove (event) {
			            	console.log(event.item.getAttribute('data-index'))
			            	console.log(event)
			           }
			        });
				}
			}
		}
	}
</script>

<style>

</style>