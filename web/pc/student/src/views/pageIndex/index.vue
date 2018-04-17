<style lang="less">
	@import "../../styles/common.less";
	@import "./pageIndex";
</style>
<template>
	<div class="box">
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
				<div class="body">
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
			
		},
		methods:{
			demo01_onIndexChange (index) {
//				console.log(index)
		    },
		    lineMess(){
		    	this.$router.push({
		    		name:'lineMess'
		    	})
		    },
		    onItemClick(index){
		    }
		}
	}
</script>

<style>
</style>