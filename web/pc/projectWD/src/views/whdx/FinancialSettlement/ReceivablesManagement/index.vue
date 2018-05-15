<!--
	收款管理
-->
<style lang="less">
    @import '../../../../styles/common.less';
</style>
<template>
	<div class="box">
		<Row class="tit" style="height: 120px;">
			<Col span="6">
				<Menu mode="horizontal" theme="light" active-name="1" @on-select="MenuClick">
			        <MenuItem name="1">
			            <Icon type="ios-paper"></Icon>
			            应收单据
			        </MenuItem>
			        <MenuItem name="2">
			            <Icon type="android-checkbox-outline"></Icon>
			            已收单据
			        </MenuItem>
			    </Menu>
		    </Col>
			<Col span="15">
				<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
					单笔费用结算公式：里程 * 单价 + 过路费 + 过桥费 + 等时费 = 合计总价
				</div>
			</Col>
			<Col span="3">
				<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
					<div v-show="form.ddzt === '30'">
						应收单据：{{list.length}}单
					</div>
					<div v-show="form.ddzt === '40'">
						已收单据：{{list.length}}单
					</div>
				</div>
			</Col>
		    <Col span="24">
		    	<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
		    		<Input placeholder="请输入机构名称" style="width: 150px;" v-model="form.jgmc"></Input>
					<DatePicker v-model="form.startTime" :options="dateOpts" type="datetime" placeholder="请输入开始时间" ></DatePicker>
					<DatePicker v-model="form.endTime" :options="dateOpts" type="datetime"  placeholder="请输入结束时间"  ></DatePicker>
					<Button type="primary" @click="getData()">
						<Icon type="search"></Icon>
					</Button>
		    	</div>
		    </Col>
		</Row>
		<Row :gutter="16" class="margin-top-10 body clientList"  v-for="(item,index) in list" >
			<Col span="24" :lg="24" :md="24" :sm="24" :xs="24" class="margin-top-10">
				<Card style="width:100%" :id="'group_'+item.orgCode">
			        <div slot="title">
			            <Icon type="person"></Icon>
			            	{{item.orgName}}
			        </div>
			        <span slot="extra">
			        	<span>
			        		收款金额：{{item.amount}}元
			        		<Button type="success" size="small" @click="print(item)">打印</Button>
			        		<Button v-if="form.ddzt === '30'" type="primary" size="small" @click="confirm(item.orderList)">确认</Button>
			        	</span>
			        </span>
			        <!--信息-->
			        <div>
			        	<Table
			        		border
			        		ref="selection"
			        		:columns="columns3"
			        		height="220"
			        		:data="item.orderList"></Table>
			        </div>
			    </Card>
			</Col>
		</Row>
		<component :is="componentName"></component>
	</div>
</template>

<script>
	import edit from './edit'
	import print from './print'
	export default{
		name:'client',
		components:{
		  edit,print
		},
		data(){
			return {
                dateOpts: {
                    shortcuts: [
                        {
                            text: '今天',
                            value () {
                                return new Date();
                            }
                        },
                        {
                            text: '三天前',
                            value () {
                                const date = new Date();
                                date.setTime(date.getTime() - 3600 * 1000 * 24 * 3);
                                return date;
                            }
                        },
                        {
                            text: '一周前',
                            value () {
                                const date = new Date();
                                date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                                return date;
                            }
                        }
                    ]
                },
			    v:this,
                componentName:'',
                choosedItem:null,
				columns3: [
					{
                        type: 'index',
                        width: 45,
                        align: 'center'
                    },
                    {
                        title: '用车人员',
                        key: 'ck'
                    },
                    {
                        title: '候车地点',
                        key: 'hcdz'
                    },
                    {
                        title: '目的地',
                        key: 'mdd'
                    },{
                        title: '司机',
                        key: 'sjxm'
                    },{
                        title: '车型',
                        key: 'zws'
                    },{
                        title: '出车时间',
                        key: 'yysj'
                    },{
                        title: '里程(公里)',
                        key: 'lc'
                    },{
                        title: '车费合计',
                        key: 'zj'
                    },{
                        title: '事由',
                        key: 'sy'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'primary',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.choosedItem = params.row;
                                            this.componentName = 'edit';
                                        }
                                    }
                                }, '编辑')
                            ]);
                        }
                    }
                ],
				munName:'1',
				form:{
				    ddzt:'30',
					ck:'',
                    jgmc:'',
                    startTime:'',
					endTime:''
				},
                list:[],
			}
		},
		created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '财务结算',
            },{
                title: '收款管理',
            }])
			this.getData();
        },
		mounted(){

		},
		methods:{
		    getData(){
                this.list = [];
                let startTime = this.form.startTime;
                let endTime = this.form.endTime;
                if (typeof startTime === 'object'){
                    this.form.startTime = startTime.format('yyyy-MM-dd hh:mm:ss');
                }
                if (typeof endTime === 'object'){
                    this.form.endTime = endTime.format('yyyy-MM-dd hh:mm:ss');
                }
		      	this.$http.get(this.apis.ORDER.collectingList,{params:this.form}).then((res)=>{
		      	    if (res.code === 200 && res.result){
						this.list = res.result;
                    }
				})
			},
			confirm(orderList){
                swal({
                    title: "确认已付款?",
                    text: "",
                    icon: "warning",
                    buttons:['取消','确认'],
                }).then((confirm) => {
                    if (confirm) {
                        let ids = '';
                        for (let r of orderList){
                            ids += r.id +',';
						}
                        let v = this;
                        let url = this.apis.ORDER.collectingConfirm;
                        v.$http.post(url,{'ids':ids}).then((res) =>{
                            if(res.code===200){
                                v.$Message.success(res.message);
                                this.getData();
                            }else{
                                v.$Message.error(res.message);
                            }
                        })
                    }
                });
			},
			//选项卡的切换
			MenuClick(event){
                this.form.ddzt = (event === '1' ? '30' : '40');
                this.getData();
			},
			//卡片事件
			changeLimit(mes){
				alert(mes)
			},
			print(item){
		        this.choosedItem = item;
		        this.componentName = 'print';
			},
			show(){

			}
		}
	}
</script>

<style>
</style>
