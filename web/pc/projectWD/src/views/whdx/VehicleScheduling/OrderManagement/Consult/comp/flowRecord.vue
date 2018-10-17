<template>
	<div>
		<Modal
	      v-model="UpModal"
	      :mask-closable="false"
	      :closable="false"
	      width="800"
	      title="流程记录">
	      <div>
	      	<div style="overflow: hidden;">
                  <Steps :current="settleStatus">
                        <Step v-for="(item,i) in detail.oracleLog" :title="getStepTitle(item.ddzt)"
                              :content="item.cjsj"></Step>
                  </Steps>
            </div>
	      </div>
	      <div slot='footer'>
	        <Button type="default" @click="colse" style="color: #949494">取消</Button>
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
	        detail: {},
	        settleStatus: 1
	      }
	    },
		props: {
	      row: {
	        type: Object,
	        default: {}
	      }
	    },
	    created(){this.getOrderDetails()},
	    methods:{
	    	getStepTitle(zt) {
                switch (zt) {
                    case '10':
                        return '订单创建';
                    case '11':
                        return '审核通过';
                    case '12':
                        return '审核驳回';
                    case '13':
                        return '已派单';
                    case '20':
                        return '司机已确认';
                    case '21':
                        return '司机完成行程';
                    case '30':
                        return '队长确认';
                }
            },
            getOrderDetails() {
                this.$http.get(this.apis.ORDER.orderDetails + '?id=' + this.row.id).then((res) => {
                    if (res.code == 200) {
                    	this.detail = res.result;
                        this.settleStatus = this.detail.oracleLog.length;
                    }
                })
            },
	    	colse(){
		       this.$parent.compName = ''
		    },
	    }
	}
</script>

<style>
</style>
