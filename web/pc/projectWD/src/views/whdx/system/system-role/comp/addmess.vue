<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" :title="operate+'角色'">
			<div style="overflow: auto;height: 300px;">
				<Form
	    			ref="addmess"
	    			:model="addmess"
	    			:rules="ruleInline"
	    			:label-width="100"
	    			:styles="{top: '20px'}">
		    		<div style="overflow: auto;height: 300px;">
						<Row>
							<Col span="12">
								<FormItem prop="jsmc" label='角色名称：'>
									<Input type="text" v-model="addmess.jsmc" placeholder="请填写角色名称">
									</Input>
								</FormItem>
							</Col>
							<Col span="12">
								<FormItem prop="jsmc" label='角色代码：'>
									<Input type="text" v-model="addmess.jsId" placeholder="请填写角色代码">
									</Input>
								</FormItem>
							</Col>
						</Row>

						<Row>
							<Col span="12">
								<FormItem label='类型：' placeholder="请选择角色类型...">
									<Select v-model="addmess.jslx">
										<Option v-for = '(item,index) in Dictionary' :value="item.key">{{item.val}}</Option>
									</Select>
								</FormItem>
							</Col>
							<Col span="12">
								<FormItem label='备注：'>
									<Input type="text" v-model="addmess.sm" placeholder="请填写备注信息...">
									</Input>
								</FormItem>
							</Col>
						</Row>

						<Row>
							<Col>
								<FormItem label='权限选择:'>
									<Tree :data="data4" show-checkbox multiple></Tree>
								</FormItem>
							</Col>
						</Row>
		    		</div>
	    		</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	import treelist from '@/data/list.js'
	import configApi from '@/axios/config.js'

	export default {
		name: '',
		data() {
			return {
				showModal: true,
				operate:'新建',
				mesF: false,
				addmess: {
					jsmc: '',
					jslx: '',
					sm: '',
				},
				ruleInline: {
                  	jsmc: [
                  		{ required: true, message: '请输角色名称', trigger: 'blur' }
                  	]
              	},
				data4: [
                ],
				choosedIds :[],
                Dictionary:[],
                lmdmDictionary:'ZDCLK0004'
			}
		},
		props:{
			usermesType:{
				type:Boolean,
				default:true
			},
		},
		created(){
            this.addmess = this.$parent.messdata
            if(!this.usermesType){
                this.operate = '编辑';
            }
        },
		mounted(){
			this.getRolePermissionTree();
            this.getLXDic();
		},
		methods: {
            getLXDic(){
                console.log('getLXDic');
                this.Dictionary = this.dictUtil.getByCode(this,this.lmdmDictionary);
                console.log(this.Dictionary);
            },
		    getAllPermissionTree(){
                this.$http.get(configApi.FUNCTION.GET_ALL_PERMISSION_TREE).then((res) =>{
                    if(res.code===200){
                        this.data4 = res.result[0].functions;
                    }
                })
			},
		    getRolePermissionTree(){
                this.$http.get(configApi.FUNCTION.GET_ROLE_PERMISSION_TREE+"?jsdm="+this.addmess.jsId).then((res) =>{
                    if(res.code===200){
                        this.data4 = res.result[0].functions;
                    }
                })
			},
		    setRolePermission(){
                this.getChoosedIds(this.data4);
                let ids = '';
                for (let r of this.choosedIds){
                    ids += r+',';
				}
                this.$http.post(configApi.FUNCTION.SET_ROLE_FUNCTIONS,{'jsdm':this.addmess.jsId,'gndms':ids}).then((res) =>{
                    if(res.code===200){
                        this.$Message.success(res.message);
                    }
                })
			},
			getChoosedIds(list){
		        for(let r of list){
		            if (r.checked)this.choosedIds.push(r.gndm);
		            if (r.children){
		             	this.getChoosedIds(r.children);
					}
				}
			},
			AddDataListOk(name){
                var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
						if(v.usermesType){
	                		v.$http.post(configApi.ROLE.ADD,v.addmess).then((res) =>{
								if(res.code===200){
                                    this.setRolePermission();
									v.$emit('listF',res)
								}
							})
						}else{
							v.$http.post(configApi.ROLE.CHANGE,v.addmess).then((res) =>{
								if(res.code===200){
									v.$Message.success(res.message);
                                    this.setRolePermission();
									v.$emit('listF',res)
								}
							})
						}
                    } else {
                        v.$Message.error('请认真填写角色信息!');
                    }
                })
            },
			colse() {
				var v = this
				v.$parent.compName = ''
			}
		}
	}
</script>

<style>

</style>
