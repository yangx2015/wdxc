<template>
  <Card style="width:100%;margin-top: 6px">
    <p slot="title">
      <Icon type="ios-film-outline"></Icon>
      信息工程学院-小名
    </p>
    <div style="font-size: 16px">
      <p>
        <Icon type="ios-clock"></Icon>
        2018-11-11 09:00:00
      </p>
      <p>
        <Icon type="ios-telephone"></Icon>
        <a>123456789000</a>
      </p>
      <p>
        <Icon type="ios-location" color="#15b740"></Icon>
        候车地点
      </p>
      <p><Icon type="arrow-down-c"></Icon></p>
      <p>
        <Icon type="ios-location" color="#ff9b00"></Icon>
        目的地
      </p>
    </div>
    <div style="text-align: right">
      <Button type="warning" @click="compName='okModal'">行程确认</Button>
    </div>
    <component
        :is="compName"
        @close="close"
    ></component>
  </Card>
</template>
<script>
  import okModal from './comp/okModal'
  export default {
    name:'',
    components:{
      okModal
    },
    data () {
      return {
        compName:''
      }
    },
    methods:{
      close(){
        this.compName = ''
      }
    }
  }
</script>
