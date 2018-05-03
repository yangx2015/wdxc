<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			:mask-closable="mesF" title="编辑功能">
			<div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
				<Spin fix>
					<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
					<div style="font-size: 30px;">数据加载中请稍后</div>
				</Spin>
			</div>
			<div style="overflow: auto;height: 500px;">
				<Form>
					<FormItem label='功能名称'>
						<Input type="text" v-model="addmess.gnmc" placeholder="请填写功能名称...">
						</Input>
					</FormItem>
					<FormItem label='功能代码'>
						<Input type="text" v-model="addmess.gndm" placeholder="请填写功能名称..." readonly>
						</Input>
					</FormItem>
					<FormItem label='服务代码'>
						<Input type="text" v-model="addmess.fwdm" placeholder="请填写功能名称...">
						</Input>
					</FormItem>
					<FormItem label='状态'>
						<Select filterable clearable  v-model="addmess.zt" placeholder="请填选择状态...">
							<Option v-for = '(item,index) in Dictionary' :value="item.key">{{item.val}}</Option>
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
				SpinShow:false,
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
			},
			Dictionary:{
				type:Array,
				default:[]
			}
		},
		created(){
			this.addmess = this.chmess
		},
		methods: {
			chMessData(){
				var v = this
				v.SpinShow = true
				delete this.addmess.children
				this.$http.post(configApi.FUNCTION.CHANGE,this.addmess).then((res) =>{
					log('功能数据',res)
					if(res.code===200){
						v.$Message.success('操作成功');
					}else{
						v.$Message.error('操作失败');
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
