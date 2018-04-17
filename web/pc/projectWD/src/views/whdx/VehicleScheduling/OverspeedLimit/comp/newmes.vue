<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='800'
		    :closable='false'
		    :mask-closable="false"
		    :title="operate+'超速设定'">
    		<Form
    			ref="addmess"
    			:model="addmess"
    			:rules="ruleInline"
    			:label-width="100"
    			:styles="{top: '20px'}">
	    		<div style="overflow: auto;height: 300px;">
					<Row>
						<Col span="12">
							<FormItem label='车型：'>
								<Select v-model="addmess.cx">
									<Option v-for="cx in cxDict" :value="cx.key">{{cx.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop="sdsx" label='速度上限：'>
								<Input type="text" v-model="addmess.sdsx" placeholder="请设置速度上限">
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
                operate:'新建',
				showModal:true,
				//新增数据
            	addmess: {
                    cx: '',
                    sdsx:'',
                },
                ruleInline: {
                  zh: [
                      { required: true, message: '请输入用户名', trigger: 'blur' }
                  ],
                  xm: [
                      { required: true, message: '请输入姓名', trigger: 'blur' }
                  ],
              	},
                cxDict:[]
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
			this.getCxDict();

            if(!this.messType){
                this.operate = '编辑'
            }
		},
		methods:{
            getCxDict(){
                this.cxDict = this.dictUtil.getByCode("ZDCLK0002");
            },
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
                this.$refs[name].validate((valid) => {
                    if (valid) {
//                    	新增
                    	if(v.messType){
                    		v.$http.post(configApi.CS.ADD,v.addmess).then((res) =>{
								if(res.code===200){
			                    	v.$Message.success(res.message);
								}else{
                                    v.$Message.warning(res.message);
								}
							}).then((res)=>{
                                v.$parent.compName = '';
							    v.$parent.getmess();
							}).catch((e)=>{
                                v.$Message.error("失败了！");
							})

                    	}else{
                    		v.$http.post(configApi.CS.CHANGE,v.addmess).then((res) =>{
                                if(res.code===200){
                                    v.$Message.success(res.message);
                                }else{
                                    v.$Message.warning(res.message);
                                }
                            }).then((res)=>{
                                v.$parent.compName = '';
                                v.$parent.getmess();
                            }).catch((e)=>{
                                v.$Message.error("失败了！");
                            })
                    	}
                    } else {
                        v.$Message.error('请认真填写超速信息!');
                    }
                })
            },
		}
	}
//15271928827
</script>

<style>
</style>