<style lang="less">
  .ycBox {
    position: absolute;
    left: 20px;
    top: 20px;
    width: 400px;
    background-color: #fff;
    z-index: 100;
  }

  .orderMessBox {
    position: absolute;
    right: 40px;
    top: 40px;
    width: 300px;
    background-color: #fff;
    z-index: 100;
  }

  .FK {
    position: absolute;
    right: 40px;
    bottom: 40px;
    border-radius: 10px;
    z-index: 100;
    width: 100px;
    height: 100px;
    /*background-color: #fff;*/
    background-color: #ed4014;
    color: #fff;
    box-shadow: -2px 4px 5px #888888;
    text-align: center;
    padding-top: 10px;
    font-size: 16px;
    font-weight: 600;
  }

  .demo-spin-icon-load{
    animation: ani-demo-spin 1s linear infinite;
  }
  @keyframes ani-demo-spin {
    from { transform: rotate(0deg);}
    50%  { transform: rotate(180deg);}
    to   { transform: rotate(360deg);}
  }
  .demo-spin-col{
  }
</style>
<template>
  <div style="position: relative;height: 100%">
    <div v-if="SpinShow" class="demo-spin-col" style="position:absolute;left: 0;top: 0;right: 0;bottom: 0;z-index: 500">
      <Spin fix>
        <Icon type="ios-loading" size=40 class="demo-spin-icon-load"></Icon>
        <div><h2>地图加载中</h2></div>
      </Spin>
    </div>
    <div class="ycBox">
      <div style="padding: 12px 16px 12px 56px;font-size: 18px;font-weight: 600;position: relative">
        <Icon type="ios-contact" size="38" color="#f90" style="position: absolute;top: 3px;left: 8px"/>
        <span style="padding-top: 6px ">
          {{userMess.xm}} {{userMess.sjhm | sjhm}}
        </span>
      </div>
      <Collapse value="1" accordion @on-change="CollClick">
        <Panel name="1" style="font-size: 20px;font-weight: 600">
          我要约车
          <div slot="content" :style="{maxHeight:AF.getPageHeight()-38*2-32-60-20+'px',overflow:'auto'}">
            <yc-form ref="ycFormBox" :ycMess="ycMess"
                     @clearAddres="(typ)=>{deleCode(typ)}"
                     @ycmessClear=ycmessClear
                     @myCar="myCar"
                     @chPeople="compName='hisPeople'"></yc-form>
          </div>
        </Panel>
        <Panel name="2" style="font-size: 20px;font-weight: 600">
          我的行程
          <div slot="content" :style="{maxHeight:AF.getPageHeight()-38*2-32-60-20+'px'}">
            <his-list @getItem="getListItem"></his-list>
          </div>
        </Panel>
      </Collapse>
    </div>
    <div class="orderMessBox">
      <order-mess v-if="orderItemMess!==null"
                  :mess="orderItemMess"
                  @messClose="orderItemMess=null"
      ></order-mess>
    </div>
    <div class="FK" style="cursor: pointer;" @click="compFkName='fkMess'">
      <Icon type="ios-paper-outline" size="60"/>
      <div style="font-weight: 600">
        意见反馈
      </div>
    </div>
    <!--地图-->
    <div id="BmapBox" style="height: 100%;width: 100%"></div>
    <component :is="compName" @okPeo="okPeo"></component>
    <component :is="compFkName"></component>
  </div>
</template>

<script>
  import ycForm from './comp/ycForm'
  import orderMess from './comp/orderMess'

  import hisList from './comp/hisList'
  import hisPeople from './comp/hisPeople'

  import fkMess from './comp/fkMess'

  export default {
    name: 'home',
    components: {ycForm, hisList, hisPeople, orderMess, fkMess},
    data() {
      return {
        SpinShow:false,
        userMess: {
          cjr: "",
          cjsj: "",
          id: "",
          jdmc: "",
          jgdm: "",
          pwd: "",
          sjhm: "",
          xb: "",
          xgr: "",
          xgsj: "",
          xm: "",
          zglx: "",
          zjhm: "",
          zw: "",
          sy: ''//备注事由
        },

        orderItemMess: null,

        Bmap: '',
        mapCode: {
          lng: 116.404,
          lat: 39.915
        },
        MarkerCode: ['', ''],
        ycMess: {
          hcdz: '',
          originLat: '',
          originLng: '',
          mdd: '',
          destinationLat: '',
          destinationLng: '',
          ck: '',//乘客
          cklxdh: '',//电话
          yysj: '',//约车时间
          cllx: '',//10小车，20大车
          zws: '',
          sy: ''//备注事由
        },
        compName: '',
        compFkName: ''
      }
    },
    filters: {
      sjhm: (val) => {
        let a = '****'
        let b = val.substring(0, 3)
        let c = val.substring(7, 11)

        return b + a + c
      }
    },
    created() {
      this.login()

      // let a= [
      //   {
      //     name:'王小二',
      //     phone:'13112345678'
      //   },
      //   {
      //     name:'王小六',
      //     phone:'13112345678'
      //   },
      //   {
      //     name:'花山秋道',
      //     phone:'13112345678'
      //   }
      // ]
      // localStorage.setItem('pelList',JSON.stringify(a))
    },
    mounted() {
      this.$nextTick(() => {
        this.getGps()
        // this.BuildMap()
      })
    },
    methods: {
      CollClick(key) {
        this.orderItemMess = null
        this.clear()
      },
      login() {
        this.$http.post('/put/jzg/jzgLogin', {key: '123456'}).then(res => {
          if (res.code == 200) {
            localStorage.setItem('tokenKey', res.result)
            this.getUserInfo()
          } else {
            this.swal({
              title: '用户信息获取失败！！！',
              type: 'error'
            })
          }

        }).catch(err => {
        })
      },
      getUserInfo() {
        this.$http.post('/put/jzg/getInfo').then(res => {
          console.log(res);
          if (res.code == 200) {
            this.userMess = res.result.userInfo
            this.ycMess.ck = res.result.userInfo.xm
            this.ycMess.cklxdh = res.result.userInfo.sjhm

          }
        }).catch(err => {
        })
      },

      okPeo(item) {
        this.compName = ''
        console.log(item);
        this.ycMess.ck = item.name
        this.ycMess.cklxdh = item.phone
        console.log(this.ycMess);
      },
      ycmessClear() {
        this.ycMess = {
          hcdz: '',
          originLat: '',
          originLng: '',
          mdd: '',
          destinationLat: '',
          destinationLng: '',
          ck: '',//乘客
          cklxdh: '',//电话
          yysj: '',//约车时间
          cllx: '',//10小车，20大车
          zws: '',
          sy: ''//备注事由
        }
      },
      getListItem(item) {
        console.log(item);
        var v = this
        this.orderItemMess = item

        v.clear()
        if(item.originLat && item.originLng && item.destinationLat && item.destinationLng){
          addcode('qd',item.originLng,item.originLat)
          addcode('zd',item.destinationLng,item.destinationLat)

          v.map.setViewport([
            new BMap.Point(item.originLng,item.originLat),
            new BMap.Point(item.destinationLng,item.destinationLat)
          ]);             //可视化点
        }

        function addcode(ico,lng,lat) {
          var myIcon = new BMap.Icon(
            'http://47.98.39.45:9092/icon/' + ico + '.png',
            new BMap.Size(50, 50),
            {
              anchor: new BMap.Size(25, 50),
              size: new BMap.Size(50, 50),
              imageSize: new BMap.Size(50, 50)
            });
          let point = new BMap.Point(lng, lat)
          let marker = new BMap.Marker(point, {icon: myIcon});  // 创建标注
          v.map.addOverlay(marker);               // 将标注添加到地图中
        }

      },
      myCar() {
        this.ycMess.ck = this.userMess.xm
        this.ycMess.cklxdh = this.userMess.sjhm
      },
      getGps() {
        var v = this
        this.SpinShow = true
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function (p) {
          console.log(p);
          v.BuildMap(p.longitude, p.latitude)
        });
      },
      BuildMap(lng, lat) {
        var v = this
        this.map = new BMap.Map("BmapBox");    // 创建Map实例
        let point = ''
        if (lng && lat) {
          point = new BMap.Point(lng, lat)
        } else {
          point = new BMap.Point(this.mapCode.lng, this.mapCode.lat)
        }
        this.map.centerAndZoom(point, 13);  // 初始化地图,设置中心点坐标和地图级别
        //添加地图类型控件
        this.map.addControl(new BMap.MapTypeControl({
          mapTypes: [
            BMAP_NORMAL_MAP,
            BMAP_HYBRID_MAP
          ]
        }));
        this.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
        this.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件
        // this.map.addControl(new BMap.OverviewMapControl());              //添加缩略地图控件
        // this.map.addControl(new BMap.NavigationControl()); 				// 添加平移缩放控件
        this.map.addEventListener("click", function (e) {
          // console.log(e.point.lng + "," + e.point.lat);
          v.getMapAddress(e.point)
        });
        this.SpinShow = false
      },
      getMapAddress(code) {
        var v = this
        new BMap.Geocoder().getLocation(new BMap.Point(code.lng, code.lat), (address) => {

          if (!(v.$refs['ycFormBox'].addStartCode)) { //出发地
            v.ycMess.hcdz = address.address

            v.ycMess.originLat = code.lat
            v.ycMess.originLng = code.lng

            v.addMarkerCode('qd', code.lng, code.lat)
          } else if (!(v.$refs['ycFormBox'].addEndCode)) { //目的地
            v.ycMess.mdd = address.address

            v.ycMess.destinationLat = code.lat
            v.ycMess.destinationLng = code.lng

            v.addMarkerCode('zd', code.lng, code.lat)
          }
        });
      },
      addMarkerCode(MarkerIcon, lng, lat) {
        this.deleCode(MarkerIcon)
        var myIcon = new BMap.Icon(
          'http://47.98.39.45:9092/icon/' + MarkerIcon + '.png',
          new BMap.Size(50, 50),
          {
            anchor: new BMap.Size(25, 50),
            size: new BMap.Size(50, 50),
            imageSize: new BMap.Size(50, 50)
          });
        let point = new BMap.Point(lng, lat)
        let marker = new BMap.Marker(point, {icon: myIcon});  // 创建标注
        this.map.addOverlay(marker);               // 将标注添加到地图中
        let label = new BMap.Label(MarkerIcon, {offset: new BMap.Size(20, -10)});
        label.setStyle({border: '#ffffff00', color: '#ffffff00', background: '#ffffff00'})
        marker.setLabel(label);
      },
      //清除层
      clear() {
        this.map.clearOverlays()
      },
      deleCode(MarkerIcon) {
        var allOverlay = this.map.getOverlays();
        for (var i = 0; i < allOverlay.length; i++) {
          try {
            if (allOverlay[i].getLabel().content == MarkerIcon) {
              this.map.removeOverlay(allOverlay[i]);
              return false;
            }
          } catch (e) {

          }
        }
      }
    }
  }
</script>

<style lang="less">
  .count-style {
    font-size: 50px;
  }
</style>
