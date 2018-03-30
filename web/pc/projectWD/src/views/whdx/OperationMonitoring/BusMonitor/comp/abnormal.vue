<style lang="less">
    @import "../../../../../styles/common.less";
	#abnormal{
	}
</style>
<template>
	<div id="abnormal" style="height: 100%;">
		<Table 
			border 
			:height="tabHeight" 
			:columns="columns1" 
			:data="data1"></Table>
	</div>
</template>
<script>
    export default {
    	name:'',
        data () {
            return {
            	tabHeight:'220',
                columns1: [
                	{
                		title: '序号',
                        type: 'index',
                        width: 80,
                        align: 'center'
                    },
                    {
                        title: '异常事件',
                        key: 'name'
                    },
                    {
                        title: '车辆编号',
                        key: 'age',
                        align: 'center'
                    },
                    {
                        title: '事发时间',
                        key: 'date',
                        align: 'center'
                    }
                ],
                data1: [
                    {
                        name: '车辆抛锚',
                        age: '鄂A15245',
                        date: '2016-10-03'
                    },
                    {
                        name: '车辆追尾',
                        age: '鄂A65345',
                        date: '2016-10-01'
                    },
                    {
                        name: '车辆追尾',
                        age: '鄂A19845',
                        date: '2016-10-02'
                    },
                    {
                        name: '车辆追尾',
                        age: '鄂A69345',
                        date: '2016-10-04'
                    },
                    {
                        name: '车辆追尾',
                        age: '鄂A12975',
                        date: '2016-10-04'
                    },
                    {
                        name: '车辆追尾',
                        age: '鄂A1635',
                        date: '2016-10-04'
                    },
                    {
                        name: '车辆追尾',
                        age: '鄂A12345',
                        date: '2016-10-04'
                    }
                ]
            }
        },
        created(){
        },
        mounted(){
			this.getalert()
        },
        methods:{
        	getalert(){
        		var windowHeight = window.innerHeight
        		this.tabHeight = windowHeight/2 - 118
        		console.log('浏览器高',this.tabHeight)
        	}
        }
    }
</script>
