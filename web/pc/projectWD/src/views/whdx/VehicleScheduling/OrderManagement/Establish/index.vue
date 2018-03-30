<style lang="less">
	@import '../../../../../styles/common.less';
	.headTit {
		text-align: center;
	} 
	/*5 7 12 20 45 48*/
</style>
<template>
	<div class="box">
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
							<Select v-model="formItem.unit" size="large" placeholder="请选择用车单位" filterable>
								<Option v-for="item in unitList" :value="item.value"></Option>
							</Select>
						</FormItem>
					</Col>	
					<Col span="8">
						<FormItem label="">
							<h3>
								用车人
							</h3>
							<Input v-model="formItem.peopleName" size="large" placeholder="请填写用车人姓名"></Input>
						</FormItem>
					</Col>
					<Col span="8">
						<FormItem label="">
							<h3>
								客户电话
							</h3>
							<Input v-model="formItem.peoplePhone" size="large" placeholder="请填写用车人电话"></Input>
						</FormItem>
					</Col>
					<Col span="8">
						<FormItem label="">
							<h3>
								出车时间
							</h3>
							<Input v-model="formItem.starTime" size="large" placeholder="请填写用车时间"></Input>
						</FormItem>
					</Col>	
					<Col span="8">
						<FormItem label="">
							<h3>
								候车地点
							</h3>
							<Input v-model="formItem.starSite" size="large" placeholder="请填写候车地点..."></Input>
						</FormItem>
					</Col>	
					<Col span="8">
						<FormItem label="">
							<h3>
								目的地
							</h3>
							<Input v-model="formItem.endSite" size="large" placeholder="请填写目的地点"></Input>
						</FormItem>
					</Col>
					<Col span="8">
						<Row>
							<Col span="12">
								<FormItem label="">
									<h3>
										费用来源
									</h3>
									<Select v-model="formItem.fromMoney" size="large" placeholder="请选着费用来源" filterable>
										<Option v-for="item in fromMoneyList" :value="item.value"></Option>
									</Select>
								</FormItem>
							</Col>
							<Col span="12">
								<FormItem label="">
									<h3>
										单据类型
									</h3>
									<Select size="large" placeholder="请选着单据类型" filterable>
										<Option value="单程"></Option>
										<Option value="往返"></Option>
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
									<Select
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
									<Select v-model="formItem.carModel" size="large" placeholder="请选择用车单位" filterable>
										<Option v-for="item in carModelList" :value="item.value"></Option>
									</Select>
								</FormItem>
							</Col>	
						</Row>
						<Row v-else	>
							<FormItem label="">
								<h3>
									车型
								</h3>
								<Select v-model="formItem.carModel" size="large" placeholder="请选择用车单位" filterable>
									<Option v-for="item in carModelList" :value="item.value"></Option>
								</Select>
							</FormItem>
						</Row>
					</Col>	
					<Col span="16">
						<FormItem label="">
							<h3>
								事由
							</h3>
							<Input v-model="formItem.mess" type="textarea" :rows="6" placeholder="请填写用车事由"></Input>
						</FormItem>
					</Col>
					<Col span="24" style="padding: 8px 0 8px 50px;">
						<Button type="primary" style="width: 100%;" @click="AddNewlist">提交</Button>
					</Col>
				</Row>
			</Form>
		</div>
	</div>
</template>

<script>
	import swal from 'sweetalert'
	
    export default {
    	name:'NewCarList',
        data () {
            return {
                formItem: {
                	unit:'',//单位
                	peopleName:'',//用车人
                	peoplePhone:'',//用车电话
                	fromMoney:'',//费用来源
                	starTime:'',//发车时间
                	starSite:'',//候车地点
                	endSite:'',//目的点
                	carModel:'',//车型
                	mess:'',//备注
                	task:'',//课题
                	newtask:''//新课题
                },
                unitList:[
                	{
                		value:'A学院'
                	},{
                		value:'B学院'
                	},{
                		value:'C学院'
                	},{
                		value:'D学院'
                	},{
                		value:'E学院'
                	},{
                		value:'F学院'
                	},{
                		value:'G学院'
                	},{
                		value:'H学院'
                	},{
                		value:'I学院'
                	}
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
                carModelList:[
                	{
                		value:'4人座'
                	},
                	{
                		value:'7人座'
                	},
                	{
                		value:'45人座'
                	},{
                		value:'48人座'
                	}
                ]
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
			
		},
		methods:{
			//表单数据提交
			AddNewlist(){
				var v = this
				swal({
				  title: "是否提交数据?",
				  text: "",
				  icon: "success",
				  buttons:['取消','确认'],
				})
				.then((willDelete) => {
				  if (willDelete) {
				  	this.$Message.success('订单创建成功');
				  	v.formItem={
	                	unit:'',
	                	peopleName:'',
	                	peoplePhone:'',
	                	fromMoney:'',
	                	starTime:'',
	                	starSite:'',
	                	endSite:'',
	                	carModel:'小车',
	                	mess:''
	                }
				  } else {
				  	this.$Message.error('订单创建失败');
				  }
				});
			}
		}
    }
</script>

<style>
</style>