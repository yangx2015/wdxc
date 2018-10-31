<style type="text/css">
	.pagerListTit{
		padding: 4px;
		font-size: 16px;
		font-weight: 600;
	}
	.pagerListMes{
		min-height: 40px;
		padding: 6px 8px;
		font-size: 15px;
		font-weight: 500;
	}
	.ColBorderlt{
		border: solid 1px #000;
		border-right: none;
		border-bottom: none;
	}
	.ColBorderCt{
		border: solid 1px #000;
		border-right: none;
		border-bottom: none;
	}
	.ColBorderRt{
		border: solid 1px #000;
		border-bottom: none;
	}
	.bf{
		border-left: solid 1px #000;
	}
	.bb{
		border-bottom: solid 1px #000;
	}
	.textHL{
		height: 70px;
		word-break:break-all;
	}
</style>
<template>
	<div>
		<Modal
	      v-model="UpModal"
	      :mask-closable="false"
	      :closable="false"
	      width="800">
	      <div slot="header" v-if="showFooter" style="color: red;">
				只能使用Chrome(谷歌)浏览器进行打印
		  </div>
	      <div>
	      	<Row>
	      		<Col span="24">
	      			<div style="text-align: center;">
	      				<h1>
	      					武汉大学用车单
	      				</h1>
	      			</div>
	      		</Col>
	      	</Row>
	      	<div style="padding: 4px 16px;">
		      	<Row>
		      		<Col class-name='ColBorderlt' span="8">
		      			<div class="pagerListTit">用车单位</div>
		      		</Col>
		      		<Col class-name='ColBorderCt' span="8">
		      			<div class="pagerListTit">用车人</div>
		      		</Col>
		      		<Col class-name='ColBorderRt' span="8">
		      			<div class="pagerListTit">客户电话</div>
		      		</Col>
		      	</Row>
		      	<Row>
		      		<Col class-name='ColBorderlt' span="8">
		      			<div class="pagerListMes">{{row.jgmc}}</div>
		      		</Col>
		      		<Col class-name='ColBorderCt' span="8">
		      			<div class="pagerListMes">{{row.ck}}</div>
		      		</Col>
		      		<Col class-name='ColBorderRt' span="8">
		      			<div class="pagerListMes">{{row.cklxdh}}</div>
		      		</Col>
		      	</Row>
		      	
		      	<Row>
		      		<Col class-name='ColBorderlt' span="12">
		      			<div class="pagerListTit">候车地点</div>
		      		</Col>
		      		<Col class-name='ColBorderRt bf' span="12">
		      			<div class="pagerListTit">目的地</div>
		      		</Col>
		      	</Row>
		      	<Row>
		      		<Col class-name='ColBorderlt textHL' span="12">
		      			<div class="pagerListMes">{{row.hcdz}}</div>
		      		</Col>
		      		<Col class-name='ColBorderRt bf textHL' span="12">
		      			<div class="pagerListMes">{{row.mdd}}</div>
		      		</Col>
		      	</Row>
	      	
	      		<Row>
		      		<Col class-name='ColBorderlt' span="6">
		      			<div class="pagerListTit">出车时间</div>
		      		</Col>
		      		<Col class-name='ColBorderCt' span="6">
		      			<div class="pagerListTit">单据类型</div>
		      		</Col>
		      		<Col class-name='ColBorderCt' span="6">
		      			<div class="pagerListTit">车型</div>
		      		</Col>
		      		<Col class-name='ColBorderRt' span="6">
		      			<div class="pagerListTit">行程费用</div>
		      		</Col>
		      	</Row>
		      	<Row>
		      		<Col class-name='ColBorderlt' span="6">
		      			<div class="pagerListMes">{{row.cjsj}}</div>
		      		</Col>
		      		<Col class-name='ColBorderCt' span="6">
		      			<div class="pagerListMes">{{wf(this.row.wf)}}</div>
		      		</Col>
		      		<Col class-name='ColBorderCt' span="6">
		      			<div class="pagerListMes">{{ cx(this.row.cllx,this.row.zws) }}</div>
		      		</Col>
		      		<Col class-name='ColBorderRt' span="6">
		      			<div class="pagerListMes">{{row.zj}}</div>
		      		</Col>
		      	</Row>
		      	
		      	<Row>
		      		<Col class-name='ColBorderlt' span="6">
		      			<div class="pagerListTit">里程(公里)</div>
		      		</Col>
		      		<Col class-name='ColBorderCt' span="6">
		      			<div class="pagerListTit">里程单价</div>
		      		</Col>
		      		<Col class-name='ColBorderCt' span="6">
		      			<div class="pagerListTit">过路费</div>
		      		</Col>
		      		<Col class-name='ColBorderRt' span="6">
		      			<div class="pagerListTit">路停费</div>
		      		</Col>
		      	</Row>
		      	<Row>
		      		<Col class-name='ColBorderlt bb' span="6">
		      			<div class="pagerListMes">{{row.lc}}</div>
		      		</Col>
		      		<Col class-name='ColBorderCt bb' span="6">
		      			<div class="pagerListMes">{{row.dj}}</div>
		      		</Col>
		      		<Col class-name='ColBorderCt bb' span="6">
		      			<div class="pagerListMes">{{row.glf}}</div>
		      		</Col>
		      		<Col class-name='ColBorderRt bb' span="6">
		      			<div class="pagerListMes">{{row.gqf}}</div>
		      		</Col>
		      	</Row>
	      	</div>
	      </div>
	      <div slot='footer'>
				<Button type="default" v-if="showFooter" @click="close" style="color: #949494">取消</Button>
				<Button type="primary" v-if="showFooter" @click="print">确定</Button>
			</div>
	    </Modal>
	</div>
</template>

<script>
	export default {
		name: "Basics",
		data() {
	      return {
	        UpModal: true,
	        showFooter:true,
	      }
	    },
	    filters:{
	    },
		props: {
	      row: {
	        type: Object,
	        default: {}
	      }
	    },
	    created(){},
	    methods:{
	    	wf(val){
	    		let cx = this.dictUtil.getValByCode(this, 'ZDCLK0042', val)
	    		return cx
	    	},
	    	cx(cllx,zws){
	    		if(cllx&&zws){
		    		let cx = this.dictUtil.getValByCode(this, 'ZDCLK0019', cllx)
	              	return cx+'/'+zws+'座'
	    		}
	    		return ''
	    	},
	    	close(){
		       this.$parent.compName = ''
		    },
		    print(){
                this.showFooter = false;
                setTimeout(function(){
                    window.print();
                },10)
                let v = this;
                setTimeout(function(){
                    v.close();
                },150)
			}
	    }
	}
</script>

<style>
</style>