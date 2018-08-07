export default {
  filters: {
    yhLx: (val) => {//用戶类型
      switch (val) {
        case '1':
          return '学员';
          break;
        case '2':
          return '专员';
          break;
        case '3':
          return '会员';
          break;
        default:
          return '用户类型'
          break;
      }
    },

    yhXm: (val) => {//用户姓名
      if (val) {
        return val
      }
      return '未实名'
    },

    yhTx: (val) => {
      if (val) {
        return val
      }
      return 'static/img/login/logo.png'
    },
    yhSfyjz(val){
      switch (val) {
        case '0':
          return '无驾驶证';
          break;
        case '1':
          return '有驾驶证';
          break;
        default:
          return "无驾驶证";
          break;
      }
    },
    userGrade: (val) => {
      switch (val) {
        case '1':
          return '一级用户'
          break;
        case '2':
          return '二级用户'
      }
    },

    yhZhye(val) {//用户账户余额
      if (val == '') {
        return 0
      }
      return val
    },

    ddSfjx: (val) => {
      switch (val) {
        case '0':
          return '未交费'
          break;
        case '1':
          return '已交费'
      }
    },

    yhZt: function (val) {//用户状态
      switch (val) {
        case '0':
          return '审核中';
          break;
        case '1':
          return '已认证';
          break;
        case '2':
          return '审核驳回';
          break;
        case '-1':
          return '未认证';
          break;
        default:
          return val
          break;
      }
    },
    yhslZt(val) {
      switch (val) {
        case "00":
          return '档案未受理'
          break;
        case "01":
          return '档案受理成功'
          break;
        case "02":
          return '档案受理中'
          break;
        case "10":
          return '科目一已约考'
          break;
        case "11":
          return '科目一已通过'
          break;
        case  "12":
          return '科目一未通过'
          break;
        case  "20":
          return '科目二已约考'
          break;
        case  "21":
          return '科目二已通过'
          break;
        case  "22":
          return '科目二未通过'
          break;
        case  "30":
          return '科目三已约考'
          break;
        case  "31":
          return '科目三已通过'
          break;
        case  "32":
          return '科目三未通过'
          break;
        case  "40":
          return '科目四已约考'
          break;
        case  "41":
          return '科目四已通过'
          break;
        case  "42":
          return '科目四未通过'
          break;
        default:
          return '完结'
      }
    },

    userInviteCount(val) {
      if (val) {
        return val
      }
      return 0
    },

    jlQu:(val)=>{
      switch (val) {
        case "430014":
          return '江岸区'
          break;
        case "4300001":
          return '江汉区'
          break;
        case "4300002":
          return '硚口区'
          break;
        case "430050":
          return '汉阳区'
          break;
        case "4300003":
          return '武昌区'
          break;
        case "430080":
          return '青山区'
          break;
        case "430070":
          return '洪山区'
          break;
        case "430040":
          return '东西湖区'
          break;
        case "430090":
          return '汉南区'
          break;
        case "430100":
          return '蔡甸区'
          break;
        case "430200":
          return '黄陂区'
          break;
        case "431400":
          return '新洲区'
          break;
        case "43":
          return '武汉市'
          break;
        default:
          return '***'
          break;
      }
    },
    getType:(val)=>{
      switch (val){
        case 0:
          return '受理专员'
        break;
        case 1:
          return '科目一专员'
          break;
        case 2:
          return '科目二专员'
        break;
        case 3:
          return '科目三专员'
        break;
        case 4:
          return '科目四专员'
        break;
      }
    },
    yhXmZY:(val)=>{
      if (val){
        return val
      }
      return '暂未分配专员'
    },
    jlPf:(val)=>{
      if (val){
        return parseInt(val)
      }
      return 0
    },
    zjZt:(val)=>{
      switch (val){
        case '0':
          return '兑换冻结'
          break;
        case '1':
          return '处理成功'
          break;
        case '2':
          return '兑换失败'
          break;
      }
    },
    txShZt:(val)=>{
      switch (val){
        case '0':
          return '待审核'
          break;
        case '1':
          return '待打款'
          break;
        case '2':
          return '审核失败'
          break;
      }
    },
    mxlx:(val)=>{
      switch (val){
        case '1':
          return '充值'
        break;
        case '2':
          return '分佣'
        break;
        case '3':
            return '消费'
        break;
        case '4':
          return '兑换'
        break;
      }
    },
    zjFs:(val)=>{
      switch (val){
        case '1':
          return ''
          break;
        case '-1':
          return '-'
          break;
      }
    }

  },
  created() {
  },
  mounted() {
  }

}
