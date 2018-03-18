package com.ldz.job.job;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;

import com.ldz.job.mapper.ClClMapper;
import com.ldz.job.mapper.ClGpsLsMapper;
import com.ldz.job.mapper.ClGpsMapper;
import com.ldz.job.model.ClCl;
import com.ldz.job.model.ClGpsLs;
import com.ldz.util.redis.RedisTemplateUtil;

/**
 * 定时器说明:每隔10分钟定时从redis里面获取数据写入CLgps,CLgpsLs表中
 * 
 * @author liuzhihao
 *
 */
// 在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
// 等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution
public class GpsSaveJob implements Job {
	Logger errorLog = LoggerFactory.getLogger("error_info");
	@Autowired
	private RedisTemplateUtil redis;
	@Autowired
	private ClClMapper clclmapper;
	@Autowired
	private ClGpsMapper clgpsmapper;
	@Autowired
	private ClGpsLsMapper clgpsls;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		// 获取所有的终端编号
		List<ClCl> gpslist = clclmapper.selectAll();
		Collection<Object> zubhList = gpslist.stream().map(ClCl::getZdbh).collect(Collectors.toList());

		// 从redis(实时)里面获取所有终端车辆的gps信息
		List<Object> gpsList = redis.boundHashOps("GPSDW").multiGet(zubhList);

		try {

			if (gpsList != null && gpsList.size() > 0) {
				// 批量存入数据库里面
				clgpsmapper.saveGpsList(gpsList);
			}
			for (Object gpsls : zubhList) {
				BoundListOperations<Object, Object> cacheList = redis.boundListOps(gpsls);
				for (int i = 0; i < cacheList.size(); i++) {
					ClGpsLs rightPop = (ClGpsLs) cacheList.rightPop();
					//将gps历史信息插入数据库里面
					clgpsls.insertSelective(rightPop);
				}
			}

		} catch (Exception e) {
			errorLog.error("同步redis中gps数据异常", e);
			JobExecutionException e2 = new JobExecutionException(e);
			// 当任务执行失败时，立即停止所有相关这个任务的触发器
			e2.setRefireImmediately(true);
		}

	}
}
