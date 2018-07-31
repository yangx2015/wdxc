<style lang="less">
    @import '../../../../styles/common.less';
</style>
<template>
	<div class="boxbackborder">
		<Row style="padding-bottom: 16px;">
            <search-items :parent="v" :showCreateButton="true" :showSearchButton="true"></search-items>
        </Row>
        <Row style="position: relative;">
        	<Table :height="tableHeight" :columns="tableColumns" :data="pageData" @on-selection-change="selectionClick"></Table>
        </Row>
        <Row class="margin-top-10 pageSty">
            <Page :total=form.total :current=form.pageNum :page-size=form.pageSize show-total show-elevator @on-change='pageChange'></Page>
        </Row>
        <component :is="componentName"></component>
	</div>
</template>

<script>
    import searchItems from '../../components/searchItems'

    export default {
        name: 'roleSelectTable',
        components: {searchItems},
        props:{
            hasIds:{
                type:Array,
                default:[]
            }
        },
        data() {
            return {
                v:this,
                SpinShow: true,
                apiRoot:this.apis.ROLE,
                tableHeight: 220,
                componentName: '',
                choosedItem: null,
                tableColumns: [
                    {title: "#", width: 60, type: 'selection'},
                    {title:'角色名称',key:'jsmc',searchKey:'jsmcLike'},
                    {title:'类型',key:'jslx',dict:'ZDCLK0004'},
                    {title:'备注',key:'bz'},
                ],
                pageData: [],
                form: {
                    total: 0,
                    pageNum: 1,
                    pageSize: 8,
                },
            }
        },
        created() {
            this.util.initTable(this),
            setTimeout(() => {
                this.checkIds();
            },100)

            //alert(this.hasIds);
        },
        methods: {
            pageChange(event) {
                this.util.pageChange(this, event);
            },
            checkIds(){
                for(let r of this.hasIds){
                    for(let t of this.pageData){
                        if(t.jsId === r){
                            t._checked = true;
                        }
                    }
                }
            },
            selectionClick(arr){
                let jsIds = [];
                for(let r of arr){
                    jsIds.push(r.jsId);
                }
                this.$emit("arrIds",jsIds);
            }
        }
    }
</script>
