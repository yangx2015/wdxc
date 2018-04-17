<style lang="less">
	.expand-row {
		margin-bottom: 16px;
		padding-left: 40px;
	}
	.demo-carousel{
		width: 100%!important;
		height: 200px;
	}
</style>
<template>
	<div>
		<div>
			附件：
		</div>
		<div class="demo-upload-list" v-for="(item,index) in uploadList" v-if="item.url != null && item.url.length != 0">
			<div v-if="item.url.substring(item.url.lastIndexOf('.')+1) == 'mp4'">
				<video src="static/movie.ogg" height="100%"></video>
				<div class="demo-upload-list-cover">
					<Icon type="arrow-right-b" @click.native="handleView(item.name)"></Icon>
				</div>
				<Modal title="View Image" v-model="visible">
					<video class="active-video" src="static/movie.ogg" controls="controls"></video>
				</Modal>
			</div>
			<div v-else>
				<img :src="staticPath+item.url">
				<div class="demo-upload-list-cover">
					<Icon type="ios-eye-outline" @click.native="handleView(item.name,index)"></Icon>
				</div>
				<Modal class="videoStyle" title="View Image" v-model="visible">
					<img :src="staticPath+item.url" v-if="visible" style="width: 100%">
				</Modal>
			</div>
		</div>
	</div>
</template>
<script>
    import configApi from '@/axios/config.js'
	export default {
		name: '',
		data() {
			return {
				Carousel:2,
				imgName: '',
				visible: false,
				staticPath:configApi.STATIC_PATH,
				uploadList: [],
				paths:[]
			}
		},
		props: {
			row: {
				type: Object,
				default: {}
			}
		},
		created(){
            if (this.row.filePaths){
                let paths = [];
                this.uploadList = [];
                paths = this.row.filePaths.split(',');
                for(let r of paths){
                    let name = r.substring(r.lastIndexOf("/"));
                    this.uploadList.push({name:name,url:r});
				}
			}
        },
		methods: {
			handleView(name,index) {
				if(index){
					this.CarouselV = index;
				}
				this.imgName = name;
				this.visible = true;
			}
		}
	};
</script>
<style>
	.demo-upload-list {
		display: inline-block;
		width: 60px;
		height: 60px;
		text-align: center;
		line-height: 60px;
		border: 1px solid transparent;
		border-radius: 4px;
		overflow: hidden;
		background: #fff;
		position: relative;
		box-shadow: 0 1px 1px rgba(0, 0, 0, .2);
		margin-right: 4px;
	}
	
	.demo-upload-list img {
		width: 100%;
		height: 100%;
	}
	
	.demo-upload-list-cover {
		display: none;
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		background: rgba(0, 0, 0, .6);
	}
	
	.demo-upload-list:hover .demo-upload-list-cover {
		display: block;
	}
	
	.demo-upload-list-cover i {
		color: #fff;
		font-size: 28px;
		cursor: pointer;
	    text-align: center;
    	line-height: 60px
	}
	.active-video{
		width: 100%;
	}
</style>