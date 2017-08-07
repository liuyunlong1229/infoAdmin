package com.lyl.base.interceptor;
import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

import com.lyl.modules.system.entity.SysUser;
import com.lyl.security.ThreadLocalUserHolder;


public class BaseDaoInterceptor extends EmptyInterceptor {
	
	private static final Logger LOG=Logger.getLogger(BaseDaoInterceptor.class); 
	
	private static final String OP_SYSTEM="system";

	@Override
	public String onPrepareStatement(String sql) {
		//TODO 根据执行的sql语句进行修改用户和创建用户的处理
		LOG.info("Prepare exec sql...."+sql);
		return super.onPrepareStatement(sql);
	}


	private static final long serialVersionUID = -3307821245690948643L;

	private static final String ADD_FLAG="save";
	private static final String UPDATE_FLAG="update";
	private static final String DELETE_FLAG="delete";
	
	private static final String CREATE_DATE_FILED="createDttm";
	private static final String CREATE_USER_FILED="createBy";
	private static final String UPDATE_DATE_FILED="updateDttm";
	private static final String UPDATE_USER_FILED="lastUpdateBy";
	
	

	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		setDefaultValues(ADD_FLAG,entity,state,propertyNames);
		return true;
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {
		setDefaultValues(UPDATE_FLAG,entity,currentState,propertyNames);
		return true;
	}
	
	
	private void setDefaultValues(String op,Object entity,Object[] state, String[] propertyNames ){
		String propertyName="";
		
		if(op.equals(ADD_FLAG)){
			for(int i=0;i<propertyNames.length;i++){
				propertyName=propertyNames[i];
				if(propertyName.equals(CREATE_DATE_FILED)){
					state[i]=new Date();
				}
				if(propertyName.equals(CREATE_USER_FILED)){
					SysUser user=ThreadLocalUserHolder.getContext();
					if(user!=null){
						state[i]=user.getLoginName();	
					}else{
						state[i]=OP_SYSTEM;
					}

				}
				if(propertyName.equals(UPDATE_DATE_FILED)){
					state[i]=new Date();
				}
				if(propertyName.equals(UPDATE_USER_FILED)){
					 SysUser user=ThreadLocalUserHolder.getContext();
					if(user!=null){
						state[i]=user.getLoginName();	
					}else{
						state[i]=OP_SYSTEM;
					}

				}
			}
			
		}else if(op.equals(UPDATE_FLAG)){
			
			for(int i=0;i<propertyNames.length;i++){
				propertyName=propertyNames[i];
				if(propertyName.equals(UPDATE_DATE_FILED)){
					state[i]=new Date();
				}
				if(propertyName.equals(UPDATE_USER_FILED)){
					SysUser user=ThreadLocalUserHolder.getContext();
					if(user!=null){
						state[i]=user.getLoginName();	
					}else{
						state[i]=OP_SYSTEM;
					}

					
				}
			}
		}
		
		
	}

	
}