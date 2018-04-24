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
	#input div{
		float:left
	}
	#input button{
		float:left
	}
</style>
<template>
	<div class="box boxbackborder" style="padding: 5px 15px;">
		<component :is="componentName"></component>
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
				        	<!--<DatePicker v-model="cjsjInRange" format="yyyy-MM-dd" type="daterange" placement="bottom-end" placeholder="请输时间" @on-keyup.enter="findMessList()" style="width: 220px"></DatePicker>-->
							<Input v-model="findMess.zjhmLike" placeholder="请输入电子围栏名称" style="width: 200px" @on-keyup.enter="findMessList()"></Input>
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
			<Row class="margin-top-10 pageSty">
				<Page
						:total=pageTotal
						:current=page.pageNum
						:page-size=page.pageSize
						show-total
						show-elevator
						@on-change='pageChange'></Page>
			</Row>
    	</div>
		<div class="body" v-show="RootShow" style="height: 80%;">
			<div class="box-row" style="height: 80%;">
				<div class="carlist carlistBor" style="width: 180px;height: 100%;">
					<div class="box">
						<div class="tit">
							<Input value="" placeholder="请输入车辆信息..." size="small" style="width: 100%"></Input>
						</div>
						<div class="body">
							<Tree :data="data1" ref="tree"
							  show-checkbox
							  @on-check-change='checkClick'
							  @on-select-change='treeClick'></Tree>
						</div>
					</div>
				</div>
				<div class="body-F carlistBor" style="position: relative;height: 100%;">
					<div style="position: absolute;top: 3px;right: 202px;z-index: 100;" id="input">
						<Input type="text" v-model="findMess.wlmc" style="width: 40%"></Input>
						<Button type="error" size="large" @click="AddRali">取消</Button>
						<Button type="success" size="large"  @click="finish">完成</Button>
					</div>
					<my-map ref='maps' :mapDot="mapDot" @choosePoint="choosePoint"></my-map>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import myMap from '../../map/mapBK.vue'
import configApi from '@/axios/config.js'
import mixins from '@/mixins'
import formData from './comp/formData'
export default {
    name: '',
    mixins:[mixins],
    components: {
    	myMap,formData
    },
    data () {
        return {
            pageTotal:1,
            page:{
                pageNum:1,
                pageSize:5
            },
        	SpinShow:false,
			tabHeight: 220,
        	mapDot:[],
            points:[],
        	RootShow:false,
            choosedRow:null,
            componentName:'',
			//收索
			cjsjInRange:[],
			findMess: {
				cjsjInRange:'',
				zjhmLike: '',
				pageNum: 1,
				pageSize: 8,
                clIds:''
			},
			fanceId:'',
            columns10: [
                {
                  title: '序号',
                  type: 'index',
                  width: 60,
                  align: 'center'
                },
                {
                    title: '围栏名称',
                    align:'center',
                    key: 'wlmc'
                },
                {
                    title: '面积',
                    align:'center',
                    key: 'mj'
                },
                {
                    title: '状态',
                    align:'center',
                    key: 'zt',
					render:(h,p)=>{
                        return h('div',p.row.zt == '00' ? '正常':'停用')
					}
                },
                {
                    title: '创建人',
                    align:'center',
                    key: 'cjr'
                },
                {
                    title: '创建时间',
                    width:'100',
                    align:'center',
                    key: 'cjsj'
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
                                        this.choosedRow = params.row;
                                        this.componentName = 'formData';
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
                                        this.listDele(params.row.id)
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
			carIds:''
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
        this.getCarTree()
        this.findMessList();
    },
    methods: {
        //删除数据
        listDele(id){
            this.util.del(this,configApi.DZWL.DELE,[id],()=>{
                this.findMessList();
            });
        },
        finish(){
            this.saveDzwl();
        },
    	getCarTree(){
    		this.$http.get(configApi.CARTREE.QUERY).then((res) =>{
    			this.data1 = res.result
        	}).catch((error) =>{
        		console.log('error',error)
        	})
    	},
        choosePoint(points){
    	    for(let r of points){
    	        this.findMess.dlxxzb += r.lng+","+r.lat+";";
            }
        },
    	//电子围栏
    	AddRali(){
            this.RootShow = !this.RootShow
    	},
    	//树多选框
    	checkClick(event){
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
        getChoosedIds(){
            this.carIds = '';
            let nodes = this.$refs['tree'].getCheckedNodes();
            for(let r of nodes){
                this.carIds += r.clid+",";
            }
        },
        save(){
            this.getChoosedIds();
            var v = this
            this.$http.post(configApi.DZWL.setCarsDzwl,{wlid:this.fanceId,carIds:this.carIds}).then((res) =>{
				if (res.code === 200){
                    this.RootShow = !this.RootShow
                    this.findMessList();
				}else{
				    this.$Message.error(res.message);
				}
                v.SpinShow = false;
            })
        },
        saveDzwl(){
            var v = this
            this.$http.post(configApi.DZWL.ADD,v.findMess).then((res) =>{
                if (res.code === 200){
                    this.fanceId = res.message;
                    this.save();
                }
                v.SpinShow = false;
            })
        },
    	//树多点击事件
    	treeClick(mess){
    		if(mess[0]){
    			this.mapDot = mess
    		}
    	},
    	changeTime(val){
        },
        findList(){
    	    this.findMessList();
        },
    	findMessList(){
            var v = this
			delete this.findMess.dlxxzb;
			delete this.findMess.wlmc;
            this.$http.get(configApi.DZWL.QUERY,{params:v.findMess}).then((res) =>{
                v.data9 = res.page.list
                v.pageTotal = res.page.total
                v.SpinShow = false;
            })
    	},
        pageChange(event){
            var v = this
            v.findMess.pageNum = event
            v.findMessList()
        },
    }
};
</script>

<style>
</style>
