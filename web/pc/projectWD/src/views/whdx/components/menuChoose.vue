<template>
    <Menu theme="light" :accordion="true" style="height: 500px;">
        <Submenu v-for="(item1,index1) in data" :key="item1.gndm" :name="item1.gndm">
            <template slot="title">
                <Checkbox :ref="item1.gndm+'>'" :label="item1.gndm" :value="item1.checked === 'checked'" @on-change="(b)=>change(b,item1.gndm+'>')">
                    <span>{{item1.gnmc}}</span>
                </Checkbox>
            </template>
            <div v-if="item1.children && item1.children.length > 0" v-for="(item2,index2) in item1.children">
                <MenuItem v-if="!item2.children || item2.children.length <= 0" :name="item2.gndm">
                    <Checkbox :ref="item1.gndm+'>'+item2.gndm" :label="item2.gndm" :value="item2.checked === 'checked'" @on-change="(b)=>change(b,item1.gndm+'>'+item2.gndm)">
                        <span>{{item2.gnmc}}</span>
                    </Checkbox>
                </MenuItem>
                <Submenu v-else :name="item2.gndm">
                    <template slot="title">
                        <Checkbox :ref="item1.gndm+'>'+item2.gndm+'>'" :label="item2.gndm" :value="item2.checked === 'checked'" @on-change="(b)=>change(b,item1.gndm+'>'+item2.gndm+'>')">
                            <span>{{item2.gnmc}}</span>
                        </Checkbox>
                    </template>
                    <MenuItem v-for="(item3,index3) in item2.children" :name="item3.gndm">
                        <Checkbox :ref="item1.gndm+'>'+item2.gndm+'>'+item3.gndm" :label="item3.gndm" :value="item3.checked === 'checked'" @on-change="(b)=>change(b,item1.gndm+'>'+item2.gndm+'>'+item3.gndm)">
                            <span>{{item3.gnmc}}</span>
                        </Checkbox>
                    </MenuItem>
                </Submenu>
            </div>
        </Submenu>
    </Menu>
</template>

<script>
    export default {
        name: 'menuChoose',
        data() {
            return {
                choosedIds:[]
            }
        },
        props: {
            data: {
                type: Array,
                default: function () {
                    return []
                }
            },
            choosedData: {
                type: Array,
                default: function () {
                    return []
                }
            }
        },
        mounted() {
        },
        created() {
            this.choosedIds = this.choosedData;
            console.log(this.$refs);
            for (let r in this.$refs){
                console.log(r);
            }
        },
        methods: {
            change(e){
                console.log('change',e);
                // this.$emit('treeChange',e);
            },
            // 判断是否有子节点
            hasChildren(ref){
                return ref.charAt(ref.length - 1) == '>';
            },
            hasParent(ref){
                return ref.split('>')[1] != '';
            },
            itemChange(check,ref){
                if (check){
                    this.itemCheck(ref);
                }else{
                    this.itemCancel(ref);
                }
            },
            itemCheck(ref){
                if (this.hasChildren(ref)){
                    this.checkChildren(ref);
                }
                if (this.hasParent(ref)){
                    this.checkParent(ref);
                }
            },
            checkChildren(ref){

            },
            checkParent(ref){

            },
            itemCancel(ref){
                if (this.hasChildren(ref)){
                    this.cancelChildren(ref);
                }
                if (this.hasParent(ref)){
                    this.cancelParent(ref);
                }
            },
            cancelChildren(ref){

            },
            cancelParent(ref){

            },
            getChildren(ref){
                for (let r of this.$refs){
                    console.log(r);
                }
            }
        }
    }
</script>

<style>

</style>