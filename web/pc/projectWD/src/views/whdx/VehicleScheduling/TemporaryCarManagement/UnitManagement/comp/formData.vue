<style lang="less">
	@import '../../../../../../styles/common.less';
</style>
<style type="text/css">

</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='false'
			:mask-closable="false" :title="operate+'单位'">
			<div style="overflow: auto;height: 500px;">
				<Form
						ref="form"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem prop='dwmc' label='单位名称'>
								<Input type="text" v-model="formItem.dwmc" placeholder="请填写单位名称..."></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='dwbh' label='单位编号'>
								<Input type="text" v-model="formItem.dwbh" placeholder="请填写单位编号..."></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='联系人'>
								<Input type="text" v-model="formItem.lxr" placeholder="请填写联系人...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='lxdh' label='联系电话'>
								<Input type="text" v-model="formItem.lxdh" placeholder="请填写联系电话...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='状态'>
								<Select filterable clearable  v-model="formItem.zt" placeholder="请填选择状态...">
									<Option value="00">正常</Option>
									<Option value="10">停用</Option>
								</Select>
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
                createUrl:this.apis.TEMP_UNIT.ADD,
                updateUrl:this.apis.TEMP_UNIT.CHANGE,
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
