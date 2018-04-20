<style lang="less">
    @import "../../../styles/common.less";
    .historyTarck{
    	.leftMess{
    		width:260px;
    		/*background:rgba(0,0,0,0.5);*/
    		.titTop{
    			.carinput{
    				padding: 8px 10px;
    				input{
	    				background: rgba(73, 80, 96,0.7);
					    color: #fdc213;
					    border: #000;
    				}
    			}
    		}
    		.body{
    			border-top: solid 2px #000000;
    			padding-top: 4px;
    			.carliststy{
    				border: solid 2px #000000;
    				border-left: none;
    				border-right: none;
    				margin: 4px 6px;
    				padding: 4px;
    				cursor: pointer;
    			}
    		}
    	}
    	
    }
</style>
<template>
	<div class="historyTarck box-row">
		<Card>
			<div class="leftMess">
				<div class="box">
					<div class="titTop">
						<div class="carinput">
							<Input
									v-model="carNumber" size="large" placeholder="车牌号码"></Input>
						</div>
						<div class="carinput" style="text-align: center;">
							<DatePicker
									:v-model="seTime"
									format="yyyy/MM/dd"
									type="daterange"
									placement="bottom-start"
									placeholder="请选这时间"></DatePicker>
						</div>
					</div>
					<div class="body">
						<div class="carliststy" v-for="item in [,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,]">
							鄂A12345
						</div>
					</div>
				</div>
			</div>
		</Card>
		<div class="body-O" style="background-color: #000000;">
			<history-map></history-map>
		</div>
	</div>
</template>

<script>
	import historyMap from '../map/historyTarckMap' 
	export default{
		name:'historyTarck',
		components:{
			historyMap
		},
		data(){
			return {
				carNumber:'',
				seTime:[]
			}
		}
	}
</script>

<style>
</style>