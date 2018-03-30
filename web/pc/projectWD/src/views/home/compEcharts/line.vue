<style lang="less">
	@import "../../../styles/common.less";
</style>
<template>
	<div style="width: 100%;height: 100%;position: relative;">
		<div style="width:100%;height:100%;" :id="Eid"></div>
		<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
			<Spin fix>
	            <Icon type="load-c" :size=loading.size class="demo-spin-icon-load"></Icon>
	            <div style="font-size: 30px;">{{loading.text}}</div>
	        </Spin>
		</div>
	</div>
</template>

<script>
	import echarts from 'echarts';

	export default {
		name: 'dataSourceLine',
		data() {
			return {
				SpinShow:true,
				loading:this.$store.state.app.loading
			};
		},
		props: {
			Eid: {
				type: String,
				default: 'eEline'
			}
		},
		created(){
			setTimeout(() => {
                this.SpinShow = false;
            }, 1000);
		},
		mounted() {
			var v = this
			this.$nextTick(() => {
				var dataSourceLine = echarts.init(document.getElementById(v.Eid));

				const option = {
					title: {
						text: '每日订单走势图',
				        textStyle: {
				            color: "rgb(255, 255, 255)"
				        }
					},
					tooltip: {
						trigger: 'axis',
						axisPointer: {
							type: 'cross',
							label: {
								backgroundColor: '#6a7985'
							}
						}
					},
					legend:{
						x: "right",
				        y: "top",
				        orient: "vertical",
				        data: ['订单数量']
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					xAxis: [{
//						type: 'category',
						boundaryGap: true,
						data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
					}],
					yAxis: [{
						type: 'value'
					}],
					series: [
						{
							name: '订单数量',
							type: 'line',
							stack: '总量',
							label: {
								normal: {
									show: true,
									position: 'top'
								}
							},
							areaStyle: {
								normal: {}
							},
							data: [20, 30, 50, 10, 15, 0, 60]
						}
					],
					color: ["#6495ed", "#32cd32", "#ff7f50", "#87cefa", "#da70d6", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
				};
				dataSourceLine.setOption(option);
				window.addEventListener('resize', function() {
					dataSourceLine.resize();
				});
			});
		},
		methods:{
		}
	};
</script>