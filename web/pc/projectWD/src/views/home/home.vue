<style lang="less">
	@import "../../styles/common.less";
	.homeE{
		.divpadd{
			padding:5px;
			.divbgcolor{
				height: 260px;
				background: rgba(0,0,0,0.5);
			}
		}
	}
</style>
<template>
	<div class="" style="height: 100%;">
		<div class="box">
			<div class="body">
				<hcl></hcl>
			</div>
		</div>
		<div v-show="false" class="homeE" style="background-color: #fff;padding: 5px 3px;">
			<Row :gutter="8" class="margin-bottom-10">
				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">
				<infor-card id-name="user_created_count" :end-val="count.createUser" iconType="planet" color="#2d8cf0" intro-text="设备总数"></infor-card>
				</Col>
				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">
				<infor-card id-name="visit_count" :end-val="count.visit" iconType="social-twitter" color="#64d572" :iconSize="50" intro-text="设备在线数量"></infor-card>
				</Col>
				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">
				<infor-card id-name="collection_count" :end-val="count.collection" iconType="social-chrome" color="#ffd572" intro-text="设备熄火数量"></infor-card>
				</Col>
				<Col :xs="24" :sm="12" :md="6" :style="{marginBottom: '10px'}">
				<infor-card id-name="transfer_count" :end-val="count.transfer" iconType="android-warning" color="#f25e43" intro-text="设备离线数量"></infor-card>
				</Col>
			</Row>
			<Row :gutter="8" class="margin-bottom-10">
				<Col span="12">
					<div class="divpadd">
						<div class="divbgcolor">
							<e-Line Eid="Eline"></e-Line>
						</div>
					</div>
				</Col>
				<Col span="12">
				<!--今日派单统计-->
					<div class="divpadd">
						<div class="divbgcolor">
							<listpie Eid="listpie"></listpie>
						</div>
					</div>
				</Col>
				
			</Row>
			<Row :gutter="8">
				<Col span="8">
				<!--连续加班统计-->
					<div class="divpadd">
						<div class="divbgcolor">
							<extra Eid="extra"></extra>
						</div>
					</div>
				</Col>
				<Col span="8">
					<!--年审提醒-->
					<div class="divpadd">
						<div class="divbgcolor">
							<y-pie Eid="ybar"></y-pie>
						</div>
					</div>
				</Col>
				<Col span="8">
					<!--安全驾驶统计-->
					<div class="divpadd">
						<div class="divbgcolor">
							<safeline Eid="saveline"></safeline>
						</div>
					</div>
				</Col>
			</Row>
			<Row :gutter="8">
				<Col span="16">
					<div class="divpadd">
						<div class="divbgcolor">
							<scbar Eid="scbar"></scbar>
						</div>
					</div>
				</Col>
				<Col span="8">
					<div class="divpadd">
						<risk></risk>
					</div>
				</Col>
			</Row>
		</div>
	</div>
</template>

<script>
	import hcl from '../whdx/components/historyCarlLine.vue'
	
	import inforCard from './components/inforCard.vue'
	import eLine from './compEcharts/line.vue'
	import yPie from './compEcharts/yearPie.vue'
	import safeline from './compEcharts/safeline.vue'
	import listpie from './compEcharts/listPie.vue'
	import extra from './compEcharts/extrabar.vue'
	import scbar from './compEcharts/scbar.vue'
	import risk from './compEcharts/riskRecord.vue'
	
	export default {
		name: 'home',
		components: {
			inforCard,eLine,yPie,
			safeline,listpie,extra,scbar,risk,hcl
		},
		data() {
			return {
				count: {
					createUser: 496,
					visit: 400,
					collection: 90,
					transfer: 6
				},
			};
		},
		computed: {},
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页'
			}])
		},
		methods: {}
	};
</script>