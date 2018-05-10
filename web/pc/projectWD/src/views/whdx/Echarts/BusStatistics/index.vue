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
							<span>班车统计</span>
						</div>
						<div class="body-r-1 inputSty">
							<Input v-model="form.gnmcLike" placeholder="请输入司机姓名" style="width: 200px"></Input>
						</div>
						<div class="butevent">
							<Button type="primary" @click="v.util.getPageData(v)">
								<Icon type="search"></Icon>
							</Button>
						</div>
					</div>
				</div>
			</Row>
			<Row style="position: relative;">
				<Table :height="tabHeight" :row-class-name="rowClassName" :columns="tableTiT" :data="pageData"></Table>
			</Row>
			<Row class="margin-top-10 pageSty">
				<Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator
					  @on-change='pageChange'></Page>
			</Row>
		</Card>
		<component
				:is="componentName"
				:Dictionary="Dictionary"></component>
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
                apiRoot:this.apis.AQJS,
                tabHeight: 220,
                componentName: '',
                choosedItem: null,
                //数据传输
                chmess: {},
                tableTiT: [
                    {title: "序号", width: 60, align: 'center', type: 'index', fixed: 'left'},
                    {title: '驾驶人', align: 'center', width: 120, key: 'gnmc', fixed: 'left'},
                    {title: '车牌号', align: 'center', width: 120, key: 'gndm'},
                    {title: '急加速', align: 'center', width: 120, key: 'fwdm'},
                    {title: '急刹车', align: 'center', width: 120, key: 'px'},
                    {title: '超速行驶', align: 'center', width: 120, key: 'bz'},
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
            this.util.initTable(this)
        },
        methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
        }
    }
</script>
