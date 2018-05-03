<template>
    <div>
        <Modal
                v-model="showModal"
                width='800'
                :closable='false'
                :mask-closable="false"
                title="车辆档案信息">
            <Form
                v-model="otherMess"
                :label-width="120"
                :styles="{top: '20px'}">
                <div>
                    <Row>
                        <Col span="12">
                            <FormItem label='车牌号：'>
                                <Input readonly type="text" v-model="otherMess.cph" placeholder="请设置车牌号"></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label='生产商：'>
                                <Input type="text" v-model="otherMess.scs" placeholder="请填写生产商名称"></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label='车辆型号：'>
                                <Input type="text" v-model="otherMess.xh" placeholder="请填写车辆型号"></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label='初次登记日期：'>
                                <DatePicker type="datetime" v-model="otherMess.ccdjrq"
                                            style="width: 100%"
                                            placeholder="请选着初次登记日期"></DatePicker>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label='保险公司名称：'>
                                <Input type="text" v-model="otherMess.bxgsmc" placeholder="请填写保险公司名称"></Input>
                            </FormItem>
                        </Col>
                        <Col span="12">
                            <FormItem label='保险时间：'>
                                <DatePicker type="datetime" v-model="otherMess.bxsj"
                                            style="width: 100%"
                                            placeholder="请选着保险时间"></DatePicker>
                            </FormItem>
                        </Col>
                    </Row>
                </div>
            </Form>
            <div slot='footer'>
                <Button type="ghost" @click="colse">取消</Button>
                <Button type="primary" @click="AddDataListOk()">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import configApi from '@/axios/config.js'

    export default {
        name:'',
        data(){
            return {
                showModal:true,
                //车辆配置信息
                otherMess:{
                    cph:'123'
                },
            }
        },
        props:{
            mess:{
                type:Object,
                default:{}
            }
        },
        created(){
            log('数据传输',this.mess)
            this.otherMess = this.mess

        },
        methods:{
            colse(){
                var v = this
                v.$parent.compName = ''
            },
            //确认添加新用户进行前台表单数据验证
            AddDataListOk(){
                var v = this
//                    	新增
                delete v.otherMess.clDzwlCl;
                delete v.otherMess.clDzwl;
                v.$http.post(configApi.CLGL.CHANGE,v.otherMess).then((res) =>{
                    if(res.code===200){
                        v.$Message.success('车辆修改成功');
                    }else{
                        v.$Message.error('车辆修改失败');
                    }
                    v.$parent.getmess()
                    v.$parent.compName = ''
                })
            },
        }
    }
    //15271928827
</script>

<style>
</style>
