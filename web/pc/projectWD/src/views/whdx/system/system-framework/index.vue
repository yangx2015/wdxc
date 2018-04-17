<style lang="less">
    @import '../../../../styles/common.less';
	.framework{
		background: #fff;
		border:solid 1px #ededed;
		.frame-tree{
			width: 170px;
			border-right:solid 2px #d8d8d8;
		}
		.frame-mess{
			padding-top: 8px;
			position: relative;
			.addTree{
				position: absolute;
				top: 50%;left:50%;
				transform:translate(-50%,-50%);
				border: solid 3px #989898;
				border-radius:25px;
				width: 200px;
				height: 200px;
				text-align: center;
				i{
					font-size: 190px;
				}
			}
		}
	}
</style>
<template>
	<div class="topDiv">
		<component :is="componentName"></component>
		<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
			<Spin fix>
				<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
				<div style="font-size: 30px;">数据加载中请稍后</div>
			</Spin>
		</div>
		<Card >
			<Row class="margin-top-30" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>组织机构管理</span>
						</div>
					</div>
				</div>
			</Row>
			<div class="box-row framework" :style="tabHeight">
				<div class='frame-tree'>
					<div class="box">
						<div class="tit" style="margin: 6px;">
							<Button type="primary" style="width: 100%;font-size: 14px;" @click="rootClick(RootTree)">
								<b>
									组织机构管理
								</b>
							</Button>
						</div>
						<div class="body" style="margin: 6px;" :style="RootTree.children.length==0 ? TreeListStyleC : TreeListStyleF">
							<Tree v-for="(item,index) in RootTree.children" :data="item"
								  @on-select-change="treeClick"
								  @on-toggle-expand="treeToggleClick"></Tree>
						</div>
					</div>
				</div>
				<div class="body-F frame-mess">

					<div style="padding: 6px;">
						<div class="box">
							<div class="tit" style="font-size: 16px;border-bottom:solid 2px #989898;height: 35px;">
								<b>
									{{treeMess.title}}
								</b>
								<Button style="float: right;margin-right: 8px;" type="primary" shape="circle" icon="navicon-round" @click="edit(treeMess)"></Button>
								<Button style="float: right;margin-right: 8px;" type="primary" shape="circle" icon="android-add" @click="rootAdd()"></Button>
							</div>
							<div class="box-row-list" v-if="treeMess.children">
								<Card class="bodyC" v-for="(item,index) in treeMess.children">
									<p slot="title">
										<Icon type="ios-film-outline"></Icon>
										{{item.title}}
									</p>
									<div slot="extra">
										<Button type="primary" shape="circle" icon="android-add" size="small" @click="add(item)"></Button>
										<Button type="primary" shape="circle" icon="navicon-round" size="small" @click="edit(item)"></Button>
										<Button type="error" shape="circle" icon="close" size="small" @click="del(item)"></Button>
									</div>
									<div>
										详情
									</div>
								</Card>
							</div>
						</div>
					</div>
				</div>
			</div>
		</Card>
	</div>
</template>
<script>
	import mixins from '@/mixins'
    import configApi from '@/axios/config.js'
    import treeList from './comp/treelist.vue'
    import modelForm from './comp/modelForm.vue'
    export default {
    	name:'',
    	components:{
    		treeList,modelForm
    	},
    	mixins: [mixins],
        data () {
            return {
            	tabHeight:{
            		height:''
            	},
            	SpinShow:true,
            	TreeListStyleC:"text-align: center",
            	TreeListStyleF:"text-align: left",
                componentName:'',
				choosedRow:null,
                jgdm:'',
            	RootTree:{
            		title:'武汉大学',
            		children:[
		            	[
		                    {
		                        title: '校巴系统',
		                        children: [
		                            {
		                                title: 'parent 1-2',
		                                expand: true,
		                                children: [
		                                    {
		                                        title: 'leaf 1-2-1'
		                                    },
		                                    {
		                                        title: 'leaf 1-2-1'
		                                    }
		                                ]
		                            }
		                        ]
		                    }
		                ],
		                [
		                    {
		                        title: '公务系统',
		                        children: [
		                            {
		                                title: 'parent 1-2',
		                                expand: true,
		                                children: [
		                                    {
		                                        title: 'leaf 1-2-1'
		                                    },
		                                    {
		                                        title: 'leaf 1-2-1'
		                                    }
		                                ]
		                            }
		                        ]
		                    }
		                ]
            		]
            	},
                treeMess:{}
            }
        },
        created(){
        	this.$store.commit('setCurrentPath', [{
                title: '首页',
            },{
                title: '系统管理',
            },{
                title: '组织机构',
            }]),
        	this.tabHeight.height = (this.getWindowHeight() - 240)+'px'
        },
		mounted(){
            this.getTree();
		},
        methods: {
    	    getTree(){
    	    	var v = this
                this.$http.get(configApi.FRAMEWORK.GET_TREE).then((res) =>{
                    if(res.code===200){
                        v.RootTree.children = [res.result];
                        v.rootClick()
                        v.SpinShow = false
                        
                    }
                })
			},
			add(item){
    	        this.choosedRow = null;
                this.jgdm = item.jgdm;
                this.componentName = 'modelForm';
			},
			edit(item){
    	        this.choosedRow = item;
                this.componentName = 'modelForm';
			},
			del(item){
    	        this.util.del(this,configApi.FRAMEWORK.DELE,[item.jgdm],()=>{
                    this.getTree();
				});
			},
        	rootAdd(){
                this.choosedRow = null;
                this.componentName = 'modelForm';
        	},
        	treeClick(event){
        		if(event.length>0){
		      		this.treeMess = event[0]
					this.jgdm = event[0].jgdm;
        		}
        	},
        	treeToggleClick(event){
                if(event.expand){
        			this.treeClick([event])
        		}
        	},
        	rootClick(){
        		var v = this
        		v.treeMess = {
        			title : v.RootTree.title,
        			children : []
        		}
        		for(var i=0;i<v.RootTree.children.length;i++){
        			v.treeMess.children.push(v.RootTree.children[i][0])
        		}
        	}
        }
    }
</script>
