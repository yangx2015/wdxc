<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' 
			:mask-closable="mesF" title="新建广告">
			<div style="overflow: auto;height: 440px;">
				<Form
						ref="formItem"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem label='广告标题'>
								<Input type="text" v-model="formItem.hdbt" placeholder="请填写广告标题...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='内容'>
								<Input type="text" v-model="formItem.RoleType" placeholder="请填写广告内容...">
								</Input>
							</FormItem>
						</Col>
					</Row>

					<Row>
						<Col span="12">
							<FormItem label='广告类型'>
								<Input type="text" v-model="formItem.hdlx" placeholder="请填写广告类型...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='附件类型'>
								<Select v-model="formItem.active">
									<Option value="img">图片</Option>
									<Option value="video">视频</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>

					<Row>
						<Col span="24">
							<FormItem v-if="formItem.active==='img'">
								<div>
									添加图片
								</div>
								<addlistfileImg></addlistfileImg>
							</FormItem>
							<FormItem v-else-if="formItem.active==='video'">
								<div>
									添加视频
								</div>
								<addlistfileVideo></addlistfileVideo>
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
	import addlistfileImg from './addlistfileImg.vue'
	import addlistfileVideo from './addlistfileVideo.vue'
    import configApi from '@/axios/config.js'
	export default {
		name: '',
		components: { 
        	addlistfileImg,
        	addlistfileVideo
        },
		data() {
			return {
			    edie:false,
				showModal: true,
				mesF: false,
				formItem: {
					active:'img'
				},
                ruleInline:{}
			}
		},
		created(){
            if (this.$parent.choosedRow){
                this.edit = true;
                this.formItem = this.$parent.choosedRow;
            }else{
                if (this.$parent.jgdm){
                    this.formItem.fjgdm = this.$parent.jgdm;
                }else{
                    console.log("请选择父节点")
                }
            }
		},
		methods: {
            save(){
                var v = this
                let url = configApi.ADVERTISING.ADD;
                if (this.edit){
                    url = configApi.ADVERTISING.CHANGE;
                }
                this.$http.post(url,this.formItem).then((res) =>{
                    if(res.code===200){
                        v.$Message.success(res.message);
                    }else{
                        v.$Message.error(res.message);
                    }
                    v.$parent.componentName = ''
                    v.$parent.getTree()
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