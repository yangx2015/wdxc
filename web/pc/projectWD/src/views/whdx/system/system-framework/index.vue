<style lang="less">
    @import '../../../../styles/common.less';
	.framework{
		background: #fff;
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
	<div class="box boxbackborder">
		<div class="tit" style="border-bottom: solid 2px #d8d8d8;">
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
		</div>
		<div class="body">
			<div class="box-row framework">
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
					<Button v-if="RootTree.length==0" 
						class="addTree" type="primary"
						@click="rootAdd()">
						<Icon type="android-add" color='#d8d8d8'></Icon>
					</Button>
					<div v-else style="padding: 6px;">
						<div class="box">
							<div class="tit" style="font-size: 16px;border-bottom:solid 2px #989898;height: 35px;">
								<b>
									{{treeMess.title}}
								</b>
							    <Button 
							    	style="float: right;"
							    	type="primary" shape="circle" 
							    	icon="navicon-round" size="small"></Button>
							</div>
							<div class="box-row-list" v-if="treeMess.children">
								<Card class="bodyC" v-for="(item,index) in treeMess.children">
							        <p slot="title">
							            <Icon type="ios-film-outline"></Icon>
							            {{item.title}}
							        </p>
							        <div slot="extra">
							            <Button type="primary" shape="circle" icon="navicon-round" size="small"></Button>
										<Button type="error" shape="circle" icon="close" size="small"></Button>
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
		</div>
	</div>
</template>
<script>
	import mixins from '@/mixins'
	
	import treeList from './comp/treelist.vue'
    export default {
    	name:'',
    	components:{
    		treeList
    	},
    	mixins: [mixins],
        data () {
            return {
            	TreeListStyleC:"text-align: center",
            	TreeListStyleF:"text-align: left",
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
            this.rootClick()
        },
        methods: {
        	rootAdd(){
        		var newData = {
        			title:'武汉大学车辆管理平台',
        			children:[]    			
        		}
        		this.dataTree.push(newData)
        	},
        	treeClick(event){
        		console.log('树节点数据',event)
        		if(event.length>0){
		      		this.treeMess = event[0]
        		}
        	},
        	treeToggleClick(event){
        		if(event.expand){
        			this.treeClick([event])
        		}
        		console.log('树节点Toggle数据',event)
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
