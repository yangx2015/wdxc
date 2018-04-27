<style lang="less">
    @import "../../../../../styles/common.less";
    #abnormal{
    }
</style>
<template>
    <div id="abnormal" style="height: 100%;">
        <Table
                border
                :height="tabHeight"
                :columns="columns1"
                :data="tabmess"></Table>
    </div>
</template>
<script>
    import mixins from '@/mixins'
    export default {
        name:'',
        mixins: [mixins],
        data () {
            return {
                tabHeight:'220',
                columns1: [
                    {
                        title: '序号',
                        type: 'index',
                        width: 80,
                        align: 'center'
                    },
                    {
                        title: '异常事件',
                        key: 'eventType',
                        render: (h, params) => {
                        	let val = this.dictUtil.getValByCode(this,this.sjBM,params.row.eventType)
							return val
//                          switch (params.row.eventType){
//                              case "80":
//                                  return '正常';
//                          }
                        }
                    },
                    {
                        title: '车辆编号',
                        key: 'cph',
                        align: 'center'
                    },
                    {
                        title: '事发时间',
                        key: 'time',
                        align: 'center',
                        render: (h, params) => {
                            return this.getDJC(params.row.time)
                        }
                    }
                ],
                sjBM:'ZDCLK0038',
                sjDIC:[]
            }
        },
        props:{
            tabmess:{
                type:Array,
                default:[]
            }
        },
        created(){
        },
        mounted(){
            this.getalert()
        },
        methods:{
        	getLXDic(){
                this.sjDIC = this.dictUtil.getByCode(this,this.sjBM);
			},
            getalert(){
                var windowHeight = window.innerHeight
                this.tabHeight = windowHeight/2 - 130
//				this.tabHeight = windowHeight - 200
                console.log('浏览器高',this.tabHeight)
            }
        }
    }
</script>
