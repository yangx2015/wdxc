<style lang="less">
	@import '../../../../../styles/common.less';
	.headTit {
		text-align: center;
	}
	/*5 7 12 20 45 48*/
</style>
<template>
	<div class="box">
		<!--<Card>-->
			<div class="tit headTit padding">
				<h1>
					武汉大学后勤集团运输中心用车单
				</h1>
			</div>
			<div class="body padding-top-10" style="border-top: solid #dddee1 2px;">
				<Form :model="formItem" :label-width="50">
					<Row>
						<Col span="8">
							<FormItem label="">
								<h3>
									用车单位
								</h3>
								<Cascader @on-change="change" change-on-select :data="orgTree"  placeholder="请选择用车单位"  filterable clearable  ></Cascader>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="">
								<h3>
									用车人
								</h3>
								<Input v-model="formItem.ck" size="large" placeholder="请填写用车人姓名"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="">
								<h3>
									客户电话
								</h3>
								<Input v-model="formItem.cklxdh" size="large" placeholder="请填写用车人电话"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="">
								<h3>
									候车地点
								</h3>
								<Input v-model="formItem.hcdz" size="large" placeholder="请填写候车地点..."></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="">
								<h3>
									目的地
								</h3>
								<Input v-model="formItem.mdd" size="large" placeholder="请填写目的地点"></Input>
							</FormItem>
						</Col>
						<Col span="4">
						<FormItem label="">
							<h3>
								出车时间
							</h3>
							<DatePicker v-model="formItem.yysj"
										size="large"  placement="left"
										format="yyyy-MM-dd HH:mm:ss"
										type="datetime"
										placeholder="请填写用车时间" ></DatePicker>
						</FormItem>
						</Col>
						<Col span="4">
							<FormItem label="">
								<h3>
									单据类型
								</h3>
								<Select v-model="formItem.wf" filterable clearable  size="large" placeholder="请选择单据类型" filterable>
									<Option value="00">单程</Option>
									<Option value="10">往返</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="8">
							<Row>
								<Col span="12">
									<FormItem label="">
										<h3>
											费用来源
										</h3>
										<Select filterable clearable  v-model="formItem.fromMoney" size="large" placeholder="请选择费用来源" filterable>
											<Option v-for="item in fromMoneyList" :value="item.value"></Option>
										</Select>
									</FormItem>
								</Col>
								<Col span="12">
									<FormItem label="">
										<h3>
											课题
										</h3>
										<div class="box-row">
											<div class="body-O">
												<Select filterable clearable
														:disabled="formItem.fromMoney!='课题费用'"
														v-model="formItem.task" size="large" placeholder="请选择课题" filterable>
													<Option v-for="item in ctasklList" :value="item.key">{{item.val}}</Option>
												</Select>
											</div>
											<div title="新建课题">
												<Button type="info"
														:disabled="formItem.fromMoney!='课题费用'"
														@click="newKT">新建</Button>
											</div>
										</div>
									</FormItem>
								</Col>
							</Row>
							<Row>
								<Col span="12">
									<FormItem label="">
										<h3>
											车型
										</h3>
										<Cascader v-model="formItem.zws" :data="CasData"></Cascader>
									</FormItem>
								</Col>
								<Col span="12">
									<FormItem label="">
										<h3>
											行程费用
										</h3>
										<Input v-model="formItem.money" placeholder="请输入行程费用"></Input>
									</FormItem>
									</Col>
								</Row>
						</Col>
						<Col span="16">
							<FormItem label="">
								<h3>
									事由
								</h3>
								<Input v-model="formItem.sy" type="textarea" :rows="6" placeholder="请填写用车事由"></Input>
							</FormItem>
						</Col>
						<Col span="24" style="padding: 8px 0 8px 50px;">
							<Button type="primary" style="width: 100%;" @click="AddNewlist">提交</Button>
						</Col>
					</Row>
				</Form>
			</div>
		<!--</Card>-->
		<component :is="compName" :dicListMess='dicListMess'></component>
	</div>
</template>

<script>
	import swal from 'sweetalert'
    import configApi from '@/axios/config.js'
    import addmessList from './comp/addmessList.vue'
    export default {
    	name:'NewCarList',
        components: {
            addmessList
        },
        data () {
            return {
                compName:'',
                dicListMess:'ktzd00001',
                formItem: {
                	jgdm:'',//单位
                	ck:'',//用车人
                	cklxdh:'',//用车电话
                	fromMoney:'',//费用来源
                	starTime:'',//发车时间
                	hcdz:'',//候车地点
                	mdd:'',//目的点
                	zws:[],//车型
                	sy:'',//备注
                	task:'',//课题
					money:''
                },
                jgdmList:[
                ],
                fromMoneyList:[
                	{
                		value:'行政费用'
                	},{
                		value:'课题费用'
                	},{
                		value:'自费'
                	}
                ],
                ctasklList:[
                	// {
                	// 	value:'H012菌课题',
                	// },
                	// {
                	// 	value:'甲骨文课题',
                	// }
                ],
				CasData:[{
                    value: '20',
                    label: '大车',
                    children: [{
                        value: '18',
                        label: '18',
                    },{
                        value: '24',
                        label: '24',
                    },{
						value: '25',
						label: '25',
					},{
                        value: '26',
                        label: '26',
                    },{
                        value: '35',
                        label: '35',
                    },{
                        value: '36',
                        label: '36',
                    },{
                        value: '41',
                        label: '41',
                    },{
                        value: '42',
                        label: '42',
                    },{
                        value: '45',
                        label: '45',
                    },{
                        value: '46',
                        label: '46',
                    }]
                }, {
                    value: '10',
                    label: '小车',
                    disabled: false,
                    children: [{
                        value: '5',
                        label: '5',
                    },{
                        value: '6',
                        label: '6',
                    },{
                        value: '7',
                        label: '7',
                    },{
                        value: '8',
                        label: '8',
                    },{
                        value: '11',
                        label: '11',
                    }]
                }],
				treeValue:[],
				orgTree:[],
            }
        },
		created(){
			this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '车辆管理',
            },{
                title: '订单管理',
            },{
                title: '创建订单',
            }]);
			this.getKT()
		},
		mounted(){
			this.getOrgTree();
		},
		methods:{
            newKT(){
                this.compName='addmessList'
			},
            getKT(){
                this.ctasklList =this.dictUtil.getByCode(this,'ktzd00001')
				console.log('********************8',this.dictUtil.getByCode(this,'ktzd00001'))

            },
            change(vaule,selectedData){
                this.treeValue = vaule;
            },
    	    getOrgTree(){
                this.$http.get(configApi.FRAMEWORK.GET_TREE_Node).then((res) =>{
                    this.orgTree = res.result
                })
			},
			//表单数据提交
			AddNewlist(){
                console.log(this.formItem)
                // if (this.treeValue.length === 0){
                 //    this.$Message.error('请选择机构');
                 //    return;
				// }
				// var v = this
				// swal({
				//   title: "是否提交数据?",
				//   text: "",
				//   icon: "success",
				//   buttons:['取消','确认'],
				// })
				// .then((willDelete) => {
				//   if (willDelete) {
				//       this.create();
				//   }
				// });
			},
			create(){
                this.formItem.jgdm = this.treeValue[this.treeValue.length - 1];
                this.$http.post(configApi.ORDER.ADD,this.formItem).then((res) =>{
                    if (res.code === 200){
                        this.$Message.success("创建成功");

					}else{
                        this.$Message.error(res.message);
					}
                })
			},
		}
    }
</script>

<style>
</style>
