<template>
    <div>
        <Modal
                v-model="showModal"
                height="400"
                :closable='false'
                :mask-closable="false"
                :title="drveName+'用户'">
            <div>
                <Table border
                       ref="selection"
                       :columns="columns4"
                       :data="data1"
                       @on-selection-change="selectionClick"></Table>
            </div>
            <div slot='footer'>
                <Button type="ghost" @click="colse">取消</Button>
                <Button type="primary" @click="colse">确定</Button>
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
                drveName:'hello',columns4: [
                    {
                        type: 'selection',
                        width: 60,
                        align: 'center'
                    },
                    {
                        title: 'Name',
                        key: 'name'
                    },
                    {
                        title: 'Age',
                        key: 'age'
                    },
                    {
                        title: 'Address',
                        key: 'address'
                    }
                ],
                data1: [
                    {
                        name: 'John Brown',
                        age: 18,
                        address: 'New York No. 1 Lake Park',
                        date: '2016-10-03'
                    },
                    {
                        name: 'Jim Green',
                        age: 24,
                        address: 'London No. 1 Lake Park',
                        date: '2016-10-01'
                    },
                    {
                        name: 'Joe Black',
                        age: 30,
                        address: 'Sydney No. 1 Lake Park',
                        date: '2016-10-02'
                    },
                    {
                        name: 'Jon Snow',
                        age: 26,
                        address: 'Ottawa No. 2 Lake Park',
                        date: '2016-10-04'
                    }
                ]

            }
        },
        created(){
        },
        methods:{
            getDfp(){
                this.$http.post(configApi.ORDER.DFP,{}).then((res) =>{
                    console.log(res)
                })
            },
            addlist(){

            },
            colse(){
                var v = this
                v.$parent.compName = ''
            },
            selectionClick(event){
                console.log('*************************',event)
            }
        }
    }
    //15271928827
</script>

<style>
</style>
