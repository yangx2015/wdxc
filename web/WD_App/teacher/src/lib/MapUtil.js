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
        var addComp = rs.addressComponents;
        let address = addComp.street;
        callback(address)
      });
    });
  },
}
export default MapUtil
