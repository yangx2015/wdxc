<template>
  <div>
    <div v-if="mesList==undefined"style="text-align: center">
      <h1 style="color: #C8C8C8">
        暂无数据
      </h1>
    </div>
    <div v-else v-for="item in mesList">
      <Card style="width:100%;margin-top: 6px">
        <p slot="title">
          <Icon type="ios-film-outline"></Icon>
          {{item.jgmc}}-{{item.ck}}
        </p>
        <div style="font-size: 16px">
          <p>
            <Icon type="ios-clock"></Icon>
            {{item.yysj}}
          </p>
          <p>
            <Icon type="ios-telephone"></Icon>
            <a>{{item.cklxdh}}</a>
          </p>
          <p>
            <Icon type="ios-location" color="#15b740"></Icon>
            {{item.hcdz}}
          </p>
          <p><Icon type="arrow-down-c"></Icon></p>
          <p>
            <Icon type="ios-location" color="#ff9b00"></Icon>
            {{item.mdd}}
          </p>
        </div>
        <div style="text-align: right">
          <Button type="warning" @click="oklist(item)">行程确认</Button>
        </div>
        <component
            :is="compName"
            :mess="mess"
            @close="close"
        ></component>
      </Card>

    </div>
  </div>
</template>
<script>
  import okModal from './comp/okModal'
  import Cookies from 'js-cookie';
  export default {
    name:'',
    components:{
      okModal
    },
    data () {
      return {
        compName:'',
        mesList:[],
        mess:{}
      }
    },
    created(){
      if(Cookies.get('result')) {
          this.dqr()
      }else{
        this.$router.push({
          name:'login'
        })
      }
    },
    methods:{
      oklist(item){
        // debugger
        this.mess = item
        this.compName = okModal
      },
      close(){
        this.compName = ''
        this.dqr()
      },
      dqr(){//待确认列表
        this.$http.post(this.apis.MESLIST.QUERTY,{'type':'2'}).then((res)=>{
          if(res.code == 200){
            this.mesList = res.result
          }
        })
      }
    }
  }
</script>
