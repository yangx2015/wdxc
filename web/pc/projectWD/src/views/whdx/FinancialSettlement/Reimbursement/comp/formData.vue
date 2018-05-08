<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='false'
			:mask-closable="false" :title="operate+'报销记账'">
			<div style="overflow: auto;height: 500px;">
				<Form
						ref="form"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem prop='bxr' label='报销人'>
								<Input type="text" v-model="formItem.bxr" placeholder="请填写报销人..."></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='bxsx' label='报销事项'>
								<Input type="text" v-model="formItem.bxsx" placeholder="请填写报销事项..."></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='报销时间'>
								<Input type="text" v-model="formItem.bxsj" placeholder="请填写报销时间...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='bxje' label='报销金额'>
								<Input type="text" v-model="formItem.bxje" placeholder="请填写报销金额...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem prop='fpsl' label='发票数量'>
								<Input type="text" v-model="formItem.fpsl" placeholder="请填写发票数量...">
								</Input>
							</FormItem>
						</Col>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="close">取消</Button>
				<Button type="primary" @click="save">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	export default {
		name: '',
		data() {
			return {
                createUrl:this.apis.BXJZ.ADD,
                updateUrl:this.apis.BXJZ.CHANGE,
                operate:'新建',
				showModal: true,
				readonly: false,
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
		    this.util.initFormModal(this);
		},
		methods: {
            beforeSave(){
			},
			save(){
			    this.util.save(this);
			},
			close() {
			    this.util.closeDialog(this);
            }
		}
	}
</script>

<style>

</style>
