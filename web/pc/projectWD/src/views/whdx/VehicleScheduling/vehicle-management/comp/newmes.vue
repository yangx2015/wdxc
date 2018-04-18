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
	    		<div style="overflow: auto;height: 300px;">
					<Row>
						<Col span="12">
							<FormItem prop="cph" label='车牌号：'>
								<Input type="text" v-model="addmess.cph" placeholder="请设置车牌号"></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='车型：'>
								<Select v-model="addmess.cx">
									<Option v-for="cx in cxDict" :value="cx.key">{{cx.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="cph" label='载客量：'>
								<Input type="text" v-model="addmess.zkl" placeholder="请设置载客量"></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="sjxm" label='司机：'>
								<Select v-model="addmess.sjId">
									<Option :value="addmess.sjId" :key="addmess.sjId">{{addmess.sjxm}}</Option>
									<Option v-for="(item) in drivers" :value="item.sfzhm" :key="item.sfzhm">{{item.xm}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zt" label='车辆状态：'>
								<Select v-model="addmess.zt">
									<Option v-for="zt in clztDict" :value="zt.key">{{zt.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zdbh" label='终端编号：'>
								<Input type="text" v-model="addmess.zdbh" placeholder="请输入终端编号"></Input>
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
                  dl: [
                      { required: true,message: '请设置密码', trigger: 'blur' }
                  ],
                  zt:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ],
                  zdbh:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ]
              	},
				drivers:[],
                clztDict:[],
                clztDictCode:'ZDCLK0016',
                cxDict:[],
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
			}
		},
		created(){
            if(!this.messType){
                this.operate = '编辑';
            }
			this.addmess = this.mess
            console.log(this.mess);
            this.getDrivers();
			this.getDict();
		},
		methods:{
            getDict(){
                this.clztDict = this.dictUtil.getByCode(this,this.clztDictCode);
                this.cxDict = this.dictUtil.getByCode(this,this.cxDictCode);
            },
		    getDrivers(){
		        let v = this;
                v.$http.get(configApi.JSY.NOT_BIND_LIST).then((res) =>{
                    if(res.code===200){
                        this.drivers = res.result;
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
                        this.$parent.SpinShow = true;
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