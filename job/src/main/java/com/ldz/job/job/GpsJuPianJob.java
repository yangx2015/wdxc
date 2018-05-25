package com.ldz.job.job;

import java.util.List;

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
 * 定时器说明:
 * 
 * @author liuzhihao
 *
 */
// 在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
// 等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution
public class GpsJuPianJob implements Job {
	Logger errorLog = LoggerFactory.getLogger("error_info");

	@Autowired
	private ClClMapper clclmapper;
	@Autowired
	private GpsService service;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<ClCl> selectAll = clclmapper.selectAll();

		try {
			for (ClCl clCl : selectAll) {
				service.guiJiJiuPian(clCl);
			}
		} catch (Exception e) {
			errorLog.error("同步鹰眼纠偏数据异常", e);
			JobExecutionException e2 = new JobExecutionException(e);
			// 当任务执行失败时，立即停止所有相关这个任务的触发器
			e2.setRefireImmediately(true);
		}

	}

}
