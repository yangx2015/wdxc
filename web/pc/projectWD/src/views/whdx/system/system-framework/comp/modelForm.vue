<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			   :mask-closable="mesF"
			   :title="operate+'机构'">
			<div style="overflow: auto;">
				<Form :rules="ruleInline" ref="formItem" :model="formItem">
					<Row>
						<Col>
							<FormItem label='机构名称' prop="jgmc">
								<Input type="text" v-model="formItem.jgmc" placeholder="请填写机构名称"></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col>
							<FormItem label='权限选择:'>
								<br>
								<Tree :data="orgTree" show-checkbox multiple></Tree>
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


    export default {
        name: '',
        data() {
            return {
                showModal: true,
				operate:'新建',
                mesF: false,
				edit:false,
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
			}else{
                this.operate = '新增'
                this.formItem.fjgdm = this.$parent.parentNode.jgdm;
                this.parentCode = this.$parent.parentNode.jgdm;
			}
            this.getOrgPermissionTree();
            // this.getUserPermissionTree();
			this.getUserList();
		},
        methods: {
            getChoosedIds(list){
                for(let r of list){
                    if (r.checked)this.choosedIds.push(r.gndm);
                    if (r.children){
                        this.getChoosedIds(r.children);
                    }
                }
            },
            getOrgPermissionTree(){
                this.orgTree = [];
                this.$http.get(this.apis.FUNCTION.getPermissionTreeWithChecked+"?parentCode="+this.parentCode+'&sonCode='+this.sonCode).then((res) =>{
                    if(res.code===200){
                        this.orgTree = res.result;
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
                this.choosedIds = [];
                this.getChoosedIds(this.orgTree);
                let ids = '';
                for (let r of this.choosedIds){
                    ids += r+',';
                }
                this.$http.post(this.apis.FUNCTION.SET_ORG_FUNCTIONS,{'jgdm':this.newOrgCode,'gndms':ids}).then((res) =>{
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
