<template>
    <div>
        <Modal v-model="showModal" width="600"
               :closable='false'
               :mask-closable="false">
            <div style="height: 400px;">
                <div v-if="SpinShow" style="width:100%;height:100%;z-index: 1111;">
                    <Spin fix>
                        <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
                        <div style="font-size: 30px;">文件上传中请稍后...</div>
                    </Spin>
                </div>
            </div>
            <div slot='footer'>
                <Button type="ghost" @click="close">取消</Button>
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
                SpinShow:true,
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