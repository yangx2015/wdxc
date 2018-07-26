<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			   :mask-closable="mesF"
			   :title="operate+'机构'">
			<div style="height:600px;overflow: scroll;">
				<Form :rules="ruleInline" ref="formItem" :model="formItem">
					<Row>
						<Col span="10">
							<FormItem label='机构编码' prop="jgbm">
								<Input type="text" v-model="formItem.jgbm" placeholder="请填写机构编码"></Input>
							</FormItem>
							<FormItem label='机构名称' prop="jgmc">
								<Input type="text" v-model="formItem.jgmc" placeholder="请填写机构名称"></Input>
							</FormItem>
							<FormItem label='机构说明' prop="jgsm">
								<Input type="text" v-model="formItem.jgsm" placeholder="请填写机构说明"></Input>
							</FormItem>
						</Col>
						<Col span="10" offset="4" style="height: 580px;overflow: scroll">
							<FormItem label='权限选择:'>
								<br>
								<menu-choose v-if="showTree" :data="orgTree" :choosedData="hasPermissionCodes" @treeChange="treeChange"></menu-choose>
								<!--<Tree :data="orgTree" show-checkbox multiple></Tree>-->
							</FormItem>
						</Col>
					</Row>
				</Form>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="save('formItem')">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>

	import menuChoose from '../../../components/menuChoose'

    export default {
        name: '',
		components:{menuChoose},
        data() {
            return {
                showModal: true,
                choosedData:[],
				operate:'新建',
                mesF: false,
				edit:false,
                showTree:false,
                formItem: {
                    fjgdm:'',
					gly:''
                },
                ruleInline: {
                    jgmc: [
                  		{ required: true, message: '请输入机构名称', trigger: 'blur' }
                  	],
              	},
				userList:[],
                orgTree: [],
                choosedIds:[],
				mode:'add',
				jdgm:'',
				parentCode:'',
				sonCode:'',
				newOrgCode:'',
				hasPermissionCodes:[],
            }
        },
        created(){
        },
		mounted(){
            this.mode = this.$parent.mode;
            if (this.mode == 'edit'){
                this.operate = '编辑'
                this.formItem = this.$parent.currentNode;
                this.parentCode = this.$parent.currentNode.fjgdm;
                this.sonCode = this.$parent.currentNode.jgdm;
                this.getHasPermissionCodes();
                this.getOrgPermissionTree();
			}else{
                this.operate = '新增'
                this.formItem.fjgdm = this.$parent.parentNode.jgdm;
                this.parentCode = this.$parent.parentNode.jgdm;
                this.getOrgPermissionTree();
			}
			this.getUserList();
		},
        methods: {
            treeChange(e){
                this.choosedIds = e;
            },
            // getChoosedIds(list){
            //     for(let r of list){
            //         if (r.checked)this.choosedIds.push(r.gndm);
            //         if (r.children){
            //             this.getChoosedIds(r.children);
            //         }
            //     }
            // },
            getOrgPermissionTree(){
                this.orgTree = [];
                this.$http.get(this.apis.FUNCTION.getPermissionTreeWithChecked+"?parentCode="+this.parentCode+'&sonCode='+this.sonCode).then((res) =>{
                    if(res.code===200){
                        this.orgTree = res.result;
                        this.showTree = true;
                    }
                })
            },
            getHasPermissionCodes(){
                this.hasPermissionCodes = [];
                this.$http.get(this.apis.FUNCTION.GET_ORG_FUNCTIONS+"?jgdm="+this.formItem.jgdm).then((res) =>{
                    if(res.code===200){
                        if (res.result){
                            for (let r of res.result){
                                this.hasPermissionCodes.push(r.gndm);
							}
						}
                        this.showTree = this.orgTree.length > 0;
                    }
                })
            },
            getUserPermissionTree(){
                this.orgTree = [];
                this.$http.get(this.apis.FUNCTION.GET_USER_PERMISSION_TREE).then((res) =>{
                    if(res.code===200){
                        this.orgTree = res.result;
                    }
                })
            },
            setOrgPermission(){
                // this.choosedIds = [];
                // this.getChoosedIds(this.orgTree);
                let ids = '';
                for (let r of this.choosedIds){
                    ids += r+',';
                }
				let orgCode = this.formItem.jgdm;
                if (this.mode == 'add'){
                    orgCode = this.newOrgCode;
                }
                this.$http.post(this.apis.FUNCTION.SET_ORG_FUNCTIONS,{'jgdm':orgCode,'gndms':ids}).then((res) =>{
                    if(res.code===200){
                        this.$Message.success(res.message);
                    }else{
                        this.$Message.error(res.message);
					}
                })
            },
            getUserList(){
                let userInfoJson = sessionStorage.getItem("userInfo");
                let userInfo = JSON.parse(userInfoJson);
                let jgdm = userInfo.jgdm;
                log('jgdm',jgdm);
                this.$http.get(this.apis.USER.QUERY,{params:{jgdmStartWith:jgdm}}).then((res) =>{
                    if(res.code===200 && res.page.list){
                        this.userList = res.page.list;
                    }
                })
			},
            save(name){
                var v = this
                v.SpinShow = true
                this.$refs[name].validate((valid) => {
                    if (valid) {
						let url = this.apis.FRAMEWORK.ADD;
                        if (this.mode == 'edit'){
		                    url = this.apis.FRAMEWORK.CHANGE;
						}
						delete this.formItem.children;
		                this.$http.post(url,this.formItem).then((res) =>{
		                    if(res.code===200){
		                        this.newOrgCode = res.result;
		                        this.setOrgPermission();
		                    }else{
		                        v.$Message.error(res.message);
		                    }
		                    v.$parent.componentName = ''
		                    v.$parent.getTree()
		                })
		            } else {
                    	v.SpinShow = false
                        v.$Message.warning('请认真填写相关信息!');
                    }
		        })
            },
            colse(){
                var v = this
                v.$parent.componentName = ''
            }
        }
    }
</script>

<style>

</style>
