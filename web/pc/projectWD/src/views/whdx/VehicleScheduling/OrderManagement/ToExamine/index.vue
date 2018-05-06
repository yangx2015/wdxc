<style lang="less">
    @import '../../../../../styles/common.less';

</style>
<!--订单审核-->
<template>
    <div class="box boxbackborder">
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>订单审核</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="findMess.ckLike" type="text" placeholder="输入乘客姓名查询" style="width: 220px"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<div class="body">
				<Row>
					<Table
							:height="tabHeight"
							:row-class-name="rowClassName"
							:columns="tableTiT"
							:data="tableData"></Table>
				</Row>
				<Row class="margin-top-10" style="text-align: right;">
					<Page :total=pageTotal
						  :current=page.pageNum
						  :page-size=page.pageSize
						  show-total
						  show-elevator
						  @on-change='pageChange'></Page>
				</Row>
			</div>
		<Modal
		    v-model="showModal"
		    width='800'
			:closable="false"
		    :mask-closable="false"
		    title="请填写驳回原因">
			<div>
				<Form ref="formValidate" :model="formValidate"
					  :rules="ruleValidate" :label-width="80">
					<FormItem label="原因" prop="bhyy">
						<Input v-model="formValidate.bhyy" type="textarea" placeholder="请填写驳回原因"></Input>
					</FormItem>
				</Form>
			</div>
		    <div slot='footer'>
				<Button type="text" @click="showModal=false,bhyy=''">取消</Button>
				<Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
			</div>
		</Modal>
    </div>
</template>

<script>
    import configApi from '@/axios/config.js'
	import mixins from '@/mixins'
	import swal from 'sweetalert'
//	import axios from '@/axios'
	export default {
    	name:'char',
    	mixins:[mixins],
        data () {
            return {
                formValidate: {
                    bhyy: ''
                },
                ruleValidate: {
                    bhyy: [
                        { required: true, message: '请认真填写驳回原因', trigger: 'blur' }
					]
                },
                //tab高度
                tabHeight: 220,
            	PickerTime:2017,
            	//分页
            	pageTotal:1,
            	page:{
            		pageNum:1,
            		pageSize:13
            	},
            	//弹层
            	showModal:false,
                tableTiT: [
                	{
	                	title:"序号",
	                	width:80,
	                	align:'center',
	                	type:'index'
	                },
	                {
                        title: '订单编号',
                        width:180,
                        align:'center',
                        key: 'id'
                    },
                    {
                        title: '用车单位',
                        align:'center',
                        key: 'jgmc'
                    },
                    {
                        title: '用车人',
                        align:'center',
                        key: 'ck'
                    },
                    {
                        title: '联系电话',
                        align:'center',
                        key: 'cklxdh'
                    },
                    {
                        title: '候车地点',
                        align:'center',
                        key: 'hcdz'
                    },
                    {
                        title: '目的地',
                        align:'center',
                        key: 'mdd'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'success',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.pass(params.row.id)
                                        }
                                    }
                                },'通过'),
                                h('Button', {
                                    props: {
                                        type: 'error',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.reject(params.row.id)
                                        }
                                    }
                                },'驳回')
                            ]);
                        }
                    }
                ],
                tableData: [
                ],
                //form表单
                formTop: {
                },
                //select
                cityList: [
                ],
                //收索
                datetime:[],
                findMess:{
                    ckLike:'',
					ddzt:'10',
                	pageNum:1,
            		pageSize:13
                },
				messError:'',
                idMs:''
            }
        },
        // watch: {
        //     datetime:function(newQuestion, oldQuestion){
			// 	this.findMess.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
			// },
        // },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆管理',
            },{
                title: '订单管理',
            },{
                title: '订单审核',
            }]),
			this.tabHeight = this.getWindowHeight() - 295
            this.findMessList();
        },
        methods: {
        	changeTime(val){
//      		this.findMess.gte_StartTime=val[0]
//      		this.findMess.lte_StartTime=val[1]
//      		log(this.findMess)
//      		this.findMessList()
        	},
        	print(){
        		window.print()
        	},
            listEve(num,event){
        		log('event',event)
        		log('num',num)
        		var v = this
				swal({
				  title: "审核通过?",
				  text: "",
				  icon: "success",
				  buttons:['取消','确认'],
				})
				.then((willDelete) => {
				  if (willDelete) {
				  	this.$Message.success('订单审核通过');
				  } else {
				  	this.$Message.error('订单审核失败');
				  }
				});
        	},

			pass(id){
                swal({
                    title: "审核通过?",
                    text: "",
                    icon: "success",
                    buttons:['取消','确认'],
                }).then((willDelete) => {
                        if (willDelete) {
                            this.audit(id,'11');
                        }
                    });
			},
			reject(id){
                swal({
                    title: "审核驳回?",
                    text: "",
                    icon: "warning",
                    buttons:['取消','确认'],
                }).then((willDelete) => {
                    if (willDelete) {
                        this.showModal = true
						this.idMs = id
                    }
                });
			},
            handleSubmit (name) {
        	    var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        v.$Message.success('完成');
                        v.audit(v.idMs,'12');
                    } else {
                        this.$Message.error('失败');
                    }
                })
            },
            audit(id,zt){
        	    let param = {
        	        ddzt:zt,
					id:id,
                    bhyy:this.bhyy
				}
                this.$http.post(configApi.ORDER.AUDIT,param).then((res) =>{
                    if (res.code === 200){

                        this.findMessList();
                        this.showModal = false
                    }
                })
			},
        	GetMess(page){
        		var v = this
//      		log(page)
        	},
            pageChange(event){
        		var v = this
        		v.page.pageNum = event
                this.findMess.pageNum = event;
        		v.findMessList()
        	},
            findMessList(){
                this.$http.get(configApi.ORDER.QUERY,{params:this.findMess}).then((res) =>{
                    if (res.code === 200 && res.page.list){
                        this.tableData = res.page.list;
                        this.pageTotal = res.page.total;
                    }
                })
            },

        }
    }
</script>
