<template>
    <div>
        <component :is="componentName"></component>
        <Modal width="1000" v-model="showModal"  title="车辆详情" :closable="false">
            <div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
                <Spin fix>
                    <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
                    <div style="font-size: 30px;">数据加载中请稍后</div>
                </Spin>
            </div>
            <div>
                <Row :gutter="16">
                    <!--<Col span="12">-->
                        <!--<Card style="height: 300px">-->
                            <!--<p slot="title"><Icon type="information-circled"></Icon> 车辆信息</p>-->
                            <!--<Row>-->
                                <!--<Col span="2"><Icon type="card"></Icon></Col>-->
                                <!--<Col span="6"><span>{{car.cph}}</span></Col>-->
                            <!--</Row>-->
                            <!--<Row>-->
                                <!--<Col span="2"><Icon type="person"></Icon></Col>-->
                                <!--<Col span="6"><span>{{car.sjxm}}</span></Col>-->
                            <!--</Row>-->
                            <!--<Row>-->
                                <!--<Col span="2"><Icon type="speedometer"></Icon></Col>-->
                                <!--<Col span="6"><span>{{car.speed}} KM/h</span></Col>-->
                            <!--</Row>-->
                            <!--<Row v-if="obd != null">-->
                                <!--<Col span="4">更新日期</Col>-->
                                <!--<Col span="10"><span>{{formatDate(obd.creatorDate)}} {{formatTime(obd.creatortime)}}</span></Col>-->
                            <!--</Row>-->
                            <!--<Row v-if="obd != null">-->
                                <!--<Col span="4">发动机转速</Col>-->
                                <!--<Col span="10"><span>{{obd.engineSpeed}} r/min</span></Col>-->
                            <!--</Row>-->
                            <!--<Row v-if="obd != null">-->
                                <!--<Col span="4">车速</Col>-->
                                <!--<Col span="10"><span>{{obd.obdSpeed}} KM/h</span></Col>-->
                            <!--</Row>-->
                            <!--<Row v-if="obd != null">-->
                                <!--<Col span="4">剩余油量</Col>-->
                                <!--<Col span="10"><span>{{obd.syyl}} L</span></Col>-->
                            <!--</Row>-->
                            <!--<Row v-if="obd != null">-->
                                <!--<Col span="4">耗油量</Col>-->
                                <!--<Col span="10"><span>{{obd.hyl}} L</span></Col>-->
                            <!--</Row>-->
                        <!--</Card>-->
                    <!--</Col>-->
                    <Col span="12">
                        <Card style="height: 400px">
                            <p slot="title"><Icon type="ios-game-controller-b"></Icon> 远程控制</p>
                            <Row>
                                <Col span="8">
                                    <Button @click="setControl('12','0-10')" icon="camera">前摄像头拍照</Button>
                                </Col>
                                <Col span="8">
                                    <Button @click="setControl('12','1-10')" icon="camera">后摄像头拍照</Button>
                                </Col>
                                <Col span="8">
                                    <Button @click="setControl('12','2-10')" icon="camera">前后摄像头拍照</Button>
                                </Col>
                            </Row>
                            <Row>
                                <Col span="8">
                                    <Button @click="setControl('11','0-10')" icon="ios-videocam">前摄像头视频</Button>
                                </Col>
                                <Col span="8">
                                    <Button @click="setControl('11','1-10')" icon="ios-videocam">后摄像头视频</Button>
                                </Col>
                                <Col span="8">
                                    <Button @click="setControl('11','2-10')" icon="ios-videocam">前后摄像头视频</Button>
                                </Col>
                            </Row>
                            <!--<Row>-->
                                <!--<Col span="8">-->
                                    <!--<DatePicker  type="datetime" format="yyyy-MM-dd HH:mm:ss" v-model="mergeVideoParam.startTime" placeholder="请选择开始时间"></DatePicker >-->
                                <!--</Col>-->
                                <!--<Col span="8">-->
                                    <!--<DatePicker  type="datetime" format="yyyy-MM-dd HH:mm:ss" v-model="mergeVideoParam.endTime" placeholder="请选择结束时间"></DatePicker>-->
                                <!--</Col>-->
                                <!--<Col span="8">-->
                                    <!--<Button @click="mergeVideo(1)" icon="qr-scanner">合并视频</Button>-->
                                <!--</Col>-->
                            <!--</Row>-->
                            <Row>
                                <Col span="8">
                                    <Button @click="showFance" icon="qr-scanner">电子围栏</Button>
                                </Col>
                                <Col span="8">
                                    <Button @click="showPathHistory" icon="pull-request">历史轨迹</Button>
                                </Col>
                            </Row>
                        </Card>
                    </Col>
                    <Col span="12">
                        <Card style="height: 400px">
                            <p slot="title"><Icon type="gear-b"></Icon> 终端设置</p>
                            <Row class="height300">
                                <Form :label-width="100">
                                    <Row>
                                        <!--<Col span="12">-->
                                            <!--<FormItem label='急加速灵敏度'>-->
                                                <!--<Select filterable clearable  v-model="carControl.jslmd" @on-change="selectChange02">-->
                                                    <!--<Option value="1">1</Option>-->
                                                    <!--<Option value="2">2</Option>-->
                                                    <!--<Option value="3">3</Option>-->
                                                    <!--<Option value="4">4</Option>-->
                                                    <!--<Option value="5">5</Option>-->
                                                    <!--<Option value="6">6</Option>-->
                                                <!--</Select>-->
                                            <!--</FormItem>-->
                                        <!--</Col>-->
                                        <Col span="20">
                                            <FormItem label='碰撞灵敏度'>
                                                <Select filterable clearable  v-model="carControl.pzlmd">
                                                    <Option value=""></Option>
                                                    <Option value="00">低</Option>
                                                    <Option value="10">中</Option>
                                                    <Option value="20">高</Option>
                                                </Select>
                                            </FormItem>
                                        </Col>
                                        <Col span="4">
                                            <Button type="primary"  @click="selectChange20">确定</Button>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col span="20">
                                            <FormItem label='上传模式'>
                                                <Select filterable clearable  v-model="carControl.scms">
                                                    <Option value=""></Option>
                                                    <Option value="00">实时</Option>
                                                    <Option value="10">仅WIFI</Option>
                                                </Select>
                                            </FormItem>
                                        </Col>
                                        <Col span="4">
                                            <Button type="primary"  @click="selectChange30">确定</Button>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col span="20">
                                            <FormItem label='上传视屏模式'>
                                                <Select filterable clearable  v-model="carControl.scspms">
                                                    <Option value=""></Option>
                                                    <Option value="00">全部视屏</Option>
                                                    <Option value="10">仅碰撞视屏</Option>
                                                </Select>
                                            </FormItem>
                                        </Col>
                                        <Col span="4">
                                            <Button type="primary"  @click="selectChange50">确定</Button>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col span="20">
                                            <FormItem label='超速设定'>
                                                <Input type="text" v-model="carControl.cssd" placeholder="请填写超速设定..."><span slot="append">KM/h</span></Input>
                                            </FormItem>
                                        </Col>
                                        <Col span="4">
                                            <Button type="primary"  @click="setCssd">确定</Button>
                                        </Col>
                                    </Row>
                                    <Row>
                                        <Col span="20">
                                            <FormItem label='GPS心跳间隔'>
                                                <Input type="text" v-model="carControl.gpsxtjg" placeholder="请填写心跳间隔..."><span slot="append">秒</span></Input>
                                            </FormItem>
                                        </Col>
                                        <Col span="4">
                                            <Button type="primary"  @click="saveXtjg">确定</Button>
                                        </Col>
                                    </Row>
                                    <Row>
                                    </Row>
                                </Form>
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
                    scms:1,
                    jslmd:'',
                    pzlmd:'',
                    scspms:'',
                    cssd:'',
                    gpsxtjg:'',
                },
                mergeVideoParam:{
                    startTime:'',
                    endTime:''
                },
                obd:null
            }

        },
        created(){
            this.init();
        },
        mounted(){
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
            setCssd(){
                var v = this
                let params = {
                    cphs:this.car.cph,
                    csz:this.carControl.cssd,
                }
                this.SpinShow = true;
                this.$http.post(configApi.CS.ADD,params).then((res) =>{
                    this.SpinShow = false;
                    if (res.code === 200){
                        this.$Message.success("设置成功!")
                    }else{
                        this.$Message.error(res.message)
                    }
                })
            },
            saveCssd(){
                this.setting('01',this.carControl.cssd);
            },
            saveXtjg(){
                this.setting('40',this.carControl.gpsxtjg);
            },
            selectChange02(e){
                this.setting('02',e);
            },
            selectChange20(){
                this.setting('20',this.carControl.pzlmd);
            },
            selectChange30(){
                this.setting('30',this.carControl.scms);
            },
            selectChange50(){
                this.setting('50',this.carControl.scspms);
            },
            setting(type,param){
                var v = this
                let params = {
                    deviceId:this.car.zdbh,
                    cmdType:type,
                    cmd:param
                }
                this.SpinShow = true;
                this.$http.post(configApi.CLJK.SEND_CONTROLL,params).then((res) =>{
                    this.SpinShow = false;
                    if (res.code === 200){
                        this.$Message.success("设置成功!")
                    }else{
                        this.$Message.error(res.message)
                    }
                })
            },
            init(){
                setTimeout(() => {
                    this.showModal = true;
                }, 100);
                this.car = this.$parent.choosedItem;
                this.car.obdId = '101601190228'
                this.getDeviceInfo();
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
            /**deviceId:设备id  cmdType:11拍视频  12拍照片 13合并视屏 (三选一)
             *
             * 	 * cmdType 为11和12的时候使用
             * 参数格式为分隔式字符串  如:0-10 前一个0 标识要抓拍的摄像头  后一个10标识当前时间点前后十秒
             * 摄像头参数如下:0,前后都抓拍, 1表示仅前摄像头, 2表示仅仅后摄像头。当cmdType为12的时候，此参数也是一样，只是抓拍前后多少秒参数无效【客户端自动判断，后台传递参数即可】
             * cmdType 为13的时候参数是0-0 或者1-0  ，特别注意，为13的时候，startTime和endTime必须有值
             * 摄像头参数如下:0 合并前摄像头  1 合并后摄像头  2 合并内置摄像头【内置摄像头这个暂时无法使用】
             */
            setControl(type,param){
                var v = this
                let params = {
                    deviceId:this.car.zdbh,
                    cmdType:type,
                    cmdParams:param
                }
                this.SpinShow = true;
                this.$http.post(configApi.CLJK.SEND_CONTROLL,params).then((res) =>{
                    this.SpinShow = false;
                    if (res.code === 200){
                        this.$Message.success("发送成功!")
                    }else{
                        this.$Message.error(res.message)
                    }
                })
            },
            mergeVideo(param){
                let params = {
                    deviceId:this.car.zdbh,
                    cmdType:13,
                    cmdParams:param,
                    startTime:this.mergeVideoParam.startTime,
                    endTime  :this.mergeVideoParam.endTime,
                }
                this.SpinShow = true;
                this.$http.post(configApi.CLJK.SEND_CONTROLL,params).then((res) =>{
                    this.SpinShow = false;
                    if (res.code === 200){
                        this.$Message.success("发送成功!")
                    }else{
                        this.$Message.error(res.message)
                    }
                })
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
            getDeviceInfo(){
                var v = this
                this.$http.get(configApi.ZDGL.GET_BY_ID+this.car.zdbh).then((res) =>{
                    console.log('getDeviceInfo');
                    if (res.code === 200){
                        this.carControl = res.result;
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
    .height300{
        height: 300px;
    }
</style>
