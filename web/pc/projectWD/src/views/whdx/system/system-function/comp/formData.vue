<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			:mask-closable="mesF" :title="operate+'功能'">
			<div style="overflow: auto;height: 500px;">
				<Form
						ref="formItem"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem prop='gnmc' label='功能名称'>
								<Input type="text" v-model="formItem.gnmc" placeholder="请填写功能名称..."></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='gndm' label='功能代码'>
								<Input :readonly="read" type="text" v-model="formItem.gndm" placeholder="请填写功能名称..."></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop='fwdm' label='服务代码'>
								<Input type="text" v-model="formItem.fwdm" placeholder="请填写功能名称..."></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='状态'>
								<Select filterable clearable  v-model="formItem.zt" placeholder="请填选择状态...">
									<Option v-for = '(item,index) in Dictionary' :value="item.key">{{item.val}}</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop='url' label='URL'>
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
							<FormItem prop='fjd' label='父节点'>
								<Input type="text" v-model="formItem.fjd" placeholder="请填写父节点...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='tzdz' label='跳转地址'>
								<Input type="text" v-model="formItem.tzdz" placeholder="请填写跳转地址...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop='apiQz' label='API 前缀'>
								<Input type="text" v-model="formItem.apiQz" placeholder="请填写API 前缀...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='apiHz' label='API 前缀'>
								<Input type="text" v-model="formItem.apiHz" placeholder="请填写API 前缀...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='排序'>
								<!--<Input type="number" v-model="formItem.px" placeholder="请填写排序..."></Input>-->
								<input class="input" type="number" min="0"  v-model="formItem.px"  placeholder="请填写排序..."/>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='bz' label='备注信息'>
								<Input type="text" v-model="formItem.bz" placeholder="请填写备注信息..."></Input>
							</FormItem>
						</Col>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="save('formItem')">确定</Button>
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
				showModal: true,
				mesF: false,
				read: false,
				formItem: {
					px:1,
					zt:'00'
				},
                ruleInline:{
                	gnmc: [
                    	{ required: true, message: '请将信息填写完整', trigger: 'blur' }
                    ],
                    gndm: [
                    	{ required: true, message: '请将信息填写完整', trigger: 'blur' }
                    ],
                    fwdm: [
                    	{ required: true, message: '请将信息填写完整', trigger: 'blur' }
                    ],
                    url: [
                    	{ required: true, message: '请将信息填写完整', trigger: 'blur' }
                    ],
                    px: [
                    	{ required: true, message: '请将信息填写完整', trigger: 'blur' },
                    	{ min: 0, message: '请将信息填写完整', trigger: 'blur' }
                    ],
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
		    if (this.$parent.choosedRow){
				this.formItem = this.$parent.choosedRow;
                this.operate = '编辑'
                this.read = true
			}
		},
		methods: {
			save(name){
				var v = this
				let url = configApi.FUNCTION.ADD;
				if (this.$parent.choosedRow){
                    url = configApi.FUNCTION.CHANGE;
				}
				delete this.formItem.children;
				this.$refs[name].validate((valid) => {
                    if (valid) {
//                    	新增
						this.$http.post(url,this.formItem).then((res) =>{
							if(res.code===200){
								v.$Message.success(res.message);
							}else{
								v.$Message.error(res.message);
							}
							v.$parent.getmess()
							v.$parent.compName = ''
						}).catch((error) =>{
							v.$Message.error('出错了！！！');
						})
					} else {
                        v.$Message.error('请将信息填写完整!');
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
