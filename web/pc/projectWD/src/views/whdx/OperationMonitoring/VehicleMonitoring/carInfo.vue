<template>
    <div>
        <component :is="componentName"></component>
        <Modal width="1000" v-model="showModal"  title="车辆详情">
            <div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
                <Spin fix>
                    <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
                    <div style="font-size: 30px;">数据加载中请稍后</div>
                </Spin>
            </div>
            <div>
                <Row :gutter="16">
                    <Col span="12">
                        <Card style="height: 200px">
                            <p slot="title"><Icon type="information-circled"></Icon> 车辆信息</p>
                            <Row>
                                <Col span="2"><Icon type="card"></Icon></Col>
                                <Col span="6"><span>{{car.cph}}</span></Col>
                            </Row>
                            <Row>
                                <Col span="2"><Icon type="person"></Icon></Col>
                                <Col span="6"><span>{{car.sjxm}}</span></Col>
                            </Row>
                            <Row>
                                <Col span="2"><Icon type="speedometer"></Icon></Col>
                                <Col span="6"><span>{{car.speed}}</span></Col>
                            </Row>
                        </Card>
                    </Col>
                    <Col span="12">
                        <Card style="height: 200px">
                            <p slot="title"><Icon type="ios-game-controller-b"></Icon> 远程控制</p>
                            <Row>
                                <Button  @click="setControl" icon="camera">远程拍照</Button>
                                <Button  @click="setControl" icon="ios-videocam">拍摄视频</Button>
                                <Button  @click="showFance" icon="qr-scanner">电子围栏</Button>
                                <Button  @click="showPathHistory" icon="pull-request">历史轨迹</Button>
                            </Row>
                        </Card>
                    </Col>
                </Row>
                <br>
                <Row :gutter="16">
                    <Col span="12">
                        <Card style="height: 300px">
                            <p slot="title"><Icon type="gear-b"></Icon> 终端设置</p>
                            <Row class="height200">
                                <Form :label-width="100">
                                    <Row>
                                        <Col span="12">
                                            <FormItem label='传感器灵敏度'>
                                                <Select filterable clearable  v-model="carControl.lmd">
                                                    <Option value="1">低</Option>
                                                    <Option value="2">中</Option>
                                                    <Option value="3">高</Option>
                                                </Select>
                                            </FormItem>
                                        </Col>
                                        <Col span="12">
                                            <FormItem label='上传模式'>
                                                <Select filterable clearable  v-model="carControl.scms">
                                                    <Option value="1">仅WIFI</Option>
                                                    <Option value="2">全部</Option>
                                                    <Option value="3">不上传</Option>
                                                </Select>
                                            </FormItem>
                                        </Col>
                                        <Col span="12">
                                            <FormItem label='超速设定'>
                                                <Input type="text" v-model="carControl.cssd" placeholder="请填写超速设定..."></Input>
                                            </FormItem>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Button type="primary"  @click="save">确定</Button>
                                    </Row>
                                </Form>
                            </Row>
                        </Card>
                    </Col>
                    <Col span="12">
                        <Card style="height: 300px" v-if="obd !== null">
                            <p slot="title"><Icon type="information-circled"></Icon> OBD信息</p>
                            <Row>
                                <Col span="4">创建日期</Col>
                                <Col span="10"><span>{{formatDate(obd.creatorDate)}} {{formatTime(obd.creatortime)}}</span></Col>
                            </Row>
                            <Row>
                                <Col span="4">发动机转速</Col>
                                <Col span="10"><span>{{obd.engineSpeed}} r/min</span></Col>
                            </Row>
                            <Row>
                                <Col span="4">车速</Col>
                                <Col span="10"><span>{{obd.obdSpeed}} KM/h</span></Col>
                            </Row>
                            <Row>
                                <Col span="4">剩余油量</Col>
                                <Col span="10"><span>{{obd.syyl}} L</span></Col>
                            </Row>
                            <Row>
                                <Col span="4">耗油量</Col>
                                <Col span="10"><span>{{obd.hyl}} L</span></Col>
                            </Row>
                        </Card>
                    </Col>
                </Row>
                <br>
            </div>
            <div slot='footer'>
                <Button type="ghost" @click="close">关闭</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import configApi from '@/axios/config.js'
    export default {
        name: "carInfo",
        components: {
        },
        data(){
            return {
                showConfirmButton:false,
                componentName:'',
                showModal:true,
                SpinShow:false,
                car:'',
                carControl:{
                    lmd:1,
                    scms:1
                },
                obd:null
            }

        },
        created(){
            console.log('created');
            this.init();
        },
        mounted(){
            console.log('mounted');
        },
        methods:{
            formatDate(date){
              return date.substring(0,4)+'年'+date.substring(4,6)+"月"+date.substring(6,8)+"日";
            },
            formatTime(time){
              return time.substring(0,2)+':'+time.substring(2,4)+":"+time.substring(4,6);
            },
            tabClick(k){
                this.showConfirmButton = k === 2
            },
            init(){
                console.log('init');
                setTimeout(() => {
                    console.log(this.showModal);
                    this.showModal = true;
                }, 100);
                this.car = this.$parent.choosedItem;
                this.car.obdId = '301404140001'
                if (this.car.obdId){
                    this.getObdInfo();
                }
            },
            close(){
                this.showModal = false;
                setTimeout(() => {
                    this.$parent.$data.componentName = "";
                }, 200)
            },
            save(){
                this.SpinShow = true;
                setTimeout(()=>{
                    this.$Message.success("操作成功!")
                    this.SpinShow = false;
                    this.init();
                },1000)
            },
            setControl(type){
                this.SpinShow = true;
                setTimeout(()=>{
                    this.$Message.success("发送成功!")
                    this.SpinShow = false;
                },1000)
            },
            getObdInfo(){
                var v = this
                this.$http.post(configApi.CLJK.getObdTimely,{obdId:this.car.obdId}).then((res) =>{
                    if (res.code === 200){
                        this.obd = res.result;
                        console.log(res);
                    }
                })
            },
            showPathHistory(){
                this.$router.push({name: 'historypath',params:{zdbh:this.car.zdbh}});
                this.close();
            },
            showFance(){
                this.$parent.showFance(this.car.clid)
                this.close();
            }
        }
    }
</script>

<style scoped>
    .padding-top16{
        padding-top: 16px;
    }
    .height200{
        height: 200px;
    }

</style>
