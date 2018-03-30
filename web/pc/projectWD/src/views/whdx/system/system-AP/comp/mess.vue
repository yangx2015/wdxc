<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" title="新建权限">
			<div style="overflow: auto;height: 300px;">
				<Form>
					<FormItem label='权限名称'>
						<Input type="text" v-model="addmess.RoleName" placeholder="请填写角色名称...">
						</Input>
					</FormItem>
					<FormItem label='备注：'>
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
					RoleName: '驾驶员权限',
					RoleType: '',
					Remarks: '驾驶员权限信息',
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