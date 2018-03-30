<style lang="less">
	@import '../../../../../styles/common.less';
	.showmapline{
		height: 260px;
		.showmap{
			height:100%;
		}
		.showline{
			height:100%;
		}
	}
	.stepsList {
		padding-top:25px ;
		.ivu-steps.ivu-steps-small .ivu-steps-content{
			padding-left: 0!important;
	    	width: 20px!important;
	    	font-size: 18px;
	   	}
   		.ivu-steps.ivu-steps-small .ivu-steps-head-inner>.ivu-steps-icon.ivu-icon{
   			font-size: 24px!important;
   		}
		
	}
</style>
<template>
    <div>
    	<div>
    		<Button type="success" @click="changeS=true">地图查看</Button>    		
			<Button type="info" @click="changeS=false">线路查看</Button>
    	</div>
    	<div class="showmapline">
    		<div class="showmap" v-if="changeS">
    			<map-show-line></map-show-line>
    		</div>
    		<div class="showline stepsList" v-else>
    			<Steps :current="routerList.length" size="small">
			        <Step icon="disc" :content="item.name" v-for="(item,index) in routerList"></Step>
			    </Steps>
    		</div>
    	</div>
    </div>
</template>
<script>
	import mapShowLine from '../../../map/mapShowLine.vue'
    export default {
    	name:'',
    	components: {
	    	mapShowLine,
	    },
    	data(){
    		return{
    			changeS:true,
    			routerList:[
					{
						name:'光谷广场'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					},{
						name:'上钱村'
					},{
						name:'九九九'
					}
				]
    		}
    	},
        props: {
            row: Object
        }
    };
</script>
