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
		<div style="height: 200px;background-color: #2b85e4">


		</div>
		<div class="body bodylist" style="background-color: #fff;">
			<div class="box" style="background-color: #f3f3f3">
				<div class="">
					<tab>
				      <tab-item :selected="listType==0" @on-item-click="onItemClick">今日接单</tab-item>
              <tab-item :selected="listType==1" @on-item-click="onItemClick">历史接单</tab-item>
				    </tab>
				</div>
				<div class="body"  style="position: relative;padding: 5px 12px">
            <ok-list></ok-list>
        </div>
      </div>
		</div>
	</div>
</template>

<script>
	import { XButton, Tab, TabItem } from 'vux'
  import Cookies from 'js-cookie';
  import okList from './comp/okList'
	export default{
		name:'',
		components: {
		    XButton, Tab, TabItem,
        okList
		 },
		data(){
			return{
        listType:0,
				titFind:'',
				imglistV:0,
				imglist: [],
				lineList:[],
        hisList:[]
			}
		},
		created(){
		  this.listType = this.$store.state.app.listType
      if(Cookies.get('result')) {
          this.His()
      }else{
        this.$router.push({
          name:'login'
        })
      }
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
		    onItemClick(index){
		  	  console.log(index)
          this.$store.commit('listTypeCh',index)
          // if(index==1){
          //     this.His()
          // }
		    },
        His(){//历史订单列表
		  	  this.$http.post(this.apis.MESLIST.QUERTY,{'type':'3'}).then((res)=>{
            if(res.code == 200){
              this.hisList = res.result
            }
          })
        }
		}
	}
</script>

<style>
</style>
