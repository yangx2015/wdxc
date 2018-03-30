<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' 
			:mask-closable="mesF" title="新建广告">
			<div style="overflow: auto;height: 440px;">
				<Form>
					<FormItem label='广告标题'>
						<Input type="text" v-model="addmess.RoleName" placeholder="请填写广告标题...">
						</Input>
					</FormItem>
					<FormItem label='内容'>
						<Input type="text" v-model="addmess.RoleType" placeholder="请填写广告内容...">
						</Input>
					</FormItem>
					<FormItem label='广告类型'>
						<Input type="text" v-model="addmess.Remarks" placeholder="请填写广告类型...">
						</Input>
					</FormItem>
					<FormItem label='附件类型'>
						<Select v-model="addmess.active">
			                <Option value="img">图片</Option>
			                <Option value="video">视频</Option>
			            </Select>
					</FormItem>
					<FormItem v-if="addmess.active==='img'">
						<div>
							添加图片
						</div>
					  	<addlistfileImg></addlistfileImg>
					</FormItem>
					<FormItem v-else-if="addmess.active==='video'">
						<div>
							添加视频
						</div>
					  	<addlistfileVideo></addlistfileVideo>
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
	import addlistfileImg from './addlistfileImg.vue'
	import addlistfileVideo from './addlistfileVideo.vue'
	export default {
		name: '',
		components: { 
        	addlistfileImg,
        	addlistfileVideo
        },
		data() {
			return {
				showModal: true,
				mesF: false,
				addmess: {
					RoleName: '',
					RoleType: '',
					Remarks: '',
					active:'img'
				}
			}
		},
		created(){
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