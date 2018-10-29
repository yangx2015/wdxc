package com.ldz.job.job;

import com.ldz.job.service.PbService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 定时器说明:
 * 第五分钟 在排班表中查询一次排班时间已经过期的排班任务。
 * @author 
 *
 */
// 在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
// 等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution
public class ClPbJob implements Job {
	Logger errorLog = LoggerFactory.getLogger("error_info");
	Logger accessLog= LoggerFactory.getLogger("access_info");

	@Autowired
	private PbService pbService;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			pbService.removePb();
		} catch (Exception e) {
			errorLog.error("移除过期排班-数据异常", e);
			JobExecutionException e2 = new JobExecutionException(e);
			// 当任务执行失败时，立即停止所有相关这个任务的触发器
			e2.setRefireImmediately(true);
		}

	}
}
