<style scoped>
    .expand-row{
        margin-bottom: 16px;
        padding-left: 40px;
    }
    .topmar{
    	margin-top: 10px;
    }
</style>
<template>
    <div>
    	<div slot="header">
        	<Menu mode="horizontal" theme="dark" active-name="1" @on-select="MenuClick">
			    <MenuItem name="1">
			        <Icon type="ios-people"></Icon>
			        基本信息
			    </MenuItem>
			    <MenuItem name="2">
			        <Icon type="ios-paper"></Icon>
			        流程记录
			    </MenuItem>
			    <MenuItem name="3">
			        <Icon type="ios-paper"></Icon>
			        车辆轨迹
			    </MenuItem>
			    <MenuItem name="4">
			        <Icon type="ios-paper"></Icon>
			        原始单据
			    </MenuItem>
			</Menu>
        </div>
    	<div v-show="horizontal=='1'" style="overflow: hidden;" class="topmar">
				<Row class="expand-row">
        	<Col span="6">
            	<h3>候车地点：</h3>
            	<div>
            		{{row.hcdz}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>目的地：</h3>
            	<div>
            		{{row.hcdz}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>单据类型：</h3>
            	<div>
            		{{row.wf}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>预约时间：</h3>
            	<div>
            		{{row.yysj}}
            	</div>
            </Col>
        </Row>
        <Row class="expand-row">
            <Col span="6">
            	<h3>行车里程：</h3>
            	<div>
            		{{row.lc}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>里程单价：</h3>
            	<div>
            		{{row.dj}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>里程费用：</h3>
            	<div>
            		{{row.lcf}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>等时：</h3>
            	<div>
            		{{row.Etc}}
            	</div>
            </Col>
        </Row>
        <Row class="expand-row">
            <Col span="6">
            	<h3>过路费：</h3>
            	<div>
            		{{row.glf}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>过桥费：</h3>
            	<div>
            		{{row.BridgeFee}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>费用合计：</h3>
            	<div>
            		{{row.zj}}
            	</div>
            </Col>
            <Col span="6">
            	<h3>备注：</h3>
            	<div style="word-wrap:break-word">
            		{{row.bz}}
            	</div>
            </Col>
        </Row>
	        </div>
	        <div v-show="horizontal=='2'" style="overflow: hidden;" class="topmar">
				<Steps :current="settleStatus">
					<Step title="订单创建" content="2018-01-12 14:30:39"></Step>
					<Step title="任务分派" content="2018-01-12 14:30:39"></Step>
					<Step title="队长确认" content="2018-01-12 14:30:39" ></Step>
					<Step title="订单修改" content="2018-01-12 14:30:39" ></Step>
					<Step title="订单修改" content="2018-01-16 14:10:39" ></Step>
					<Step title="财务核算" content="2018-01-12 14:30:39" ></Step>
				</Steps>
	        </div>
	        <div v-show="horizontal=='3'" style="overflow: hidden;text-align: center;" class="topmar">
	        	<img src="../../../../../images/map.png" width="60%"/>
	        </div>
	        <div v-show="horizontal=='4'" style="overflow: hidden;" class="topmar">
	        	<Col span="6" class="margin-top-5">
					<h5>用车单位：</h5>{{detail.initialOracle}}
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>用车人：</h5>杨毛毛
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>电话：</h5>13113131313
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>出车时间：</h5>2018-2-10 10:00:00
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>候车地点：</h5>武汉大学正门
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>目的地：</h5>汉口火车站
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>行车里程：</h5>45公里
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>等时：</h5>50分钟
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>过桥费：</h5>30元
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>过路费：</h5>30元
				</Col>
				<Col span="6" class="margin-top-5">
					<h5>合计金额：</h5>1000元
				</Col>
	        </div>
    </div>
</template>
<script>
    import configApi from '@/axios/config.js'
    export default {
    	name:'',
    	data(){
    		return {
    			horizontal:'1',
    			settleStatus:5,
				detail:{

				}
    		}
    	},
        props: {
            row: Object
        },
		mounted(){
            this.getOrderDetails();
		},
        methods:{
        	MenuClick(event){
				var v = this
				v.horizontal = event
			},
            getOrderDetails(){
                this.$http.get(configApi.ORDER.orderDetails+'?id='+this.row.id).then((res) =>{
                    if (res.code == 200){
                        this.detail = res.result;
					}
                })
			}
        }
    };
</script>
