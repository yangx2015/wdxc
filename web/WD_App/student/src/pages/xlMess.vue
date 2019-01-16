<template>
  <div>
    <button @click="back">返回</button>
    <button @click="go">地图</button>
  </div>
</template>

<script>
    export default {
        name: "xlMess",
      methods:{
          back(){
            this.$router.back()
          },
        go(){
          this.$router.push({name:'xlMap'})
        }
      }
    }
</script>

<style scoped>

</style>
