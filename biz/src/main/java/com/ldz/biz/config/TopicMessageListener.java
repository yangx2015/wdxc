package com.ldz.biz.config;

import com.ldz.biz.module.model.ClXc;
import com.ldz.biz.module.model.Clyy;
import com.ldz.biz.module.service.ClYyService;
import com.ldz.biz.module.service.XcService;
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
    private RedisTemplateUtil redisTemplateUtil;


    /**
     * 根据 redis 失效时间存储行程
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {

        byte[] body = message.getBody();// 请使用valueSerializer
        byte[] channel = message.getChannel();
        String topic = new String(channel);
        String itemValue = new String(body);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.contains(topic, "expired")) {
            String[] vals = itemValue.split(",");
            String zdbh = vals[1];
            String startTime = vals[2];
            String endTime = vals[3];
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
            ClXc clXc = new ClXc();
            clXc.setClZdbh(zdbh);
            clXc.setXcKssj(startTime);
            clXc.setXcJssj(endTime);
            xcService.saveEntity(clXc);

            try {
                guiJiJiuPian(zdbh, s, e);
            }catch (Exception e2){
                // 百度轨迹点异常 ， 存储异常行程 ， 等待第二次纠偏
                redisTemplateUtil.boundValueOps("compencate,"+zdbh+","+startTime+","+endTime).set("1",1,TimeUnit.MINUTES);
            }

        }

    }


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

        } else {
            return;
        }

    }

    public String parse(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Long aLong = time * 1000;
        Date date = new Date(aLong);
        String format = simpleDateFormat.format(date);
        return format;
    }


}
