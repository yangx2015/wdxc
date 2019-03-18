/**
 * 绑定路线功能
 * 将给点的点与给定路线绑定
 *
 * @type {{}}
 */

let CarIndexMap = new Map();
let stationIndexMap = new Map();
let xlIds = ''

let bindRoad = {
    initStationIndex:(stationList,linePoints,xlId)=>{
        let t = '';
        for (let i = 0;i<stationList.length;i++){
            let s = stationList[i];
            let nearPoints = [];
            for (let p of linePoints){
                let d = GetDistance(p.lat,p.lng,s.wd,s.jd);
                if (d < 100){
                    p.distance = d;
                    nearPoints.push(p);
                }
            }
            let p1 = '';
            let d1 = 9999999;
            for (let p of nearPoints){
                if (p.distance < d1){
                    d1 = p.distance;
                    p1 = p;
                }
            }
            t += '('+i+')'+p1.lat+','+p1.lng+';'
        }
        for (let i =0;i<linePoints.length;i++){
            let p = linePoints[i];
            let k = p.lat + ',' + p.lng;
            let index = t.indexOf(k);
            if (index === -1)continue
            let n = t.substring(index - 5,index-1)
            n = n.substring(n.indexOf('(')+1);
            let stationNumber = parseInt(n);
            stationIndexMap.set(xlId+'-'+stationNumber,index)
        }
        xlIds += xlId+','
    },
    /**
     *
     * @param pointInfo         websocketInfo
     * @param linePoints        线路上的点
     * @param range             纠偏精度
     * @param xlId              线路id
     * @param stationList       站点列表
     * @returns {*}
     */
    bindPoint3:(pointInfo,linePoints,range,xlId,stationList)=>{
        let nearPoints = [];
        if (xlIds.indexOf(xlId) === -1){
            bindRoad.initStationIndex(stationList,linePoints,xlId);
        }
        let stationNumber = pointInfo.stationNumber;
        if (stationNumber && stationNumber !== ''){
            let min = stationNumber - 1;
            if (min < 0) min = 0
            let max = stationNumber + 1;
            let stationIndex1 = stationIndexMap.get(xlId+'-' + min);
            let stationIndex2 = stationIndexMap.get(xlId+'-' + max);
            let find = false;
            for (let i = stationIndex1 ;i< stationIndex2 ;i++){
                let p = linePoints[i];
                let d = GetDistance(p.lat,p.lng,pointInfo.bdwd,pointInfo.bdjd);
                if (d < range){
                    p.distance = d;
                    nearPoints.push(p);
                    find = true;
                }
            }
            if (!find){
                console.log('!find');
                // pointInfo.bdjd = linePoints[index].lng;
                // pointInfo.bdwd = linePoints[index].lat;
                return pointInfo
            }
        }else{
            console.log('CarIndexMap.index',CarIndexMap.get(pointInfo.cphm));
            if (CarIndexMap.has(pointInfo.cphm)){
                let find = false;
                let index = CarIndexMap.get(pointInfo.cphm);
                let max = index + 10;
                if (max > linePoints.length) max = linePoints.length;
                for (let i = index ;i< max ;i++){
                    let p = linePoints[i];
                    let d = GetDistance(p.lat,p.lng,pointInfo.bdwd,pointInfo.bdjd);
                    if (d < range){
                        p.distance = d;
                        nearPoints.push(p);
                        CarIndexMap.set(pointInfo.cphm,i);
                        find = true;
                    }
                }
                if (!find){
                    let max = linePoints.length/2;
                    for (let i = 0 ;i< max ;i++){
                        let p = linePoints[i];
                        let d = GetDistance(p.lat,p.lng,pointInfo.bdwd,pointInfo.bdjd);
                        if (d < range){
                            p.distance = d;
                            nearPoints.push(p);
                            CarIndexMap.set(pointInfo.cphm,i);
                            find = true;
                        }
                    }
                }
                if (!find){
                    console.log('!find');
                    pointInfo.bdjd = linePoints[index].lng;
                    pointInfo.bdwd = linePoints[index].lat;
                    return pointInfo
                }
            }else{
                let max = linePoints.length/2;
                for (let i = 0 ;i< max ;i++){
                    let p = linePoints[i];
                    let d = GetDistance(p.lat,p.lng,pointInfo.bdwd,pointInfo.bdjd);
                    if (d < range){
                        p.distance = d;
                        nearPoints.push(p);
                        CarIndexMap.set(pointInfo.cphm,i);
                    }
                }
            }
        }

        console.log(pointInfo);
        if (nearPoints.length === 0)return pointInfo;

        let p1 = '';
        let d1 = 9999999;
        for (let p of nearPoints){
            if (p.distance < d1){
                d1 = p.distance;
                p1 = p;
            }
        }

        pointInfo.bdjd = p1.lng;
        pointInfo.bdwd = p1.lat;
        console.log('p1',p1);
        return pointInfo
    },
    bindPoint1:(pointInfo,linePoints,range,xlId,stationList)=>{
        let nearPoints = [];
        if (xlIds.indexOf(xlId) === -1){
            bindRoad.initStationIndex(stationList,linePoints,xlId);
        }
        console.log('CarIndexMap.index',CarIndexMap.get(pointInfo.zdbh));
        if (CarIndexMap.has(pointInfo.zdbh)){
            let find = false;
            let index = CarIndexMap.get(pointInfo.zdbh);
            let max = index + 10;
            if (max > linePoints.length) max = linePoints.length;
            for (let i = index ;i< max ;i++){
                let p = linePoints[i];
                let d = GetDistance(p.lat,p.lng,pointInfo.bdwd,pointInfo.bdjd);
                if (d < range){
                    p.distance = d;
                    nearPoints.push(p);
                    CarIndexMap.set(pointInfo.zdbh,i);
                    find = true;
                }
            }
            if (!find){
                let max = linePoints.length/2;
                for (let i = 0 ;i< max ;i++){
                    let p = linePoints[i];
                    let d = GetDistance(p.lat,p.lng,pointInfo.bdwd,pointInfo.bdjd);
                    if (d < range){
                        p.distance = d;
                        nearPoints.push(p);
                        CarIndexMap.set(pointInfo.zdbh,i);
                        find = true;
                    }
                }
            }
            if (!find){
                console.log('!find');
                pointInfo.bdjd = linePoints[index].lng;
                pointInfo.bdwd = linePoints[index].lat;
                return pointInfo
            }
        }else{
            let max = linePoints.length/2;
            for (let i = 0 ;i< max ;i++){
                let p = linePoints[i];
                let d = GetDistance(p.lat,p.lng,pointInfo.bdwd,pointInfo.bdjd);
                if (d < range){
                    p.distance = d;
                    nearPoints.push(p);
                    CarIndexMap.set(pointInfo.zdbh,i);
                }
            }
        }
        console.log(pointInfo);
        if (nearPoints.length === 0)return pointInfo;

        let p1 = '';
        let d1 = 9999999;
        for (let p of nearPoints){
            if (p.distance < d1){
                d1 = p.distance;
                p1 = p;
            }
        }

        pointInfo.bdjd = p1.lng;
        pointInfo.bdwd = p1.lat;
        console.log('p1',p1);
        return pointInfo
    },
    bindPoint2:(pointInfo,linePoints,range)=>{
        console.log('linePoints',linePoints);
        console.log('pointInfo',pointInfo);
        let nearPoints = [];
        for (let p of linePoints){
            let d = GetDistance(p.lat,p.lng,pointInfo.bdwd,pointInfo.bdjd);
            if (d < range){
                p.distance = d;
                nearPoints.push(p);
            }
        }
        console.log('nearPoints',nearPoints);
        if (nearPoints.length === 0)return pointInfo;

        let p1 = '';
        let d1 = 9999999;
        for (let p of nearPoints){
            if (p.distance < d1){
                d1 = p.distance;
                p1 = p;
            }
        }
        let p2 = '';
        let d2 = 9999999;
        for (let p of nearPoints){
            if (p.distance === d1)continue
            if (p.distance < d2){
                d2 = p.distance;
                p2 = p;
            }
        }

        let shadow = getShadow(pointInfo,p1,p2)
        console.log('shadow',shadow);
        console.log('p1',p1);
        console.log('d1',d1);
        console.log('p2',p2);
        console.log('d2',d2);
        return pointInfo
    }
}

function getShadow(p,p1,p2){
    let m = p.bdwd;
    let n = p.bdjd;
    let x1 = p1.lat;
    let y1 = p1.lng;
    let x2 = p2.lat;
    let y2 = p2.lng;
    let jd = (m*(x2-x1)*(x2-x1)+n*(y2-y1)*(x2-x1)+(x1*y2-x2*y1)*(y2-y1))
        /((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1))

    let wd = (m*(x2-x1)*(y2-y1)+n*(y2-y1)*(y2-y1)+(x2*y1-x1*y2)*(x2-x1))
        /((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1))
    return [jd,wd]
}
//进行经纬度转换为距离的计算

function Rad(d){
    return d * Math.PI / 180.0;//经纬度转换成三角函数中度分表形式。
}
//计算距离，参数分别为第一点的纬度，经度；第二点的纬度，经度
function GetDistance(lat1,lng1,lat2,lng2){
    lat1 = parseFloat(lat1)
    lng1 = parseFloat(lng1)
    lat2 = parseFloat(lat2)
    lng2 = parseFloat(lng2)

    var radLat1 = Rad(lat1);
    var radLat2 = Rad(lat2);
    var a = radLat1 - radLat2;
    var  b = Rad(lng1) - Rad(lng2);
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
    s = s *6378.137 ;// EARTH_RADIUS;
    s = Math.round(s * 10000); //输出为分米
    //s=s.toFixed(4);
    return s;
}

export default bindRoad
