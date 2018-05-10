<!--角色分配-->
<template>
	<div class="root">
		<Modal v-model='RootShow'  height="400" :closable='false' :mask-closable="false" title="角色分配">
			<div v-if="SpinShow" style="width:100%;height:100%;position: fixed;top: 0;left:0;z-index: 1111;">
				<Spin fix>
					<Icon type="load-c" size=55 class="demo-spin-icon-load"></Icon>
					<div style="font-size: 30px;">数据加载中请稍后</div>
				</Spin>
			</div>
			<div style="overflow: auto;height: 300px;">
				<div style="border-bottom: 1px solid #e9e9e9;padding-bottom:6px;margin-bottom:6px;">
					<Checkbox :indeterminate="indeterminate" :value="checkAll" @click.prevent.native="handleCheckAll">全选</Checkbox>
				</div>
				<CheckboxGroup v-model="checkAllGroup" @on-change="checkAllGroupChange">
					<div class="CheckboxList" v-for="item in roleList" 
						style="margin-bottom: 6px;margin-left:15px;">
						<Checkbox :label="item.key">{{item.value}}</Checkbox>
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

	export default {
		name: '',
		data() {
			return {
				SpinShow:false,
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
			log('数据传输',this.usermes.yhid)
		},
		mounted(){
            this.getUserRoles();
		},
		methods: {
		    getRoleList(){
                this.$http.get(this.apis.ROLE.ALL).then((res) =>{
                    if(res.code===200){
                        this.roleList = [];
                        let list = res.result;
                        for(let r of list){
                            let t = {key:r.jsId,value:r.jsmc};
							this.roleList.push(t);
							if (this.hasRole(r.jsId)){
							    this.checkAllGroup.push(r.jsId)
							}
                        }
                        log(this.roleList);
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
                this.$http.get(this.apis.ROLE.GET_USER_ROLES+'?userId='+this.usermes.yhid).then((res) =>{
                    if(res.code===200){
                        if (res.result){
                            this.userRoles = res.result;
						}else{
                            this.userRoles = [];
						}
                        this.getRoleList();
                    }
                })
			},
			close(){
				var v = this
				v.$parent.compName = ''
		   },
			save(){
		        let v = this;
				v.SpinShow = true
                let ids = new Array();
                for (let r of this.checkAllGroup){
                    ids.push(r);
				}
                this.$http.post(this.apis.ROLE.MODIFY_USER_ROLES,{userId:this.usermes.yhid,roleIds:ids}).then((res) =>{
                    if(res.code===200){
                        this.$Message.success('操作成功');
                        this.$emit('listF',res)
                        v.SpinShow = false
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