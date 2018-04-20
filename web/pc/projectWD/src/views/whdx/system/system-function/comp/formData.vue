<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<style type="text/css">
	.input{
		display: inline-block;
	    width: 100%;
	    height: 32px;
	    line-height: 1.5;
	    padding: 4px 7px;
	    font-size: 12px;
	    border: 1px solid #dddee1;
	    border-radius: 4px;
	    color: #495060;
	    background-color: #fff;
	    background-image: none;
	    position: relative;
	    cursor: text;
	    transition: border .2s ease-in-out,background .2s ease-in-out,box-shadow .2s ease-in-out;
	}
	.input::-webkit-input-placeholder{
		color: #dddee1;
	}
	.input::-moz-placeholder{
		color: #dddee1;
	}
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			:mask-closable="mesF" :title="operate+'功能'">
			<div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
				<Spin fix>
					<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
					<div style="font-size: 30px;">数据加载中请稍后</div>
				</Spin>
			</div>
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
								<Select filterable clearable  v-model="formItem.zt" placeholder="请填选择状态...">
									<Option v-for = '(item,index) in Dictionary' :value="item.key">{{item.val}}</Option>
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
						<Col span="12">
							<FormItem label='排序'>
								<!--<Input type="number" v-model="formItem.px" placeholder="请填写排序..."></Input>-->
								<input class="input" type="number"  v-model="formItem.px"  placeholder="请填写排序..."/>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='备注信息'>
								<Input type="text" v-model="formItem.bz" placeholder="请填写备注信息..."></Input>
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
				SpinShow:false,
                operate:'新建',
				showModal: true,
				mesF: false,
				formItem: {
				},
                ruleInline:{
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
			}
		},
		methods: {
			save(){
				var v = this
				v.SpinShow = true
				let url = configApi.FUNCTION.ADD;
				if (this.$parent.choosedRow){
                    url = configApi.FUNCTION.CHANGE;
				}
				delete this.formItem.children;
				this.$http.post(url,this.formItem).then((res) =>{
					if(res.code===200){
						v.$Message.success(res.message);
					}else{
						v.$Message.error(res.message);
					}
					v.$parent.getmess()
					v.$parent.compName = ''
					v.SpinShow = false
				}).catch((error) =>{
					v.$Message.error('出错了！！！');
					v.SpinShow = false
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
