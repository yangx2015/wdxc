package com.ldz.autoupdate;

import com.ldz.autoupdate.util.ConfigUtil;
import com.ldz.autoupdate.util.FileUtil;
import com.ldz.autoupdate.util.LogUtil;
import com.ldz.autoupdate.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class AutoUpdateApplication {


    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "["+sdf.format(new Date()) +"]";
        FileUtil fileUtil = new FileUtil();
        String path = fileUtil.getCurrentPath();
        System.out.println(time+"starting autoUpdate...");
        System.out.println(time+"current path : "+path);
        System.out.println(time+"see log.txt for further information");
        LogUtil logUtil = LogUtil.getInstance();
        logUtil.setInfoLogPath(path + "/info.log");
        logUtil.setErrorLogPath(path + "/error.log");

        ConfigUtil configUtil = new ConfigUtil();

        Map<String,String> configMap = configUtil.getConfig(path + "/config.txt");
        if (configMap == null)return;
        List<DeviceItem> deviceList = configUtil.getItems(path+"/deviceList.txt");
        if (deviceList == null)return;
        if (deviceList.size() == 0) {
            logUtil.info("暂无需要升级的设备");
            return;
        }
        List<String> deviceIds = deviceList.stream().map(DeviceItem::getDeviceId).collect(Collectors.toList());
        SpringApplication.run(AutoUpdateApplication.class, args);
        Config config = SpringContextUtil.getBean(Config.class);
        if (config == null){
            logUtil.error("启动失败");
            return;
        }
        config.setDecviceIds(deviceIds);
        config.setDeviceItemList(deviceList);
        config.setConfigMap(configMap);

        System.out.println(time+"starting autoUpdate success!");
    }
}
