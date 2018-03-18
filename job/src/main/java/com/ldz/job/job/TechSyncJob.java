package com.ldz.job.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//在成功执行了job类的execute方法后,更新JobDetail中JobDataMap的数据
@PersistJobDataAfterExecution
//等待上一次任务执行完成，才会继续执行新的任务
@DisallowConcurrentExecution
public class TechSyncJob implements Job{
    
    Logger errorLog = LoggerFactory.getLogger("error_info");
	
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
    }
	/*@Autowired
	private TechRecordService techRecordService;
	@Autowired
	private RedisTemplateUtil redisDao;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	BoundListOperations<Object, Object> cacheList = this.redisDao.boundListOps(Constants.RedisCacheKey.TECH_TOGA_KEY);
		if (cacheList != null && cacheList.size() > 0){
			Map<String, BeianTechRecord> exeMap = new HashMap<String, BeianTechRecord>();
			long cSize = cacheList.size();
			for (int i=0; i<cSize; i++){
				try{
					BeianTechRecord record = (BeianTechRecord)cacheList.rightPop();
					if (record != null && !exeMap.containsKey(record.getTechId())){
						record.setScr("系统自动上传");
						ApiResponse<String> res = this.techRecordService.syncToGa(record);
						if (res.getCode() == ApiResponse.SUCCESS){
							//将已写入的记录，放入临时缓存，防止相同记录重复请求接口
							exeMap.put(record.getTechId(), record);
						}
					}
				}catch(Exception e){
					errorLog.error("同步培训记录异常", e);
					JobExecutionException e2 = new JobExecutionException(e);  
			        //当任务执行失败时，立即停止所有相关这个任务的触发器
			        e2.setRefireImmediately(true);  
				}
			}
			
			exeMap.clear();
		}
    }*/
}