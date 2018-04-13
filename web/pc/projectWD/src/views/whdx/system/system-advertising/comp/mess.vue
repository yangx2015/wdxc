<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' 
			:mask-closable="mesF" title="新建广告">
			<div style="overflow: auto;height: 300px;">
				<Form>
					<FormItem label='广告标题'>
						<Input type="text" v-model="addmess.RoleName" placeholder="请填写广告标题...">
						</Input>
					</FormItem>
					<FormItem label='内容'>
						<Input type="text" v-model="addmess.Remarks" placeholder="请填写广告内容...">
						</Input>
					</FormItem>
					<FormItem label='广告类型'>
						<Input type="text" v-model="addmess.RoleType" placeholder="请填写广告类型...">
						</Input>
					</FormItem>
					<FormItem label='附件'>
						 <Row>
				        	<Col span="10">
				            	<h3>视频</h3>
				            	<div style="position: relative;">
				            		<img src="../../../../../images/router.jpg" width="60%"/>
				            		<span style="cursor:pointer;position: absolute;top: 40%;left: 30%;z-index: 100;">
				            			<Icon type="play" size ='28' color="red"></Icon>
				            		</span>
				            	</div>
				            </Col>
				            <Col span="10">
				            	<h3>图片</h3>
				            	<div>
				            		<img src="../../../../../images/router.jpg" width="60%"/>
				            	</div>
				            </Col>
				        </Row>
					</FormItem>
				</Form>
			</div>
			</Form>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="colse">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	export default {
		name: '',
		data() {
			return {
				showModal: true,
				mesF: false,
				addmess: {
					RoleName: '信息学院公益活动',
					RoleType: '公益',
					Remarks: '电脑维护',
				}
			}
		},
		created(){
		    this.addmess = $parent.choosedRow;
		},
		methods: {
			colse() {
				//				var v = this
				//				v.$parent.compName = ''
				//				console.log(v.$parent)
				this.$emit('colsemodal')
			}
		}
	}
</script>

<style>

</style>