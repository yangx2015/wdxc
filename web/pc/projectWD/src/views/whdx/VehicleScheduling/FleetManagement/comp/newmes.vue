<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='800'
		    :closable='false'
		    :mask-closable="false"
		    :title="operate+'车队'">
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
								<!--<Input type="text" v-model="addmess.dzxm" placeholder="请输入队长姓名">
								</Input>-->
								<Select filterable clearable remote
										:remote-method="remoteMethod"  v-model="userSelectMss" @on-change='userSelect()'>
									<Option v-for="(item,index) in userList" :value="item.yhid">{{item.xm}}</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<!--<Col span="12">
							<FormItem prop="sjhm" label='手机号码：'>
								<Input type="text" v-model="addmess.sjhm" placeholder="请输入手机号码">
								</Input>
							</FormItem>
						</Col>-->
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


	export default {
		name:'',
		data(){
			return {
				showModal:true,
                operate:'新增',
                userSelectMss:'',
				//新增数据
            	addmess: {
                    cdmc:'',//车队名称
                    dzxm:'',//队长姓名
                    sjhm:'',//手机号码
                    zt:'00',//状态
                    dzbh:''//队长编号
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
              	userList:[]
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
			log('字典状态',this.ty)
			this.fullcal()
            if(!this.messType){
            	log('数据传递',this.mess)
            	this.addmess = this.mess
			    this.operate = '编辑'
//			    alert(this.mess.dzbh)
			    this.userSelectMss = this.mess.dzbh
            }
		},
		methods:{
            remoteMethod(k){
                var v = this
                this.$http.get(this.apis.USER.QUERY,{params:{xmLike:k,pageSize:1000}}).then((res) =>{
                    this.userList = res.page.list
                })
			},
			fullcal(){
				log('信息',this.mess)
			},
			colse(){
				var v = this
				v.$parent.compName = ''
		   	},
		   	userSelect(){
				var v = this
				var listIndex = 0
		   		this.userList.forEach(function(item,index){
		   			if(item.yhid == v.userSelectMss){
		   				listIndex = index
		   				v.addmess.dzxm = v.userList[listIndex].xm
		   				v.addmess.sjhm = v.userList[listIndex].sjh
		   				v.addmess.dzbh = v.userList[listIndex].yhid
		   				return 
		   			}
		   		})
		   	},
		   	getUSER(){
				var v = this
				this.$http.get(this.apis.USER.QUERY).then((res) =>{
					log('用户列表',res)
					this.userList = res.page.list
				})
		   	},
		   //确认添加新用户进行前台表单数据验证
            AddDataListOk(name){
            	var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
//                    	新增
                    	if(v.messType){
                    		v.$http.post(this.apis.CD.ADD,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('操作成功');
								}else{
									v.$Message.warning(res.message);
								}
							}).then((res) =>{
								v.$parent.getmess()
            					v.$parent.compName = ''
							}).catch((error) =>{
								v.$Message.error('出错了！！！');
							})
							//修改
                    	}else{
                    		v.$http.post(this.apis.CD.CHANGE,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success('操作成功');
								}else{
									v.$Message.warning(res.message);
								}
							}).then((res) =>{
								v.$parent.getmess()
            					v.$parent.compName = ''
							}).catch((error) =>{
								v.$Message.error('出错了！！！');
							})
                    	}
                    } else {
                        v.$Message.error('请认真填写用户信息!');
                    }
                })
            }
		}
	}
</script>

<style>
</style>
