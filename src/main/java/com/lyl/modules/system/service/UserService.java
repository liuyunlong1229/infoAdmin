package com.lyl.modules.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lyl.base.dao.BaseDao;
import com.lyl.base.paging.PageData;
import com.lyl.base.paging.PageParam;
import com.lyl.base.util.PasswordEncoder;
import com.lyl.modules.system.entity.SysUser;
import com.lyl.modules.system.entity.UserRole;
@Service
public class UserService {

	@Autowired
	private BaseDao			baseDao;

	@Autowired
	private PasswordEncoder	passwordEncoder;

	public SysUser findUserById(String Id) {
		return baseDao.getById(SysUser.class, Id);
	}

	@SuppressWarnings("unchecked")
	public SysUser findByLoginName(String loginName) {
		String hql = "from SysUser where loginName =:loginName";
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("loginName", loginName);
		List<SysUser> userList = baseDao.findByHQL(hql, args);
		SysUser user = null;
		if (userList != null && userList.size() > 0) {
			user = userList.get(0);
		}
		return user;
	}
	
	
	public SysUser validate(String loginName,String password){
		SysUser user=findByLoginName(loginName);
		if(user !=null){
			boolean result=passwordEncoder.isPasswordValid(user.getPassword(), password, user.getLoginName());
			if(result){
				return user;
			}
		}
		return null;
		
	}

	public PageData findUsersWithPage(SysUser user,PageParam pageParam) {
		
		StringBuffer hqlBuffer = new StringBuffer("select u from SysUser u where 1=1");
		Map<String,Object> args=new HashMap<String,Object>();
 		if(StringUtils.isNotBlank(user.getLoginName())){
			hqlBuffer.append(" and u.loginName=:loginName ");
			args.put("loginName", user.getLoginName());
		}
		if(StringUtils.isNotBlank(user.getSex())){
			hqlBuffer.append(" and u.sex=:sex ");
			args.put("sex", user.getSex());
		}
		return baseDao.findByHQL(hqlBuffer.toString(),args, pageParam);
	}

	@Transactional
	public void saveUser(SysUser user) {
		user.setPassword("000000");
		encryptPassword(user);
		String uid=(String)baseDao.save(user);
		
		if (StringUtils.isNotBlank(user.getRoleids())) {
			String [] roleIdArr=user.getRoleids().split(",");
			for (String rid :roleIdArr) {
				UserRole ur = new UserRole();
				ur.setUid(uid);
				ur.setRid(rid);
				baseDao.save(ur);
			}

		}
		

	}

	public void updateUser(SysUser user) {
		String uid=user.getId();
		String sql = "delete from T_SYS_USER_ROLE where uid='" + uid+"'";
		baseDao.execSQL(sql,null);
		if (StringUtils.isNotBlank(user.getRoleids())) {
			String [] roleIdArr=user.getRoleids().split(",");
			for (String rid :roleIdArr) {
				UserRole ur = new UserRole();
				ur.setUid(uid);
				ur.setRid(rid);
				baseDao.save(ur);
			}

		}
		SysUser sysUser=baseDao.getById(SysUser.class, uid);
		sysUser.setAddress(user.getAddress());
		sysUser.setSex(user.getSex());
		sysUser.setEmail(user.getEmail());
		sysUser.setTelephone(user.getTelephone());


	}

	public void deleteUser(SysUser user) {
		String sql = "delete from T_SYS_USER_ROLE where uid= '" + user.getId()+"'";
		baseDao.execSQL(sql,null);
		sql = "delete from T_SYS_USER where id= '" + user.getId()+"'";
		baseDao.execSQL(sql,null);
	}


	private void encryptPassword(SysUser user) {
		String encodePassword = passwordEncoder.encodePassword(user.getPassword(), user.getLoginName());
		user.setPassword(encodePassword);
	}

	
	public void reSetPwd(SysUser user) {
		SysUser sysUser=baseDao.getById(SysUser.class, user.getId());
		sysUser.setPassword("000000");
		encryptPassword(sysUser);
		baseDao.update(sysUser);
		
	}
	
	
	public void updatePwd(String loginName,String passWord) {
		SysUser sysUser=findByLoginName(loginName);
		sysUser.setPassword(passWord);
		encryptPassword(sysUser);
		baseDao.update(sysUser);
		
	}

	public boolean checkPwdIsCorrect(SysUser user, String password) {
		return passwordEncoder.isPasswordValid(user.getPassword(), password, user.getLoginName());
	}
	


}
