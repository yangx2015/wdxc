<style type="text/css">
    #printArea td {
        padding: 4px;
        text-align: center;
        height: 60px;
        font-size: 18px;
    }
</style>
<template>
    <div>
        <Modal
                v-model="UpModal"
                :mask-closable="false"
                :closable="false"
                width="800">
            <div slot="header" v-if="showFooter" style="color: red;">
                只能使用Chrome(谷歌)浏览器进行打印
            </div>
            <div>
                <div style=";" id="printArea">
                    <Row>
                        <Col span="24">
                            <div style="text-align: center;">
                                <h1>
                                    武大后勤集团运输中心用车单
                                </h1>
                            </div>
                        </Col>
                    </Row>
                    <Row>
                        <Col span="24">
                            <div style="text-align: center;padding: 16px;font-size: 18px;">
                                {{getDate(row.cjsj)}}
                            </div>
                        </Col>
                    </Row>
                    <table border="1" cellpadding="0" cellspacing="0" style="width: 100%">
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
                            <td colspan="8">
                                <div style="text-align: center;font-weight: 600;font-size: 20px;">安全准点
                                    <div class="blank" style="width: 50px;display: inline-block"></div>
                                    优质服务
                                </div>
                            </td>
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
                <Button type="default" v-if="showFooter" @click="close" style="color: #949494">取消</Button>
                <Button type="primary" v-if="showFooter" @click="print">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import {Printd} from 'printd'

    export default {
        name: "Basics",
        data() {
            return {
                UpModal: true,
                showFooter: true,
            }
        },
        filters: {},
        props: {
            row: {
                type: Object,
                default: {}
            }
        },
        created() {
        },
        methods: {
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
            wf(val) {
                let cx = this.dictUtil.getValByCode(this, 'ZDCLK0042', val)
                return cx
            },
            cx(cllx, zws) {
                if (cllx && zws) {
                    let cx = this.dictUtil.getValByCode(this, 'ZDCLK0019', cllx)
                    return cx + '/' + zws + '座'
                }
                return ''
            },
            close() {
                this.$parent.compName = ''
            },
            print() {
                var v = this
                this.bzShow = false
                const cssText = `
                    #printArea td {
                    padding: 4px;
                    text-align: center;
                    height: 60px;
                    font-size: 18px;
                }
                `
                const d = new Printd();
                setTimeout(() => {
                    d.print(document.getElementById('printArea'), cssText)
                }, 50)
                setTimeout(() => {
                    v.close()
                }, 300)
            }
        }
    }
</script>

<style>
</style>