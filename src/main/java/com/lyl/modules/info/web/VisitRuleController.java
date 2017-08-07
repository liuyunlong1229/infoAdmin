package com.lyl.modules.info.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.base.util.StringDateUtil;
import com.lyl.base.web.BaseController;
import com.lyl.modules.info.entity.VisitRule;
import com.lyl.modules.info.service.VisitRuleService;

@Controller
@RequestMapping(value = "/visitRule")
public class VisitRuleController extends BaseController{

	private static Logger	LOG	= Logger.getLogger(VisitRuleController.class);

	@Autowired
	VisitRuleService		 visitRuleService;		;



	@RequestMapping(value ="/list",method=RequestMethod.GET)
	public String showListView(){
		return "/info/visitRule/visitRule_list";
	}
	
	@RequestMapping(value ="/list",method=RequestMethod.POST)
	@ResponseBody
	public PageData list(VisitRule vr,PageParam pageParam) {
		PageData pageData=  visitRuleService.findVisitRuleWithPage(vr,pageParam);
		return pageData;

	}



	@RequestMapping(value = "/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(VisitRule visitRule) {
		visitRule.setCode(StringDateUtil.dateToString(new Date(), 5));
		visitRuleService.saveVisitRule(visitRule);
		return SUCCESS;
	}
	
	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		return "/info/visitRule/visitRule_add";

	}
	
	@RequestMapping(value = "/visitRule/{code}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value="根据编码获得访问规则",httpMethod="GET",response=VisitRule.class)
	@ApiImplicitParam(name="code",value="规则编码",paramType="path",dataType="String",required=true)
	public VisitRule getRuleByCode(@PathVariable("code")String code) {
		VisitRule vr=visitRuleService.getVisitRuleByCode(code);
		return vr;

	}
	
}
