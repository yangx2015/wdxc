<template>
	<div>
	    <div class="demo-upload-list" v-for="item in uploadList">
	        <template v-if="item.status === 'finished'">
	            <img :src="item.url">
	            <div class="demo-upload-list-cover">
	                <!--<Icon type="ios-eye-outline" @click.native="handleView(item.name)"></Icon>-->
	                <Icon type="ios-trash-outline" @click.native="handleRemove(item)"></Icon>
	            </div>
	        </template>
	        <template v-else>
	            <Progress v-if="item.showProgress" :percent="item.percentage" hide-info></Progress>
	        </template>
	    </div>
	    <Upload
	        ref="upload"
	        :show-upload-list="false"
	        :default-file-list="defaultList"
	        :on-success="handleSuccess"
	        :max-size="1024000"
	        :on-format-error="handleFormatError"
	        :on-exceeded-size="handleMaxSize"
	        :before-upload="handleBeforeUpload"
	        multiple
	        type="drag"
	        :action="uploadUrl+'?targetPath=hdFile'"
	        style="display: inline-block;width:58px;">
	        <div style="width: 58px;height:58px;line-height: 58px;">
	            <Icon type="camera" size="20"></Icon>
	        </div>
	    </Upload>
	    <!--<Modal title="View Image" v-model="visible">
	        <img :src="'https://o5wwk8baw.qnssl.com/' + imgName + '/large'" v-if="visible" style="width: 100%">
	    </Modal>-->
		
	</div>
</template>
<script>
	import configApi from '@/axios/config.js'
    export default {
        data () {
            return {
                defaultList: [
                ],
                imgName: '',
                visible: false,
                uploadList: [],
                uploadUrl:configApi.UPLOAD
            }
        },
        methods: {
            handleView (name) {
                this.imgName = name;
                this.visible = true;
            },
            handleRemove (file) {
                const fileList = this.$refs.upload.fileList;
                this.$refs.upload.fileList.splice(fileList.indexOf(file), 1);
            },
            handleSuccess (res, file,fileList) {
            	alert('上传完成')
                file.url = 'https://o5wwk8baw.qnssl.com/7eb99afb9d5f317c912f08b5212fd69a/avatar';
                file.name = '7eb99afb9d5f317c912f08b5212fd69a';
            },
            handleFormatError (file) {
                this.$Notice.warning({
                    title: '文件格式错误',
                    desc: 'File format of ' + file.name + ' is incorrect, please select jpg or png.'
                });
            },
            handleMaxSize (file) {
                this.$Notice.warning({
                    title: '文件太大了',
                    desc: 'File  ' + file.name + ' is too large, no more than 2M.'
                });
            },
            handleBeforeUpload () {
                const check = this.uploadList.length < 5;
                if (!check) {
                    this.$Notice.warning({
                        title: 'Up to five pictures can be uploaded.'
                    });
                }
                return check;
            }
        },
        mounted () {
        	console.log('fileList',this.$refs.upload.fileList)
            this.uploadList = this.$refs.upload.fileList;
        }
    }
</script>