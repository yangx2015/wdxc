<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='800'
		    :closable='false'
		    :mask-closable="false"
		    title="新增驾驶员">
    		<Form
    			ref="addmess"
    			:model="addmess"
    			:rules="ruleInline"
    			:label-width="100"
    			:styles="{top: '20px'}">
	    		<div style="overflow: auto;height: 300px;">
					<Row>
						<Col span="12">
							<FormItem prop="xm" label='姓名：'>
								<Input type="text" v-model="addmess.xm" placeholder="请输入姓名">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label="性别： ">
								<Select v-model="addmess.xb">
									<Option value="1">男</Option>
									<Option value="0">女</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop="nl" label='年龄：'>
								<Input type="text" v-model="addmess.nl" placeholder="请输入年龄">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='驾驶证类型：'>
								<Select v-model="addmess.zjcx">
									<Option value="11">类型一</Option>
									<Option value="22">类型二</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop="sfzhm" label='证件号码：'>
								<Input type="text" v-model="addmess.sfzhm" placeholder="请输入证件号码">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="cdbh" label='车队编号：'>
								<Input type="text" v-model="addmess.cdbh" placeholder="请输入车队编号">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop="clrq" label='初领日期：'>
								<DatePicker v-model="clrq" format="yyyy-MM-dd" type="date" placement="bottom-end" placeholder="请输时间"  style="width: 220px"></DatePicker>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="dabh" label='档案编号：'>
								<Input type="text" v-model="addmess.dabh" placeholder="请输入档案编号">
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
    import mixins from '@/mixins'
	export default {
		name:'',
        mixins:[mixins],
		data(){
			return {
				showModal:true,
				//新增数据
                clrq:'',
                addmess: {
                	xm:'',
                	xb:'1',
                	nl:'',
                	zjcx:'11',
                	sfzhm:'',
                	cdbh:'',
                	dabh:'',
                    clrq:''
                },
                ruleInline: {
                  xm: [
                      { required: true, message: '请输入驾驶员名', trigger: 'blur' }
                  ],
                  nl: [
                      { required: true, message: '请输入驾驶员名', trigger: 'blur' }
                  ],
                  zjcx:[
                      { required: true,message: '请输入手机号码', trigger: 'blur' }
                  ],
                  sfzhm:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ],
                  cdbh:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ],
                  dabh:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ],
                  clrq:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ]
              	},
			}
		},

        watch:{
            clrq:function(n,o){
                console.log('书剑',n);
                console.log('书剑',this.getdateParaD(n));
            	this.addmess.clrq = this.getdateParaD(n)
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
			this.addmess.xb = '1'
			this.addmess.zjcx = '11'
			this.fullcal()
		},
		methods:{
			fullcal(){
				console.log('信息',this.mess)
			},
			colse(){
				var v = this
				v.$parent.compName = ''
		   	},
		   //确认添加新驾驶员进行前台表单数据验证
            AddDataListOk(name){
            	var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
//                    	新增
                    	if(v.messType){
                    		v.$http.post(configApi.JSY.ADD,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('驾驶员注册成功');
			                    	v.$parent.getmess()
                					v.$parent.compName = ''
								}else{
									v.$Message.warning(res.message);
								}
							})
                    	}else{
                    		v.$http.post(configApi.JSY.CHANGE,v.addmess).then((res) =>{
								if(res.code===200){
									v.$Message.success('驾驶员修改成功');
//									v.$emit('listF',res)
								}else{
									v.$Message.error('驾驶员修改失败');
								}
							})
                    	}
                    } else {
                        v.$Message.error('请认真填写驾驶员信息!');
                    }
                })
            },
		}
	}
//15271928827
</script>

<style>
</style>