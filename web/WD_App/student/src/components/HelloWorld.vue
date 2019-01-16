<template>
  <div class="hello">
    <img src="../assets/logo.png">
    <div @click="go" style="height: 200px;background-color: #10AEFF">
      welcome
    </div>
    <div @click="goxlmess" style="height: 200px;background-color: #10AEFF">
      xlmess
    </div>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  },
  methods:{
    go(){
      this.$router.push({name:'welcome'})
    },
    goxlmess(){
      this.$router.push({name:'xlMess'})
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
