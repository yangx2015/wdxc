<template>
	<div>
		<Modal
      v-model="UpModal"
      :mask-closable="false"
      :closable="false"
      width="800"
      title="原始单据">
      <div>
      </div>
      <div slot='footer'>
        <Button type="default" @click="" style="color: #949494">取消</Button>
        <Button type="primary" @click="">确定</Button>
      </div>
    </Modal>
	</div>
</template>

<script>
	export default {
		name: "Basics",
		data() {
	      return {
	        UpModal: true,
	      }
	    },
		props: {
	      row: {
	        type: Object,
	        default: {}
	      }
	    },
	    created(){},
	    methods:{
	    	colse(){
		       this.$parent.compName = ''
		    },
	    }
	}
</script>

<style>
</style>