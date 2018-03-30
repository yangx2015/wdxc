<!--
	收款管理
-->
<style lang="less">
    @import '../../../../styles/common.less';
</style>
<template>
	<div class="box">
		<Row class="tit" style="height: 60px;">
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
		    <Col span="6">
		    	<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
		    		<Input value="数据搜索" placeholder="数据搜索" style="width: 100%;"></Input>
		    	</div>
		    </Col>
		    <Col span="9">
		    	<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
		    		单笔费用结算公式：里程 * 单价 + 过路费 + 过桥费 + 等时费 = 合计总价
		    	</div>
		    </Col>
		    <Col span="3">
		    	<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
		    		<div v-show="munName=='1'">
		    			应收单据：80单
		    		</div>
		    		<div v-show="munName=='2'">
		    			已收单据：96单
		    		</div>
		    	</div>
		    </Col>
		</Row>
		<Row :gutter="16" class="margin-top-10 body" v-show="munName=='1'">
			<Col span="24" :lg="24" :md="24" :sm="24" :xs="24" v-for="(item,index) in list" class="margin-top-10">
				<Card style="width:100%">
			        <div slot="title">
			            <Icon type="person"></Icon>
			            	<span v-if='index==list.length-1'>
			            		生茂集团
			            	</span>
			            	<span v-else>
			            		学院{{(index+1)}}
			            	</span>
			        </div>
			        <span slot="extra">
			        	<span>
			        		应收金额：715元
			        		<Button type="success" size="small">打印</Button>
			        		<Button type="primary" size="small">确认</Button>
			        	</span>
			        </span>
			        <!--信息-->
			        <div>
			        	<Table 
			        		border 
			        		ref="selection" 
			        		:columns="columns4"
			        		height="220"
			        		:data="data1"></Table>
			        </div>
			    </Card>
			</Col>
		</Row>
		<Row :gutter="16" class="margin-top-10 body clientList" v-show="munName=='2'">
			<Col span="24" :lg="24" :md="24" :sm="24" :xs="24" v-for="(item,index) in list" class="margin-top-10">
				<Card style="width:100%">
			        <div slot="title">
			            <Icon type="person"></Icon>
			            	{{item.num}}
			        </div>
			        <span slot="extra">
			        	<span>
			        		收款金额：715元
			        		<Button type="success" size="small">打印</Button>
			        	</span>
			        </span>
			        <!--信息-->
			        <div>
			        	<Table 
			        		border 
			        		ref="selection" 
			        		:columns="columns3"
			        		height="220"
			        		:data="data1"></Table>
			        </div>
			    </Card>
			</Col>
		</Row>
	</div>
</template>

<script>
	export default{
		name:'client',
		data(){
			return {
				columns3: [
					{
                        type: 'index',
                        width: 45,
                        align: 'center'
                    },
                    {
                        title: '用车人员',
                        key: 'name'
                    },
                    {
                        title: '候车地点',
                        key: 'Sunit'
                    },
                    {
                        title: '目的地',
                        key: 'Eunit'
                    },{
                        title: '司机',
                        key: 'driver'
                    },{
                        title: '车型',
                        key: 'model'
                    },{
                        title: '出车时间',
                        key: 'time'
                    },{
                        title: '里程(公里)',
                        key: 'mileage'
                    },{
                        title: '车费合计',
                        key: 'addMoney'
                    },{
                        title: '事由',
                        key: 'mess'
                    }
                ],
				columns4: [
                    {
                        type: 'selection',
                        width: 45,
                        align: 'center'
                    },
                    {
                        title: '用车人员',
                        key: 'name'
                    },
                    {
                        title: '候车地点',
                        key: 'Sunit'
                    },
                    {
                        title: '目的地',
                        key: 'Eunit'
                    },{
                        title: '司机',
                        key: 'driver'
                    },{
                        title: '车型',
                        key: 'model'
                    },{
                        title: '出车时间',
                        key: 'time'
                    },{
                        title: '里程(公里)',
                        key: 'mileage'
                    },{
                        title: '车费合计',
                        key: 'addMoney'
                    },{
                        title: '事由',
                        key: 'mess'
                    },
                    {
                        title: 'Action',
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
                                            this.show(params.index)
                                        }
                                    }
                                }, '编辑')
                            ]);
                        }
                    }
                ],
                data1: [
                    {
                        name: '杨毛毛',
                        Sunit: '武汉大学正门',
                        Eunit: '武汉火车站',
                        driver:'杨师傅',
                        model:'小型',
                        time:'2018-01-01 09:00:00',
                        mileage:'45',
                        addMoney:'750',
                        mess:'XXXXXXXXXXXXX'
                    },
                    {
                        name: '李海',
                        Sunit: '武汉大学正门',
                        Eunit: '武汉火车站',
                        driver:'杨师傅',
                        model:'小型',
                        time:'2018-01-01 09:00:00',
                        mileage:'45',
                        addMoney:'750',
                        mess:'XXXXXXXXXXXXX'
                    },
                    {
                        name: '周晓枫',
                        Sunit: '武汉大学正门',
                        Eunit: '武汉火车站',
                        driver:'杨师傅',
                        model:'小型',
                        time:'2018-01-01 09:00:00',
                        mileage:'45',
                        addMoney:'750',
                        mess:'XXXXXXXXXXXXX'
                    },
                    {
                        name: '陈明',
                        Sunit: '武汉大学正门',
                        Eunit: '武汉火车站',
                        driver:'杨师傅',
                        model:'小型',
                        time:'2018-01-01 09:00:00',
                        mileage:'45',
                        addMoney:'750',
                        mess:'XXXXXXXXXXXXX'
                    },
                    {
                        name: '陈明',
                        Sunit: '武汉大学正门',
                        Eunit: '武汉火车站',
                        driver:'杨师傅',
                        model:'小型',
                        time:'2018-01-01 09:00:00',
                        mileage:'45',
                        addMoney:'750',
                        mess:'XXXXXXXXXXXXX'
                    }
                ],
				list:[
					{
						num:'信息学院',
						cum:[
							{
								it:'1'
							},{
								it:'2'
							}
						]
					},{
						num:'生物学院',
						cum:[
							{
								it:'1'
							},{
								it:'2'
							}
						]
					},{
						num:'人文学院',
						cum:[
							{
								it:'1'
							},{
								it:'2'
							}
						]
					},{
						num:'商学院',
						cum:[
							{
								it:'1'
							},{
								it:'2'
							}
						]
					},{
						num:'法学院',
						cum:[
							{
								it:'1'
							},{
								it:'2'
							},{
								it:'2'
							}
						]
					}
				],
				munName:'1'
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
        },
		mounted(){
			
		},
		methods:{
			//选项卡的切换
			MenuClick(event){
				console.log(event)
				this.munName=event
			},
			//卡片事件
			changeLimit(mes){
				alert(mes)
			},
			print(){
				alert('打印')
			},
			show(){
				
			}
		}
	}
</script>

<style>
</style>