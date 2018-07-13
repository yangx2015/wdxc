package com.ldz.biz.config;

import com.ldz.biz.module.model.ClGpsLs;
import com.ldz.biz.module.model.ClXc;
import com.ldz.biz.module.model.Clyy;
import com.ldz.biz.module.service.ClYyService;
import com.ldz.biz.module.service.GpsLsService;
import com.ldz.biz.module.service.XcService;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.bean.TrackJiuPian;
import com.ldz.util.bean.TrackPointsForReturn;
import com.ldz.util.redis.RedisTemplateUtil;
import com.ldz.util.yingyan.GuiJIApi;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class TopicMessageListener implements MessageListener {


    @Autowired
    private XcService xcService;
    @Autowired
    private ClYyService clYyService;
    @Autowired
    private GpsLsService gpsLsService;

    private RedisTemplateUtil redisTemplate;

    public void setRedisTemplate(RedisTemplateUtil redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 根据 redis 失效时间存储行程
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String itemValue = new String(message.getBody());
        String topic =  new String(message.getChannel());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.contains(topic, "expired")) {
            String[] vals = itemValue.split(",");
            String type = vals[0];
            String zdbh = vals[1];
            String startTime = vals[2];
            String endTime = vals[3];
            String startPoint = vals[4];
            String endPoint = vals[5];
            long s = 0;
            long e = 0;
            try {
                s = simpleDateFormat.parse(startTime).getTime();
                e = simpleDateFormat.parse(endTime).getTime();
            } catch (ParseException e1) {
            }
            if((e - s) < 60000 ){ // 开始时间与结束时间小于1分钟 ， 行程短 ， 过滤
                // 轨迹点不存储
                return;
            }

            try {
                guiJiJiuPian(zdbh, s, e);
            }catch (Exception e2){
                if(StringUtils.equals(type,"start_end")) {
                    // 百度轨迹点异常 ， 存储异常行程 ， 等待第二次纠偏
                    redisTemplate.boundValueOps("compencate," + zdbh + "," + startTime + "," + endTime).set("1", 1, TimeUnit.MINUTES);
                }else if(StringUtils.equals(type,"compencate")){
                    // 存储当前原始轨迹点
                    saveGps(zdbh,startTime,endTime);
                }
            }
            ClXc clXc = new ClXc();
            clXc.setClZdbh(zdbh);
            clXc.setXcKssj(startTime);
            clXc.setXcJssj(endTime);
            clXc.setXcStartEnd(startPoint + "," + endPoint);
            xcService.saveEntity(clXc);

        }

    }

    /**
     * 存储百度鹰眼GPS
     * @param zdbh
     * @param startTime
     * @param endTime
     */
    public void guiJiJiuPian(String zdbh, long startTime, long endTime) {

        TrackJiuPian guijis = new TrackJiuPian();
        guijis.setAk(GuiJIApi.AK);
        guijis.setService_id(GuiJIApi.SERVICE_ID);
        guijis.setEntity_name(zdbh);
        guijis.setIs_processed("1");
        // 查询 去燥 ，抽希 ， 绑路 的坐标点
        guijis.setProcess_option("need_denoise=1,need_vacuate=1,need_mapmatch=1,transport_mode=driving");
        guijis.setSupplement_mode("driving");
        guijis.setSort_type("asc");
        guijis.setCoord_type_output("bd09ll");
        guijis.setEnd_time(String.valueOf(endTime / 1000));
        guijis.setStart_time(String.valueOf(startTime / 1000));
        guijis.setPage_size("5000");
        // 查询 小时内的轨迹坐标点
        TrackPointsForReturn points = GuiJIApi.getPoints(guijis, GuiJIApi.getPointsURL);
        List<TrackPointsForReturn.Point> points2 = points.getPoints();
        if (CollectionUtils.isNotEmpty(points2)) {
            List<Clyy> yyList = new ArrayList<>();

            for (TrackPointsForReturn.Point point : points2) {
                Clyy clyy = new Clyy();
                clyy.setDirection(point.getDirection() + "");
                clyy.setLatitude(BigDecimal.valueOf(point.getLatitude()));
                clyy.setLoc_time(parse(point.getLoc_time()));
                clyy.setLongitude(BigDecimal.valueOf(point.getLongitude()));
                clyy.setSpeed(BigDecimal.valueOf(point.getSpeed()));
                clyy.setZdbh(zdbh);
                yyList.add(clyy);
            }
            clYyService.saveBatch(yyList);
            String start_end = yyList.get(0).getLongitude() + "-" + yyList.get(0).getLatitude() + "," + yyList.get(yyList.size()-1).getLongitude()+"-"+yyList.get(yyList.size()-1).getLatitude();
        } else {
            return;
        }

    }

    /**
     * 存储原始轨迹点
     */
    public void saveGps(String zdbh,String startTime, String endTime){

        SimpleCondition simpleCondition = new SimpleCondition(ClGpsLs.class);
        simpleCondition.and().andBetween(ClGpsLs.InnerColumn.cjsj.name(),startTime,endTime);
        simpleCondition.eq(ClGpsLs.InnerColumn.zdbh.name(),zdbh);
        List<Clyy> yyList = new ArrayList<>();
        List<ClGpsLs> gpsLsList = gpsLsService.findByCondition(simpleCondition);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        gpsLsList.stream().forEach(clGpsLs -> {
            Clyy clyy = new Clyy();
            clyy.setDirection(clGpsLs.getFxj()+"");
            clyy.setLatitude(clGpsLs.getBdwd());
            clyy.setLoc_time(simpleDateFormat.format(clGpsLs.getCjsj()));
            clyy.setLongitude(clGpsLs.getBdjd());
            clyy.setSpeed(new BigDecimal(clGpsLs.getYxsd()));
            clyy.setZdbh(zdbh);
            yyList.add(clyy);
        });
        clYyService.saveBatch(yyList);


    }



    public String parse(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Long aLong = time * 1000;
        Date date = new Date(aLong);
        String format = simpleDateFormat.format(date);
        return format;
    }


}
