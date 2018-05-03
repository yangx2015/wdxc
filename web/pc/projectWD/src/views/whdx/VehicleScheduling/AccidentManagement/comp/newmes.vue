<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='800'
		    :closable='false'
		    :mask-closable="false"
		    :title="operate+'用户'">
    		<Form
    			ref="addmess"
    			:model="addmess"
    			:rules="ruleInline"
    			:label-width="100"
    			:styles="{top: '20px'}">
	    		<div style="overflow: auto;height: 300px;">
			        <FormItem prop="zh" label='用户名：'>
			            <Input type="text" v-model="addmess.zh" placeholder="请设置用户帐号">
			            </Input>
			        </FormItem>
			        <FormItem prop="mm" label='密码：'>
			            <Input type="password" v-model="addmess.mm" placeholder="请设置用户密码">
			            </Input>
			        </FormItem>
			        <FormItem prop="xm" label='姓名：'>
			            <Input type="text" v-model="addmess.xm" placeholder="请输入姓名">
			            </Input>
			        </FormItem>
			        <FormItem label="性别： ">
			            <Select filterable clearable  v-model="addmess.xb">
			                <Option value="1">男</Option>
			                <Option value="0">女</Option>
			            </Select>
			        </FormItem>
			        <FormItem label='类型：'>
			        	<Select filterable clearable  v-model="addmess.lx">
			                <Option value="11">类型一</Option>
			                <Option value="22">类型二</Option>
			            </Select>
			        </FormItem>
			        <FormItem label='职务：'>
			            <Input type="text" v-model="addmess.zw" placeholder="请输入职务">
			            </Input>
			        </FormItem>
			        <FormItem prop="sjh" label='手机号码：'>
			            <Input type="text" v-model="addmess.sjh" placeholder="请输入手机号码">
			            </Input>
			        </FormItem>
			        <FormItem prop="zjhm" label='证件号码：'>
			            <Input type="text" v-model="addmess.zjhm" placeholder="请输入证件号码">
			            </Input>
			        </FormItem>
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
                    zh: '',
                    xm:'',
                    mm:'',
                    lx:'',
                    xb:'0',
                    zw:'',
                    sjh:'',
                    zjhm:''
                },
                ruleInline: {
                  zh: [
                      { required: true, message: '请输入用户名', trigger: 'blur' }
                  ],
                  xm: [
                      { required: true, message: '请输入姓名', trigger: 'blur' }
                  ],
                  mm: [
                      { required: true,message: '请设置密码', trigger: 'blur' }
                  ],
                  sjh:[
                      { required: true,message: '请输入手机号码', trigger: 'blur' }
                  ],
                  zjhm:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ]
              	},
				operate:'新建'
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
			this.addmess = this.mess
			this.fullcal()
            if(!this.messType){
			    this.operate = '编辑'
            }
		},
		methods:{
			fullcal(){
				log('信息',this.mess)
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
//                    	新增
                    	if(v.messType){
                    		v.$http.post(configApi.USER.ADD,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('用户注册成功');
									v.$emit('listF',res)
								}
							})
                    	}else{
                    		v.$http.post(configApi.USER.CHANGE,v.addmess).then((res) =>{
								if(res.code===200){
									v.$Message.success('用户修改成功');
									v.$emit('listF',res)
								}
							})
                    	}
                    } else {
                        v.$Message.error('请认真填写用户信息!');
                    }
                })
            },
		}
	}
//15271928827
</script>

<style>
</style>
