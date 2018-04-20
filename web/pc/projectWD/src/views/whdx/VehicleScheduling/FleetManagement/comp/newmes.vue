<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='800'
		    :closable='false'
		    :mask-closable="false"
		    :title="operate+'用户'">
		    <div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
				<Spin fix>
					<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
					<div style="font-size: 30px;">数据加载中请稍后</div>
				</Spin>
			</div>
    		<Form
    			ref="addmess"
    			:model="addmess"
    			:rules="ruleInline"
    			:label-width="100"
    			:styles="{top: '20px'}">
	    		<div style="overflow: auto;height: 360px;">
					<Row>
						<Col span="12">
							<FormItem prop="cdmc" label='车队名称：'>
								<Input v-model="addmess.cdmc" placeholder="请设置车队名称">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="dzxm" label='队长姓名：'>
								<Input type="text" v-model="addmess.dzxm" placeholder="请输入队长姓名">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop="sjhm" label='手机号码：'>
								<Input type="text" v-model="addmess.sjhm" placeholder="请输入手机号码">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="zt" label='状态：'>
								<Select filterable clearable  v-model="addmess.zt">
									<Option v-for="item in ty" :value="item.key">{{item.val}}</Option>
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
				SpinShow:false,
				showModal:true,
                operate:'新增',
				//新增数据
            	addmess: {
                    cdmc:'',
                    dzxm:'',
                    sjhm:'',
                    zt:'0'
                },
                ruleInline: {
                  cdmc: [
                      { required: true, message: '请输入姓名', trigger: 'blur' }
                  ],
                  dzxm: [
                      { required: true,message: '请设置密码', trigger: 'blur' }
                  ],
                  sjhm:[
                      { required: true,message: '请输入手机号码', trigger: 'blur' }
                  ],
                  zt:[
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
			},
			ty:{
				type:Array,
				default:[]
			}
		},
		created(){
			console.log('字典状态',this.ty)
			this.addmess = this.mess
			this.fullcal()
            if(!this.messType){
			    this.operate = '编辑'
            }
		},
		methods:{
			fullcal(){
				console.log('信息',this.mess)
			},
			colse(){
				var v = this
				v.$parent.compName = ''
		   	},
		   //确认添加新用户进行前台表单数据验证
            AddDataListOk(name){
            	var v = this
            	v.SpinShow = true
                this.$refs[name].validate((valid) => {
                    if (valid) {
//                    	新增
                    	if(v.messType){
                    		v.$http.post(configApi.CD.ADD,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('操作成功');
								}else{
									v.$Message.warning(res.message);
								}
							}).then((res) =>{
								v.$parent.getmess()
            					v.$parent.compName = ''
            					v.SpinShow = false
							}).catch((error) =>{
								v.$Message.error('出错了！！！');
								v.SpinShow = false
							})
							//修改
                    	}else{
                    		v.$http.post(configApi.CD.CHANGE,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('操作成功');
								}else{
									v.$Message.warning(res.message);
								}
							}).then((res) =>{
								v.$parent.getmess()
            					v.$parent.compName = ''
            					v.SpinShow = false
							}).catch((error) =>{
								v.$Message.error('出错了！！！');
								v.SpinShow = false
							})
                    	}
                    } else {
                    	v.SpinShow = false
                        v.$Message.error('请认真填写用户信息!');
                    }
                })
            }
		}
	}
//15271928827
</script>

<style>
</style>
