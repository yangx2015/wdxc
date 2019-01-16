<template>
    <div>
      <button @click="back">返回</button>
      <button @click="go">首页</button>
    </div>
</template>

<script>
    export default {
        name: "welcome",
      methods:{
        back() {
            this.$router.back()
        },
        go(){
            this.$router.push({name:'HelloWorld'})
        }
      }
    }
</script>

<style scoped>

</style>
