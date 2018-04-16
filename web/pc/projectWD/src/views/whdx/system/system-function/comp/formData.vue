<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			:mask-closable="mesF" :title="operate + entityName">
			<div style="overflow: auto;height: 500px;">
				<Form
						ref="formItem"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem label='功能名称'>
								<Input type="text" v-model="formItem.gnmc" placeholder="请填写功能名称..."></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='功能代码'>
								<Input type="text" v-model="formItem.gndm" placeholder="请填写功能名称..."></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='服务代码'>
								<Input type="text" v-model="formItem.fwdm" placeholder="请填写功能名称..."></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='状态'>
								<Select v-model="formItem.zt" placeholder="请填选择状态...">
									<Option value="00">正常</Option>
									<Option value="01">停用</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='URL'>
								<Input type="text" v-model="formItem.url" placeholder="请填写地址...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='图标'>
								<Input type="text" v-model="formItem.tb" placeholder="请填写地址...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='父节点'>
								<Input type="text" v-model="formItem.fjd" placeholder="请填写父节点...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='跳转地址'>
								<Input type="text" v-model="formItem.tzdz" placeholder="请填写跳转地址...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='API 前缀'>
								<Input type="text" v-model="formItem.apiQz" placeholder="请填写API 前缀...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='API 前缀'>
								<Input type="text" v-model="formItem.apiHz" placeholder="请填写API 前缀...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="24">
							<FormItem label='备注信息'>
								<Input type="text" v-model="formItem.bz" placeholder="请填写备注信息...">
								</Input>
							</FormItem>
						</Col>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="save">确定</Button>
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
			    operate:'新建',
				entityName:'功能',
				showModal: true,
				mesF: false,
				formItem: {
				},
                ruleInline:{
				}
			}
		},
		created(){
		    if (this.$parent.choosedRow){
				this.formItem = this.$parent.choosedRow;
                this.operate = '编辑'
			}
		},
		methods: {
			save(){
				var v = this
				let url = configApi.FUNCTION.ADD;
				if (this.$parent.choosedRow){
                    url = configApi.FUNCTION.CHANGE;
				}
				this.$http.post(url,this.formItem).then((res) =>{
					if(res.code===200){
						v.$Message.success('创建成功');
					}else{
						v.$Message.error('创建失败');
					}
					v.$parent.getmess()
					v.$parent.compName = ''
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
