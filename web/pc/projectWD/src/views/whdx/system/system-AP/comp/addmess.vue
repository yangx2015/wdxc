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
			                <Option value="0">管理</Option>
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
			</div>
			</Form>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="colse">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	export default {
		name: '',
		data() {
			return {
				showModal: true,
				mesF: false,
				addmess: {
					RoleName: '',
					RoleType: '',
					Remarks: '',
				}
			}
		},
		created(){
		},
		methods: {
			colse() {
				//				var v = this
				//				v.$parent.compName = ''
				//				console.log(v.$parent)
				this.$emit('colsemodal')
			}
		}
	}
</script>

<style>

</style>