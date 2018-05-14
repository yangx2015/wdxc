<style lang="less">
  @import '../../styles/box.less';
  .feedbackHis{
    .hisList{
      background: #00a050;
    }
  }
</style>
<template>
  <div class="box feedbackHis">
      <head-tit tit="反馈记录">
      </head-tit>
    <div class="body" style="background: #2b85e4;margin-top: 8px;padding: 8px">
        <div class="hisList">
            <div>
                <i class="iconfont icon-shijian"></i>
                2017-08-09 11:10:00
            </div>
            <div>
              <div>反馈内容：</div>
              <div style="max-height: 65px;">
                ashdfkasjf alkdjf;akjfd;lakjfaj;fj;ldkjfn
              </div>
            </div>
            <div>
              <div>处理结果：</div>
              <div style="max-height: 65px;">
                ashdfkasjf alkdjf;akjfd;lakjfaj;fj;ldkjfn
              </div>
            </div>
        </div>
    </div>
  </div>

</template>

<script>
    import headTit from "@/views/comp/headTit"
    export default {
        name: "index",
        components: {
          headTit,
        },
    }
</script>

<style scoped>

</style>
