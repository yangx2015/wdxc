<template>
  <div class="content">
    <div height="40" space="10">
      <div span="2" vertical-align="middle">
        地址：
      </div>
      <div span="10">
        <input class="input" type="string" name="input" value="hasLocation?locationAddress:'未选择位置'"></input>
      </div>
    </div>
    <div height="40" space="10">
      <div span="2" vertical-align="middle">
        经纬度：
      </div>
      <div span="10">
        <input class="input" type="string" name="input" value="hasLocation?'E:'+ location.longitude[0]+'°'+location.longitude[1]+'′ N:'+location.latitude[0]+'°'+location.latitude[1]+'′':'未选择位置'"></input>
      </div>
    </div>
    <button type="primary" bindtap="chooseLocation">选择位置</button>
    <button bindtap="clear">清空</button>
  </div>
</template>

<script>
  var util = require('@/utils/util')
  var formatLocation = util.formatLocation
  export default {
    data () {
      return {
        hasLocation: false,
        location: '',
        locationAddress: ''
      }
    },
    methods: {
      chooseLocation: function () {
        var that = this
        ui.chooseLocation({
          backgroundColor:'#3399ff',
          success: function (res) {
            console.log('success:', res)
            that.setData({
              hasLocation: true,
              location: formatLocation(res.longitude, res.latitude),
              locationAddress: res.address
            })
          },
          fail: function (res) {
            console.log('fail:', res)
          }
        })
      },
      clear: function () {
        this.setData({
          hasLocation: false
        })
      }
    }
  }
</script>

<style scoped>

</style>
