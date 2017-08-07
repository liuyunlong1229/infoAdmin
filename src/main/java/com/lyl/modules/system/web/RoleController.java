package com.lyl.modules.system.web;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.base.vo.TreeVo;
import com.lyl.base.web.BaseController;
import com.lyl.modules.system.entity.SysRole;
import com.lyl.modules.system.service.ResourceService;
import com.lyl.modules.system.service.RoleResourceService;
import com.lyl.modules.system.service.RoleService;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

	private static Logger	LOG	= Logger.getLogger(RoleController.class);

	@Autowired
	RoleService				roleService;

	@Autowired
	ResourceService			resourceService;

	@Autowired
	RoleResourceService		roleResourceService;

	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String showListView() {
		return "/system/role/role_list";

	}
	
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public PageData list(PageParam pageParam) {
		PageData pageData=  roleService.findRolesWithPage(pageParam);
		 return pageData;

	}

	@RequestMapping(value = "/add",method=RequestMethod.GET)
	public String showAddView() {
		return "/system/role/role_add";
	}

	
	@RequestMapping(value = "/findResourceTree")
	@ResponseBody
	public List<TreeVo> findResourceTree(String  rid){
		List<TreeVo> resourceTree=resourceService.findResourceTreeByRid(rid);
		return resourceTree;
		
	}
	
	@RequestMapping(value = "/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(SysRole role) {
		roleService.saveRole(role);
		return SUCCESS;
	}

	
	@RequestMapping(value = "/update",method=RequestMethod.GET)
	public String showUpdateView(String rid, Model model) {
		SysRole role = roleService.findRoleById(rid);
		model.addAttribute("role", role);
		return "/system/role/role_update";
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	@ResponseBody
	public String update(SysRole role) {
		roleService.updateRole(role);
		return SUCCESS;
	}

	

	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(String rid) {
		SysRole role = new SysRole();
		role.setId(rid);
		roleService.deleteRole(role);
		return SUCCESS;
	}

}
