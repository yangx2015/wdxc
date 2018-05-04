<template>
    <div>
        <Modal
                v-model="showModal"
                height="400"
                :closable='false'
                :mask-closable="false"
                :title="drveName+'用户'">
            <div>
                12132
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
                drveName:'hello'
            }
        },
        created(){
        },
        methods:{
            colse(){
                var v = this
                v.$parent.compName = ''
            }
        }
    }
    //15271928827
</script>

<style>
</style>
