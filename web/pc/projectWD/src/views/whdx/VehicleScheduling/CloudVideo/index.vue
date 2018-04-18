<style lang="less">
	@import '../../../../styles/common.less';
	.CloudVideo{
		background-color: #fff;
		.videoSty{
			border-bottom:solid 1px #dedede;
		}
		.inputTit{
			margin-bottom: 5px;
		}
	}
	.videoInfo{
		text-align: center;
		padding: 16px;
		margin-bottom: 16px;
	}
</style>
<template>
	<div class="CloudVideo">
		<Card>
			<div class="tit">
				<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
					<div style="height: 45px;line-height: 45px;">
						<div class="margin-top-10 box-row">
							<div class="titmess">
								<span>云视频库</span>
							</div>
							<div class="body-r-1 inputSty">
								<DatePicker v-model="cjsjInRange"
											format="yyyy-MM-dd"
											type="daterange"
											placement="bottom-end"
											placeholder="请输时间"
											@on-keyup.enter="findMessList()"
											style="width: 220px"></DatePicker>
								<Input v-model="findMess.cphLike"
									   placeholder="请输入车辆编号"
									   style="width: 200px"
									   @on-keyup.enter="findMessList()"></Input>
							</div>
							<div class="butevent">
								<Button type="primary" @click="findMessList()">
									<Icon type="search"></Icon>
									<!--查询-->
								</Button>
							</div>
						</div>
					</div>
				</Row>
			</div>
			<div class="body">
				<div class="box-row">

					<div class="body-F">
						<div class="box-row-list">
							<div class="bodyC videoSty" v-for="item in videoList">
								<video class="videoFile"
									   :src="videoPath+item.dz"
									   controls="controls"></video>
								<div class="videoInfo">
									<span>{{item.cph}} [{{item.cjsj}}]</span>
								</div>
							</div>
						</div>
					</div>
					<div class="" style="width:160px;height:100%;overflow:auto;padding-top:8px;margin: 0 3px;">
						<Menu theme="dark" :active-name="activeName" @on-select="MenuClick" style="width: 100%;">
							<MenuItem v-for="(item,index) in carList" :name="item.cph">
								<Icon type="model-s"></Icon>
								{{item.cph}}
							</MenuItem>
						</Menu>
					</div>
				</div>
			</div>
			<Row class="margin-top-10 pageSty">
				<Page
						:total=pageTotal
						:current=page.pageNum
						:page-size=page.pageSize
						show-total
						show-elevator
						@on-change='pageChange'></Page>
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
		data(){
			return {
                videoPath :configApi.VIDEO_PATH,
				activeName:0,
				cjsjInRange:[],
				carList:[],
                pageTotal: 1,
                page: {
                    pageNum: 1,
                    pageSize: 5
                },
				videoList:[],
                findMess:{
                    cjsjInRange:'',
                    cphLike:'',
                    pageNum: 1,
                    pageSize: 12
				}
			}
		},
		created(){
			this.getmess()
			this.getCarList();
		},
		methods:{
			getmess(){
                if (this.cjsjInRange.length != 0 && this.cjsjInRange[0] != '' && this.cjsjInRange[1] != ''){
                    this.findMess.cjsjInRange = this.getdateParaD(this.cjsjInRange[0])+","+this.getdateParaD(this.cjsjInRange[1]);
                }else{
                    this.findMess.cjsjInRange = '';
                }
				var v = this
				this.$http.get(configApi.CLOUD.QUERY,{params:v.findMess}).then((res) =>{
					this.videoList = res.page.list
                    v.pageTotal = res.page.total
					for (let r of this.videoList){
					    if (r.dz){
                            r.dz = r.dz.substring(9);
                        }
					}
					v.SpinShow = false;
				})
			},
			getCarList(){
                var v = this
                this.$http.get(configApi.CLGL.GET_ORG_CAR_LIST).then((res) =>{
                    this.carList = res.result
                    v.SpinShow = false;
                })
			},
			MenuClick(name){
                this.findMess.cphLike = name;
                this.getmess()
			},
			findMessList(){
                this.getmess();
			},
            pageChange(event){
                var v = this
                v.findMess.pageNum = event
                this.getmess()
            },
		}
	}
</script>

<style>

</style>