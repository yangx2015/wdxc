<!--&lt;!&ndash;&ndash;&gt;-->
<!--<template>-->
  <!--<div>-->
    <!--&lt;!&ndash;<nav-bar slot="nav-bar" custom-style="{{ {'color':'#fff','background-color':'#FF7F50'} }}">&ndash;&gt;-->
      <!--&lt;!&ndash;<row height="46">&ndash;&gt;-->
        <!--&lt;!&ndash;<col vertical-align="middle" align="center" width="60" bindtap="Goback">&ndash;&gt;-->
          <!--&lt;!&ndash;<icon type="left" size="16" color="#fff"></icon>&ndash;&gt;-->
        <!--&lt;!&ndash;</col>&ndash;&gt;-->
        <!--&lt;!&ndash;<col vertical-align="middle" align="center">&ndash;&gt;-->
          <!--&lt;!&ndash;<div class="nav_title">意见反馈</div>&ndash;&gt;-->
        <!--&lt;!&ndash;</col>&ndash;&gt;-->
        <!--&lt;!&ndash;<col vertical-align="middle" align="center" width="60">&ndash;&gt;-->
        <!--&lt;!&ndash;</col>&ndash;&gt;-->
      <!--&lt;!&ndash;</row>&ndash;&gt;-->
    <!--&lt;!&ndash;</nav-bar>&ndash;&gt;-->

    <!--<div class="box_col">-->
      <!--<div class="box_col_auto">-->
        <!--<div>-->
          <!--<tabs width="200" style="margin:0 auto;"-->
                   <!--active-tab-style="{{activeTabStyle}}"-->
                   <!--index="{{ current }}"  @click="handleChange">-->
            <!--<tab>-->
              <!--<div>建议</div>-->
            <!--</tab>-->
            <!--<tab>-->
              <!--<div>投诉</div>-->
            <!--</tab>-->
            <!--<tab>-->
              <!--<div>反馈</div>-->
            <!--</tab>-->
          <!--</tabs>-->
        <!--</div>-->
        <!--&lt;!&ndash; 反馈信息 &ndash;&gt;-->
        <!--<div class="messbox">-->
          <!--<div class="sample_content">-->
            <!--<textarea placeholder="{{placeholderTit}}"-->
                         <!--placeholder-class="blue-placeholder" v-model=" textareaValue "></textarea>-->
          <!--</div>-->

          <!--<div class="submitBut">-->
            <!--<button type="primary" @click="upMess">提交</button>-->
          <!--</div>-->

        <!--</div>-->

        <!--&lt;!&ndash; 反馈信息列表 &ndash;&gt;-->
        <!--<div class="fkboxSty">-->
          <!--<sticky stick-on-tap  custom-style="{{ customStyle }}">-->
            <!--<div>-->
              <!--<div class="title">-->
                <!--<divider color="#ff9900">-->
                  <!--<div style="color:#ff9900">-->
                    <!--建议&#45;&#45;投诉&#45;&#45;反馈-->
                  <!--</div>-->
                <!--</divider>-->
              <!--</div>-->
            <!--</div>-->
            <!--<div slot="sticky">-->
              <!--<div class="title">-->
                <!--<divider color="#ff9900">-->
                  <!--<div style="color:#ff9900">-->
                    <!--评论-->
                  <!--</div>-->
                <!--</divider>-->
              <!--</div>-->
            <!--</div>-->
          <!--</sticky>-->
        <!--</div>-->

        <!--&lt;!&ndash; <divider color="#ff9900">-->
          <!--<div style="color:#ff9900">-->
            <!--*请注意您的学车进度！-->
          <!--</div>-->
        <!--</divider> &ndash;&gt;-->

        <!--<div>-->
        <!--</div>-->
        <!--<scroll-div-->
          <!--scroll-y-->
          <!--height="{{divHeight(0)}}"-->
          <!--pull-down="{{ pullDown }}"-->
          <!--enable-reach-bottom-load ="{{loadMore}}"-->
          <!--on-load-more="{{ handleLoadMore }}">-->
          <!--<div class="content_list">-->
            <!--<div v-for="{{ items }}">-->
              <!--<div class="box_row propMessSty">-->
                <!--<div class="messImg">-->
                  <!--<image src="static/img/logo.jpg" width="60" height="60"></image>-->
                <!--</div>-->
                <!--<div class="box_row_100 messmess">-->
                  <!--<div class="box_row">-->
                    <!--<div class="box_row_100">-->
                      <!--<div class="time">-->
                        <!--{{item.cjsj}}-->
                      <!--</div>-->
                    <!--</div>-->

                    <!--<div style="color:#ff7f50">-->
                      <!--{{item.yjlx | yjlx}}-->
                    <!--</div>-->
                  <!--</div>-->
                  <!--<div class="nrboxSty">-->
                    <!--<textarea value="{{ item.nr }}"-->
                                 <!--readonly auto-height rows="1"-->
                                 <!--show-counter="false"></textarea>-->
                  <!--</div>-->

                  <!--<div class="box_row cljg" v-if="{{item.cljg!=''}}">-->
                    <!--<div>-->
                      <!--结果回复：-->
                    <!--</div>-->
                    <!--<div class="box_row_100">-->
                      <!--<textarea value="{{ item.cljg }}"-->
                                   <!--readonly auto-height rows="1"-->
                                   <!--show-counter="false"></textarea>-->
                    <!--</div>-->
                  <!--</div>-->

                <!--</div>-->

              <!--</div>-->
            <!--</div>-->
          <!--</div>-->

          <!--<div style="width:200px;margin:0 auto;">-->
            <!--<divider v-show="{{!loadMore}}">没有新数据了</divider>-->
          <!--</div>-->
        <!--</scroll-div>-->
      <!--</div>-->
    <!--</div>-->

  <!--</div>-->
<!--</template>-->

<!--<script>-->
  <!--export default {-->
    <!--config: {-->
      <!--navigationStyle: 'custom',-->
      <!--navigationBarTextStyle:"white",-->
      <!--navigationBarTitleText:"新增意见反馈",-->
      <!--title:'新增意见反馈',-->
      <!--backgroundColor:'#fff',-->
      <!--delay:false,//延迟加载-->
    <!--},-->
    <!--filters: {-->
      <!--yjlx:(val)=>{-->
        <!--switch (val) {-->
          <!--case '00':-->
            <!--return '建议';-->
            <!--break;-->
          <!--case '10':-->
            <!--return '反馈';-->
            <!--break;-->
          <!--case '20':-->
            <!--return '投诉';-->
            <!--break;-->
          <!--default:-->
            <!--return '建议'-->
            <!--break;-->
        <!--}-->
      <!--}-->
    <!--},-->
    <!--data() {-->
      <!--return {-->
        <!--activeTabStyle:{-->
          <!--'color': '#FF7F50',-->
          <!--'border-bottom': '1px solid #FF7F50',-->
        <!--},-->
        <!--current:0,-->
        <!--textareaValue:'',-->
        <!--placeholderTitList:[-->
          <!--'请输入您的建议信息',-->
          <!--'请输入您的投诉内容',-->
          <!--'请输入您的反馈内容'-->
        <!--],-->
        <!--placeholderTit:'请输入您的建议信息',-->
        <!--customStyle:{-->
          <!--'background-color':'#fff'-->
        <!--},-->
        <!--items: [],-->
        <!--pullDown: {-->
          <!--distance: 50,-->
          <!--onBegin: () => {},-->
          <!--onActive: this.handleActive,-->
          <!--onAfter: this.handlePullDownAfter-->
        <!--},-->
        <!--count:0,-->
        <!--loadMore:true,-->
        <!--divNum:1,//当前页面-->
        <!--divs:0,//页面总数-->
      <!--}-->
    <!--},-->
    <!--methods: {-->
      <!--Goback(){-->
        <!--ui.navigateBack()-->
      <!--},-->
      <!--handleChange(val){-->
        <!--this.placeholderTit = this.placeholderTitList[val]-->
        <!--this.current = val-->
        <!--console.log(val);-->
      <!--},-->
      <!--upMess(){-->
        <!--var v  = this-->
        <!--var a = '00'-->
        <!--if(this.current == 0){-->
          <!--a = '00'-->
        <!--}else if(this.current == 1){-->
          <!--a = '20'-->
        <!--}else if(this.current == 2){-->
          <!--a = '10'-->
        <!--}-->
        <!--console.log(this.textareaValue+this.current);-->
        <!--if(this.textareaValue == ''){-->
          <!--ui.showToast({ title: '请填写文字信息' })-->
          <!--return-->
        <!--}-->

        <!--this.$http('POST',ui.getApp().apis.PROPOSAL,{yjlx:a,nr:this.textareaValue},(res)=>{-->
          <!--console.log(res)-->
          <!--if (res.code == 200) {-->
            <!--v.textareaValue = ''-->

            <!--v.handlePullDownAfter()-->
            <!--ui.showToast({ title: '信息提交成功', icon: 'success', duration: 3000 })-->
          <!--}else{-->
            <!--ui.showToast({ title: '信息提交失败，请重新提交' })-->
          <!--}-->
        <!--})-->

      <!--},-->
      <!--getpropList(num){-->
        <!--var v = this-->
        <!--this.$http('POST',ui.getApp().apis.PROPList,{yjLx:'',divSize:10,divNum:num},(res)=>{-->
          <!--if(res.code==200){-->
            <!--v.divs = res.div.divs-->
            <!--if(res.div.list){-->
              <!--res.div.list.forEach((item,index) => {-->
                <!--v.items.push(item)-->
              <!--});-->
            <!--}-->
          <!--}-->
        <!--})-->
      <!--},-->
      <!--handleLoadMore () {-->
        <!--if(this.divNum<this.divs){-->
          <!--this.divNum ++-->
          <!--this.getpropList(this.divNum)-->
        <!--}else{-->
          <!--window.setTimeout(() => {-->
            <!--this.loadMore = false-->
            <!--return-->
          <!--}, 1500)-->
        <!--}-->
      <!--},-->
      <!--handleActive () {//下拉刷新时的方法-->
        <!--// 将返回的请求接管，实现让请求结果来控制下拉刷新的状态-->
        <!--// return ui.request({-->
        <!--//   // promise设置为true，让请求返回promise，这样就可以接管请求-->
        <!--//   // 请求结束后页面自动回弹-->
        <!--//   promise: true,-->
        <!--//   url: '/touchbackstage/listInfo.do?state=1',-->
        <!--//   success: (response) => {-->
        <!--//   },-->
        <!--//   fail: (err) => {-->
        <!--//   }-->
        <!--// })-->
      <!--},-->
      <!--// 下拉结束后，执行的函数。因为只要下拉即时没有到达刷新的高度也会触发这个方法-->
      <!--// flag 为1表示执行了handleActive。为undefined代表handleActive没有执行。-->
      <!--handlePullDownAfter (flag) {-->
        <!--this.divNum =1-->
        <!--this.items = []-->
        <!--this.getpropList(this.divNum)-->
        <!--if(this.divNum<this.divs){-->
          <!--this.loadMore = true-->
        <!--}-->
      <!--}-->
    <!--},-->
    <!--mounted() {-->
      <!--this.getpropList(1)-->
    <!--}-->
  <!--}-->
<!--</script>-->

<!--<style lang="less">-->
  <!--.transfer-dom{-->
    <!--background-color: #eee!important;-->
  <!--}-->
  <!--.fkboxSty{-->
    <!--.title {-->
      <!--padding:0 30px;-->
      <!--font-weight: 700;-->
    <!--}-->
  <!--}-->
  <!--.propMessSty{-->
    <!--padding: 15px;-->
    <!--border: #f2f2f2 3px solid;-->
    <!--.messImg{-->
      <!--padding:15px;-->
    <!--}-->
    <!--.messmess{-->
      <!--padding: 10px 20px;-->
      <!--.time{-->
        <!--padding: 10px 0;-->
        <!--font-size: 26px;-->
      <!--}-->
      <!--.nrboxSty{-->
        <!--.textarea-wrapper{-->
          <!--background-color: #fff;-->
          <!--// border: none;-->
          <!--padding-left:0;-->
          <!--textarea{-->
            <!--background-color: #fff;-->
            <!--color: #000;-->
          <!--}-->
        <!--}-->
      <!--}-->
      <!--.cljg{-->
        <!--padding: 15px 0;-->
        <!--.textarea-wrapper{-->
          <!--background-color: #f2f2f2;-->
          <!--border: none;-->
          <!--textarea{-->
            <!--background-color: #f2f2f2;-->
          <!--}-->
        <!--}-->
      <!--}-->
    <!--}-->
  <!--}-->
  <!--.messbox{-->
    <!--padding: 15px 25px;-->
    <!--.blue-placeholder::-webkit-input-placeholder {-->
      <!--color:#FF7F50;-->
    <!--}-->
    <!--.submitBut{-->
      <!--padding: 10px 0;-->
    <!--}-->
  <!--}-->
  <!--.sticky-in-relative-container {-->
    <!--position: relative;-->
    <!--height: 46px;-->
  <!--}-->
<!--</style>-->
