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
		name: 'dataSourceBar',
		data() {
			return {
				SpinShow:true,
				loading:this.$store.state.app.loading
			};
		},
		props: {
			Eid: {
				type: String,
				default: 'eBar'
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
				var dataSourceBar = echarts.init(document.getElementById(v.Eid));

				const option = {
					title: {
				        text: "今日校巴开班",
				        textStyle: {
				            color: "rgb(255, 255, 255)"
				        }
				    },
					color: ['#3398DB'],
					tooltip: {
						trigger: 'axis',
						formatter: '{a} <br/>{b} : {c}班',
						axisPointer: { // 坐标轴指示器，坐标轴触发有效
							type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
						}
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					xAxis: [{
						type: 'category',
						data: ['一号线路', '二号线路', '三号线路'],
						axisTick: {
							alignWithLabel: true
						}
					}],
					yAxis: [{
						type: 'value'
					}],
					series: [{
						name: '校巴线路',
						type: 'bar',
						label: {
							normal: {
								show: true,
								position: 'top'
							}
						},
						barWidth: '60%',
						data: [10, 8, 11,]
					}]
				};
				dataSourceBar.setOption(option);
				window.addEventListener('resize', function() {
					dataSourceBar.resize();
				});
			});
		}
	};
</script>