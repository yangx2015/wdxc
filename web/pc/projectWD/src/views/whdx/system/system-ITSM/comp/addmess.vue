<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			:mask-closable="mesF" title="新建服务">
			<div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
				<Spin fix>
					<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
					<div style="font-size: 30px;">数据加载中请稍后</div>
				</Spin>
			</div>
			<div style="overflow: auto;height: 500px;">
				<Form
						ref="addmess"
						:model="addmess"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem label='服务名称'>
								<Input type="text" v-model="addmess.fwmc" placeholder="请填写功能名称...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='服务代码'>
								<Input type="text" v-model="addmess.fwdm" placeholder="请填写功能名称...">
								</Input>
							</FormItem>
						</Col>
					</Row>

					<Row>
						<Col span="12">
							<FormItem label='状态'>
								<Select filterable clearable  v-model="addmess.zt" placeholder="请填选择状态...">
									<Option v-for = '(item,index) in Dictionary' :value="item.key">{{item.val}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='API 前缀'>
								<Input type="text" v-model="addmess.apiQz" placeholder="请填写API 前缀...">
								</Input>
							</FormItem>
						</Col>
					</Row>

					<Row>
						<Col span="24">
							<FormItem label='图标'>
								<Input type="text" v-model="addmess.tb" placeholder="请填写地址...">
								</Input>
							</FormItem>
						</Col>
					</Row>
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
				SpinShow:false,
				showModal: true,
				mesF: false,
				addmess: {
				}
			}
		},
		props:{
			Dictionary:{
				type:Array,
				default:[]
			}
		},
		created(){
		},
		methods: {
			addmessData(){
				var v = this
				v.SpinShow = true
				this.$http.post(configApi.ITMS.ADD,this.addmess).then((res) =>{
					console.log('功能数据',res)
					if(res.code===200){
						v.$Message.success('创建成功');
					}else{
						v.$Message.error('创建失败');
					}
					v.$parent.getmess()
					v.$parent.compName = ''
					v.SpinShow = false
				}).catch((error) =>{
					v.$Message.error('出错了！！！');
					v.SpinShow = false
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
