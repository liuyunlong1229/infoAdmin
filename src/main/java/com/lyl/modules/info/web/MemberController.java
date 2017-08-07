package com.lyl.modules.info.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.base.web.BaseController;
import com.lyl.modules.info.entity.Member;
import com.lyl.modules.info.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController extends BaseController{

	private static Logger	LOG	= Logger.getLogger(MemberController.class);

	@Autowired
	MemberService		 memberService;		;



	@RequestMapping(value ="/list",method=RequestMethod.GET)
	public String showListView(){
		return "/info/member/member_list";
	}
	
	@RequestMapping(value ="/list",method=RequestMethod.POST)
	@ResponseBody
	public PageData list(Member member,PageParam pageParam) {
		PageData pageData=  memberService.findMemberWithPage(member,pageParam);
		return pageData;

	}


	
	@RequestMapping(value = "/add",method=RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "添加会员信息接口", httpMethod = "POST", response = String.class, notes = "添加成功返回success字符串")
	@ApiImplicitParams({
				@ApiImplicitParam(name = "userId", value = "会员账号",paramType="query"),
				@ApiImplicitParam(name = "name", value = "姓名",paramType="query"),
				@ApiImplicitParam(name = "gender", value = "性别（1-男 2-女）",paramType="query"),
				@ApiImplicitParam(name = "age", value = "年龄",paramType="query"),
				@ApiImplicitParam(name = "hobbies", value = "爱好：多种爱好用中文逗号隔开，如（唱歌，打球，看书）",paramType="query"),
				@ApiImplicitParam(name = "mobile", value = "手机号",paramType="query"),
				@ApiImplicitParam(name = "alipayNum", value = "支付宝号",paramType="query"),
				@ApiImplicitParam(name = "weChatNum", value = "微信号",paramType="query"),
				@ApiImplicitParam(name = "email", value = "邮箱",paramType="query"),
				@ApiImplicitParam(name = "address", value = "住址",paramType="query")
	})
	public String add(String userId,String name,String gender, int age,  String hobbies, String mobile, String alipayNum, String weChatNum,String email, String address) {
		
		
			Member member=new Member();
			member.setUserId(userId);
			member.setName(name);
			member.setAge(age);
			member.setGender(gender);
			member.setHobbies(hobbies);
			member.setType(0); //默认为普通会员
			member.setMobile(mobile);
			member.setAlipayNum(alipayNum);
			member.setWeChatNum(weChatNum);
			member.setEmail(email);
			member.setAddress(address);
		
		memberService.saveMember(member);
		return SUCCESS;
	}
	
	
	@RequestMapping(value = "/detail",method=RequestMethod.GET)
	public String add(Model model,String mid) {
		Member member=memberService.getMemberById(mid);
		model.addAttribute("member", member);
		return "/info/member/member_detail";
	}
	
	
	@RequestMapping(value = "/sendMoney",method=RequestMethod.POST)
	@ResponseBody
	public String sendMoney(String id,String sendMoney) {
		memberService.sendMoney(id,sendMoney);
		return SUCCESS;
	}
	
	
	
}
