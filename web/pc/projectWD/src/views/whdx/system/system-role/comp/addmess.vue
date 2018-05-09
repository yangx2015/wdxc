<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" :title="operate+'角色'">
			<div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
				<Spin fix>
					<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
					<div style="font-size: 30px;">数据加载中请稍后</div>
				</Spin>
			</div>
			<div style="overflow: auto;height: 300px;">
				<Form
	    			ref="addmess"
	    			:model="addmess"
	    			:rules="ruleInline"
	    			:label-width="100"
	    			:styles="{top: '20px'}">
		    		<div style="overflow: auto;height: 300px;">
						<Row>
							<Col span="12">
								<FormItem prop="jsmc" label='角色名称：'>
									<Input type="text" v-model="addmess.jsmc" placeholder="请填写角色名称">
									</Input>
								</FormItem>
							</Col>
							<Col span="12">
								<FormItem prop="jsId" label='角色代码：'>
									<Input type="text" v-model="addmess.jsId" placeholder="请填写角色代码" :disabled="edit">
									</Input>
								</FormItem>
							</Col>
						</Row>
						<Row>
							<Col span="12">
								<FormItem label='类型：' placeholder="请选择角色类型...">
									<Select filterable clearable  v-model="addmess.jslx">
										<Option v-for = '(item,index) in Dictionary' :value="item.key">{{item.val}}</Option>
									</Select>
								</FormItem>
							</Col>
							<Col span="12">
								<FormItem label='备注：'>
									<Input type="text" v-model="addmess.sm" placeholder="请填写备注信息...">
									</Input>
								</FormItem>
							</Col>
						</Row>

						<Row>
							<Col>
								<FormItem label='权限选择:'>
									<Tree :data="data4" show-checkbox multiple></Tree>
								</FormItem>
							</Col>
						</Row>
		    		</div>
	    		</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	import treelist from '@/data/list.js'


	export default {
		name: '',
		data() {
			return {
				SpinShow:false,
				showModal: true,
				operate:'新建',
				mesF: false,
				addmess: {
					jsmc: '',
					jslx: '',
					sm: '',
				},
				ruleInline: {
                  	jsmc: [
                  		{ required: true, message: '请输角色名称', trigger: 'blur' }
                  	],
                  	jsId: [
                  		{ required: true, message: '请输角色代码', trigger: 'blur' }
                  	]
              	},
				data4: [
                ],
				choosedIds :[],
                Dictionary:[],
                lmdmDictionary:'ZDCLK0004',
				edit:false,
			}
		},
		props:{
			usermesType:{
				type:Boolean,
				default:true
			},
		},
		created(){
            if(!this.usermesType){
                this.addmess = this.$parent.messdata
                this.operate = '编辑';
                this.edit = true;
            }
        },
		mounted(){
			this.getRolePermissionTree();
            this.getLXDic();
		},
		methods: {
            getLXDic(){
                this.Dictionary = this.dictUtil.getByCode(this,this.lmdmDictionary);
            },
		    getRolePermissionTree(){
                let url = this.apis.FUNCTION.GET_ROLE_PERMISSION_TREE;
                if (this.addmess.jsId){
                    url += "?jsdm="+this.addmess.jsId;
				}
                this.$http.get(url).then((res) =>{
                    if(res.code===200){
                        this.data4 = res.result[0].functions;
                    }
                })
			},
		    setRolePermission(){
                this.getChoosedIds(this.data4);
                let ids = '';
                for (let r of this.choosedIds){
                    ids += r+',';
				}
                this.$http.post(this.apis.FUNCTION.SET_ROLE_FUNCTIONS,{'jsdm':this.addmess.jsId,'gndms':ids}).then((res) =>{
                    if(res.code===200){
                        this.$Message.success(res.message);
                    }
                })
			},
			getChoosedIds(list){
		        for(let r of list){
		            if (r.checked)this.choosedIds.push(r.gndm);
		            if (r.children){
		             	this.getChoosedIds(r.children);
					}
				}
			},
			AddDataListOk(name){
                var v = this
                v.SpinShow = true
                this.$refs[name].validate((valid) => {
                    if (valid) {
						if(v.usermesType){
	                		v.$http.post(this.apis.ROLE.ADD,v.addmess).then((res) =>{
								if(res.code===200){
                                    this.setRolePermission();
									v.$emit('listF',res)
                                }else{
                                    v.$Message.error(res.message);
								}
                                v.SpinShow = false
							}).catch((error) =>{
								v.$Message.error('出错了！！！');
								v.SpinShow = false
							})
						}else{
							v.$http.post(this.apis.ROLE.CHANGE,v.addmess).then((res) =>{
								if(res.code===200){
                                    this.setRolePermission();
									v.$emit('listF',res)
								}else{
                                    v.$Message.error(res.message);
								}
                                v.SpinShow = false
							}).catch((error) =>{
								v.$Message.error('出错了！！！');
								v.SpinShow = false
							})
						}
                    } else {
                    	v.SpinShow = false
                        v.$Message.warning('请认真填写角色信息!');
                    }
                })
            },
			colse() {
				var v = this
				v.$parent.compName = ''
			}
		}
	}
</script>

<style>

</style>
