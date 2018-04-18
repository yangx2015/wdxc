<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' 
			:closable='false' :mask-closable="mesF" title="终端设备信息编辑">
			<div style="overflow: auto;height: 300px;">
				<Form
						ref="form"
						:model="form"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem label='终端编号'>
								<Input type="text" v-model="form.zdbh" placeholder="请填写终端编号...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='终端名称'>
								<Input type="text" v-model="form.mc" placeholder="请填终端名称...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='设备状态:' placeholder="请选择设备状态">
								<Select v-model="form.zt">
									<Option v-for="item in dic" :value="item.key">{{item.val}}</Option>
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
				</Form>
			</div>
			</Form>
			<div slot='footer'>
				<Button type="ghost" @click="close">取消</Button>
				<Button type="primary" @click="save">确定</Button>
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
				showModal: true,
                mesF:false,
				form: {
                    zdbh:'',//终端编号
					mc: '',//名称
                    cs: '',//厂商
                    zt:''//终端状态
				},
			}
		},
		props:{
			dic:{
				type:Array,
				default:[]
			},
			mess:{
				type:Object,
				default:{}
			}
		},
		created(){
			this.form = this.mess
		},
        mounted(){
        },
		methods: {
		    save(){
		        let url = configApi.ZNZP.CHANGE;
                this.$http.post(url,this.form).then((res) =>{
                    this.$Message.success(res.message);
                    this.close();
                })
			},
			close(){
		        this.showModal = false;
		        this.$parent.componentName = '';
			}

		}
	}
</script>

<style>

</style>
