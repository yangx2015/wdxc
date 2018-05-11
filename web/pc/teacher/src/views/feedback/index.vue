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
	<div class="box feedback" style="height: 100%;">
    <div class="header">
      <div class="box-row">
        <div class="titLeft" @click="back()">
          <i class="iconfont icon-left"></i>
        </div>
        <div class="titCenter body-O" style="text-align: center;">
            意见反馈
        </div>
        <div class="titLeft">
        </div>
      </div>
    </div>
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
                  <textarea></textarea>
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
</template>

<script>
  import {TabBar} from 'mand-mobile'
	export default{
		name:'',
		components: {
      [TabBar.name]: TabBar,
		},
    data(){
		  return{
        titles: ['建议', '投诉'],
      }
    },
    methods:{
      back(){
        this.$router.push({
          name:'Home'
        })
      },
      tabbarC(n,o){//tab切换
          // alert('约车')
      },
    }
	}
</script>
