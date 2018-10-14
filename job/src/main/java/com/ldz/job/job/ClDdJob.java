package com.ldz.job.job;

import com.ldz.job.mapper.ClDdMapper;
import com.ldz.job.mapper.ClZdglMapper;
import com.ldz.job.service.GpsService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 定时器说明:每天凌晨，将约车时间为 前一天，并未还未派单(10,11) 的订单，标记为 订单驳回 状态
 *
 * @author 
 *
 */
// 在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
// 等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution
public class ClDdJob implements Job {
	Logger errorLog = LoggerFactory.getLogger("error_info");
	Logger accessLog= LoggerFactory.getLogger("access_info");

	@Autowired
	private ClDdMapper clDdMapper;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			clDdMapper.updateBeOverdueOrder();
		} catch (Exception e) {
			errorLog.error("清除过期订单-数据异常", e);
			JobExecutionException e2 = new JobExecutionException(e);
			// 当任务执行失败时，立即停止所有相关这个任务的触发器
			e2.setRefireImmediately(true);
		}

	}
}
