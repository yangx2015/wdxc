<style lang="less">
  @import '../../styles/box.less';
  @import "./feedback.less";
  .md-tab-bar .md-tab-title.active{
    color:#ff8a00
  }
  .md-tab-bar .ink-bar.animate{
    background-color: #ff8a00;
  }
</style>
<template>
  <div class="feedbackF">
      <div class="box feedback" style="height: 100%;">
        <head-tit tit="意见反馈"></head-tit>
        <div class="body">
            <div class="box">
                <div>
                    <md-tab-bar
                      @indexChanged="tabbarC"
                      :titles="titles"
                    ></md-tab-bar>
                </div>
                <div class="body" style="padding: 0.5rem">
                  <div class="textarea">
                      <textarea class="text"
                                :placeholder="'请输入您的'+placeholder+'信息'"
                      ></textarea>
                  </div>
                </div>
            </div>
        </div>
        <div class="button">
          <button>
            提    交
          </button>
        </div>
      </div>
  </div>
</template>

<script>
  import {TabBar} from 'mand-mobile'
  import headTit from "@/views/comp/headTit"
	export default{
		name:'',
		components: {
      [TabBar.name]: TabBar,
      headTit,
		},
    data(){
		  return{
        titles: ['建议', '投诉'],
        placeholder:'建议'
      }
    },
    methods:{
      back(){
        this.$router.push({
          name:'Home'
        })
      },
      tabbarC(n,o){//tab切换
            this.placeholder = this.titles[n]
          // alert('约车')
      },
    }
	}
</script>
