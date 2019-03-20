<style lang="less">
  @import "../../styles/common.less";
  .textarea{
      background: rgba(227,227,227,0.5);
      padding: 1rem;
      height: 60%;
      margin-top: 10%;
      border:dashed 0.03rem #b3b3b3;
      textarea {
          resize: none;
          width: 100%;
          height: 100%;
          padding: 0;
          outline: none;
          .text {

          }
          .text::-webkit-input-placeholder {
            color: red;
          }
          .text::-moz-placeholder {
            color: red;
          }
      }
  }
  .header{
      .vux-header{
          background-color: #FFF;
          .vux-header-left{
            color: #8d8d8d;
          }
          .vux-header-title{
            color: #8d8d8d;
            font-weight: 600;
          }
      }
  }
  .button{
    padding: 0.4rem 0.4rem;
        button{
          height: 50px;
          width: 100%;
          background-color: #ff8a00;
          border: none;
          color: #fff;
          font-size: 18px;
          font-weight: 500;
          /*padding-bottom: 40px;*/
        }
  }
  .titbar {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
  }
  .select-control-row{
    padding: 8px 0;
  }
</style>
<template>
	<div class="box" style="">
		  <div class="titbar">
	  		<!--<x-header-->
	  			<!--:left-options="{showBack: true}">-->
	  			<!--<span class="tit">反馈</span>-->
	  			<!--</x-header>-->
        <mu-appbar style="width: 100%;text-align: center" color="#fb8c00">
          <mu-button icon slot="left" @click="goback">
            <mu-icon value="chevron_left"></mu-icon>
          </mu-button>
          意见反馈

          <mu-button flat slot="right" @click="goFeedbackHis">记录</mu-button>
        </mu-appbar>
	  	</div>
      <div class="body" style="padding: 1rem">
        <!--<div class="textarea">-->
                      <!--<textarea class="text"-->
                                <!--:placeholder="'请输入您的'+placeholder+'信息'"-->
                      <!--&gt;</textarea>-->
        <!--</div>-->
        <div style="padding-top: 50px">
          <mu-tabs @change="getyjlx" :value.sync="active" inverse color="secondary" text-color="rgba(0, 0, 0, .54)"  center>
            <mu-tab>意见</mu-tab>
            <mu-tab>反馈</mu-tab>
            <mu-tab>投诉</mu-tab>
          </mu-tabs>
        </div>
        <!--<group>-->
          <!--<x-input-->
            <!--title="姓名  "-->
            <!--:min="2"-->
            <!--:max="4"-->
            <!--v-model="form.lxrxm"-->
            <!--:is-type="rulName"-->
            <!--placeholder="请填写姓名"></x-input>-->
        <!--</group>-->
        <!--<group>-->
          <!--<x-input-->
              <!--title="手机号码"-->
              <!--mask="999 9999 9999"-->
              <!--v-model="form.lxfs" :max="13"-->
              <!--placeholder="请填写联系方式"-->
              <!--:is-type="rul"></x-input>-->
        <!--</group>-->
        <group>
          <!--<x-input :placeholder="placeholder" ></x-input>-->
          <x-textarea
            title="内容"
            v-model="form.nr"
            :max="200" name="description" placeholder="请输入您的内容"></x-textarea>
        </group>
      </div>
      <div class="button" @click="submit">
        <mu-container class="button-wrapper">
          <mu-flex justify-content="center" align-items="center">
            <mu-button round color="success">提  交</mu-button>
          </mu-flex>
          <!--<mu-flex justify-content="center" align-items="center">-->
            <!--<mu-button full-width color="primary">full width button</mu-button>-->
          <!--</mu-flex>-->
        </mu-container>
      </div>
	</div>
</template>

<script>
	import {XHeader,XInput,Group,XTextarea,AlertModule } from 'vux'

	export default{
		name:'',
		components: {
		    XHeader,Group,XInput,XTextarea,AlertModule
		},
    data(){
		  return{
        active:0,
        form:{
          lxfs:'',
          nr:'',
          lxrxm:'',
          yjlx:'00'//00意见 10反馈 20投诉
        },
        // rulName: function (value) {
        //   return {
        //     valid: value.length==2|| value.length==3|| value.length==4,
        //     msg: '请输入你的名字'
        //   }
        // },
        // rul: function (value) {
        //   return {
        //     valid: value.length ==13,
        //     msg: '请输入正确的电话号码！'
        //   }
        // },
        placeholder:''
      }
    },
    methods:{
      goback(){
        this.$router.back()
      },
      goFeedbackHis(){
        this.$router.push({name:'feedbackHis'})
      },
      getyjlx(){
        if(this.active ===0){
          this.form.yjlx = '00'
          console.log(this.form.yjlx);
        }
        if(this.active === 1){
          this.form.yjlx = '10'
          console.log(this.form.yjlx);
        }
        if(this.active === 2){
          this.form.yjlx = '20'
          console.log(this.form.yjlx);
        }
      },
      submit(){
        if(this.form.nr.length!=0){
          this.sub()
        }else{
          alert('请将信息填写完整')
        }
      },
      sub(){
          var v = this
          v.$http.post(this.apis.FEEDBACK.QUERTY, this.form).then((res) =>{
            console.log('*****',res)
            if(res.code){
              v.showPlugin
            }
          }).catch((error)=>{

          })
      },
      showPlugin () {
        var v = this
        AlertModule.show({
          title: '提交成功',
          content: '信息提交成功!',
          onShow () {
            console.log('Plugin: I\'m showing')
          },
          onHide () {
            console.log('Plugin: I\'m hiding now')
            v.$router.push({
              name:'center'
            })
          }
        })
        setTimeout(function () {
          AlertModule.hide()
        }, 3000)
      },
    }
	}
</script>

<style>
</style>
