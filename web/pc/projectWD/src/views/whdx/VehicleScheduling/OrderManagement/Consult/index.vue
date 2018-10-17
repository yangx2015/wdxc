<!--订单查阅-->
<style lang="less">
      @import '../../../../../styles/common.less';
</style>
<template>
      <div class="box boxbackborder">
            <Row class="margin-top-10" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
                  <div style="height: 45px;line-height: 45px;">
                        <div class="margin-top-10 box-row">
                              <div class="titmess">
                                    <span>订单查询</span>
                              </div>
                              <div class="body-r-1 inputSty">
                                    <Cascader
                                            style="width:300px;float: right;margin-top: 7px;margin-left: 4px;padding-right: 16px;"
                                            @on-change="change" change-on-select :data="orgTree"
                                            placeholder="请选择用车单位" filterable clearable></Cascader>
                                    <Input v-model="param.ckLike" type="text" placeholder="输入乘客姓名查询"
                                           style="width: 220px"></Input>
                                    <Input v-model="param.sjxmLike" type="text" placeholder="输入司机姓名查询"
                                           style="width: 220px"></Input>
                              </div>
                              <div class="butevent">
                                    <Button type="primary" @click="findMessList()">
                                          <Icon type="md-search"></Icon>
                                          <!--查询-->
                                    </Button>
                              </div>
                        </div>
                  </div>
            </Row>
            <!--<div class="body">-->
            <Row>
                  <Table ref="table"
                         :height="pageHeight"
                         :row-class-name="rowClassName"
                         :columns="columns10"
                         :data="data9"></Table>
            </Row>
            <Row class="margin-top-10" style="text-align: right;">
                  <Page
                          :total=pageTotal
                          :current=param.pageNum
                          :page-size=param.pageSize :page-size-opts=[8,10,20,30,40,50]
                          @on-page-size-change='(e)=>{param.pageSize=e;pageChange()}'
                          show-total
                          show-elevator show-sizer placement='top'
                          @on-change='pageChange'></Page>
            </Row>
            <component
				      :is="compName"
				      :row='listMess'
				    >
				    </component>
      </div>
</template>
<script>
    import mixins from '@/mixins'

    import expandRow from './table-expand.vue';
    
    import jnxx from './comp/BasicsMess'
    import lcjl from './comp/flowRecord'
    import clgj from './comp/trajectory'
    import ysdj from './comp/Original'

    export default {
        components: {
            expandRow,jnxx,lcjl,clgj,ysdj
        },
        mixins: [mixins],
        data() {
            return {
                pageHeight: this.getWindowHeight() - 260,
                compName:'',
                listMess:{},
                //收索
                datetime: '',
                param: {
                    ckLike: '',
                    pageNum: 1,
                    pageSize: 8
                },
                //弹层
                pageTotal: 1,
                page: {
                    pageNum: 1,
                    pageSize: 8
                },
                columns10: [
                    {
                        title: '#',
                        type: 'expand',
                        width: 50,
                        render: (h, params) => {
                            return h(expandRow, {
                                props: {
                                    row: params.row
                                }
                            })
                        }
                    },
                    {
                        title: '用车单位',
                        align: 'center',
                        key: 'jgmc'
                    },
                    {
                        title: '用车人',
                        align: 'center',
                        key: 'ck'
                    },
                    {
                        title: '客户电话',
                        align: 'center',
                        key: 'cklxdh'
                    },
                    {
                        title: '出车司机',
                        align: 'center',
                        key: 'sjxm'
                    },
                    {
                        title: '司机电话',
                        align: 'center',
                        key: 'DriverPhone'
                    },
                    {
                        title: '约车时间',
                        align: 'center',
                        key: 'yysj',
                        render:(h,p)=>{
                            return h('div', p.row.yysj.substring(0, 13));
                        }
                    },
                    {
                        title: '约车地点',
                        align: 'center',
                        key: 'hcdz'
                    },
                    {
                        title: '目的地',
                        key: 'mdd'
                    },
                    {
                        title: '座位数',
                        align: 'center',
                        key: 'zws',
                        render: (h, p) => {
                            let cx = this.dictUtil.getValByCode(this, 'ZDCLK0019', p.row.cllx)
                            return h('span', cx + '/' + p.row.zws+'座')
                        }
                    },
                    {
                        title:'操作',
                        fixed:'right',
                        align: 'center',
                        minWidth:140,
                        render:(h,p) =>{
                            return h('div',[
                                h('Tooltip',
                                    {
                                        props: {
                                            placement: 'top',
                                            content: '订单详情',
                                            'transfer':true
                                        }
                                    },
                                    [
                                        h('Button', {
												                  props: {
												                    icon: 'md-clipboard',
												                    type: 'text',
												                    ghost: true,
												                    shape: "circle",
												
												                  },
												                  style: {
												                    fontSize: '24px',
												                    margin:'0 6px',
												                    color: '#2db7f5'
												                  },
												                  on: {
												                    click: () => {
												                    	this.listMess = p.row
												                    	this.compName = 'jnxx'
												                    }
												                  }
												                })
                                    ]
                                ),
                                h('Tooltip',
                                    {
                                        props: {
                                            placement: 'top',
                                            content: '流程记录',
                                            'transfer':true
                                        }
                                    },
                                    [
                                        h('Button', {
												                  props: {
												                    icon: 'logo-steam',
												                    type: 'text',
												                    ghost: true,
												                    shape: "circle",
												
												                  },
												                  style: {
												                    fontSize: '24px',
												                    margin:'0 6px',
												                    color: '#2db7f5'
												                  },
												                  on: {
												                    click: () => {
												                    	this.listMess = p.row
												                    	this.compName = 'lcjl'
												                    }
												                  }
												                })
                                    ]
                                ),
                                h('Tooltip',
                                    {
                                        props: {
                                            placement: 'top',
                                            content: '流程记录',
                                            'transfer':true
                                        }
                                    },
                                    [
                                        h('Button', {
												                  props: {
												                    icon: 'md-pulse',
												                    type: 'text',
												                    ghost: true,
												                    shape: "circle",
												
												                  },
												                  style: {
												                    fontSize: '24px',
												                    margin:'0 6px',
												                    color: '#2db7f5'
												                  },
												                  on: {
												                    click: () => {
												                    	this.listMess = p.row
												                    	this.compName = 'clgj'
												                    }
												                  }
												                })
                                    ]
                                ),
                                h('Tooltip',
                                    {
                                        props: {
                                            placement: 'top',
                                            content: '流程记录',
                                            'transfer':true
                                        }
                                    },
                                    [
                                        h('Button', {
												                  props: {
												                    icon: 'ios-paper',
												                    type: 'text',
												                    ghost: true,
												                    shape: "circle",
												
												                  },
												                  style: {
												                    fontSize: '24px',
												                    margin:'0 6px',
												                    color: '#2db7f5'
												                  },
												                  on: {
												                    click: () => {
												                    	this.listMess = p.row
												                    	this.compName = 'ysdj'
												                    }
												                  }
												                })
                                    ]
                                )
                            ])
                        }
                    }
                ],
                data9: [],
                treeValue: [],
                orgTree: [],
            }
        },
        created() {
            this.$store.commit('setCurrentPath', [{
                title: '首页',
            }, {
                title: '车辆管理',
            }, {
                title: '订单管理',
            }, {
                title: '订单查阅',
            }])
            this.findMessList()
            this.getOrgTree();
        },
        methods: {
            getOrgTree() {
                this.$http.get(this.apis.FRAMEWORK.GET_TREE_Node).then((res) => {
                    this.orgTree = res.result
                })
            },
            change(vaule, selectedData) {
                this.param.jgdm = selectedData[selectedData.length - 1].value
                this.treeValue = vaule;
            },
            changeTime(val) {
            },
            pageChange(event) {
                var v = this
                v.page.pageNum = event
                this.param.pageNum = event;
                v.findMessList()
            },
            findMessList() {
                this.$http.get(this.apis.ORDER.QUERY, {params: this.param}).then((res) => {
                    if (res.code === 200 && res.page.list) {
                        this.data9 = res.page.list;
                        this.pageTotal = res.page.total;
                    }
                })
            },
        }
    }
</script>
