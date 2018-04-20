<style lang="less">
    @import "../../../../../styles/common.less";
</style>
<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='800'
		    :closable='false'
		    :mask-closable="false"
		    title="车辆限速修改">
    		<Form
    			ref="addmess"
    			:model="mess"
    			:rules="ruleInline"
    			:label-width="100"
    			:styles="{top: '20px'}">
	    		<div style="height: 300px;">
	    			<Row>
	    				<Col span="12">
	    					<FormItem label='车牌号：'>
								<Input readonly="readonly" type="text" v-model="mess.cph" placeholder="请设置速度上限">
								</Input>
							</FormItem>
	    				</Col>
	    				<Col span="12">
	    					<FormItem prop="cph" label='速度上限：'>
								<Input type="text" v-model="mess.sdsx" placeholder="请设置速度上限">
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
                    cph: '',
                    sdsx:'',
                },
                ruleInline: {
                  cph: [
                      { required: true, message: '请输车速上限', trigger: 'blur' }
                  ]
              	}
			}
		},
		props:{
			mess:{
				type:Object,
				default:{}
			}
		},
		created(){
			console.log('数据传输',this.mess)
			this.addmess.cph = this.mess.cph
			this.addmess.sdsx = this.mess.sdsx
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
//                    	修改
	                		v.$http.post(configApi.CS.ADD,{'cphs':v.mess.cph, 'csz':v.mess.sdsx}).then((res) =>{
								if(res.code===200){
									v.$parent.getmess();
			                    	v.$Message.success(res.message);
			                    	v.colse()
								}else{
	                                v.$Message.warning(res.message);
								}
							}).catch((e)=>{
	                            v.$Message.error("失败了！");
							})
                    } else {
                        v.$Message.error('请认真填写超速信息!');
                    }
                })
            }
		}
	}
</script>

<style>
</style>