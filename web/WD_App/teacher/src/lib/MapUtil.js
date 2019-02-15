let MapUtil = {
  getPoint(map,callback){
    map.addEventListener("click",function(e){
      callback(e.point)
    });
  },
  getAddress(map,callback){
    map.removeEventListener('click',()=>{})
    map.addEventListener("click",function(e){
      var geoc = new BMap.Geocoder();
      geoc.getLocation(new BMap.Point(e.point.lng,e.point.lat), (rs)=>{
        console.log(rs);
        callback(rs)
      });
    });
  },
}
export default MapUtil
