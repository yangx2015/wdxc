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
		<div v-if="row.active==='img'">
			<div>
				附件：图片
			</div>
			<div class="demo-upload-list" v-for="(item,index) in uploadList">
				<img :src="item.url">
				<div class="demo-upload-list-cover">
					<Icon type="ios-eye-outline" @click.native="handleView(item.name,index)"></Icon>
				</div>
			</div>
			<Modal class="videoStyle" title="View Image" v-model="visible">
							<img :src="'https://o5wwk8baw.qnssl.com/' + imgName + '/large'" v-if="visible" style="width: 100%">
				
    			<!--<Carousel v-model="Carousel" loop style="height: 200px;">
			        <CarouselItem class="demo-item">
            			<div class="demo-carousel">
							<img :src="'https://o5wwk8baw.qnssl.com/' + imgName + '/large'" v-if="visible" style="width: 100%">
            			</div>
			        </CarouselItem>
			        <CarouselItem>
			            <div class="demo-carousel">2</div>
			        </CarouselItem>
			        <CarouselItem>
			            <div class="demo-carousel">3</div>
			        </CarouselItem>
			        <CarouselItem>
			            <div class="demo-carousel">4</div>
			        </CarouselItem>
			    </Carousel>-->
			</Modal>
		</div>
		<div v-else-if="row.active==='video'">
			<div>
				附件：视频
			</div>
			<div class="demo-upload-list" v-for="item in uploadList">
				<video src="static/movie.ogg" height="100%"></video>
				<!--<img :src="item.url">-->
				<div class="demo-upload-list-cover">
					<Icon type="arrow-right-b" @click.native="handleView(item.name)"></Icon>
				</div>
			</div>
			<Modal title="View Image" v-model="visible">
				<video class="active-video" src="static/movie.ogg" controls="controls"></video>
			</Modal>
		</div>
	</div>
</template>
<script>
	export default {
		name: '',
		data() {
			return {
				Carousel:2,
				imgName: '',
				visible: false,
				uploadList: [{
						'name': 'a42bdcc1178e62b4694c830f028db5c0',
						'url': 'https://o5wwk8baw.qnssl.com/a42bdcc1178e62b4694c830f028db5c0/avatar'
					},
					{
						'name': 'bc7521e033abdd1e92222d733590f104',
						'url': 'https://o5wwk8baw.qnssl.com/bc7521e033abdd1e92222d733590f104/avatar'
					}
				]
			}
		},
		props: {
			row: {
				type: Object,
				default: {}
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