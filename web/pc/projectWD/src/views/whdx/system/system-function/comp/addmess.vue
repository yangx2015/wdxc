<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' 
			:mask-closable="mesF" title="新建功能">
			<div style="overflow: auto;height: 500px;">
				<Form>
					<FormItem label='功能名称'>
						<Input type="text" v-model="addmess.gnmc" placeholder="请填写功能名称...">
						</Input>
					</FormItem>
					<FormItem label='功能代码'>
						<Input type="text" v-model="addmess.gndm" placeholder="请填写功能名称...">
						</Input>
					</FormItem>
					<FormItem label='服务代码'>
						<Input type="text" v-model="addmess.fwdm" placeholder="请填写功能名称...">
						</Input>
					</FormItem>
					<FormItem label='状态'>
						<Select v-model="addmess.zt" placeholder="请填选着状态...">
					        <Option value="00">正常</Option>
					        <Option value="01">停用</Option>
					    </Select>
					</FormItem>
					<FormItem label='URL'>
						<Input type="text" v-model="addmess.url" placeholder="请填写地址...">
						</Input>
					</FormItem>
					<FormItem label='图标'>
						<Input type="text" v-model="addmess.tb" placeholder="请填写地址...">
						</Input>
					</FormItem>
					<FormItem label='父节点'>
						<Input type="text" v-model="addmess.fjd" placeholder="请填写父节点...">
						</Input>
					</FormItem>
					<FormItem label='跳转地址'>
						<Input type="text" v-model="addmess.tzdz" placeholder="请填写跳转地址...">
						</Input>
					</FormItem>
					<FormItem label='API 前缀'>
						<Input type="text" v-model="addmess.apiQz" placeholder="请填写API 前缀...">
						</Input>
					</FormItem>
					<FormItem label='API 前缀'>
						<Input type="text" v-model="addmess.apiHz" placeholder="请填写API 前缀...">
						</Input>
					</FormItem>
					<FormItem label='备注信息'>
						<Input type="text" v-model="addmess.bz" placeholder="请填写备注信息...">
						</Input>
					</FormItem>
				</Form>
			</div>
			</Form>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="addmessData">确定</Button>
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
		created(){
		},
		methods: {
			addmessData(){
				var v = this
				this.$http.post(configApi.FUNCTION.ADD,this.addmess).then((res) =>{
					console.log('功能数据',res)
					if(res.code===200){
						v.$Message.success('创建成功');
					}else{
						v.$Message.error('创建失败');
					}
					v.$parent.getmess()
					v.$parent.compName = ''
				})
			},
			colse(){
								var v = this
								v.$parent.compName = ''
				//				console.log(v.$parent)
//				this.$emit('colsemodal')
			}
		}
	}
</script>

<style>

</style>