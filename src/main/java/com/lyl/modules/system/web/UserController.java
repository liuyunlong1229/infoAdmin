package com.lyl.modules.system.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.base.Constant;
import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.base.vo.ComboboxVo;
import com.lyl.base.web.BaseController;
import com.lyl.modules.system.entity.SysRole;
import com.lyl.modules.system.entity.SysUser;
import com.lyl.modules.system.service.RoleService;
import com.lyl.modules.system.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController{

	private static Logger	LOG	= Logger.getLogger(UserController.class);

	@Autowired
	UserService				userService;

	@Autowired
	RoleService				roleService;



	@RequestMapping(value ="/list",method=RequestMethod.GET)
	public String showListView(){
		return "/system/user/user_list";
	}
	
	@RequestMapping(value ="/list",method=RequestMethod.POST)
	@ResponseBody
	public PageData list(SysUser user,PageParam pageParam) {
		
		PageData pageData=  userService.findUsersWithPage(user,pageParam);
		return pageData;

	}

	@RequestMapping(value ="/add",method=RequestMethod.GET)
	public String showAddView() {
		return "/system/user/user_add";
	}

	
	
		
	@RequestMapping(value = "/checkExits")
	@ResponseBody
	public String checkNameIsExits(String loginName) {
		SysUser user=userService.findByLoginName(loginName);
		return user==null?"true":"false";
	}
	
	
	
	@RequestMapping(value = "/checkPwd")
	@ResponseBody
	public String checkPwdIsCorrect(HttpServletRequest request,String password) {
		SysUser user=(SysUser)request.getSession().getAttribute(Constant.SESSSION_USER);
		return userService.checkPwdIsCorrect(user,password)?"true":"false";
	}
	
	
	@RequestMapping(value = "/changePwd",method=RequestMethod.GET)
	public String showChangePwdView() {
		return "/system/user/user_changePwd";
	}
	
	@RequestMapping(value = "/changePwd")
	@ResponseBody
	public String changePwd(HttpServletRequest request,String newPassword) {
		SysUser user=(SysUser)request.getSession().getAttribute(Constant.SESSSION_USER);
		userService.updatePwd(user.getLoginName(), newPassword);
		return SUCCESS;
	}
	
	
	
	
	@RequestMapping(value = "/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(SysUser user) {
		userService.saveUser(user);
		return SUCCESS;
	}

	
	@RequestMapping(value = "/update",method=RequestMethod.GET)
	public String showUpdateView(String uid, Model model) {
		SysUser user = userService.findUserById(uid);
		model.addAttribute("user", user);
		return "/system/user/user_update";
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	@ResponseBody
	public String update(SysUser user) {
		userService.updateUser(user);
		return SUCCESS;
	}
	
	
	@RequestMapping(value ="/findAllRoles")
	@ResponseBody
	public List<ComboboxVo> findAllRoles(String uid){
		List<SysRole> allRoles=roleService.findAll();
		List<SysRole> hasRoleIds=null;
		if(StringUtils.isNotBlank(uid)){
			hasRoleIds=roleService.findRolesByUid(uid);
		}
		//return new ComboboxGenerater("name","id").generate(allRoles, hasRoleIds);
		return generateComboboxVo(allRoles,hasRoleIds);
	}

	private List<ComboboxVo> generateComboboxVo(List<SysRole> allRoles,
			List<SysRole> hasRoles) {
		 hasRoles=hasRoles==null?new ArrayList<SysRole>():hasRoles;
		 List<ComboboxVo> cvs=new ArrayList<ComboboxVo>();
		
		 for(SysRole r:allRoles){
			 boolean result=false;
			 ComboboxVo cv=new ComboboxVo(r.getId(), r.getName());
			 for(SysRole r1:hasRoles){
				 if(r1.getId().equals(r.getId())){
					 result=true;
					 break;
				 }
			 }
		      cv.setSelected(result?true:false);
		      cvs.add(cv);
		 }
		 
		return cvs;
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(String uid) {
		LOG.debug("删除的用户id==" + uid);
		SysUser user = new SysUser();
		user.setId(uid);
		userService.deleteUser(user);
		return SUCCESS;
	}
	
	@RequestMapping(value = "/reSetPwd")
	@ResponseBody
	public String reSetPwd(String uid) {
		SysUser user = new SysUser();
		user.setId(uid);
		userService.reSetPwd(user);
		return SUCCESS;
	}
	
	
}
