package com.ldz.autoupdate.util;

import org.springframework.stereotype.Component;

/**
 * auther chenwei
 * create at 2018/9/22
 */

public class LogUtil {
    private String infoLogPath;
    private String errorLogPath;
    private static LogUtil instance = null;

    private LogUtil(){}

    public static LogUtil getInstance(){
        if (instance == null){
            instance = new LogUtil();
        }
        return instance;
    }

    public void setInfoLogPath(String infoLogPath) {
        this.infoLogPath = infoLogPath;
    }

    public void setErrorLogPath(String errorLogPath) {
        this.errorLogPath = errorLogPath;
    }

    public void info(String s){
        FileUtil fileUtil = new FileUtil();
        fileUtil.append(infoLogPath,"[INFO] "+s);
    }

    public void error(String s){
        FileUtil fileUtil = new FileUtil();
        fileUtil.append(errorLogPath,"[ERROR] "+s);
    }

}
