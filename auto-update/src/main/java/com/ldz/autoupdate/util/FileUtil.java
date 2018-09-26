package com.ldz.autoupdate.util;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * auther chenwei
 * create at 2018/9/22
 */
public class FileUtil {

    public void append(String path,String content){
        File file = new File(path);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.append(sdf.format(new Date()) + " : "+content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                try {
                    writer.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public List<String> readFile(String path){
        List<String> lines = null;
        try {
            lines = FileUtils.readLines(new File(path), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public String getCurrentPath(){
        File directory = new File("");//设定为当前文件夹
        try{
            String path1 = directory.getCanonicalPath();
            return path1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
