package com.lyl.security;


import org.junit.Assert;

import com.lyl.modules.system.entity.SysUser;

public class ThreadLocalUserHolder {

	    //~ Static fields/initializers =====================================================================================

	    private static final ThreadLocal<SysUser> contextUserHolder = new ThreadLocal<SysUser>();

	    //~ Methods ========================================================================================================

	    public  static void clearContext() {
	    	contextUserHolder.remove();
	    }

	    public static SysUser getContext() {
	    	SysUser user = contextUserHolder.get();
	        return user;
	    }
	    
	 
	    public static void setContext(SysUser user) {
	        Assert.assertNotNull("Only non-null SysUser instances are permitted" ,user );
	        contextUserHolder.set(user);
	    }
	}

