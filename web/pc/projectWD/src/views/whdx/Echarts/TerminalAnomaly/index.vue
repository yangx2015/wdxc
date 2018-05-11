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
							<span>终端异常</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="form.gnmcLike" placeholder="请输入司机姓名" style="width: 200px"></Input>
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
				<Table :height="tabHeight" :row-class-name="rowClassName" :columns="tableTiT" :data="pageData"></Table>
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
                componentName: '',
                choosedItem: null,
                //数据传输
                chmess: {},
                tableTiT: [
                    {
						title: "序号",
						width: 80,
						align: 'center',
						type: 'index'
					},
                    {
                        title: '终端编号',
                        align: 'center',
                        key: 'zdbh'
                    },
                    {
                        title: '车牌号',
                        align: 'center',
                        key: 'cph'
                    },
                    {
                        title: '最后线日期',
                        align: 'center',
                        key: 'time'
                    },
                    {
                        title: '离线时长',
                        align: 'center',
                        key: 'time'
                    },
                ],
                pageData: [],
                form: {
                    gnmcLike: '',
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
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
                this.pageData = [];
                this.$http.get(this.apis.CLJK.QUERY,this.form).then((res) =>{
                    if (res.code == 200){
                        for (let r of res.result){
                            if (r.zxzt == '10'){
                                this.pageData.push(r);
							}
						}
                        console.log(this.pageData);
                    }
                })
            },
        }
    }
</script>
