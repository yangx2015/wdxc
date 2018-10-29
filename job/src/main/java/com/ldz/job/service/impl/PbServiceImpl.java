package com.ldz.job.service.impl;

import com.ldz.job.mapper.ClPbMapper;
import com.ldz.job.model.ClPb;
import com.ldz.job.service.PbService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.bean.SimpleCondition;
import com.ldz.util.commonUtil.DateUtils;
import com.ldz.util.redis.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PbServiceImpl extends BaseServiceImpl<ClPb, String> implements PbService {
	@Autowired
	private ClPbMapper entityMapper;
	Logger errorLog = LoggerFactory.getLogger("error_info");
	Logger accessLog = LoggerFactory.getLogger("access_info");
	@Override
	protected Mapper<ClPb> getBaseMapper() {
		return entityMapper;
	}

	@Override
	protected Class<?> getEntityCls() {
		return ClPb.class;
	}

	@Autowired
	private RedisTemplateUtil redisTemplate;

	/**
	 * 移除一条排班
	 */
	@Override
	public void removePb(){
		String time = DateUtils.getNowTime().substring(11);
		SimpleCondition condition = new SimpleCondition(ClPb.class);
		condition.lte(ClPb.InnerColumn.endTime,time);
		condition.and().andCondition(" TO_CHAR (PBSJ, 'yyyy-MM-dd') = ",DateUtils.getToday("yyyy-MM-dd"));
		List<ClPb> pbs = this.findByCondition(condition);
		errorLog.error("查询到的数据量：", pbs.size());
		if (pbs.size() == 0) return;
		List<String> pbIdList=pbs.stream().map(ClPb::getId).collect(Collectors.toList());
		errorLog.error("返回的排班列表：", org.apache.commons.lang.StringUtils.join(pbIdList, ","));
		redisTemplate.convertAndSend("removePbJOB", org.apache.commons.lang.StringUtils.join(pbIdList, ","));
	}
}
