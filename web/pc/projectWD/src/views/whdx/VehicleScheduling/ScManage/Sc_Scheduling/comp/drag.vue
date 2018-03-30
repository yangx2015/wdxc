<style lang="less">
@import './draggable-list.less';
li{
	list-style: none;
}
</style>
<template>
	<div class="box">
		<div class="tit">
			<Row class="margin-top-10">
				<Col span="24" style="text-align:center;">
				<h1>
					{{todaytime}}
					排班</h1>
				<Button style="float: right;margin-left:8px" type="success" @click="okmodel()">完成</Button>
				</Col>
			</Row>
		</div>
		<div class="body">
			<div class="box-row">
				<div style="width: 260px;margin-right:5px;height: 100%;font-size: 16px;border-right: solid 1px #008855;">
					<Row style="text-align: center;border-bottom: solid 3px #9B9B9B;">
						<Col span="8">姓名</Col>
						<Col span="8">车辆</Col>
						<Col span="8">状态</Col>
					</Row>
					<Row style="text-align: center;font-size: 14px;">
						<ul id="todoList" class="iview-admin-draggable-list">
                            <li v-for="(item,index) in [1,2,2,2,2,]" :key="index" :data-index="index">
		                        <Row style="border: solid 1px #9B9B9B;padding: 3px 0;">
									<Col span="8">周师傅</Col>
									<Col span="8">鄂A12345</Col>
									<Col span="8">静态</Col>
								</Row>
                            </li>
                        </ul>
					</Row>
				</div>
				<div class="body-F" style="background-color: #00A854;padding: 5px;">
					<Row :gutter='8'>
						<Col span="8" class="margin-bottom-10" v-for="(item,index) in listdata">
							<Card style="width:100%">
								<p slot="title">
									<Icon type="ios-film-outline"></Icon>
									光谷大道一号线
								</p>
								<span slot="extra">
										            <Icon type="ios-loop-strong"></Icon>
										            早班
										        </span>
								<div style="height: 140px;text-align: center;overflow: auto;" :id="index+'card'">
									<Row style="border: solid 1px #9B9B9B;padding: 3px 0;">
										<Col span="8">周师傅</Col>
										<Col span="8">鄂A12345</Col>
										<Col span="8">静态</Col>
									</Row>
								</div>
							</Card>
						</Col>
					</Row>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import Sortable from 'sortablejs';
	export default {
		name:'',
		data(){
			return{
				listdata:[1,2,2,2,2,2,2,2,2,2,,2,2,2,2,,,,,,]
			}
		},
		props: {
			todaytime: {
				type: String,
				default: '2018-08-08'
			}
		},
		mounted (){
			let vm = this;
	        let todoList = document.getElementById('todoList');
	        Sortable.create(todoList, {
	            group: {
	                name: 'list',
	                pull: true
	            },
	            animation: 120,
//	            ghostClass: 'placeholder-style',
//	            fallbackClass: 'iview-admin-cloned-item',
	            onRemove (event) {
//	            	event.to.innerHTML="<div style='background-color:#000;width:50px;height:50px'></div>"
	            	console.log(event.item.getAttribute('data-index'))
	            	console.log(event)
	            	console.log(event.to.id)
	//              vm.doArray.splice(event.newIndex, 0, vm.todoArray[event.item.getAttribute('data-index')]);
	//          	vm.doArray.splice(event.newIndex, 0, vm.todoArray[]
	            }
	        });
	        setTimeout(function(){
	        	vm.fordata()
	        },200)
		},
		methods:{
			okmodel(){
				this.$emit('okdrag')
			},
			fordata(){
				let vm = this;
				for(var i=0;i<vm.listdata.length;i++){
//					let todoList+index = document.getElementById(index+'card');
					Sortable.create((document.getElementById(i+'card')), {
			            group: {
			                name: 'list',
			                pull: true
			            },
			            animation: 120,
//			            ghostClass: 'placeholder-style',
//			            fallbackClass: 'iview-admin-cloned-item',
			            onRemove (event) {
			            	console.log(event.item.getAttribute('data-index'))
			            	console.log(event)
			//              vm.doArray.splice(event.newIndex, 0, vm.todoArray[event.item.getAttribute('data-index')]);
			//          	vm.doArray.splice(event.newIndex, 0, vm.todoArray[]
			           }
			        });
				}
			}
		}
	}
</script>

<style>

</style>