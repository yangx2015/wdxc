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
		    title="新增校巴线路">
		    <div>
		    	<Row :gutter='30' style="margin-bottom: 15px;">
		    		<Col span="6">
		    			<Input v-model="addspot.xlmc" placeholder="请输入线路名称...">
		    			</Input>
		    		</Col>
		    		<Col span="5">
		    			<Select v-model="addspot.zt">
					        <Option value="00">正常</Option>
					        <Option value="10">停用</Option>
					    </Select>
		    		</Col>
		    		<Col span="5">
		    			<Select v-model="addspot.yxfs">
					        <Option value="10">上行</Option>
					        <Option value="20">下行</Option>
					    </Select>
		    		</Col>
		    		<Col span="5">
	    				<Input v-model="addspot.bz" placeholder="备注信息...">
		    			</Input>
		    		</COl>
		    	</Row>
		    </div>
		    <div class="box-row">
		    	<div class="body-F stepsList">
		    		<Steps :current="choosedStations.length" size="small">
				        <Step icon="disc" :content="item.name" v-for="(item,index) in choosedStations"></Step>
				    </Steps>
		    	</div>
		    	<div style="width: 100px;">
		    		<div>
		    			<Select v-model="stationId">
					        <Option v-for="r in stationList" :value="r.id">{{r.mc}}</Option>
					    </Select>
		    		</div>
		    		<div style="margin-top: 8px;">
		    			<Button type="primary" shape="circle" icon="plus" 
		    				:disabled="stationId==''"
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
	        	<Button type="primary" @click="save">确定</Button>
		    </div>
		</Modal>
	</div>
</template>

<script>
    import configApi from '@/axios/config.js'
	export default{
		name:'',
		data(){
			return{
				showModal:true,
                stationId:'',
				choosedStations:[],
				addspot:{
                    xlmc:'',
                    zt:'00',
                    yxfs:'10',
				},
				stationList:[],
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
		mounted(){
		  this.getAllStation();
		},
		methods:{
		    getAllStation(){
                this.$http.get(configApi.ZD.GET_ALL).then((res) =>{
                    if(res.code===200){
                        this.stationList = res.result;
                    }
                })
			},
			getStationById(id){
				for (let r of this.stationList){
				    if (r.id === id)return r;
				}
				return null;
            },
			getStationNameById(id){
		        let station = this.getStationById(id);
		        if (station == null)return '';
		        return station.mc;
			},
			save(){
                let zdIds = '';
                for(let r of this.choosedStations){
                    zdIds += r.id+",";
				}
                this.addspot.zdIds = zdIds;
                this.$http.post(configApi.XL.ADD,this.addspot).then((res) =>{
                    if(res.code===200){
                        this.$Message.success(res.message);
                    }
                })
			},
			addspotlist(){
		        this.choosedStations.push({id:this.stationId,name:this.getStationNameById(this.stationId)});
		        this.stationId = '';
			},
			removespot(){
				this.choosedStationIds.pop()
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