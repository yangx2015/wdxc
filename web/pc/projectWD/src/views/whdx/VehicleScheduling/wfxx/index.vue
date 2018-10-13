<style lang="less">
    @import '../../../../styles/common.less';

</style>
<!--违法信息-->
<template>
    <div class="box boxbackborder">
		<!--<Card>-->
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>违法信息</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="param.wfCphLike" placeholder="车牌号...." style="width: 160px;"></Input>

							<!--<DatePicker v-model="cjsjInRange"
								@on-change = 'findMessList'
								format="yyyy-MM-dd" type="daterange"
								placement="bottom-end" placeholder="请输时间"
								@on-keyup.enter="findMessList()" style="width: 180px"></DatePicker>-->
						</div>
						<div class="butevent">
							<Button type="primary" @click="v.util.getPageData(v)">
								<Icon type="md-search"></Icon>
								<!--查询-->
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<Row>
				<Table ref="table"
						:height="tableHeight"
						:row-class-name="rowClassName"
						:columns="tableTiT"
						:data="pageData"></Table>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal
					  :current=param.pageNum
					  :page-size=param.pageSize :page-size-opts=[8,10,20,30,40,50]  @on-page-size-change='(e)=>{param.pageSize=e;pageChange()}'
					  show-total
					  show-elevator show-sizer placement='top'
					  @on-change='pageChange'></Page>
			</Row>
		<!--</Card>-->
    </div>
</template>

<script>
	import mixins from '@/mixins'
    import swal from 'sweetalert2'


	export default {
    	name:'char',
    	mixins:[mixins],
        data () {
            return {
                v:this,
            	SpinShow:true,
                tableHeight: 220,
            	//分页
            	pageTotal:1,
                apiRoot:this.apis.CLWF,
                choosedItem:null,
                componentName:'',
                pagerUrl:this.apis.CLWF.QUERY,
                tableTiT: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
	                },
                    {
                        title: '车牌号',
                        key: 'wfCph',
                        align: 'center',
                    },
                    {
                        title: '违法日期',
                        key: 'wfDate',
                        align: 'center',

                    },
                    {
                        title: '违法地点',
                        key: 'wfSite',
                        align: 'center',
                    },
                    {
                        title: '违法行为',
                        key: 'wfxw',
                        align: 'center',
                    },
                    {
                        title: '违法记分',
                        key: 'wfScoring',
                        align: 'center',
                    },
                    {
                        title: '违法金额(元)',
                        key: 'wfMoney',
                        align: 'center',
                    },
                    {
                        title: '违法状态',
                        key: 'wfStatus',
                        align: 'center',
                        render:(h,p)=>{
                            if (p.row.wfStatus == 1){
                                return h('Tag', {
                                    props: {
                                        color: 'green'
                                    }
                                }, '已处理');
                            }else {
                                return h('div',
                                    [
                                        h('Tag',
										{
											props: {
												color: 'red'
											}
										}, '未处理'),

                                        h('Button', {
                                            props: {
                                                type: 'text',
                                                shape:"circle",
                                                icon:'md-checkmark-circle-outline',
                                            },
											style:{
                                                color:'#19be6b',
												fontSize:'18px'
											},
                                            on:{
                                                'click':()=>{
                                                    swal({
                                                        text: "确认违法已处理完成?",
                                                        type: "warning",
                                                        showCancelButton: true,
                                                        confirmButtonText: '确认',
                                                        cancelButtonText: '取消'
                                                    }).then((isConfirm) => {
                                                        if (isConfirm.value) {
                                                            this.$http.get(this.apis.CLWF.UPDATEWFZT+"/"+p.row.id).then((res) =>{
                                                                if(res.code===200){
                                                                    this.$Message.success(res.message);
                                                                    this.util.getPageData(this);
                                                                }else{
                                                                    this.$Message.error(res.message);
                                                                }
                                                            })
                                                        }
                                                    });
                                                }
                                            }
                                        })
                                    ]
                                );
                            }
                        }
                    },
                    {title:'操作',render:(h,p)=>{
							let buttons = [];
							buttons.push(this.util.buildDeleteButton(this,h,p.row.id));
							return h('div',buttons);
						}
                    },
                ],
                pageData: [
                ],
                //收索时间
                cjsjInRange:[],
                param:{
                    wfCphLike:'',
                	cjsjInRange:[],
					orderBy:'id desc',
					pageNum:1,
					pageSize:13
                }
            }
        },
        watch: {
			cjsjInRange:function(newQuestion, oldQuestion){
				if (newQuestion.length > 0 && newQuestion[0] != ''){
					this.param.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
				}else{
					this.param.cjsjInRange  = ''
				}
			},
		},
        created(){
			this.util.initTable(this);
        },
        methods: {
            pageChange(event){
                this.util.pageChange(this, event);
        	}

        }
    }
</script>
