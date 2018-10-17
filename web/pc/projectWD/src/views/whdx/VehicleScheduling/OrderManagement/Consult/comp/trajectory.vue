<template>
	<div>
		<Modal
      v-model="UpModal"
      :mask-closable="false"
      :closable="false"
      width="800"
      title="车辆轨迹">
      <div>
      	<div style="overflow: hidden;text-align: center;">
                  <div v-if="detail.sjqrsj == ''">
                        <h2>司机未确认行程结束</h2>
                  </div>
                  <div v-else>
                        <h2 v-if="NewGps.length == 0">暂无轨迹信息</h2>
                        <div v-else>
                              <show-map :index="row._index" :gpsList="NewGps"></show-map>
                        </div>

                  </div>
            </div>
      </div>
      <div slot='footer'>
        <Button type="default" @click="colse" style="color: #949494">取消</Button>
      </div>
    </Modal>
	</div>
</template>

<script>
import showMap from '../hisList'
	export default {
		name: "Basics",
		components: {
            showMap
        },
		data() {
	      return {
	        UpModal: true,
	        detail: {},
	        NewGps:[],
	        simpleRoute: null,
	      }
	    },
		props: {
	      row: {
	        type: Object,
	        default: {}
	      }
	    },
	    created(){},
	    mounted() {
            this.getOrderDetails();
        },
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
                            this.simpleRoute= {
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