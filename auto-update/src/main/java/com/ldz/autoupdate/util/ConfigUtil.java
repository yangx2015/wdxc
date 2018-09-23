package com.ldz.autoupdate.util;

import com.ldz.autoupdate.DeviceItem;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * auther chenwei
 * create at 2018/9/22
 */
public class ConfigUtil {
    private LogUtil logUtil = LogUtil.getInstance();
    public Map<String,String> getConfig(String path){
        Map<String,String> map = new HashMap<>();
        FileUtil fileUtil = new FileUtil();
        File file = new File(path);
        if (!file.exists()){
            logUtil.info("请设置配置文件：config.txt");
            return null;
        }
        List<String> lines = fileUtil.readFile(path);
        for (String line : lines) {
            if (StringUtils.isEmpty(line)) continue;
            String[] row = line.trim().split("=");
            if (row.length < 2) continue;
            map.put(row[0],row[1]);
        }
        return map;
    }

    public List<DeviceItem> getItems(String path){
        List<DeviceItem> list = new ArrayList<>();
        FileUtil fileUtil = new FileUtil();
        File file = new File(path);
        if (!file.exists()){
            logUtil.info("请设置配置文件：deviceList.txt");
            return null;
        }
        List<String> lines = fileUtil.readFile(path);
        for (String line : lines) {
            String[] row = line.trim().split("-");
            DeviceItem deviceItem = new DeviceItem(row);
            list.add(deviceItem);
        }
        return list;
    }
}
