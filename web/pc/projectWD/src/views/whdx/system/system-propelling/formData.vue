<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" title="新建站牌">
			<div style="overflow: auto;height: 300px;">
				<Form>
					<FormItem label='终端编号'>
						<Input type="text" v-model="form.zdbh" placeholder="请填写终端编号...">
						</Input>
					</FormItem>
					<FormItem label='站牌名称'>
						<Input type="text" v-model="form.mc" placeholder="请填写站牌名称...">
						</Input>
					</FormItem>
					<FormItem label='型号:' placeholder="请选着站牌型号...">
						<Select v-model="form.xh">
			                <Option value="10">型号1</Option>
			                <Option value="20">型号2</Option>
			            </Select>
					</FormItem>
					<FormItem label='厂商：'>
						<Input type="text" v-model="form.cs" placeholder="请填写厂商信息...">
						</Input>
					</FormItem>
					<FormItem label='地址:'>
						<Input type="text" v-model="form.dz" placeholder="请填写地址...">
						</Input>
					</FormItem>
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
                    zdbh:'',
					mc: '',
					xh: '',
                    cs: '',
                    dz:'',
				},
			}
		},
		created(){
			if (this.$parent.choosedRow){
				this.form = this.$parent.choosedRow;
			}
		},
        mounted(){
        },
		methods: {
		    save(){
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