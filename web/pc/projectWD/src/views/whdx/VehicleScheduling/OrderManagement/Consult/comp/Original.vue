<style>

    .my_table td {
        padding: 4px;
        text-align: center;
        font-size: 16px;
    }
</style>
<template>
	<div>
		<Modal
      v-model="UpModal"
      :mask-closable="false"
      :closable="false"
      width="800"
      title="原始单据">
      <div>
      	<div v-if="detail.initialOracle.length > 0" v-for="row in detail.initialOracle"
                 style="overflow: hidden;" class="topmar">
            <Row>
                <Col span="24">
                    <div style="text-align: center;padding: 6px;font-size: 18px;">
                        {{getDate(row.cjsj)}}
                    </div>
                </Col>
            </Row>
            <table class="my_table" border="1" cellpadding="0" cellspacing="0" style="width: 100%">
                <tbody>
                <tr>
                    <td rowspan="2" width="20%">用车单位</td>
                    <td rowspan="2" width="20%">{{row.jgmc}}</td>
                    <td colspan="3" width="30%">出车时间</td>
                    <td colspan="3" width="30%">目的地</td>
                </tr>
                <tr>
                    <td style="width: 80px;">上午</td>
                    <td colspan="2" width="20%">{{getSw(row.cjsj)}}</td>
                    <td colspan="3">{{row.mdd}}</td>
                </tr>
                <tr>
                    <td>用车人</td>
                    <td>{{row.ck}}</td>
                    <td>下午</td>
                    <td colspan="2">{{getXw(row.cjsj)}}</td>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td>候车地点</td>
                    <td>{{row.hcdz}}</td>
                    <td>用户签字</td>
                    <td colspan="2"></td>
                    <td rowspan="2" width="10%" style="max-width: 80px">事由</td>
                    <td colspan="2" rowspan="2" width="20%"></td>
                </tr>
                <tr>
                    <td>车型</td>
                    <td></td>
                    <td>司机</td>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td>公里数</td>
                    <td>{{row.lc}}</td>
                    <td>单价</td>
                    <td colspan="2">{{row.dj}}</td>
                    <td>金额</td>
                    <td colspan="2"></td>
                </tr>
                <tr>
                    <td>等时</td>
                    <td></td>
                    <td>路停费</td>
                    <td colspan="2">{{row.glf}}</td>
                    <td>过桥费</td>
                    <td colspan="2">{{row.gqf}}</td>
                </tr>
                <tr>
                    <td>用户电话</td>
                    <td colspan="4">{{row.cklxdh}}</td>
                    <td>车费合计</td>
                    <td colspan="2">{{row.zj}}</td>
                </tr>
                <tr>
                    <td width="10%">早班</td>
                    <td width="15%"></td>
                    <td width="10%">中班</td>
                    <td width="15%"></td>
                    <td width="10%">晚班</td>
                    <td width="15%"></td>
                    <td width="10%" rowspan="2">加班合计</td>
                    <td width="15%" rowspan="2" style="min-width: 80px;"></td>
                </tr>
                <tr>
                    <td>长途</td>
                    <td></td>
                    <td>包车</td>
                    <td></td>
                    <td>休班</td>
                    <td></td>
                </tr>
                <tr>
                    <td>备注</td>
                    <td colspan="5"></td>
                    <td>签发人</td>
                    <td colspan="1"></td>
                </tr>
                </tbody>
            </table>
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
            getSw(s) {
                let hour = parseInt(s.substring(11, 13));
                console.log('hour', hour);
                if (hour < 12) {
                    let date = new Date(s)
                    return date.format("hh:mm:ss")
                } else {
                    return '';
                }
            },
            getXw(s) {
                let hour = parseInt(s.substring(12, 14));
                console.log('hour', hour);
                if (hour >= 12) {
                    let date = new Date(s)
                    return date.format("hh:mm:ss")
                } else {
                    return '';
                }
            },
            getDate(s) {
                let date = new Date(s)
                return date.format("yyyy年MM月dd日")
            },
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