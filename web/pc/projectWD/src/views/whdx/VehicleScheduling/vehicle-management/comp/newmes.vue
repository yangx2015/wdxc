<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='800'
		    :closable='false'
		    :mask-closable="false"
		    title="新增车辆">
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
								<Input type="text" v-model="addmess.cph" placeholder="请设置车牌号">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='车型：'>
								<Select v-model="addmess.cx">
									<Option value="11">大车</Option>
									<Option value="22">小车</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop="sjxm" label='司机：'>
								<Select v-model="addmess.sjxm">
									<Option value="张三">张三</Option>
									<Option value="李四">李四</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zt" label='车辆状态：'>
								<Select v-model="addmess.zt">
									<Option value="00">正常</Option>
									<Option value="10">停用</Option>
								</Select>
								</Input>
							</FormItem>
						</Col>
					</Row>

					<Row>
						<Col span="24">
							<FormItem prop="zdbh" label='终端编号：'>
								<Input type="text" v-model="addmess.zdbh" placeholder="请输入终端编号">
								</Input>
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
				//新增数据
            	addmess: {
                    cph: '',
                    cx:'',
                    dl:'',
                    sjxm:'',
                    zt:'',
                    zdbh:''
                },
                ruleInline: {
                  cph: [
                      { required: true, message: '请输入用户名', trigger: 'blur' }
                  ],
                  dl: [
                      { required: true,message: '请设置密码', trigger: 'blur' }
                  ],
                  sjxm:[
                      { required: true,message: '请输入手机号码', trigger: 'blur' }
                  ],
                  zt:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ],
                  zdbh:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ]
              	},
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
			console.log(this.mess)
			this.addmess = this.mess
		},
		methods:{
			colse(){
				var v = this
				v.$parent.compName = ''
		   	},
		   //确认添加新用户进行前台表单数据验证
            AddDataListOk(name){
            	var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
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