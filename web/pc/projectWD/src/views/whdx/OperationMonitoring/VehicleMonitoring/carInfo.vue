<template>
    <div>
        <Modal width='900' v-model="showModal"  title="车辆详情">
            <div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
                <Spin fix>
                    <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
                    <div style="font-size: 30px;">数据加载中请稍后</div>
                </Spin>
            </div>
            <div style="overflow: auto;height: 300px;">

            </div>
            <div slot='footer'>
                <Button type="ghost" @click="close">取消</Button>
                <Button type="primary" @click="save">确定</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: "carInfo",
        data(){
            return {
                showModal:true,
                SpinShow:false,

            }

        },
        methods:{
            close(){

            },
            save(){

            }
        }
    }
</script>

<style scoped>

</style>