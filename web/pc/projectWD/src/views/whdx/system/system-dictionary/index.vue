<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--字典管理-->
<template>
	<div class="topDiv box" style="background-color: #fff;">
		<Row class="margin-top-30" style='background-color: #fff;position: relative;border-bottom: solid 2px #ededed;'>
			<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
			<div style="height: 45px;line-height: 45px;">
				<div class="margin-top-10 box-row">
					<div class="titmess">
						<span>字典管理</span>
					</div>
					<div class="body-r-1 inputSty">
						<Input v-model="findMess.lmmcLike" placeholder="请输入字典名称..." style="width: 200px"></Input>
					</div>
					<div class="butevent">
						<Button type="primary" @click="findMessList()">
								<Icon type="search"></Icon>
								<!--查询-->
							</Button>
						<Button type="primary" @click="AddDc()">
								<Icon type="plus-round"></Icon>
							</Button>
					</div>
				</div>
			</div>
		</Row>
		<div class="body padding-auto margin-5">
			<div class="box-row-list">
				<div class="bodyWidth" v-for="(item,index) in dictionary" :key='index'>
					<Card style="width:100%">
						<p slot="title">
							<Icon type="ios-film-outline"></Icon>
							{{item.lmmc}}
						</p>
						<span slot="extra">
					        <a href="#" @click.prevent="AddDcList(item,index)">
					            <Icon type="plus-circled" size="24"></Icon>
								<!--新增-->
					        </a>
					        <a href="#" @click.prevent="removeDc(item,index)" style="color: red;">
					            <Icon type="close-circled" size="24"></Icon>
								<!--删除-->
					        </a>
				        </span>
						<div style="height: 240px;">
							<div>
								<Row class="margin-bottom-10">
									<Input v-model="dictionaryMess[index]" clearable placeholder="请输入字典信息..." @on-change="findDicList(item,index,dictionaryMess[index])"></Input>
								</Row>
								<Row class="padding-2px-5px" style='background-color: #a09d9d;'>
									<Col span="2"> 序号
									</Col>
									<Col span="8"> 类目编码
									</Col>
									<Col span="8"> 类目名称
									</Col>
									<Col span="5" style="text-align: center;"> 操作
									</Col>
								</Row>
							</div>
							<div style="height: 180px;overflow: auto;">
								<Row class="padding-4px-5px" v-for='(items,indexs) in item.zdxmList'>
									<Col span="2"> {{(indexs+1)}}
									</Col>
									<Col span="8"> {{items.zddm}}
									</Col>
									<Col span="8"> {{items.zdmc}}
									</Col>
									<Col span="6" style="text-align: center;">
									<Button type="success" size='small' shape="circle" icon="edit" @click="changrDcList(item,items)"></Button>
									<Button type="error" size='small' shape="circle" icon="close" @click="removeDcList(item,items)"></Button>
									</Col>
								</Row>
							</div>
						</div>
					</Card>
				</div>
				<div v-if="SpinShow" style="width:100%;height:100%;position: absolute;top: 0;left:0;z-index: 100;">
					<Spin fix>
						<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
						<div style="font-size: 30px;">数据加载中请稍后</div>
					</Spin>
				</div>
			</div>
			<Row class="margin-top-10 pageSty">
				<!--<Page :total=pageTotal :current=page.pageNum :page-size=page.pageSize show-total show-elevator @on-change='pageChange'></Page>-->
			</Row>
		</div>
		<component :is="compName" :dicListMess='dicListMess'></component>
	</div>
</template>

<script>
	import mixins from '@/mixins'
	import configApi from '@/axios/config.js'

	import addmess from './comp/addmess.vue'
	import addmessList from './comp/addmessList.vue'
	import mess from './comp/mess.vue'
	export default {
		name: 'char',
		mixins: [mixins],
		components: {
			addmessList,
			addmess,
			mess
		},
		data() {
			return {
				dicListMess: '',
				SpinShow: true,
				compName: '',
				dictionaryMess: [], //字典信息查找
				//分页
				pageTotal: 1,
				page: {
					pageNum: 1,
					pageSize: 5
				},
				dictionary: [],
				//收索
				findMess: {
					gte_StartTime: '',
					lte_StartTime: '',
					lmmcLike: '',
					like_ScName: '',
					pageNum: 1,
					pageSize: 150
				}
			}
		},
		created() {
			this.$store.commit('setCurrentPath', [{
					title: '首页',
				}, {
					title: '系统管理',
				}, {
					title: '字典管理',
				}]),
				this.SpinShow = false;
			this.getmess()
		},
		methods: {
			getmess() {
				var v = this
				this.$http.post(configApi.DICTIONARY.QUERY, this.findMess).then((res) => {
					console.log('字典数据', res)
					v.dictionary = res.page.list
					this.SpinShow = false;
				})
			},
			colsemodal() {
				this.compName = ''
			},
			AddDc() {
				var v = this
				v.compName = 'addmess'
			},
			findMessList(mess) {
				var v = this
				this.$http.post(configApi.DICTIONARY.QUERY, this.findMess).then((res) => {
					console.log('字典数据', res)
					v.dictionary = res.page.list
				})
			},
			removeDc(item, index) {
				this.util.del(this,configApi.DICTIONARY.DELE,[item.lmdm],()=>{
                    this.getmess();
				});
			},
			AddDcList(item, index) {
				var v = this
				v.compName = 'addmessList'
				v.dicListMess = item.lmdm
			},
			findDicList(item, index, mess) {
				var v = this
				this.$http.post(configApi.DICTIONARY_LIST.QUERY, {
					'zdlmdm': item.lmdm,
					'zdmcLike': mess
				}).then((res) => {
					console.log('字典数据', res)
					if(res.code === 200) {
						v.dictionary[index].zdxmList = res.page.list
					}
					return
				})
			},
			removeDcList(item, items) {
//				this.util.del(this,configApi.DICTIONARY_LIST.DELE,[item.zdId],()=>{
//                  this.getmess();
//				});
				var v = this
				swal({
				  title: "是删除数据?",
				  text: "",
				  icon: "warning",
				  buttons:['取消','确认'],
				})
				.then((willDelete) => {
				  if (willDelete) {
					v.$http.post(configApi.DICTIONARY_LIST.DELE, {'ids': [items.zdId]}).then((res) =>{
						if(res.code===200){
							this.$Message.success(res.message);
						}else{
							this.$Message.warning(res.message);
						}
						v.getmess()
					})
				  } else {
				  }
				});	
			},
			changrDcList(item, items) {
				var v = this
				v.compName = 'addmessList'
				v.dicListMess = '你好啊！'
			},
			pageChange(event) {
				var v = this
				v.page.pageNum = event
				v.AddDc(v.page)
				//      		console.log(v.page)
			},
		}
	}
</script>