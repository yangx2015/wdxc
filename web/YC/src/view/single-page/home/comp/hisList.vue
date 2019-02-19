<template>
  <div class="box_col">
    <div>
      <DatePicker v-model="time" type="daterange" format="yyyy-MM-dd"
                  split-panels placeholder="Select date" style="width: 100%"></DatePicker>
    </div>
    <div class="box_row" style="border-bottom: solid 2px #ededed">
      <div class="box_row_100" v-for="(it,index) in tabList" :key="index"
           style="text-align: center;padding: 12px 0">
        <Tag @click.native="tagClick(it,index)" :color="index==tabindex?'cyan':'default'" >{{it.val}}</Tag>
      </div>
    </div>
    <div class="box_col_auto" :style="{maxHeight:AF.getPageHeight()-38*2-32-60-20-140+'px'}">
      <div class="ListItem" v-for="(it,index) in pagerList" :key="index" @click="getItem(it)">
        <div class="addres">
          {{it.hcdz}}———{{it.mdd}}
        </div>
        <div class="box_row">
          <div style="margin-right: 22px">
            <div class="orderTime" style="border-bottom: solid 1px #ededed">
              <Icon type="md-time" size="24" color="#19be6b"/><span style="font-size: 18px">{{it.yysj}}</span>
            </div>
            <div class="orderTime">
              <Icon type="md-time" size="24" color="#ed4014"/><span style="font-size: 18px">{{it.sjqrsj | sjqrsj}}</span>
            </div>
          </div>
          <div class="box_row_100 carSty">
            <div class="box_col">
              <div class="box_col_100 carTyp">
                {{it.cllx | cllx}}{{it.zws}}座
              </div>
              <div class="box_col_100">
                <Button type="primary">
                  {{it.ddzt | ddzt}}
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-if="pagerList.length == 0" style="text-align: center;padding-top: 20px">
        <h3>无订单数据</h3>
      </div>
    </div>
    <div v-if="pagerList.length > 0" style="text-align: right;padding-top: 8px;border-top: solid 1px #ededed">
      <Page :current="param.pageNum" :total="total"
            :page-size="param.pageSize" simple size="small"
            @on-change = "pageChange"/>
    </div>
  </div>
</template>

<script>
  export default {
    name: "hisList",
    data(){
      return{
        time:[],
        timer:'',
        tabindex:0,
        tabTyp:'',
        tabList:[
          {
            val:'ALL',
            key:''
          },{
            val:'待审核',
            key:'10'
          },{
            val:'待分派',
            key:'11'
          },{
            val:'待出发',
            key:'13'
          },{
            val:'已完结',
            key:'20,30,40'
          },{
            val:'驳回',
            key:'12'
          }
        ],
        pagerList:[],
        total:0,
        param:{
          ddztIn:'',
          yysjInRange:'',
          pageNum:1,
          pageSize:4
        }
      }
    },
    filters:{
      sjqrsj:(val)=>{
        if(val == ''){
          return '订单进行中'
        }
        return val
      },
      cllx:(val)=>{
        if(val == 10){
          return '小车'
        }else {
          return '大车'
        }
      },
      ddzt:(val)=>{
        if(val == 10){
          return '待审核'
        }else if(val == 11){
          return '待派单'
        }else if(val == 12){
          return '订单驳回'
        }else if(val == 13){
          return '待出发'
        }else {
          return '完成'
        }
      }

    },
    watch:{
      time:function(n,o){
        console.log(n);
        let a = this.AF.trimDate(n[0]) + ' 00:00:00'
        let b = this.AF.trimDate(n[1]) + ' 23:59:59'
        this.timer = a+','+b
        this.getPagerList(this.tabTyp,this.timer)
      },
      tabindex:(n,o)=>{

      }
    },
    created(){
      let todayTime = Date.parse(new Date())
      let SevenDaysAgo = todayTime-7*24*60*60*1000
      this.time[0] = this.AF.trimDate(SevenDaysAgo)+"00:00:00"
      this.time[1] = this.AF.trimDate()+"23:59:59"
    },
    mounted(){
    },
    methods:{
      tagClick(it,index){
        this.tabTyp = it.key
        this.tabindex = index
        this.getPagerList(this.tabTyp,this.timer)
      },
      getPagerList(ddtyp,time){
        this.param.ddztIn = ddtyp
        this.param.yysjInRange = time

        this.$http.post('put/dd/pager',this.param).then(res=>{
          if(res.code == 200){
            this.pagerList = res.page.list
            this.total = res.page.total
          }
        }).catch(err=>{})
      },
      pageChange(val){
        this.param.pageNum = val
        this.getPagerList()
      },
      getItem(it){
        this.$emit('getItem',it)
      }
    }
  }
</script>

<style lang="less">
  .ListItem{
    cursor: pointer;
    padding: 8px 8px 16px 8px;
    border-bottom: dashed 1px #adadad;
    .addres{
      font-size: 18px;
      padding: 6px 0;
    }
    .orderTime{
      position: relative;
      padding-left: 30px;
      line-height: 30px;
      i{
        position: absolute;
        left: 0;
        top:2px;
      }
    }
    .carSty{
      text-align: center;
      .carTyp{
        font-size: 14px;
        font-weight: 600;
      }
      .orderType{
        font-size: 14px;
        background-color: #348EED;
        color: #fff;
        padding: 10spx 16px;
        border-radius: 8px;
      }
    }
  }
</style>
