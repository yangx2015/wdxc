<style lang="less">
	@import "../styles/common.less";
    @import "./main.less";
</style>
<template>
  <div class="box main">
  	<!--<div class="header">
  		<x-header
  			:left-options="{showBack: false}">
  			with more menu</x-header>
  	</div>-->
  	<div class="body center">
  		<router-view></router-view>
  	</div>
  	<!--<div class="footer">
  		<div class="box-row">
  			<div class="body-O">
  				<a>1</a>
  			</div>
  			<div class="body-O">
  				<a>2</a>
  			</div>
  			<div class="body-O">
  				<a>3</a>
  			</div>
  		</div>
  	</div>-->
  </div>
</template>

<script>
	import { Tabbar, TabbarItem, XHeader} from 'vux'
	export default {
	  name: 'HelloWorld',
	  components: {
	    Tabbar,
	    TabbarItem,
	    XHeader
	  },
	  data () {
	    return {
	      msg: 'Welcome to Your Vue.js App'
	    }
	  },
	  methods:{
	  	back(){
	  		alert('back')
	  	}
	  }
	}
</script>
