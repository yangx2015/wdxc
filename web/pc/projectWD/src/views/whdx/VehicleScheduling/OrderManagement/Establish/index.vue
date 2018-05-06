<style lang="less">
	@import '../../../../../styles/common.less';
	.headTit {
		text-align: center;
	}
	/*5 7 12 20 45 48*/
</style>
<template>
	<div class="box">
		<Card>
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
									出车时间
								</h3>
								<DatePicker v-model="formItem.yysj" size="large"  placement="right" format="yyyy-MM-dd HH:mm:ss" type="datetime"  placeholder="请填写用车时间" ></DatePicker>
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
											单据类型
										</h3>
										<Select v-model="formItem.wf" filterable clearable  size="large" placeholder="请选择单据类型" filterable>
											<Option value="00">单程</Option>
											<Option value="10">往返</Option>
										</Select>
									</FormItem>
								</Col>
							</Row>
							<Row v-if="formItem.fromMoney=='课题费用'">
								<Col span="12">
									<FormItem label="">
										<h3>
											课题
										</h3>
										<Input
												v-if="formItem.task=='添加课题'"
												v-model="formItem.newtask"
												size="large"
												placeholder="添加课题"></Input>
										<Select filterable clearable
												v-else
												v-model="formItem.task" size="large" placeholder="请选择用车单位" filterable>
											<Option v-for="item in ctasklList" :value="item.value"></Option>
										</Select>
									</FormItem>
								</Col>
								<Col span="12">
									<FormItem label="">
										<h3>
											车型
										</h3>
										<Select filterable clearable  v-model="formItem.zws" size="large" placeholder="请选择用车单位" filterable>
											<Option v-for="item in zwsList" :value="item.value"></Option>
										</Select>
									</FormItem>
								</Col>
							</Row>
							<Row v-else	>
								<FormItem label="">
									<h3>
										车型
									</h3>
									<Select filterable clearable  v-model="formItem.zws" size="large" placeholder="请选择用车单位" filterable>
										<Option v-for="item in zwsList" :value="item.value"></Option>
									</Select>
								</FormItem>
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
		</Card>
	</div>
</template>

<script>
	import swal from 'sweetalert'
    import configApi from '@/axios/config.js'
    export default {
    	name:'NewCarList',
        data () {
            return {
                formItem: {
                	jgdm:'',//单位
                	ck:'',//用车人
                	cklxdh:'',//用车电话
                	fromMoney:'',//费用来源
                	starTime:'',//发车时间
                	hcdz:'',//候车地点
                	mdd:'',//目的点
                	zws:'',//车型
                	sy:'',//备注
                	task:'',//课题
                	newtask:''//新课题
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
                	{
                		value:'添加课题',
                	},
                	{
                		value:'H012菌课题',
                	},
                	{
                		value:'甲骨文课题',
                	}
                ],
                zwsList:[
                	{
                		value:'4'
                	},
                	{
                		value:'7'
                	},
                	{
                		value:'45'
                	},{
                		value:'48'
                	}
                ],
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
            }])
		},
		mounted(){
			this.getOrgTree();
		},
		methods:{
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
                if (this.treeValue.length === 0){
                    this.$Message.error('请选择机构');
                    return;
				}
				var v = this
				swal({
				  title: "是否提交数据?",
				  text: "",
				  icon: "success",
				  buttons:['取消','确认'],
				})
				.then((willDelete) => {
				  if (willDelete) {
				      this.create();
				  }
				});
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
