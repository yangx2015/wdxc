<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='900'
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
						<Col span="8">
							<FormItem prop="cph" label='车牌号：'>
								<Input type="text" v-model="addmess.cph" :readonly="!messType" placeholder="请设置车牌号"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label='车型：'>
								<Select filterable  v-model="addmess.cx">
									<Option v-for="cx in cxDict" :value="cx.key">{{cx.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label='初登日期：'>
								<DatePicker type="date" v-model="addmess.ccdjrq"
											placement="left-start"
											style="width: 100%"
											placeholder="请选着初次登记日期"></DatePicker>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop="zkl" label='载客量：'>
								<Select v-model="addmess.zkl" placeholder="请设置载客量">
									<Option v-for="(item ,index) in zws" :value="item">{{item}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="sjxm" label='司机：'>
								<Select filterable clearable  v-model="addmess.sjId">
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
						<!--<Col span="12">-->
							<!--<FormItem prop="cdbh" label='车队：'>-->
								<!--<Select filterable clearable  v-model="addmess.cdbh">-->
									<!--<Option v-for="e in fleetList" :value="e.cdbh" :key="e.cdbh">{{e.cdmc}}</Option>-->
								<!--</Select>-->
							<!--</FormItem>-->
						<!--</Col>-->
						<Col span="12">
							<FormItem prop="zdbh" label='终端编号：'>
								<Select placement='top' filterable clearable  v-model="addmess.zdbh">
									<Option v-for="e in deviceList" :value="e.zdbh" :key="e.zdbh">{{e.zdbh}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zdbh" label='obdCode：'>
								<Input type="text" v-model="addmess.obdCode" placeholder="请输入obdCode"></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='生产商：'>
								<Input type="text" v-model="addmess.scs" placeholder="请填写生产商名称"></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='车辆型号：'>
								<Input type="text" v-model="addmess.xh" placeholder="请填写车辆型号"></Input>
							</FormItem>
						</Col>

						<Col span="12">
							<FormItem label='保险公司名称：'>
								<Input type="text" v-model="addmess.bxgsmc" placeholder="请填写保险公司名称"></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='终保日期：'>
								<DatePicker type="date" v-model="addmess.bxsj"
											:options="disableDate"
											placement="top-start"
											style="width: 100%"
											placeholder="请选择终保日期"></DatePicker>
							</FormItem>
						</Col>
					</Row>
	    		</div>
    		</Form>
		    <div slot='footer'>
		    	<Button type="default" @click="colse"style="color: #949494">取消</Button>
	        	<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>
		    </div>
		</Modal>
	</div>
</template>

<script>


	export default {
		name:'',
		data(){
			return {
				showModal:true,
				operate:'新增',
				editMode :false,
                disableDate: {
                    disabledDate (date) {
                        return date && date.valueOf() < Date.now();
                    }
                },
				//新增数据
            	addmess: {
                    cph: '鄂',
                    cx:'10',
                    dl:'',
                    sjxm:'',
                    zt:'00',
                    zdbh:'',
                    zkl:5,
                    sjId:'',
                    obdCode:'',
                    bxsj:'',
                    bxgsmc:'',
                    ccdjrq:'',
                    xh:'',
                    scs:'',
                },
                ruleInline: {
                  cph: [
                      { required: true, message: '请输入车牌号', trigger: 'blur' }
                  ],
                  // zkl: [
                  //     { required: true, message: '请输入载客量', trigger: 'blur' }
                  // ],
              	},
				deviceList:[],//终端设备
				drivers:[],//驾驶员
				fleetList:[],
                clztDict:[],//车辆状态
                clztDictCode:'ZDCLK0016',
                cxDict:[],//车量型号
                cxDictCode:'ZDCLK0019',
				zws:[5,7,11,20,32,45,48],
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
                if(this.mess.zdbh == null){
                    this.mess.zdbh = ''
				}
                this.addmess = JSON.parse(JSON.stringify(this.mess))
            }
            this.getDrivers();
			this.getDict();
			this.getFleetList();
			this.getDeviceList();
		},
		methods:{
		    getDeviceList(){//获取终端编号
                let v = this;
                v.$http.post(this.apis.ZDGL.SXQUERY).then((res) =>{
                    if(res.code===200){
                    	if(res.result==undefined){
                    		res.result = []
                    	}
                    	log('终端数据',res)
                        this.deviceList = res.result;
                        if(!this.messType){
                        	this.deviceList.push({'zdbh':v.mess.zdbh})
                        }
                    }
                })
			},
		    getFleetList(){
                let v = this;
                v.$http.get(this.apis.CD.QUERY,{params:{pageSize:10000}}).then((res) =>{
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
                v.$http.get(this.apis.JSY.QUERY,{params:{pageSize:1000}}).then((res) =>{
                    if(res.code===200){
                        v.drivers = res.page.list;
                        // if(v.derMess.sjId!=null&&!v.messType){
		                // 	v.drivers.push({'xm':v.derMess.sjxm,'sfzhm':v.derMess.sjId})
		                // }
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
                    		v.$http.post(this.apis.CLGL.ADD,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('车辆添加成功');
                                    v.$parent.getPageData()
                                    v.$parent.compName = ''
								}else{
									v.$Message.error(res.message);
								}
							})
                    	}else{
                            console.log(1);
                    	    delete v.addmess.clDzwlCl;
                    	    delete v.addmess.clDzwl;
                            console.log(2);
                    		v.$http.post(this.apis.CLGL.CHANGE,v.addmess).then((res) =>{
                    		    console.log(res.code);
								if(res.code===200){
									v.$Message.success('车辆修改成功');
                                    v.$parent.getPageData()
                                    v.$parent.compName = ''
								}else{
									v.$Message.error(res.message);
								}
							})
                    	}
                    } else {
                        v.$Message.error('请认真填写信息!');
                    }
                })
            },
		}
	}
</script>

<style>
</style>
