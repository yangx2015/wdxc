<style lang="less">
	@import "../../styles/common.less";
	@import "./pageIndex";
</style>
<template>
	<div class="box">
		<div class="header">
			<div class="box-row">
				<!--<div class="titLeft" @click="MyCenter()">-->
					<!--<Icon type="person" color="#949494" size='26'></Icon>-->
				<!--</div>-->
				<div class="titCenter body-O"
             style="text-align: center;font-size: 16px;font-weight: 600;color:#a8a8a8">
					 <!--<Input v-model="titFind" size="small" placeholder="请输入站点名称" ></Input>-->
				    我的单据
        </div>
				<!--<div class="titRight" @click="feedback()">-->
					<!--<Icon type="ios-compose" color="#949494" size='26'></Icon>-->
				<!--</div>-->
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
				      <tab-item selected @on-item-click="onItemClick">今日单据</tab-item>
				      <tab-item @on-item-click="onItemClick">待确认</tab-item>
              <tab-item @on-item-click="onItemClick">历史单据</tab-item>
				    </tab>
				</div>
				<div v-if="school==0" class="body" style="position: relative;padding: 5px 12px">
					<!--<div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%,-150%);font-size: 28px;">-->
						<!--今日单据-->
					<!--</div>-->
          <Card style="width:100%">
            <p slot="title">
              <Icon type="ios-film-outline"></Icon>
              信息工程学院-小名
            </p>
              <div>
                  <p>
                    <Icon type="ios-telephone"></Icon>
                    <a>123456789000</a>
                  </p>
              </div>
          </Card>
				</div>
				<div v-else-if="school==1" class="body">
          <div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%,-150%);font-size: 28px;">
            待确认
          </div>
        </div>
        <div v-else-if="school==2" class="body">
          <div style="position: absolute;top: 50%;left: 50%;transform: translate(-50%,-150%);font-size: 28px;">
            历史单据
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
				school:0,
				titFind:'',
				imglistV:0,
				imglist: [{
				  img: 'https://static.vux.li/demo/1.jpg',
				}, {
				  img: 'https://static.vux.li/demo/2.jpg',
				}, {
				  img: 'https://static.vux.li/demo/3.jpg',
				}],
				lineList:[]
			}
		},
		created(){
			// this.getSwiperMess()
			// this.getLineMess()
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
		  			if(res.code ==200){
		  				console.log('图片数据',res)
//		  				v.$Message.success('This is a success tip');
		  				v.imglist = res.result
		  			}else{
		  				v.$Message.success('图片获取失败');
		  			}
		  		}).catch((error) =>{
	        		console.log('出错了',error)
	        	})
		  	},
		  	getLineMess(){
		  		var v = this
		  		this.$http.post(configApi.LINE.QUERTY).then((res)=>{
		  			if(res.code ==200){
		  				console.log('线路数据',res)
//		  				v.$Message.success('This is a success tip');
		  				v.lineList = res.result
		  			}else{
		  				v.$Message.success('线路数据获取失败');
		  			}
		  		}).catch((error) =>{
	        		console.log('出错了',error)
	        	})
		  	},
			demo01_onIndexChange (index) {
//				console.log(index)
		    },
		    lineMess(id){
		    	this.$router.push('/lineMess?lineID='+id)
		    },
		    onItemClick(index){
		  	  console.log(index)
          this.school = index
		    }
		}
	}
</script>

<style>
</style>
