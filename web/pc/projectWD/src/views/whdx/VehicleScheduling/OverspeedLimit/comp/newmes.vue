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
		    :title="operate+'超速设定'">
    		<Form
    			ref="addmess"
    			:model="addmess"
    			:rules="ruleInline"
    			:label-width="100"
    			:styles="{top: '20px'}">
	    		<div style="overflow: auto;height: 300px;">
	    			<div class="box-row">
	    				<div>
	    					<div v-show="treeList.length==0" style="color: red;">
	    						*请选车量
	    					</div>
	    					<Tree :data="data1"
						  		show-checkbox
						 	 	@on-check-change='checkClick'></Tree>
	    				</div>
	    				<div class="body-F">
	    					<FormItem prop="sdsx" label='速度上限：'>
								<Input type="text" v-model="addmess.sdsx" placeholder="请设置速度上限">
								</Input>
							</FormItem>
	    				</div>
	    			</div>
	    		</div>
    		</Form>
    		<div>
    			<span v-for="item in treeList">{{item.title}}</span>
    		</div>
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
                  sdsx: [
                      { required: true, message: '请输车数上线', trigger: 'blur' }
                  ],
              	},
              	data1:[],
              	treeList:[]
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
            
            this.getCarTree()
		},
		methods:{
			//获取车辆树
			getCarTree(){
	    		this.$http.get(configApi.CARTREE.QUERY).then((res) =>{
	    			console.log('数据结构数据',res)
	    			this.data1 = res.result
	        	}).catch((error) =>{
	        		console.log('error',error)
	        	})
	    	},
			//树多选框
	    	checkClick(event){
	    		console.log('2',event)
	    		var v = this
	    		v.treeList = []
	    		for( var i = 0 ; i<event.length;i++){
	    			if(event[i].children){
	    				console.log('树输出')
	    			}else{
	    				v.treeList.push(event[i])
	    			}
	    		}
	    	},
	    	//树多点击事件
//	    	treeClick(mess){
//	    		console.log('1',mess)
//	    		if(mess[0]){
//	    			this.treeList = mess
//	    		}
//	    	},
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
                    if (valid && v.treeList.length>0) {
//                    	新增
						for(var a=0;a<v.treeList.length;a++){
	                		v.$http.post(configApi.CS.ADD,{'cph':v.treeList[a].title}).then((res) =>{
								if(res.code===200){
									v.$parent.getmess();
			                    	v.$Message.success(res.message);
								}else{
	                                v.$Message.warning(res.message);
								}
							}).catch((e)=>{
	                            v.$Message.error("失败了！");
							})
							if(a==v.treeList.length-1){
								v.$parent.compName = '';
							}
						}
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