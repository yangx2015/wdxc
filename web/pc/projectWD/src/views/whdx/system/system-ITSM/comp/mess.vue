<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			:mask-closable="mesF" title="角色编辑">
			<div style="overflow: auto;height: 500px;">
				<Form>
					<FormItem label='服务名称'>
						<Input type="text" v-model="addmess.fwmc" placeholder="请填写功能名称...">
						</Input>
					</FormItem>
					<FormItem label='服务代码'>
						<Input type="text" v-model="addmess.fwdm" placeholder="请填写功能名称...">
						</Input>
					</FormItem>
					<FormItem label='状态'>
						<Select v-model="addmess.zt" placeholder="请填选择状态...">
					        <Option value="00">正常</Option>
					        <Option value="01">停用</Option>
					    </Select>
					</FormItem>
					<FormItem label='API 前缀'>
						<Input type="text" v-model="addmess.apiQz" placeholder="请填写API 前缀...">
						</Input>
					</FormItem>
					<FormItem label='图标'>
						<Input type="text" v-model="addmess.tb" placeholder="请填写地址...">
						</Input>
					</FormItem>
				</Form>
			</div>
			</Form>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="chMessData">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	import configApi from '@/axios/config.js'

	export default {
		name: '',
		data() {
			return {
				showModal: true,
				mesF: false,
				addmess: {
				}
			}
		},
		props:{
			chmess:{
				type:Object,
				default:{}
			}
		},
		created(){
			this.addmess = this.chmess
		},
		methods: {
			chMessData(){
				var v = this
				delete this.addmess.functions
				delete this.addmess.cjsj
				delete this.addmess.xgsj
				this.$http.post(configApi.ITMS.CHANGE,this.addmess).then((res) =>{
					console.log('功能数据',res)
					if(res.code===200){
						v.$Message.success('操作成功');
					}else{
						v.$Message.error('操作失败');
					}
					v.$parent.getmess()
					v.$parent.compName = ''
				})
			},
			colse(){
				var v = this
				v.$parent.compName = ''
			}
		}
	}
</script>

<style>

</style>
