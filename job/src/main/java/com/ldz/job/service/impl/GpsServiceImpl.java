package com.ldz.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.stereotype.Service;

import com.ldz.job.mapper.ClGpsLsMapper;
import com.ldz.job.mapper.ClGpsMapper;
import com.ldz.job.model.ClGps;
import com.ldz.job.model.ClGpsLs;
import com.ldz.job.service.GpsService;
import com.ldz.sys.base.BaseServiceImpl;
import com.ldz.util.commonUtil.JsonUtil;
import com.ldz.util.redis.RedisTemplateUtil;

import tk.mybatis.mapper.common.Mapper;

@Service
public class GpsServiceImpl extends BaseServiceImpl<ClGps, String> implements GpsService {
	@Autowired
	private ClGpsMapper entityMapper;
	@Autowired
	private RedisTemplateUtil redis;
	@Autowired
	private ClGpsLsMapper clgpsMapper;

	@Override
	protected Mapper<ClGps> getBaseMapper() {
		return entityMapper;
	}

	@Override
	public void insetAndUpdate(ClGps entity) {

		boolean flag = ifExists("zdbh", entity.getZdbh());
		if (flag == true) {
			update(entity);
		} else {
			save(entity);
		}
	}

	@Override
	public void InsetRedisToDb(String zdbh) {

		String bean = (String) redis.boundValueOps(ClGps.class.getSimpleName() + zdbh).get();
		if (bean != null) {
			ClGps object = JsonUtil.toBean(bean, ClGps.class);
			// 将该终端的实时点位插入数据库中
			insetAndUpdate(object);
		}

		BoundListOperations<Object, Object> boundListOps = redis.boundListOps(ClGpsLs.class.getSimpleName() + zdbh);
		
		String index=(String) boundListOps.index(0);
		if (StringUtils.isNotEmpty(index)) {
			Long length = boundListOps.size();
			List<ClGpsLs> list = new ArrayList<>();
			for (int i = 0; i < length; i++) {
				String clgpsls = (String) boundListOps.rightPop();
				ClGpsLs gpssss = JsonUtil.toBean(clgpsls, ClGpsLs.class);
				list.add(gpssss);

			}

			clgpsMapper.insertList(list);

		}

	}

}
