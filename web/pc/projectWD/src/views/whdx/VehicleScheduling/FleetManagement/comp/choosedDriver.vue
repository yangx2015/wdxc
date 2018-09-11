<template>
    <div>
        <Input placeholder="姓名" v-model="xm" @on-change="getList"></Input>
        <Table
                :height="300"
                size="large"
                stripe
                :columns="columns"
                :data="tableData"
                @on-selection-change="selectionChange"></Table>
    </div>
</template>

<script>
    export default {
        name: "choosedCar",
        data() {
            return {
                xm:'',
                columns: [
                    {type:"selection"},
                    {title:"姓名",key:'xm'},
                ],
                tableData: [],
            }
        },
        props:{
            selectedData:{
                type:Array,
                default:[]
            }
        },
        created(){
            if (this.selectedData.length !== 0){
                for (let r of this.selectedData){
                    r._checked = true;
                }
            }
            this.getList();
        },
        methods: {
            selectionChange(e) {
                this.$emit("driverChange",e);
            },
            getList(){
                this.tableData = JSON.parse(JSON.stringify(this.selectedData));
                this.$http.get(this.apis.CD.notBindDriverList).then((res)=>{
                    if (res.code === 200 && res.result){
                        for (let r of res.result){
                            this.tableData.push(r);
                        }
                    }else{
                        this.$Message.error(res.message);
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>
