package com.ldz.biz.module.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.ldz.biz.module.bean.GpsInfo;
import com.ldz.biz.module.mapper.ClClMapper;
import com.ldz.biz.module.mapper.ClSpkMapper;
import com.ldz.biz.module.model.ClCl;
import com.ldz.biz.module.model.ClSpk;
import com.ldz.biz.module.service.SpkService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.sys.base.LimitedCondition;
import com.ldz.util.exception.RuntimeCheck;
import com.ldz.sys.model.SysYh;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.redis.RedisTemplateUtil;

import tk.mybatis.mapper.common.Mapper;

@Service
public class SpkServiceImpl extends BaseServiceImpl<ClSpk, String> implements SpkService {


    private static final Logger log = LoggerFactory.getLogger(SpkServiceImpl.class);

    @Autowired
    private ClSpkMapper entityMapper;

    @Autowired
    private SimpMessagingTemplate websocket;
    @Autowired
    private RedisTemplateUtil redisutils;

  /*  @Value("${spk.url}")
	private String path;*/

    @Autowired
    private ClClMapper clclmapper;

    @Override
    protected Mapper<ClSpk> getBaseMapper() {
        return entityMapper;
    }

    @Override
    protected Class<?> getEntityCls() {
        return ClSpk.class;
    }

    @Override
    public boolean fillCondition(LimitedCondition condition) {

        return true;
    }

    @Override
    protected void afterPager(PageInfo<ClSpk> resultPage) {

        List<ClSpk> list = resultPage.getList();

        if (CollectionUtils.isNotEmpty(list)) {

            list.stream().forEach(s -> s.setDz(null));
        }

        return;
    }

    @Override
    public ApiResponse<String> saveEntity(ClSpk entity) {
//        SysYh user = getCurrentUser();
        Date now = new Date();
//        entity.setCjr(getOperateUser());
        entity.setCjsj(now);
        entity.setId(genId());
//        entity.setJgdm(user.getJgdm());

        save(entity);
        return ApiResponse.saveSuccess();
    }

    @Override
    public ApiResponse<String> updateEntity(ClSpk entity) {
        ClSpk oldRecord = findById(entity.getId());
        RuntimeCheck.ifNull(oldRecord, "未找到记录");
//        entity.setXgr(getOperateUser());
        entity.setXgsj(new Date());
        update(entity);
        return ApiResponse.success();
    }


    @Override
    public ApiResponse<String> saveSpk(GpsInfo entity) {
        log.info("收到的文件模型:" + entity);

        boolean save = true;
        BoundValueOperations<Object, Object> boundValueOps = redisutils.boundValueOps(entity.getFileRealName());
        String wjm = (String) boundValueOps.get();
        if (StringUtils.isNotEmpty(wjm)) {
            if (StringUtils.equals(entity.getFileRealName(), wjm)) {
                save = false;
                if (StringUtils.isEmpty(entity.getTaskId())) {
                    return ApiResponse.fail("文件名已存在");
                }
            }
        } else {
            boundValueOps.set(entity.getFileRealName());
            boundValueOps.set(entity.getFileRealName(), 30, TimeUnit.SECONDS);
        }


        ClSpk clSpk = new ClSpk();
        ClCl selectOne = new ClCl();
        selectOne.setZdbh(entity.getDeviceId());
        //通过终端编号找到对应车辆信息
        ClCl clinfo = clclmapper.selectOne(selectOne);
        clSpk.setDz(entity.getFileLocalPath());//本地地址 必传
        clSpk.setUrl(entity.getFilePath());//url   必传
        clSpk.setWjm(entity.getFileRealName());//文件名称  必传
        clSpk.setZdbh(entity.getDeviceId());//设备id  必传
        clSpk.setSplx(entity.getEventType());
        clSpk.setCjsj(new Date()); //创建时间
        clSpk.setClId(clinfo.getClId());//车辆id
        clSpk.setCph(clinfo.getCph());//车牌号
        clSpk.setId(genId());
        clSpk.setJgdm(clinfo.getJgdm());//机构代码
        clSpk.setJgmc(clinfo.getJgmc());//机构名称
        //这个视屏的任务id
        if (StringUtils.isNotEmpty(entity.getTaskId())) {
            clSpk.setBj(entity.getTaskId());
        }

        if (save){
            save(clSpk);
        }

        if (StringUtils.isNotEmpty(entity.getTaskId())) {
            //比较拍视频
            BoundListOperations<Object, Object> boundListOpsSp = redisutils.boundListOps("SP" + entity.getDeviceId());
            String indexSp = (String) boundListOpsSp.index(0);
            if (StringUtils.isNotEmpty(indexSp)) {
                for (int i = 0; i < boundListOpsSp.size(); i++) {
                    String index2 = (String) boundListOpsSp.index(i);
                    if (StringUtils.equals(entity.getTaskId(), index2)) {
                        websocket.convertAndSend("/topic/sendsp", JsonUtil.toJson(clSpk));
                        log.info("拍摄视频成功,并推送至前端" + JsonUtil.toJson(clSpk));
                        Long remove = boundListOpsSp.remove(1, index2);
                        log.info("redis中移除" + "SP" + entity.getDeviceId() + ":" + remove + "个元素为:" + entity.getTaskId());
                        String key = "sendInstruction-" + entity.getDeviceId() + "-video";
                        redisutils.boundValueOps(key).set(null, 1, TimeUnit.SECONDS);
                        return ApiResponse.success();
                    }

                }

            }
            //比较拍照片
            BoundListOperations<Object, Object> boundListOpsZp = redisutils.boundListOps("ZP" + entity.getDeviceId());
            String indexZp = (String) boundListOpsZp.index(0);
            if (StringUtils.isNotEmpty(indexZp)) {
                for (int i = 0; i < boundListOpsZp.size(); i++) {
                    String index2 = (String) boundListOpsZp.index(i);
                    if (StringUtils.equals(entity.getTaskId(), index2)) {
                        websocket.convertAndSend("/topic/sendzp", JsonUtil.toJson(clSpk));
                        log.info("拍摄照片成功,并推送至前端" + JsonUtil.toJson(clSpk));
                        Long remove = boundListOpsZp.remove(1, index2);
                        log.info("redis中移除" + "ZP" + entity.getDeviceId() + ":" + remove + "个元素为:" + entity.getTaskId());
                        String key = "sendInstruction-" + entity.getDeviceId() + "-photo";
                        redisutils.boundValueOps(key).set(null, 1, TimeUnit.SECONDS);
                        return ApiResponse.success();
                    }

                }

            }

            //比较合并视频
            BoundListOperations<Object, Object> boundListOps = redisutils.boundListOps("BJ" + entity.getDeviceId());
            String index = (String) boundListOps.index(0);

            if (StringUtils.isNotEmpty(index)) {
                for (int i = 0; i < boundListOps.size(); i++) {
                    String index2 = (String) boundListOps.index(i);
                    if (StringUtils.equals(entity.getTaskId(), index2)) {
                        websocket.convertAndSend("/topic/sendhbsp", JsonUtil.toJson(clSpk));
                        log.info("视屏合并成功,并推送至前端" + JsonUtil.toJson(clSpk));
                        Long remove = boundListOps.remove(1, index2);
                        log.info("redis中移除" + "BJ" + entity.getDeviceId() + ":" + remove + "个元素为:" + entity.getTaskId());
                        return ApiResponse.success();
                    }

                }

            }


        }

        return ApiResponse.success();
    }

    public static void main(String[] args) {

        List<String> ssList = new ArrayList<>();
        ssList.add("苹果");
        ssList.add("香蕉");
        ssList.add("荔枝");
        ssList.add("西瓜");

        for (int i = 0; i < ssList.size(); i++) {
            String string = ssList.get(i);
            if (string.equals("西瓜")) {
                String remove = ssList.remove(i);
                System.out.println(remove);
            }

        }
        System.out.println(ssList);
    }
}
