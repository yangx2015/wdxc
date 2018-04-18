<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" :title="operate+'站牌'">
			<div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
				<Spin fix>
					<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
					<div style="font-size: 30px;">数据加载中请稍后</div>
				</Spin>
			</div>
			<div style="overflow: auto;height: 300px;">
				<Form
						ref="addmess"
						:model="addmess"
						:rules="ruleInline"
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
							<FormItem label='站牌名称'>
								<Input type="text" v-model="form.mc" placeholder="请填写站牌名称...">
								</Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="12">
							<FormItem label='型号:' placeholder="请选择站牌型号...">
								<Select v-model="form.xh">
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
						<Col span="24">
							<FormItem label='地址:'>
								<Input type="text" v-model="form.dz" placeholder="请填写地址...">
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
				SpinShow:false,
			    operate:'新建',
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
				this.operate = '编辑'
			}
		},
        mounted(){
        },
		methods: {
		    save(){
		    	var v = this
            	v.SpinShow = true
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
                        v.SpinShow = false
                    }
                }).catch((error) =>{
					v.$Message.error('出错了！！！');
					v.SpinShow = false
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
