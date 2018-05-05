<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" :title="operate+'站牌'">
			<div style="overflow: auto;height: 300px;">
				<Form
						ref="addmess"
						:model="form"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem prop='zdbh' label='终端编号'>
								<Input type="text" v-model="form.zdbh" placeholder="请填写终端编号..." :disabled="edit">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem prop='mc' label='站牌名称'>
								<Input type="text" v-model="form.mc" placeholder="请填写站牌名称...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='型号:' placeholder="请选择站牌型号...">
								<Select filterable clearable  v-model="form.xh">
									<Option value="10">型号1</Option>
									<Option value="20">型号2</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='厂商：'>
								<Input type="text" v-model="form.cs" placeholder="请填写厂商信息...">
								</Input>
							</FormItem>
						</Col>
					</Row>

					<Row>
						<Col span="12">
							<FormItem label='地址:'>
								<Input type="text" v-model="form.dz" placeholder="请填写地址..."></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='站点:' placeholder="请选择站点...">
								<Select filterable clearable  v-model="form.zdId">
									<Option v-for="item in stationList" :value="item.id" :key="item.id">{{item.mc}}</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
				</Form>
			</div>
			</Form>
			<div slot='footer'>
				<Button type="ghost" @click="close">取消</Button>
				<Button type="primary" @click="save('addmess')">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	import treelist from '@/data/list.js'
    import configApi from '@/axios/config.js'
	export default {
		name: '',
		data() {
			return {
			    operate:'新建',
				showModal: true,
                mesF:false,
				edit:false,
				form: {
                    zdbh:'',
					mc: '',
					xh: '',
                    cs: '',
                    dz:'',
					zdId:''
				},
				ruleInline: {
                  zdbh: [
                      { required: true, message: '请输入终端编号', trigger: 'blur' }
                  ],
                  mc: [
                      { required: true, message: '请输站牌名称', trigger: 'blur' }
                  ],
                  sjh:[
                      { required: true,message: '请输入手机号码', trigger: 'blur' }
                  ],
                  zjhm:[
                  	{ required: true,message: '请输入证件号码', trigger: 'blur' }
                  ]
              	},
				stationList:[]
			}
		},
		created(){
			if (this.$parent.choosedRow){
				this.form = this.$parent.choosedRow;
				this.operate = '编辑'
				this.edit = true;
			}
            this.getNotBindList();
		},
        mounted(){
        },
		methods: {
		    save(){
		    	var v = this
		    	this.$refs['addmess'].validate((valid) => {
                    if (valid) {
				        let url = configApi.ZNZP.ADD;
						if (this.$parent.choosedRow){
		                    url = configApi.ZNZP.CHANGE;
						}
		                this.$http.post(url,this.form).then((res) =>{
		                    if(res.code===200){
		                        var v = this
		                        v.$parent.componentName = ''
		                        v.$parent.getPageData()
		                        this.$Message.success(res.message);
		                    }
		                }).catch((error) =>{
							v.$Message.error('出错了！！！');
						})
		            } else {
                        v.$Message.error('请认真填写用户信息!');
                    }
                })
			},
            getNotBindList(){
		        let jgdm = this.$store.state.app.userInfo.jgdm;
                this.$http.get(configApi.ZD.getNotBindList+"?stationId="+this.form.zdId).then((res) =>{
                    if(res.code===200 && res.result){
                        this.stationList = res.result;
                    }
                })
			},
			close(){
		        let v = this;
                v.$parent.componentName = ''
                v.$parent.getPageData()
			}

		}
	}
</script>

<style>

</style>
