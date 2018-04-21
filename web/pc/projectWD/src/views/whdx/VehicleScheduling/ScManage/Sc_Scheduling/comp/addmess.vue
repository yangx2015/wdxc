<style type="text/css">
	.carListsty{
		padding: 3px;
		margin:8px;
		border: solid #657180 1px;
		height: 30px;
		position: relative;
	}
</style>
<template>
	<div>
		<Modal
		    v-model="showModal"
			height="400"
		    :closable='false'
		    :mask-closable="false"
		    :title="mess.xlmc+'_线路排班__'+pbTime">
		    <div>
		    	<div style="height: 200px;border: solid 1px #000;">
		    		<Row>
		    			<!--<Col span="4">
		    				<div class="carListsty" 
		    					@mouseenter="Rbut('1')"
		    					@mouseleave="cscs('2')">
		    					cpamkl
		    				</div>	    		
		    			</Col>-->
		    			<Col span="4" v-if="mess.clList.length>0" v-for = '(item,index) in mess.clList'>
		    				<div class="carListsty" 
		    					@mouseenter="item.ico = true"
		    					@mouseleave="item.ico = false">
		    					{{item.cph}}{{item.ico}}
		    					<span v-show="item.ico" style="position:absolute;top: -6px;right: -6px;z-index: 100;">
		    						 <Button type="primary" shape="circle" 
		    						 	size="small" icon="plus-round"
		    						 	@click="AddList(item.clId,mess.id)"></Button>
		    					</span>
		    				</div>	    		
		    			</Col>
		    		</Row>
		    		<!--<span class="carListsty" v-if="mess.clList.length>0" v-for = '(item,index) in mess.clList'>{{item.cph}}</span>-->
		    	</div>
		    	<div style="height: 200px;border: solid 1px #000;">
		    		<Row>
		    			<Col span="4" v-for = '(item,index) in chrlist'>
		    				<div class="carListsty" 
		    					@mouseenter="item.ico = true"
		    					@mouseleave="item.ico = false">
		    					{{item.cph}}
		    					<span v-show="item.ico" style="position:absolute;top: -6px;right: -6px;z-index: 100;">
		    						 <Button type="primary" shape="circle" 
		    						 	size="small" icon="plus-round"
		    						 	@click="AddList(item.clId,mess.id)"></Button>
		    					</span>
		    				</div>	    		
		    			</Col>
		    		</Row>
		    	</div>
		    </div>
		    <div slot='footer'>
		    	<Button type="ghost" @click="colse">取消</Button>
	        	<!--<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>-->
		    </div>
	    </Modal>
	</div>
</template>

<script>
	import configApi from '@/axios/config.js'
	export default{
		name:'',
		data(){
			return {
				showModal:true,
				chrlist:[]
			}
		},
		props:{
			mess:{
				type:Object,
				default:{}
			},
			pbTime:''
		},
		created(){
			console.log('信息传递',this.mess)
			this.getCarList()
		},
		methods:{
			Rbut(b,i){
				this.mess.clList[i].ico = b
			},
			getCarList(){//获取车辆列表
				var v = this
				this.$http.get(configApi.CLGL.QUERY).then((res) =>{
					if(res.code == 200){
						res.page.list.forEach(function(item,index){
							item.ico = false
						})
						v.chrlist = res.page.list
					}else{
						console.log('bug')
					}
					console.log('车辆数据',res)
				}).catch((err) =>{
					console.log('bug')
				})
			},
			AddList(carID,LineID){
				var v = this
				this.$http.post(configApi.XLPBXX.ADD,{"clId":carID,"xlId":LineID,"date":v.pbTime}).then((res) =>{
					console.log('排版新增',res)
					v.$parent.getmess()
//					if(res.code==500){
//						v.$parent.domeC()
//					}
				})
			},
			deleteById(carID,LineID){
                this.$Message.success('移出成功');
                var v = this
                this.$http.post(configApi.XLPBXX.DELE,{"clId":carID,"xlId":LineID,"date":v.pbTime}).then((res) =>{
                    this.getmess();
                })
			},
			colse(){
				var v = this
				v.$parent.compName = ''
		   	}
		}
	}
</script>

<style>
</style>