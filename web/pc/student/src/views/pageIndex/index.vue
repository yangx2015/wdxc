<style lang="less">
	@import "../../styles/common.less";
	@import "./pageIndex";
</style>
<template>
	<div class="box">
		<div class="header">
			<div class="box-row">
				<div class="titLeft" @click="MyCenter()">
					<Icon type="person" color="#949494" size='26'></Icon>
				</div>
				<div class="titCenter body-O" style="text-align: center;">
					 <Input v-model="titFind" size="small" placeholder="请输入站点名称" ></Input>
				</div>
				<div class="titRight" @click="feedback()">
					<Icon type="ios-compose" color="#949494" size='26'></Icon>
				</div>
				
			</div>
		</div>
		<div>
			<swiper 
				:list="imglist" 
				v-model="imglistV" 
				:auto="true"
				:loop="true"
				:interval="2000"
				@on-index-change="demo01_onIndexChange"></swiper>
		</div>
		<div class="body bodylist" style="background-color: #fff;">
			<div class="box">
				<div class="">
					<tab>
				      <tab-item selected @on-item-click="onItemClick">校园巴士</tab-item>
				      <tab-item @on-item-click="onItemClick">通勤巴士</tab-item>
				    </tab>
				</div>
				<div v-if="!school" class="body" style="position: relative;">
					<div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%,-150%);font-size: 28px;">
						功能开发中
					</div>
				</div>		
				<div v-else class="body">
					<div class="list">
						<div class="listTiT box-row">
							<div class="body-O">
								<i class="iconfont icon-003lubiao"></i>
								校巴一号站点
							</div>
							<div>
								<i class="iconfont icon-right"></i>
							</div>
						</div>
						<ul>
							<li v-for="item in [,,,,,,]">
								<div class="carNum">
									202
								</div>
								<div class="box-row line lineTop" @click="lineMess">
									<div class="body-O">
										<i class="iconfont icon-jiantouarrow591"></i>
										开往校图书馆
									</div>
									<div>
										<i class="iconfont icon-xinhao"></i>2站
									</div>
								</div>
								<div class="box-row line" @click="lineMess">
									<div class="body-O">
										<i class="iconfont icon-jiantouarrow591"></i>
										第一食堂
									</div>
									<div>
										<i class="iconfont icon-xinhao"></i>4站
									</div>
								</div>
							</li>
						</ul>			
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import { Swiper, SwiperItem, Cell, XButton, Tab, TabItem } from 'vux'
	import configApi from '@/axios/config.js'
	export default{
		name:'',
		components: {
		    Swiper,
		    SwiperItem,
		    Cell,
		    XButton, Tab, TabItem
		 },
		data(){
			return{
				school:true,
				titFind:'',
				imglistV:0,
				imglist: [{
				  img: 'https://static.vux.li/demo/1.jpg',
				}, {
				  img: 'https://static.vux.li/demo/2.jpg',
				}, {
				  img: 'https://static.vux.li/demo/3.jpg',
				}]
			}
		},
		created(){
			this.getSwiperMess()
			console.log('12312313131231')
		},
		methods:{
		  	MyCenter(){//个人中心
		  		this.$router.push({
		    		name:'myCenter'
		    	})
		  	},
		  	feedback(){//反馈信息
		  		this.$router.push({
		    		name:'feedback'
		    	})
		  	},
		  	getSwiperMess(){
		  		var v = this
		  		this.$http.post(configApi.SWIPER.QUERTY).then((res)=>{
		  			console.log('图片数据',res)
		  		}).catch((error) =>{
	        		console.log('出错了',error)
	        	})
		  	},
			demo01_onIndexChange (index) {
//				console.log(index)
		    },
		    lineMess(){
		    	this.$router.push({
		    		name:'lineMess'
		    	})
		    },
		    onItemClick(index){
		    	if(index==1){
		    		this.school = false
		    	}else{
		    		this.school = true
		    	}
		    }
		}
	}
</script>

<style>
</style>