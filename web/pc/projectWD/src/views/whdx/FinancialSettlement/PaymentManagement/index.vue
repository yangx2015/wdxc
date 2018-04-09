<!--
	付款管理
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
			            应付单据
			        </MenuItem>
			        <MenuItem name="2">
			            <Icon type="android-checkbox-outline"></Icon>
			            已付单据
			        </MenuItem>
			    </Menu>
		    </Col>
		    <Col span="6">
		    	<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
		    		<Input value="数据搜索" placeholder="数据搜索"></Input>
		    	</div>
		    </Col>
		    <Col span="9">
		    	<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
					单笔费用结算：里程费 + 加班费 + 节假日补助费 = 合计总价
		    	</div>
		    </Col>
		    <Col span="3">
		    	<div style="height: 60px;line-height: 60px;background-color: #fff;border-bottom: 1px solid #dddee1;padding: 0 15px;">
		    		<div v-show="munName=='1'">
		    			应付人员：{{list.length}}
		    		</div>
		    		<div v-show="munName=='2'">
		    			已付人员：{{list.length}}
		    		</div>
		    	</div>
		    </Col>
		</Row>
		<Row :gutter="16" class="margin-top-10 body" v-show="munName=='1'">
			<Col span="24" :lg="24" :md="24" :sm="24" :xs="24" v-for="(item,index) in list" class="margin-top-10">
				<Card style="width:100%">
			        <p slot="title">
			            <Icon type="person"></Icon>
			            {{item.num}}  
			            <Icon type="ios-telephone"></Icon>
			            13562225566
			            <Icon type="stats-bars"></Icon>
			            {{item.carList.length}}
			        </p>
			        <span slot="extra">
			        	<span>
			        		合计费用：3500元
			        		<Button type="success" size="small">打印</Button>
			        		<Button type="primary" size="small">确认</Button>
			        	</span>
				        <!--<a href="#" @click.prevent="changeLimit('确付收款')">
				            <Icon type="ios-checkmark"></Icon>
				            确认付款
				        </a>-->
				        <!--打印-->
			        	<!--<Icon type="printer" color="#19be6b" size="18"></Icon>-->
			        </span>
			        <!--信息-->
			        <!--时间--从那-到哪  行车里程  加班时长  节假日  客户  合计金额-->
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
		<Row :gutter="16" class="margin-top-10 body" v-show="munName=='2'">
			<Col span="24" :lg="24" :md="24" :sm="24" :xs="24" v-for="(item,index) in list" class="margin-top-10">
				<Card style="width:100%">
			        <p slot="title">
			            <Icon type="person"></Icon>
			            {{item.num}}  
			            <Icon type="ios-telephone"></Icon>
			            13562225566
			            <Icon type="stats-bars"></Icon>
			            {{item.carList.length}}
			        </p>
			        <span slot="extra">
			        	<span @click="print" style="cursor: pointer;">
				        	<Icon type="printer" color="#19be6b" size="18"></Icon>
				        </span>
			        </span>
			        <!--信息-->
			        <!--时间--从那-到哪  行车里程  加班时长  节假日  客户  合计金额-->
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
		name:'driver',
		data(){
			return{
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
                        title: '车型',
                        key: 'model'
                    },{
                        title: '出车时间',
                        key: 'time'
                    },{
                        title: '里程(公里)',
                        key: 'mileage'
                    },{
            	    	title: '过路费',
                        key: 'driver1'
                    },{
                		title: '过桥费',
                        key: 'driver2'
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
                        title: '车型',
                        key: 'model'
                    },{
                        title: '出车时间',
                        key: 'time'
                    },{
                        title: '里程(公里)',
                        key: 'mileage'
                    },{
            	    	title: '加班费',
                        key: 'driver1'
                    },{
                		title: '节日补助',
                        key: 'driver2'
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
                        driver1:'200',
                        driver2:'200',
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
                        driver1:'200',
                        driver2:'200',
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
                        driver1:'200',
                        driver2:'200',
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
                        driver1:'200',
                        driver2:'200',
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
                        driver1:'200',
                        driver2:'200',
                        model:'小型',
                        time:'2018-01-01 09:00:00',
                        mileage:'45',
                        addMoney:'750',
                        mess:'XXXXXXXXXXXXX'
                    }
                ],
				list:[
					{
						num:'张师傅',
						carList:[
							{
								starTime:'2017-01-01',
							}
						]
					},{
						num:'王师傅',
						carList:[
							{
								starTime:'2017-01-01',
							}
						]
					},{
						num:'李师傅',
						carList:[
							{
								starTime:'2017-01-01',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03',
							}
						]
					},{
						num:'陈师傅',
						carList:[
							{
								starTime:'2017-01-01',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03',
							}
						]
					},{
						num:'赵师傅',
						carList:[
							{
								starTime:'2017-01-01',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03'
							}
						]
					},{
						num:'邱师傅',
						carList:[
							{
								starTime:'2017-01-01',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03',
							}
						]
					},{
						num:'吴师傅',
						carList:[
							{
								starTime:'2017-01-01',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03',
							}
						]
					},{
						num:'黄师傅',
						carList:[
							{
								starTime:'2017-01-01',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03',
							}
						]
					},{
						num:'杨师傅',
						carList:[
							{
								starTime:'2017-01-01',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03',
							}
						]
					},{
						num:'冯师傅',
						carList:[
							{
								starTime:'2017-01-01',
							},{
								starTime:'2017-01-02',
							},{
								starTime:'2017-01-03',
							}
						]
					},
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
                title: '付款管理',
            }])
		},
		mounted(){
			
		},
		methods:{
			//选项卡的切换
			MenuClick(event){
				this.munName=event
			},
			print(){
				alert('打印')
			}
		}
	}
</script>

<style>
</style>