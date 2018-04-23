<template>
	<div>
		<Modal
		    v-model="showModal"
		    :closable='false'
		    width='800'
		    :mask-closable="false"
		    title="新增站点">
		    <div>
		    	<Row :gutter='30' style="margin-bottom: 15px;">
		    		<Col span="6">
		    			<Input v-model="formItem.mc"
		    				placeholder="请输入站点名称...">
		    			</Input>
		    		</Col>
		    		<Col span="6">
					<Select filterable clearable  v-model="formItem.fw"
							placeholder="请选择站点范围...">
						<Option value="5">5</Option>
						<Option value="10">10</Option>
						<Option value="20">20</Option>
						<Option value="30">30</Option>
						<Option value="30">30</Option>
					</Select>
		    		</Col>
		    		<Col span="6">
	    				<Input v-model="formItem.jd"
	    					readonly="readonly"
	    					placeholder="用鼠标选取站点坐标">
		    			</Input>
		    		</Col>
		    		<Col span="6">
	    				<Input v-model="formItem.wd"
	    					readonly="readonly"
	    					placeholder="用鼠标选取站点坐标">
		    			</Input>
		    		</Col>
		    	</Row>
		    	<Row :gutter='30' style="margin-bottom: 15px;">
		    		<Col span="12">
		    			<Select filterable clearable  v-model="formItem.zt"
		    				placeholder="请选择站点状态...">
					        <Option value="00">正常</Option>
					        <Option value="10">停用</Option>
					    </Select>
		    		</Col>
		    		<Col span="12">
	    				<Input v-model="formItem.bz" placeholder="备注信息...">
		    			</Input>
		    		</Col>
		    	</Row>
		    	<div style="height: 400px;">
		    		<get-map-dot ref='maps'
		    			:center="mapCenter"
		    			@getDot="getDot"></get-map-dot>
		    	</div>
		    </div>
		    <div slot='footer'>
		    	<Button type="ghost" @click="close">取消</Button>
	        	<Button type="primary" @click="save">确定</Button>
		    </div>
		</Modal>
	</div>
</template>

<script>
	import getMapDot from '../../../../map/getMapDot.vue'

    import configApi from '@/axios/config.js'
	export default{
		name:'',
		components: {
	    	getMapDot,
	    },
		data(){
			return{
				showModal:true,
				formItem:{
                    mc:'',
                    fw:'',
				    jd:'',
                    zt:'',
                    bz:'',
					wd:''
				},
				mapCenter:{
	        		lng: 114.372443,
	        		lat: 30.544572
	        	},
			}
		},
		created(){
		},
		mounted(){
            if (this.$parent.choosedRow !== null){
                this.formItem = this.$parent.choosedRow;
            }
        },
		methods:{
			close(){
				var v = this
				v.$parent.componentName = ''
                v.$parent.getmess();
		    },
			save(){
                var v = this
                v.$http.post(configApi.ZD.ADD,v.formItem).then((res) =>{
                    if(res.code===200){
                        v.$Message.success(res.message);
                    }else{
                        v.$Message.warning(res.message);
                    }
                }).then((res)=>{
                    v.close();
                }).catch((e)=>{
                    console.log(e);
                    v.$Message.error("失败了！");
                })

            },
		    getDot(e){
                this.formItem.jd = e.point.lng
        		this.formItem.wd = e.point.lat
                console.log(this.formItem);
            }
		}
	}
</script>

<style>
</style>
