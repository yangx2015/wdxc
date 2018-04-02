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
    			<Steps :current="stationList.length" size="small">
			        <Step icon="disc" :content="item.mc" v-for="(item,index) in stationList"></Step>
			    </Steps>
    		</div>
    	</div>
    </div>
</template>
<script>
	import mapShowLine from '../../../map/mapShowLine.vue'
    import configApi from '@/axios/config.js'
    export default {
    	name:'',
    	components: {
	    	mapShowLine,
	    },
    	data(){
    		return{
    			changeS:true,
				xlId:'',
    			stationList:[]
    		}
    	},
        props: {
            row: Object
        },
		mounted(){
            this.xlId = this.row.id;
            console.log('p.xlid:'+this.xlId);
            this.getStations();
		},
        methods:{
    	    getStations(){
                this.$http.get(configApi.ZD.GET_BY_ROUTE_ID,{params:{xlId:this.xlId}}).then((res) =>{
                    if(res.code===200){
                        this.stationList = res.result;
                    }
                })
			}
		}
    };
</script>
