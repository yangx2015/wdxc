<style lang="less">
  @import "./center.less";
</style>
<template>
	<div class="myCenter box">
    <div class="body" style="margin-top: 0.2rem">
      <div class="Flist" @click="pageList">
        <i class="iconfont icon-list"></i>
        <span>
           订单
        </span>
      </div>
      <div class="Flist">
        <i class="iconfont icon-setting"></i>
        <span>
           设置
        </span>
      </div>
    </div>
    <div class="LoginClose">
        <button>退出登录</button>
    </div>
	</div>
</template>

<script>

	export default{
		name:'',
		components: {
		},
    data(){
		  return{

      }
    },
    methods:{
      pageList(){
        this.$router.push({
          name:'list'
        })
      }
    }
	}
</script>

<style>
</style>
