package com.ldz.biz.module.controller;

import com.ldz.biz.module.model.ClLsdw;
import com.ldz.biz.module.service.LsdwService;
import com.ldz.sys.base.BaseController;
import com.ldz.sys.base.BaseService;
import com.ldz.util.bean.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 临时车设定
 */
@RestController
@RequestMapping("api/lsdw")
public class LsdwCtrl extends BaseController<ClLsdw, String> {
	 @Autowired
	 private LsdwService service;

	@Override
	protected BaseService<ClLsdw, String> getBaseService() {
		
		return service;
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public ApiResponse<String> save(ClLsdw entity) {
		return service.saveEntity(entity);
	}

	/**
	 * 临时单位删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/remove/{pkid}", method={RequestMethod.POST})
	public ApiResponse<String> remove(@PathVariable("pkid")String id){
		return service.delUnit(id);
	}

}
