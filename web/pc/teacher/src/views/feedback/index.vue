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
                                v-model="form.nr"
                                :placeholder="'请输入您的'+placeholder+'信息'"
                      ></textarea>
                  </div>
                </div>
            </div>
        </div>
        <div class="button" @click="feedbackSave">
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
        placeholder:'建议',
        form:{
            yhId:'2334232',
            yjlx:'00',//意见类型
            lxrxm:'宋林殊',//联系人姓名
            lxfs:'38381438',//联系方式
            nr:'',//内容
        }
      }
    },
    created(){
      this.feedbackList()
    },
    methods:{
      back(){
        this.$router.push({
          name:'Home'
        })
      },
      tabbarC(n,o){//tab切换****10投诉  00改建/建议
        if(n==0){
          this.form.yjlx = '00'
        }else if(n==1){
          this.form.yjlx = '10'
        }
      },
      feedbackSave(){
        var v = this
        v.$http.post(this.apis.FEEDBACK.QUERTY, this.form).then((res) =>{
            console.log('*****',res)
        }).catch((error)=>{

        })
      },
      feedbackList(){
        var v = this
        v.$http.post(this.apis.FEEDBACK.LIST, {yhId:'2334232',yjlx:'',yjId:''}).then((res) =>{
          console.log('*****',res)
        }).catch((error)=>{

        })
      }
    }
	}
</script>
