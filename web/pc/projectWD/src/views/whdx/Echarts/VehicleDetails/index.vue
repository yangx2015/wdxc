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
				<span class="nscl">
					<marquee behavior="" direction="">
						据年审，还有90天以内的车量
					</marquee>
				</span>
				<div style="height: 45px;line-height: 45px;">
					<div class="margin-top-10 box-row">
						<div class="titmess">
							<span>年审提醒</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="form.cph" placeholder="请输入车牌号" style="width: 200px"></Input>
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
                    {title: "序号",  align: 'center', type: 'index'},
                    {title: '司机姓名', align: 'center',  key: 'sjxm'},
                    {title: '车牌号', align: 'center', key: 'cph'},
                    {title: '年审时间', align: 'center',  key: 'nssj'},
                ],
                pageData: [],
                form: {
                    cph: '',
                    total: 0,
                },
            }
        },
        created() {
            this.$store.commit('setCurrentPath', [{title: '首页',}, {title: '数据报表',}, {title: '年审提醒',}])
            this.tabHeight = this.getWindowHeight() - 295
            this.getData()
        },
        methods: {
            getData(){
                this.pageData = [];
                this.$http.get(this.apis.CLGL.nianshen,{params:this.form}).then((res) =>{
                    if (res.code == 200 && res.result){
                        this.pageData = res.result;
                    }
                })
            },
        }
    }
</script>
