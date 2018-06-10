<style lang="less">
      @import '../../../../styles/common.less';
</style>
<template>
      <div>
            <Modal v-model="showModal" width='900'
                   :closable='false' :mask-closable="mesF"
                   title="批量导入">
                  <div class="box-row">
                        <div>
                              模版下载：
                        </div>
                        <div class="body-O">
                              <a href="http://47.98.39.45:9092/批量新增终端模板.xls">批量导入终端模版</a>
                        </div>
                  </div>
                  <div class="box-row">
                        <div>
                              上传文件：
                        </div>
                        <div class="body-O">
                              <Upload :action="apis.UPLOAD"
                                      :show-upload-list="true"
                                      :format="['xlsx','xls']"
                                      :on-format-error="handleFormatError"
                                      :on-success="handleSuccess"
                                      :on-error="handleError">
                                    <Button type="ghost" icon="ios-cloud-upload-outline">Upload files</Button>
                              </Upload>
                        </div>

                  </div>
                  <div slot='footer'>
                        <Button type="ghost" @click="close">取消</Button>
                        <Button type="primary" @click="save">确定</Button>
                  </div>
            </Modal>
      </div>
</template>

<script>
    import treelist from '@/data/list.js'

    export default {
        name: '',
        data() {
            return {
                mesF:false,
                showModal: true,
                defaultList: [
                ],
            }
        },
        props:{
            mess:{}
        },
        created(){
        },
        mounted(){
        },
        methods: {
            handleSuccess (res, file) {
                debugger
                console.log('上传成功')
                file.url = 'https://o5wwk8baw.qnssl.com/7eb99afb9d5f317c912f08b5212fd69a/avatar';
                file.name = '7eb99afb9d5f317c912f08b5212fd69a';
            },
            handleError(err,file){
                console.log('上传失败')
            },
            handleFormatError (file) {
                this.$Notice.warning({
                    title: '文件格式上传错误！！！',
                    desc: '请上传模版文件。'
                });
            },
            save(){
                var v = this
            },
            close(){
                this.$parent.componentName = '';
            }

        }
    }
</script>

<style>

</style>
