<style lang="less" scoped>
  .star{
    i{
      font-size: 0.6rem;
      margin: 0.08rem;
    }
  }
</style>
<template>
    <div class="star">
        <i v-for="(item,index) in star"
          class="iconfont icon-xing"
           :style="{color:item.color}"
          @click="starClick(item,index)"
        ></i>
    </div>
</template>

<script>
    export default {
        name: "",
        data(){
          return{
            star:[
              {//ffbe00
                color:'#a09999'
              },
              {
                color:'#a09999'
              },
              {
                color:'#a09999'
              },
              {
                color:'#a09999'
              },
              {
                color:'#a09999'
              }
            ]
          }
        },
      methods:{
        starClick(item,index){
            this.star.forEach((val,i)=>{
                if(i<=index){
                  val.color='#ffbe00'
                }else {
                  val.color='#a09999'
                }

            })
            this.$emit('starNum',(index+1))
        }
      }
    }
</script>

<style scoped>

</style>
