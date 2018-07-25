<template>
    <Menu theme="light" :accordion="true" style="height: 600px;">
        <CheckboxGroup v-model="choosedIds" @on-change="change">
            <Submenu v-for="(item1,index1) in data" :key="item1.gndm" :name="item1.gndm">
                <template slot="title">
                    <Checkbox :label="item1.gndm" :value="item1.gndm">
                        <span>{{item1.gnmc}}</span>
                    </Checkbox>
                </template>
                <div v-if="item1.children && item1.children.length > 0" v-for="(item2,index2) in item1.children">
                    <MenuItem v-if="!item2.children || item2.children.length <= 0" :name="item2.gndm">
                        <Checkbox :label="item2.gndm">
                            <span>{{item2.gnmc}}</span>
                        </Checkbox>
                    </MenuItem>
                    <Submenu v-else :name="item2.gndm">
                        <template slot="title">
                            <Checkbox :label="item2.gnmc" :value="item2.gndm">
                                <span>{{item2.gnmc}}</span>
                            </Checkbox>
                        </template>
                        <MenuItem v-for="(item3,index3) in item2.children" :name="item3.gndm">
                            <Checkbox :label="item3.gndm">
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
            }
        },
        mounted() {
        },
        created() {
        },
        methods: {
            change(e){
                console.log(e);
            }
        }
    }
</script>

<style>

</style>