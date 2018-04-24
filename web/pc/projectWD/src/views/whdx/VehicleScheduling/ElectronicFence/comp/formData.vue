<style lang="less">
	@import '../../../../../styles/common.less';
</style>
<template>
	<div>
		<Modal v-model="showModal" width='900' :closable='mesF' :mask-closable="mesF" :title="operate+'站牌'">
			<div>
				<Form
					ref="addmess"
					:model="addmess"
					:rules="ruleInline"
					:label-width="100"
					:styles="{top: '20px'}">
					<Row>
						<Col span="12">
							<FormItem label='围栏名称'>
								<Input type="text" v-model="form.wlmc" placeholder="请填写围栏名称..."></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='状态'>
								<Select v-model="form.zt">
									<Option value="00">正常</Option>
									<Option value="10">停用</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
				</Form>
			</div>
			<div :style="divheight">
				<mapbkzs :mapDot = 'this.$parent.choosedRow' @choosePoint="choosePoint"></mapbkzs>
			</div>
			<div slot='footer'>
				<Button type="ghost" @click="close">取消</Button>
				<Button type="primary" @click="save">确定</Button>
			</div>
		</Modal>
	</div>
</template>

<script>
	import treelist from '@/data/list.js'
    import configApi from '@/axios/config.js'
    import mixins from '@/mixins'
    
    import mapbkzs from '../../../map/mapBKZS.vue'
	export default {
		name: '',
		components: {
			mapbkzs
		},
		mixins: [mixins],
		data() {
			return {
				divheight:{height:'220px'},
			    operate:'新建',
				showModal: true,
                mesF:false,
				form: {
                    zdbh:'',
					mc: '',
					xh: '',
                    cs: '',
                    dz:'',
				},
                ruleInline:{

				},
                addmess:{

				}

			}
		},
		created(){
			this.divheight.height = (this.getWindowHeight() - 360)+'px'
			if (this.$parent.choosedRow){
				this.form = this.$parent.choosedRow;
				this.operate = '编辑'
			}
		},
        mounted(){
        },
		methods: {
		    save(){
		    	var v = this
		        let url = configApi.DZWL.ADD;
				if (this.$parent.choosedRow){
                    url = configApi.DZWL.CHANGE;
				}
                this.form.wlmj = 1000;
                this.$http.post(url,this.form).then((res) =>{
                    if(res.code===200){
                        var v = this
                        v.$parent.findMessList()
                        this.$Message.success(res.message);
                        v.$parent.componentName = ''
                    }
                }).catch((error) =>{
					v.$Message.error('出错了！！！');
				})
			},
			choosePoint(points){
	            this.findMess.dlxxzb = '';
	            for(let r of points){
	    	        this.findMess.dlxxzb += r.lng+","+r.lat+";";
	            }
	            console.log(this.findMess.dlxxzb);
	        },
			close(){
		        let v = this;
                v.$parent.componentName = ''
                v.$parent.findMessList()
			}

		}
	}
</script>

<style>

</style>
