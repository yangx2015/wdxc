<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' 
			:mask-closable="mesF" title="活动编辑">
			<div style="overflow: auto;height: 440px;">
				<Form
						ref="formItem"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem label='活动标题'>
								<Input type="text" v-model="formItem.hdbt" placeholder="请填写活动标题...">
								</Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='URL'>
								<Input type="text" v-model="formItem.url" placeholder="请填写URL...">
								</Input>
							</FormItem>
						</Col>
					</Row>

					<Row>
						<Col span="12">
							<FormItem label='活动时间'>
								<DatePicker v-model="cjsjInRange" 
									format="yyyy-MM-dd" type="daterange" 
									placement="bottom-end" 
									placeholder="请输活动时间" 
									style="width: 100%"></DatePicker>
								
								<!--<Input type="text" v-model="formItem.hdlx" placeholder="请填写广告类型...">
								</Input>-->
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='附件类型'>
								<Select v-model="formItem.hdlx">
									<Option value="00">图片</Option>
									<Option value="01">视频</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>

					<Row>
						<Col span="24">
							<FormItem v-if="formItem.hdlx==='00'">
								<div>
									添加图片
								</div>
								<addlistfileImg
									@addImg="addImg"
									@removeFile = "removeFile"
									:urlList = "mess.filePaths"
								></addlistfileImg>
							</FormItem>
							<FormItem v-else-if="formItem.hdlx==='01'">
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
	import mixins from '@/mixins'
	
	import addlistfileImg from './addlistfileImg.vue'
	import addlistfileVideo from './addlistfileVideo.vue'
    import configApi from '@/axios/config.js'
	export default {
		name: '',
		mixins: [mixins],
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
					hdlx:'00',
					kssj:'',
					jssj:''
				},
                ruleInline:{},
                cjsjInRange:[]
			}
		},
		props:{
			mess:{
				type:Object,
				default:{}
			}
		},
		watch: {
			cjsjInRange:function(newQuestion, oldQuestion){
				this.formItem.kssj = this.getdateParaD(newQuestion[0])
				this.formItem.jssj = this.getdateParaD(newQuestion[1])
				console.log(this.formItem)
			},
		},
		created(){
			this.formItem = this.mess
			this.cjsjInRange = [this.mess.kssj,this.mess.jssj]
			console.log('时间转换',this.cjsjInRange)
		},
		methods: {
            addImg(path){
                this.formItem.filePaths += path+",";
			},
			removeFile(url){
                this.formItem.filePaths = this.formItem.filePaths.split(',').splice(url+',',1).join(',')
			},
            save(){
                var v = this
                let url = configApi.ADVERTISING.CHANGE;
                this.$http.post(url,this.formItem).then((res) =>{
                    if(res.code===200){
                        v.$Message.success(res.message);
                    }else{
                        v.$Message.error(res.message);
                    }
                    v.$parent.componentName = ''
                    v.$parent.getmess()
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