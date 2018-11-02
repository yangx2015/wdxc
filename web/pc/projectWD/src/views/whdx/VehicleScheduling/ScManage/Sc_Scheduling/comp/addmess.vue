<style lang="less">
      .carListsty {
             padding: 3px 0;
             margin: 8px;
             border: solid #657180 1px;
             /*height: 30px;*/
             position: relative;
       }
      .carListstyB {
            padding: 3px 0 0 0;
            margin: 8px;
            border: solid #657180 1px;
            /*height: 30px;*/
            position: relative;
            .clickSty{
                  /*background-color: #2db7f5;*/
                  color: #fff;
                  cursor: pointer;
            }
            .borRight{
                  border-right: solid #a9a7a7 2px;
            }
      }
</style>
<template>
      <div>
            <Modal
                    v-model="showModal"
                    height="400"
                    :closable='false'
                    :mask-closable="false"
                    width='60%'>
                  <div slot="header" class="box-row" style="align-items: center">
                        <div style="font-weight: 600;font-size: 18px">
                              {{mess.xlmc+'_线路排班'}}
                        </div>
                        <div style="margin-left: 22px">
                              <RadioGroup v-model="vertical">
                                    <Radio label="0" size="large">
                                          <span style="font-weight: 600;font-size: 18px">今日排班</span>
                                    </Radio>
                                    <Radio label="1">
                                          <span style="font-weight: 600;font-size: 18px">批量排班</span>
                                    </Radio>
                              </RadioGroup>
                        </div>
                        <div>
                              <DatePicker :readonly="vertical=='0'" :value="DatePicker"
                                          format="yyyy-MM-dd" type="daterange"
                                          placement="bottom-end" placeholder="Select date"
                                          :options="optionsDate"
                                          :split-panels="true"
                                          @on-change="DatePick"
                                          style="width: 200px"></DatePicker>
                        </div>
                  </div>
                  <div>
                        <div style="height: 200px;border: solid 1px #000;overflow: auto">
                              <Row>
                                    <Col span="4" v-if="mess.clList.length>0" v-for='(item,index) in mess.clList'>
                                          <div class="carListsty">
                                                <div style="padding: 0 3px">
                                                      <Icon type="md-person" size="16" color="#3bb84b"></Icon>
                                                      ：
                                                      {{item.sjxm}}
                                                </div>
                                                <div style="padding: 0 3px">
                                                      <Icon type="ios-car" size="16" color="#ff8300"></Icon>
                                                      ：
                                                      {{item.cph}}
                                                </div>
                                                <div style="padding: 0 3px; cursor: pointer;">
                                                      {{item.pbbx | pbbx}}:{{item.startTime}}~{{item.endTime}}
                                                      <!--<Icon type="ios-construct" size="16" color="#2db7f5"/>-->
                                                </div>
                                                <span style="position:absolute;top: -6px;right: -6px;z-index: 100;">
		    						 <Button type="error" shape="circle"
                                                                         size="small" icon="md-close"
                                                                         @click="deleteById(item.clId,index,item.pbbx)"></Button>
		    					</span>
                                          </div>
                                    </Col>
                              </Row>
                        </div>
                        <div style="height: 200px;border: solid 1px #000;overflow: auto">
                              <Row>
                                    <Col v-show="chrlist" span="4" v-for='(item,index) in chrlist'>
                                          <div class="carListstyB">
                                                <div style="padding: 0 3px">
                                                      <Icon type="md-person" size="16" color="#3bb84b"></Icon>
                                                      ：
                                                      {{item.sjxm}}
                                                </div>
                                                <div style="padding: 0 3px">
                                                      <Icon type="ios-car" size="16" color="#ff8300"></Icon>
                                                      ：
                                                      {{item.cph}}
                                                </div>
                                                <Row style="margin-top: 3px">
                                                      <Col span='8' align="center" class-name="clickSty borRight">
                                                            <Button type="info" size="small" long
                                                                    :disabled="item.classesList.indexOf('1')!=-1 || item.classesList.indexOf('7')!=-1"
                                                                    @click="AddList(item.clId,item.cph,item.sjxm,'1')"
                                                            >早班</Button>
                                                      </Col>
                                                      <!--<Col span='6' align="center" class-name="clickSty borRight">
                                                            <Button type="info" size="small" long
                                                                    :disabled="item.classesList.indexOf('2')!=-1 || item.classesList.indexOf('7')!=-1"
                                                                    @click="AddList(item.clId,item.cph,item.sjxm,'2')"
                                                            >中班</Button>
                                                      </Col>-->
                                                      <Col span='8' align="center" class-name="clickSty borRight">
                                                            <Button type="info" size="small" long
                                                                    :disabled="item.classesList.indexOf('2')!=-1 || item.classesList.indexOf('7')!=-1"
                                                                    @click="AddList(item.clId,item.cph,item.sjxm,'2')"
                                                            >晚班</Button>
                                                      </Col>
                                                      <Col span='8' align="center" class-name="clickSty borRight">
                                                            <Button type="info" size="small" long
                                                                    :disabled="item.classesList.indexOf('7')!=-1"
                                                                    @click="AddList(item.clId,item.cph,item.sjxm,'7')"
                                                            >全天</Button>
                                                      </Col>
                                                </Row>
                                                <!--<span style="position:absolute;top: -6px;right: -6px;z-index: 100;">-->
		    						 <!--<Button v-if="!(item.clId==='000000')" type="primary"-->
                                                                         <!--shape="circle"-->
                                                                         <!--size="small" icon="md-add"-->
                                                                         <!--@click="AddList(item.clId,item.cph,item.sjxm)"></Button>-->
		    					<!--</span>-->
                                          </div>
                                    </Col>
                              </Row>
                        </div>
                  </div>
                  <div slot='footer'>
                        <Button type="primary" @click="finish">完成</Button>
                        <Button type="primary" @click="close">关闭</Button>
                  </div>
            </Modal>
      </div>
</template>

<script>

    export default {
        name: '',
        data() {
            return {
                optionsDate: {
                    disabledDate(date) {
                        return date && date.valueOf() < Date.now() - 86400000;
                    }
                },
                vertical: '0',
                showModal: true,
                chrlist: [],
                DatePicker: [this.pbTime, this.pbTime]
            }
        },
        filters:{
            pbbx:(val)=>{
                if(val == '1'){
                    return '早班'
                }else if(val == '2'){
                    return '中班'
                }else if(val == '4'){
                    return '晚班'
                }else if(val == '7'){
                    return '全天'
                }
            }
        },
        watch: {
            vertical: function (val) {
                if (val == '0') {
                    this.DatePicker = [this.pbTime, this.pbTime]
                }
            }
        },
        props: {
            mess: {
                type: Object,
                default: {}
            },
            pbTime: ''
        },
        created() {
            if (this.mess.clList == (null || "")) {
                this.mess.clList = []
            }
            log('信息传递', this.mess)
            this.getCarList()
        },
        methods: {
            DatePick(val) {
                console.log(val);
                var v = this
                this.DatePicker = [val[0], val[1]]
                this.$http.post(this.apis.XLPBXX.YZ, {
                    "xlId": v.mess.id,
                    "kssj": val[0],
                    "jssj": val[1],
                    'cx': '30'
                }).then((res) => {
                    log('数据数据数据车量', res)
                    this.swa
                }).catch((error) => {
                    v.$Message.error('出错了！！！');
                })
            },
            getCarList() {//获取车辆列表
                var v = this
                this.$http.post(this.apis.XLPBXX.CARLIST, {
                    'xlId': v.mess.id,
                    'date': v.pbTime,
                    'cx': '30'
                }).then((res) => {
                    if (res.code == 200) {
                        if (res.result) {
                            res.result.forEach((item,index)=>{
                                item.classesList = item.classesList.join(',')
                            })

                            v.chrlist = res.result
                            // console.log(res);
                        } else {
                            v.chrlist = [{'cph': '无车辆数据', 'clId': '000000', 'sjxm': '****'}]
                        }
                    } else {
                        log('bug')
                    }
                    log('车辆数据', res)
                }).catch((err) => {
                    log('bug')
                })
            },
            AddList(carID, cph, sjxm,num) {
                var v = this
                this.$http.post(this.apis.XLPBXX.PLADD, {
                    "clId": carID,
                    "xlId": v.mess.id,
                    "kssj": this.DatePicker[0],
                    "jssj": this.DatePicker[1],
                    'cx': '30',
                    'classes':num
                }).then((res) => {
                    log('排版新增', res)
                    if (res.code == 200) {
                        console.log(res)
                        v.$Message.success(res.message);
                        v.mess.clList.push({'cph': cph, 'clId': carID, 'sjxm': sjxm,'pbbx':num,'startTime':res.result.startTime,'endTime':res.result.endTime})
                        v.getCarList()
                    } else {
                        v.$Message.error(res.message);
                    }
                }).catch((error) => {
                    v.$Message.error('出错了！！！');
                })
            },
            deleteById(carID, index,num) {
                var v = this
                this.$http.post(this.apis.XLPBXX.PLDELE, {
                    "clId": carID,
                    "xlId": v.mess.id,
                    "kssj": this.DatePicker[0],
                    "jssj": this.DatePicker[1],
                    'cx': '30',
                    'classes':num
                }).then((res) => {
                    if (res.code == 200) {
                        v.$Message.success(res.message);
                        v.$parent.getmess();
                        v.getCarList();
                        v.mess.clList.splice(index, 1)
                    } else {
                        v.$Message.error(res.message);
                    }
                }).catch((error) => {
                    v.$Message.error('出错了！！！');
                })
            },
            finish() {
                var v = this
                v.$parent.getmess()
                v.$parent.compName = ''
            },
            close() {
                var v = this
                v.$parent.getmess()
                v.$parent.compName = ''
            }
        }
    }
</script>

<style>
</style>