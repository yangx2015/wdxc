package com.ldz.util.commonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.util.FileCopyUtils;

/**
 * @since 2017/11/1
 */
public class FileUtil {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
    
    
    public static void uploadCopyFile(InputStream  f,String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        //File out = new File(filePath+fileName);
        FileCopyUtils.copy(f,out);
        try {
        	
        	out.flush();
        	out.close();
		} catch (Exception e) {
			// TODO: handle exception
		} 
        //out.write(file);
        //out.flush();
        //out.close();
    }
}
