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
		    width='600'
		    :title="mess.xlmc+'_线路排班__'+pbTime">
		    <div>
		    	<div style="height: 200px;border: solid 1px #000;">
		    		<Row>
		    			<Col span="4" v-if="mess.clList.length>0" v-for = '(item,index) in mess.clList'>
		    				<div class="carListsty">
		    					{{item.cph}}
		    					<span style="position:absolute;top: -6px;right: -10px;z-index: 100;">
		    						 <Button type="error" shape="circle" 
		    						 	size="small" icon="minus-round"
		    						 	@click="deleteById(item.clId,index)"></Button>
		    					</span>
		    				</div>	    		
		    			</Col>
		    		</Row>
		    	</div>
		    	<div style="height: 200px;border: solid 1px #000;">
		    		<Row>
		    			<Col v-show="chrlist" span="4" v-for = '(item,index) in chrlist'>
		    				<div class="carListsty" 
		    					@mouseenter="item.ico = true"
		    					@mouseleave="item.ico = false">
		    					{{item.cph}}
		    					<span style="position:absolute;top: -6px;right: -6px;z-index: 100;">
		    						 <Button v-if="!(item.clId==='000000')" type="primary" shape="circle" 
		    						 	size="small" icon="plus-round"
		    						 	@click="AddList(item.clId,item.cph)"></Button>
		    					</span>
		    				</div>	    		
		    			</Col>
		    		</Row>
		    	</div>
		    </div>
		    <div slot='footer'>
		    	<Button type="primary" @click="colse">完成</Button>
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
			if(this.mess.clList==null){
				this.mess.clList = []
			}
			console.log('信息传递',this.mess)
			this.getCarList()
		},
		methods:{
			getCarList(){//获取车辆列表
				var v = this
				this.$http.post(configApi.XLPBXX.CARLIST,{'xlId':v.mess.id,'date':v.pbTime,'cx':'30'}).then((res) =>{
					if(res.code == 200){
						console.log('车辆据',res)
//						res.result.forEach(function(item,index){
//							item.ico = false
//						})
						if( res.result){
							v.chrlist = res.result
						}else{
							v.chrlist = [{'cph':'无车辆数据','clId':'000000'}]
						}
					}else{
						console.log('bug')
					}
					console.log('车辆数据',res)
				}).catch((err) =>{
					console.log('bug')
				})
			},
			AddList(carID,cph){
				var v = this
				this.$http.post(configApi.XLPBXX.ADD,{"clId":carID,"xlId":v.mess.id,"date":v.pbTime,'cx':'30'}).then((res) =>{
					console.log('排版新增',res)
					if(res.code==200){
						v.$Message.success(res.message);
						v.$parent.getmess()
						v.mess.clList.push({'cph':cph,'clId':carID})
						v.getCarList()
					}else{
						v.$Message.error(res.message);
					}
					v.getCarList()
				}).catch((error)=>{
					v.$Message.error('出错了！！！');
				})
			},
			deleteById(carID,index){
                var v = this
                this.$http.post(configApi.XLPBXX.DELE,{"clId":carID,"xlId":v.mess.id,"date":v.pbTime,'cx':'30'}).then((res) =>{
                    if(res.code==200){
                    	v.$Message.success(res.message);
                    	v.$parent.getmess();
                    	v.getCarList();
                    	v.mess.clList.splice(index,1)
                    }else{
						v.$Message.error(res.message);
					}
                }).catch((error)=>{
					v.$Message.error('出错了！！！');
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