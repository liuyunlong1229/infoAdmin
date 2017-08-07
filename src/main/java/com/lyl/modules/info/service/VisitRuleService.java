package com.lyl.modules.info.service;

import java.util.HashMap;
import java.util.Map;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lyl.base.dao.BaseDao;
import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.modules.info.entity.VisitRule;
@Service
public class VisitRuleService {

	@Autowired
	private BaseDao			baseDao;
	
	

	public PageData findVisitRuleWithPage(VisitRule visitRule, PageParam pageParam) {
		StringBuffer hqlBuffer = new StringBuffer("select v from VisitRule v where 1=1");
		Map<String,Object> args=new HashMap<String,Object>();
		return baseDao.findByHQL(hqlBuffer.toString(),args, pageParam);
	}

	public void saveVisitRule(VisitRule visitRule) {
			baseDao.save(visitRule); 
	}

	public VisitRule getVisitRuleByCode(String code) {
		StringBuffer hqlBuffer = new StringBuffer("select v from VisitRule v where 1=1 and v.code=:code");
		Map<String,Object> args=new HashMap<String,Object>();
		args.put("code", code);
		Object obj= baseDao.findUniqueResultByHQL(hqlBuffer.toString(), args);
		if(obj !=null){
			return  (VisitRule)obj;
		}
		return null;
	}



}
