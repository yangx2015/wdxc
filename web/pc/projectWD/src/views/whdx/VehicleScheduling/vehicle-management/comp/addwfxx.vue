<template>
	<div>
		<Modal
		    v-model="showModal"
		    width='900'
		    :closable='false'
		    :mask-closable="false"
		    title="车辆违法录入">
    		<Form
    			ref="addmess"
    			:model="addmess"
    			:label-width="100"
    			:styles="{top: '20px'}">
	    		<div style="overflow: auto;height:420px">
					<Row>
						<Col span="8">
							<FormItem label='车牌号：'>
								<Input type="text" v-model="addmess.wfCph" disabled ></Input>
							</FormItem>
						</Col>
						<Col span="12">
							<FormItem label='处理状态：'>
								<Select filterable  v-model="addmess.wfStatus">
									<Option v-for="zt in wfztDict" :value="zt.key" :key="zt.key">{{zt.val}}</Option>
								</Select>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="8">
							<FormItem prop="wfDate" label='违法日期：'>
								<DatePicker type="date" v-model="addmess.wfDate"
											placement="top-start"
											style="width: 100%"
											placeholder="请选择违法日期"></DatePicker>
							</FormItem>
						</Col>
						<Col span="16">
							<FormItem label='违法地点：'>
								<Input type="text" v-model="addmess.wfSite" placeholder="请填写违法地点"></Input>
							</FormItem>
						</Col>
					</Row>
					<Row>
						<Col span="8">
							<FormItem label='违法行为：'>
								<Input type="text" v-model="addmess.wfxw" placeholder="请填写违法行为"></Input>
							</FormItem>
						</Col>
						<Col span="8">
							<FormItem label='违法记分：'>
								<Input type="text" v-model="addmess.wfScoring" placeholder="请填写违法记分"></Input>
							</FormItem>
						</Col>

						<Col span="8">
							<FormItem label='违法金额：'>
								<Input type="text" v-model="addmess.wfMoney" placeholder="请填写违法金额"></Input>
							</FormItem>
						</Col>
					</Row>
	    		</div>
    		</Form>
		    <div slot='footer'>
		    	<Button type="default" @click="colse"style="color: #949494">取消</Button>
	        	<Button type="primary" @click="AddDataListOk('addmess')">确定</Button>
		    </div>
		</Modal>
	</div>
</template>

<script>


	export default {
		name:'',
		data(){
			return {
				showModal:true,
				operate:'新增',
				editMode :false,
				//新增数据
            	addmess: {
                    wfCl:this.mess.clId,
                    wfCph: this.mess.cph,
                    wfType:'10',
                    wfDate:'',
                    wfSite:'',
                    wfScoring:'0',
                    wfMoney:'0',
                    wfxw:'',
                    wfStatus:'0'
                },
                wfztDict:[],//车辆状态
                wfztDictCode:'ZDCLK0053'
			}
		},
		props:{
			messType:{
				type:Boolean,
				default:true
			},
			mess:{
				type:Object,
				default:{}
			},
			derMess:{
				type:Object,
				default:{}
			}
		},
		created(){
			this.getDict();
		},
		methods:{

            getDict(){
                this.wfztDict = this.dictUtil.getByCode(this,this.wfztDictCode);
            },
			colse(){
				var v = this
				v.$parent.compName = ''
		   	},
		   //确认添加新用户进行前台表单数据验证
            AddDataListOk(name){
            	var v = this
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        v.$http.post(this.apis.CLGL.ADDWF,v.addmess).then((res) =>{
                            if(res.code===200){
                                v.$Message.success('保存成功');
                                v.$parent.getPageData()
                                v.$parent.compName = ''
                            }else{
                                v.$Message.error(res.message);
                            }
                        })
                    } else {
                        v.$Message.error('请认真填写信息!');
                    }
                })
            },
		}
	}
</script>

<style>
</style>
