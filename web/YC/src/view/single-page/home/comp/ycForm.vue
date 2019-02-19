<template>
  <div class="box_col YcForm">
    <div class="TIT">
      当前乘车人 {{ycMess.ck}}
      <Tooltip style="float: right" content="本人乘车" placement="top">
        <Button @click="myCar" size="large"
                icon="md-person" type="primary" shape="circle"></Button>
      </Tooltip>
      <Tooltip style="float: right" content="点击更换乘车人" placement="top">
        <Button @click="chPeople" size="large"
                icon="md-swap" type="primary" shape="circle"></Button>
      </Tooltip>
    </div>
    <div class="formBox" style="padding: 30px 12px 0">
      <Form :model="ycMess"  ref="formValidate" :rules="ruleValidate" label-position="left">
        <FormItem>
          <DatePicker v-model="timer" type="datetime" format="yyyy-MM-dd HH:mm" :options="options"
                      placeholder="请选择出发时间" style="width: 100%"></DatePicker>
        </FormItem>

        <FormItem>
          <Input v-model="ycMess.hcdz" placeholder="请填写出发地" clearable
            @on-blur="addStartCode=true" @on-focus="addStartCode=false,addEndCode = true">
            <Icon type="md-flag" color="#19be6b" size="22" slot="prepend" />
          </Input>
        </FormItem>

        <FormItem>
          <Input v-model="ycMess.mdd" placeholder="请填写目的地" clearable
                 @on-blur="addEndCode=true" @on-focus="addEndCode=false,addStartCode = true">
            <Icon type="md-flag" color="#ed4014" size="22" slot="prepend" />
          </Input>
        </FormItem>


        <div>
          <div style="font-size: 18px;font-weight: 600;margin: 12px 0">
            车型 :
          </div>
          <div class="box_row carTypSty">
            <div class="box_row_100" style="margin: 0 6px" @click="getCarTyp('10',5)">
              <img src="../img/car5.png" width="100%" alt="">
              <div class="carItem" :style="{color:carTyp==5?'#f00':'#000'}">
                  5座
              </div>
            </div>
            <div class="box_row_100" style="margin: 0 6px" @click="getCarTyp('10',7)">
              <img src="../img/car7.png" width="100%" alt="">
              <div class="carItem" :style="{color:carTyp==7?'#f00':'#000'}">
                7座
              </div>
            </div>
            <div class="box_row_100" style="margin: 0 6px" @click="getCarTyp('10',11)">
              <img src="../img/car11.png" width="100%" alt="">
              <div class="carItem" :style="{color:carTyp==11?'#f00':'#000'}">
                11座
              </div>
            </div>
          </div>

          <div class="box_row carTypSty">
            <div class="box_row_100" style="margin: 0 6px" @click="getCarTyp('20',20)">
              <img src="../img/car20.png" width="100%" alt="">
              <div class="carItem" :style="{color:carTyp==20?'#f00':'#000'}">
                20座
              </div>
            </div>
            <div class="box_row_100" style="margin: 0 6px" @click="getCarTyp('20',32)">
              <img src="../img/car32.png" width="100%" alt="">
              <div class="carItem" :style="{color:carTyp==32?'#f00':'#000'}">
                32座
              </div>
            </div>
            <div class="box_row_100" style="margin: 0 6px" @click="getCarTyp('20',45)">
              <img src="../img/car45.png" width="100%" alt="">
              <div class="carItem" :style="{color:carTyp==45?'#f00':'#000'}">
                45座
              </div>
            </div>
            <div class="box_row_100" style="margin: 0 6px" @click="getCarTyp('20',48)">
              <img src="../img/car48.png" width="100%" alt="">
              <div class="carItem" :style="{color:carTyp==48?'#f00':'#000'}">
                48座
              </div>
            </div>
          </div>

        </div>
      </Form>
      <div>
        <Button type="warning" size="large" long @click="submit">提交</Button>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    name: "ycForm",
    props:{
      ycMess:{
        type:Object,
        default:{
          hcdz:'',
          originLat:'',
          originLng:'',
          mdd:'',
          destinationLat:'',
          destinationLng:'',
          ck:'',//乘客
          cklxdh:'',//电话
          yysj:'',//约车时间
          cllx:'',//10小车，20大车
          zws:''
        }
      }
    },
    watch:{
      'ycMess.hcdz':function (n,o) {
        if(o!==''&&n == ''){
          this.$emit("clearAddres",'qd')
        }
      },
      'ycMess.mdd':function (n,o) {
        if(o!==''&&n == ''){
          this.$emit("clearAddres",'zd')
        }
      },
      timer:function (n,o) {
        if(n){
          this.ycMess.yysj = this.AF.getTime(n)
        }
      }
    },
    data(){
      return{
        timer:'',

        addStartCode:true,
        addEndCode:true,
        options: {
          disabledDate (date) {
            return date && date.valueOf() < Date.now()-1000*60*60*24;
          }
        },
        ruleValidate:{},
        chrList:[],
        carTyp:null
      }
    },
    methods:{
      myCar(){
        this.$emit("myCar")
      },
      getCarTyp(typ,zws){
        this.carTyp=zws
        this.ycMess.cllx = typ
        this.ycMess.zws = zws
      },
      chPeople(){
        this.$emit('chPeople')
      },
      submit(){
        this.$http.post('put/dd/save',this.ycMess).then(res=>{
          console.log(res);
          if(res.code == 200){
            this.swal({
              title:'订单提交成功',
              type:'success'
            })
            this.$emit('ycmessClear')
            this.carTyp = null
            this.timer = ''
          }else {
            this.swal({
              title:res.message,
              type:'warning'
            })
          }
        }).catch(err=>{})
      }
    }
  }
</script>

<style lang="less">
  .YcForm{
    .TIT{
      background-color: #348EED;
      text-align: center;
      color: #fff;
      font-size: 22px;
      font-weight: 600;
      line-height: 40px;
      border-radius: 0 0 20px 20px;
    }
    .formBox{
      .ivu-form-item-label{
        width: 100%;
        .formLab{
          font-size: 18px;
          font-weight: 600;
          color: #000;
          text-align: left;
          position: relative;
          .but{
            position: absolute;
            right: 0;

          }
        }
      }
      .carTypSty{
        padding-bottom: 12px;cursor: pointer;
        .carItem{
          text-align: center;font-size: 20px;font-weight: 600
        }
      }
    }
  }

</style>
