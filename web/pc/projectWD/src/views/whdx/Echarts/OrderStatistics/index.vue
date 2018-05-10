<style lang="less">
	@import '../../../../styles/common.less';
</style>
<!--角色管理-->
<template>
	<div class="boxbackborder">
		<Card>
			<Row class="margin-top-10" style='background-color: #fff;position: relative;'>
				<span class="tabPageTit">
    				<Icon type="ios-paper" size='30' color='#fff'></Icon>
    			</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>订单统计</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="form.sjxmLike" placeholder="请输入司机姓名" style="width: 200px"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="getData()">
								<Icon type="search"></Icon>
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<Row style="position: relative;">
				<Table :height="tabHeight" :row-class-name="rowClassName" :columns="tableColumns" :data="pageData"></Table>
			</Row>
		</Card>
	</div>
</template>

<script>
    import mixins from '@/mixins'

    export default {
        name: 'char',
        mixins: [mixins],
        components: {
        },
        data() {
            return {
                v:this,
                SpinShow: true,
                tabHeight: 220,
                tableColumns: [
                    {
                        title:"序号",
                        width:80,
                        align:'center',
                        type:'index'
                    },
                    {
                        title: '用车单位',
                        align:'center',
                        key: 'unit'
                    },
                    {
                        title: '时间',
                        align:'center',
                        width:100,
                        key: 'time'
                    },
                    {
                        title: '订单总数',
                        align:'center',
                        key: 'allpage'
                    },
                    {
                        title: '已审核',
                        align:'center',
                        key: 'OKaudit'
                    },
                    {
                        title: '未审核',
                        align:'center',
                        key: 'UNaudit'
                    },
                    {
                        title: '已取消',
                        align:'center',
                        key: 'cancel'
                    },
                    {
                        title: '司机确认',
                        align:'center',
                        key: 'dr'
                    },
                    {
                        title: '对长确认',
                        align:'center',
                        key: 'drM'
                    }
                ],
                pageData: [],
                form: {
                    sjxmLike: '',
                    total: 0,
                },
            }
        },
        created() {
            this.$store.commit('setCurrentPath', [{title: '首页',}, {title: '数据报表',}, {title: '安全驾驶',}])
            this.tabHeight = this.getWindowHeight() - 295
            this.getData()
        },
        methods: {
            getData(){
                this.$http.post(this.apis.ORDER.TJ,{params:this.form}).then((res) =>{
                    if (res.code == 200){
                        this.pageData = res.result;
                    }
                })
            },
        }
    }
</script>
