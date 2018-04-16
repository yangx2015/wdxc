<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" title="新建角色">
			<div style="overflow: auto;height: 300px;">
				<Form
						ref="addmess"
						:model="addmess"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<div style="overflow: auto;height: 300px;">
						<FormItem prop="jsmc" label='角色名称：'>
							<Input type="text" v-model="addmess.jsmc" readonly>
							</Input>
						</FormItem>
						<FormItem label='权限选择:'>
							<Tree :data="data4" show-checkbox multiple></Tree>
						</FormItem>
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
				hasPermissions:[],
            }
        },
        props:{
            usermesType:{
                type:Boolean,
                default:true
            },
            messdata:{
                type:Object,
                default:{}
            }
        },
        created(){
            this.addmess = this.messdata
        },
        mounted(){
            this.getRolePermission();
        },
        methods: {
            getPermissionTree(){
                this.$http.get(configApi.FUNCTION.GET_ALL_PERMISSION_TREE).then((res) =>{
                    if(res.code===200){
                        this.data4 = res.result[0].functions;
                    }
                })
            },
			getRolePermission(){
                this.$http.get(configApi.FUNCTION.GET_ROLE_FUNCTIONS+"?jsdm="+this.addmess.jsId).then((res) =>{
                    if(res.code===200){
                        this.hasPermissions = res.result;
                        this.getPermissionTree();
                    }
                })
			},
            setRolePermission(){
                this.getChoosedIds(this.data4);
                this.$http.post(configApi.FUNCTION.SET_ROLE_FUNCTIONS,{'jsdm':this.addmess.jsId,'gndms':this.choosedIds}).then((res) =>{
                    if(res.code===200){
                        v.$Message.success('修改成功');
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
                                    v.$Message.success('角色注册成功');
                                    this.setRolePermission();
                                    v.$emit('listF',res)
                                }
                            })
                        }else{
                            v.$http.post(configApi.ROLE.CHANGE,v.addmess).then((res) =>{
                                if(res.code===200){
                                    v.$Message.success('角色修改成功');
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
