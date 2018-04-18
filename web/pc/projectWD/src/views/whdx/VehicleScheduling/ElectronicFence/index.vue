<style lang="less">
    @import "../../../../styles/common.less";
</style>
<style type="text/css" scoped="">
	.carlist{
	    padding: 3px 4px;
	    background-color: #fff;
	}
	.carlistBor{
		border: solid 1px #bbb3b3;
	}
</style>
<template>
	<div class="box boxbackborder" style="padding: 5px 15px;">
		<div class="tit" v-show="!RootShow">
    		<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
    			<div style="height: 45px;line-height: 45px;">
    				<div class="margin-top-10 box-row">
			    		<div class="titmess">
			    			<span class="titmess">电子围栏</span>
			    		</div>
				        <div class="body-r-1 inputSty">
				        	<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>
							<Input v-model="findMess.zjhmLike" placeholder="请输入用户名" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
						</div>
				        <div class="butevent">
				        	<Button type="success" @click="findList">
				        		<Icon type="search"></Icon>
								<!--查询-->
				        	</Button>
				        	<Button type="primary" @click="RootShow = !RootShow">
				        		<Icon type="plus-round"></Icon>
				        	</Button>
				        </div>
				    </div>
    			</div>
			</Row>
    	</div>
    	<div class="body" v-show="!RootShow">
		    <Table
		    	:height="tabHeight"
		    	:row-class-name="rowClassName"
		    	:columns="columns10"
		    	:data="data9"></Table>
		    <div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
				<Spin fix>
		            <Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
		            <div style="font-size: 30px;">数据加载中请稍后</div>
		        </Spin>
			</div>
    	</div>
		<div class="body" v-show="RootShow" style="height: 100%;">
			<div class="box-row" style="height: 100%;">
				<div class="carlist carlistBor" style="width: 180px;height: 100%;">
					<div class="box">
						<div class="tit">
							<Input value="" placeholder="请输入车辆信息..." size="small" style="width: 100%"></Input>
						</div>
						<div class="body">
							<Tree :data="data1"
							  show-checkbox
							  @on-check-change='checkClick'
							  @on-select-change='treeClick'></Tree>
						</div>
					</div>
				</div>
				<div class="body-F carlistBor" style="position: relative;height: 100%;">
					<div style="position: absolute;top: 3px;right: 2px;z-index: 100;">
						<Button type="error" size="large" @click="AddRali">电子围栏</Button>
						<Button type="success" size="large"  @click="RootShow = !RootShow">完成</Button>
					</div>
					<my-map ref='maps' :mapDot="mapDot"></my-map>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import myMap from '../../map/mapBK.vue'
import configApi from '@/axios/config.js'
import mixins from '@/mixins'
export default {
    name: '',
    mixins:[mixins],
    components: {
    	myMap,
    },
    data () {
        return {
        	SpinShow:false,
			tabHeight: 220,
        	mapDot:[],
        	RootShow:false,
			//收索
			cjsjInRange:[],
			findMess: {
				cjsjInRange:'',
				zjhmLike: '',
				pageNum: 1,
				pageSize: 5
			},
            columns10: [
                {
                  title: '序号',
                  type: 'index',
                  width: 60,
                  align: 'center'
                },
                {
                    title: '车牌号',
                    align:'center',
                    key: 'FleetNumber'
                },
                {
                    title: '车型',
                    align:'center',
                    key: 'FleetName'
                },
                {
                    title: '围栏名称',
                    align:'center',
                    key: 'captainName'
                },
                {
                    title: '创建人',
                    align:'center',
                    key: 'Founder'
                },
                {
                    title: '创建时间',
                    width:'100',
                    align:'center',
                    key: 'time'
                },
                {
                    title: '操作',
                    key: 'action',
                    width: 150,
                    align: 'center',
                    render: (h, params) => {
                        return h('div', [
                            h('Button', {
                                props: {
									type: 'success',
									icon: 'edit',
									shape: 'circle',
									size: 'small'
								},
								style: {
									cursor: "pointer",
									margin: '0 8px 0 0'
								},
                                on: {
                                    click: () => {
                                        this.show(params.index)
                                    }
                                }
                            }),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    icon:'close',
                                    shape:'circle',
                                    size:'small'
                                },
                                style: {
                                    cursor: "pointer",
                                    margin:'0 8px 0 0'
                                },
                                on: {
                                    click: () => {
                                        this.remove(params.index)
                                    }
                                }
                            })
                        ]);
                    }
                }
            ],
            data9: [
                {
                  FleetNumber:'鄂A12345',
                  FleetName:'公务车',
                  captainName:'公务车电子围栏0001',
                  Founder:'王铭',
                  time:'20170203 08:20:22'
                }
            ],
            data1:  [{
                "children": [{
                    "mapCenMap": {},
                    "title": "111111"
                }, {
                    "mapCenMap": {},
                    "title": "1212"
                }, {
                    "mapCenMap": {},
                    "title": "121212"
                }],
                "expand": true,
                "title": "小车"
            }, {
                "children": [{
                    "mapCen": {
                        "lat": "31.3366798136",
                        "lng": "114.2353441913"
                    },
                    "title": "鄂A66771"
                }, {
                    "mapCenMap": {
                        "lat": "31.2173317951",
                        "lng": "114.1443044286"
                    },
                    "title": "鄂A66773"
                }, {
                    "mapCenMap": {},
                    "title": "鄂TEST"
                }],
                "expand": true,
                "title": "校巴"
            }],
        };
    },
    computed: {
    },
    watch: {
		cjsjInRange:function(newQuestion, oldQuestion){
			this.findMess.cjsjInRange = this.getdateParaD(newQuestion[0]) + ',' + this.getdateParaD(newQuestion[1])
		},
	},
    created(){
		this.$store.commit('setCurrentPath', [{
				title: '首页',
		},{
				title: '车辆管理',
		},{
				title: '电子围栏',
		}])
		this.tabHeight = this.getWindowHeight() - 212
		setTimeout(() => {
            this.SpinShow = false;
        }, 500);
        this.getCarTree()
    },
    methods: {
    	getCarTree(){
    		this.$http.get(configApi.CARTREE.QUERY).then((res) =>{
    			console.log('数据结构数据',res)
    			this.data1 = res.result
        	}).catch((error) =>{
        		console.log('error',error)
        	})
    	},
    	//电子围栏
    	AddRali(){
//  		this.$refs.maps.addPolygonPoint()
    	},
    	//树多选框
    	checkClick(event){
    		console.log('2',event[0])
    		var v = this
    		v.mapDot = []
    		if(event[0]){
    			v.getLeaf([event[0]])
    		}else if(event==undefined){
    			v.getLeaf([])
    		}
    	},
    	getLeaf(list){
    		let v = this;
    		if(list.length>0){
    			for (let r of list){
    				if (r.children){
    					v.getLeaf(r.children)
    				}else{
    					v.mapDot.push(r);
    				}
    			}
    		}else{
    			v.mapDot.push([]);
    		}
    	},
    	//树多点击事件
    	treeClick(mess){
    		console.log('1',mess)
    		if(mess[0]){
    			this.mapDot = mess
    		}
    	},
    	changeTime(val){
        },
        findList(){
        	alert('数据查询')
        },
    	findMessList(){
    		var v = this
//      		axios.get('carLogs/pager',this.findMess).then((res) => {
//                  v.tableData = res.data
//                  v.pageTotal = res.total
//              })
    	},
    }
};
</script>

<style>
</style>
