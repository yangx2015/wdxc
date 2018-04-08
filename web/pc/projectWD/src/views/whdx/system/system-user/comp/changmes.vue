<!--角色分配-->
<template>
	<div>
		<Modal v-model='RootShow' width='800' :closable='false' :mask-closable="false" title="角色分配">
			<div style="overflow: auto;height: 300px;">
				<div style="border-bottom: 1px solid #e9e9e9;padding-bottom:6px;margin-bottom:6px;">
					<Checkbox :indeterminate="indeterminate" :value="checkAll" @click.prevent.native="handleCheckAll">全选</Checkbox>
				</div>
				<CheckboxGroup v-model="checkAllGroup" @on-change="checkAllGroupChange">
					<div v-for="item in roleList" style="margin-left:15px">
						<Checkbox :label="item.key" :checked.sync="item.checked">{{item.value}}</Checkbox>
					</div>

				</CheckboxGroup>
			</div>
			<div slot='footer'>
				<Button type="primary" @click="save">确定</Button>
				<Button type="default" @click="close">取消</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
    import configApi from '@/axios/config.js'
	export default {
		name: '',
		data() {
			return {
				RootShow: true,
				indeterminate: false,
				checkAll: false,
				checkAllGroup: [],
				userRoles:[],
				roleList: [
				],
			}
		},
		props:{
			usermes:{
				type:Object,
				default:{}
			}
		},
		created(){
			console.log('数据传输',this.usermes.yhid)
		},
		mounted(){
            this.getUserRoles();
		},
		methods: {
		    getRoleList(){
                this.$http.post(configApi.ROLE.QUERY).then((res) =>{
                    if(res.code===200){
                        this.roleList = [];
                        let list = res.page.list;
						for(let r of list){
							let t = {key:r.jsId,value:r.jsmc,checked:this.hasRole(r.jsId)};
							this.roleList.push(t);
						}
                    }
                })
			},
			hasRole(roleId){
		        for(let r of this.userRoles){
		            if (r.jsId == roleId)return true;
				}
				return false;
			},
		    getUserRoles(){
                this.$http.post(configApi.ROLE.GET_USER_ROLES+'?userId='+this.usermes.yhid).then((res) =>{
                    if(res.code===200){
                        this.userRoles = res.result;
                        this.getRoleList();
                    }
                })
			},
			close(){
				var v = this
				v.$parent.compName = ''
		   },
			save(){
                let ids = new Array();
                for (let r of this.checkAllGroup){
                    ids.push(r);
				}
                this.$http.post(configApi.ROLE.MODIFY_USER_ROLES,{userId:this.usermes.yhid,roleIds:ids}).then((res) =>{
                    if(res.code===200){
                        this.$Message.success('操作成功');
                        this.close();
                    }
                })
			},
			handleCheckAll() {
				var v = this
				if(v.indeterminate) {
					v.checkAll = false;
				} else {
					v.checkAll = !this.checkAll;
				}
				v.indeterminate = false;
				if(v.checkAll) {
					v.roleList.forEach((item, index) => {
						v.checkAllGroup.push(item.key)
					})
				} else {
					v.checkAllGroup = [];
				}
			},
			checkAllGroupChange(data) {
                var v = this
				if(data.length == v.roleList.length) {
					v.checkAll = true;
				} else {
					v.checkAll = false;
				}
			}
		}
	}
</script>

<style>

</style>