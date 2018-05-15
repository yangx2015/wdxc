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
							<span>付款统计</span>
						</div>
						<div class="body-r-1 inputSty">
							<DatePicker v-model="form.kssj" :options="dateOpts" type="datetime" placeholder="请输入开始时间" ></DatePicker>
							<DatePicker v-model="form.jssj" :options="dateOpts" type="datetime"  placeholder="请输入结束时间"  ></DatePicker>
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

    Date.prototype.format = function(format)
    {
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(),    //day
            "h+" : this.getHours(),   //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3),  //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
            (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                RegExp.$1.length==1 ? o[k] :
                    ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }
    export default {
        name: 'char',
        mixins: [mixins],
        components: {
        },
        data() {
            return {
                v:this,
                dateOpts: {
                    shortcuts: [
                        {
                            text: '今天',
                            value () {
                                return new Date();
                            }
                        },
                        {
                            text: '三天前',
                            value () {
                                const date = new Date();
                                date.setTime(date.getTime() - 3600 * 1000 * 24 * 3);
                                return date;
                            }
                        },
                        {
                            text: '一周前',
                            value () {
                                const date = new Date();
                                date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                                return date;
                            }
                        }
                    ]
                },
                SpinShow: true,
                tabHeight: 220,
                tableColumns: [{
                    title: "序号",
                    width: 80,
                    align: 'center',
                    type: 'index'
                },
                    {
                        title: '姓名',
                        align: 'center',
                        key: 'sjname'
                    },
                    {
                        title: '付款金额',
                        align: 'center',
                        key: 'fkze'
                    },
                ],
                pageData: [],
                form: {
                    sjxmLike: '',
                },
            }
        },
        created() {
            this.form.kssj  = this.getTodayDate() + " 00:00:00";
            this.form.jssj  = this.getTodayDate() + " 23:59:59";
            this.$store.commit('setCurrentPath', [{title: '首页',}, {title: '数据报表',}, {title: '付款统计',}])
            this.tabHeight = this.getWindowHeight() - 295
            this.getData()
        },
        methods: {
            getTodayDate(){
                let now = new Date();
                return now.format("yyyy-MM-dd");
            },
            getData(){
                let startTime = this.form.kssj;
                let endTime = this.form.jssj;
                if (typeof startTime === 'object'){
                    this.form.kssj = startTime.format('yyyy-MM-dd hh:mm:ss');
                }
                if (typeof endTime === 'object'){
                    this.form.jssj = endTime.format('yyyy-MM-dd hh:mm:ss');
                }
                this.$http.post(this.apis.ORDER.fktj,this.form).then((res) =>{
                    if (res.code == 200){
                        this.pageData = res.result;
                    }
                })
            },
        }
    }
</script>
