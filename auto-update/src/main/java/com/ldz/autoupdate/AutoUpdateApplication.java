package com.ldz.autoupdate;

import com.ldz.autoupdate.util.ConfigUtil;
import com.ldz.autoupdate.util.FileUtil;
import com.ldz.autoupdate.util.LogUtil;
import com.ldz.autoupdate.util.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class AutoUpdateApplication {


    public static void main(String[] args) {
        FileUtil fileUtil = new FileUtil();
        String path = fileUtil.getCurrentPath();
        LogUtil logUtil = LogUtil.getInstance();
        logUtil.setInfoLogPath(path + "/info.log");
        logUtil.setErrorLogPath(path + "/error.log");

        ConfigUtil configUtil = new ConfigUtil();

        Map<String,String> configMap = configUtil.getConfig(path + "/config.txt");
        List<DeviceItem> deviceList = configUtil.getItems(path+"/deviceList.txt");
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
    }
}
