<style lang="less">
  @import '../styles/box.less';
  @import "./hone.less";
</style>
<template>
  <div class="box bodylist">
      <div class="header">
          <div class="box-row">
              <div class="titLeft" @click="MyCenter()">
                  <i class="iconfont icon-wo"></i>
              </div>
              <div class="titCenter body-O" style="text-align: center;">
                  约车
              </div>
              <div class="titRight">
                  <!--<i class="iconfont icon-ybbfeedback"></i>-->
              </div>
          </div>
      </div>
      <div class="body" style="padding:0 0.5rem;background-color: #f4f5f7">
        <div class="formlist">
          <i class="iconfont icon-dian2"
             style="color: #46ac00"></i>
          <md-input-item
            v-model="form.hcdz"
            title=""
            placeholder="你在哪"
            align="left"
          ></md-input-item>
        </div>
        <div class="formlist">
          <i class="iconfont icon-dian2"
             style="color: #ffa817"></i>
          <md-input-item
            v-model="form.mdd"
            title=""
            placeholder="你要去哪"
            align="left"
          ></md-input-item>
        </div>
        <div class="carMes">
          <div class="time box-row">
              <div class="body-O" @click="isDatePickerShow = true">
                  <i class="iconfont icon-shijian"></i>
                  {{datePickerValue}}
              </div>
              <div class="body-O" @click="showPic = true">
                  <i class="iconfont icon-wo"></i>
                  {{lxfs}}
              </div>
          </div>
          <div class="box-row carZws">
            <div class="body-O carimg" v-for="(item,index) in carZws" @click="zws(item.val,index)">
              <img src="/static/car.jpg" alt="">
              <div :style="{color:item.color,fontWeight:'600'}">
                {{item.text}}
              </div>
            </div>
            <!--<div class="body-O carimg" @click="zws(7)">-->
              <!--<img src="/static/car.jpg" alt="">-->
              <!--<div>-->
                <!--7人座-->
              <!--</div>-->
            <!--</div>-->
            <!--<div class="body-O carimg" @click="zws(11)">-->
              <!--<img src="/static/car.jpg" alt="">-->
              <!--<div>-->
                <!--11人座-->
              <!--</div>-->
            <!--</div>-->
          </div>
        </div>

        <div class="button" @click="addList">
          <button>
            提    交
          </button>
        </div>



        <md-landscape v-model="showPic">
          <div>
            <div>
              <md-input-item
                title=""
                type="phone"
                v-model="lxfsF"
                size="large"
                align="center"
                placeholder="请输入联系方式"
              ></md-input-item>
            </div>
            <div class="button"  @click="showPic = false">
              <button>
                确    定
              </button>
            </div>
          </div>
        </md-landscape>
      </div>
      <md-date-picker
        ref="datePicker"
        v-model="isDatePickerShow"
        type="custom"
        title="选择出发时间"
        :custom-types="['MM','dd', 'hh','mm']"
        :default-date="currentDate"
        @confirm="onDatePickerConfirm"
      ></md-date-picker>
      <transition name="bounce">
        <div class="trans" v-if="show">
          <div class="centerMess box">
              <div class="header">
                <div class="box-row">
                  <div class="titCenter body-O" style="text-align:center;line-height: 0.68rem">
                    <i class="iconfont icon-wo"
                        style="font-size: 0.4rem;color: #949494;"
                    ></i>
                    <span style="font-size: 16px">
                      {{User}}
                    </span>
                  </div>
                </div>
              </div>
              <div class="body">
                <myCenter></myCenter>
              </div>
          </div>
          <div class="close" @click="MyCenter">

          </div>
        </div>
      </transition>
  </div>
</template>
<script>
  import {DatePicker,FieldItem,InputItem,Landscape,Button} from 'mand-mobile'
  import  myCenter from './myCenter'
  export default {
    name: 'swiper-demo',
    components: {
      myCenter,
      [Button.name]: Button,
      [Landscape.name]: Landscape,
      [InputItem.name]: InputItem,
      [FieldItem.name]: FieldItem,
      [DatePicker.name]: DatePicker,
    },
    data() {
      return {
        form:{
          hcdz:'',//候车地址
          mdd:'',//目的地
          yysj:'',
          cklxdh:'',
          zws:5
        },
        carZws:[
          {
            text:'5人座',
            val:5,
            color:'#000',
          },{
            text:'7人座',
            val:7,
            color:'#000',
          },{
            text:'11人座',
            val:11,
            color:'#000',
          }
        ],
        lxfsF:'',
        lxfs:'换乘车人',
        showPic:false,
        currentDate: new Date(),
        isDatePickerShow:false,
        datePickerValue:'乘车时间',
        User:this.cok.get('result')? JSON.parse(this.cok.get('result')).name : '123',
        show:false,
        findInput:'',
        lineList:[],
        barNum:this.$store.state.app.barNum,
      }
    },
    watch:{
      lxfsF:function(n,o) {
        if(n){
          this.lxfs = n
          this.form.cklxdh = n
        }else {
          this.lxfs = '换乘车人'
          this.form.lxfs = ''
        }
      }
    },
    created(){
      if(this.cok.get('result')){

      }else {
        this.$router.push({
          name:'login'
        })
      }
    },
    mounted() {
    },
    methods: {
      zws(val,index){
          this.form.zws = val
          this.carZws.forEach((it,vl)=>{
              if(vl == index){
                it.color = '#1faa41'
              }else {
                it.color = '#000'
              }
          })
      },
      addList(){//订单创建
        var v =this
        console.log('***********',this.form)
        v.$http.post(this.apis.DDSAVE.SAVE, this.form).then((res) =>{

        }).catch((error)=>{

        })
      },
      MyCenter(){//个人中心
        this.show = !this.show
      },
      feedback(){//信息反馈
        this.$router.push({
          name: 'feedBack'
        });
      },
      onDatePickerConfirm(c) {
        console.log('-************',c)
        this.datePickerValue = ''
        this.form.yysj = ''
        let a = '-'
        c.forEach((item, index) => {
          if((item.type == "Date")) {
            a = ' '
          }else if(item.type == "Minute"){
            a = ':00'
          }else if(item.type == "Hour"){
            a=':'
          }else {
            a='-'
          }
          this.form.yysj = this.form.yysj+item.value+a
          this.datePickerValue =this.datePickerValue + item.text
        })
      }
    },
  }

</script>

<style lang="less" scoped>
  .md-example-child{
    height:320px;
    .banner-item{
        float:left;
        width:100%;
        height:100%;
        line-height:250px;
        text-align:center;
        font-size:28px;
        color:#FFF;
        box-align:center;
        align-items:center;
        box-pack:center;
        justify-content:center;
        text-decoration-line:none;
    }
  }
</style>
