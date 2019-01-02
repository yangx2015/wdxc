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
	.blank{
		display: inline-block;
		width: 50px;
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
			  <Row>
				  <Col span="24">
					  <div style="text-align: center;">
						  <span class="blank"></span><h3>年</h3><span class="blank"></span><h3>月</h3><span class="blank"></span><h3>日</h3>
					  </div>
				  </Col>
			  </Row>
			  <table border="1" cellpadding="0" cellspacing="0" style="width: 100%">
				  <tbody>
				  	<tr>
						<td rowspan="2" width="20%">用车单位</td>
						<td rowspan="2" width="20%"></td>
						<td colspan="3" width="30%">出车时间</td>
						<td colspan="3" width="30%">目的地</td>
					</tr>
				  <tr>
					<td>上午</td>
					<td>时 分</td>
				  </tr>
				  <tr>
					<td>用车人</td>
					<td></td>
					<td>下午</td>
					<td>时分</td>
					<td></td>
				  </tr>
				  <tr>
					  <td>候车地点</td>
					  <td></td>
					  <td>用户签字</td>
					  <td></td>
					  <td rowspan="2">事由</td>
					  <td></td>
				  </tr>
				  <tr>
					  <td>车型</td>
					  <td></td>
					  <td>司机</td>
					  <td></td>
				  </tr>
				  <tr>
					  <td>公里数</td>
					  <td></td>
					  <td>单价</td>
					  <td></td>
					  <td>金额</td>
					  <td></td>
				  </tr>
				  <tr>
					  <td>等时</td>
					  <td></td>
					  <td>路停费</td>
					  <td></td>
					  <td>过桥费</td>
					  <td></td>
				  </tr>
				  <tr>
					  <td colspan="2">用户电话</td>
					  <td colspan="2"></td>
					  <td colspan="2">车费合计</td>
					  <td colspan="2"></td>
				  </tr>
				  </tbody>
			  </table>
			  <table border="1" cellspacing="0" cellpadding="0" style="width: 100%">
				<thead>
				<tr>安全</tr>
				</thead>
			  </table>
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