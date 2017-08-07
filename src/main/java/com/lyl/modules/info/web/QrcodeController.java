package com.lyl.modules.info.web;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.base.util.StringDateUtil;
import com.lyl.base.web.BaseController;
import com.lyl.modules.info.entity.Member;
import com.lyl.modules.info.entity.QrCode;
import com.lyl.modules.info.service.MemberService;
import com.lyl.modules.info.service.QrCodeService;
import com.lyl.modules.system.entity.SysResource;
import com.lyl.modules.system.entity.SysResource.ResourceType;

@Controller
@RequestMapping(value = "/qrCode")
public class QrcodeController extends BaseController{

	private static Logger	LOG	= Logger.getLogger(QrcodeController.class);

	@Autowired
	QrCodeService		 qrCodeService;		;



	@RequestMapping(value ="/list",method=RequestMethod.GET)
	public String showListView(){
		return "/info/qrCode/qrCode_list";
	}
	
	@RequestMapping(value ="/list",method=RequestMethod.POST)
	@ResponseBody
	public PageData list(QrCode qrCode,PageParam pageParam) {
		PageData pageData=  qrCodeService.findQrCodeWithPage(qrCode,pageParam);
		return pageData;

	}


	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		QrCode qrCode=new QrCode();
		qrCode.setCode(StringDateUtil.dateToString(new Date(), 5));
		model.addAttribute("qrCode", qrCode);
		return "/info/qrCode/qrCode_add";

	}

	@RequestMapping(value = "/add",method=RequestMethod.POST)
	@ResponseBody
	public String add(QrCode qrCode) {
		qrCodeService.saveQrCode(qrCode);
		return SUCCESS;
	}
	
	
	@RequestMapping(value = "/detail",method=RequestMethod.GET)
	public String add(Model model,String qrId) {
		QrCode qrCode=qrCodeService.getQrCodeById(qrId);
		model.addAttribute("qrCode", qrCode);
		return "/info/qrCode/qrCode_detail";
	}
	
	
	
}
