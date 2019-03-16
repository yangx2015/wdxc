// Vue.prototype.AF = comFun;
import swal from 'sweetalert2'
import iView from 'iview'
import $http from '../axios/index'
import store from '../store'

export default {
  setItem(key, val) {
    let value = JSON.stringify(val)
    sessionStorage.setItem(key, value)
  },
  getItem(key) {
    let sessData = sessionStorage.getItem(key)
    return JSON.parse(sessData)
  },
  getRandom(val) {//取随机数
    let line = 1
    if (val && val > 1) {
      line = val
    }
    let num = ''
    for (var i = 0; i < line; i++) {
      num += Math.floor(Math.random() * 10)
    }
    return num
  },
  DX: (n) => {//金额大写转换
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
      return "数据非法";
    var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
    n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
      n = n.substring(0, p) + n.substr(p + 1, 2);
    unit = unit.substr(unit.length - n.length);
    for (var i = 0; i < n.length; i++)
      str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
  },
  getPageHeight: () => {//获取浏览器页面高度
    var windowHeight = window.innerHeight
    return windowHeight
  },
  getTime: (val) => {
    if (val) {
      var newDate = new Date(val);
    } else {
      var newDate = new Date();
    }
    var newDate = new Date(val);
    let Year = newDate.getFullYear();
    let Month = newDate.getMonth() + 1;
    let Day = newDate.getDate();
    let Hours = newDate.getHours();
    let Minutes = newDate.getMinutes();
    let Seconds = newDate.getSeconds();

    function minNum(num) {
      if (num < 10) {
        return '0' + num
      } else {
        return num
      }
    }
    let time = Year + '-' + minNum(Month) + '-' + minNum(Day) + ' ' + minNum(Hours) + ':' + minNum(Minutes) + ':' + minNum(Seconds)
    return time
  },
  trimDate: (val) => {
    if (val) {
      var newDate = new Date(val);
    } else {
      var newDate = new Date();
    }
    let Year = newDate.getFullYear();
    let Month = newDate.getMonth() + 1;
    let Day = newDate.getDate();
    if (Month < 10) {
      Month = '0' + Month
    }

    if (Day < 10) {
      Day = '0' + Day
    }
    let time = Year + '-' + Month + '-' + Day
    return time
  },
//  Date 转换 时间戳
  Num_Date(time) {
    return Date.parse(new Date(time))
  },
  getBrowserTyp() {
    var Typ = navigator.userAgent; //取得浏览器的userAgent字符串
    return Typ
    /*
    //判断是否Opera浏览器
    if (userAgent.indexOf("Opera") > -1) {
        return "Opera"
    };
    //判断是否Firefox浏览器
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    }
    //判断是否chorme浏览器
    if (userAgent.indexOf("Chrome") > -1){
		return "Chrome";
    }
    //判断是否Safari浏览器
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    }
    //判断是否IE11浏览器 !!
    if (userAgent.indexOf("Trident") > -1) {
        return "IE";
    }
    //判断是否IE10浏览器
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && userAgent.indexOf("Opera") == -1) {
        return "IE";
    }
    //判断是否Edge浏览器
    if (userAgent.indexOf("Trident") > -1) {
        return "Edge";
    };

    *
    * */
  },
  getYear() {
    let date = new Date();
    let year = date.getFullYear();
    return year
  }
}
/*
this.$nextTick(() => {
        $(document).keypress(function (event) {//keypress 单件
          console.log(event.keyCode);
          // $(document).keyup(function(event){  keyup 组合件
          if (event.keyCode === 44) {
            alert('你按下了<,');
          }else if (event.keyCode === 45) {
            alert('你按下了>.');
          }else if (event.keyCode === 47) {
            alert('你按下了?/');
          }else if (event.keyCode === 112) {
            alert('你按下了__p');
          }
        });
      })
* */
