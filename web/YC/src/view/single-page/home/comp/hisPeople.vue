<template>
  <div>
    <Modal
      v-model="showModal"
      width="300"
      :closable="false"
      :mask-closable="false"
      :footer-hide="true">
      <div slot="header">
        <h2>乘车人</h2>
      </div>
      <div style="max-height:300px" class="box_col">
        <div class="box_row">
          <div style="margin-right:10px">
            <Input v-model="hisPeo.name" placeholder="姓名" style="width:90px" />
          </div>
          <div style="margin-right:10px">
            <Input v-model="hisPeo.phone" placeholder="电话" style="width: 100px" />
          </div>
          <div>
            <Button  icon="md-add" type="primary" @click="addPeItem"></Button>
          </div>
        </div>
        <div class="box_col_auto">
          <div v-for="(it,index) in pelList" :key="index" @click="okPeo(it)">
            <div class="pelItemSty">
              {{(index+1)}} {{it.name}}__{{it.phone}}
            </div>
          </div>
        </div>
      </div>
    </Modal>
  </div>
</template>

<script>
  export default {
    name: "hisPeople",
    data(){
      return{
        showModal:true,
        pelList:[],
        hisPeo:{
          name:'',
          phone:''
        }
      }
    },
    created(){
      this.pelList = JSON.parse(localStorage.getItem('pelList'))
    },
    methods:{
      okPeo(item){
        this.$emit('okPeo',item)
      },
      addPeItem(){
        if(this.hisPeo.name=='' || this.hisPeo.phone==''){
          this.swal({
            title:'请将乘客信息填写完成！',
            type:'warning'
          })
        }else {
          this.pelList.splice(0,0,this.hisPeo)
          if(this.pelList.length = 11){
            this.pelList.splice(10,1)
          }
          localStorage.setItem('pelList',JSON.stringify(this.pelList))
          this.hisPeo = {
            name:'',
            phone:''
          }
        }
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
