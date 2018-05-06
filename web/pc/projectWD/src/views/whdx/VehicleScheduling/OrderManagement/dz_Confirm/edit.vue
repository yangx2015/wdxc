<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='1200' :closable='mesF' :mask-closable="mesF" title="编辑订单">
			<div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
				<Spin fix>
					<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
					<div style="font-size: 30px;">数据加载中请稍后</div>
				</Spin>
			</div>
			<div style="overflow: auto;height: 400px;">
				<Form :model="form" :label-width="80">
					<Row :gutter="16">
						<Col span="8">
							<FormItem label="用车单位">
								<Select disabled filterable clearable  v-model="form.jgdm" size="large" placeholder="请选择用车单位" filterable>
									<Option v-for="item in jgdmList" :value="item.jgdm">{{item.jgmc}}</Option>
								</Select>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="用车人">
								<Input v-model="form.ck" size="large" placeholder="请填写用车人姓名"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="客户电话">
								<Input v-model="form.cklxdh" size="large" placeholder="请填写用车人电话"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="出车时间">
								<DatePicker v-model="form.yysj" size="large"  format="yyyy-MM-dd HH:mm:ss" type="datetime" placement="bottom-end" placeholder="请填写用车时间" ></DatePicker>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="候车地点">
								<Input v-model="form.hcdz" size="large" placeholder="请填写候车地点..."></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="目的地">
								<Input v-model="form.mdd" size="large" placeholder="请填写目的地点"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<Row>
								<Col span="12">
									<FormItem label="费用来源">
										<Select filterable clearable  v-model="form.fromMoney" size="large" placeholder="请选择费用来源" filterable>
											<Option v-for="item in fromMoneyList" :value="item.value"></Option>
										</Select>
									</FormItem>
								</Col>
								<Col span="12">
									<FormItem label="单据类型">
										<Select v-model="form.wf" filterable clearable  size="large" placeholder="请选择单据类型" filterable>
											<Option value="00">单程</Option>
											<Option value="10">往返</Option>
										</Select>
									</FormItem>
								</Col>
							</Row>
							<Row v-if="form.fromMoney=='课题费用'">
								<Col span="12">
									<FormItem label="课题">
										<Input
												v-if="form.task=='添加课题'"
												v-model="form.newtask"
												size="large"
												placeholder="添加课题"></Input>
										<Select filterable clearable
												v-else
												v-model="form.task" size="large" placeholder="请选择用车单位" filterable>
											<Option v-for="item in ctasklList" :value="item.value"></Option>
										</Select>
									</FormItem>
								</Col>
								<Col span="12">
									<FormItem label="车型">
										<Select filterable clearable  v-model="form.zws" size="large" placeholder="请选择用车单位" filterable>
											<Option v-for="item in zwsList" :value="item.value"></Option>
										</Select>
									</FormItem>
								</Col>
							</Row>
							<Row v-else	>
								<FormItem label="车型">
									<Select filterable clearable  v-model="form.zws" size="large" placeholder="请选择用车单位" filterable>
										<Option v-for="item in zwsList" :value="item.value"></Option>
									</Select>
								</FormItem>
							</Row>
						</Col>
						<Col span="16">
							<FormItem label="事由">
								<Input v-model="form.sy" type="textarea" :rows="6" placeholder="请填写用车事由"></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="8">
							<FormItem label="行车里程">
								<Input v-model="form.lc" type="text" :rows="6" placeholder="请填写行车里程"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="里程单价">
								<Input v-model="form.dj" type="text" :rows="6" placeholder="请填写里程单价"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="里程费用">
								<Input v-model="form.jcf" type="text" :rows="6" placeholder="请填写里程费用"></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="8">
							<FormItem label="结束时间">
								<DatePicker v-model="form.sjqrsj" format="yyyy-MM-dd HH:mm:ss" type="datetime" placement="bottom-end" placeholder="请填写结束时间" ></DatePicker>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="过路费">
								<Input v-model="form.glf" type="text" :rows="6" placeholder="请填写过路费"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label="总费用">
								<Input v-model="form.zj" type="text" :rows="6" placeholder="请填写总费用"></Input>
							</FormItem>
						</Col>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="close">取消</Button>
				<Button type="primary" @click="save">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
    import treelist from '@/data/list.js'
    import configApi from '@/axios/config.js'
    export default {
        name: '',
        data() {
            return {
                SpinShow:false,
                operate:'新建',
                showModal: true,
                mesF:false,
                form: {
                    zdbh:'',
                    mc: '',
                    xh: '',
                    cs: '',
                    dz:'',
                },
                jgdmList:[],
                ruleInline:{

                },
                addmess:{

                },
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
                ]

            }
        },
        created(){
            if (this.$parent.choosedRow){
                this.form = this.$parent.choosedRow;
                this.operate = '编辑'
            }
        },
        mounted(){
            this.getOrgList();
        },
        methods: {
            getOrgList(){
                this.$http.get(configApi.FRAMEWORK.getSubOrgList).then((res) =>{
                    this.jgdmList = res.result
                })
            },
            save(){
                let v = this
                this.SpinShow = true
                let url = configApi.ORDER.CHANGE;
                this.$http.post(url,this.form).then((res) =>{
                    this.SpinShow = false
                    if(res.code===200){
                        var v = this
                        v.$parent.findMessList()
                        this.$Message.success(res.message);
                        v.$parent.componentName = ''
                    }else{
                        this.$Message.error(res.message);
                    }
                    // }).catch((error) =>{
                    //     v.$Message.error('出错了！！！');
                    //     v.SpinShow = false
                })
            },
            close(){
                let v = this;
                v.$parent.componentName = ''
                v.$parent.findMessList()
            }

        }
    }
</script>

<style>

</style>
