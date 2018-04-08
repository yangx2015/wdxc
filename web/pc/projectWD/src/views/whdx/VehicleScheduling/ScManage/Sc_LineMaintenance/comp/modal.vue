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
		    			<Input v-model="form.xlmc" placeholder="请输入线路名称...">
		    			</Input>
		    		</Col>
		    		<Col span="5">
		    			<Select v-model="form.zt">
					        <Option value="00">正常</Option>
					        <Option value="10">停用</Option>
					    </Select>
		    		</Col>
		    		<Col span="5">
		    			<Select v-model="form.yxfs">
					        <Option value="10">上行</Option>
					        <Option value="20">下行</Option>
					    </Select>
		    		</Col>
		    		<Col span="5">
	    				<Input v-model="form.bz" placeholder="备注信息...">
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
		    				@click='addStation'></Button>
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
				form:{
				    id:'',
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
		    if (this.$parent.currentRow){
				this.form = this.$parent.currentRow;
			}
		  this.getAllStation();
		},
		methods:{
		    getStations(){
                this.$http.get(configApi.ZD.GET_BY_ROUTE_ID+'?xlId='+this.form.id).then((res) =>{
                    if(res.code === 200){
                        for (let r of res.result){
                            this.addByStationId(r.id);
						}
                    }
                })
			},
			addByStationId(stationId){
                this.choosedStations.push({id:stationId,name:this.getStationNameById(stationId)});
			},
		    getAllStation(){
                this.$http.get(configApi.ZD.GET_ALL).then((res) =>{
                    if(res.code===200){
                        this.stationList = res.result;
                        if (this.$parent.currentRow){
                            this.getStations();
                        }
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
                this.form.zdIds = zdIds;
                let url = configApi.XL.ADD;
                if (this.$parent.currentRow){
                    url = configApi.XL.CHANGE;
                }
                this.$http.post(url,this.form).then((res) =>{
                    if(res.code===200){
                        var v = this
                        v.$parent.compName = ''
                        v.$parent.getmess()
                        this.$Message.success(res.message);
                    }
                })
			},
			addStation(){
		        this.choosedStations.push({id:this.stationId,name:this.getStationNameById(this.stationId)});
		        this.stationId = '';
			},
			removespot(){
				this.choosedStations.pop()
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