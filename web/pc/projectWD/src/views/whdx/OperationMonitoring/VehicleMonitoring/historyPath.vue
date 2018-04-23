<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--用户管理-->
<template>
	<div class="boxbackborder">
		<component :is="componentName"></component>
		<Card>
			<Row class="margin-top-10" style='background-color: #fff'>
				<Col span="6">
					<DatePicker v-model="formItem.startTime" format="yyyy-MM-dd" type="date" placement="bottom-end" placeholder="请输入开始时间" ></DatePicker>
				</Col>
				<Col span="6">
					<DatePicker v-model="formItem.endTime" format="yyyy-MM-dd" type="date" placement="bottom-end" placeholder="请输入结束时间"  ></DatePicker>
				</Col>
				<Col span="6">
					<Button type="primary" @click="formItemList()">
						<Icon type="search"></Icon>
					</Button>
				</Col>
			</Row>
			<Row :gutter="16">
				<Col span="6" v-for="item in tableData">
					<Card>
						<Row>
							<img @click="showMap(item)" :src="'http://api.map.baidu.com/staticimage/v2?ak=evDHwrRoILvlkrvaZEFiGp30&center='+item.ksjd+','+item.kswd+'&width=300&height=200&zoom=12&markers='+item.ksjd+','+item.kswd+'|'+item.jsjd+','+item.jswd+'&markerStyles=-1,http://47.98.39.45:9092/icon/map_line_begin.png|-1,http://47.98.39.45:9092/icon/map_line_end.png'" class="imgItem">
						</Row>
						<Row>
							<Col span="8"><span>开始时间</span></Col><Col span="16"><span class="span_time">{{item.kssj}}</span></Col>
							<Col span="8"><span>结束时间</span></Col><Col span="16"><span class="span_time">{{item.jssj}}</span></Col>
							<Col span="8"><span>时长</span></Col><Col span="16"><span class="span_time">{{dateFormat(item.sc)}}分钟</span></Col>
						</Row>
					</Card>
				</Col>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>
			</Row>
		</Card>
	</div>
</template>

<script>
    Date.prototype.format = function(format)
    {
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(),    //day
            "h+" : this.getHours(),   //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length==1 ? o[k] :
                    ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }
	import historyMap from '../../map/historyTarckMap'
	import configApi from '@/axios/config.js'
	export default {
		name: 'char',
		components:{historyMap},
		data() {
			return {
                componentName:'',
                showModal:true,
				tabHeight: 220,
				compName: '',
				pageTotal: 2,
				page: {

					pageNum: 1,
					pageSize: 5
				},
				tableTiT: [
				    {
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
				    {
						title: "开始时间",
						width: 200,
						align: 'center',
						key: 'kssj'
					},
				    {
						title: "结束时间",
						width: 200,
						align: 'center',
                        key: 'jssj'
					},
                    {
                        title:'操作',
                        align:'center',
                        type: 'action',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type: 'success',
                                        icon: 'navicon-round',
                                        shape: 'circle',
                                        size: 'small'
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.showPath();
                                        }
                                    }
                                }),
                            ]);
                        }
                    }
				],
				tableData: [
				    {
				        ksjd:'114.365288',
						kswd:'30.54485',
				        jsjd:'114.374252',
						jswd:'30.546126',
						kssj: '2018-04-01 09:10:00',
						jssj: '2018-04-01 09:10:00',
					},
				    {
                        ksjd:'114.365252',
                        kswd:'30.546126',
                        jsjd:'114.353288',
                        jswd:'30.54485',
						kssj: '2018-04-01 09:10:00',
						jssj: '2018-04-01 09:10:00',
					}
				],
				//收索
				cjsjInRange:[],
				formItem: {
                    startTime:'2018-01-01 00:00:00',
                    endTime: '2018-05-05 00:00:00',
                    zdbh: 1,
                    ignition: 50,
                    brennschluss:'60'
				},
				choosedItem:null
			}
		},
		// watch: {
		// 	cjsjInRange:function(newQuestion, oldQuestion){
		// 		this.formItem.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
		// 	},
		// },
		created() {
			this.$store.commit('setCurrentPath', [{
				title: '首页',
			}, {
				title: '系统管理',
			}, {
				title: '用户管理',
			}])
			this.formItem.zdbh = this.$route.params.zdbh;
			this.formItemList();
		},
		methods: {
            dateFormat(longTypeDate){
                if(!longTypeDate)return;
                let s = longTypeDate / (1000 * 60)
                return parseInt(s);
			},
		    showMap(item){
		        this.choosedItem = item;
		      	this.componentName = 'historyMap';
			},
		    back(){
                this.$router.back();
			},
		    showPath(){
		      this.$parent.showPath();
		      this.$parent.componentName = '';
			},
            formItemList(){
                var v = this
                let p = {
                    startTime:this.formItem.startTime.replace(/T/g,' '),
                    endTime: this.formItem.endTime.replace(/T/g,' '),
                    zdbh: this.formItem.zdbh,
                    ignition: this.formItem.ignition,
                    brennschluss:this.formItem.brennschluss
				}
                this.$http.post(configApi.CLGL.GPS_HITSOR,p).then((res) =>{
                    if (res.code === 200){
                        for (let r of res.result){
                            let ksgps = r.ksgps.split(',');
                            let jsgps = r.jsjps.split(',');
                            r.ksjd = ksgps[1],
                            r.kswd = ksgps[0],
                            r.jsjd = jsgps[1],
                            r.jswd = jsgps[0]
                            console.log(r);
                        }
                        this.tableData = res.result;
					}
                })
			},
			//分页点击事件按
			pageChange(event) {
				var v = this
				v.formItem.pageNum = event
			}
		}
	}
</script>
<style>
	.span_time{
		color: #5cadff;
	}
	.imgItem{
		width: 100%;
		height: 100%;
	}
</style>
