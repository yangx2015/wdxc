<template>
    <Menu theme="light" :accordion="true" style="height: 500px;">
        <CheckboxGroup v-model="choosedIds" @on-change="groupChange">
            <Submenu v-for="(item1,index1) in data" :key="item1.gndm" :name="item1.gndm">
                <template slot="title">
                    <Checkbox :ref="item1.gndm+'>'" :label="item1.gndm" :value="item1.checked === 'checked'">
                        <span>{{item1.gnmc}}</span>
                    </Checkbox>
                </template>
                <div v-if="item1.children && item1.children.length > 0" v-for="(item2,index2) in item1.children">
                    <MenuItem v-if="!item2.children || item2.children.length <= 0" :name="item2.gndm">
                        <Checkbox :ref="item1.gndm+'>'+item2.gndm+'>'" :label="item2.gndm" :value="item2.checked === 'checked'">
                            <span>{{item2.gnmc}}</span>
                        </Checkbox>
                    </MenuItem>
                    <Submenu v-else :name="item2.gndm">
                        <template slot="title">
                            <Checkbox :ref="item1.gndm+'>'+item2.gndm+'>'" :label="item2.gndm" :value="item2.checked === 'checked'">
                                <span>{{item2.gnmc}}</span>
                            </Checkbox>
                        </template>
                        <MenuItem v-for="(item3,index3) in item2.children" :name="item3.gndm">
                            <Checkbox :ref="item1.gndm+'>'+item2.gndm+'>'+item3.gndm+'>'" :label="item3.gndm" :value="item3.checked === 'checked'">
                                <span>{{item3.gnmc}}</span>
                            </Checkbox>
                        </MenuItem>
                    </Submenu>
                </div>
            </Submenu>
        </CheckboxGroup>
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
        },
        methods: {
            groupChange(e){
                this.$emit('treeChange',e);
            },
        }
    }
</script>

<style>

</style>