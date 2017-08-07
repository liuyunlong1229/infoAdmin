package com.lyl.modules.system.web;



import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.lyl.base.Constant;
import com.lyl.base.util.KaptchaUtil;
import com.lyl.base.web.BaseController;
import com.lyl.modules.system.entity.SysUser;
import com.lyl.modules.system.service.UserService;
import com.lyl.security.AuthenticationHolder;

@Controller
public class LoginController extends BaseController {
	
	
	private static Logger	LOG	= Logger.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;
	
	@Autowired
	KaptchaUtil kaptchaUtil;
	@Autowired
	AuthenticationHolder authenticationHolder;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String showLoginView(HttpServletRequest reqeust,HttpServletResponse response){
		System.out.println("request url=="+reqeust.getRequestURI());
		//Cookie[]  cookies=reqeust.getCookies();
		//for(Cookie ck:cookies){
		//	LOG.info(ck.getName()+"=="+ck.getValue()+"=="+ck.getMaxAge());
		//}
		return "login";
	}
	
 	
	@RequestMapping(value="/showKaptcha")
	public void showKaptcha(HttpServletRequest reques,HttpServletResponse resp){
		try {
			kaptchaUtil.generate(reques, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> login(HttpServletRequest request,Model model){
		String session_katcha=(String)request.getSession().getAttribute( Constants.KAPTCHA_SESSION_KEY);
		String req_katcha=request.getParameter("katcha");
		/*if(!req_katcha.equals(session_katcha)){
			resultMap.put("code", "0");
			resultMap.put("errorMsg","验证码错误");
		}*/
		
		
		
		Map<String,String> resultMap=new HashMap<String,String>();
		String loginName=request.getParameter("loginName");
		String password=request.getParameter("password");
		
		SysUser user=userService.validate(loginName, password);
		if(user ==null){
			resultMap.put("code", "0");
			resultMap.put("errorMsg","用户名或密码错误");
			
			return resultMap;
		}
		request.getSession().setAttribute(Constant.SESSSION_USER, user);
		authenticationHolder.setAuthResource(request);
		
		resultMap.put("code", "1");
		
		return resultMap;
	}

	
	
	
	@RequestMapping(value="/logout")
	public String login(HttpServletRequest request){
		authenticationHolder.destoryAuthResource(request);
		request.getSession().invalidate();
		return "redirect:/login";
	}
	
	
	
	
}
