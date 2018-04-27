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
		    :title="tit+'校巴线路'">
		    <div>
		    	<Form
    			ref="addmess"
    			:model="form"
    			:rules="ruleInline"
    			:label-width="0"
    			:styles="{top: '20px'}">
			    	<Row :gutter='30' style="margin-bottom: 15px;">
			    		<Col span="6">
			    			<FormItem prop="xlmc">
				    			<Input v-model="form.xlmc" placeholder="请输入线路名称...">
				    			</Input>
			    			</FormItem>
			    		</Col>
			    		<Col span="3">
			    			<FormItem prop="zt">
				    			<Select filterable clearable  v-model="form.zt">
							        <Option value="00">正常</Option>
							        <Option value="10">停用</Option>
							    </Select>
							</FormItem>
			    		</Col>
			    		<Col span="3">
			    			<FormItem prop="yxfs">
				    			<Select filterable clearable  v-model="form.yxfs">
							        <Option value="10">上行</Option>
							        <Option value="20">下行</Option>
							    </Select>
							</FormItem>
			    		</Col>
						<Col span="5">
							<FormItem>
								<Input type="text" v-model="form.yxkssj" placeholder="开始时间..."></Input>
							</FormItem>
						</COl>
						<Col span="5">
							<FormItem>
								<Input type="text" v-model="form.yxjssj" placeholder="结束时间..."></Input>
							</FormItem>
						</COl>
					</Row>
				</Form>
		    </div>
		    <div class="box-row">
		    	<div class="body-F stepsList">
		    		<Steps :current="choosedStations.length" size="small">
				        <Step icon="disc" :content="item.name" v-for="(item,index) in choosedStations"></Step>
				    </Steps>
		    	</div>
		    	<div style="width: 100px;">
		    		<div>
		    			<Select filterable clearable  v-model="stationId">
					        <Option v-for="(item,index) in stationList" 
					        	:disabled='item.disabled'
					        	:value="index+1">{{item.mc}}</Option>
					    </Select>
		    		</div>
		    		<div style="margin-top: 8px;">
		    			<Button type="primary" shape="circle" icon="plus"
		    				:disabled="stationId==''"
		    				@click='addStation(stationId-1)'></Button>
		    			<Button type="primary" shape="circle" icon="minus"
		    				:disabled="routerList.length==0" style="float: right;"
		    				@click='removespot(stationId-1)'></Button>
		    		</div>
		    	</div>
			</div>
		    <div slot='footer'>
		    	<Button type="ghost" @click="colse">取消</Button>
	        	<Button type="primary" @click="save('addmess')">确定</Button>
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
				tit:'新增',
                stationId:0,
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
				],
				ruleInline: {
                  xlmc: [
                      { required: true, message: '请输入线路名称', trigger: 'blur' }
                  ],
                  zt: [
                      { required: true, message: '请选择线路状态', trigger: 'blur' }
                  ],
                  yxfs:[
                      { required: true,message: '请选择方向', trigger: 'blur' }
                  ]
              	},

			}
		},
		mounted(){
			if(this.$parent.addmessType){
				this.tit = '新增'
			}else{
				this.tit = '编辑'
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
				var v = this
                this.choosedStations.push({id:stationId,name:this.getStationNameById(stationId)});
                for(var i = 0 ; i<this.stationList.length ; i++){
                	if(v.stationList[i].id == stationId){
                		v.stationList[i].disabled = true
                		return
                	}
                }
			},
		    getAllStation(){
                this.$http.get(configApi.ZD.GET_ALL).then((res) =>{
                    if(res.code===200){
                        this.stationList = res.result;
                        console.log('站点列表',res)
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
			save(name){
                var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
		                let zdIds = '';
		                for(let r of this.choosedStations){
		                    zdIds += r.id+",";
						}
		                this.form.zdIds = zdIds;
                        delete this.form.startStation
                        delete this.form.endStation
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
		            } else {
                        v.$Message.error('请认真填写用户信息!');
                    }
                })
			},
			addStation(index){
//				debugger
                this.choosedStations.push({
                	id:this.stationList[index].id,
                	name:this.stationList[index].mc,
                	'index':index
                });//向线路插入数据
                this.stationId = 0;
				this.stationList[index].disabled = true
                console.log(this.stationList)
			},
			removespot(){
				var chLength = this.choosedStations.length-1
				var chindex = this.choosedStations[chLength].index
				this.stationList[chindex].disabled = false
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
