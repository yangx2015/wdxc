<template>
    <Col span="6" v-for="(item,index) in tableData" :key="index" style="margin-top: 16px;">
        <Card>
            <p slot="title" style="font-size: 18px">
                <Icon type="ios-car" size="24"/>
                {{item.cph}}
            </p>
            <a href="#" slot="extra" @click.prevent="changeLimit">
                <Tag color="cyan">{{getCx(item.cx)+item.zkl+'座'}}</Tag>
            </a>
            <Row>
                <Col span="24">
                    <Row v-if="item.sjxm">
                        <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                            <Icon type="md-person" size="28" color="#2d8cf0"/>
                        </Col>
                        <Col span="10">
                            <span>{{item.sjxm}}</span>
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
                    <Row v-else-if="!item.sjxm">
                        <Col span="2" style="padding-right: 45px;margin-top: -5px;">
                            <Icon type="md-person" size="28" color="#2d8cf0"/>
                        </Col>
                        <Col span="10">
                            <span  v-if="!cell.bindDrvFlag">暂未绑定</span>
                            <Select v-else-if="cell.bindDrvFlag" :ref="'driverSelect_'" style="width:150px" clearable @on-clear="()=>{cell.bindDrvFlag=false}">
                                <Option value="10">11</Option>
                            </Select>
                        </Col>
                        <Col span="4" offset="6">
                            <Tooltip content="绑定司机" v-if="!cell.bindDrvFlag">
                                <Button type="text" icon="md-code-working" style="color:#2db7f5;font-size:24px;margin-top: -16px;" ghost @click="()=>{cell.bindDrvFlag=true}">绑定1</Button>
                            </Tooltip>
                            <Button v-else-if="cell.bindDrvFlag" type="info" ghost >绑定2</Button>
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
                            <span v-if="!item.bindTremFlag">暂未绑定</span>
                            <Select v-else-if="item.bindTremFlag" style="width:150px" clearable @on-clear="()=>{item.bindTremFlag=false}">
                                <Option value="10">{{item.zdbh}}</Option>
                            </Select>
                        </Col>
                        <Col span="4" offset="6">
                            <Tooltip content="绑定设备" v-if="!item.bindTremFlag">
                                <Button type="text" icon="md-code-working" style="color:#2db7f5;font-size:24px;margin-top: -16px;" ghost  @click="()=>{cellData.bindTremFlag=true}"></Button>
                            </Tooltip>
                            <Button v-else-if="item.bindTremFlag" type="info" ghost >绑定</Button>
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
        name: "carItem"
    }
</script>

<style scoped>

</style>