<template>
      <div id="lineModel" style="overflow: auto">
            <div class="box-row-z">
                  <div class="itemSty" v-for="(item,index) in mess">
                        <Tooltip class="changeBut"  placement="top" content="站点信息修改">
                              <Button size="small" type="success" shape="circle" icon="edit" @click="codeChange(item,index)"></Button>
                        </Tooltip>
                        <i v-show="index!=mess.length-1" class="iconfont icon-one-line-arrow"></i>

                        <div class="name">
                              <div v-for="it in item.name.split('')">
                                    {{it}}
                              </div>

                        </div>
                        <!--<Tooltip class="deleBut"  placement="bottom" content="站点移除">-->
                              <!--<Button size="small" type="error" shape="circle" icon="trash-a"></Button>-->
                        <!--</Tooltip>-->

                  </div>

            </div>
      </div>
      
</template>

<script>
    export default {
        name: "line",
        props:{
            mess:{
                type:Array,
                default:[
                    // {
                    //     name:'你好啊'
                    // },{
                    //     name:'你好啊666666'
                    // },{
                    //     name:'你好啊'
                    // },{
                    //     name:'你好啊'
                    // },
                ]
            }
        },
        watch:{
            mess:(n,o)=>{
                console.log(n);
            }
        },
        created(){
            console.log(this.mess)
            // this.mess.forEach((item,index)=>{
            //     item.name.split('')
            // })

        },
        methods:{
            codeChange(item,index){
                var abj ={
                    item:item,
                    index:inex
                }
                this.$emit('codeMess',abj)
            }
        }
    }
</script>

<style lang="less">
      #lineModel{
            width: 100%;
            height: 100%;
            padding: 0 8px;
            .itemSty{
                  position: relative;
                  width: 80px;
                  height: 100%;
                  padding: 10px 0 30px 0;
                  .icon-9{
                        font-size: 24px;
                        color: #57a3f3;
                  }
                  .icon-one-line-arrow{
                        font-size: 40px;
                        font-weight: 900;
                        color: #57a3f3;
                        position: absolute;
                        left: 36px;
                        top: -8px;
                  }
                  .name{
                        font-size: 16px;
                        margin-bottom: 10px;
                        transform: translateX(4px);
                  }
                  .deleBut{
                        position: absolute;
                        buttom: 4px;
                  }
            }
      }
</style>