<template>
    <div>
    <Card>
        <p slot="title" style="font-size: 18px;height:58px;">
            <Icon type="ios-car" size="26"/>
            {{data.cph}}
            <Tag color="cyan" v-if="!showBxgqFlag && data.bxsj != null && data.bxsj != ''">
                终保日期：{{ data.bxsj.substring(0, 10) }}
            </Tag>
            <Tag color="red" v-if="showBxgqFlag && data.bxsj != null && data.bxsj != ''">
                终保日期：{{ data.bxsj.substring(0, 10) }}
            </Tag>
            <br>
            <Tag color="green" v-if="!showYQNssjFlag && data.nssj != null && data.nssj != ''">
                年审日期：{{ data.nssj.substring(0, 10) }}
            </Tag>
            <Tag color="red" v-if="showYQNssjFlag && data.nssj != null && data.nssj != ''">
                年审日期：{{ data.nssj.substring(0, 10) }}
            </Tag>

            <span v-if="showYQNssjFlag">
                <Poptip
                        confirm
                        title="确认已完成本次年审?"
                        @on-ok="nextNssjYear"
                        @on-cancel="cancel"
                >
                    <Button type="error" ghost>逾期未审</Button>
                </Poptip>
            </span>
            <span style="font-size: 18px;color:#ff9900">{{totalWfxx}}</span><span style="font-size: 12px;">条违法未处理</span>
        </p>
        <a href="#" slot="extra" @click.prevent="changeLimit">
            <Tag color="cyan">{{getCx(data.cx)+data.zkl+'座'}}</Tag>
        </a>
        <Row>
            <Col span="24">
                <Row v-if="data.sjxm">
                    <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                        <Icon type="ios-person" size="28" color="#2d81f0"/>
                    </Col>
                    <Col span="10">
                        <span>{{data.sjxm}}</span>
                    </Col>
                    <Col span="6" offset="4">
                        <Poptip
                                confirm
                                title="确认解除绑定?"
                                @on-ok="unbindDriver"
                                @on-cancel="cancel">
                            <Button type="text" icon="ios-trash" style="color:#ff9900;font-size:24px;margin-top: -16px;" ghost></Button>
                        </Poptip>
                    </Col>
                </Row>
                <Row v-else-if="!data.sjxm">
                    <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                        <Icon type="ios-person" size="28" color="#2d8cf0"/>
                    </Col>
                    <Col span="12">
                        <span  v-if="!bindDriverFlag">暂未绑定</span>
                        <Select v-else-if="bindDriverFlag" v-model="driverId" style="width:100%" filterable clearable>
                            <Option v-for="(item,index) in driverList" :key="item.sfzhm" :value="item.sfzhm">{{item.xm}}</Option>
                        </Select>
                    </Col>
                    <Col span="4" offset="4">
                        <Tooltip content="绑定司机" v-if="!bindDriverFlag">
                            <Button type="text" icon="md-code-working" style="color:#2db7f5;font-size:24px;margin-top: -16px;" ghost @click="chooseDriver"></Button>
                        </Tooltip>
                        <div v-else-if="bindDriverFlag">
                            <Tooltip content="绑定司机">
                                <Button type="success" shape='circle' size="small" icon="md-checkmark" @click="bindDriver"></Button>
                            </Tooltip>
                            <Tooltip content="取消绑定">
                                <Button type="error" shape='circle' size="small" icon="md-close" @click="cancelChooseDriver"></Button>
                            </Tooltip>
                        </div>
                    </Col>
                </Row>
            </Col>
        </Row>
        <Row style="margin-top: 10px;">
            <Col span="24">
                <Row v-if="data.zdbh">
                    <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                        <Icon type="ios-videocam" size="28" color="#2d8cf0"/>
                    </Col>
                    <Col span="10">
                        <span>{{data.zdbh}}</span>
                    </Col>
                    <Col span="6" offset="4">
                        <Poptip
                                confirm
                                title="确认解除绑定?"
                                @on-ok="unbindDevice"
                                @on-cancel="cancel">
                            <Button type="text" icon="ios-trash" style="color:#ff9900;font-size:24px;margin-top: -16px;" ghost></Button>
                        </Poptip>
                    </Col>
                </Row>
                <Row v-else-if="!data.zdbh">
                    <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                        <Icon type="ios-videocam" size="28" color="#2d8cf0"/>
                    </Col>
                    <Col span="12">
                        <span  v-if="!bindDeviceFlag">暂未绑定</span>
                        <Select v-else-if="bindDeviceFlag" v-model="deviceId" style="width:100%" filterable clearable sea>
                            <Option v-for="(item,index) in deviceList" :key="item.zdbh" :value="item.zdbh">{{item.zdbh}}</Option>
                        </Select>
                    </Col>
                    <Col span="4" offset="4">
                        <Tooltip content="绑定终端" v-if="!bindDeviceFlag">
                            <Button type="text" icon="md-code-working" style="color:#2db7f5;font-size:24px;margin-top: -16px;" ghost @click="chooseDevice"></Button>
                        </Tooltip>
                        <div v-else-if="bindDeviceFlag">
                            <Tooltip content="绑定终端">
                                <Button type="success" shape='circle' size="small" icon="md-checkmark" @click="bindDevice"></Button>
                            </Tooltip>
                            <Tooltip content="取消绑定">
                                <Button type="error" shape='circle' size="small" icon="md-close" @click="cancelChooseDevice"></Button>
                            </Tooltip>
                        </div>
                    </Col>
                </Row>
            </Col>
        </Row>
        <!--<Row type="flex" justify="end" style="padding-top: 20px">
            <Col span="22" style="text-align: center">
                <ButtonGroup size="large">
                    <Tooltip content="编辑">
                        <Button  icon="md-create" @click="emit('editCar')"></Button>
                    </Tooltip>
                    <Tooltip content="车辆档案">
                        <Button  icon="ios-clipboard-outline" @click="emit('showDoc')"></Button>
                    </Tooltip>
                    <Tooltip content="历史轨迹">
                        <Button  icon="ios-map-outline" @click="emit('trace')"></Button>
                    </Tooltip>
                    <Tooltip content="电子围栏">
                        <Button icon="ios-globe-outline" @click="emit('showFance')"></Button>
                    </Tooltip>
                    <Tooltip content="删除">
                        <Button icon="ios-trash" @click="emit('delCar')"></Button>
                    </Tooltip>
                </ButtonGroup>
            </Col>
        </Row>-->
    </Card>
    <Row type="flex">
        <Col span="5" style="text-align: center">
        <Tooltip content="编辑" style="width: 100%">
            <Button  icon="md-create" @click="emit('editCar')" style="width: 100%;border-top: 0px;border-right: 0px;background-color: #f8f8f9"></Button>
        </Tooltip>
        </Col>
        <Col span="5" style="text-align: center">
            <Tooltip content="违法录入" style="width: 100%">
                <Button  icon="md-add" @click="emit('addWf')" style="width: 100%;border-top: 0px;;border-right: 0px;background-color: #f8f8f9"></Button>
            </Tooltip>
        </Col>
        <Col span="5" style="text-align: center">
            <Tooltip content="历史轨迹" style="width: 100%">
                <Button  icon="ios-map-outline" @click="emit('trace')" style="width: 100%;border-top: 0px;;border-right: 0px;background-color: #f8f8f9"></Button>
            </Tooltip>
        </Col>
        <Col span="5" style="text-align: center">
            <Tooltip content="电子围栏" style="width: 100%">
                <Button icon="ios-globe-outline" @click="emit('showFance')" style="width: 100%;border-top: 0px;;border-right: 0px;background-color: #f8f8f9"></Button>
            </Tooltip>
        </Col>
        <Col span="4" style="text-align: center">
            <Tooltip content="删除" style="width: 100%">
                <Button icon="ios-trash" @click="emit('delCar')" style="width: 100%;border-top: 0px;;background-color: #f8f8f9"></Button>
            </Tooltip>
        </Col>
    </Row>
    </div>
</template>

<script>
    import moment from 'moment'
    export default {
        name: "carItem",
        data(){
          return{
              driverList:[],
              deviceList:[],
              driverId:'',
              deviceId:'',
              totalWfxx:0,
              cxDict:[],
              cxDictCode:'ZDCLK0019',
              bindDriverFlag:false,
              bindDeviceFlag:false,
              showNssjFlag:true,
              showYQNssjFlag:false,
              showBxgqFlag:false,
          }
        },
        props:{
          data:{
              type:Object,
              default:function(){
                  return {}
              }
          }
        },
        created(){
            this.bindDriverFlag = !!this.data.sjxm
            this.bindDeviceFlag = !!this.data.zdbh;
            //判断车辆是否逾期未年审
            if (this.data.nssj != null && this.data.nssj != ''){
                this.showYQNssjFlag = moment(this.data.nssj).isBefore(new Date());
            }
            //判断车辆保险是否过期
            if (this.data.bxsj != null && this.data.bxsj != ''){
                this.showBxgqFlag = moment(this.data.bxsj).isBefore(new Date());
            }
            //加载违法未处理数量
            this.loadWfxxNum();
        },
        mounted(){

        },
        methods:{
            emit(method){
                console.log(method);
                this.$emit(method,this.data);
            },
            loadWfxxNum(){
                let v = this;
                v.$http.get(this.apis.CLGL.TOTALWFXX+"/"+this.data.clId).then((res) =>{
                    if(res.code === 200){
                        this.totalWfxx = res.result;
                    }
                })
            },
            cancelChooseDriver(){
                this.bindDriverFlag = false
            },
            cancelChooseDevice(){
                this.bindDeviceFlag = false
            },
            chooseDriver(){
                this.getdriverList();
                this.bindDriverFlag = true
            },
            chooseDevice(){
                this.getDevices();
                this.bindDeviceFlag = true
            },
            bindDriver(){
                this.postAndReload(this.apis.CLGL.bindDriver,{carId:this.data.clId,driverId:this.driverId})
            },
            bindDevice(){
                this.postAndReload(this.apis.CLGL.bindDevice,{carId:this.data.clId,devcieId:this.deviceId})
            },
            unbindDriver(){
                this.driverId = '';
                this.postAndReload(this.apis.CLGL.unbindDriver,{carId:this.data.clId})
            },
            unbindDevice(){
                this.devcieId = '';
                this.postAndReload(this.apis.CLGL.unbindDevice,{carId:this.data.clId})
            },
            nextNssjYear(){
                let v = this;
                v.$http.get(this.apis.CLGL.nextNssjYear+"/"+this.data.clId).then((res) =>{
                    if(res.code === 200){
                        this.$emit('reload');
                        this.$Message.success(res.message);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
            postAndReload(url,param){
                let v = this;
                v.$http.post(url,param).then((res) =>{
                    if(res.code === 200){
                        this.$emit('reload');
                        this.$Message.success(res.message);
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            },
            getdriverList(){
                let v = this;
                v.driverList = [];
                v.$http.get(this.apis.JSY.QUERY,{params:{pageSize:1000}}).then((res) =>{
                    if(res.code===200){
                        v.driverList = res.page.list;
                    }
                })
            },
            getDevices(){
                let v = this;
                v.deviceList = []
                v.$http.post(this.apis.ZDGL.SXQUERY).then((res) =>{
                    if(res.code===200){
                        v.deviceList = res.result;
                    }
                })
            },
            ok(item){

            },
            cancel(item){

            },
            getCx(code){
                let val = this.dictUtil.getValByCode(this,this.cxDictCode,code)
                return val;
            },
        }
    }
</script>

<style scoped>

</style>