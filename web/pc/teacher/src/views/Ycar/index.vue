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
            type="phone"
            title="联系方式"
            placeholder="请输入您的联系方式"
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
      <div class="button">
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
          }
        },
        mounted() {
        },
        methods: {
          textRender() {
            // debugger
            const args = Array.prototype.slice.call(arguments)
            console.log('***************',args)
            const typeFormat = args[0] // 类型
            // const column0Value = args[1] // 第1列选中值
            // const column1Value = args[2] // 第2列选中值
            // const column2Value = args[3] // 第3列选中值
            //
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
