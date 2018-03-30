<template>
	<div>
		<Modal
		    v-model="showModal"
		    :closable='false'      
		    width='800'
		    :mask-closable="false"
		    title="报销记账">
		    <div>
		    	<Row :gutter= '15'>
		    		<Col span="6">
		    			<Input v-model="addlist.name" placeholder="报销人...">
		    			</Input>
		    		</Col>
		    		<Col span="6">
		    			<Input v-model="addlist.mess" placeholder="报销事项...">
		    			</Input>
		    		</Col>
		    		<Col span="6">
		    			<Input v-model="addlist.money" placeholder="报销金额...">
		    			</Input>
		    		</Col>
		    		<Col span="6">
		    			<Input v-model="addlist.money" placeholder="发票数量...">
		    			</Input>
		    		</Col>
		    	</Row>
		    </div>
		    <div slot='footer'>
		    	<Button type="ghost" @click="colse">取消</Button>
	        	<Button type="primary" @click="colse">确定</Button>
		    </div>
		</Modal>
	</div>
</template>

<script>
	export default{
		name:'',
		data(){
			return{
				showModal:true,
				addlist:[
					{
						name:'',
						mess:'',
						money:''
					}
				]
			}
		},
		methods:{
			colse(){
				var v = this
				v.$parent.compName = ''
				console.log(v.$parent)
		    }
		}
	}
</script>

<style>
</style>