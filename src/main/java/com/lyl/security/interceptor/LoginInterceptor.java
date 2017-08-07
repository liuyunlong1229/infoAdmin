package com.lyl.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lyl.base.Constant;
import com.lyl.modules.system.entity.SysUser;
import com.lyl.security.ThreadLocalUserHolder;

public class LoginInterceptor implements HandlerInterceptor {

	private static Logger	LOG	= Logger.getLogger(LoginInterceptor.class);

	
	private String[]	ignoreUrlArr;

	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpServletRequest req = request;
		HttpServletResponse resp = response;
		//LOG.info("LoginInterceptor的preHandle方法");
		String reqURI = req.getRequestURI();
		String requestType = request.getHeader("X-Requested-With");
		//LOG.info("reqURL==" + reqURI + "    reqType==" + requestType);
		
		Object user=req.getSession().getAttribute(Constant.SESSSION_USER);
		if (user!= null) {
			ThreadLocalUserHolder.setContext((SysUser)user);
			return true;
		}
		if (isIgnoreURL(reqURI)) {
			return true;
		}
		if (isAjaxRequest(req)) {
			resp.sendError(HttpStatus.UNAUTHORIZED.value());
		} else {
			resp.sendRedirect(req.getContextPath() + "/login");
		}

		return false;
	}

	private boolean isIgnoreURL(String reqUrl) {
		for (String ignoreUrl : ignoreUrlArr) {
			if(reqUrl.contains(ignoreUrl)){
				return true;
			}
		}
		return false;
	}
	

	private boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		return requestType != null && requestType.equals("XMLHttpRequest");
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//LOG.info("LoginInterceptor的postHandle方法");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//LOG.info("LoginInterceptor的afterCompletion方法");
	}

	public String[] getIgnoreUrlArr() {
		return ignoreUrlArr;
	}

	public void setIgnoreUrlArr(String[] ignoreUrlArr) {
		this.ignoreUrlArr = ignoreUrlArr;
	}

}
