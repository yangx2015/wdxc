<template>
	<div>
		<Modal
      v-model="UpModal"
      :mask-closable="false"
      :closable="false"
      width="800"
      title="原始单据">
      <div>
      	<div v-if="detail.initialOracle.length > 0" v-for="item in detail.initialOracle"
                 style="overflow: hidden;" class="topmar">
                  <Col span="6" class="margin-top-5">
                        <h5>用车单位：</h5>{{item.jgmc}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>用车人：</h5>{{item.ck}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>电话：</h5>{{item.cklxdh}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>出车时间：</h5>{{item.yysj}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>候车地点：</h5>{{item.hcdz}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>目的地：</h5>{{item.mdd}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>行车里程：</h5>{{item.lc}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>等时：</h5>{{item.ck}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>过桥费：</h5>{{item.glf}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>过路费：</h5>{{item.glf}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>合计金额：</h5>{{item.zj}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>修改人：</h5>{{item.xgr}}
                  </Col>
                  <Col span="6" class="margin-top-5">
                        <h5>修改时间：</h5>{{item.xgsj}}
                  </Col>
                  <Col>
                        <div style="border-bottom: 1px solid gainsboro"></div>
                  </Col>
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
            simpleRoute: null,
            NewGps:[],
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
            getOrderDetails() {
                this.$http.get(this.apis.ORDER.orderDetails + '?id=' + this.row.id).then((res) => {
                    if (res.code == 200) {
                        console.log('数据',res.result);
                        this.detail = res.result;
                        this.settleStatus = this.detail.oracleLog.length;
                        // this.NewGps = this.detail.gpslist
                        this.NewGps = this.detail.gpsLog
                        let gpsLog = this.detail.gpsLog;
                        if (gpsLog.length != 0) {
                            this.simpleRoute = {
                                ksjd: gpsLog[0].bdjd,
                                kswd: gpsLog[0].bdwd,
                                jsjd: gpsLog[gpsLog.length - 1].bdjd,
                                jswd: gpsLog[gpsLog.length - 1].bdwd,
                            };
                        }

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