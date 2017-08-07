package com.lyl.modules.system.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.base.vo.ResourceVO;
import com.lyl.base.web.BaseController;
import com.lyl.modules.system.entity.SysResource;
import com.lyl.modules.system.entity.SysResource.ResourceType;
import com.lyl.modules.system.service.ResourceService;
import com.lyl.modules.system.service.RoleResourceService;
import com.lyl.modules.system.service.RoleService;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends BaseController {

	private static Logger	LOG	= Logger.getLogger(ResourceController.class);
	

	@Autowired
	RoleService				roleService;

	@Autowired
	ResourceService			resourceService;

	@Autowired
	RoleResourceService		roleResourceService;

	@RequestMapping(value = "/list",method=RequestMethod.GET)
	public String showListView() {
		return "/system/resource/resource_list";
	}
	
	@RequestMapping(value ="/list",method=RequestMethod.POST)
	@ResponseBody
	public ModelMap list() {
		List<ResourceVO> allResourceVos = resourceService.findAllResourceVos();
		ModelMap modelMap = new ModelMap();
		modelMap.put("rows", allResourceVos);// 数据表格数据传递
		modelMap.put("total", allResourceVos.size());// 统计条数传递
		Map<String, String> footer = new HashMap<String, String>();
		footer.put("name", "总记录数");
		footer.put("resourceType", allResourceVos.size() + "条");
		footer.put("iconCls", "icon-sum");
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list.add(footer);
		modelMap.put("footer", list);
		return modelMap;

	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(String rsid, Model model) {
		SysResource parent=null;
		SysResource resource=new SysResource();
		if("0".equals(rsid)){
			parent=new SysResource();
			parent.setName("系统资源");
			parent.setResType("根节点");
			parent.setId("0");
			resource.setResType(ResourceType.module.getText());
			resource.setResourceType(ResourceType.module);
		}else{
			parent=resourceService.findById(rsid);	
			if(parent.getResourceType()==ResourceType.module){
				 resource.setResType(ResourceType.menu.getText());
				 resource.setResourceType(ResourceType.menu);
			}else if(parent.getResourceType()==ResourceType.menu){
				 resource.setResType(ResourceType.button.getText());
				 resource.setResourceType(ResourceType.button);
			}else{
				 throw new RuntimeException("添加未知资源类型");
			}
		}
		model.addAttribute("parent", parent);
		model.addAttribute("resource", resource);
		return "/system/resource/resource_add";

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public String add(SysResource resource) {
		if("0".equals(resource.getPid())){
			resource.setPid(null);
		}
		resource.setIsLeaf(Boolean.TRUE);
		resourceService.saveResource(resource);
		return SUCCESS;
	}

	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String showUpdateView(String rsid, Model model) {
		//LOG.debug("修改的资源编号为==" + rsid);
		SysResource resource = resourceService.findById(rsid);
		resource.setResType(resource.getResourceType().getText());
		model.addAttribute("resource", resource);
		return "/system/resource/resource_update";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public String update(SysResource resource) {
		resourceService.updateResource(resource);
		return SUCCESS;
	}

	

	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(String rsid, Model model) {
		LOG.debug("删除的资源编号为==" + rsid);
		resourceService.delResById(rsid);
		return SUCCESS;
	}

}
