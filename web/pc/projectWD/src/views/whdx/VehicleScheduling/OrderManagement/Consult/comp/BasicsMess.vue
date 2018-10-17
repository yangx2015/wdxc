<template>
	<div>
		<Modal
	      v-model="UpModal"
	      :mask-closable="false"
	      :closable="false"
	      width="800"
	      title="单据信息">
	      <div>
	      	<div style="overflow: hidden;">
	                  <Row class="expand-row">
	                        <Col span="6">
	                              <h3>候车地点：</h3>
	                              <div>
	                                    {{row.hcdz}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>目的地：</h3>
	                              <div>
	                                    {{row.hcdz}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>单据类型：</h3>
	                              <div>
	                                    {{row.wf}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>预约时间：</h3>
	                              <div>
	                                    {{row.yysj}}
	                              </div>
	                        </Col>
	                  </Row>
	                  <Row class="expand-row">
	                        <Col span="6">
	                              <h3>行车里程：</h3>
	                              <div>
	                                    {{row.lc}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>里程单价：</h3>
	                              <div>
	                                    {{row.dj}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>里程费用：</h3>
	                              <div>
	                                    {{row.lcf}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>等时：</h3>
	                              <div>
	                                    {{row.Etc}}
	                              </div>
	                        </Col>
	                  </Row>
	                  <Row class="expand-row">
	                        <Col span="6">
	                              <h3>过路费：</h3>
	                              <div>
	                                    {{row.glf}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>过桥费：</h3>
	                              <div>
	                                    {{row.BridgeFee}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>费用合计：</h3>
	                              <div>
	                                    {{row.zj}}
	                              </div>
	                        </Col>
	                        <Col span="6">
	                              <h3>备注：</h3>
	                              <div style="word-wrap:break-word">
	                                    {{row.sy}}
	                              </div>
	                        </Col>
	                  </Row>
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