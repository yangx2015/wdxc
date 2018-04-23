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
import com.ldz.job.job.GpsZtSyncJob;

@Component
public class ScheduleComponent {

	Logger errorLog = LoggerFactory.getLogger("error_info");

	@Autowired
	private SchedulerFactoryBean schedulerFactory;

	public void startScheduler() {
		// gps同步job
		JobDetail jobDetail = JobBuilder.newJob(GpsSaveJob.class).withIdentity(GpsSaveJob.class.getName(), "GPSSync")
				.build();
		// 设备状态同步job
		JobDetail zdglJob = JobBuilder.newJob(GpsZtSyncJob.class).withIdentity(GpsZtSyncJob.class.getName(), "zdglsync")
				.build();

		// 执行周期，每10分钟执行一次
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ? *");
		
		
		// gps同步创建一个trigger
		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(GpsSaveJob.class.getName(), "GPSSync")
				.withSchedule(scheduleBuilder).build();
		// 创建设备状态同步trigger
		CronTrigger cronTriggerzdgl = TriggerBuilder.newTrigger().withIdentity(GpsZtSyncJob.class.getName(), "zdglsync")
				.withSchedule(scheduleBuilder).build();
		try {

			schedulerFactory.getScheduler().scheduleJob(jobDetail, cronTrigger);
			schedulerFactory.getScheduler().scheduleJob(zdglJob, cronTriggerzdgl);
		} catch (SchedulerException e) {
			errorLog.error("任务创建失败", e);
		}
	}
}
