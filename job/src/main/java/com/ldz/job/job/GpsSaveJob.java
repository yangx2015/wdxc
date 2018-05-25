package com.ldz.job.job;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ldz.job.mapper.ClClMapper;
import com.ldz.job.model.ClCl;
import com.ldz.job.service.GpsService;

/**
 * 定时器说明:每隔1分钟定时从redis里面获取数据写入CLgps,CLgpsLs表中
 * 
 * @author 
 *
 */
// 在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
// 等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution
public class GpsSaveJob implements Job {
	Logger errorLog = LoggerFactory.getLogger("error_info");
	Logger accessLog= LoggerFactory.getLogger("access_info");  

	@Autowired
	private ClClMapper clclmapper;
	@Autowired
	private GpsService GpsService;
	
	
    private  List<String> zdbhs = new ArrayList<>();
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	 zdbhs= new ArrayList<>();
		
	if (CollectionUtils.isEmpty(zdbhs)) {
		// 获取所有的终端编号
		List<ClCl> gpslist = clclmapper.selectAll();
	
		zdbhs = gpslist.stream().filter(s->StringUtils.isNotEmpty(s.getZdbh())).map(ClCl::getZdbh).collect(Collectors.toList());
		
		}
	
	
	
		try {
			for (String zdbh : zdbhs) {

				GpsService.InsetRedisToDb(zdbh);
			}

		} catch (Exception e) {
			errorLog.error("同步redis中gps数据异常", e);
			JobExecutionException e2 = new JobExecutionException(e);
			// 当任务执行失败时，立即停止所有相关这个任务的触发器
			e2.setRefireImmediately(true);
		}

	}
}
