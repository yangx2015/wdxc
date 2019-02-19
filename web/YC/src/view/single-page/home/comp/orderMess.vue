<template>
  <div>
    <Card>
      <div slot="title" style="position: relative">
        <p>订单详情</p>
        <div style="position: absolute;right: -5px;top: -5px;cursor: pointer" @click="messClose">
          <!--<Button type="text" size="small">-->
            <Icon type="md-close" size="22"/>
          <!--</Button>-->
        </div>
      </div>
      <div>
        <div class="boxListSty" style="padding-top: 0">
          <div class="box_row" style="margin-bottom: 14px">
            <div style="padding: 6px 30px 0 0">
              <Icon type="ios-car" size="24"/>
              {{mess.cph | space}}
            </div>
            <div>
              <Button type="info"><span style="font-weight: 600">
                  {{mess.cllx | cllx}}{{mess.zws}}座
                  </span></Button>
            </div>
          </div>
          <div class="box_row">
            <div style="padding: 6px 30px 0 0">
              <Icon type="ios-contact" size="24"/>
              {{mess.sjxm | space}}
            </div>
            <div style="padding-top: 6px">
              <Icon type="ios-call" size="24"/>
              {{mess.sjdh | space}}
            </div>
          </div>
        </div>
        <div class="boxListSty">
          <div class="addresSty">
            <Icon type="md-flag" color="#19be6b" size="24"/>
            <span>{{mess.hcdz}}</span>
          </div>
          <div>
            <Icon type="md-flag" color="#ed4014" size="24"/>
            <span>{{mess.mdd}}</span>
          </div>
        </div>

        <div class="boxListSty">
          <div style="text-align: center;font-size: 15px;font-weight: 600">
            总费用 : {{mess.zj}}元
          </div>

          <div class="box_row" style="font-size: 13px;margin-top: 18px">
            <div class="box_row_100" style="margin-right: 10px">
              里程 : {{mess.lc}}公里
            </div>
            <div class="box_row_100" style="margin-left: 10px">
              单价 : {{mess.dj}}元/公里
            </div>
          </div>

          <div class="box_row" style="font-size: 13px;margin-top: 12px">
            <div class="box_row_100" style="margin-right: 10px">
              过桥费 : {{mess.gqf}}元
            </div>
            <div class="box_row_100" style="margin-left: 10px">
              路停费 : {{mess.glf}}元
            </div>
          </div>
        </div>

        <div class="boxListSty" v-if="mess.pjdj">
          <div class="box_row">
            <div style="margin-right: 12px;font-size: 14px;font-weight:600;line-height: 40px">
              评价
            </div>
            <div class="box_row_100">
              <Rate disabled v-model="mess.pjdj" icon="ios-heart"/>
            </div>
          </div>
          <div>
            <Input v-model="mess.pjnr" readonly type="textarea" :autosize="{minRows:3,maxRows: 3}" placeholder="请填写评价！"/>
          </div>
        </div>

        <div class="boxListSty" v-else>
          <div class="box_row">
            <div style="margin-right: 12px;font-size: 14px;font-weight:600;line-height: 40px">
              评价
            </div>
            <div class="box_row_100">
              <Rate :count="5" v-model="Rate" icon="ios-heart"/>
            </div>
          </div>
          <div>
            <Input v-model="textVal" type="textarea" :autosize="{minRows:3,maxRows: 3}" placeholder="请填写评价！" />
          </div>
          <div style="padding-top: 15px">
            <Button type="info" long @click = submit>提交</Button>
          </div>
        </div>
      </div>
    </Card>
  </div>
</template>

<script>
  export default {
    name: "orderMess",
    props: {
      mess: {
        type: Object,
        default: {}
      }
    },
    filters: {
      space:(val)=>{
        if(val){
          return val
        }

        return '----------'
      },
      cllx: (val) => {
        if (val == 10) {
          return '小车'
        } else {
          return '大车'
        }
      },
    },
    data() {
      return {
        RateVal: 3,
        textVal: '',
        Rate:0
      }
    },
    methods:{
      submit(){
        this.$http.post('put/dd/evaluate',{orderId:this.mess.id,grade:this.Rate,pjnr:this.textVal}).then(res=>{
          console.log(res);
          if(res.code == 200){
            this.swal({
              title:"评价成功",
              type:'success'
            })
            this.mess.pjdj =this.Rate
            this.mess.pjnr =this.textVal
          }
        }).catch(err=>{})
      },
      messClose(){
        this.$emit('messClose')
      }
    }
  }
</script>

<style lang="less">
  .boxListSty {
    padding: 18px 16px 18px;
    border-bottom: dashed 1px #adadad;
  }

  .addresSty {
    position: relative;
    padding: 8px 0;
    padding-left: 28px;
    i {
      position: absolute;
      left: 0;
    }
  }
</style>
