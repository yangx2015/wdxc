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
							<FormItem label='开始时间'>
								<Select v-model="formItem.zdbh" filterable>
									<Option  v-for="(item,index) in carList"
											 :value="item.zdbh" :key="item.zdbh">{{item.cph}}</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="8">
							<FormItem label='开始时间'>
								<DatePicker v-model="formItem.startTime" type="datetime" placeholder="请输时间" style="width: 220px"></DatePicker>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="8">
							<FormItem label='时长' prop="duration">
								<Input v-model="formItem.duration" :number="true" placeholder="请输时长" style="width: 220px"></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="8">
							<Button type="ghost" @click="confirm">确定</Button>
						</Col>
					</Row>
				</Form>
			</Row>
			<Row>
				<video v-if="videoUrl != null"
						style="width: 100%;"
						:src="videoPath+videoUrl"
						autoplay="autoplay"
						controls="controls"></video>
			</Row>
		</Card>
	</div>
</template>

<script>

    Date.prototype.format = function(format)
    {
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(),    //day
            "h+" : this.getHours(),   //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length==1 ? o[k] :
                    ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }
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
                    startTime: [
                        { required: true, message: '请输入开始时间', trigger: 'blur' }
                    ],
                },
                formItem:{
                    startTime:'',
					endTime:'',
                    zdbh:'',
                    duration:10
				},
                carList:[],
				count:0,
				bj:'',
                videoUrl:null
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
                var v = this
                this.$refs['formItem'].validate((valid) => {
                    if (valid) {
                        if (this.formItem.duration > 30){
                            this.$Message.error('时长不能大于30秒')
                            return;
                        }
                        this.mergeVideo("1-10");
                    } else {
                        v.$Message.error('请将信息填写完整!');
                    }
                })
			},
            mergeVideo(param){
                this.videoUrl = null;
                let endTime = new Date(this.formItem.startTime.getTime());
                endTime.setSeconds(endTime.getSeconds() + this.formItem.duration);
                let params = {
                    deviceId:this.formItem.zdbh,
                    cmdType:11,
                    cmdParams:param,
                    startTime:this.formItem.startTime.format('yyyy-MM-dd hh:mm:ss'),
                    endTime  :endTime.format('yyyy-MM-dd hh:mm:ss'),
                }
                this.SpinShow = true;
                this.$http.post(configApi.CLJK.SEND_CONTROLL,params).then((res) =>{
                    if (res.code === 200){
                        this.$Message.success("发送成功!")
						this.bj = res.result;
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
                        if (res.page.total > 0){
                            this.SpinShow = false;
                            this.$Message.success("拍摄成功!")
							this.videoUrl = res.page.list[0].url;
						}else{
                            clearTimeout();
                            setTimeout(()=>{
                                this.check()
                            },5000)
						}
                    }else{
                        this.$Message.error(res.message);
					}
                })
			}
		}
	}
</script>

<style>

</style>