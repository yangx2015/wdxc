<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF'
			   :mask-closable="mesF" title="新建机构">
			<div style="overflow: auto;height: 500px;">
				<Form>
					<FormItem label='机构名称'>
						<Input type="text" v-model="formItem.jgmc" placeholder="请填写机构名称...">
						</Input>
					</FormItem>
				</Form>
			</div>
			</Form>
			<div slot='footer'>
				<Button type="ghost" @click="colse">取消</Button>
				<Button type="primary" @click="save">确定</Button>
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
                showModal: true,
                mesF: false,
				edit:false,
                formItem: {
                    fjgdm:''
                }
            }
        },
        created(){
        },
		mounted(){
            if (this.$parent.choosedRow){
                this.edit = true;
				this.formItem = this.$parent.choosedRow;
			}else{
                if (this.$parent.jgdm){
                    this.formItem.fjgdm = this.$parent.jgdm;
                }else{
                    console.log("请选择父节点")
				}
			}
		},
        methods: {
            save(){
                var v = this
				let url = configApi.FRAMEWORK.ADD;
                if (this.edit){
                    url = configApi.FRAMEWORK.CHANGE;
				}
                this.$http.post(url,this.formItem).then((res) =>{
                    if(res.code===200){
                        v.$Message.success(res.message);
                    }else{
                        v.$Message.error(res.message);
                    }
                    v.$parent.componentName = ''
                    v.$parent.getTree()
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