<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='800'
		    :closable='false'
		    :mask-closable="false"
		    :title="operate+'车辆'">
    		<Form
    			ref="addmess"
    			:model="addmess"
    			:rules="ruleInline"
    			:label-width="100"
    			:styles="{top: '20px'}">
	    		<div style="overflow: auto;">
					<Row>
						<Col span="12">
							<FormItem prop="cph" label='车牌号：'>
								<Input type="text" v-model="addmess.cph" placeholder="请设置车牌号"></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='车型：'>
								<Select filterable clearable  v-model="addmess.cx">
									<Option v-for="cx in cxDict" :value="cx.key">{{cx.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zkl" label='载客量：'>
								<!--<Input type="number" v-model="addmess.zkl" placeholder="请设置载客量"></Input>-->
								<input class="input" type="number"  v-model="addmess.zkl"  placeholder="请设置载客量"/>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="sjxm" label='司机：'>
								<Select filterable clearable  v-model="addmess.sjId">
									<!--<Option v-if="editMode" :value="addmess.sjId" :key="addmess.sjId">{{addmess.sjxm}}</Option>-->
									<Option v-for="(item) in drivers" :value="item.sfzhm" :key="item.sfzhm">{{item.xm}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zt" label='车辆状态：'>
								<Select filterable clearable  v-model="addmess.zt">
									<Option v-for="zt in clztDict" :value="zt.key" :key="zt.key">{{zt.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="cdbh" label='车队：'>
								<Select filterable clearable  v-model="addmess.cdbh">
									<Option v-for="e in fleetList" :value="e.cdbh" :key="e.cdbh">{{e.cdmc}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zdbh" label='终端编号：'>
								<Select placement='top' filterable clearable  v-model="addmess.zdbh">
									<Option v-for="e in deviceList" :value="e.zdbh" :key="e.zdbh">{{e.zdbh}}</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
	    		</div>
    		</Form>
		    <div slot='footer'>
		    	<Button type="ghost" @click="colse">取消</Button>
	        	<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>
		    </div>
		</Modal>
	</div>
</template>

<script>
	import configApi from '@/axios/config.js'

	export default {
		name:'',
		data(){
			return {
				showModal:true,
				operate:'新增',
				editMode :false,
				//新增数据
            	addmess: {
                    cph: '',
                    cx:'',
                    dl:'',
                    sjxm:'',
                    zt:'',
                    zdbh:'',
                    sjId:''
                },
                ruleInline: {
                  cph: [
                      { required: true, message: '请输入用户名', trigger: 'blur' }
                  ],
              	},
				deviceList:[],//终端设备
				drivers:[],//驾驶员
				fleetList:[],
                clztDict:[],//车辆状态
                clztDictCode:'ZDCLK0016',
                cxDict:[],//车量型号
                cxDictCode:'ZDCLK0019',
			}
		},
		props:{
			messType:{
				type:Boolean,
				default:true
			},
			mess:{
				type:Object,
				default:{}
			},
			derMess:{
				type:Object,
				default:{}
			}
		},
		created(){
            if(!this.messType){
                this.operate = '编辑';
                this.editMode = true;
                this.addmess = this.mess
                console.log('数据传递',this.derMess)
            }
            this.getDrivers();
			this.getDict();
			this.getFleetList();
			this.getDeviceList();
		},
		methods:{
		    getDeviceList(){
                let v = this;
                v.$http.get(configApi.ZDGL.QUERY,{params:{pageSize:10000}}).then((res) =>{
                    if(res.code===200){
                        this.deviceList = res.page.list;
                    }
                })
			},
		    getFleetList(){
                let v = this;
                v.$http.get(configApi.CD.QUERY,{params:{pageSize:10000}}).then((res) =>{
                    if(res.code===200){
                        this.fleetList = res.page.list;
                    }
                })
			},
		    getDriverName(){
		      for(let r of this.drivers){
		          if (r.sfzhm ===  this.addmess.sjId){
		              this.addmess.sjxm = r.xm;
				  }
			  }
			},
            getDict(){
                this.clztDict = this.dictUtil.getByCode(this,this.clztDictCode);
                this.cxDict = this.dictUtil.getByCode(this,this.cxDictCode);
            },
		    getDrivers(){
		        let v = this;
                v.$http.get(configApi.JSY.NOT_BIND_LIST).then((res) =>{
                    if(res.code===200){
                        this.drivers = res.result;
                        if(v.derMess.sjId!=null&&!v.messType){
		                	v.drivers.push({'xm':v.derMess.sjxm,'sfzhm':v.derMess.sjId})
		                	console.log('驾驶员添加数据',v.drivers)
		                }
                        console.log('驾驶员',this.drivers)
                    }
                })
			},
			colse(){
				var v = this
				v.$parent.compName = ''
		   	},
		   //确认添加新用户进行前台表单数据验证
            AddDataListOk(name){
            	var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.getDriverName();
//                    	新增
                    	if(v.messType){
                    		v.$http.post(configApi.CLGL.ADD,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('车辆添加成功');
								}else{
									v.$Message.error('车辆添加创建失败');
								}
								v.$parent.getmess()
                    			v.$parent.compName = ''
							})
                    	}else{
                    	    delete v.addmess.clDzwlCl;
                    	    delete v.addmess.clDzwl;
                    		v.$http.post(configApi.CLGL.CHANGE,v.addmess).then((res) =>{
								if(res.code===200){
									v.$Message.success('车辆修改成功');
								}else{
									v.$Message.error('车辆修改失败');
								}
								v.$parent.getmess()
                    			v.$parent.compName = ''
							})
                    	}
                    } else {
                        v.$Message.error('请认真填写信息!');
                    }
                })
            },
		}
	}
//15271928827
</script>

<style>
</style>
