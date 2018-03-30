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
</style>
<template>
	<div class="box CloudVideo boxbackborder">
		<div class="tit">
			<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
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
							<Input v-model="findCar" 
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
								:src="'http://192.168.3.177:8090/'+item.dz"
								controls="controls"></video>
						</div>
					</div>
				</div>
				<div class="" style="width:160px;height:100%;overflow:auto;padding-top:8px;margin: 0 3px;">
							<Menu theme="dark" :active-name="activeName" @on-select="MenuClick" style="width: 100%;">
					            <MenuItem v-for="(item,index) in videoList" :name="index">
					                <Icon type="model-s"></Icon>
				                		鄂A12345
					            </MenuItem>
						    </Menu>
				</div>
			</div>
		</div>

	</div>
</template>

<script>
	import configApi from '@/axios/config.js'
	export default{
		name:'',
		data(){
			return {
				activeName:0,
				findCar:'',
				cjsjInRange:[],
				videoList:[],
			}
		},
		created(){
			this.getmess()
		},
		methods:{
			getmess(){
				var v = this
				this.$http.get(configApi.CLOUD.QUERY).then((res) =>{
					console.log('云视频数据',res)
					this.videoList = res.page.list
					v.SpinShow = false;
				})
			},
			MenuClick(name){
				alert(name)
			},
			findMessList(){
				
			}
		}
	}
</script>

<style>

</style>