<template>
    <Col span="6" style="margin-top: 16px;">
        <Card>
            <p slot="title" style="font-size: 18px">
                <Icon type="ios-car" size="24"/>
                {{data.cph}}
            </p>
            <a href="#" slot="extra" @click.prevent="changeLimit">
                <Tag color="cyan">{{getCx(data.cx)+data.zkl+'座'}}</Tag>
            </a>
            <Row>
                <Col span="24">
                    <Row v-if="data.sjxm">
                        <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                            <Icon type="md-person" size="28" color="#2d8cf0"/>
                        </Col>
                        <Col span="10">
                            <span>{{data.sjxm}}</span>
                        </Col>
                        <Col span="4" offset="6">
                            <Poptip
                                    confirm
                                    title="确认解除绑定?"
                                    @on-ok="ok"
                                    @on-cancel="cancel">
                                <Button type="text" icon="ios-trash" style="color:#ff9900;font-size:24px;margin-top: -16px;" ghost></Button>
                            </Poptip>
                        </Col>
                    </Row>
                    <Row v-else-if="!data.sjxm">
                        <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                            <Icon type="md-person" size="28" color="#2d8cf0"/>
                        </Col>
                        <Col span="10">
                            <span  v-if="!bindDriverFlag">暂未绑定</span>
                            <Select v-else-if="bindDriverFlag" :ref="'driverSelect_'" style="width:150px" clearable @on-clear="()=>{bindDriverFlag=false}">
                                <Option v-for="(item,index) in drivers" :key="item.id" value="10">{{item.sjxm}}</Option>
                            </Select>
                        </Col>
                        <Col span="4" offset="6">
                            <Tooltip content="绑定司机" v-if="!bindDriverFlag">
                                <Button type="text" icon="md-code-working" style="color:#2db7f5;font-size:24px;margin-top: -16px;" ghost @click="bindDriver">绑定1</Button>
                            </Tooltip>
                            <Button v-else-if="bindDriverFlag" type="info" ghost >绑定2</Button>
                        </Col>
                    </Row>
                </Col>
            </Row>
            <Row>
                <Col span="24">
                    <Row>
                        <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                            <Icon type="md-videocam" size="28" color="#2d8cf0"/>
                        </Col>
                        <Col span="10" ref="termRef">
                            <span v-if="!bindDeviceFlag">暂未绑定</span>
                            <Select v-else-if="bindDeviceFlag" style="width:150px" clearable @on-clear="()=>{bindDeviceFlag=false}">
                                <Option value="10">{{data.zdbh}}</Option>
                            </Select>
                        </Col>
                        <Col span="4" offset="6">
                            <Tooltip content="绑定设备" v-if="!bindDeviceFlag">
                                <Button type="text" icon="md-code-working" style="color:#2db7f5;font-size:24px;margin-top: -16px;" ghost  @click="bindDevice"></Button>
                            </Tooltip>
                            <Button v-else-if="bindDeviceFlag" type="info" ghost >绑定</Button>
                        </Col>
                    </Row>
                </Col>
            </Row>
            <Row type="flex" justify="end" style="padding-top: 20px">
                <Col span="22">
                    <ButtonGroup size="large">
                        <Tooltip content="编辑">
                            <Button  icon="ios-clipboard"></Button>
                        </Tooltip>
                        <Tooltip content="车辆档案">
                            <Button  icon="ios-bookmarks"></Button>
                        </Tooltip>
                        <Tooltip content="历史轨迹">
                            <Button  icon="md-map"></Button>
                        </Tooltip>
                        <Tooltip content="电子围栏">
                            <Button icon="ios-globe"></Button>
                        </Tooltip>
                        <Tooltip content="删除">
                            <Button icon="md-trash"></Button>
                        </Tooltip>
                    </ButtonGroup>
                </Col>
            </Row>
        </Card>
    </Col>
</template>

<script>
    export default {
        name: "carItem",
        data(){
          return{
              drivers:[],
              cxDict:[],
              cxDictCode:'ZDCLK0019',
              bindDriverFlag:false,
              bindDeviceFlag:false,
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
            this.bindDeviceFlag = !!this.data.zdbh
        },
        methods:{
            bindDriver(){
                this.bindDriverFlag = true
            },
            bindDevice(){
                this.bindDeviceFlag = true
            },
            getDrivers(){
                let v = this;
                v.$http.get(this.apis.JSY.QUERY,{params:{pageSize:1000}}).then((res) =>{
                    if(res.code===200){
                        v.drivers = res.page.list;
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