package com.lyl.base.hander;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


public class BaseExceptionHandler implements HandlerExceptionResolver {
	
	protected final Log LOG = LogFactory.getLog(getClass());

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();
		String message = ExceptionUtils.getStackTrace(ex);
		model.put("message", message);
		/*
		 * if(ex instanceof BusinessException) { return new
		 * ModelAndView("error-business", model); }else if(ex instanceof
		 * ParameterException) { return new ModelAndView("error-parameter",
		 * model); } else { return new ModelAndView("error", model); }
		 */
		LOG.error(ex.getMessage(), ex);
	        return new ModelAndView("error", model);  
	}

}
