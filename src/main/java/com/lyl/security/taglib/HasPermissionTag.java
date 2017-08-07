package com.lyl.security.taglib;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.lyl.base.Constant;
import com.lyl.base.util.CacheUtils;
import com.lyl.modules.system.entity.SysUser;

/**
 * @since 0.1
 */
public class HasPermissionTag extends TagSupport {

	private static final long serialVersionUID = -2315857476162041215L;
	
	private static final Logger LOG=Logger.getLogger(HasPermissionTag.class);
	
	private String name = null;

    public HasPermissionTag() {
    	
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void verifyAttributes() throws JspException {
        String permission = getName();

        if (permission == null || permission.length() == 0) {
            String msg = "The 'name' tag attribute must be set.";
            throw new JspException(msg);
        }
    }

    @Override
	public int doStartTag() throws JspException {
    	verifyAttributes();
    	return onDoStartTag();
	}

	public int onDoStartTag() throws JspException {

        String p = getName();
        boolean show = showTagBody(p);
        if (show) {
            return TagSupport.EVAL_BODY_INCLUDE;
        } else {
            return TagSupport.SKIP_BODY;
        }
    }

	
    @SuppressWarnings("unchecked")
	protected boolean isPermitted(String p) {
    	HttpSession session=pageContext.getSession();
    	Object obj=session.getAttribute(Constant.SESSSION_USER);
    	Assert.assertNotNull("the user not found in session", obj);
		SysUser user=(SysUser)obj;
		Object objs=CacheUtils.get(user.getLoginName()+Constant.BUTTON_SUFFIX);
		Assert.assertNotNull("the permission cached not found", objs);
		List<String> permissions=(List<String>)objs;
		if(permissions.contains(p)){
			LOG.info("can access in ");
			return true;
		}
 
		
        return false;
    }

    protected boolean showTagBody(String p){
    	return isPermitted(p);
    }

}
