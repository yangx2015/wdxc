<template>
    <div>
        <component :is="componentName"></component>
        <Modal width='600' v-model="showModal"  title="车辆详情">
            <div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
                <Spin fix>
                    <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
                    <div style="font-size: 30px;">数据加载中请稍后</div>
                </Spin>
            </div>
            <Tabs @on-click="tabClick">
                <Tab-pane label="车辆信息" icon="information-circled">
                    <Row>
                        <Col span="2"><span>车牌号:</span></Col>
                        <Col span="6"><span>{{car.cph}}</span></Col>
                    </Row>
                    <Row>
                        <Col span="2"><span>司机:</span></Col>
                        <Col span="6"><span>{{car.sjxm}}</span></Col>
                    </Row>
                    <Row>
                        <Col span="2"><span>时速:</span></Col>
                        <Col span="6"><span>{{car.speed}}</span></Col>
                    </Row>
                </Tab-pane>
                <Tab-pane label="远程控制" icon="ios-game-controller-b">
                    <Row>
                        <Button type="primary" @click="setControl">远程拍照</Button>
                        <Button type="primary" @click="setControl">拍摄视频</Button>
                        <Button type="primary" @click="showFance">电子围栏</Button>
                        <Button type="primary" @click="showPathHistory">历史轨迹</Button>
                    </Row>
                </Tab-pane>
                <Tab-pane label="终端设置" icon="gear-b">
                    <Row>
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
                            </Row>
                        </Form>
                    </Row>
                </Tab-pane>
            </Tabs>
            <div slot='footer'>
                <Button type="ghost" @click="close">关闭</Button>
                <Button type="primary" @click="save" v-if="showConfirmButton">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import pathHistory from './pathHistory'
    export default {
        name: "carInfo",
        components: {
            pathHistory
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
                }
            }

        },
        mounted(){
            this.init();
        },
        methods:{
            tabClick(k){
                if (k === 2){
                    this.showConfirmButton = true;
                }
            },
            init(){
                setTimeout((h) => {
                    this.showModal = true;
                }, 100);
                this.car = this.$parent.choosedItem;
            },
            close(){
                this.showModal = false;
                setTimeout((t) => {
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
            showPathHistory(){
                this.$parent.$parent.componentName = 'pathHistory'
                this.close();
            },
            showFance(){
                this.$parent.showFance()
                this.close();
            }
        }
    }
</script>

<style scoped>
    .padding-top16{
        padding-top: 16px;
    }

</style>
