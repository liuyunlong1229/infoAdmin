package com.lyl.modules.system.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lyl.base.Constant;
import com.lyl.base.web.BaseController;
import com.lyl.modules.system.entity.SysResource;
import com.lyl.modules.system.entity.SysResource.ResourceType;
import com.lyl.modules.system.entity.SysUser;
import com.lyl.modules.system.service.ResourceService;

@Controller
@RequestMapping(value = "/index")
public class IndexController extends BaseController{

	@Autowired
	private ResourceService	resourceService;
	

	@RequestMapping(value = "/init")
	public String init(HttpServletRequest request) {
		
		//System.err.println("index的init方法开始");
		HttpSession session=request.getSession();
		SysUser user=(SysUser)session.getAttribute(Constant.SESSSION_USER);
		String loginName=user.getLoginName();
		List<SysResource> resouceList = resourceService.findResourcesByLoginName(loginName);
		Set<SysResource> resourceSet=new HashSet<SysResource>();
		resourceSet.addAll(resouceList);
		List<SysResource> menuList=groupResources(resourceSet);
		request.setAttribute("menus", menuList);
		//System.err.println("index的init方法结束");
		return "index";
		

	}
	
	


	public List<SysResource> groupResources(Set <SysResource> resouceList){
		List<SysResource> moduleList=new ArrayList<SysResource>();
		for(SysResource res:resouceList){
			if(res.getResourceType()==ResourceType.module){
				moduleList.add(res);
			}
		}

		
		Collections.sort(moduleList,new Comparator<SysResource>(){

			@Override
			public int compare(SysResource o1, SysResource o2) {
				return o1.getOrderNo()>o2.getOrderNo()?1:-1;
			}
			
			
		});
		
		/*for(SysResource sr:moduleList){
			System.out.println(sr.getName());
		}*/
		
	  for(SysResource moudle:moduleList){
		  List<SysResource> menuList=new ArrayList<SysResource>();
		  for(SysResource res:resouceList){
				if(res.getResourceType()==ResourceType.menu && res.getPid().equals(moudle.getId())){
					menuList.add(res);
				}
			} 
		  Collections.sort(menuList,new Comparator<SysResource>(){

				@Override
				public int compare(SysResource o1, SysResource o2) {
					return o1.getOrderNo()>o2.getOrderNo()?1:-1;
				}
				
				
			});
		 /* for(SysResource sr:menuList){
			  System.err.println(sr.getName());
			} */
		  moudle.getSubList().addAll(menuList);
	  }
	  
	  return moduleList;
		
	}

	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

}
