<template>
    <div>
        <Input placeholder="车牌号" v-model="cph" @on-change="getList"></Input>
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
                cph:'',
                columns: [
                    {type:"selection"},
                    {title:"车牌号",key:'cph'},
                    {title:"车型",render:(h,p)=>{
                        return h("div",this.getCx(p.row.cx));
                    }},
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
            getCx(c){
              switch (c) {
                  case '10': return '小车';
                  case '20': return '大车';
                  case '30': return '校巴';
                  default:return "小车"
              }
            },
            cphChange(e) {
                console.log(e);
            },
            selectionChange(e) {
                this.$emit("carChange",e);
                console.log(e);
            },
            getList(){
                this.tableData = JSON.parse(JSON.stringify(this.selectedData));
                this.$http.get(this.apis.CD.notBindCarList).then((res)=>{
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
