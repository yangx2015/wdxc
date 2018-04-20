<style type="text/css">
	.carListsty{
		padding: 3px;
		margin: 0 4px;
		border: solid #657180 1px;
	}
</style>
<template>
	<div>
		<Modal
		    v-model="showModal"
			height="400"
		    :closable='false'
		    :mask-closable="false"
		    :title="mess.xlmc+'_线路排班'">
		    <div>
		    	<div style="height: 200px;">
		    		<span class="carListsty" v-if="mess.clList.length>0" v-for = '(item,index) in mess.clList'>{{item.cph}}</span>
		    	</div>
		    	<div style="height: 200px;">
					<span class="carListsty" v-for = '(item,index) in chrlist'>{{item.cph}}</span>	    		
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
			}
		},
		created(){
			this.getCarList()
		},
		methods:{
			getCarList(){
				var v = this
				this.$http.get(configApi.CLGL.QUERY).then((res) =>{
					if(res.code == 200){
						v.chrlist = res.page.list
					}else{
						console.log('bug')
					}
					console.log('车辆数据',res)
				}).catch((err) =>{
					console.log('bug')
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