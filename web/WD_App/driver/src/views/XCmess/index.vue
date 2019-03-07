<style lang="less">
  @import "../../styles/common.less";
  .linemess{
    background-size: 100%;
    background-repeat:no-repeat;
    position: relative;
    .titBox{
      /*height: 142px;*/
      background: rgba(255,255,255,0.8);
      margin: 14px;
      margin-bottom: 0;
      .ck{
        border-bottom: 2px solid #C8C8C8;
        height: 71px;
        .iconImg{
            img{
              margin: 8px 8px;
            }
          .iconBack{
            background-color: #375a7e;
            text-align: center;
            line-height: 50px;
            font-size:22px;
            font-weight: 700px;
            margin: 5px;
            margin-top: 10px;
            /*position: absolute;*/
            /*left: 50%;*/
            /*top: 50%;*/
            /*transform: translate(-50%,-50%);*/
            height: 50px;
            width: 50px;
            border-radius: 25px;
            color: #fff;

          }
        }
        .ckName{
          font-size: 18px;
          /*line-height: 71px;*/
          .name{
            margin-top: 12px;
            margin-left: 15px;
            font-size: 15px;
            font-weight: 600;
          }
          .time{
            margin-left: 15px;
            font-size: 15px;
            font-weight: 500;
          }
        }
        .ckPhone{
          line-height: 50px;
          .phoneBorder{
            border-left: 2px solid #fdc087;
            height: 31px;
            width: 60px;
            margin: 20px 0;
            text-align: center;;
          }
          i{
            /*transform: rotate(180deg);*/
          }
        }
      }
      .line{
        font-size: 18px;
        padding:10px 0 10px 25px;
        position: relative;
        .xcqr{
          position: absolute;
          top: 0;
          right: 0;
          padding: 1rem;
          float: right;
        }
      }
    }
    /*行程详细*/
    .xcxx{
        padding:0 14px 0 14px;
    }
    /*行程确认*/
    /*.xcqr{*/
      /*padding: 1rem;*/
      /*float: right;*/
    /*}*/
  }
</style>
<template>
    <!--<div id="mapDOt" class="box linemess" >-->
      <div class="linemess box">
      <div id="allmap" class="box">
      </div>
        <div style="position: absolute;z-index: 9999;left: 0;right: 0">
          <div class="box-row" style="height: auto;">
            <div @click="center">
              <i class="iconfont icon-left"
                style="font-size: 28px"
              ></i>
            </div>
            <div class="body-O" style="text-align: center;line-height:40px;">
              <h2>
                单据详情
              </h2>
            </div>
          </div>
          <div class="titBox">
            <div class="box-row ck">
                <div class="iconImg">
                  <!--<img src="/static/tou.jpg" alt="">-->
                  <div class="iconBack">
                    {{mess.ck | fist}}
                  </div>
                </div>
                <div class="body-O ckName">
                  <div class="name">
                    {{mess.jgmc}}-{{mess.ck}}
                  </div>
                  <div class="time">
                    {{mess.yysj}}
                  </div>
                </div>
                <div class="ckPhone">
                  <div  class="phoneBorder">
                    <a :href="'tel:'+ mess.cklxdh">
                      <Icon type="ios-telephone" size="28" color="#fdc087"></Icon>
                    </a>
                  </div>
                </div>
            </div>
            <div class="line">
              <div class="hcdz">
                <Icon type="ios-location" color="#15b740"></Icon>
                {{mess.hcdz}}
              </div>
              <div class="mdd">
                <Icon type="ios-location" color="#ff9b00"></Icon>
                {{mess.mdd}}
              </div>
              <div class="xcqr" v-show="mess.ddzt!=20&&mess.ddzt!=30">
                <div class="box-row">
                  <div class="body-O" style="margin: 0 8px">
                    <Button type="warning" size="large" long @click="lineOk">行程确认</Button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <div class="xcxx" v-show="mess.ddzt==20||mess.ddzt==30">
              <div class="box-row"
                   style="text-align: center;height: 75px;background: #fff">
                <div class="body-O">
                  <div>
                    里程:
                  </div>
                  <div>
                    {{mess.lc | text}}公里
                  </div>
                </div>
                <div class="body-O">
                  <div>
                    单价:
                  </div>
                  <div>
                    {{mess.dj | text}}元/公里
                  </div>
                </div>
                <div mess="body-O">
                  <div>
                    过桥费:
                  </div>
                  <div>
                    {{mess.gqf | text}}元
                  </div>
                </div>
                <div class="body-O">
                  <div>
                    路停费:
                  </div>
                  <div>
                    {{mess.glf | text}}元
                  </div>
                </div>
              </div>
          </div>
          <div class="body">
        </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "index",
        data(){
          return{
            zoom:18,
            map:'',
            mess:this.$store.state.app.lineData
          }
        },
        filters: {
          text: function (value) {
            if (!value) return '***'
            else return value
          },
          fist(val){
            // return val.substr(0,1)
          }

        },
      created(){
          this.getZoom();
          console.log(this.mess);
        if(!this.$store.state.app.lineData.ck){
          this.$router.push({
            name:'center'
          })
        }
      },
      mounted(){
        this.initMap();
        // 百度地图API功能
        // this.map = new BMap.Map("mapDOt"); // 创建Map实例

        if(this.$store.state.app.listType==1){
          this.gpsMess()
        }else if (this.$store.state.app.listType==0){
          this.addmap()
        }
      },
        methods:{
          mapKS(val){
            if(val){
              return val
            }
            return this.mess.hcdz
          },
          mapJS(val){
            if(val){
              return val
            }
            return this.mess.mdd
          },
          gpsMess(){
            var v =this
            this.$http.post(this.apis.GPS.DOT,{'orderId':this.mess.id}).then((res)=>{
              if(res.code == 200){
                v.addmap(res)
              }
            })
          },
          addmap(dot){
            var v = this
            var point = new BMap.Point(114.29788, 30.833161);
            if(dot){
              if(dot.result){
                point = new BMap.Point(dot.result.centerJd, dot.result.centerWd);
              }
            }
            // v.map.centerAndZoom(point, 10)
            //添加地图类型控件
            v.map.enableScrollWheelZoom(true);     					     //开启鼠标滚轮缩放
            v.map.addControl(new BMap.ScaleControl()); 					 // 添加比例尺控件


            if(dot){
              if(dot.result){
                let zoom = [
                  new BMap.Point(dot.result.ksjd,dot.result.kswd),
                  new BMap.Point(dot.result.jsjd,dot.result.jswd)
                ]
                v.getZoom(zoom)
              }
            }
          },
          //撒点
          // disDot(list){
          //   this.map.clearOverlays()
          //   // 随机向地图添加25个标注
          //   for (var i = 0; i < list.length; i ++) {
          //     var point = new BMap.Point(list[i].lng, list[i].lat);
          //     this.addMarker(point,i);
          //   }
          //   this.map.setViewport(list)
          // },
          // 编写自定义函数,创建标注
          // addMarker(point,index){
          //     var v = this
          //     let imgUrl = 'http://lbsyun.baidu.com/jsdemo/img/fox.gif'
          //     if(index==0){
          //       imgUrl = 'http://47.98.39.45:9092/icon/map_line_begin.png'
          //     }else if(index==1){
          //       imgUrl = 'http://47.98.39.45:9092/icon/map_line_end.png'
          //     }
          //     var myIcon = new BMap.Icon(imgUrl, new BMap.Size(300,157));
          //     var marker = new BMap.Marker(point,{icon:myIcon});
          //     v.map.addOverlay(marker);
          //   },
          center(){
            this.$router.push({
              name:'center'
            })
          },
          lineOk(){
            this.$router.push({
              name:'okModal'
            })
          },
          addMarker(points) {
            console.log('666',points);
            var pt = new BMap.Point(points[0].longitude, points[0].latitude);
            var myIcon = new BMap.Icon("http://47.98.39.45:9092/icon/map_line_begin.png", new BMap.Size(70,70),{imageSize: new BMap.Size(40,40),anchor: new BMap.Size(25, 25)});
            var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
            this.map.addOverlay(marker2);              // 将标注添加到地图中
          },
          addMarkert(points) {
            console.log('666',points.length);
            var pt = new BMap.Point(points[points.length-1].longitude, points[points.length-1].latitude);
            var myIcon = new BMap.Icon("http://47.98.39.45:9092/icon/map_line_end.png", new BMap.Size(70,70),{imageSize: new BMap.Size(40,40),anchor: new BMap.Size(25, 25)});
            var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
            this.map.addOverlay(marker2);              // 将标注添加到地图中
          },
          showLine(points){
            let ps = [];
            this.map.centerAndZoom(new BMap.Point(points[(points.length -(points.length % 2))/2].longitude, points[(points.length -(points.length % 2))/2].latitude),  this.zoom);
            for (let r of points){
              ps.push(new BMap.Point(r.longitude, r.latitude));
            }
            var polyline = new BMap.Polyline(ps,
              {strokeColor:"green", strokeWeight:5, strokeOpacity:0.5}
            );

            this.map.addOverlay(polyline);
          },
          getDdGpsList(){
              this.$http.post('/put/dd/getddgps',{orderId: this.mess.id}).then((res)=>{
                console.log(res.result);
                if(res.code == 200 && res.result){
                  this.points= res.result;
                  if(this.points.length > 0){
                    this.showLine(this.points);
                    this.addMarker(this.points);
                    this.addMarkert(this.points)
                  }
                }else{
                  this.$Message.warning(res.message);
                }
                // this.addMarker()
              })
          },
          getZoom(line){
            let zoom = [
              [1000000,500000,4],
              [500000,200000,5],
              [200000,100000,6],
              [100000,50000,7],
              [50000,25000,8],
              [25000,20000,9],
              [20000,10000,10],
              [10000,5000,11],
              [5000,2000,12],
              [2000,1000,13],
              [1000,500,14],
              [500,200,15],
              [200,100,16],
              [100,50,17],
              [50,20,18]
            ];
            zoom.forEach((item,index) => {
              if(line<=item[0]&&line>=item[1]){
                this.zoom =  item[2]
                console.log(item[2]);
              }
            });
          },
          initMap(){
            var script = document.createElement("script")
            script.type = "text/javascript"
            var callbackName = '_callback'+Date.now()
            let v = this
            window[callbackName]= function(){
              console.log(BMap)
              v.map = new BMap.Map("allmap");    // 创建Map实例
              v.map.centerAndZoom(new BMap.Point(114.368107, 30.543083), v.zoom);  // 初始化地图,设置中心点坐标和地图级别
              var point = new BMap.Point(114.368107, 30.543083);
              // v.map.centerAndZoom(point, 15);
              // var marker = new BMap.Marker(point,);
              // v.map.addOverlay(marker);
              //添加地图类型控件
              v.map.addControl(new BMap.MapTypeControl({
                mapTypes:[
                  BMAP_NORMAL_MAP,
                  BMAP_HYBRID_MAP
                ]}));
              v.map.setCurrentCity("武汉");          // 设置地图显示的城市 此项是必须设置的
              v.map.enableScrollWheelZoom(true);

              v.getDdGpsList()
              //开启鼠标滚轮缩放
              delete window[callbackName]
            }
            script.src="https://api.map.baidu.com/api?v=2.0&ak=evDHwrRoILvlkrvaZEFiGp30&callback="+callbackName
            document.body.appendChild(script)
          },
        }
    }
</script>

<style scoped>

</style>
