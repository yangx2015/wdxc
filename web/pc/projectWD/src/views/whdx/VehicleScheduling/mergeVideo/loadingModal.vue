<template>
    <div>
        <Modal v-model="showModal" width="600" height="300"
               :closable='false'
               :mask-closable="false">
            <div style="height: 400px;">
                <div v-if="SpinShow1" style="width:80%;height:80%;z-index: 1111;text-align: center">
                    <Spin fix style="width:80%;height: 80%;text-align: center;position:inherit">
                        <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
                        <div style="font-size: 30px;">文件上传中请稍后...</div>
                    </Spin>
                </div>
            </div>
            <div slot='footer' style="text-align: center;">
                <Button type="primary" @click="close">取消等待</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: "loading",
        data(){
            return{
                showModal:true,
                SpinShow1:true,
            }
        },
        methods:{
            close(){
                var v = this
                v.$parent.componentName = ''
            },
        }
    }
</script>

<style scoped>

</style>