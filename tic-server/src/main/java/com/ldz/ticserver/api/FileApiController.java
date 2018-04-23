package com.ldz.ticserver.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ldz.ticserver.plugins.file.FileConvertManager;
import com.ldz.ticserver.service.BizApiService;
import com.ldz.util.bean.ApiResponse;
import com.ldz.util.bean.RequestCommonParamsDto;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.commonUtil.FileUtil;

/**
 * 文件上传接口
 * @author wanggang
 *
 */
@RestController
@RequestMapping("/api/file")
public class FileApiController {
	
	@Value("${interface.filelocalpath}")
	private String filelocalpath;
	@Value("${interface.tsconvertmp4cmd}")
	private String tsconvertmp4;

	@Value("${interface.cacheimgdir}")
	private String cacheImgDir;
	@Value("${interface.videodir}")
	private String videoDir;
	@Value("${interface.imgdir}")
	private String imgdir;
	@Value("${interface.filesaveflag}")
	private Integer filesaveflag;
	
	
	@Autowired
	private BizApiService bizApiService;
	@Autowired
	private FileConvertManager convertManager;
	

	private static final Logger logger = LoggerFactory.getLogger(FileApiController.class);
	 //文件上传相关代码
    @RequestMapping(value = "upload")
    public ApiResponse<RequestCommonParamsDto> upload(RequestCommonParamsDto dto,@RequestParam("file") MultipartFile file) {
    	ApiResponse<RequestCommonParamsDto> sp = new ApiResponse<>();
    	sp.setMessage("上传成功");
        if (file.isEmpty()) {
        	sp.setCode(ApiResponse.FAILED);
        	sp.setMessage("文件不能为空");
            return sp;
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        // 文件上传后的路径
        String filePath = filelocalpath+dto.getDeviceId()+File.separator+DateUtils.getToday()+File.separator;
        if(suffixName.contains("jpg")){
        	filePath += imgdir+File.separator;
        }
        //if()
        // 解决中文问题，liunx下中文路径，图片显示问题 ，因为这里几乎不可能出现中文，所以这里先不用管
        // fileName = UUID.randomUUID() + suffixName;
        fileName = "car_"+dto.getDeviceId()+fileName;
        logger.info("文件保存的名称为：" + suffixName);
        try {
           // file.transferTo(dest);
        	FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        	dto.setFileLocalPath(filePath+fileName);
        	dto.setFilePath(fileName);
        	dto.setFilePostfix(suffixName);
        	dto.setFileRealName(file.getOriginalFilename());
        	dto.setFileSize(file.getSize()+"");
        	//dto.setEventType(eventType);
        	if(suffixName.contains("ts")){//只有ts文件才转换
        		dto.setFileLocalPath(filePath+fileName.replace(".ts", ".mp4"));
            	dto.setFilePath(fileName.replace(".ts", ".mp4"));
            	dto.setFilePostfix("mp4");
            	String convsp = tsconvertmp4.replace("@localfile", filePath+fileName);
            	convsp = convsp.replace("@newfile", filePath+fileName.replace(".ts", ".mp4"));
            	convertManager.convertMp4(convsp);
        	}
        	if(StringUtils.isBlank(dto.getEventType()) || !dto.getEventType().equals("99")){//上传的是缓存图片，不用上传到对应的服务器
        		bizApiService.pushFileData(dto);
        	}
        	sp.setMessage(file.getOriginalFilename());
        	sp.setResult(dto);
        	
            return sp;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
			// TODO: handle exception
        	 e.printStackTrace();
		}
        sp.setCode(ApiResponse.FAILED);
        sp.setMessage("上传失败");
        return sp;
    }
    
    //多文件上传(上传视频和视频截图得时候用，普通得文件上传还是用单个文件上传)
    @RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
    public ApiResponse<RequestCommonParamsDto> handleFileUpload(RequestCommonParamsDto dto,HttpServletRequest request) {
    	ApiResponse<RequestCommonParamsDto> sp = new ApiResponse<>();
    	sp.setMessage("上传成功");
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        MultipartFile file = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    //
                 // 获取文件名
                    String fileName = file.getOriginalFilename();
                    logger.info("上传的文件名为：" + fileName);
                    // 获取文件的后缀名
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    logger.info("上传的后缀名为：" + suffixName);
                    // 文件上传后的路径
                    String filePath = filelocalpath+dto.getDeviceId()+File.separator+DateUtils.getToday()+File.separator;
                    if(suffixName.contains("jpg")){
                    	filePath += cacheImgDir+File.separator;
                    }else{
                    	filePath += videoDir+File.separator;
                    }
                    //if()
                    // 解决中文问题，liunx下中文路径，图片显示问题 ，因为这里几乎不可能出现中文，所以这里先不用管
                    // fileName = UUID.randomUUID() + suffixName;
                    fileName = "car_"+dto.getDeviceId()+fileName;
                    logger.info("文件保存的名称为：" + suffixName);
                    try {
                    	if(filesaveflag==1){
                    		byte[] bytes = file.getBytes();
                    		FileUtil.uploadFile(bytes, filePath, fileName);
                    	}else if(filesaveflag == 2){
                    		File targetFile = new File(filePath);
                    		 if(!targetFile.exists()){
                    	            targetFile.mkdirs();
                    	      }
                    		file.transferTo(new File(filePath+fileName));
                    	}else{
                    		FileUtil.uploadCopyFile(file.getInputStream(), filePath, fileName);
                    	}
                       // 
                    	
                    	if(!suffixName.contains("jpg")){
	                    	dto.setFileLocalPath(filePath+fileName);
	                    	dto.setFilePath(fileName);
	                    	dto.setFilePostfix(suffixName);
	                    	dto.setFileRealName(file.getOriginalFilename());
	                    	dto.setFileSize(file.getSize()+"");
	                    	//dto.setEventType(eventType);
	                    	if(suffixName.contains("ts")){//只有ts文件才转换
	                    		dto.setFileLocalPath(filePath+fileName.replace(".ts", ".mp4"));
	                        	dto.setFilePath(fileName.replace(".ts", ".mp4"));
	                        	dto.setFilePostfix("mp4");
	                        	String convsp = tsconvertmp4.replace("@localfile", filePath+fileName);
	                        	convsp = convsp.replace("@newfile", filePath+fileName.replace(".ts", ".mp4"));
	                        	convertManager.convertMp4(convsp);
	                    	}
	                    	//if(StringUtils.isBlank(dto.getEventType()) || !dto.getEventType().equals("99")){//上传的是缓存图片，不用上传到对应的服务器
	                    		bizApiService.pushFileData(dto);
	                    	//}
	                    	sp.setMessage(file.getOriginalFilename());
	                    	sp.setResult(dto);
                    	}
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
            			// TODO: handle exception
                    	 e.printStackTrace();
            		}finally {
					}

                } catch (Exception e) {
                    sp.setCode(ApiResponse.FAILED);
                    sp.setMessage("上传的第"+i+"个文件出错，上传中断");
                    logger.error("You failed to upload " + i + " => "
                            + e.getMessage());
                    return sp;
                }
            } else {
            	 sp.setCode(ApiResponse.FAILED);
                 sp.setMessage("上传的第"+i+"个文件出错，上传中断");
                 logger.error("You failed to upload " + i
                        + " because the file was empty.");
                return sp;
            }
        }
        
        return sp;
    }
	
	
}
