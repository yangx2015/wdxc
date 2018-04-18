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
	<div class="box">
		<div class="tit" v-show="!RootShow">
    		<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
    			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
    			<div style="height: 45px;line-height: 45px;">
    				<Row class="margin-top-10">
			    		<Col span="3">
			    			<span class="titmess">电子围栏</span>
			    		</Col>
				        <Col span="15">
				        	<DatePicker v-model="datetime" type="datetime" placeholder="请输时间" style="width: 220px" @on-change="changeTime"></DatePicker>
				        	<Input v-model="findMess.like_CarNumber" placeholder="..." style="width: 200px" @on-change="findMessList"></Input>
				        	<Input v-model="findMess.like_ScName" placeholder="..." style="width: 200px" @on-change="findMessList"></Input>
				        </Col>
				        <Col span="6" class="butevent">
				        	<Button type="success" @click="findList">
				        		<Icon type="search"></Icon>
								<!--查询-->
				        	</Button>
				        	<Button type="primary" @click="RootShow = !RootShow">
				        		<Icon type="plus-round"></Icon>
				        	</Button>
				        </Col>
				    </Row>
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
import mixins from '@/mixins'
export default {
    name: '',
    mixins:[mixins],
    components: {
    	myMap,
    },
    data () {
        return {
        	SpinShow:true,
			tabHeight: 220,
        	mapDot:[],
        	RootShow:true,
			//收索
            datetime:[],
            findMess:{
            	gte_StartTime:'',
        		lte_StartTime:'',
            	like_CarNumber:'',
            	like_ScName:'',
            	pageNum:1,
        		pageSize:5
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
                            }, '编辑'),
                            h('Button', {
                                props: {
                                    type: 'error',
                                    size: 'small'
                                },
                                on: {
                                    click: () => {
                                        this.remove(params.index)
                                    }
                                }
                            }, '删除')
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
            data1: [
                {
                    title: '车辆',
                    expand: true,
                    children: [
                        {
                            title: '公务车',
                            expand: true,
                            children: [
                                {
                                    title: '鄂A123456',
                                    mapCen:{
                                    	lng: 114.378443,
        								lat: 30.554572
                                    }
                                },
                                {
                                    title: '鄂A123457',
                                    mapCen:{
                                    	lng: 114.278443,
        								lat: 30.564572
                                    }
                                }
                            ]
                        },
                        {
                            title: '校巴',
                            expand: true,
                            children: [
                                {
                                    title: '鄂B655552',
                                    mapCen:{
                                    	lng: 114.378553,
        								lat: 30.554562
                                    }
                                },
                                {
                                    title: '鄂A129957',
                                    mapCen:{
                                    	lng: 114.378233,
        								lat: 30.554372
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        };
    },
    computed: {
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
        }, 1000);
    },
    methods: {
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
