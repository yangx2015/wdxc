package com.ldz.job.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.ldz.job.job.GpsSaveJob;

@Component
public class ScheduleComponent {
	
	Logger errorLog = LoggerFactory.getLogger("error_info");

	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	
	public void startScheduler(){
		
		JobDetail jobDetail = JobBuilder.newJob(GpsSaveJob.class).withIdentity(GpsSaveJob.class.getName(), "GPSSync").build(); 
		//执行周期，每10分钟执行一次
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/10 * * * ? *"); 
		//创建一个trigger
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(GpsSaveJob.class.getName(), "GPSSync") .withSchedule(scheduleBuilder).build();
		try {
			
			schedulerFactory.getScheduler().scheduleJob(jobDetail, cronTrigger);
		} catch (SchedulerException e) {
			errorLog.error("任务创建失败", e);
		}
	}
}
