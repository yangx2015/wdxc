<style lang="less">
	@import '../../../../../../styles/common.less';
	.stepsList .ivu-steps.ivu-steps-small .ivu-steps-content{
		padding-left: 0!important;
    	width: 20px!important;
   	}
</style>
<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='900'
		    :closable='false'   
		    :mask-closable="false"
		    title="新增线路">
		    <div>
		    	<Row :gutter='30' style="margin-bottom: 15px;">
		    		<Col span="6">
		    			<Input v-model="addspot.routertName" placeholder="请输入线路名称...">
		    			</Input>
		    		</Col>
		    		<Col span="6">
		    			<Select v-model="addspot.classes"  placeholder="请输选着线路班次...">
					        <Option value="早班">早班</Option>
					        <Option value="午班">午班</Option>
					        <Option value="下午班">下午班</Option>
					        <Option value="晚班">晚班</Option>
					    </Select>
		    		</Col>
		    		<Col span="5">
		    			<Select v-model="addspot.spotType">
					        <Option value="正常">正常</Option>
					        <Option value="停用">停用</Option>
					    </Select>
		    		</Col>
		    		<Col span="5">
	    				<Input v-model="addspot.spotmess" placeholder="备注信息...">
		    			</Input>
		    		</COl>
		    	</Row>
		    </div>
		    <div class="box-row">
		    	<div class="body-F stepsList">
		    		<Steps :current="routerList.length" size="small">
				        <Step icon="disc" :content="item.name" v-for="(item,index) in routerList"></Step>
				    </Steps>
		    	</div>
		    	<div style="width: 100px;">
		    		<div>
		    			<Select v-model="spotName">
					        <Option value="光谷广场">光谷广场</Option>
					        <Option value="珞喻路">珞喻路</Option>
					        <Option value="武汉大学">武汉大学</Option>
					        <Option value="珞珈山">珞珈山</Option>
					    </Select>
		    		</div>
		    		<div style="margin-top: 8px;">
		    			<Button type="primary" shape="circle" icon="plus" 
		    				:disabled="spotName==''"
		    				@click='addspotlist'></Button>
		    			<Button type="primary" shape="circle" icon="minus"
		    				:disabled="routerList.length==0" style="float: right;"
		    				@click='removespot'></Button>
		    		</div>
		    		</Row>
		    	</div>
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
				addspot:{
					routertName:'',
					classes:'',
					spotType:'正常',
					spotmess:''
				},
				spotName:'',
				routerList:[
					{
						name:'光谷广场'
					},{
						name:'上钱村'
					},{
						name:'0075'
					}
				]
				
			}
		},
		methods:{
			addspotlist(){
				this.routerList.push({'name':this.spotName})
				this.spotName = ''
			},
			removespot(){
				this.routerList.pop()
			},
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