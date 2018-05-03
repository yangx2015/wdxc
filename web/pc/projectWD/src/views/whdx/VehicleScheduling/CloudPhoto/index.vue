<style lang="less">
	@import '../../../../styles/common.less';
	.CloudVideo{
		background-color: #fff;
		.videoSty{
			border-bottom:solid 1px #dedede;
			position:relative;
			.videoBF{
				cursor: pointer;
				position: absolute;
				top: 0;
				left: 0;
				right: 0;
				bottom: 0;
				background:rgba(0,0,0,0.5);
				z-index: 100;
			}
			.icon{
				position: absolute;
				top: 50%;
				left: 50%;
				transform:translate(-50%,-50%);
				z-index: 101;
			}
			.VideoTit{
				position: absolute;
				top: 3px;
				left: 0;
				z-index: 102;
				color: #fff;
				background: rgba(0,0,0,0.5);
				padding: 3px 5px;
				width: 100%;
				
			}
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
	<div class="CloudVideo boxbackborder box">
			<div class="tit">
				<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
					<span class="tabPageTit">
	    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
	    			</span>
					<div style="height: 45px;line-height: 45px;">
						<div class="margin-top-10 box-row">
							<div class="titmess">
								<span>云图片库</span>
							</div>
							<div class="body-r-1 inputSty">
								<DatePicker v-model="cjsjInRange"
											format="yyyy-MM-dd"
											type="daterange"
											placement="bottom-end"
											placeholder="请输时间"
											@on-keyup.enter="findMessList()"
											style="width: 220px"></DatePicker>
							   	<Select v-model="findMess.cphLike" filterable
							   		@on-change="findMessList()"
							   		style="width: 200px;text-align: left;">
									<Option value="" key="">全部</Option>
					                <Option  v-for="(item,index) in carList" 
					                	:value="item.cph" :key="index">{{item.cph}}</Option>
					            </Select>
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
			<div class="body" style="border: 1px solid #dddee1">
				<div class="box-row-list">
					<div class="bodyC videoSty" 
						style="min-height: 140px;"
						v-for="(item,index) in videoList">
                        <img :src="videoPath+'/test/'+item.url" style="width: 100%;">
					    <div class="VideoTit">
					    	{{item.cph}} [{{item.cjsj}}]
					    	<div style="float: right;cursor: pointer;">
						    	<span v-show="item.video" @click="videoF(item.video,item,index)">
						    		关闭
						    	</span>
						    	<span style="color: #ff9900;" @click="videoColse(item,index)">
						    		移除
						    	</span>
					    	</div>
					    </div>
					</div>
				</div>
			</div>
			<div class="margin-top-10 pageSty" style="height: 60px;">
				<Page
						:total=pageTotal
						:current=page.pageNum
						:page-size=page.pageSize
						show-total
						show-elevator
						@on-change='pageChange'></Page>
			</div>
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
                videoPath :configApi.VIDEO_PATH,
				activeName:0,
				cjsjInRange:[],
				carList:[],
                pageTotal: 1,
                page: {
                    pageNum: 1,
                    pageSize:12
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
			this.getCarList();
		},
		methods:{
			videoS(type,item,index){
				this.videoList[index].video = true
			},
			videoF(type,item,index){
				this.videoList[index].video = false
			},
			videoColse(item,index){
				var v = this
				swal({
			        title: "是否删除数据?",
			        text: "",
			        icon: "warning",
			        buttons:['取消','确认'],
			    }).then((willDelete) => {
		            if (willDelete) {
		                this.$http.post(configApi.CLOUD.DELE+'/'+item.id).then((res) =>{
							if(res.code==200){
								v.$Message.success(res.message);
							}else{
								v.$Message.error(res.message);
							}
							v.getmess()
						}).catch((error)=>{
							v.$Message.error('出错了！！！');
						})
		            }
		        });
			},
			getmess(){
                if (this.cjsjInRange.length != 0 && this.cjsjInRange[0] != '' && this.cjsjInRange[1] != ''){
                    this.findMess.cjsjInRange = this.getdateParaD(this.cjsjInRange[0])+","+this.getdateParaD(this.cjsjInRange[1]);
                }else{
                    this.findMess.cjsjInRange = '';
                }
				var v = this
                v.findMess.wjmEndwith = '.jpg';
				this.$http.get(configApi.CLOUD.QUERY,{params:v.findMess}).then((res) =>{
            	    v.pageTotal = res.page.total
					for (let r of res.page.list){
					    if (r.url){
							r.video = false
                            r.imgdz = r.url.replace('video','cache');
                            r.imgdz = r.imgdz.replace('mp4','jpg')
                        }
					}
					this.videoList = res.page.list
                    v.SpinShow = false;
				})
			},
			getCarList(){
                var v = this
                this.$http.get(configApi.CLGL.GET_ORG_CAR_LIST).then((res) =>{
                    this.carList = res.result
                    this.getmess();
                })
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