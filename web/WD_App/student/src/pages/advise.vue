<template>
  <div>
    <div>
      <mt-header title="意见反馈">
        <router-link to="/" slot="left">
          <mt-button icon="back">返回</mt-button>
        </router-link>
      </mt-header>
    </div>
    <div style="padding: 10px 10px"
      v-model="showModal"
      :closable="false"
      :mask-closable="false"
      :footer-hide="true">
      <div slot="header" style="position: relative">
        <h2>建议反馈</h2>
        <!--<Icon @click.native="closrModal" type="md-close" size="26" style="position: absolute;right: 0;top: -5px;cursor: pointer"/>-->
      </div>
      <div class="box_col">
        <Tabs type="card" v-model="param.yjlx">
          <TabPane label="意见" name="00"></TabPane>
          <TabPane label="反馈" name="10"></TabPane>
          <TabPane label="投诉" name="20"></TabPane>
        </Tabs>
        <div style="position: relative">
          <Input v-model="param.nr" type="textarea" :autosize="{minRows:3,maxRows: 3}" placeholder="请填写您想说的话" />
          <Button type="info" :disabled="param.nr==''" @click="addFk"
                  style="position:absolute;right: 2px;bottom: 2px"
          >提交</Button>
        </div>
        <div style="padding: 12px 0;font-weight: 600;font-size: 15px;color: #f90;border-bottom: dashed 1px #adadad">
          意见、反馈、投诉记录
        </div>
        <div class="box_col_auto">
          <div v-for="(it,index) in fkData" style="border-bottom: solid 1px #a9a9a9">
            <div style="padding: 12px 0 8px 12px;position: relative">
              <h2>{{(index +(params.pageNum - 1) * params.pageSize + 1)}}.{{it.yjlx | yjlx}}:</h2>
              <div style="position: absolute;right: 0;bottom: 5px">
                {{it.cjsj}}
              </div>
            </div>
            <div style="padding:0 0 0 40px;font-size: 16px;">
              {{it.nr}}
            </div>
            <div style="padding: 12px 0 8px 40px;border-top: dashed 1px #adadad" v-if="it.cljg">
              <h2>回复：</h2>
              <div>
                <Input v-model="it.cljg" type="textarea" readonly :autosize="{minRows:3,maxRows: 3}" placeholder="…………" />
              </div>
            </div>

          </div>
        </div>
        <!--v-if="pagerList.length > 0"-->
        <div  style="text-align: right;padding-top: 8px;border-top: solid 1px #ededed">
          <Page :current="params.pageNum" :total="total"
                :page-size="params.pageSize" simple size="small"
                @on-change = "pageChange"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { Header , Toast} from 'mint-ui';
  export default {
    name: "hisPeople",
    data(){
      return{
        showModal:true,
        param:{
          nr:'',
          yjlx:'00',//00意见 10反馈 20投诉
          lxrxm:'',
          lxfs:''
        },
        total:0,
        params:{
          pageNum:1,
          pageSize:4
        },
        fkData:[]
      }
    },
    filters:{
      yjlx:(val)=>{
        let dict={
          '00':'意见',
          '10':'反馈',
          '20':'投诉'
        }
        return dict[val]
      }
    },
    created(){
      this.fkList()
      // this.param.lxrxm = this.$parent.userMess.xm
      // this.param.lxfs = this.$parent.userMess.sjhm
    },
    methods:{
      Indexes(val){
        return val + (this.params.pageNum - 1) * this.params.pageSize + 1
      },
      closrModal(){
        this.$parent.compFkName = ''
      },
      addFk(){
        if(this.param.nr==''){
          Toast('请填写您想说的话')
          return
        }else {
          this.$http.post('put/yj/save',this.param).then(res=>{
            if(res.code == 200){
              console.log(123.123);
              Toast('提交成功')
              this.param.nr = ''
              this.fkList()
            }
          }).catch(err=>{})
        }
      },
      fkList(){
        this.$http.post('put/yj/pager',this.params).then(res=>{
          console.log(res);
          if(res.code == 200){
            this.fkData = res.page.list
            this.total = res.page.total
          }
        }).catch(err=>{})
      },
      pageChange(val){
        this.params.pageNum = val
        this.fkList()
      }
    }
  }
</script>

<style lang="less">
  .pelItemSty{
    font-size: 16px;
    padding: 8px 0;
    border-bottom: solid #ededed 2px;
    cursor: pointer;
  }
</style>
