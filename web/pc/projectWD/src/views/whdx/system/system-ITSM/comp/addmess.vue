<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' 
			:mask-closable="mesF" title="新建功能">
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
						<Select v-model="addmess.zt" placeholder="请填选着状态...">
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
				this.$http.post(configApi.ITMS.ADD,this.addmess).then((res) =>{
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