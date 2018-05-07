<style lang="less">
	@import "../../../styles/common.less";
	.carNumber{
		.select{
			text-align: center;
			.ivu-select-selection{
				background-color: #262743;
				color: #fff;
				.ivu-select-input{
					color: #fff;
				};
			};
			.ivu-select-dropdown{
				background-color: #262743;
				ul{
					li{
						color: #fff;
						border: solid #fff 1px;
						border-bottom: none;
					}
					li:hover{
						color: #262743;
						border: solid #262743 1px;
					}
					li:last-child{
						border-bottom: solid #fff 1px;
					}
				}
				.ivu-select-item-selected, .ivu-select-item-selected:hover{
					background-color: #8397ace6;
				}
			}
			input{
				text-align: center;
			}
		}
	}
	.sTime{
		input{
			text-align: center;
			background-color: #262743;
			color: #fff;
		}
	}
	.carMessH{
		text-align: center;
		.tit{
			color: #9b9b9b;
			font-size: 16px;
			font-weight: 600;
		}
		.mess{
			color: #fff;
			font-size: 16px;
		}

	}
	.pageListCarH{
		background-color: #303250;
		border: solid 1px #303250;
		padding: 4px;
		color: #fff;
		margin-top:6px;
		.pageMessH{
			font-size: 14px;
			margin-left: 4px;
		}
	}
	.pageListCarH:hover{
		border: solid 1px #fff;
	}
</style>
<template>
	<div class="box-row" style="background-color: #fff">
		<div class="body-F">
			<div class="box">
				<div class="body">
					<bkshow></bkshow>
				</div>
				<div style="height: 200px">

				</div>
			</div>
		</div>
		<div style="width: 260px;background-color: #262743">
			<div class="box">
				<div style="padding: 8px">
					<div class="carNumber">
						<Select class="select" v-model="carCode" filterable>
							<Option v-for="item in carList" :value="item.car"></Option>
						</Select>
					</div>
					<div class="sTime" style="margin-top: 6px">
						<DatePicker :value="time"
									format="yyyy/MM/dd"
									type="daterange"
									placement="bottom-end"
									placeholder="请选择时间段" style="width:100%"></DatePicker>
					</div>
					<div style="margin-top: 6px;text-align: center">
						<Button type="info"
						size="small"
						style="width: 60px">查询</Button>
					</div>
					<div class="carMessH" style="margin-top: 6px">
						<div class="box-row-nh">
							<div class="body-1" style="margin: 4px;">
								<div class="tit">
									行驶里程(km)
								</div>
								<div class="mess">
									200
								</div>
							</div>
							<div class="body-1" style="margin: 4px;">
								<div class="tit">
									驾驶时长(min)
								</div>
								<div class="mess">
									3600
								</div>
							</div>
						</div>
						<div class="box-row-nh">
							<div class="body-1" style="margin: 4px;">
								<div class="tit">
									驾驶次数
								</div>
								<div class="mess">
									26
								</div>
							</div>
							<div class="body-1" style="margin: 4px">
								<div class="tit">
									最高时速(km/h)
								</div>
								<div class="mess">
									150
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="body" style="padding: 8px;margin-top: 8px">
					<div class="pageListCarH" v-for="item in [,,,,,,,]">
						<div>
							<Icon type="ios-location"
								color="#00c3c1" size="22"></Icon>
							<span class="pageMessH" >
								出发地——目的地
							</span>
						</div>
						<div>
							<Icon type="ios-clock"
								  color="#2fa2d7" size="20"></Icon>
							<span class="pageMessH">
								2017/05/11 - 2017/07/11
							</span>
						</div>
						<div class="box-row-nh">
							<div class="body-1" style="margin: 4px;">
								<div class="tit">
									里程
								</div>
								<div class="mess">
									200
								</div>
							</div>
							<div class="body-1" style="margin: 4px;">
								<div class="tit">
									耗时
								</div>
								<div class="mess">
									3600
								</div>
							</div>
							<div class="body-1" style="margin: 4px;">
								<div class="tit">
									油耗
								</div>
								<div class="mess">
									200
								</div>
							</div>
							<div class="body-1" style="margin: 4px;">
								<div class="tit">
									最高时速
								</div>
								<div class="mess">
									3600
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import bkshow from '../map/mapBK_show'
	export default{
		name:'historyTarck',
		components:{
            bkshow
		},
		data(){
			return {
                carCode:'',
                carList:[
					{
					    code:'',
						car:'鄂A12345'
					},{
                        code:'',
                        car:'鄂A12045'
                    }
				],
                time:[]
			}
		}
	}
</script>

<style>
</style>