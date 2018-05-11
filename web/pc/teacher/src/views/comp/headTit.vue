<style lang="less">
  @import "../../styles/box.less";
</style>
<template>
  <div class="boxHeader">
      <div class="box-row">
          <div class="titLeft" @click="back()">
            <i class="iconfont icon-left"></i>
          </div>
          <div class="titCenter body-O" style="text-align: center;">
            {{tit}}
          </div>
          <div class="titLeft">
          </div>
      </div>
  </div>
</template>

<script>
    export default {
        name: "headTit",
        props:{
            tit:{
              type:String,
              default:'tit'
            }
        },
        methods:{
          back(){
            this.$router.push({
              name: 'Home'
            });
          }
        }
    }
</script>

<style scoped>

</style>
