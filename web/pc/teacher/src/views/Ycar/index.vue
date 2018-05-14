<style lang="less">
  @import '../../styles/box.less';
  @import "./Ycar.less";
  .md-picker-column-hook{
    text-align: center;
  }
</style>
<!--约车-->
<template>
    <div class="box">
      <head-tit tit="约车"></head-tit>
      <div class="body">
        <md-field>
          <md-input-item
            ref="input0"
            title="约车人"
            :readonly="true"
            v-model="form.ycr"
            :maxlength="5"
          ></md-input-item>
          <md-input-item
            ref="input0"
            title="乘车人"
            placeholder="请输入乘车人姓名"
            v-model="form.ccr"
            :maxlength="5"
          ></md-input-item>
          <md-input-item
            ref="input0"
            type="phone"
            title="联系方式"
            placeholder="请输入您的联系方式"
            :maxlength="5"
          ></md-input-item>
          <md-input-item
            ref="input0"
            title="出发地"
            placeholder="您的候车地点"
            :maxlength="5"
          ></md-input-item>
          <md-input-item
            ref="input0"
            title="目的地"
            placeholder="您的目的地"
            :maxlength="5"
          ></md-input-item>
          <md-input-item
            ref="input0"
            type="text"
            title="乘车人数"
            placeholder="请输入您的乘车人数"
            :maxlength="5"
          ></md-input-item>
          <md-field-item
            name="name"
            title="出发时间"
            arrow="arrow-right"
            align="right"
            :value="datePickerValue"
            @click.native="isDatePickerShow = true">
          </md-field-item>
        </md-field>
        <md-date-picker
          ref="datePicker"
          v-model="isDatePickerShow"
          type="custom"
          today-text="&(今天)"
          title="选择出发时间"
          is-twelve-hours
          :text-render="textRender"
          :custom-types="['yyyy', 'MM','dd', 'hh','mm']"
          :default-date="currentDate"
          @change="onDatePickerChange"
          @confirm="onDatePickerConfirm"
        ></md-date-picker>
      </div>
      <div class="button" @click="addList">
        <button>
          提    交
        </button>
      </div>
    </div>
</template>

<script>
  import headTit from "@/views/comp/headTit"
  import {InputItem, Field,DatePicker, FieldItem} from 'mand-mobile'
    export default {
        name: "",
        components: {
          headTit,
          [InputItem.name]: InputItem,
          [DatePicker.name]: DatePicker,
          [Field.name]: Field,
          [FieldItem.name]: FieldItem,
        },
        data(){
          return{
            currentDate: new Date(),
            isDatePickerShow: false,
            datePickerValue: '',
            Azws:200,
            Lzws:[10,7,5],
            form:{
              ycr:JSON.parse(this.cok.get('result')).name,
              xm:'',//乘车人
              cklxdh:'',//联系电话
              gethcdz:'',//候车地址
              mdd:'',//目的地
              cllx:'10',//10小车20大车
              zws:'',//座位数
              yysj:''// 约车时间按
            }
          }
        },
        created(){
          if (this.cok.get('result')) {

          }else{
            this.$router.push({
              name:'login'
            })
          }
          this.getzws()
        },
        mounted() {
        },
        methods: {
          zwsList(){
              // if(a<=5){
              //   ==5
              // }else if(5<a<=7){
              //   ==7
              // }else if(7<a<=10){
              //   ==10
              // }else if(10<a){
              //   ==10
              //
              //   a= a-10
              //
              //   this.zwsList()
              // }
          },
          getzws(){
            var v =this
            v.$http.post(this.apis.ZWS.QUERTY, {'zdlmdm':'ZDCLK0041'}).then((res) =>{

            }).catch((error)=>{

            })
          },
          addList(){//订单创建
            var v =this
            v.$http.post(this.apis.DDSAVE.SAVE, this.form).then((res) =>{

            }).catch((error)=>{

            })
          },
          textRender() {
            const args = Array.prototype.slice.call(arguments)
            const typeFormat = args[0] // 类型
            if (typeFormat === 'yyyy') {
              return args[1].toString().substring(2,4) + '年'
            }
          },
          onDatePickerChange(columnIndex, itemIndex, value) {
          },
          onDatePickerConfirm(c) {
            c.forEach((item, index) => {
              if(index==0){
                var NowDate = new Date
                let Year = NowDate.getFullYear()
                item.text=Year+'年'
              }
              this.datePickerValue = this.datePickerValue + item.text
            })
          }
        }
    }
</script>

<style scoped>

</style>
