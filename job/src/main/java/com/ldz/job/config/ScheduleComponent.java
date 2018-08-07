package com.ldz.job.config;

import com.ldz.job.job.ClNianShenJob;
import com.ldz.job.job.GpsSaveJob;
import com.ldz.job.job.SbYxSjJlJob;
import com.ldz.job.job.ZdToYyJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ScheduleComponent {

    Logger errorLog = LoggerFactory.getLogger("error_info");

    @Autowired
    private SchedulerFactoryBean schedulerFactory;

    public void startScheduler() {
        // gps同步job
        JobDetail jobDetail = JobBuilder.newJob(GpsSaveJob.class).withIdentity(GpsSaveJob.class.getName(), "GPSSync")
                .build();
        // gps同步定执行周期，每1分钟执行一次
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ? *");
        // gps同步创建一个trigger
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(GpsSaveJob.class.getName(), "GPSSync")
                .withSchedule(scheduleBuilder).build();

        // 车辆年审日期获取job
        JobDetail nianshenJob = JobBuilder.newJob(ClNianShenJob.class).withIdentity(ClNianShenJob.class.getName(), "clnssync")
                .build();
        // 车辆年审日期设置  每天中午12点执行一次
        CronScheduleBuilder scheduleBuilderns = CronScheduleBuilder.cronSchedule("0 0 12 * * ?");
        //创建车辆年审日期获取trigger
        CronTrigger cronTriggerclns = TriggerBuilder.newTrigger().withIdentity(ClNianShenJob.class.getName(), "clnssync")
                .withSchedule(scheduleBuilderns).build();

        // 设备运行事件记录 job
        JobDetail sbyxsjjlJob = JobBuilder.newJob(SbYxSjJlJob.class).withIdentity(SbYxSjJlJob.class.getName(), "sbyxsjjlSync")
                .build();
        // 设备运行事件记录周期，每一分钟运行一次
        CronScheduleBuilder sbyxsjjlBuilder = CronScheduleBuilder.cronSchedule("0 0/1 * * * ? *");
        // 设备运行事件trigger
        CronTrigger sbyxsjjlTrigger = TriggerBuilder.newTrigger().withIdentity(SbYxSjJlJob.class.getName(), "sbyxsjjlSync")
                .withSchedule(sbyxsjjlBuilder).build();

        // 设备状态同步job
		/*JobDetail zdglJob = JobBuilder.newJob(GpsZtSyncJob.class).withIdentity(GpsZtSyncJob.class.getName(), "zdglsync")
				.build();*/
        // 终端状态监测定时周期 每10分钟执行一次
        /*CronScheduleBuilder scheduleBuilderZD = CronScheduleBuilder.cronSchedule("0 0/10 * * * ? *");*/
        // 创建设备状态同步trigger
		/*CronTrigger cronTriggerzdgl = TriggerBuilder.newTrigger().withIdentity(GpsZtSyncJob.class.getName(), "zdglsync")
				.withSchedule(scheduleBuilderZD).build();*/


        // 纠偏gps轨迹job
        /*JobDetail jiuPianJob = JobBuilder.newJob(GpsJuPianJob.class).withIdentity(GpsJuPianJob.class.getName(), "cljpsync")
						.build();*/
		/*// 鹰眼数据纠偏记录 每6小时执行一次
		CronScheduleBuilder scheduleBuilderJP = CronScheduleBuilder.cronSchedule("0 0 6/6 * * ? *");*/
        //创建gps点位纠偏trigger
        /*CronTrigger cronTriggercljp = TriggerBuilder.newTrigger().withIdentity(GpsJuPianJob.class.getName(), "cljpsync")
					.withSchedule(scheduleBuilderJP).build();*/

        // 上传鹰眼定时任务
        JobDetail zdToYyJob = JobBuilder.newJob(ZdToYyJob.class).withIdentity(ZdToYyJob.class.getName(), "zdToYy")
                .build();
        // 上传鹰眼定时任务，每1天执行一次
        CronScheduleBuilder zdToYyCron = CronScheduleBuilder.cronSchedule("0 0 0 1/1 * ? ");

        CronTrigger zdToYyTrigger = TriggerBuilder.newTrigger().withIdentity(ZdToYyJob.class.getName(), "zdToYy")
                .withSchedule(zdToYyCron).build();


        try {
            schedulerFactory.getScheduler().scheduleJob(jobDetail, cronTrigger);
            //schedulerFactory.getScheduler().scheduleJob(zdglJob, cronTriggerzdgl);
            schedulerFactory.getScheduler().scheduleJob(nianshenJob, cronTriggerclns);
//			schedulerFactory.getScheduler().scheduleJob(jiuPianJob, cronTriggercljp);
            schedulerFactory.getScheduler().scheduleJob(sbyxsjjlJob,sbyxsjjlTrigger);
            schedulerFactory.getScheduler().scheduleJob(zdToYyJob,zdToYyTrigger);
        } catch (SchedulerException e) {
            errorLog.error("任务创建失败", e);
        }
    }
}
