<style lang="less">
	@import '../../../../styles/common.less';
</style>
<template>
	<div>
		<div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
			<Spin fix>
				<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
				<div style="font-size: 30px;">数据加载中请稍后</div>
			</Spin>
		</div>
		<Card>
			<Row>
				<Form
						ref="formItem"
						:model="formItem"
						:rules="ruleInline"
						:label-width="100"
						:styles="{top: '20px'}">
					<Row>
						<Col span="8">
							<Select v-model="formItem.cphLike" filterable>
								<Option  v-for="(item,index) in carList"
										 :value="item.zdbh" :key="item.zdbh">{{item.cph}}</Option>
							</Select>
						</Col>
						<Col span="8">
							<FormItem label='开始时间'>
								<DatePicker v-model="formItem.startTime" type="datetime" placeholder="请输时间" style="width: 220px"></DatePicker>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label='时长' prop="duration">
								<Input v-model="formItem.duration" :number="true" placeholder="请输时长" style="width: 220px"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label='确定'>
								<Button type="ghost" @click="confirm">确定</Button>
							</FormItem>
						</Col>
					</Row>
				</Form>
			</Row>
		</Card>
	</div>
</template>

<script>
	import configApi from '@/axios/config.js'
    import mixins from '@/mixins'
    
	export default{
		name:'',
        mixins: [mixins],
        components: {
      	},
		data(){
			return {
                SpinShow:false,
                videoPath :configApi.VIDEO_PATH,
				cjsjInRange:[],
                ruleInline: {
                    xm: [
                        { required: true, message: '请输入驾驶员名', trigger: 'blur' }
                    ],
                    duration: [
                        { required: true, message: '请输入驾驶员名', trigger: 'blur' }
                    ],
                },
                formItem:{
                    startTime:'',
					endTime:'',
                    zdbh:''
				},
                carList:[],
				count:0,
				bj:'',
			}
		},
		created(){
			this.getCarList();
		},
		methods:{
            getCarList(){
                var v = this
                this.$http.get(configApi.CLGL.GET_ORG_CAR_LIST).then((res) =>{
                    this.carList = res.result
                    v.SpinShow = false;
                })
            },
            confirm(){
                this.mergeVideo("0-10");
			},
            mergeVideo(param){
                let params = {
                    deviceId:this.zdbh,
                    cmdType:13,
                    cmdParams:param,
                    startTime:this.formItem.startTime,
                    endTime  :this.formItem.endTime,
                }
                this.SpinShow = true;
                this.$http.post(configApi.CLJK.SEND_CONTROLL,params).then((res) =>{
                    if (res.code === 200){
                        this.$Message.success("发送成功!")
						this.check();
                    }else{
                        this.SpinShow = false;
                        this.$Message.error(res.message)
                    }
                })
            },
			check(){
                let params = {
                    bj:this.bj,
                }
                this.$http.post(configApi.CLOUD.QUERY,params).then((res) =>{
                    if (res.code === 200){
                        this.SpinShow = false;
                        this.$Message.success("发送成功!")
                    }else{
                        clearTimeout();
                        setTimeout(()=>{
                            this.check()
						},5000)
					}
                })
			}
		}
	}
</script>

<style>

</style>