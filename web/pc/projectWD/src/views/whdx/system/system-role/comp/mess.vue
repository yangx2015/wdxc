<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" title="新建角色">
			<div style="overflow: auto;height: 300px;">
				<Form>
					<FormItem label='角色名称'>
						<Input type="text" v-model="addmess.RoleName" placeholder="请填写角色名称...">
						</Input>
					</FormItem>
					<FormItem label='类型:' placeholder="请选着角色类型...">
						<Select v-model="addmess.RoleType">
			                <Option value="0">驾驶员</Option>
			                <Option value="1">员工</Option>
			            </Select>
					</FormItem>
					<FormItem label='备注：'>
						<Input type="text" v-model="addmess.Remarks" placeholder="请填写备注信息...">
						</Input>
					</FormItem>
					<FormItem label='权限选着:'>
						<Input type="text" v-model="addmess.Remarks" placeholder="请填写备注信息...">
						</Input>
					</FormItem>
				</Form>
				<Tree :data="data4" show-checkbox multiple></Tree>
			</div>
			</Form>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="save">确定</Button>
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
					RoleName: '驾驶员角色',
					RoleType: '0',
					Remarks: '驾驶员的权限',
				},
                choosedIds :'',
				data4: [
                    {
                        title: '系统权限',
                        expand: true,
                        children: [
                            {
                                title: '权限一',
                            },
                            {
                                title: '权限二',
                            },
                            {
                                title: '权限三'
                            },
                            {
                                title: '权限四',
                            },
	                        {
	                            title: '权限五',
	                        },
	                        {
	                            title: '权限六'
                            }
                        ]
                    }
                ]
			}
		},
		props:{
			messdata:{
				type:Object,
				default:{}
			}
		},
		created(){
			console.log('数据传输',this.messdata)
			this.data4 = treelist.menuTree
		},
        mounted(){
            this.getPermissionTree();
        },
		methods: {
            getPermissionTree(){
                this.$http.get(configApi.FUNCTION.GET_ALL_PERMISSION_TREE).then((res) =>{
                    if(res.code===200){
                        this.data4 = res.result[0].functions;
                        console.log(res);
                    }
                })
            },
            setRolePermission(){
                this.getChoosedIds(this.data4);
                this.$http.post(configApi.FUNCTION.SET_ROLE_FUNCTIONS,{'jsdm':this.messdata.jsId,'gndms':this.choosedIds}).then((res) =>{
                    if(res.code===200){
                        v.$Message.success('修改成功');
                    }
                })
            },
            getChoosedIds(list){
                this.choosedIds = '';
                for(let r of list){
                    if (r.checked){
                        this.choosedIds += r.gndm+",";
					}
                    if (r.children){
                        this.getChoosedIds(r.children);
                    }
                }
            },
			save(){
				this.setRolePermission();
			},
			modifyRolePermission(){
                this.$http.get(configApi.FUNCTION.GET_ALL_PERMISSION_TREE).then((res) =>{
                    console.log(res)
                    this.SpinShow = false;
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
