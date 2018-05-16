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
			<div class="box">
				<div class="">
					<tab>
				      <tab-item selected @on-item-click="onItemClick">待确认</tab-item>
              <tab-item @on-item-click="onItemClick">历史单据</tab-item>
				    </tab>
				</div>
				<div v-if="school==0" class="body"  style="position: relative;padding: 5px 12px">
          <ok-list></ok-list>
        </div>
        <div v-else-if="school==1" class="body"  style="padding: 5px 12px">
          <div v-for="item in hisList">
              <Card style="width:100%;margin-top: 6px">
                <p slot="title">
                  <Icon type="ios-film-outline"></Icon>
                  {{item.jgmc}}-{{item.ck}}
                </p>
                <div style="font-size: 16px">
                  <p>
                    <Icon type="ios-clock"></Icon>
                    {{item.yysj}}
                  </p>
                  <p>
                    <Icon type="ios-telephone"></Icon>
                    <a>{{item.cklxdh}}</a>
                  </p>
                  <p>
                    <Icon type="ios-location" color="#15b740"></Icon>
                    {{item.hcdz}}
                  </p>
                  <p><Icon type="arrow-down-c"></Icon></p>
                  <p>
                    <Icon type="ios-location" color="#ff9b00"></Icon>
                    {{item.mdd}}
                  </p>
                </div>
                <div class="box-row"
                     style="text-align: center">
                    <div class="body-O">
                        <div>
                          里程:
                        </div>
                        <div>
                          {{item.lc}}公里
                        </div>
                    </div>
                    <div class="body-O">
                        <div>
                          单价:
                        </div>
                        <div>
                          {{item.dj}}元/公里
                        </div>
                    </div>
                    <div class="body-O">
                      <div>
                        过桥费:
                      </div>
                      <div>
                        {{item.gqf}}元
                      </div>
                    </div>
                    <div class="body-O">
                      <div>
                        路停费:
                      </div>
                      <div>
                        {{item.glf}}元
                      </div>
                    </div>
                </div>
              </Card>
          </div>
        </div>
			</div>
		</div>
	</div>
</template>

<script>
	import { Swiper, SwiperItem, Cell, XButton, Tab, TabItem } from 'vux'
  import Cookies from 'js-cookie';
  import okList from './comp/okList'
	export default{
		name:'',
		components: {
		    Swiper,
		    SwiperItem,
		    Cell,
		    XButton, Tab, TabItem,
        okList
		 },
		data(){
			return{
				school:0,
				titFind:'',
				imglistV:0,
				imglist: [],
				lineList:[],
        hisList:[]
			}
		},
		created(){
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
          this.school = index
          if(index==1){
              this.His()
          }
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
