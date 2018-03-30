<template>
	<div style="width:100%;height:100%;position: relative;">
		<div style="position: absolute;top:0;right: 0;">
			<ButtonGroup size="small" shape="circle">
		        <Button type="primary">月</Button>
		        <Button type="primary">周</Button>
		    </ButtonGroup>
		</div>
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
						text: '安全驾驶',
				        textStyle: {
				            color: "rgb(255, 255, 255)"
				        }
					},
					tooltip: {
						axisPointer: {
							type: 'cross',
							label: {
								backgroundColor: '#6a7985'
							}
						},
						trigger: 'axis'
					},
					legend:{
//						x: "right",
//				        y: "top",
				        orient: "vertical",
				        data: ['鄂A2345']
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					xAxis: [{
//						type: 'category',
						boundaryGap: false,
						data: ['急刹车', '急转弯', '急加速']
					}],
					yAxis: [{
						type: 'value'
					}],
					series: [
						{
							name: '鄂A2345',
							type: 'line',
							label: {
								normal: {
									show: true,
									position: 'top'
								}
							},
							areaStyle: {
								normal: {}
							},
							data: [20, 30, 50]
						},
						{
							name: '鄂A36545',
							type: 'line',
							label: {
								normal: {
									show: true,
									position: 'top'
								}
							},
							areaStyle: {
								normal: {}
							},
							data: [2, 5, 15]
						}
					],
					color: ["#6495ed", "#da70d6", "#ff69b4", "#ba55d3", "#cd5c5c", "#ffa500", "#40e0d0", "#1e90ff", "#ff6347", "#7b68ee", "#00fa9a", "#ffd700", "#6699FF", "#ff6666", "#3cb371", "#b8860b", "#30e0e0"]
				};
				dataSourceLine.setOption(option);
				window.addEventListener('resize', function() {
					dataSourceLine.resize();
				});
			});
		}
	};
</script>