package com.lyl.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lyl.base.Constant;
import com.lyl.base.util.CacheUtils;
import com.lyl.modules.system.entity.SysResource;
import com.lyl.modules.system.entity.SysResource.ResourceType;
import com.lyl.modules.system.entity.SysUser;
import com.lyl.modules.system.service.ResourceService;

@Component
public class AuthenticationHolder {
	
	@Autowired
	private ResourceService resourceService;
	
	private static final Logger LOG=Logger.getLogger(AuthenticationHolder.class);

	public void setAuthResource(HttpServletRequest request) {
		SysUser user =(SysUser)request.getSession().getAttribute(Constant.SESSSION_USER);
		String loginName=user.getLoginName();
		List<SysResource> resourceList=resourceService.findResourcesByLoginName(loginName);
		
		Set<SysResource> resourceSet=new HashSet<SysResource>(resourceList);
		List<String> bttonList=new ArrayList<String>();
		List<String> urlList=new ArrayList<String>();
 		for(SysResource rs:resourceSet){
			if(rs.getResourceType().equals(ResourceType.button)){
				bttonList.add(rs.getLabel());
			}
			if(StringUtils.isNotBlank(rs.getResourcePath())){
				urlList.add(rs.getResourcePath());
			}
			
		}
 		urlList.add("index/init");
 		urlList.add("index/welcome");
		CacheUtils.put(loginName+Constant.BUTTON_SUFFIX, bttonList);
		CacheUtils.put(loginName+Constant.URL_SUFFIX, urlList);
		
		LOG.info("======user  permissions has cached finished=======");

	}
	
	
	public void destoryAuthResource(HttpServletRequest req){
		HttpSession session=req.getSession();
		SysUser user=(SysUser)session.getAttribute(Constant.SESSSION_USER);
		String logiName=user.getLoginName();
		CacheUtils.remove(logiName+Constant.BUTTON_SUFFIX);
		
		LOG.info("======user permissions has destory  finished=======");
	}


	
	
}
