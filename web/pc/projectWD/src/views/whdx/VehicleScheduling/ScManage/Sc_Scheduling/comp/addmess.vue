<template>
	<div>
		<Modal
		    v-model="showModal"
			height="400"
		    :closable='false'
		    :mask-closable="false"
		    :title="'新增'">
		    <div>
		    	
		    	hello
		    </div>
		    <div slot='footer'>
		    	<Button type="ghost" @click="colse">取消</Button>
	        	<!--<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>-->
		    </div>
	    </Modal>
	</div>
</template>

<script>
	export default{
		name:'',
		data(){
			return {
				showModal:true
			}
		},
		methods:{
			colse(){
				var v = this
				v.$parent.compName = ''
		   	}
		}
	}
</script>

<style>
</style>